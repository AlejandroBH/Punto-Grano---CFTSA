package puntograno.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import puntograno.conexion.ConexionBD;

public class ClienteDAO {

    public boolean registrarCliente() throws SQLException {
        String sql = "INSERT INTO cliente (rut_cliente,nombre,telefono,email, fecha_registro) VALUES (?, ?, ?, ?, NOW())";

        try (
                Connection conexion = ConexionBD.conectar();
                PreparedStatement consulta = conexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {
        }

        return false;
    }

}
