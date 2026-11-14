package puntograno.controladores;

import java.sql.SQLException;
import java.util.ArrayList;
import puntograno.dao.RecompensaDAO;
import puntograno.modelos.Cliente;
import puntograno.modelos.Producto;
import puntograno.modelos.Recompensa;

public class RecompensaControlador {

    private ArrayList<Recompensa> recompesas;
    private RecompensaDAO recompensaDAO;

    public RecompensaControlador() {
        recompesas = new ArrayList<>();
        recompensaDAO = new RecompensaDAO();
    }

    // solo para pruebas
    public void seedRecompensas(ArrayList<Producto> premios) {
        Recompensa recompensa1 = new Recompensa(premios.get(1), 25, true);
        Recompensa recompensa2 = new Recompensa(premios.get(2), 50, true);
        Recompensa recompensa3 = new Recompensa(premios.get(3), 100, true);

        recompesas.add(recompensa1);
        recompesas.add(recompensa2);
        recompesas.add(recompensa3);
    }

    public boolean registrarRecompensa(Recompensa recompensa) throws SQLException {
        boolean registrado = recompensaDAO.registrarRecompensa(recompensa);

        if (registrado) {
            recompesas.add(recompensa);
        }
        return registrado;
    }

    public ArrayList<Recompensa> getRecompensas() {
        return recompesas;
    }

    public ArrayList<Recompensa> listarRecompensas() throws SQLException {
        this.recompesas = recompensaDAO.listarRecompensas();
        return this.recompesas;
    }

    public Recompensa obtenerRecompensa(int idRecompensa) {
        return recompesas.get(idRecompensa);
    }

    public boolean actualizarRecompensa(Recompensa recompensa) throws SQLException {
        return recompensaDAO.actualizarRecompensa(recompensa);
    }

    public void editarRecompensa(Recompensa recompensa, Producto producto, String premio, int costoPuntos, Boolean disponible) {
        recompensa.setPremio(producto);
        recompensa.setCostoPuntos(costoPuntos);
        recompensa.setDisponible(disponible);
    }

    public boolean eliminarRecompensa(Recompensa recompensa) throws SQLException {
        boolean eliminado = recompensaDAO.eliminarRecompensa(recompensa.getIdRecompensa().toString());

        if (eliminado) {
            this.recompesas.remove(recompensa);
        }
        return eliminado;
    }

    public void canjearRecompensa(Cliente cliente, Recompensa recompensa) {
        cliente.getTarjeta().canjearPuntos(recompensa.getCostoPuntos());

        // Si la recompensa existe actualiza el stock (descuenta -1)
        if (recompensa.getProducto() != null) {
            Producto producto = recompensa.getProducto();
            producto.setStock(producto.getStock() - 1);

            // con esto impido que el stock baje de cero y hago que actualice el estado de disponibilidad automaticamente
            if (producto.getStock() <= 0) {
                producto.setStock(0);
                producto.setDisponible(false);
            }
        }
    }
}
