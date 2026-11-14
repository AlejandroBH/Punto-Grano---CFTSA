package puntograno.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.UUID;
import puntograno.conexion.ConexionBD;
import puntograno.modelos.Producto;

public class ProductoDAO {

    public boolean registrarProducto(Producto producto) throws SQLException {
        String sql = "INSERT INTO producto (id_producto, nombre, categoria, precio, stock, disponible) VALUES (?, ?, ?, ?, ?, ?)";

        String nuevoId = UUID.randomUUID().toString();

        try (
                Connection conexion = ConexionBD.conectar();
                PreparedStatement consulta = conexion.prepareStatement(sql)) {

            consulta.setString(1, nuevoId);
            consulta.setString(2, producto.getNombre());
            consulta.setString(3, producto.getCategoria());
            consulta.setInt(4, producto.getPrecio());
            consulta.setInt(5, producto.getStock());
            consulta.setBoolean(6, producto.isDisponible());

            int filasInsertadas = consulta.executeUpdate();

            if (filasInsertadas == 1) {
                producto.setCodigo(UUID.fromString(nuevoId));
                return true;
            }
            return false;
        }
    }

    public ArrayList<Producto> listarProductos() throws SQLException {
        ArrayList<Producto> lista = new ArrayList<>();
        String sql = "SELECT id_producto, nombre, categoria, precio, stock, disponible FROM producto ORDER BY nombre ASC";

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
                        resultado.getBoolean("disponible")
                );
                producto.setCodigo(UUID.fromString(resultado.getString("id_producto")));

                lista.add(producto);
            }
        }
        return lista;
    }

    public boolean actualizarProducto(Producto producto) throws SQLException {
        String sql = "UPDATE producto SET nombre = ?, categoria = ?, precio = ?, stock = ?, disponible = ? WHERE id_producto = ?";

        try (
                Connection conexion = ConexionBD.conectar();
                PreparedStatement consulta = conexion.prepareStatement(sql)) {

            consulta.setString(1, producto.getNombre());
            consulta.setString(2, producto.getCategoria());
            consulta.setInt(3, producto.getPrecio());
            consulta.setInt(4, producto.getStock());
            consulta.setBoolean(5, producto.isDisponible());
            consulta.setString(6, producto.getCodigo().toString());

            int filasActualizadas = consulta.executeUpdate();
            return filasActualizadas == 1;
        }
    }

    public boolean eliminarProducto(String idProducto) throws SQLException {
        String sql = "DELETE FROM producto WHERE id_producto = ?";

        try (
                Connection conexion = ConexionBD.conectar();
                PreparedStatement consulta = conexion.prepareStatement(sql)) {

            consulta.setString(1, idProducto);

            int filasEliminadas = consulta.executeUpdate();
            return filasEliminadas == 1;
        }
    }
}
