package puntograno.vistas;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.util.ArrayList;
import javax.swing.*;
import puntograno.controladores.ProductoControlador;
import puntograno.controladores.RecompensaControlador;
import puntograno.modelos.Producto;
import puntograno.modelos.Recompensa;

public class NuevaRecompensa extends JFrame {

    private RecompensaControlador recompensaControlador;
    private ProductoControlador productoControlador;

    private ArrayList<Producto> listaProductos;

    private JPanel panel;
    private JLabel lblTitulo;
    private JLabel lblPremio;
    private JComboBox comPremio;
    private JLabel lblCostoPuntos;
    private JTextField txtCostoPuntos;
    private JLabel lblDisponible;
    private JCheckBox cheDisponible;
    private JButton btnRegistrar;

    private void cargarProductos() {
        listaProductos = new ArrayList<>();

        comPremio.removeAllItems();
        for (Producto producto : productoControlador.getProductos()) {
            if (producto.isDisponible()) {
                listaProductos.add(producto);
                comPremio.addItem(producto.getNombre());
            }
        }
    }

    public NuevaRecompensa(RecompensaControlador recompensaControlador, ProductoControlador productoControlador) {
        this.recompensaControlador = recompensaControlador;
        this.productoControlador = productoControlador;

        setTitle("Registrar Nueva Recompensa");
        setSize(500, 350);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);

        panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(new Color(245, 240, 235));

        lblTitulo = new JLabel("Registrar Nueva Recompensa");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        lblTitulo.setForeground(new Color(70, 45, 30));
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);

        lblPremio = new JLabel("Premio");
        lblPremio.setFont(new Font("Arial", Font.BOLD, 13));
        lblPremio.setForeground(new Color(70, 45, 30));

        comPremio = new JComboBox<>();
        comPremio.setFont(new Font("Arial", Font.PLAIN, 14));
        comPremio.setBackground(Color.WHITE);
        cargarProductos(); // Llenamos combobox con lista de productos disponibles

        lblCostoPuntos = new JLabel("Puntos *");
        lblCostoPuntos.setFont(new Font("Arial", Font.BOLD, 13));
        lblCostoPuntos.setForeground(new Color(70, 45, 30));

        txtCostoPuntos = new JTextField();
        txtCostoPuntos.setFont(new Font("Arial", Font.PLAIN, 14));
        txtCostoPuntos.setBackground(Color.WHITE);
        txtCostoPuntos.setBorder(BorderFactory.createLineBorder(new Color(180, 160, 140)));
        txtCostoPuntos.setToolTipText("Costo en puntos necesarios");

        lblDisponible = new JLabel("Disponible");
        lblDisponible.setFont(new Font("Arial", Font.BOLD, 13));
        lblDisponible.setForeground(new Color(70, 45, 30));

        cheDisponible = new JCheckBox("", true);
        cheDisponible.setBackground(new Color(245, 240, 235));

        btnRegistrar = new JButton("Registrar");
        btnRegistrar.setBackground(new Color(240, 235, 230));
        btnRegistrar.setForeground(new Color(70, 45, 30));
        btnRegistrar.setFont(new Font("Arial", Font.BOLD, 14));
        btnRegistrar.setFocusPainted(false);
        btnRegistrar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnRegistrar.setBorder(BorderFactory.createLineBorder(new Color(180, 160, 140)));
        btnRegistrar.setToolTipText("Registrar nueva recompensa");

        lblTitulo.setBounds(70, 20, 310, 30);
        lblPremio.setBounds(70, 70, 80, 30);
        comPremio.setBounds(150, 70, 230, 30);
        lblCostoPuntos.setBounds(70, 120, 80, 30);
        txtCostoPuntos.setBounds(150, 120, 230, 30);
        lblDisponible.setBounds(70, 170, 80, 30);
        cheDisponible.setBounds(150, 170, 230, 30);
        btnRegistrar.setBounds(150, 225, 230, 35);

        btnRegistrar.addActionListener(e -> {
            if (comPremio.getSelectedIndex() == -1 || comPremio.getSelectedItem() == null) {
                JOptionPane.showMessageDialog(this, "No existen productos en el catalogo", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String premio = comPremio.getSelectedItem().toString();
            String costoPuntosTexto = txtCostoPuntos.getText();
            Boolean disponible = cheDisponible.isSelected();

            if (premio == null || costoPuntosTexto.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Debes completar todos los campos", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int costoPuntos;

            try {
                costoPuntos = Integer.parseInt(costoPuntosTexto);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "El costo de puntos debe ser un numero", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (costoPuntos <= 0) {
                JOptionPane.showMessageDialog(this, "Los puntos deben ser mayor a cero", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int idProducto = comPremio.getSelectedIndex();
            Producto producto = listaProductos.get(idProducto);

            Recompensa recompensa = new Recompensa(producto, costoPuntos, disponible);
            recompensaControlador.registrarRecompensa(recompensa);
            JOptionPane.showMessageDialog(this, "Recompensa " + recompensa.getPremio() + " registrada correctamente");
            setVisible(false);
        });

        panel.add(lblTitulo);
        panel.add(lblPremio);
        panel.add(comPremio);
        panel.add(lblCostoPuntos);
        panel.add(txtCostoPuntos);
        panel.add(lblDisponible);
        panel.add(cheDisponible);
        panel.add(btnRegistrar);
        add(panel);
    }
}
