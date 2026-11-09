package puntograno.vistas;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import puntograno.controladores.ClienteControlador;
import puntograno.controladores.PedidoControlador;
import puntograno.controladores.ProductoControlador;
import puntograno.modelos.Cliente;
import puntograno.modelos.Pedido;
import puntograno.modelos.Producto;

public class NuevaVenta extends JFrame {

    private PedidoControlador carritoControlador;
    private PedidoControlador pedidoControlador;
    private ProductoControlador productoControlador;
    private ClienteControlador clienteControlador;

    private ArrayList<Producto> listaProductos;

    private JPanel panel;
    private JLabel lblTitulo;
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
        listaProductos = new ArrayList<>();
        comProducto.removeAllItems();

        for (Producto producto : productoControlador.getProductos()) {
            if (producto.isDisponible() && producto.getStock() > 0) {
                listaProductos.add(producto);
                comProducto.addItem(producto.getNombre() + " (Stock: " + producto.getStock() + ")");
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
        panel.setBackground(new Color(245, 240, 235));

        lblTitulo = new JLabel("Registrar Nueva Venta");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        lblTitulo.setForeground(new Color(70, 45, 30));
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);

        lblCliente = new JLabel("Cliente");
        lblCliente.setFont(new Font("Arial", Font.BOLD, 13));
        lblCliente.setForeground(new Color(70, 45, 30));

        comCliente = new JComboBox<>();
        comCliente.setFont(new Font("Arial", Font.PLAIN, 14));
        comCliente.setBackground(Color.WHITE);
        cargarClientes();

        lblProducto = new JLabel("Producto");
        lblProducto.setFont(new Font("Arial", Font.BOLD, 13));
        lblProducto.setForeground(new Color(70, 45, 30));

        comProducto = new JComboBox<>();
        comProducto.setFont(new Font("Arial", Font.PLAIN, 14));
        comProducto.setBackground(Color.WHITE);
        cargarProductos();

        btnAgregar = new JButton("Agregar");
        btnAgregar.setBackground(new Color(240, 235, 230));
        btnAgregar.setForeground(new Color(70, 45, 30));
        btnAgregar.setFont(new Font("Arial", Font.BOLD, 13));
        btnAgregar.setFocusPainted(false);
        btnAgregar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnAgregar.setBorder(BorderFactory.createLineBorder(new Color(180, 160, 140)));
        btnAgregar.setToolTipText("Agregar producto al carrito");

        modeloTabla = new DefaultTableModel();
        modeloTabla.addColumn("Producto");
        modeloTabla.addColumn("Precio");
        tablaProductos = new JTable(modeloTabla);

        tablaProductos.setRowHeight(28);
        tablaProductos.setFont(new Font("Arial", Font.PLAIN, 13));
        tablaProductos.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));
        tablaProductos.getTableHeader().setForeground(new Color(70, 45, 30));
        tablaProductos.getTableHeader().setReorderingAllowed(false);
        tablaProductos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tablaProductos.setSelectionBackground(new Color(230, 215, 195));
        tablaProductos.setSelectionForeground(Color.BLACK);

        scrollTabla = new JScrollPane(tablaProductos);
        scrollTabla.getViewport().setBackground(Color.WHITE);
        scrollTabla.setBorder(BorderFactory.createLineBorder(new Color(180, 160, 140)));

        lblTotal = new JLabel("Total");
        lblTotal.setFont(new Font("Arial", Font.BOLD, 14));
        lblTotal.setForeground(new Color(70, 45, 30));

        txtTotal = new JLabel("$ 0");
        txtTotal.setFont(new Font("Arial", Font.BOLD, 16));
        txtTotal.setForeground(new Color(111, 78, 55));

        btnRegistrar = new JButton("Registrar Venta");
        btnRegistrar.setBackground(new Color(240, 235, 230));
        btnRegistrar.setForeground(new Color(70, 45, 30));
        btnRegistrar.setFont(new Font("Arial", Font.BOLD, 14));
        btnRegistrar.setFocusPainted(false);
        btnRegistrar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnRegistrar.setBorder(BorderFactory.createLineBorder(new Color(180, 160, 140)));
        btnRegistrar.setToolTipText("Registrar y completar venta");

        btnAgregar.addActionListener(e -> {
            int indice = comProducto.getSelectedIndex();

            if (indice == -1 || comProducto.getSelectedItem() == null) {
                JOptionPane.showMessageDialog(this, "No existen productos en el catalogo", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Producto productoSeleccionado = listaProductos.get(indice);

            if (productoSeleccionado.getStock() <= 0) {
                JOptionPane.showMessageDialog(this, "No hay stock disponible para este producto", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int cantidadEnCarrito = 0;

            for (Producto producto : carritoControlador.getCarrito()) {
                if (producto.getCodigo().equals(productoSeleccionado.getCodigo())) {
                    cantidadEnCarrito++;
                }
            }

            if (cantidadEnCarrito >= productoSeleccionado.getStock()) {
                JOptionPane.showMessageDialog(this, "No hay suficiente stock disponible para este producto", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

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

            if (clienteIndice == -1 || comCliente.getSelectedItem() == null) {
                JOptionPane.showMessageDialog(this, "No existen clientes registrados", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (carritoControlador.getCarrito().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Debes agregar al menos un producto a la venta", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Cliente clienteEncontrado = clienteControlador.obtenerCliente(clienteIndice);

            // Descontar stock de los productos vendidos
            for (Producto producto : carritoControlador.getCarrito()) {
                producto.setStock(producto.getStock() - 1);
                if (producto.getStock() <= 0) {
                    producto.setStock(0);
                    producto.setDisponible(false);
                }
            }

            Pedido nuevoPedido = new Pedido(clienteEncontrado);
            pedidoControlador.agregarCarritoPedido(nuevoPedido, carritoControlador.getCarrito());
            pedidoControlador.registrarPedido(nuevoPedido);

            JOptionPane.showMessageDialog(this, "Pedido creado correctamente a cliente: " + nuevoPedido.getCliente().getNombre());
            setVisible(false);
        });

        lblTitulo.setBounds(50, 10, 460, 25);
        lblCliente.setBounds(50, 40, 80, 30);
        comCliente.setBounds(140, 40, 200, 30);
        lblProducto.setBounds(50, 90, 80, 30);
        comProducto.setBounds(140, 90, 200, 30);
        btnAgregar.setBounds(360, 90, 150, 30);
        scrollTabla.setBounds(50, 140, 460, 200);
        lblTotal.setBounds(50, 360, 80, 30);
        txtTotal.setBounds(140, 360, 200, 30);
        btnRegistrar.setBounds(140, 415, 200, 35);

        panel.add(lblTitulo);
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
