package vistas;

import controladores.ClienteControlador;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import modelos.Cliente;
import modelos.Usuario;

public class VerClientes extends JInternalFrame {

    private ClienteControlador clienteControlador;

    private JPanel panel;
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

        modeloTabla = new DefaultTableModel();
        modeloTabla.addColumn("Nombre");
        modeloTabla.addColumn("RUT");
        modeloTabla.addColumn("Telefono");
        modeloTabla.addColumn("Nivel");
        modeloTabla.addColumn("Puntos");
        tablaClientes = new JTable(modeloTabla);

        scrollTabla = new JScrollPane(tablaClientes);
        scrollTabla.setBounds(40, 40, 550, 250);

        btnModificarCliente = new JButton("Modificar Cliente");
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
