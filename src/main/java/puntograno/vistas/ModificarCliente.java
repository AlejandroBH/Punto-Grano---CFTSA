package puntograno.vistas;

import puntograno.controladores.ClienteControlador;
import javax.swing.*;
import puntograno.modelos.Cliente;

public class ModificarCliente extends JFrame {

    private ClienteControlador clienteControlador;

    private JPanel panel;
    private JLabel lblNombre;
    private JTextField txtNombre;
    private JLabel lblTelefono;
    private JTextField txtTelefono;
    private JButton btnModificar;

    public ModificarCliente(ClienteControlador clienteControlador, Cliente cliente) {
        this.clienteControlador = clienteControlador;

        setTitle("Modificar Cliente");
        setSize(500, 320);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);

        panel = new JPanel();
        panel.setLayout(null);
        lblNombre = new JLabel("Nombre");
        txtNombre = new JTextField();
        lblTelefono = new JLabel("Telefono");
        txtTelefono = new JTextField();
        btnModificar = new JButton("Modificar");

        lblNombre.setBounds(70, 70, 80, 30);
        txtNombre.setBounds(150, 70, 230, 30);
        lblTelefono.setBounds(70, 120, 80, 30);
        txtTelefono.setBounds(150, 120, 230, 30);
        btnModificar.setBounds(150, 170, 230, 30);

        // Cargamos datos originales de cliente seleccionado
        txtNombre.setText(cliente.getNombre());
        txtTelefono.setText(cliente.getTelefono());

        btnModificar.addActionListener(e -> {
            String nombre = txtNombre.getText();
            String telefono = txtTelefono.getText();

            if (nombre.isEmpty() || telefono.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Debes completar todos los campos", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            clienteControlador.editarCliente(cliente, nombre, telefono);
            JOptionPane.showMessageDialog(this, "Cliente " + nombre + " modificado correctamente");
            setVisible(false);
        });

        panel.add(lblNombre);
        panel.add(txtNombre);
        panel.add(lblTelefono);
        panel.add(txtTelefono);
        panel.add(btnModificar);
        add(panel);
    }
}
