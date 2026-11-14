package puntograno.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.UUID;
import puntograno.conexion.ConexionBD;
import puntograno.modelos.Cliente;
import puntograno.modelos.Pedido;
import puntograno.modelos.Producto;

public class PedidoDAO {

    public boolean registrarPedido(Pedido pedido) throws SQLException {
        String sqlPedido = "INSERT INTO pedido (id_pedido, fecha_hora, estado, id_cliente) VALUES (?, ?, ?, ?)";
        String sqlDetalle = "INSERT INTO detalle_pedido (id_pedido, id_producto, cantidad, precio_unitario) VALUES (?, ?, ?, ?)";
        String sqlStock = "UPDATE producto SET stock = stock - ? WHERE id_producto = ?";

        String nuevoIdPedido = UUID.randomUUID().toString();

        try (Connection conexion = ConexionBD.conectar()) {
            conexion.setAutoCommit(false); // Iniciamos transacción controlada

            try (
                    PreparedStatement consultaPedido = conexion.prepareStatement(sqlPedido);
                    PreparedStatement consultaDetalle = conexion.prepareStatement(sqlDetalle);
                    PreparedStatement consultaStock = conexion.prepareStatement(sqlStock)) {

                // 1. Insertamos el pedido
                consultaPedido.setString(1, nuevoIdPedido);
                consultaPedido.setDate(2, new Date(pedido.getFechaPedido().getTime()));
                consultaPedido.setString(3, pedido.getEstado());
                consultaPedido.setString(4, pedido.getCliente().getIdUsuario());
                int filasPedido = consultaPedido.executeUpdate();

                // 2. Insertamos los productos del pedido y descontamos stock
                ArrayList<String> idsRegistrados = new ArrayList<>();

                for (Producto producto : pedido.mostrarProductos()) {
                    String idProducto = producto.getCodigo().toString();

                    // Fix error de duplicidad DB
                    if (idsRegistrados.contains(idProducto)) {
                        continue;
                    }

                    // Contamos cuántas veces se compró este producto en el pedido
                    int cantidad = 0;
                    for (Producto p : pedido.mostrarProductos()) {
                        if (p.getCodigo().toString().equals(idProducto)) {
                            cantidad++;
                        }
                    }

                    // Guardamos el detalle del pedido
                    consultaDetalle.setString(1, nuevoIdPedido);
                    consultaDetalle.setString(2, idProducto);
                    consultaDetalle.setInt(3, cantidad);
                    consultaDetalle.setInt(4, producto.getPrecio());
                    consultaDetalle.executeUpdate();

                    // Descontamos del stock en la base de datos
                    consultaStock.setInt(1, cantidad);
                    consultaStock.setString(2, idProducto);
                    consultaStock.executeUpdate();

                    idsRegistrados.add(idProducto); // Los productos repetidos se agregan solo una vez con su determinada cantidad
                }

                if (filasPedido == 1) {
                    conexion.commit(); // Confirmamos transacción
                    pedido.setIdPedido(UUID.fromString(nuevoIdPedido));
                    return true;
                } else {
                    conexion.rollback();
                    return false;
                }
            } catch (SQLException ex) {
                conexion.rollback(); // Revertimos ante cualquier error
                throw ex;
            }
        }
    }

    public ArrayList<Pedido> listarPedidos() throws SQLException {
        ArrayList<Pedido> lista = new ArrayList<>();
        String sql = "SELECT pedido.id_pedido, pedido.fecha_hora, pedido.estado, "
                + "cliente.id_cliente, cliente.rut_cliente, cliente.nombre, cliente.telefono, cliente.email, "
                + "tarjeta_fidelidad.puntos_acumulados "
                + "FROM pedido "
                + "INNER JOIN cliente ON pedido.id_cliente = cliente.id_cliente "
                + "INNER JOIN tarjeta_fidelidad ON tarjeta_fidelidad.id_cliente = cliente.id_cliente "
                + "ORDER BY pedido.fecha_hora DESC";

        try (
                Connection conexion = ConexionBD.conectar();
                PreparedStatement consulta = conexion.prepareStatement(sql);
                ResultSet resultado = consulta.executeQuery()) {

            while (resultado.next()) {
                Cliente cliente = new Cliente(
                        resultado.getString("rut_cliente"),
                        resultado.getString("nombre"),
                        resultado.getString("telefono"),
                        resultado.getString("email")
                );
                cliente.setIdUsuario(resultado.getString("id_cliente"));

                int puntos = resultado.getInt("puntos_acumulados");
                cliente.getTarjeta().actualizarProgreso(puntos);

                Pedido pedido = new Pedido(cliente);
                pedido.setIdPedido(UUID.fromString(resultado.getString("id_pedido")));
                pedido.setFechaPedido(resultado.getDate("fecha_hora"));
                pedido.setEstado(resultado.getString("estado"));

                // Cargamos los productos del pedido desde detalle_pedido
                cargarDetallesPedido(conexion, pedido);

                lista.add(pedido);
            }
        }
        return lista;
    }

    private void cargarDetallesPedido(Connection conexion, Pedido pedido) throws SQLException {
        String sql = "SELECT detalle_pedido.cantidad, detalle_pedido.precio_unitario, "
                + "producto.id_producto, producto.nombre, producto.categoria, producto.stock, producto.disponible "
                + "FROM detalle_pedido "
                + "INNER JOIN producto ON detalle_pedido.id_producto = producto.id_producto "
                + "WHERE detalle_pedido.id_pedido = ?";

        try (PreparedStatement consulta = conexion.prepareStatement(sql)) {
            consulta.setString(1, pedido.getIdPedido().toString());

            try (ResultSet resultado = consulta.executeQuery()) {
                while (resultado.next()) {
                    int cantidad = resultado.getInt("cantidad");

                    for (int i = 0; i < cantidad; i++) {
                        Producto producto = new Producto(
                                resultado.getString("nombre"),
                                resultado.getString("categoria"),
                                resultado.getInt("precio_unitario"),
                                resultado.getInt("stock"),
                                resultado.getBoolean("disponible")
                        );
                        producto.setCodigo(UUID.fromString(resultado.getString("id_producto")));
                        pedido.mostrarProductos().add(producto);
                    }
                }
            }
        }
    }

    public boolean actualizarEstadoPedido(Pedido pedido) throws SQLException {
        String sql = "UPDATE pedido SET estado = ? WHERE id_pedido = ?";

        try (
                Connection conexion = ConexionBD.conectar();
                PreparedStatement consulta = conexion.prepareStatement(sql)) {

            consulta.setString(1, pedido.getEstado());
            consulta.setString(2, pedido.getIdPedido().toString());

            int filasActualizadas = consulta.executeUpdate();
            return filasActualizadas == 1;
        }
    }

    public boolean eliminarPedido(String idPedido) throws SQLException {
        String sqlDetalle = "DELETE FROM detalle_pedido WHERE id_pedido = ?";
        String sqlPedido = "DELETE FROM pedido WHERE id_pedido = ?";

        try (Connection conexion = ConexionBD.conectar()) {
            conexion.setAutoCommit(false); // Transacción para eliminar detalles y pedido

            try (
                    PreparedStatement eliminarDetalle = conexion.prepareStatement(sqlDetalle);
                    PreparedStatement eliminarPedido = conexion.prepareStatement(sqlPedido)) {

                // 1. Eliminamos primero los detalles del pedido
                eliminarDetalle.setString(1, idPedido);
                eliminarDetalle.executeUpdate();

                // 2. Eliminamos el pedido
                eliminarPedido.setString(1, idPedido);
                int filasEliminadas = eliminarPedido.executeUpdate();

                if (filasEliminadas == 1) {
                    conexion.commit();
                    return true;
                } else {
                    conexion.rollback();
                    return false;
                }
            } catch (SQLException ex) {
                conexion.rollback();
                throw ex;
            }
        }
    }
}
