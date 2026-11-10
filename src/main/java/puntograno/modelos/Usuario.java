package puntograno.modelos;

import java.util.Date;
import java.util.UUID;

public class Usuario {

    private UUID idUsuario;
    private String rut;
    private String nombre;
    private String telefono;
    private String email;
    private Date fechaRegistro;

    public Usuario(String rut, String nombre, String telefono, String email) {
        this.idUsuario = UUID.randomUUID();
        this.rut = rut;
        this.nombre = nombre;
        this.telefono = telefono;
        this.email = email;
        this.fechaRegistro = new Date();
    }

    public UUID getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(UUID idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getRut() {
        return rut;
    }

    public void setRut(String rut) {
        this.rut = rut;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void mostrarDatosPerfil() {
        System.out.println("Mostrando datos de Usuario");
    }

    public boolean puedeGestionarCatalogo() {
        return false;
    }
}
