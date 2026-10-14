package vistas;

import controladores.ClienteControlador;
import controladores.RecompensaControlador;
import java.util.ArrayList;
import javax.swing.*;
import modelos.Cliente;
import modelos.Recompensa;

public class CanjearPuntos extends JFrame {

    private RecompensaControlador recompensaControlador;
    private ClienteControlador clienteControlador;

    private ArrayList<String> recompensas;

    private JPanel panel;
    private JLabel lblRut;
    private JTextField txtRut;
    private JLabel lblRecompensa;
    private JComboBox comRecompensa;
    private JButton btnCanjear;

    private void cargarRecompensas() {
        for (Recompensa recompensa : recompensaControlador.getRecompensas()) {
            if (recompensa.isDisponible()) {
                comRecompensa.addItem(recompensa.getPremio() + " (" + recompensa.getCostoPuntos() + " puntos)");
            }
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
        lblRut = new JLabel("Rut Cliente");
        txtRut = new JTextField();
        lblRecompensa = new JLabel("Recompensa");
        comRecompensa = new JComboBox<>();
        cargarRecompensas();
        btnCanjear = new JButton("Canjear");

        lblRut.setBounds(70, 50, 100, 30);
        txtRut.setBounds(180, 50, 220, 30);
        lblRecompensa.setBounds(70, 100, 100, 30);
        comRecompensa.setBounds(180, 100, 220, 30);
        btnCanjear.setBounds(180, 150, 220, 35);

        btnCanjear.addActionListener(e -> {
            String rut = txtRut.getText();
            int indice = comRecompensa.getSelectedIndex();

            if (indice == -1 || comRecompensa.getSelectedItem() == null) {
                JOptionPane.showMessageDialog(this, "No existen productos en el catalogo", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Cliente clienteEncontrado = clienteControlador.obtenerClientePorRut(rut);
            Recompensa recompensaSeleccionada = recompensaControlador.obtenerRecompensa(indice);

            if (rut.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Debes completar todos los campos", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            } else if (clienteEncontrado == null) {
                JOptionPane.showMessageDialog(this, "El cliente no se encuentra registrado", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            } else if (clienteEncontrado.getTarjeta().getPuntosAcumulados() < recompensaSeleccionada.getCostoPuntos()) {
                JOptionPane.showMessageDialog(this, "El cliente no tiene puntos suficientes", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            recompensaControlador.canjearRecompensa(clienteEncontrado, recompensaSeleccionada);
            JOptionPane.showMessageDialog(this, "Puntos canjeados correctamente por: " + clienteEncontrado.getNombre());
            setVisible(false);
        });

        panel.add(lblRut);
        panel.add(txtRut);
        panel.add(lblRecompensa);
        panel.add(comRecompensa);
        panel.add(btnCanjear);
        add(panel);
    }
}
