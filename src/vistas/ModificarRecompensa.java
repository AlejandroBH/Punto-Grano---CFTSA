package vistas;

import controladores.ProductoControlador;
import controladores.RecompensaControlador;
import java.util.ArrayList;
import javax.swing.*;
import modelos.Producto;
import modelos.Recompensa;

public class ModificarRecompensa extends JFrame {

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
    private JButton btnModificar;

    private void cargarProductos() {
        for (Producto producto : productoControlador.getProductos()) {
            if (producto.isDisponible()) {
                comPremio.addItem(producto.getNombre());
            }
        }
    }

    public ModificarRecompensa(RecompensaControlador recompensaControlador, ProductoControlador productoControlador, Recompensa recompensa) {
        this.recompensaControlador = recompensaControlador;
        this.productoControlador = productoControlador;

        setTitle("Modificar Recompensa");
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
        btnModificar = new JButton("Modificar");

        lblPremio.setBounds(70, 70, 80, 30);
        comPremio.setBounds(150, 70, 230, 30);
        lblCostoPuntos.setBounds(70, 120, 80, 30);
        txtCostoPuntos.setBounds(150, 120, 230, 30);
        lblDisponible.setBounds(70, 170, 80, 30);
        cheDisponible.setBounds(150, 170, 230, 30);
        btnModificar.setBounds(150, 220, 230, 30);

        // Cargamos datos originales de cliente seleccionado
        comPremio.setSelectedItem(recompensa.getPremio());
        txtCostoPuntos.setText(Integer.toString(recompensa.getCostoPuntos()));
        cheDisponible.setSelected(recompensa.isDisponible());

        btnModificar.addActionListener(e -> {
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

            recompensaControlador.editarRecompensa(recompensa, producto, premio, costoPuntos, disponible);

            JOptionPane.showMessageDialog(this, "Recompensa " + recompensa.getPremio() + " registrada correctamente");
            setVisible(false);
        });

        panel.add(lblPremio);
        panel.add(comPremio);
        panel.add(lblCostoPuntos);
        panel.add(txtCostoPuntos);
        panel.add(lblDisponible);
        panel.add(cheDisponible);
        panel.add(btnModificar);
        add(panel);
    }
}
