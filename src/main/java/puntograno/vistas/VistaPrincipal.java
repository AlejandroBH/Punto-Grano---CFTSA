package puntograno.vistas;

import puntograno.controladores.ClienteControlador;
import puntograno.controladores.PedidoControlador;
import puntograno.controladores.ProductoControlador;
import puntograno.controladores.RecompensaControlador;
import javax.swing.*;
import puntograno.modelos.Usuario;

public class VistaPrincipal extends JFrame {

    private ClienteControlador clienteControlador;
    private ProductoControlador productoControlador;
    private RecompensaControlador recompensaControlador;
    private PedidoControlador pedidoControlador;

    private JDesktopPane escritorio;
    private JMenuBar barraMenu;

    // Menu clientes
    private JMenu menuClientes;
    private ImageIcon iconoNuevoCliente;
    private ImageIcon iconoVerClientes;
    private JMenuItem itemNuevoCliente;
    private JMenuItem itemVerClientes;

    // Menu productos
    private JMenu menuProductos;
    private ImageIcon iconoNuevoProducto;
    private ImageIcon iconoVerProductos;
    private JMenuItem itemNuevoProducto;
    private JMenuItem itemVerProductos;

    // Menu recompensas
    private JMenu menuRecompensa;
    private ImageIcon iconoNuevaRecompensa;
    private ImageIcon iconoVerRecompensas;
    private JMenuItem itemNuevaRecompensa;
    private JMenuItem itemVerRecompensas;

    // Menu operaciones
    private JMenu menuOperaciones;
    private ImageIcon iconoNuevaVenta;
    private ImageIcon iconoProcesarVentas;
    private ImageIcon iconoCanjearPuntos;
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

        // Menu clientes
        iconoNuevoCliente = new ImageIcon(getClass().getResource("/puntograno/imagenes/icono_nuevo-cliente.png"));
        iconoVerClientes = new ImageIcon(getClass().getResource("/puntograno/imagenes/icono_ver-clientes.png"));
        menuClientes = new JMenu("Clientes");
        itemNuevoCliente = new JMenuItem("Nuevo Cliente");
        itemVerClientes = new JMenuItem("Ver Clientes");
        itemNuevoCliente.setIcon(iconoNuevoCliente);
        itemVerClientes.setIcon(iconoVerClientes);
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

        // Menu productos
        iconoNuevoProducto = new ImageIcon(getClass().getResource("/puntograno/imagenes/icono_nuevo-producto.png"));
        iconoVerProductos = new ImageIcon(getClass().getResource("/puntograno/imagenes/icono_ver-productos.png"));
        menuProductos = new JMenu("Productos");
        itemNuevoProducto = new JMenuItem("Nuevo Producto");
        itemVerProductos = new JMenuItem("Ver Productos");
        itemNuevoProducto.setIcon(iconoNuevoProducto);
        itemVerProductos.setIcon(iconoVerProductos);
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

        // Menu recompensa
        iconoNuevaRecompensa = new ImageIcon(getClass().getResource("/puntograno/imagenes/icono_nueva-recompensa.png"));
        iconoVerRecompensas = new ImageIcon(getClass().getResource("/puntograno/imagenes/icono_ver-recompensas.png"));
        menuRecompensa = new JMenu("Recompensa");
        itemNuevaRecompensa = new JMenuItem("Nueva Recompensa");
        itemVerRecompensas = new JMenuItem("Ver Recompensas");
        itemNuevaRecompensa.setIcon(iconoNuevaRecompensa);
        itemVerRecompensas.setIcon(iconoVerRecompensas);
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

        // Menu operaciones
        iconoNuevaVenta = new ImageIcon(getClass().getResource("/puntograno/imagenes/icono_nueva-venta.png"));
        iconoProcesarVentas = new ImageIcon(getClass().getResource("/puntograno/imagenes/icono_procesar-ventas.png"));
        iconoCanjearPuntos = new ImageIcon(getClass().getResource("/puntograno/imagenes/icono_canjear-puntos.png"));
        menuOperaciones = new JMenu("Operaciones");
        itemNuevaVenta = new JMenuItem("Nueva Venta");
        itemProcesarVentas = new JMenuItem("Procesar Ventas");
        itemCanjearPuntos = new JMenuItem("Canjear Puntos");
        itemNuevaVenta.setIcon(iconoNuevaVenta);
        itemProcesarVentas.setIcon(iconoProcesarVentas);
        itemCanjearPuntos.setIcon(iconoCanjearPuntos);
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
