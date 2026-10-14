package vistas;

import controladores.ClienteControlador;
import controladores.PedidoControlador;
import controladores.ProductoControlador;
import controladores.RecompensaControlador;
import javax.swing.*;
import modelos.Usuario;

public class VistaPrincipal extends JFrame {

    private ClienteControlador clienteControlador;
    private ProductoControlador productoControlador;
    private RecompensaControlador recompensaControlador;
    private PedidoControlador pedidoControlador;

    private JDesktopPane escritorio;
    private JMenuBar barraMenu;
    private JMenu menuClientes;
    private JMenuItem itemNuevoCliente;
    private JMenuItem itemVerClientes;
    private JMenu menuProductos;
    private JMenuItem itemNuevoProducto;
    private JMenuItem itemVerProductos;

    private JMenu menuRecompensa;
    private JMenuItem itemNuevaRecompensa;
    private JMenuItem itemVerRecompensas;

    private JMenu menuOperaciones;
    private JMenuItem itemNuevaVenta;
    private JMenuItem itemProcesarVentas;
    private JMenuItem itemCanjearPuntos;

    public VistaPrincipal(Usuario usuarioLogeado) {
        this.clienteControlador = new ClienteControlador();
        this.productoControlador = new ProductoControlador();
        this.recompensaControlador = new RecompensaControlador();
        this.pedidoControlador = new PedidoControlador();

        // Rellena tablas con objetos iniciales (para pruebas)
        productoControlador.seedProductos();
        clienteControlador.seedClientes();
        recompensaControlador.seedRecompensas(productoControlador.getProductos());

        setTitle("Bienvenido Sr. " + usuarioLogeado.getNombre() + " a PuntoGrano - Sistema de Cafeteria");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        escritorio = new JDesktopPane();
        barraMenu = new JMenuBar();

        menuClientes = new JMenu("Clientes");
        itemNuevoCliente = new JMenuItem("Nuevo Cliente");
        itemVerClientes = new JMenuItem("Ver Clientes");
        menuClientes.add(itemNuevoCliente);
        menuClientes.add(itemVerClientes);

        itemNuevoCliente.addActionListener(e -> {
            NuevoCliente nuevoCliente = new NuevoCliente(clienteControlador);
            nuevoCliente.setVisible(true);
        });

        itemVerClientes.addActionListener(e -> {
            VerClientes verClientes = new VerClientes(usuarioLogeado, clienteControlador);
            escritorio.add(verClientes);
            verClientes.setVisible(true);
        });

        menuProductos = new JMenu("Productos");
        itemNuevoProducto = new JMenuItem("Nuevo Producto");
        itemVerProductos = new JMenuItem("Ver Productos");
        menuProductos.add(itemNuevoProducto);
        menuProductos.add(itemVerProductos);

        itemNuevoProducto.addActionListener(e -> {
            if (usuarioLogeado.puedeGestionarCatalogo()) {
                NuevoProducto nuevoProducto = new NuevoProducto(productoControlador);
                nuevoProducto.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Solo administradores pueden usar esta opcion", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        });

        itemVerProductos.addActionListener(e -> {
            VerProductos verProductos = new VerProductos(usuarioLogeado, productoControlador);
            escritorio.add(verProductos);
            verProductos.setVisible(true);
        });

        menuRecompensa = new JMenu("Recompensa");
        itemNuevaRecompensa = new JMenuItem("Nueva Recompensa");
        itemVerRecompensas = new JMenuItem("Ver Recompensas");
        menuRecompensa.add(itemNuevaRecompensa);
        menuRecompensa.add(itemVerRecompensas);

        itemNuevaRecompensa.addActionListener(e -> {
            if (usuarioLogeado.puedeGestionarCatalogo()) {
                NuevaRecompensa nuevaRecompensa = new NuevaRecompensa(recompensaControlador, productoControlador);
                nuevaRecompensa.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Solo administradores pueden usar esta opcion", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        });

        itemVerRecompensas.addActionListener(e -> {
            VerRecompensas verRecompensas = new VerRecompensas(usuarioLogeado, recompensaControlador, productoControlador);
            escritorio.add(verRecompensas);
            verRecompensas.setVisible(true);
        });

        menuOperaciones = new JMenu("Operaciones");
        itemNuevaVenta = new JMenuItem("Nueva Venta");
        itemProcesarVentas = new JMenuItem("Procesar Ventas");
        itemCanjearPuntos = new JMenuItem("Canjear Puntos");
        menuOperaciones.add(itemNuevaVenta);
        menuOperaciones.add(itemProcesarVentas);
        menuOperaciones.add(itemCanjearPuntos);

        itemNuevaVenta.addActionListener(e -> {
            NuevaVenta nuevaVenta = new NuevaVenta(pedidoControlador, productoControlador, clienteControlador);
            nuevaVenta.setVisible(true);
        });

        itemProcesarVentas.addActionListener(e -> {
            VerVentas verVentas = new VerVentas(usuarioLogeado, pedidoControlador);
            escritorio.add(verVentas);
            verVentas.setVisible(true);
        });

        itemCanjearPuntos.addActionListener(e -> {
            CanjearPuntos canjearPuntos = new CanjearPuntos(recompensaControlador, clienteControlador);
            canjearPuntos.setVisible(true);
        });

        barraMenu.add(menuClientes);
        barraMenu.add(menuProductos);
        barraMenu.add(menuRecompensa);
        barraMenu.add(menuOperaciones);

        setJMenuBar(barraMenu);
        add(escritorio);
    }
}
