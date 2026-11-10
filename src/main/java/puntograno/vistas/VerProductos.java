package puntograno.vistas;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import puntograno.controladores.ProductoControlador;
import puntograno.modelos.Producto;
import puntograno.modelos.Usuario;

public class VerProductos extends JInternalFrame {

    private ProductoControlador productoControlador;

    private JPanel panel;
    private JLabel lblTitulo;
    private JTable tablaProductos;
    private DefaultTableModel modeloTabla;
    private JScrollPane scrollTabla;
    private JButton btnModificarProducto;
    private JButton btnEliminarProducto;

    private void cargarProductos() {
        modeloTabla.setRowCount(0);

        for (Producto producto : productoControlador.getProductos()) {
            String disponible = producto.isDisponible() ? "SI" : "NO"; // formatea condicion booleana a string con operador ternario

            modeloTabla.addRow(new Object[]{
                producto.getNombre(),
                producto.getCategoria(),
                "$ " + producto.getPrecio(),
                producto.getStock(),
                disponible
            });
        }
    }

    public VerProductos(Usuario usuarioLogeado, ProductoControlador productoControlador) {
        this.productoControlador = productoControlador;

        setTitle("Productos Registrados");
        setSize(650, 450);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);

        panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(new Color(245, 240, 235));

        lblTitulo = new JLabel("Productos Registrados");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        lblTitulo.setForeground(new Color(70, 45, 30));
        lblTitulo.setBounds(40, 10, 550, 30);

        modeloTabla = new DefaultTableModel();
        modeloTabla.addColumn("Nombre");
        modeloTabla.addColumn("Categoria");
        modeloTabla.addColumn("Precio");
        modeloTabla.addColumn("Stock");
        modeloTabla.addColumn("Disponible");
        tablaProductos = new JTable(modeloTabla);

        tablaProductos.setRowHeight(28);
        tablaProductos.setFont(new Font("Arial", Font.PLAIN, 13));
        tablaProductos.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));
        tablaProductos.getTableHeader().setForeground(new Color(70, 45, 30));
        tablaProductos.getTableHeader().setReorderingAllowed(false);
        tablaProductos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tablaProductos.setSelectionBackground(new Color(230, 215, 195));
        tablaProductos.setSelectionForeground(Color.BLACK);

        scrollTabla = new JScrollPane(tablaProductos);
        scrollTabla.getViewport().setBackground(Color.WHITE);
        scrollTabla.setBorder(BorderFactory.createLineBorder(new Color(180, 160, 140)));
        scrollTabla.setBounds(40, 45, 550, 255);

        btnModificarProducto = new JButton("Modificar Producto");
        btnModificarProducto.setUI(new javax.swing.plaf.basic.BasicButtonUI());
        btnModificarProducto.setBackground(new Color(70, 45, 30));
        btnModificarProducto.setForeground(Color.WHITE);
        btnModificarProducto.setOpaque(true);
        btnModificarProducto.setFont(new Font("Arial", Font.BOLD, 14));
        btnModificarProducto.setFocusPainted(false);
        btnModificarProducto.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnModificarProducto.setBorder(BorderFactory.createLineBorder(new Color(70, 45, 30)));
        btnModificarProducto.setToolTipText("Modificar el producto seleccionado");
        btnModificarProducto.setBounds(40, 320, 200, 40);

        btnModificarProducto.addActionListener(e -> {
            int filaSeleccionada = tablaProductos.getSelectedRow();

            if (filaSeleccionada == -1) {
                JOptionPane.showMessageDialog(this, "Debes seleccionar una fila", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Producto productoSeleccionado = productoControlador.obtenerProducto(filaSeleccionada);

            ModificarProducto modificarProducto = new ModificarProducto(productoControlador, productoSeleccionado);
            modificarProducto.setVisible(true);
        });

        btnEliminarProducto = new JButton("Eliminar Producto");
        btnEliminarProducto.setUI(new javax.swing.plaf.basic.BasicButtonUI());
        btnEliminarProducto.setBackground(new Color(160, 50, 40));
        btnEliminarProducto.setForeground(Color.WHITE);
        btnEliminarProducto.setOpaque(true);
        btnEliminarProducto.setFont(new Font("Arial", Font.BOLD, 14));
        btnEliminarProducto.setFocusPainted(false);
        btnEliminarProducto.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnEliminarProducto.setBorder(BorderFactory.createLineBorder(new Color(160, 50, 40)));
        btnEliminarProducto.setToolTipText("Eliminar el producto seleccionado");
        btnEliminarProducto.setBounds(270, 320, 200, 40);

        btnEliminarProducto.addActionListener(e -> {
            int filaSeleccionada = tablaProductos.getSelectedRow();

            if (filaSeleccionada == -1) {
                JOptionPane.showMessageDialog(this, "Debes seleccionar una fila", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Producto productoSeleccionado = productoControlador.obtenerProducto(filaSeleccionada);
            productoControlador.eliminarProducto(productoSeleccionado);
            JOptionPane.showMessageDialog(this, "Producto " + productoSeleccionado.getNombre() + " eliminado correctamente");
            setVisible(false);
        });

        panel.add(lblTitulo);
        panel.add(scrollTabla);

        // Solo administradores pueden modificar o eliminar productos
        if (usuarioLogeado.puedeGestionarCatalogo()) {
            panel.add(btnModificarProducto);
            panel.add(btnEliminarProducto);
        }

        add(panel);
        cargarProductos();
    }
}
