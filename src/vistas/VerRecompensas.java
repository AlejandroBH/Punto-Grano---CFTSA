package vistas;

import controladores.ProductoControlador;
import controladores.RecompensaControlador;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import modelos.Recompensa;
import modelos.Usuario;

public class VerRecompensas extends JInternalFrame {

    private RecompensaControlador recompensaControlador;
    private ProductoControlador productoControlador;

    private JPanel panel;
    private JTable tablaRecompensa;
    private DefaultTableModel modeloTabla;
    private JScrollPane scrollTabla;
    private JButton btnModificarRecompensa;
    private JButton btnEliminarRecompensa;

    private void cargarRecompensas() {
        modeloTabla.setRowCount(0);

        for (Recompensa recompensa : recompensaControlador.getRecompensas()) {
            String disponible = recompensa.isDisponible() ? "SI" : "NO"; // formatea condicion booleana a string con operador ternario

            modeloTabla.addRow(new Object[]{
                recompensa.getPremio(),
                recompensa.getCostoPuntos(),
                disponible
            });
        }
    }

    public VerRecompensas(Usuario usuarioLogeado, RecompensaControlador recompensaControlador, ProductoControlador productoControlador) {
        this.recompensaControlador = recompensaControlador;
        this.productoControlador = productoControlador;

        setTitle("Recompensas Registradas");
        setSize(650, 450);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);

        panel = new JPanel();
        panel.setLayout(null);

        modeloTabla = new DefaultTableModel();
        modeloTabla.addColumn("Premio");
        modeloTabla.addColumn("Puntos");
        modeloTabla.addColumn("Disponible");
        tablaRecompensa = new JTable(modeloTabla);

        scrollTabla = new JScrollPane(tablaRecompensa);
        scrollTabla.setBounds(40, 40, 550, 250);

        btnModificarRecompensa = new JButton("Modificar Recompensa");
        btnModificarRecompensa.setBounds(40, 320, 200, 40);

        btnModificarRecompensa.addActionListener(e -> {
            int filaSeleccionada = tablaRecompensa.getSelectedRow();

            if (filaSeleccionada == -1) {
                JOptionPane.showMessageDialog(this, "Debes seleccionar una fila", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Recompensa recompensaSeleccionada = recompensaControlador.obtenerRecompensa(filaSeleccionada);

            ModificarRecompensa modificarRecompensa = new ModificarRecompensa(recompensaControlador, productoControlador, recompensaSeleccionada);
            modificarRecompensa.setVisible(true);
        });

        btnEliminarRecompensa = new JButton("Eliminar Recompensa");
        btnEliminarRecompensa.setBounds(270, 320, 200, 40);

        btnEliminarRecompensa.addActionListener(e -> {
            int filaSeleccionada = tablaRecompensa.getSelectedRow();

            if (filaSeleccionada == -1) {
                JOptionPane.showMessageDialog(this, "Debes seleccionar una fila", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Recompensa recompensaSeleccionada = recompensaControlador.obtenerRecompensa(filaSeleccionada);
            recompensaControlador.eliminarRecompensa(recompensaSeleccionada);
            JOptionPane.showMessageDialog(this, "Recompensa " + recompensaSeleccionada.getPremio() + " eliminada correctamente");
            setVisible(false);
        });

        panel.add(scrollTabla);

        // Solo administradores pueden modificar o eliminar recompensas
        if (usuarioLogeado.puedeGestionarCatalogo()) {
            panel.add(btnModificarRecompensa);
            panel.add(btnEliminarRecompensa);
        }

        add(panel);
        cargarRecompensas();
    }
}
