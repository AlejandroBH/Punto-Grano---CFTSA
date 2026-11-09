package puntograno.vistas;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import javax.swing.*;
import puntograno.controladores.ClienteControlador;
import puntograno.modelos.Cliente;

public class ModificarCliente extends JFrame {

    private ClienteControlador clienteControlador;

    private JPanel panel;
    private JLabel lblTitulo;
    private JLabel lblNombre;
    private JTextField txtNombre;
    private JLabel lblRut;
    private JTextField txtRut;
    private JLabel lblTelefono;
    private JTextField txtTelefono;
    private JLabel lblEmail;
    private JTextField txtEmail;
    private JButton btnModificar;

    public ModificarCliente(ClienteControlador clienteControlador, Cliente cliente) {
        this.clienteControlador = clienteControlador;

        setTitle("Modificar Cliente");
        setSize(500, 350);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);

        panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(new Color(245, 240, 235));

        lblTitulo = new JLabel("Modificar Cliente");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        lblTitulo.setForeground(new Color(70, 45, 30));
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);

        lblNombre = new JLabel("Nombre");
        lblNombre.setFont(new Font("Arial", Font.BOLD, 13));
        lblNombre.setForeground(new Color(70, 45, 30));

        txtNombre = new JTextField();
        txtNombre.setFont(new Font("Arial", Font.PLAIN, 14));
        txtNombre.setBackground(Color.WHITE);
        txtNombre.setBorder(BorderFactory.createLineBorder(new Color(180, 160, 140)));
        txtNombre.setToolTipText("Nombre del cliente");

        lblTelefono = new JLabel("Telefono");
        lblTelefono.setFont(new Font("Arial", Font.BOLD, 13));
        lblTelefono.setForeground(new Color(70, 45, 30));

        txtTelefono = new JTextField();
        txtTelefono.setFont(new Font("Arial", Font.PLAIN, 14));
        txtTelefono.setBackground(Color.WHITE);
        txtTelefono.setBorder(BorderFactory.createLineBorder(new Color(180, 160, 140)));
        txtTelefono.setToolTipText("Telefono del cliente");

        lblEmail = new JLabel("Email");
        lblEmail.setFont(new Font("Arial", Font.BOLD, 13));
        lblEmail.setForeground(new Color(70, 45, 30));

        txtEmail = new JTextField();
        txtEmail.setFont(new Font("Arial", Font.PLAIN, 14));
        txtEmail.setBackground(Color.WHITE);
        txtEmail.setBorder(BorderFactory.createLineBorder(new Color(180, 160, 140)));
        txtEmail.setToolTipText("Email del cliente");

        btnModificar = new JButton("Modificar");
        btnModificar.setBackground(new Color(240, 235, 230));
        btnModificar.setForeground(new Color(70, 45, 30));
        btnModificar.setFont(new Font("Arial", Font.BOLD, 14));
        btnModificar.setFocusPainted(false);
        btnModificar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnModificar.setBorder(BorderFactory.createLineBorder(new Color(180, 160, 140)));
        btnModificar.setToolTipText("Guardar modificaciones del cliente");

        lblTitulo.setBounds(70, 20, 310, 30);
        lblNombre.setBounds(70, 70, 80, 30);
        txtNombre.setBounds(150, 70, 230, 30);
        lblTelefono.setBounds(70, 120, 80, 30);
        txtTelefono.setBounds(150, 120, 230, 30);
        lblEmail.setBounds(70, 170, 80, 30);
        txtEmail.setBounds(150, 170, 230, 30);
        btnModificar.setBounds(150, 225, 230, 35);

        // Cargamos datos originales de cliente seleccionado
        txtNombre.setText(cliente.getNombre());
        txtTelefono.setText(cliente.getTelefono());
        txtEmail.setText(cliente.getEmail());

        btnModificar.addActionListener(e -> {
            String nombre = txtNombre.getText();
            String telefono = txtTelefono.getText();
            String email = txtEmail.getText();

            if (nombre.isEmpty() || telefono.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Debes completar todos los campos", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            clienteControlador.editarCliente(cliente, nombre, telefono, email);
            JOptionPane.showMessageDialog(this, "Cliente " + nombre + " modificado correctamente");
            setVisible(false);
        });

        panel.add(lblTitulo);
        panel.add(lblNombre);
        panel.add(txtNombre);
        panel.add(lblTelefono);
        panel.add(txtTelefono);
        panel.add(lblEmail);
        panel.add(txtEmail);
        panel.add(btnModificar);
        add(panel);
    }
}
