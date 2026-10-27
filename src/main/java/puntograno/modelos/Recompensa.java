package puntograno.modelos;

import java.util.UUID;

public class Recompensa {

    private UUID idRecompensa;
    private Producto premio; //Recompensa - Producto 1:1
    private int costoPuntos;
    private boolean disponible;

    public Recompensa(Producto premio, int costoPuntos, boolean disponible) {
        this.idRecompensa = UUID.randomUUID();
        this.premio = premio;
        this.costoPuntos = costoPuntos;
        this.disponible = disponible;
    }

    public UUID getIdRecompensa() {
        return idRecompensa;
    }

    public String getPremio() {
        return premio.getNombre();
    }

    public void setPremio(Producto premio) {
        this.premio = premio;
    }

    public int getCostoPuntos() {
        return costoPuntos;
    }

    public void setCostoPuntos(int costoPuntos) {
        this.costoPuntos = costoPuntos;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }
}
