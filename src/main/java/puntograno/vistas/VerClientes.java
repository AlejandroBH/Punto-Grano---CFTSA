package puntograno.vistas;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import puntograno.controladores.ClienteControlador;
import puntograno.modelos.Cliente;
import puntograno.modelos.Usuario;

public class VerClientes extends JInternalFrame {

    private ClienteControlador clienteControlador;

    private JPanel panel;
    private JLabel lblTitulo;
    private JTable tablaClientes;
    private DefaultTableModel modeloTabla;
    private JScrollPane scrollTabla;
    private JButton btnModificarCliente;
    private JButton btnEliminarCliente;

    private void cargarClientes() {
        modeloTabla.setRowCount(0);

        for (Cliente cliente : clienteControlador.getClientes()) {
            modeloTabla.addRow(new Object[]{
                cliente.getNombre(),
                cliente.getRut(),
                cliente.getTelefono(),
                cliente.getEmail(),
                cliente.getTarjeta().getNivel(),
                cliente.getTarjeta().getPuntosAcumulados()
            });
        }
    }

    public VerClientes(Usuario usuarioLogeado, ClienteControlador clienteControlador) {
        this.clienteControlador = clienteControlador;

        setTitle("Clientes Registrados");
        setSize(650, 450);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);

        panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(new Color(245, 240, 235));

        lblTitulo = new JLabel("Clientes Registrados");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        lblTitulo.setForeground(new Color(70, 45, 30));
        lblTitulo.setBounds(40, 10, 550, 30);

        modeloTabla = new DefaultTableModel();
        modeloTabla.addColumn("Nombre");
        modeloTabla.addColumn("RUT");
        modeloTabla.addColumn("Telefono");
        modeloTabla.addColumn("Email");
        modeloTabla.addColumn("Nivel");
        modeloTabla.addColumn("Puntos");
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

        btnModificarCliente = new JButton("Modificar Cliente");
        btnModificarCliente.setBackground(new Color(240, 235, 230));
        btnModificarCliente.setForeground(new Color(70, 45, 30));
        btnModificarCliente.setFont(new Font("Arial", Font.BOLD, 14));
        btnModificarCliente.setFocusPainted(false);
        btnModificarCliente.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnModificarCliente.setBorder(BorderFactory.createLineBorder(new Color(180, 160, 140)));
        btnModificarCliente.setToolTipText("Modificar el cliente seleccionado");
        btnModificarCliente.setBounds(40, 320, 200, 40);

        btnModificarCliente.addActionListener(e -> {
            int filaSeleccionada = tablaClientes.getSelectedRow();

            if (filaSeleccionada == -1) {
                JOptionPane.showMessageDialog(this, "Debes seleccionar una fila", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Cliente clienteSeleccionado = clienteControlador.obtenerCliente(filaSeleccionada);

            ModificarCliente modificarCliente = new ModificarCliente(clienteControlador, clienteSeleccionado);
            modificarCliente.setVisible(true);
        });

        btnEliminarCliente = new JButton("Eliminar Cliente");
        btnEliminarCliente.setBackground(new Color(250, 240, 240));
        btnEliminarCliente.setForeground(new Color(160, 50, 40));
        btnEliminarCliente.setFont(new Font("Arial", Font.BOLD, 14));
        btnEliminarCliente.setFocusPainted(false);
        btnEliminarCliente.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnEliminarCliente.setBorder(BorderFactory.createLineBorder(new Color(200, 140, 130)));
        btnEliminarCliente.setToolTipText("Eliminar el cliente seleccionado");
        btnEliminarCliente.setBounds(270, 320, 200, 40);

        btnEliminarCliente.addActionListener(e -> {
            int filaSeleccionada = tablaClientes.getSelectedRow();

            if (filaSeleccionada == -1) {
                JOptionPane.showMessageDialog(this, "Debes seleccionar una fila", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Cliente clienteSeleccionado = clienteControlador.obtenerCliente(filaSeleccionada);
            clienteControlador.eliminarCliente(clienteSeleccionado);
            JOptionPane.showMessageDialog(this, "Cliente " + clienteSeleccionado.getNombre() + " eliminado correctamente");
            setVisible(false);
        });

        panel.add(lblTitulo);
        panel.add(scrollTabla);
        panel.add(btnModificarCliente);

        // Solo administradores pueden eliminar clientes
        if (usuarioLogeado.puedeGestionarCatalogo()) {
            panel.add(btnEliminarCliente);
        }

        add(panel);
        cargarClientes();
    }
}
