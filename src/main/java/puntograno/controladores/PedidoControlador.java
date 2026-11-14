package puntograno.controladores;

import java.sql.SQLException;
import java.util.ArrayList;
import puntograno.dao.PedidoDAO;
import puntograno.modelos.Pedido;
import puntograno.modelos.Producto;

public class PedidoControlador {

    private ArrayList<Producto> carrito;
    private ArrayList<Pedido> pedidos;
    private PedidoDAO pedidoDAO;

    public PedidoControlador() {
        carrito = new ArrayList<>();
        pedidos = new ArrayList<>();
        pedidoDAO = new PedidoDAO();
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

    public boolean registrarPedido(Pedido pedido) throws SQLException {
        boolean registrado = pedidoDAO.registrarPedido(pedido);

        if (registrado) {
            pedidos.add(pedido);
        }
        return registrado;
    }

    public ArrayList<Pedido> getPedidos() {
        return pedidos;
    }

    public ArrayList<Pedido> listarPedidos() throws SQLException {
        this.pedidos = pedidoDAO.listarPedidos();
        return this.pedidos;
    }

    public Pedido obtenerPedido(int idPedido) {
        return pedidos.get(idPedido);
    }

    public boolean procesarPedido(Pedido pedido) throws SQLException {
        pedido.finalizarPedidoYPremiar();
        return pedidoDAO.actualizarEstadoPedido(pedido);
    }

    public boolean finalizarPedido(Pedido pedido) throws SQLException {
        pedido.setEstado("Entregado");
        return pedidoDAO.actualizarEstadoPedido(pedido);
    }

    public boolean eliminarPedido(Pedido pedido) throws SQLException {
        boolean eliminado = pedidoDAO.eliminarPedido(pedido.getIdPedido().toString());

        if (eliminado) {
            this.pedidos.remove(pedido);
        }
        return eliminado;
    }
}
