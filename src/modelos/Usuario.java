package modelos;

import java.util.UUID;

public class Usuario {

    private UUID idUsuario;
    private String rut;
    private String nombre;
    private String telefono;

    public Usuario(String rut, String nombre, String telefono) {
        this.idUsuario = UUID.randomUUID();
        this.rut = rut;
        this.nombre = nombre;
        this.telefono = telefono;
    }

    public UUID getIdUsuario() {
        return idUsuario;
    }

    public String getRut() {
        return rut;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public void mostrarDatosPerfil() {
        System.out.println("Mostrando datos de Usuario");
    }

    public boolean puedeGestionarCatalogo() {
        return false;
    }
}
