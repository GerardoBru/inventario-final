package com.edu.tecnocomfenalco.gui;

import com.edu.tecnocomfenalco.controlador.ControladorProducto;
import com.edu.tecnocomfenalco.modelo.Producto;
import com.edu.tecnocomfenalco.modelo.Usuario;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

/**
 * Panel para gestionar productos del inventario.
 * Permite crear, actualizar, eliminar y buscar productos.
 * 
 * @author chancro
 */
public class PanelProductos extends JPanel {
    private static final long serialVersionUID = 1L;
    
    private ControladorProducto controlador;
    private Usuario usuarioActual;
    private JTable tablaProductos;
    private DefaultTableModel modeloTabla;
    private JTextField campoBusqueda;
    private JButton btnBuscar, btnNuevo, btnActualizar, btnEliminar;
    private int productoSeleccionado;
    
    /**
     * Constructor del panel de productos.
     * 
     * @param controlador controlador de productos
     * @param usuarioActual usuario actual del sistema
     */
    public PanelProductos(ControladorProducto controlador, Usuario usuarioActual) {
        this.controlador = controlador;
        this.usuarioActual = usuarioActual;
        this.productoSeleccionado = -1;
        
        setLayout(new BorderLayout());
        construirGUI();
        actualizarTabla();
    }
    
    /**
     * Construye la interfaz gráfica del panel.
     */
    private void construirGUI() {
        // Panel de búsqueda
        JPanel panelBusqueda = new JPanel();
        panelBusqueda.add(new JLabel("Buscar:"));
        campoBusqueda = new JTextField(20);
        panelBusqueda.add(campoBusqueda);
        btnBuscar = new JButton("Buscar");
        btnBuscar.addActionListener(e -> buscar());
        panelBusqueda.add(btnBuscar);
        
        // Panel de botones
        JPanel panelBotones = new JPanel();
        btnNuevo = new JButton("Nuevo Producto");
        btnNuevo.addActionListener(e -> nuevoProducto());
        // Solo administrador puede crear productos
        if (usuarioActual.getRol() != Usuario.Rol.ADMINISTRADOR) {
            btnNuevo.setEnabled(false);
        }
        panelBotones.add(btnNuevo);
        
        btnActualizar = new JButton("Actualizar");
        btnActualizar.addActionListener(e -> actualizarProducto());
        // Solo administrador y encargado pueden actualizar
        if (usuarioActual.getRol() == Usuario.Rol.BODEGUERO) {
            btnActualizar.setEnabled(false);
        }
        panelBotones.add(btnActualizar);
        
        btnEliminar = new JButton("Eliminar");
        btnEliminar.addActionListener(e -> eliminarProducto());
        // Solo administrador puede eliminar
        if (usuarioActual.getRol() != Usuario.Rol.ADMINISTRADOR) {
            btnEliminar.setEnabled(false);
        }
        panelBotones.add(btnEliminar);
        
        // Tabla de productos
        String[] columnasEncabezado = {"ID", "Nombre", "Código", "Tallas", "Precio", "Cantidad", "Categoría", "Ventas"};
        modeloTabla = new DefaultTableModel(columnasEncabezado, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        tablaProductos = new JTable(modeloTabla);
        tablaProductos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tablaProductos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                productoSeleccionado = tablaProductos.getSelectedRow();
            }
        });
        
        JScrollPane scrollPane = new JScrollPane(tablaProductos);
        
        // Agregar componentes al panel
        add(panelBusqueda, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);
    }
    
    /**
     * Actualiza la tabla con todos los productos.
     */
    private void actualizarTabla() {
        modeloTabla.setRowCount(0);
        for (Producto p : controlador.obtenerTodos()) {
            agregarFilaTabla(p);
        }
    }
    
    /**
     * Agrega una fila a la tabla.
     * 
     * @param producto producto a agregar
     */
    private void agregarFilaTabla(Producto producto) {
        Object[] fila = {
            producto.getId(),
            producto.getNombre(),
            producto.getCodigo(),
            producto.getTallas(),
            String.format("$ %,.0f", producto.getPrecio()),
            producto.getCantidad(),
            producto.getCategoria(),
            producto.getVentasTotal()
        };
        modeloTabla.addRow(fila);
    }
    
    /**
     * Busca productos por nombre.
     */
    private void buscar() {
        String criterio = campoBusqueda.getText().trim();
        modeloTabla.setRowCount(0);
        
        if (criterio.isEmpty()) {
            actualizarTabla();
        } else {
            for (Producto p : controlador.buscarPorNombre(criterio)) {
                agregarFilaTabla(p);
            }
        }
    }
    
    /**
     * Crea un nuevo producto.
     */
    private void nuevoProducto() {
        DialogoProducto dialogo = new DialogoProducto((Frame) SwingUtilities.getWindowAncestor(this), controlador, null);
        if (dialogo.getProductoGuardado()) {
            actualizarTabla();
        }
    }
    
    /**
     * Actualiza un producto existente.
     */
    private void actualizarProducto() {
        if (productoSeleccionado < 0) {
            JOptionPane.showMessageDialog(this, "Seleccione un producto", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int id = (Integer) modeloTabla.getValueAt(productoSeleccionado, 0);
        Producto producto = controlador.obtenerProductoPorId(id);
        
        DialogoProducto dialogo = new DialogoProducto((Frame) SwingUtilities.getWindowAncestor(this), controlador, producto);
        if (dialogo.getProductoGuardado()) {
            actualizarTabla();
        }
    }
    
    /**
     * Elimina un producto.
     */
    private void eliminarProducto() {
        if (productoSeleccionado < 0) {
            JOptionPane.showMessageDialog(this, "Seleccione un producto", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int id = (Integer) modeloTabla.getValueAt(productoSeleccionado, 0);
        String nombre = (String) modeloTabla.getValueAt(productoSeleccionado, 1);
        
        int confirmacion = JOptionPane.showConfirmDialog(this, 
                "¿Eliminar el producto " + nombre + "?", 
                "Confirmar eliminación", JOptionPane.YES_NO_OPTION);
        
        if (confirmacion == JOptionPane.YES_OPTION) {
            try {
                controlador.eliminarProducto(id);
                actualizarTabla();
                JOptionPane.showMessageDialog(this, "Producto eliminado correctamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
