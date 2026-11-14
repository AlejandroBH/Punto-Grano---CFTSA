package puntograno.controladores;

import java.sql.SQLException;
import java.util.ArrayList;
import puntograno.dao.ProductoDAO;
import puntograno.modelos.Producto;

public class ProductoControlador {

    private ArrayList<Producto> productos;
    private ProductoDAO productoDAO;

    public ProductoControlador() {
        productos = new ArrayList<>();
        productoDAO = new ProductoDAO();
    }

    // solo para pruebas
    public void seedProductos() {
        Producto producto1 = new Producto("Café expreso", "Productos", 4500, 10, true);
        Producto producto2 = new Producto("Muffin o Galleta", "Productos", 3200, 10, true);
        Producto producto3 = new Producto("Café del día", "Productos", 3500, 10, true);
        Producto producto4 = new Producto("Bolsa de Café en Grano", "Productos", 7500, 10, true);
        Producto producto5 = new Producto("Capuccino grande", "Productos", 5700, 10, true);
        Producto accesorio1 = new Producto("Bombilla Mate", "Accesorios", 3800, 10, true);
        Producto accesorio2 = new Producto("Moledor de Café", "Accesorios", 4200, 10, true);

        productos.add(producto1);
        productos.add(producto2);
        productos.add(producto3);
        productos.add(producto4);
        productos.add(producto5);
        productos.add(accesorio1);
        productos.add(accesorio2);
    }

    public boolean registrarProducto(Producto producto) throws SQLException {
        boolean registrado = productoDAO.registrarProducto(producto);

        if (registrado) {
            productos.add(producto);
        }
        return registrado;
    }

    public ArrayList<Producto> getProductos() {
        return productos;
    }

    public ArrayList<Producto> listarProductos() throws SQLException {
        this.productos = productoDAO.listarProductos();
        return this.productos;
    }

    public Producto obtenerProducto(int idProducto) {
        return productos.get(idProducto);
    }

    public boolean actualizarProducto(Producto producto) throws SQLException {
        return productoDAO.actualizarProducto(producto);
    }

    public void editarProducto(Producto producto, String nombre, int precio, int stock, String categoria, Boolean disponible) {
        producto.setNombre(nombre);
        producto.setPrecio(precio);
        producto.setStock(stock);
        producto.setCategoria(categoria);

        // Validacion para montos inferiores a cero, y actualiza valor a falso automaticamente si no hay stock
        if (stock <= 0) {
            producto.setStock(0);
            producto.setDisponible(false);
        } else {
            producto.setDisponible(disponible);
        }
    }

    public boolean eliminarProducto(Producto producto) throws SQLException {
        boolean eliminado = productoDAO.eliminarProducto(producto.getCodigo().toString());
        if (eliminado) {
            this.productos.remove(producto);
        }
        return eliminado;
    }
}
