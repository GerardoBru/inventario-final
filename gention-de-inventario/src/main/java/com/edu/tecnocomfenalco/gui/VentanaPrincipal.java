package com.edu.tecnocomfenalco.gui;

import com.edu.tecnocomfenalco.controlador.ControladorProducto;
import com.edu.tecnocomfenalco.controlador.ControladorUsuario;
import com.edu.tecnocomfenalco.controlador.ControladorMovimiento;
import com.edu.tecnocomfenalco.modelo.Usuario;
import javax.swing.*;

/**
 * Ventana principal de la aplicación de Gestión de Inventario.
 * Esta ventana contiene el menú principal y gestiona la navegación entre las diferentes secciones.
 * 
 * @author chancro
 */
public class VentanaPrincipal extends JFrame {
    private static final long serialVersionUID = 1L;
    
    private ControladorProducto controladorProducto;
    private ControladorUsuario controladorUsuario;
    private ControladorMovimiento controladorMovimiento;
    private Usuario usuarioActual;
    
    private JMenuBar menuBar;
    private JTabbedPane tabbedPane;
    
    /**
     * Constructor que inicializa la ventana principal.
     */
    public VentanaPrincipal() {
        setTitle("Sistema de Gestión de Inventario - STF Group");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 700);
        setLocationRelativeTo(null);
        setResizable(true);
        
        // Inicializar controladores
        inicializarControladores();
        
        // Mostrar diálogo de login
        mostrarLogin();
    }
    
    /**
     * Inicializa los controladores del sistema.
     */
    private void inicializarControladores() {
        controladorProducto = new ControladorProducto();
        controladorUsuario = new ControladorUsuario();
        controladorMovimiento = new ControladorMovimiento(controladorProducto);
        
        try {
            controladorProducto.cargarProductos();
            controladorUsuario.cargarUsuarios();
            controladorMovimiento.cargarMovimientos();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al cargar datos: " + e.getMessage(), 
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Muestra el diálogo de login.
     */
    private void mostrarLogin() {
        DialogoLogin dialogoLogin = new DialogoLogin(this, controladorUsuario);
        usuarioActual = dialogoLogin.obtenerUsuario();
        
        if (usuarioActual == null) {
            System.exit(0);
        } else {
            construirInterfaz();
        }
    }
    
    /**
     * Construye la interfaz principal basada en el rol del usuario.
     */
    private void construirInterfaz() {
        getContentPane().removeAll();
        
        // Crear barra de menú
        crearMenuBar();
        setJMenuBar(menuBar);
        
        // Crear pestaña con tabs
        tabbedPane = new JTabbedPane();
        
        // Agregar panels según los permisos del usuario
        if (usuarioActual.getRol() == Usuario.Rol.ADMINISTRADOR) {
            tabbedPane.addTab("Productos", new PanelProductos(controladorProducto, usuarioActual));
            tabbedPane.addTab("Movimientos", new PanelMovimientos(controladorProducto, controladorMovimiento, usuarioActual));
            tabbedPane.addTab("Reportes", new PanelReportes(controladorProducto, controladorMovimiento));
            tabbedPane.addTab("Usuarios", new PanelUsuarios(controladorUsuario));
        } else if (usuarioActual.getRol() == Usuario.Rol.BODEGUERO) {
            tabbedPane.addTab("Productos", new PanelProductos(controladorProducto, usuarioActual));
        } else if (usuarioActual.getRol() == Usuario.Rol.ENCARGADO) {
            tabbedPane.addTab("Productos", new PanelProductos(controladorProducto, usuarioActual));
            tabbedPane.addTab("Movimientos", new PanelMovimientos(controladorProducto, controladorMovimiento, usuarioActual));
        }
        
        add(tabbedPane);
        revalidate();
        repaint();
    }
    
    /**
     * Crea la barra de menú.
     */
    private void crearMenuBar() {
        menuBar = new JMenuBar();
        
        // Menú Archivo
        JMenu menuArchivo = new JMenu("Archivo");
        JMenuItem itemSalir = new JMenuItem("Salir");
        itemSalir.addActionListener(e -> salir());
        menuArchivo.add(itemSalir);
        
        // Menú Sesión
        JMenu menuSesion = new JMenu("Sesión");
        JMenuItem itemCerrarSesion = new JMenuItem("Cerrar Sesión");
        itemCerrarSesion.addActionListener(e -> cerrarSesion());
        JMenuItem itemVerPerfil = new JMenuItem("Ver Perfil");
        itemVerPerfil.addActionListener(e -> mostrarPerfil());
        menuSesion.add(itemVerPerfil);
        menuSesion.addSeparator();
        menuSesion.add(itemCerrarSesion);
        
        // Menú Ayuda
        JMenu menuAyuda = new JMenu("Ayuda");
        JMenuItem itemAcerca = new JMenuItem("Acerca de");
        itemAcerca.addActionListener(e -> mostrarAcerca());
        menuAyuda.add(itemAcerca);
        
        menuBar.add(menuArchivo);
        menuBar.add(menuSesion);
        menuBar.add(menuAyuda);
    }
    
    /**
     * Muestra el perfil del usuario actual.
     */
    private void mostrarPerfil() {
        StringBuilder perfil = new StringBuilder();
        perfil.append("Usuario: ").append(usuarioActual.getNombre()).append("\n");
        perfil.append("Correo: ").append(usuarioActual.getCorreo()).append("\n");
        perfil.append("Rol: ").append(usuarioActual.getRol().getDescripcion()).append("\n\n");
        perfil.append("Permisos:\n");
        perfil.append(usuarioActual.obtenerPermisos());
        
        JOptionPane.showMessageDialog(this, perfil.toString(), "Mi Perfil", JOptionPane.INFORMATION_MESSAGE);
    }
    
    /**
     * Cierra la sesión actual.
     */
    private void cerrarSesion() {
        int confirmacion = JOptionPane.showConfirmDialog(this, 
                "¿Está seguro de que desea cerrar la sesión?", 
                "Confirmar", JOptionPane.YES_NO_OPTION);
        
        if (confirmacion == JOptionPane.YES_OPTION) {
            controladorUsuario.cerrarSesion();
            getContentPane().removeAll();
            mostrarLogin();
        }
    }
    
    /**
     * Muestra información acerca de la aplicación.
     */
    private void mostrarAcerca() {
        String mensaje = "Sistema de Gestión de Inventario v1.0\n\n" +
                        "Desarrollado para STF Group\n" +
                        "Programación Orientada a Objetos - Java\n\n" +
                        "Características:\n" +
                        "- Gestión de productos\n" +
                        "- Registro de movimientos\n" +
                        "- Reportes de inventario\n" +
                        "- Gestión de usuarios\n" +
                        "- Persistencia en CSV";
        
        JOptionPane.showMessageDialog(this, mensaje, "Acerca de", JOptionPane.INFORMATION_MESSAGE);
    }
    
    /**
     * Sale de la aplicación.
     */
    private void salir() {
        int confirmacion = JOptionPane.showConfirmDialog(this, 
                "¿Está seguro de que desea salir?", 
                "Confirmar salida", JOptionPane.YES_NO_OPTION);
        
        if (confirmacion == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }
}
