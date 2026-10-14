package controladores;

import java.util.ArrayList;
import modelos.Cliente;

public class ClienteControlador {

    private ArrayList<Cliente> clientes;

    public ClienteControlador() {
        clientes = new ArrayList<>();
    }

    // solo para pruebas
    public void seedClientes() {
        Cliente cliente1 = new Cliente("12.123.456-7", "Francisco", "+56987465432");
        Cliente cliente2 = new Cliente("14.246.654-8", "Javier", "+56975239514");
        Cliente cliente3 = new Cliente("21.346.754-2", "Fernanda", "+56965326497");
        Cliente cliente4 = new Cliente("18.853.423-7", "Camila", "+56985236542");

        clientes.add(cliente1);
        clientes.add(cliente2);
        clientes.add(cliente3);
        clientes.add(cliente4);
    }

    public void registrarCliente(Cliente cliente) {
        clientes.add(cliente);
    }

    public ArrayList<Cliente> getClientes() {
        return clientes;
    }

    public Cliente obtenerCliente(int idCliente) {
        return clientes.get(idCliente);
    }

    public Cliente obtenerClientePorRut(String rutCliente) {
        for (Cliente cliente : clientes) {
            if (cliente.getRut().equals(rutCliente)) {
                return cliente;
            }
        }
        return null;
    }

    public void editarCliente(Cliente cliente, String nombre, String telefono) {
        cliente.setNombre(nombre);
        cliente.setTelefono(telefono);
    }

    public void eliminarCliente(Cliente cliente) {
        this.clientes.remove(cliente);
    }
}
