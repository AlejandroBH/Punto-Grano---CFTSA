package modelos;

import java.util.UUID;

public class Producto {

    private UUID codigo;
    private String nombre;
    private String categoria; // "Productos", "Accesorios"
    private int precio;
    private boolean disponible;

    public Producto(String nombre, String categoria, int precio, boolean disponible) {
        this.codigo = UUID.randomUUID();
        this.nombre = nombre;
        this.categoria = categoria;
        this.precio = precio;
        this.disponible = disponible;
    }

    public UUID getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public int getPrecio() {
        return precio;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    public void mostrarDetalleProducto() {
        System.out.println("Mostrando detalles del pedido");
    }
}
