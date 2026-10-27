package puntograno.controladores;

import java.util.ArrayList;
import puntograno.modelos.Producto;

public class ProductoControlador {

    private ArrayList<Producto> productos;

    public ProductoControlador() {
        productos = new ArrayList<>();
    }

    // solo para pruebas
    public void seedProductos() {
        Producto producto1 = new Producto("Café expreso", "Productos", 4500, true);
        Producto producto2 = new Producto("Muffin o Galleta", "Productos", 3200, true);
        Producto producto3 = new Producto("Café del día", "Productos", 3500, true);
        Producto producto4 = new Producto("Bolsa de Café en Grano", "Productos", 7500, true);
        Producto producto5 = new Producto("Capuccino grande", "Productos", 5700, true);
        Producto accesorio1 = new Producto("Bombilla Mate", "Accesorios", 3800, true);
        Producto accesorio2 = new Producto("Moledor de Café", "Accesorios", 4200, true);

        productos.add(producto1);
        productos.add(producto2);
        productos.add(producto3);
        productos.add(producto4);
        productos.add(producto5);
        productos.add(accesorio1);
        productos.add(accesorio2);
    }

    public void registrarProducto(Producto producto) {
        productos.add(producto);
    }

    public ArrayList<Producto> getProductos() {
        return productos;
    }

    public Producto obtenerProducto(int idProducto) {
        return productos.get(idProducto);
    }

    public void editarProducto(Producto producto, String nombre, int precio, String categoria, Boolean disponible) {
        producto.setNombre(nombre);
        producto.setPrecio(precio);
        producto.setCategoria(categoria);
        producto.setDisponible(disponible);
    }

    public void eliminarProducto(Producto producto) {
        this.productos.remove(producto);
    }
}
