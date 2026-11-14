package puntograno.controladores;

import java.sql.SQLException;
import java.util.ArrayList;
import puntograno.dao.ClienteDAO;
import puntograno.modelos.Cliente;

public class ClienteControlador {

    private ArrayList<Cliente> clientes;
    private ClienteDAO clienteDAO;

    public ClienteControlador() {
        clientes = new ArrayList<>();
        clienteDAO = new ClienteDAO();
    }

    // solo para pruebas
    public void seedClientes() {
        Cliente cliente1 = new Cliente("12.123.456-7", "Francisco", "+56987465432", "francisco@email.com");
        Cliente cliente2 = new Cliente("14.246.654-8", "Javier", "+56975239514", "javier@email.com");
        Cliente cliente3 = new Cliente("21.346.754-2", "Fernanda", "+56965326497", "fernanda@email.com");
        Cliente cliente4 = new Cliente("18.853.423-7", "Camila", "+56985236542", "camila@email.com");

        clientes.add(cliente1);
        clientes.add(cliente2);
        clientes.add(cliente3);
        clientes.add(cliente4);
    }

    public boolean registrarCliente(Cliente cliente) throws SQLException {
        boolean registrado = clienteDAO.registrarCliente(cliente);

        if (registrado) {
            clientes.add(cliente);
        }
        return registrado;
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

    public void editarCliente(Cliente cliente, String rut, String nombre, String telefono, String email) {
        cliente.setRut(rut);
        cliente.setNombre(nombre);
        cliente.setTelefono(telefono);
        cliente.setEmail(email);
    }

    public void eliminarCliente(Cliente cliente) {
        this.clientes.remove(cliente);
    }
}
