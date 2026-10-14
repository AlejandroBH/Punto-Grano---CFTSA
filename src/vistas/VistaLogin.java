package vistas;

import javax.swing.*;
import modelos.Administrador;
import modelos.Operador;
import modelos.Usuario;

public class VistaLogin extends JFrame {

    private JPanel panel;
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
        lblNombre = new JLabel("Nombre");
        txtNombre = new JTextField(10);
        lblClave = new JLabel("Clave");
        txtClave = new JPasswordField();

        btnIngresar = new JButton("Ingresar");

        lblNombre.setBounds(70, 70, 80, 30);
        txtNombre.setBounds(150, 70, 230, 30);
        lblClave.setBounds(70, 120, 80, 30);
        txtClave.setBounds(150, 120, 230, 30);

        btnIngresar.setBounds(150, 170, 230, 30);

        // Simulacion de login basico con roles
        btnIngresar.addActionListener(e -> {
            Operador operador = new Operador("12.345.678-9", "Operador", "+56987654321", "Op123");
            Administrador administrador = new Administrador("13.456.789-0", "Administrador", "+56945628523", "Ad123");

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

        panel.add(lblNombre);
        panel.add(txtNombre);
        panel.add(lblClave);
        panel.add(txtClave);

        panel.add(btnIngresar);
        add(panel);
    }
}
