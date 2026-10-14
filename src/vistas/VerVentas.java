package vistas;

import controladores.PedidoControlador;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import modelos.Pedido;
import modelos.Usuario;

public class VerVentas extends JInternalFrame {

    private PedidoControlador pedidoControlador;

    private JPanel panel;
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

        modeloTabla = new DefaultTableModel();
        modeloTabla.addColumn("Cliente");
        modeloTabla.addColumn("Estado");
        modeloTabla.addColumn("Cantidad");
        modeloTabla.addColumn("Total a pagar");
        tablaClientes = new JTable(modeloTabla);

        scrollTabla = new JScrollPane(tablaClientes);
        scrollTabla.setBounds(40, 40, 550, 250);

        btnProcesarPedido = new JButton("Procesar Pedido");
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

        panel.add(scrollTabla);
        panel.add(btnProcesarPedido);
        panel.add(btnEntregarPedido);

        add(panel);
        cargarVentas();
    }
}
