package puntograno.vistas;

import puntograno.controladores.ClienteControlador;
import javax.swing.*;
import puntograno.modelos.Cliente;

public class NuevoCliente extends JFrame {

    private ClienteControlador clienteControlador;

    private JPanel panel;
    private JLabel lblNombre;
    private JTextField txtNombre;
    private JLabel lblRut;
    private JTextField txtRut;
    private JLabel lblTelefono;
    private JTextField txtTelefono;
    private JButton btnRegistrar;

    public NuevoCliente(ClienteControlador clienteControlador) {
        this.clienteControlador = clienteControlador;

        setTitle("Registrar Nuevo Cliente");
        setSize(500, 350);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);

        panel = new JPanel();
        panel.setLayout(null);
        lblNombre = new JLabel("Nombre");
        txtNombre = new JTextField(10);
        lblRut = new JLabel("Rut");
        txtRut = new JTextField();
        lblTelefono = new JLabel("Telefono");
        txtTelefono = new JTextField();
        btnRegistrar = new JButton("Registrar");

        lblNombre.setBounds(70, 70, 80, 30);
        txtNombre.setBounds(150, 70, 230, 30);
        lblRut.setBounds(70, 120, 80, 30);
        txtRut.setBounds(150, 120, 230, 30);
        lblTelefono.setBounds(70, 170, 80, 30);
        txtTelefono.setBounds(150, 170, 230, 30);
        btnRegistrar.setBounds(150, 220, 230, 30);

        btnRegistrar.addActionListener(e -> {
            String nombre = txtNombre.getText();
            String rut = txtRut.getText();
            String telefono = txtTelefono.getText();

            if (nombre.isEmpty() || rut.isEmpty() || telefono.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Debes completar todos los campos", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Cliente cliente = new Cliente(rut, nombre, telefono);
            clienteControlador.registrarCliente(cliente);
            JOptionPane.showMessageDialog(this, "Cliente " + nombre + " registrado correctamente");
            setVisible(false);
        });

        panel.add(lblNombre);
        panel.add(txtNombre);
        panel.add(lblRut);
        panel.add(txtRut);
        panel.add(lblTelefono);
        panel.add(txtTelefono);
        panel.add(btnRegistrar);
        add(panel);
    }
}
