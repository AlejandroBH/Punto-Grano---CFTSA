package puntograno.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.UUID;
import puntograno.conexion.ConexionBD;
import puntograno.modelos.Cliente;

public class ClienteDAO {

    public boolean registrarCliente(Cliente cliente) throws SQLException {
        String sqlCliente = "INSERT INTO cliente (id_cliente, rut_cliente, nombre, telefono, email, fecha_registro) VALUES (?, ?, ?, ?, ?, ?)";
        String sqlTarjeta = "INSERT INTO tarjeta_fidelidad (id_tarjeta, puntos_acumulados, fecha_emision, id_cliente) VALUES (?, ?, ?, ?)";

        String nuevoIdCliente = UUID.randomUUID().toString();
        String nuevoIdTarjeta = UUID.randomUUID().toString();

        try (Connection conexion = ConexionBD.conectar()) {
            conexion.setAutoCommit(false); // Iniciamos transacción controlada

            try (
                    PreparedStatement consultaCliente = conexion.prepareStatement(sqlCliente);
                    PreparedStatement consultaTarjeta = conexion.prepareStatement(sqlTarjeta)) {

                // 1. Insertamos el cliente
                consultaCliente.setString(1, nuevoIdCliente);
                consultaCliente.setString(2, cliente.getRut());
                consultaCliente.setString(3, cliente.getNombre());
                consultaCliente.setString(4, cliente.getTelefono());
                consultaCliente.setString(5, cliente.getEmail());
                consultaCliente.setDate(6, new Date(cliente.getFechaRegistro().getTime()));
                int filasCliente = consultaCliente.executeUpdate();

                // 2. Insertamos su tarjeta de fidelidad
                consultaTarjeta.setString(1, nuevoIdTarjeta);
                consultaTarjeta.setInt(2, cliente.getTarjeta().getPuntosAcumulados());
                consultaTarjeta.setDate(3, new Date(cliente.getTarjeta().getFechaEmision().getTime()));
                consultaTarjeta.setString(4, nuevoIdCliente);
                int filasTarjeta = consultaTarjeta.executeUpdate();

                if (filasCliente == 1 && filasTarjeta == 1) {
                    conexion.commit(); // Confirmamos transacción
                    cliente.setIdUsuario(nuevoIdCliente);
                    return true;
                } else {
                    conexion.rollback(); // Revertimos si alguna inserción falla
                    return false;
                }
            } catch (SQLException ex) {
                conexion.rollback(); // Revertimos ante cualquier error
                throw ex;
            }
        }
    }

    public ArrayList<Cliente> listarClientes() throws SQLException {
        ArrayList<Cliente> lista = new ArrayList<>();
        String sql = "SELECT cliente.id_cliente, cliente.rut_cliente, cliente.nombre, cliente.telefono, cliente.email, tarjeta_fidelidad.puntos_acumulados "
                + "FROM cliente "
                + "INNER JOIN tarjeta_fidelidad ON tarjeta_fidelidad.id_cliente = cliente.id_cliente "
                + "ORDER BY cliente.fecha_registro ASC";

        try (
                Connection conexion = ConexionBD.conectar();
                PreparedStatement consulta = conexion.prepareStatement(sql);
                ResultSet resultado = consulta.executeQuery()) {

            while (resultado.next()) {
                Cliente cliente = new Cliente(
                        resultado.getString("rut_cliente"),
                        resultado.getString("nombre"),
                        resultado.getString("telefono"),
                        resultado.getString("email")
                );
                cliente.setIdUsuario(resultado.getString("id_cliente"));

                int puntos = resultado.getInt("puntos_acumulados");
                cliente.getTarjeta().actualizarProgreso(puntos);

                lista.add(cliente);
            }
        }
        return lista;
    }

    public boolean actualizarCliente(Cliente cliente) throws SQLException {
        String sql = "UPDATE cliente SET rut_cliente = ?, nombre = ?, telefono = ?, email = ? WHERE id_cliente = ?";

        try (
                Connection conexion = ConexionBD.conectar();
                PreparedStatement consulta = conexion.prepareStatement(sql)) {

            consulta.setString(1, cliente.getRut());
            consulta.setString(2, cliente.getNombre());
            consulta.setString(3, cliente.getTelefono());
            consulta.setString(4, cliente.getEmail());
            consulta.setString(5, cliente.getIdUsuario());

            int filasActualizadas = consulta.executeUpdate();
            return filasActualizadas == 1;
        }
    }

    public boolean eliminarCliente(String idCliente) throws SQLException {
        String sqlTarjeta = "DELETE FROM tarjeta_fidelidad WHERE id_cliente = ?";
        String sqlCliente = "DELETE FROM cliente WHERE id_cliente = ?";

        try (
                Connection conexion = ConexionBD.conectar();) {
            conexion.setAutoCommit(false);

            try (
                    PreparedStatement eliminarTarjeta = conexion.prepareStatement(sqlTarjeta);
                    PreparedStatement eliminarCliente = conexion.prepareStatement(sqlCliente);) {
                eliminarTarjeta.setString(1, idCliente);
                eliminarTarjeta.executeUpdate();
                eliminarCliente.setString(1, idCliente);

                int filasEliminadas = eliminarCliente.executeUpdate();

                if (filasEliminadas == 1) {
                    conexion.commit();
                    return true;
                }
                conexion.rollback();
                return false;
            } catch (SQLException ex) {
                conexion.rollback();
                throw ex;
            }
        }
    }
}
