package puntograno.vistas;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
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

        lblNombre = new JLabel("Nombre *");
        lblNombre.setFont(new Font("Arial", Font.BOLD, 13));
        lblNombre.setForeground(new Color(70, 45, 30));

        txtNombre = new JTextField(10);
        txtNombre.setFont(new Font("Arial", Font.PLAIN, 14));
        txtNombre.setBackground(Color.WHITE);
        txtNombre.setBorder(BorderFactory.createLineBorder(new Color(180, 160, 140)));
        txtNombre.setToolTipText("Ingrese nombre completo");

        lblRut = new JLabel("Rut *");
        lblRut.setFont(new Font("Arial", Font.BOLD, 13));
        lblRut.setForeground(new Color(70, 45, 30));

        txtRut = new JTextField();
        txtRut.setFont(new Font("Arial", Font.PLAIN, 14));
        txtRut.setBackground(Color.WHITE);
        txtRut.setBorder(BorderFactory.createLineBorder(new Color(180, 160, 140)));
        txtRut.setToolTipText("Ingrese RUT sin espacios");

        lblTelefono = new JLabel("Telefono *");
        lblTelefono.setFont(new Font("Arial", Font.BOLD, 13));
        lblTelefono.setForeground(new Color(70, 45, 30));

        txtTelefono = new JTextField();
        txtTelefono.setFont(new Font("Arial", Font.PLAIN, 14));
        txtTelefono.setBackground(Color.WHITE);
        txtTelefono.setBorder(BorderFactory.createLineBorder(new Color(180, 160, 140)));
        txtTelefono.setToolTipText("Ingrese numero de telefono");

        lblEmail = new JLabel("Email *");
        lblEmail.setFont(new Font("Arial", Font.BOLD, 13));
        lblEmail.setForeground(new Color(70, 45, 30));

        txtEmail = new JTextField();
        txtEmail.setFont(new Font("Arial", Font.PLAIN, 14));
        txtEmail.setBackground(Color.WHITE);
        txtEmail.setBorder(BorderFactory.createLineBorder(new Color(180, 160, 140)));
        txtEmail.setToolTipText("Ingrese correo electronico");

        btnRegistrar = new JButton("Registrar");
        btnRegistrar.setUI(new javax.swing.plaf.basic.BasicButtonUI());
        btnRegistrar.setBackground(new Color(70, 45, 30));
        btnRegistrar.setForeground(Color.WHITE);
        btnRegistrar.setOpaque(true);
        btnRegistrar.setFont(new Font("Arial", Font.BOLD, 14));
        btnRegistrar.setFocusPainted(false);
        btnRegistrar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnRegistrar.setBorder(BorderFactory.createLineBorder(new Color(70, 45, 30)));
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

            Cliente cliente = new Cliente(rut, nombre, telefono, email);

            try {
                clienteControlador.registrarCliente(cliente);
            } catch (SQLException ex) {
                // Si hay error de duplicidad de rut arroja alerta
                if (ex.getErrorCode() == 1062) {
                    JOptionPane.showMessageDialog(this, "Ya existe un cliente registrado con el rut: " + rut, "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }

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
