package modelos;

public class Operador extends Usuario {

    private String clave;

    public Operador(String rut, String nombre, String telefono, String clave) {
        super(rut, nombre, telefono);
        this.clave = clave;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    @Override
    public void mostrarDatosPerfil() {
        System.out.println("Mostrando datos de Operador");
    }

    @Override
    public boolean puedeGestionarCatalogo() {
        return false;
    }
}
