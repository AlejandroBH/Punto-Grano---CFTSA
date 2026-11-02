package puntograno.vistas;

import puntograno.controladores.ClienteControlador;
import puntograno.controladores.PedidoControlador;
import puntograno.controladores.ProductoControlador;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import puntograno.modelos.Cliente;
import puntograno.modelos.Pedido;
import puntograno.modelos.Producto;

public class NuevaVenta extends JFrame {

    private PedidoControlador carritoControlador;
    private PedidoControlador pedidoControlador;
    private ProductoControlador productoControlador;
    private ClienteControlador clienteControlador;

    private JPanel panel;
    private JLabel lblCliente;
    private JComboBox comCliente;
    private JLabel lblProducto;
    private JComboBox comProducto;
    private JButton btnAgregar;
    private JTable tablaProductos;
    private DefaultTableModel modeloTabla;
    private JScrollPane scrollTabla;
    private JLabel lblTotal;
    private JLabel txtTotal;
    private JButton btnRegistrar;

    private void cargarProductos() {
        for (Producto producto : productoControlador.getProductos()) {
            if (producto.isDisponible()) {
                comProducto.addItem(producto.getNombre());
            }
        }
    }

    private void cargarClientes() {
        for (Cliente cliente : clienteControlador.getClientes()) {
            comCliente.addItem(cliente.getNombre() + " (" + cliente.getRut() + ")");
        }
    }

    private void actualizarCarrito() {
        modeloTabla.setRowCount(0);

        for (Producto producto : carritoControlador.getCarrito()) {
            modeloTabla.addRow(new Object[]{
                producto.getNombre(),
                "$ " + producto.getPrecio()
            });
        }
    }

    public NuevaVenta(PedidoControlador pedidoControlador, ProductoControlador productoControlador, ClienteControlador clienteControlador) {
        this.carritoControlador = new PedidoControlador();
        this.pedidoControlador = pedidoControlador;
        this.productoControlador = productoControlador;
        this.clienteControlador = clienteControlador;

        setTitle("Registrar Nueva Venta");
        setSize(580, 520);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);

        panel = new JPanel();
        panel.setLayout(null);
        lblCliente = new JLabel("Cliente");
        comCliente = new JComboBox<>();
        cargarClientes();
        lblProducto = new JLabel("Producto");
        comProducto = new JComboBox<>();
        cargarProductos();
        btnAgregar = new JButton("Agregar");
        modeloTabla = new DefaultTableModel();
        modeloTabla.addColumn("Producto");
        modeloTabla.addColumn("Precio");
        tablaProductos = new JTable(modeloTabla);
        scrollTabla = new JScrollPane(tablaProductos);
        lblTotal = new JLabel("Total");
        txtTotal = new JLabel("$ 0");
        btnRegistrar = new JButton("Registrar Venta");

        btnAgregar.addActionListener(e -> {
            int indice = comProducto.getSelectedIndex();

            if (indice == -1 || comProducto.getSelectedItem() == null) {
                JOptionPane.showMessageDialog(this, "No existen productos en el catalogo", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Producto productoSeleccionado = productoControlador.obtenerProducto(indice);
            carritoControlador.agregarCarrito(productoSeleccionado);
            actualizarCarrito();

            int totalCalculado = 0;
            for (int i = 0; i < carritoControlador.getCarrito().size(); i++) {
                Producto producto = carritoControlador.getCarrito().get(i);
                totalCalculado = totalCalculado + producto.getPrecio();
            }

            txtTotal.setText(String.valueOf("$ " + totalCalculado));
        });

        btnRegistrar.addActionListener(e -> {
            int clienteIndice = comCliente.getSelectedIndex();
            Cliente clienteEncontrado = clienteControlador.obtenerCliente(clienteIndice);

            if (clienteIndice == -1 || comCliente.getSelectedItem() == null) {
                JOptionPane.showMessageDialog(this, "No existen clientes registrados", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Pedido nuevoPedido = new Pedido(clienteEncontrado);
            pedidoControlador.agregarCarritoPedido(nuevoPedido, carritoControlador.getCarrito());
            pedidoControlador.registrarPedido(nuevoPedido);

            JOptionPane.showMessageDialog(this, "Pedido creado correctamente a cliente: " + nuevoPedido.getCliente().getNombre());
            setVisible(false);
        });

        lblCliente.setBounds(50, 40, 80, 30);
        comCliente.setBounds(140, 40, 200, 30);
        lblProducto.setBounds(50, 90, 80, 30);
        comProducto.setBounds(140, 90, 200, 30);
        btnAgregar.setBounds(360, 90, 150, 30);
        scrollTabla.setBounds(50, 140, 460, 200);
        lblTotal.setBounds(50, 360, 80, 30);
        txtTotal.setBounds(140, 360, 200, 30);
        btnRegistrar.setBounds(140, 415, 200, 35);

        panel.add(lblCliente);
        panel.add(comCliente);
        panel.add(lblProducto);
        panel.add(comProducto);
        panel.add(btnAgregar);
        panel.add(scrollTabla);
        panel.add(lblTotal);
        panel.add(txtTotal);
        panel.add(btnRegistrar);
        add(panel);
    }
}
