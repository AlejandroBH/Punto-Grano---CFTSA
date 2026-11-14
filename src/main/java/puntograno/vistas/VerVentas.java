package puntograno.vistas;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.sql.SQLException;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import puntograno.controladores.PedidoControlador;
import puntograno.modelos.Pedido;
import puntograno.modelos.Usuario;

public class VerVentas extends JInternalFrame {

    private PedidoControlador pedidoControlador;

    private JPanel panel;
    private JLabel lblTitulo;
    private JTable tablaClientes;
    private DefaultTableModel modeloTabla;
    private JScrollPane scrollTabla;
    private JButton btnProcesarPedido;
    private JButton btnEntregarPedido;

    public void cargarVentas() {
        modeloTabla.setRowCount(0);

        try {
            for (Pedido pedido : pedidoControlador.listarPedidos()) {
                modeloTabla.addRow(new Object[]{
                    pedido.getCliente().getNombre(),
                    pedido.getEstado(),
                    pedido.mostrarProductos().size(),
                    "$ " + pedido.calcularTotal()
                });
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error al cargar las ventas desde la base de datos: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public VerVentas(Usuario usuarioLogeado, PedidoControlador pedidoControlador) {
        this.pedidoControlador = pedidoControlador;

        setTitle("Ventas Registradas");
        setSize(650, 450);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);

        panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(new Color(245, 240, 235));

        lblTitulo = new JLabel("Ventas Registradas");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        lblTitulo.setForeground(new Color(70, 45, 30));
        lblTitulo.setBounds(40, 10, 550, 30);

        modeloTabla = new DefaultTableModel();
        modeloTabla.addColumn("Cliente");
        modeloTabla.addColumn("Estado");
        modeloTabla.addColumn("Cantidad");
        modeloTabla.addColumn("Total a pagar");
        tablaClientes = new JTable(modeloTabla);

        tablaClientes.setRowHeight(28);
        tablaClientes.setFont(new Font("Arial", Font.PLAIN, 13));
        tablaClientes.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));
        tablaClientes.getTableHeader().setForeground(new Color(70, 45, 30));
        tablaClientes.getTableHeader().setReorderingAllowed(false);
        tablaClientes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tablaClientes.setSelectionBackground(new Color(230, 215, 195));
        tablaClientes.setSelectionForeground(Color.BLACK);

        scrollTabla = new JScrollPane(tablaClientes);
        scrollTabla.getViewport().setBackground(Color.WHITE);
        scrollTabla.setBorder(BorderFactory.createLineBorder(new Color(180, 160, 140)));
        scrollTabla.setBounds(40, 45, 550, 255);

        btnProcesarPedido = new JButton("Procesar Pedido");
        btnProcesarPedido.setUI(new javax.swing.plaf.basic.BasicButtonUI());
        btnProcesarPedido.setBackground(new Color(70, 45, 30));
        btnProcesarPedido.setForeground(Color.WHITE);
        btnProcesarPedido.setOpaque(true);
        btnProcesarPedido.setFont(new Font("Arial", Font.BOLD, 14));
        btnProcesarPedido.setFocusPainted(false);
        btnProcesarPedido.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnProcesarPedido.setBorder(BorderFactory.createLineBorder(new Color(70, 45, 30)));
        btnProcesarPedido.setToolTipText("Procesar pedido en preparación");
        btnProcesarPedido.setBounds(40, 320, 200, 40);

        btnProcesarPedido.addActionListener(e -> {
            int filaSeleccionada = tablaClientes.getSelectedRow();

            if (filaSeleccionada == -1) {
                JOptionPane.showMessageDialog(this, "Debes seleccionar una fila", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Pedido pedidoSeleccionado = pedidoControlador.obtenerPedido(filaSeleccionada);

            if (!"En preparacion".equals(pedidoSeleccionado.getEstado())) {
                JOptionPane.showMessageDialog(this, "No se puede realizar la operacion", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                int total = pedidoSeleccionado.calcularTotal();
                int puntosGanados = (total >= 10000) ? (total / 10000) * 50 : 0;

                pedidoControlador.procesarPedido(pedidoSeleccionado);

                if (puntosGanados > 0) {
                    JOptionPane.showMessageDialog(this, "Pedido procesado (Listo para retiro).\n¡Se han sumado " + puntosGanados + " puntos al cliente " + pedidoSeleccionado.getCliente().getNombre() + "!");
                } else {
                    JOptionPane.showMessageDialog(this, "Pedido procesado (Listo para retiro).\n(Compra menor a $10.000, no acumula puntos)");
                }

                cargarVentas();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Error al procesar el pedido en la base de datos", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnEntregarPedido = new JButton("Entregar Pedido");
        btnEntregarPedido.setUI(new javax.swing.plaf.basic.BasicButtonUI());
        btnEntregarPedido.setBackground(new Color(70, 45, 30));
        btnEntregarPedido.setForeground(Color.WHITE);
        btnEntregarPedido.setOpaque(true);
        btnEntregarPedido.setFont(new Font("Arial", Font.BOLD, 14));
        btnEntregarPedido.setFocusPainted(false);
        btnEntregarPedido.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnEntregarPedido.setBorder(BorderFactory.createLineBorder(new Color(70, 45, 30)));
        btnEntregarPedido.setToolTipText("Entregar el pedido al cliente");
        btnEntregarPedido.setBounds(270, 320, 200, 40);

        btnEntregarPedido.addActionListener(e -> {
            int filaSeleccionada = tablaClientes.getSelectedRow();

            if (filaSeleccionada == -1) {
                JOptionPane.showMessageDialog(this, "Debes seleccionar una fila", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Pedido pedidoSeleccionado = pedidoControlador.obtenerPedido(filaSeleccionada);

            if (!"Listo para retiro".equals(pedidoSeleccionado.getEstado())) {
                JOptionPane.showMessageDialog(this, "No se puede realizar la operacion", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                pedidoControlador.finalizarPedido(pedidoSeleccionado);
                JOptionPane.showMessageDialog(this, "Pedido entregado");
                cargarVentas();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Error al entregar el pedido en la base de datos", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        panel.add(lblTitulo);
        panel.add(scrollTabla);
        panel.add(btnProcesarPedido);
        panel.add(btnEntregarPedido);

        add(panel);
        cargarVentas();
    }
}
