package vistas;

import controladores.ProductoControlador;
import javax.swing.*;
import modelos.Producto;

public class ModificarProducto extends JFrame {

    private ProductoControlador productoControlador;

    private String[] categorias = {"Productos", "Accesorios"};

    private JPanel panel;
    private JLabel lblNombre;
    private JTextField txtNombre;
    private JLabel lblPrecio;
    private JTextField txtPrecio;
    private JLabel lblCategoria;
    private JComboBox comCategoria;
    private JLabel lblDisponible;
    private JCheckBox cheDisponible;
    private JButton btnModificar;

    public ModificarProducto(ProductoControlador productoControlador, Producto producto) {
        this.productoControlador = productoControlador;

        setTitle("Modificar Producto");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);

        panel = new JPanel();
        panel.setLayout(null);
        lblNombre = new JLabel("Nombre");
        txtNombre = new JTextField(10);
        lblPrecio = new JLabel("Precio");
        txtPrecio = new JTextField();
        lblCategoria = new JLabel("Categoria");
        comCategoria = new JComboBox<>(categorias);
        lblDisponible = new JLabel("Disponible");
        cheDisponible = new JCheckBox("", true);
        btnModificar = new JButton("Modificar");

        lblNombre.setBounds(70, 70, 80, 30);
        txtNombre.setBounds(150, 70, 230, 30);
        lblPrecio.setBounds(70, 120, 80, 30);
        txtPrecio.setBounds(150, 120, 230, 30);
        lblCategoria.setBounds(70, 170, 80, 30);
        comCategoria.setBounds(150, 170, 230, 30);
        lblDisponible.setBounds(70, 220, 80, 30);
        cheDisponible.setBounds(150, 220, 230, 30);
        btnModificar.setBounds(150, 270, 230, 30);

        // Cargamos datos originales de producto seleccionado
        txtNombre.setText(producto.getNombre());
        txtPrecio.setText(Integer.toString(producto.getPrecio()));
        comCategoria.setSelectedItem(producto.getCategoria());
        cheDisponible.setSelected(producto.isDisponible());

        btnModificar.addActionListener(e -> {

            String nombre = txtNombre.getText();
            String precioTexto = txtPrecio.getText();
            String categoria = comCategoria.getSelectedItem().toString();
            Boolean disponible = cheDisponible.isSelected();

            if (nombre.isEmpty() || precioTexto.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Debes completar todos los campos", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int precio;

            try {
                precio = Integer.parseInt(precioTexto);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "El precio debe ser un numero", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (precio <= 0) {
                JOptionPane.showMessageDialog(this, "El precio debe ser mayor a cero", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            productoControlador.editarProducto(producto, nombre, precio, categoria, disponible);

            JOptionPane.showMessageDialog(this, "Producto " + nombre + " modificado correctamente");
            setVisible(false);
        });

        panel.add(lblNombre);
        panel.add(txtNombre);
        panel.add(lblPrecio);
        panel.add(txtPrecio);
        panel.add(lblCategoria);
        panel.add(comCategoria);
        panel.add(lblDisponible);
        panel.add(cheDisponible);
        panel.add(btnModificar);
        add(panel);
    }
}
