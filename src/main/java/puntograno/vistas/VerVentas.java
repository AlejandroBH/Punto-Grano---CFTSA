package puntograno.vistas;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
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

    private void cargarVentas() {
        modeloTabla.setRowCount(0);

        for (Pedido pedido : pedidoControlador.getPedidos()) {
            modeloTabla.addRow(new Object[]{
                pedido.getCliente().getNombre(),
                pedido.getEstado(),
                pedido.mostrarProductos().size(),
                "$ " + pedido.calcularTotal()
            });
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
        btnProcesarPedido.setBackground(new Color(240, 235, 230));
        btnProcesarPedido.setForeground(new Color(70, 45, 30));
        btnProcesarPedido.setFont(new Font("Arial", Font.BOLD, 14));
        btnProcesarPedido.setFocusPainted(false);
        btnProcesarPedido.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnProcesarPedido.setBorder(BorderFactory.createLineBorder(new Color(180, 160, 140)));
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

            pedidoControlador.procesarPedido(pedidoSeleccionado);
            JOptionPane.showMessageDialog(this, "Pedido listo para entregarse");
            setVisible(false);
        });

        btnEntregarPedido = new JButton("Entregar Pedido");
        btnEntregarPedido.setBackground(new Color(240, 235, 230));
        btnEntregarPedido.setForeground(new Color(70, 45, 30));
        btnEntregarPedido.setFont(new Font("Arial", Font.BOLD, 14));
        btnEntregarPedido.setFocusPainted(false);
        btnEntregarPedido.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnEntregarPedido.setBorder(BorderFactory.createLineBorder(new Color(180, 160, 140)));
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

            pedidoControlador.finalizarPedido(pedidoSeleccionado);
            JOptionPane.showMessageDialog(this, "Pedido entregado");
            setVisible(false);
        });

        panel.add(lblTitulo);
        panel.add(scrollTabla);
        panel.add(btnProcesarPedido);
        panel.add(btnEntregarPedido);

        add(panel);
        cargarVentas();
    }
}
