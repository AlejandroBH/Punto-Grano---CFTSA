package modelos;

import java.util.ArrayList;
import java.util.UUID;

public class Pedido {

    private UUID idPedido;
    private String estado; // "En preparacion", "Listo para retiro", "Entregado"
    private Cliente cliente; // Cliente - Pedido 1:1 
    private ArrayList<Producto> listaProductos; // Producto - Pedido 1:N 

    public Pedido(Cliente cliente) {
        this.idPedido = UUID.randomUUID();
        this.estado = "En preparacion";
        this.cliente = cliente;
        this.listaProductos = new ArrayList<Producto>();
    }

    public UUID getIdPedido() {
        return idPedido;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        if ("En preparacion".equals(estado) || "Listo para retiro".equals(estado) || "Entregado".equals(estado)) {
            this.estado = estado;
        }
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    // Añade un ArrayList de productos a la lista.
    public void agregarProductos(ArrayList<Producto> productos) {
        this.listaProductos = productos;
    }

    // Mostrar lista de productos del cliente
    public ArrayList<Producto> mostrarProductos() {
        return listaProductos;
    }

    // Recorre el ArrayList de productos y suma sus precios para retornar el monto total del pedido.
    public int calcularTotal() {
        int totalPagar = 0;
        for (Producto producto : listaProductos) {
            totalPagar += producto.getPrecio();
        }
        return totalPagar;
    }

    // Cambia el estado a "Listo para retiro"
    // calcula los puntos ganados (por ejemplo, por compras superiores a $10.000 gana 50 puntos)
    // y llama al método actualizarProgreso() de la TarjetaFidelidad del cliente.
    public void finalizarPedidoYPremiar() {
        int totalCompra = calcularTotal();
        this.setEstado("Listo para retiro");
        if (totalCompra >= 10000) {
            this.cliente.getTarjeta().actualizarProgreso(50);
        }
    }
}
