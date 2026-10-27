package puntograno.conexion;

import java.sql.Connection;
import java.sql.SQLException;

public class NuevaConexion {

    public static void main(String[] args) {

        try (Connection conexion = ConexionBD.conectar()) {
            System.out.println("¡Conexion EXITOSA!");
        } catch (SQLException e) {
            System.out.println("Error servidor:");
            e.printStackTrace();
        }
    }
}
