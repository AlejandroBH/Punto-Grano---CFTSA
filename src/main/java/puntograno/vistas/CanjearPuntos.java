package puntograno.vistas;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.util.ArrayList;
import javax.swing.*;
import puntograno.controladores.ClienteControlador;
import puntograno.controladores.RecompensaControlador;
import puntograno.modelos.Cliente;
import puntograno.modelos.Recompensa;

public class CanjearPuntos extends JFrame {

    private RecompensaControlador recompensaControlador;
    private ClienteControlador clienteControlador;

    private ArrayList<Recompensa> listaRecompensas; // carga local para evitar incontinencia con indices cuando hay productos no disponibles

    private JPanel panel;
    private JLabel lblTitulo;
    private JLabel lblCliente;
    private JComboBox comCliente;
    private JLabel lblRecompensa;
    private JComboBox comRecompensa;
    private JButton btnCanjear;

    private void cargarRecompensas() {
        listaRecompensas = new ArrayList<>();
        comRecompensa.removeAllItems();

        for (Recompensa recompensa : recompensaControlador.getRecompensas()) {
            if (recompensa.isDisponible()) {
                listaRecompensas.add(recompensa);
                comRecompensa.addItem(recompensa.getPremio() + " (" + recompensa.getCostoPuntos() + " puntos)");
            }
        }
    }

    private void cargarClientes() {
        for (Cliente cliente : clienteControlador.getClientes()) {
            comCliente.addItem(cliente.getNombre() + " (" + cliente.getRut() + ")");
        }
    }

    public CanjearPuntos(RecompensaControlador recompensaControlador, ClienteControlador clienteControlador) {
        this.recompensaControlador = recompensaControlador;
        this.clienteControlador = clienteControlador;

        setTitle("Canjear Puntos");
        setSize(500, 270);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);

        panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(new Color(245, 240, 235));

        lblTitulo = new JLabel("Canjear Puntos de Fidelidad");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        lblTitulo.setForeground(new Color(70, 45, 30));
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);

        lblCliente = new JLabel("Cliente");
        lblCliente.setFont(new Font("Arial", Font.BOLD, 13));
        lblCliente.setForeground(new Color(70, 45, 30));

        comCliente = new JComboBox<>();
        comCliente.setFont(new Font("Arial", Font.PLAIN, 14));
        comCliente.setBackground(Color.WHITE);
        cargarClientes();

        lblRecompensa = new JLabel("Recompensa");
        lblRecompensa.setFont(new Font("Arial", Font.BOLD, 13));
        lblRecompensa.setForeground(new Color(70, 45, 30));

        comRecompensa = new JComboBox<>();
        comRecompensa.setFont(new Font("Arial", Font.PLAIN, 14));
        comRecompensa.setBackground(Color.WHITE);
        cargarRecompensas();

        btnCanjear = new JButton("Canjear");
        btnCanjear.setBackground(new Color(240, 235, 230));
        btnCanjear.setForeground(new Color(70, 45, 30));
        btnCanjear.setFont(new Font("Arial", Font.BOLD, 14));
        btnCanjear.setFocusPainted(false);
        btnCanjear.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnCanjear.setBorder(BorderFactory.createLineBorder(new Color(180, 160, 140)));
        btnCanjear.setToolTipText("Canjear la recompensa seleccionada");

        lblTitulo.setBounds(70, 15, 330, 25);
        lblCliente.setBounds(70, 50, 100, 30);
        comCliente.setBounds(180, 50, 220, 30);
        lblRecompensa.setBounds(70, 100, 100, 30);
        comRecompensa.setBounds(180, 100, 220, 30);
        btnCanjear.setBounds(180, 155, 220, 35);

        btnCanjear.addActionListener(e -> {
            int clienteIndice = comCliente.getSelectedIndex();
            int recompensaIndice = comRecompensa.getSelectedIndex();

            if (recompensaIndice == -1 || comRecompensa.getSelectedItem() == null) {
                JOptionPane.showMessageDialog(this, "No existen productos en el catalogo", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (clienteIndice == -1 || comCliente.getSelectedItem() == null) {
                JOptionPane.showMessageDialog(this, "No existen clientes registrados", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Cliente clienteEncontrado = clienteControlador.obtenerCliente(clienteIndice);
            Recompensa recompensaSeleccionada = listaRecompensas.get(recompensaIndice);

            if (recompensaSeleccionada.getProducto() != null && recompensaSeleccionada.getProducto().getStock() <= 0) {
                JOptionPane.showMessageDialog(this, "No hay stock disponible para esta recompensa", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (clienteEncontrado.getTarjeta().getPuntosAcumulados() < recompensaSeleccionada.getCostoPuntos()) {
                JOptionPane.showMessageDialog(this, "El cliente no tiene puntos suficientes", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            recompensaControlador.canjearRecompensa(clienteEncontrado, recompensaSeleccionada);
            JOptionPane.showMessageDialog(this, "Puntos canjeados correctamente por: " + clienteEncontrado.getNombre());
            setVisible(false);
        });

        panel.add(lblTitulo);
        panel.add(lblCliente);
        panel.add(comCliente);
        panel.add(lblRecompensa);
        panel.add(comRecompensa);
        panel.add(btnCanjear);
        add(panel);
    }
}
