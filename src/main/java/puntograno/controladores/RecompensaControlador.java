package puntograno.controladores;

import java.util.ArrayList;
import puntograno.modelos.Cliente;
import puntograno.modelos.Producto;
import puntograno.modelos.Recompensa;

public class RecompensaControlador {

    private ArrayList<Recompensa> recompesas;

    public RecompensaControlador() {
        recompesas = new ArrayList<>();
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

    public void registrarRecompensa(Recompensa recompensa) {
        recompesas.add(recompensa);
    }

    public ArrayList<Recompensa> getRecompensas() {
        return recompesas;
    }

    public Recompensa obtenerRecompensa(int idRecompensa) {
        return recompesas.get(idRecompensa);
    }

    public void editarRecompensa(Recompensa recompensa, Producto producto, String premio, int costoPuntos, Boolean disponible) {
        recompensa.setPremio(producto);
        recompensa.setCostoPuntos(costoPuntos);
        recompensa.setDisponible(disponible);
    }

    public void eliminarRecompensa(Recompensa recompensa) {
        this.recompesas.remove(recompensa);
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
