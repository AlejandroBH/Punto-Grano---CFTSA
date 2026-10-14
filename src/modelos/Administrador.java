package modelos;

public class Administrador extends Usuario {

    private String clave;

    public Administrador(String rut, String nombre, String telefono, String clave) {
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
        System.out.println("Mostrando datos de Administrador");
    }

    @Override
    public boolean puedeGestionarCatalogo() {
        return true;
    }
}
