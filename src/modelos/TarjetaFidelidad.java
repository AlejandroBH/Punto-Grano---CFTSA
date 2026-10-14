package modelos;

import java.util.UUID;

public class TarjetaFidelidad {

    private UUID codigoTarjeta;
    private int puntosAcumulados;
    private String nivel; // "Inicial", "Bronce", "Plata", "Oro"

    public TarjetaFidelidad() {
        this.codigoTarjeta = UUID.randomUUID();
        this.puntosAcumulados = 0;
        this.nivel = "Inicial";
    }

    public UUID getCodigoTarjeta() {
        return codigoTarjeta;
    }

    public int getPuntosAcumulados() {
        return puntosAcumulados;
    }

    public void setPuntosAcumulados(int puntosAcumulados) {
        if (puntosAcumulados > 0) {
            this.puntosAcumulados += puntosAcumulados;
        }
    }

    public String getNivel() {
        return nivel;
    }

    public void setNivel(String nivel) {
        if ("Inicial".equals(nivel) || "Bronce".equals(nivel) || "Plata".equals(nivel) || "Oro".equals(nivel)) {
            this.nivel = nivel;
        }
    }

    // Incrementa puntos acumulados y actualiza el nivel si supera cierto umbral (ej. más de 100 puntos).
    public void actualizarProgreso(int puntosGanados) {
        this.setPuntosAcumulados(puntosGanados);

        if (this.getPuntosAcumulados() >= 300) {
            this.setNivel("Oro");
        } else if (this.getPuntosAcumulados() >= 200) {
            this.setNivel("Plata");
        } else if (this.getPuntosAcumulados() >= 100) {
            this.setNivel("Bronce");
        } else {
            this.setNivel("Inicial");
        }
    }

    // Verifica si puntosAcumulados >= puntosRequeridos; si se cumple, descuenta los puntos.
    public void canjearPuntos(int puntosRequeridos) {
        if (puntosRequeridos <= this.puntosAcumulados) {
            this.puntosAcumulados -= puntosRequeridos;
        }
    }
}
