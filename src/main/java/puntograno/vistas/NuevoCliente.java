package puntograno.vistas;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import javax.swing.*;
import puntograno.controladores.ClienteControlador;
import puntograno.modelos.Cliente;

public class NuevoCliente extends JFrame {

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
    private JButton btnRegistrar;

    public NuevoCliente(ClienteControlador clienteControlador) {
        this.clienteControlador = clienteControlador;

        setTitle("Registrar Nuevo Cliente");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);

        panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(new Color(245, 240, 235));

        lblTitulo = new JLabel("Registrar Nuevo Cliente");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        lblTitulo.setForeground(new Color(70, 45, 30));
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);

        lblNombre = new JLabel("Nombre");
        lblNombre.setFont(new Font("Arial", Font.BOLD, 13));
        lblNombre.setForeground(new Color(70, 45, 30));

        txtNombre = new JTextField(10);
        txtNombre.setFont(new Font("Arial", Font.PLAIN, 14));
        txtNombre.setBackground(Color.WHITE);
        txtNombre.setBorder(BorderFactory.createLineBorder(new Color(180, 160, 140)));
        txtNombre.setToolTipText("Ingrese nombre completo");

        lblRut = new JLabel("Rut");
        lblRut.setFont(new Font("Arial", Font.BOLD, 13));
        lblRut.setForeground(new Color(70, 45, 30));

        txtRut = new JTextField();
        txtRut.setFont(new Font("Arial", Font.PLAIN, 14));
        txtRut.setBackground(Color.WHITE);
        txtRut.setBorder(BorderFactory.createLineBorder(new Color(180, 160, 140)));
        txtRut.setToolTipText("Ingrese RUT sin espacios");

        lblTelefono = new JLabel("Telefono");
        lblTelefono.setFont(new Font("Arial", Font.BOLD, 13));
        lblTelefono.setForeground(new Color(70, 45, 30));

        txtTelefono = new JTextField();
        txtTelefono.setFont(new Font("Arial", Font.PLAIN, 14));
        txtTelefono.setBackground(Color.WHITE);
        txtTelefono.setBorder(BorderFactory.createLineBorder(new Color(180, 160, 140)));
        txtTelefono.setToolTipText("Ingrese numero de telefono");

        lblEmail = new JLabel("Email");
        lblEmail.setFont(new Font("Arial", Font.BOLD, 13));
        lblEmail.setForeground(new Color(70, 45, 30));

        txtEmail = new JTextField();
        txtEmail.setFont(new Font("Arial", Font.PLAIN, 14));
        txtEmail.setBackground(Color.WHITE);
        txtEmail.setBorder(BorderFactory.createLineBorder(new Color(180, 160, 140)));
        txtEmail.setToolTipText("Ingrese correo electronico");

        btnRegistrar = new JButton("Registrar");
        btnRegistrar.setBackground(new Color(240, 235, 230));
        btnRegistrar.setForeground(new Color(70, 45, 30));
        btnRegistrar.setFont(new Font("Arial", Font.BOLD, 14));
        btnRegistrar.setFocusPainted(false);
        btnRegistrar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnRegistrar.setBorder(BorderFactory.createLineBorder(new Color(180, 160, 140)));
        btnRegistrar.setToolTipText("Guardar un nuevo cliente");

        lblTitulo.setBounds(70, 20, 310, 30);
        lblNombre.setBounds(70, 70, 80, 30);
        txtNombre.setBounds(150, 70, 230, 30);
        lblRut.setBounds(70, 120, 80, 30);
        txtRut.setBounds(150, 120, 230, 30);
        lblTelefono.setBounds(70, 170, 80, 30);
        txtTelefono.setBounds(150, 170, 230, 30);
        lblEmail.setBounds(70, 220, 80, 30);
        txtEmail.setBounds(150, 220, 230, 30);
        btnRegistrar.setBounds(150, 275, 230, 35);

        btnRegistrar.addActionListener(e -> {
            String nombre = txtNombre.getText();
            String rut = txtRut.getText();
            String telefono = txtTelefono.getText();
            String email = txtEmail.getText();

            if (nombre.isEmpty() || rut.isEmpty() || telefono.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Debes completar todos los campos", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Cliente cliente = new Cliente(rut, nombre, telefono, email);
            clienteControlador.registrarCliente(cliente);
            JOptionPane.showMessageDialog(this, "Cliente " + nombre + " registrado correctamente");
            setVisible(false);
        });

        panel.add(lblTitulo);
        panel.add(lblNombre);
        panel.add(txtNombre);
        panel.add(lblRut);
        panel.add(txtRut);
        panel.add(lblTelefono);
        panel.add(txtTelefono);
        panel.add(lblEmail);
        panel.add(txtEmail);
        panel.add(btnRegistrar);
        add(panel);
    }
}
