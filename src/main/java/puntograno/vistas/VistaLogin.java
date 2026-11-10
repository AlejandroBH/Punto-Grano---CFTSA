package puntograno.vistas;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import javax.swing.*;
import puntograno.modelos.Administrador;
import puntograno.modelos.Operador;
import puntograno.modelos.Usuario;

public class VistaLogin extends JFrame {

    private JPanel panel;
    private JLabel lblTitulo;
    private JLabel lblNombre;
    private JTextField txtNombre;
    private JLabel lblClave;
    private JPasswordField txtClave;
    private JButton btnIngresar;

    public VistaLogin() {
        setTitle("Iniciar Sesion");
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);

        panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(new Color(245, 240, 235));

        lblTitulo = new JLabel("PuntoGrano - Iniciar Sesion");
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
        txtNombre.setToolTipText("Ingrese su nombre de usuario");

        lblClave = new JLabel("Clave *");
        lblClave.setFont(new Font("Arial", Font.BOLD, 13));
        lblClave.setForeground(new Color(70, 45, 30));

        txtClave = new JPasswordField();
        txtClave.setFont(new Font("Arial", Font.PLAIN, 14));
        txtClave.setBackground(Color.WHITE);
        txtClave.setBorder(BorderFactory.createLineBorder(new Color(180, 160, 140)));
        txtClave.setToolTipText("Ingrese su clave");

        btnIngresar = new JButton("Ingresar");
        btnIngresar.setUI(new javax.swing.plaf.basic.BasicButtonUI());
        btnIngresar.setBackground(new Color(70, 45, 30));
        btnIngresar.setForeground(Color.WHITE);
        btnIngresar.setOpaque(true);
        btnIngresar.setFont(new Font("Arial", Font.BOLD, 14));
        btnIngresar.setFocusPainted(false);
        btnIngresar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnIngresar.setBorder(BorderFactory.createLineBorder(new Color(70, 45, 30)));
        btnIngresar.setToolTipText("Iniciar sesion en el sistema");

        lblTitulo.setBounds(70, 20, 310, 30);
        lblNombre.setBounds(70, 70, 80, 30);
        txtNombre.setBounds(150, 70, 230, 30);
        lblClave.setBounds(70, 120, 80, 30);
        txtClave.setBounds(150, 120, 230, 30);

        btnIngresar.setBounds(150, 175, 230, 35);

        // Simulacion de login basico con roles
        btnIngresar.addActionListener(e -> {
            Operador operador = new Operador("12.345.678-9", "Operador", "+56987654321", "operador@email.com", "Op123");
            Administrador administrador = new Administrador("13.456.789-0", "Administrador", "+56945628523", "administrador@email.com", "Ad123");

            Boolean loginCorrecto = false;
            Usuario usuarioLogeado = null;

            String nombre = txtNombre.getText();
            String clave = txtClave.getText();

            if (nombre.equals(operador.getNombre()) && clave.equals(operador.getClave())) {
                loginCorrecto = true;
                usuarioLogeado = operador;
                JOptionPane.showMessageDialog(this, "Has iniciado sesion correctamente");
            } else if (nombre.equals(administrador.getNombre()) && clave.equals(administrador.getClave())) {
                loginCorrecto = true;
                usuarioLogeado = administrador;
                JOptionPane.showMessageDialog(this, "Has iniciado sesion correctamente");
            } else if (nombre.isEmpty() || clave.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Debe llenar los campos requeridos", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            } else {
                JOptionPane.showMessageDialog(this, "La cuenta ingresada no existe", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (loginCorrecto) {
                VistaPrincipal vistaPrincipal = new VistaPrincipal(usuarioLogeado);
                vistaPrincipal.setVisible(true);
                this.setVisible(false); // cierra ventana luego de acceder
            }
        });

        panel.add(lblTitulo);
        panel.add(lblNombre);
        panel.add(txtNombre);
        panel.add(lblClave);
        panel.add(txtClave);

        panel.add(btnIngresar);
        add(panel);
    }
}
