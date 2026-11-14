package puntograno.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.UUID;
import puntograno.conexion.ConexionBD;
import puntograno.modelos.Usuario;

public class ClienteDAO {

    public boolean registrarCliente(Usuario usuario) throws SQLException {
        String sql = "INSERT INTO cliente (id_cliente, rut_cliente, nombre, telefono, email, fecha_registro) VALUES (?, ?, ?, ?, ?, ?)";

        String nuevoId = UUID.randomUUID().toString();

        try (
                Connection conexion = ConexionBD.conectar();
                PreparedStatement consulta = conexion.prepareStatement(sql);) {

            consulta.setString(1, nuevoId);
            consulta.setString(2, usuario.getRut());
            consulta.setString(3, usuario.getNombre());
            consulta.setString(4, usuario.getTelefono());
            consulta.setString(5, usuario.getEmail());
            consulta.setDate(6, new Date(usuario.getFechaRegistro().getTime()));

            int filasInsertadas = consulta.executeUpdate();

            if (filasInsertadas == 1) {
                usuario.setIdUsuario(nuevoId);
                return true;
            }
            return false;
        }
    }
}
