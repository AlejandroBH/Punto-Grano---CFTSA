package puntograno.modelos;

public class Cliente extends Usuario {

    private TarjetaFidelidad tarjeta; // Cliente - TarjetaFidelidad 1:1 

    public Cliente(String rut, String nombre, String telefono) {
        super(rut, nombre, telefono);
        this.tarjeta = new TarjetaFidelidad();
    }

    public TarjetaFidelidad getTarjeta() {
        return tarjeta;
    }

    // Accede a atributo tarjeta y muestra cuántos puntos y qué nivel tiene actualmente el cliente.
    public int consultarPuntos() {
        return this.tarjeta.getPuntosAcumulados();
    }

    @Override
    public void mostrarDatosPerfil() {
        System.out.println("Mostrando datos de Cliente");
    }

    @Override
    public boolean puedeGestionarCatalogo() {
        return false;
    }
}
