package puntograno.vistas;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import puntograno.controladores.ProductoControlador;
import puntograno.controladores.RecompensaControlador;
import puntograno.modelos.Recompensa;
import puntograno.modelos.Usuario;

public class VerRecompensas extends JInternalFrame {

    private RecompensaControlador recompensaControlador;
    private ProductoControlador productoControlador;

    private JPanel panel;
    private JLabel lblTitulo;
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
        panel.setBackground(new Color(245, 240, 235));

        lblTitulo = new JLabel("Recompensas Registradas");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        lblTitulo.setForeground(new Color(70, 45, 30));
        lblTitulo.setBounds(40, 10, 550, 30);

        modeloTabla = new DefaultTableModel();
        modeloTabla.addColumn("Premio");
        modeloTabla.addColumn("Puntos");
        modeloTabla.addColumn("Disponible");
        tablaRecompensa = new JTable(modeloTabla);

        tablaRecompensa.setRowHeight(28);
        tablaRecompensa.setFont(new Font("Arial", Font.PLAIN, 13));
        tablaRecompensa.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));
        tablaRecompensa.getTableHeader().setForeground(new Color(70, 45, 30));
        tablaRecompensa.getTableHeader().setReorderingAllowed(false);
        tablaRecompensa.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tablaRecompensa.setSelectionBackground(new Color(230, 215, 195));
        tablaRecompensa.setSelectionForeground(Color.BLACK);

        scrollTabla = new JScrollPane(tablaRecompensa);
        scrollTabla.getViewport().setBackground(Color.WHITE);
        scrollTabla.setBorder(BorderFactory.createLineBorder(new Color(180, 160, 140)));
        scrollTabla.setBounds(40, 45, 550, 255);

        btnModificarRecompensa = new JButton("Modificar Recompensa");
        btnModificarRecompensa.setBackground(new Color(240, 235, 230));
        btnModificarRecompensa.setForeground(new Color(70, 45, 30));
        btnModificarRecompensa.setFont(new Font("Arial", Font.BOLD, 14));
        btnModificarRecompensa.setFocusPainted(false);
        btnModificarRecompensa.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnModificarRecompensa.setBorder(BorderFactory.createLineBorder(new Color(180, 160, 140)));
        btnModificarRecompensa.setToolTipText("Modificar la recompensa seleccionada");
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
        btnEliminarRecompensa.setBackground(new Color(250, 240, 240));
        btnEliminarRecompensa.setForeground(new Color(160, 50, 40));
        btnEliminarRecompensa.setFont(new Font("Arial", Font.BOLD, 14));
        btnEliminarRecompensa.setFocusPainted(false);
        btnEliminarRecompensa.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnEliminarRecompensa.setBorder(BorderFactory.createLineBorder(new Color(200, 140, 130)));
        btnEliminarRecompensa.setToolTipText("Eliminar la recompensa seleccionada");
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

        panel.add(lblTitulo);
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
