package puntograno.vistas;

import puntograno.controladores.ProductoControlador;
import puntograno.controladores.RecompensaControlador;
import java.util.ArrayList;
import javax.swing.*;
import puntograno.modelos.Producto;
import puntograno.modelos.Recompensa;

public class NuevaRecompensa extends JFrame {

    private RecompensaControlador recompensaControlador;
    private ProductoControlador productoControlador;

    private ArrayList<String> premios;

    private JPanel panel;
    private JLabel lblPremio;
    private JComboBox comPremio;
    private JLabel lblCostoPuntos;
    private JTextField txtCostoPuntos;
    private JLabel lblDisponible;
    private JCheckBox cheDisponible;
    private JButton btnRegistrar;

    private void cargarProductos() {
        for (Producto producto : productoControlador.getProductos()) {
            if (producto.isDisponible()) {
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
        lblPremio = new JLabel("Premio");
        comPremio = new JComboBox<>();
        cargarProductos(); // Llenamos combobox con lista de productos disponibles
        lblCostoPuntos = new JLabel("Puntos");
        txtCostoPuntos = new JTextField();
        lblDisponible = new JLabel("Disponible");
        cheDisponible = new JCheckBox("", true);
        btnRegistrar = new JButton("Registrar");

        lblPremio.setBounds(70, 70, 80, 30);
        comPremio.setBounds(150, 70, 230, 30);
        lblCostoPuntos.setBounds(70, 120, 80, 30);
        txtCostoPuntos.setBounds(150, 120, 230, 30);
        lblDisponible.setBounds(70, 170, 80, 30);
        cheDisponible.setBounds(150, 170, 230, 30);
        btnRegistrar.setBounds(150, 220, 230, 30);

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
            Producto producto = productoControlador.obtenerProducto(idProducto);

            Recompensa recompensa = new Recompensa(producto, costoPuntos, disponible);
            recompensaControlador.registrarRecompensa(recompensa);
            JOptionPane.showMessageDialog(this, "Recompensa " + recompensa.getPremio() + " registrada correctamente");
            setVisible(false);
        });

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
