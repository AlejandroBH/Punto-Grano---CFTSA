package puntograno.vistas;

import puntograno.controladores.ProductoControlador;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import puntograno.modelos.Producto;
import puntograno.modelos.Usuario;

public class VerProductos extends JInternalFrame {

    private ProductoControlador productoControlador;

    private JPanel panel;
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

        modeloTabla = new DefaultTableModel();
        modeloTabla.addColumn("Nombre");
        modeloTabla.addColumn("Categoria");
        modeloTabla.addColumn("Precio");
        modeloTabla.addColumn("Disponible");
        tablaProductos = new JTable(modeloTabla);

        scrollTabla = new JScrollPane(tablaProductos);
        scrollTabla.setBounds(40, 40, 550, 250);

        btnModificarProducto = new JButton("Modificar Producto");
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
