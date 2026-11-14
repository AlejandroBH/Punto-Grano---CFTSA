package puntograno.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.UUID;
import puntograno.conexion.ConexionBD;
import puntograno.modelos.Cliente;
import puntograno.modelos.Producto;
import puntograno.modelos.Recompensa;

public class RecompensaDAO {

    public boolean registrarRecompensa(Recompensa recompensa) throws SQLException {
        String sql = "INSERT INTO recompensa (id_recompensa, id_producto, costo_puntos, disponible) VALUES (?, ?, ?, ?)";

        String nuevoId = UUID.randomUUID().toString();

        try (
                Connection conexion = ConexionBD.conectar();
                PreparedStatement consulta = conexion.prepareStatement(sql)) {

            consulta.setString(1, nuevoId);
            consulta.setString(2, recompensa.getProducto().getCodigo().toString());
            consulta.setInt(3, recompensa.getCostoPuntos());
            consulta.setBoolean(4, recompensa.isDisponible());

            int filasInsertadas = consulta.executeUpdate();

            if (filasInsertadas == 1) {
                recompensa.setIdRecompensa(UUID.fromString(nuevoId));
                return true;
            }
            return false;
        }
    }

    public ArrayList<Recompensa> listarRecompensas() throws SQLException {
        ArrayList<Recompensa> lista = new ArrayList<>();

        String sql = "SELECT recompensa.id_recompensa, recompensa.costo_puntos, recompensa.disponible, "
                + "producto.id_producto, producto.nombre, producto.categoria, producto.precio, producto.stock, producto.disponible AS producto_disponible "
                + "FROM recompensa "
                + "INNER JOIN producto ON recompensa.id_producto = producto.id_producto "
                + "ORDER BY producto.nombre ASC";

        try (
                Connection conexion = ConexionBD.conectar();
                PreparedStatement consulta = conexion.prepareStatement(sql);
                ResultSet resultado = consulta.executeQuery()) {

            while (resultado.next()) {
                Producto producto = new Producto(
                        resultado.getString("nombre"),
                        resultado.getString("categoria"),
                        resultado.getInt("precio"),
                        resultado.getInt("stock"),
                        resultado.getBoolean("producto_disponible")
                );
                producto.setCodigo(UUID.fromString(resultado.getString("id_producto")));

                Recompensa recompensa = new Recompensa(
                        producto,
                        resultado.getInt("costo_puntos"),
                        resultado.getBoolean("disponible")
                );
                recompensa.setIdRecompensa(UUID.fromString(resultado.getString("id_recompensa")));

                lista.add(recompensa);
            }
        }
        return lista;
    }

    public boolean actualizarRecompensa(Recompensa recompensa) throws SQLException {
        String sql = "UPDATE recompensa SET id_producto = ?, costo_puntos = ?, disponible = ? WHERE id_recompensa = ?";

        try (
                Connection conexion = ConexionBD.conectar();
                PreparedStatement consulta = conexion.prepareStatement(sql)) {

            consulta.setString(1, recompensa.getProducto().getCodigo().toString());
            consulta.setInt(2, recompensa.getCostoPuntos());
            consulta.setBoolean(3, recompensa.isDisponible());
            consulta.setString(4, recompensa.getIdRecompensa().toString());

            int filasActualizadas = consulta.executeUpdate();
            return filasActualizadas == 1;
        }
    }

    public boolean eliminarRecompensa(String idRecompensa) throws SQLException {
        String sql = "DELETE FROM recompensa WHERE id_recompensa = ?";

        try (
                Connection conexion = ConexionBD.conectar();
                PreparedStatement consulta = conexion.prepareStatement(sql)) {

            consulta.setString(1, idRecompensa);

            int filasEliminadas = consulta.executeUpdate();
            return filasEliminadas == 1;
        }
    }

    public boolean canjearRecompensa(Cliente cliente, Recompensa recompensa) throws SQLException {
        String sqlTarjeta = "UPDATE tarjeta_fidelidad SET puntos_acumulados = puntos_acumulados - ? WHERE id_cliente = ?";
        String sqlProducto = "UPDATE producto SET stock = stock - 1 WHERE id_producto = ?";

        try (Connection conexion = ConexionBD.conectar()) {
            conexion.setAutoCommit(false); // Transacción controlada

            try (
                    PreparedStatement consultaTarjeta = conexion.prepareStatement(sqlTarjeta);
                    PreparedStatement consultaProducto = conexion.prepareStatement(sqlProducto)) {

                // 1. Descontamos los puntos de la tarjeta del cliente
                consultaTarjeta.setInt(1, recompensa.getCostoPuntos());
                consultaTarjeta.setString(2, cliente.getIdUsuario());
                int filasTarjeta = consultaTarjeta.executeUpdate();

                // 2. Descontamos 1 unidad del stock del producto canjeado
                consultaProducto.setString(1, recompensa.getProducto().getCodigo().toString());
                int filasProducto = consultaProducto.executeUpdate();

                if (filasTarjeta == 1 && filasProducto == 1) {
                    conexion.commit(); // Confirmamos transaccion
                    return true;
                } else {
                    conexion.rollback(); // Revertimos si falla
                    return false;
                }
            } catch (SQLException ex) {
                conexion.rollback(); // Revertimos ante cualquier error
                throw ex;
            }
        }
    }
}
