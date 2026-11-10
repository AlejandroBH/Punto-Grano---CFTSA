package puntograno.vistas;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import javax.swing.*;
import puntograno.controladores.ProductoControlador;
import puntograno.modelos.Producto;

public class ModificarProducto extends JFrame {

    private ProductoControlador productoControlador;

    private String[] categorias = {"Productos", "Accesorios"};

    private JPanel panel;
    private JLabel lblTitulo;
    private JLabel lblNombre;
    private JTextField txtNombre;
    private JLabel lblPrecio;
    private JTextField txtPrecio;
    private JLabel lblStock;
    private JTextField txtStock;
    private JLabel lblCategoria;
    private JComboBox comCategoria;
    private JLabel lblDisponible;
    private JCheckBox cheDisponible;
    private JButton btnModificar;

    public ModificarProducto(ProductoControlador productoControlador, Producto producto) {
        this.productoControlador = productoControlador;

        setTitle("Modificar Producto");
        setSize(500, 450);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);

        panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(new Color(245, 240, 235));

        lblTitulo = new JLabel("Modificar Producto");
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
        txtNombre.setToolTipText("Nombre del producto");

        lblPrecio = new JLabel("Precio *");
        lblPrecio.setFont(new Font("Arial", Font.BOLD, 13));
        lblPrecio.setForeground(new Color(70, 45, 30));

        txtPrecio = new JTextField();
        txtPrecio.setFont(new Font("Arial", Font.PLAIN, 14));
        txtPrecio.setBackground(Color.WHITE);
        txtPrecio.setBorder(BorderFactory.createLineBorder(new Color(180, 160, 140)));
        txtPrecio.setToolTipText("Precio en pesos");

        lblStock = new JLabel("Stock *");
        lblStock.setFont(new Font("Arial", Font.BOLD, 13));
        lblStock.setForeground(new Color(70, 45, 30));

        txtStock = new JTextField();
        txtStock.setFont(new Font("Arial", Font.PLAIN, 14));
        txtStock.setBackground(Color.WHITE);
        txtStock.setBorder(BorderFactory.createLineBorder(new Color(180, 160, 140)));
        txtStock.setToolTipText("Cantidad disponible");

        lblCategoria = new JLabel("Categoria");
        lblCategoria.setFont(new Font("Arial", Font.BOLD, 13));
        lblCategoria.setForeground(new Color(70, 45, 30));

        comCategoria = new JComboBox<>(categorias);
        comCategoria.setFont(new Font("Arial", Font.PLAIN, 14));
        comCategoria.setBackground(Color.WHITE);

        lblDisponible = new JLabel("Disponible");
        lblDisponible.setFont(new Font("Arial", Font.BOLD, 13));
        lblDisponible.setForeground(new Color(70, 45, 30));

        cheDisponible = new JCheckBox("", true);
        cheDisponible.setBackground(new Color(245, 240, 235));

        btnModificar = new JButton("Modificar");
        btnModificar.setUI(new javax.swing.plaf.basic.BasicButtonUI());
        btnModificar.setBackground(new Color(70, 45, 30));
        btnModificar.setForeground(Color.WHITE);
        btnModificar.setOpaque(true);
        btnModificar.setFont(new Font("Arial", Font.BOLD, 14));
        btnModificar.setFocusPainted(false);
        btnModificar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnModificar.setBorder(BorderFactory.createLineBorder(new Color(70, 45, 30)));
        btnModificar.setToolTipText("Guardar modificaciones del producto");

        lblTitulo.setBounds(70, 20, 310, 30);
        lblNombre.setBounds(70, 70, 80, 30);
        txtNombre.setBounds(150, 70, 230, 30);
        lblPrecio.setBounds(70, 120, 80, 30);
        txtPrecio.setBounds(150, 120, 230, 30);
        lblStock.setBounds(70, 170, 80, 30);
        txtStock.setBounds(150, 170, 230, 30);
        lblCategoria.setBounds(70, 220, 80, 30);
        comCategoria.setBounds(150, 220, 230, 30);
        lblDisponible.setBounds(70, 270, 80, 30);
        cheDisponible.setBounds(150, 270, 230, 30);
        btnModificar.setBounds(150, 325, 230, 35);

        // Cargamos datos originales de producto seleccionado
        txtNombre.setText(producto.getNombre());
        txtPrecio.setText(Integer.toString(producto.getPrecio()));
        txtStock.setText(Integer.toString(producto.getStock()));
        comCategoria.setSelectedItem(producto.getCategoria());
        cheDisponible.setSelected(producto.isDisponible());

        btnModificar.addActionListener(e -> {
            String nombre = txtNombre.getText();
            String precioTexto = txtPrecio.getText();
            String stockTexto = txtStock.getText();

            String categoria = comCategoria.getSelectedItem().toString();
            Boolean disponible = cheDisponible.isSelected();

            if (nombre.isEmpty() || precioTexto.isEmpty() || stockTexto.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Debes completar todos los campos", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int precio;
            int stock;

            try {
                precio = Integer.parseInt(precioTexto);
                stock = Integer.parseInt(stockTexto);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "El precio o stock deben ser numeros", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (precio <= 0) {
                JOptionPane.showMessageDialog(this, "El precio debe ser mayor a cero", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (stock < 0) {
                JOptionPane.showMessageDialog(this, "El stock no puede ser negativo", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (stock == 0) {
                disponible = false;
            }

            productoControlador.editarProducto(producto, nombre, precio, stock, categoria, disponible);

            JOptionPane.showMessageDialog(this, "Producto " + nombre + " modificado correctamente");
            setVisible(false);
        });

        panel.add(lblTitulo);
        panel.add(lblNombre);
        panel.add(txtNombre);
        panel.add(lblPrecio);
        panel.add(txtPrecio);
        panel.add(lblStock);
        panel.add(txtStock);
        panel.add(lblCategoria);
        panel.add(comCategoria);
        panel.add(lblDisponible);
        panel.add(cheDisponible);
        panel.add(btnModificar);
        add(panel);
    }
}
