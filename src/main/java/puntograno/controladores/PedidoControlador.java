package puntograno.controladores;

import java.util.ArrayList;
import puntograno.modelos.Pedido;
import puntograno.modelos.Producto;

public class PedidoControlador {

    private ArrayList<Producto> carrito;
    private ArrayList<Pedido> pedidos;

    public PedidoControlador() {
        carrito = new ArrayList<>();
        pedidos = new ArrayList<>();
    }

    public void agregarCarrito(Producto producto) {
        carrito.add(producto);
    }

    public ArrayList<Producto> getCarrito() {
        return carrito;
    }

    public void agregarCarritoPedido(Pedido pedido, ArrayList<Producto> productos) {
        pedido.agregarProductos(productos);
    }

    public void registrarPedido(Pedido pedido) {
        pedidos.add(pedido);
    }

    public ArrayList<Pedido> getPedidos() {
        return pedidos;
    }

    public Pedido obtenerPedido(int idPedido) {
        return pedidos.get(idPedido);
    }

    public void procesarPedido(Pedido pedido) {
        pedido.finalizarPedidoYPremiar();
    }

    public void finalizarPedido(Pedido pedido) {
        pedido.setEstado("Entregado");
    }
}
