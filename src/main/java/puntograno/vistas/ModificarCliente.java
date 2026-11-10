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
        setSize(500, 400);
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

        lblNombre = new JLabel("Nombre *");
        lblNombre.setFont(new Font("Arial", Font.BOLD, 13));
        lblNombre.setForeground(new Color(70, 45, 30));

        txtNombre = new JTextField();
        txtNombre.setFont(new Font("Arial", Font.PLAIN, 14));
        txtNombre.setBackground(Color.WHITE);
        txtNombre.setBorder(BorderFactory.createLineBorder(new Color(180, 160, 140)));
        txtNombre.setToolTipText("Nombre del cliente");

        lblRut = new JLabel("Rut *");
        lblRut.setFont(new Font("Arial", Font.BOLD, 13));
        lblRut.setForeground(new Color(70, 45, 30));

        txtRut = new JTextField();
        txtRut.setFont(new Font("Arial", Font.PLAIN, 14));
        txtRut.setBackground(Color.WHITE);
        txtRut.setBorder(BorderFactory.createLineBorder(new Color(180, 160, 140)));
        txtRut.setToolTipText("Rut del cliente");

        lblTelefono = new JLabel("Telefono *");
        lblTelefono.setFont(new Font("Arial", Font.BOLD, 13));
        lblTelefono.setForeground(new Color(70, 45, 30));

        txtTelefono = new JTextField();
        txtTelefono.setFont(new Font("Arial", Font.PLAIN, 14));
        txtTelefono.setBackground(Color.WHITE);
        txtTelefono.setBorder(BorderFactory.createLineBorder(new Color(180, 160, 140)));
        txtTelefono.setToolTipText("Telefono del cliente");

        lblEmail = new JLabel("Email *");
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
        lblRut.setBounds(70, 120, 80, 30);
        txtRut.setBounds(150, 120, 230, 30);
        lblTelefono.setBounds(70, 170, 80, 30);
        txtTelefono.setBounds(150, 170, 230, 30);
        lblEmail.setBounds(70, 220, 80, 30);
        txtEmail.setBounds(150, 220, 230, 30);
        btnModificar.setBounds(150, 275, 230, 35);

        // Cargamos datos originales de cliente seleccionado
        txtNombre.setText(cliente.getNombre());
        txtRut.setText(cliente.getRut());
        txtTelefono.setText(cliente.getTelefono());
        txtEmail.setText(cliente.getEmail());

        btnModificar.addActionListener(e -> {
            String nombre = txtNombre.getText().trim();
            String rut = txtRut.getText().trim();
            String telefono = txtTelefono.getText().trim();
            String email = txtEmail.getText().trim();

            if (nombre.isEmpty() || rut.isEmpty() || telefono.isEmpty() || email.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Debes completar todos los campos", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Validamos campos con expresiones regulares
            String regexRut = "^[0-9]{1,2}\\.[0-9]{3}\\.[0-9]{3}-[0-9kK]$";
            String regexTelefono = "^\\+569[0-9]{8}$";
            String regexEmail = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";

            if (!rut.matches(regexRut)) {
                JOptionPane.showMessageDialog(this, "El RUT no tiene un formato válido. Formato esperado: 12.123.123-1", "Error de Validación", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!telefono.matches(regexTelefono)) {
                JOptionPane.showMessageDialog(this, "El número de teléfono no tiene un formato válido. Formato esperado: +56912341234", "Error de Validación", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!email.matches(regexEmail)) {
                JOptionPane.showMessageDialog(this, "El email no tiene un formato válido. Formato esperado: cuenta@correo.com", "Error de Validación", JOptionPane.ERROR_MESSAGE);
                return;
            }

            clienteControlador.editarCliente(cliente, rut, nombre, telefono, email);
            JOptionPane.showMessageDialog(this, "Cliente " + nombre + " modificado correctamente");
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
        panel.add(btnModificar);
        add(panel);
    }
}
