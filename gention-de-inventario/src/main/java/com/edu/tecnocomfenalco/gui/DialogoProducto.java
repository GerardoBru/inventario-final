package com.edu.tecnocomfenalco.gui;

import com.edu.tecnocomfenalco.controlador.ControladorProducto;
import com.edu.tecnocomfenalco.modelo.Producto;
import javax.swing.*;
import java.awt.*;

/**
 * Diálogo para crear o actualizar un producto.
 * 
 * @author chancro
 */
public class DialogoProducto extends JDialog {
    private static final long serialVersionUID = 1L;
    
    private JTextField campoNombre;
    private JTextArea campoDescripcion;
    private JTextField campoTallas;
    private JTextField campoPrecio;
    private JTextField campoCantidad;
    private JTextField campoCodigo;
    private JTextField campoCategoria;
    private JButton btnGuardar;
    private JButton btnCancelar;
    private ControladorProducto controlador;
    private Producto productoOriginal;
    private boolean guardado;
    
    /**
     * Constructor del diálogo de producto.
     * 
     * @param parent ventana padre
     * @param controlador controlador de productos
     * @param producto producto a editar o null para crear nuevo
     */
    public DialogoProducto(Frame parent, ControladorProducto controlador, Producto producto) {
        super(parent, "Producto", true);
        this.controlador = controlador;
        this.productoOriginal = producto;
        this.guardado = false;
        
        construirGUI();
        if (producto != null) {
            cargarDatos(producto);
            setTitle("Editar Producto");
        } else {
            setTitle("Nuevo Producto");
        }
        
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setSize(500, 400);
        setLocationRelativeTo(parent);
        setVisible(true);
    }
    
    /**
     * Construye la interfaz gráfica del diálogo.
     */
    private void construirGUI() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        
        // Nombre
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Nombre:"), gbc);
        campoNombre = new JTextField(20);
        gbc.gridx = 1;
        gbc.weightx = 1;
        panel.add(campoNombre, gbc);
        
        // Descripción
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0;
        panel.add(new JLabel("Descripción:"), gbc);
        campoDescripcion = new JTextArea(3, 20);
        campoDescripcion.setLineWrap(true);
        campoDescripcion.setWrapStyleWord(true);
        gbc.gridx = 1;
        gbc.weightx = 1;
        panel.add(new JScrollPane(campoDescripcion), gbc);
        
        // Tallas
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0;
        panel.add(new JLabel("Tallas:"), gbc);
        campoTallas = new JTextField(20);
        gbc.gridx = 1;
        gbc.weightx = 1;
        panel.add(campoTallas, gbc);
        
        // Precio
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weightx = 0;
        panel.add(new JLabel("Precio:"), gbc);
        campoPrecio = new JTextField(20);
        gbc.gridx = 1;
        gbc.weightx = 1;
        panel.add(campoPrecio, gbc);
        
        // Cantidad
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.weightx = 0;
        panel.add(new JLabel("Cantidad:"), gbc);
        campoCantidad = new JTextField(20);
        gbc.gridx = 1;
        gbc.weightx = 1;
        panel.add(campoCantidad, gbc);
        
        // Código
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.weightx = 0;
        panel.add(new JLabel("Código:"), gbc);
        campoCodigo = new JTextField(20);
        gbc.gridx = 1;
        gbc.weightx = 1;
        panel.add(campoCodigo, gbc);
        
        // Categoría
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.weightx = 0;
        panel.add(new JLabel("Categoría:"), gbc);
        campoCategoria = new JTextField(20);
        gbc.gridx = 1;
        gbc.weightx = 1;
        panel.add(campoCategoria, gbc);
        
        // Botones
        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        
        JPanel panelBotones = new JPanel();
        btnGuardar = new JButton("Guardar");
        btnGuardar.addActionListener(e -> guardar());
        panelBotones.add(btnGuardar);
        
        btnCancelar = new JButton("Cancelar");
        btnCancelar.addActionListener(e -> dispose());
        panelBotones.add(btnCancelar);
        
        panel.add(panelBotones, gbc);
        
        add(new JScrollPane(panel));
    }
    
    /**
     * Carga los datos del producto en los campos.
     * 
     * @param producto producto a cargar
     */
    private void cargarDatos(Producto producto) {
        campoNombre.setText(producto.getNombre());
        campoDescripcion.setText(producto.getDescripcion());
        campoTallas.setText(producto.getTallas());
        campoPrecio.setText(String.valueOf(producto.getPrecio()));
        campoCantidad.setText(String.valueOf(producto.getCantidad()));
        campoCodigo.setText(producto.getCodigo());
        campoCategoria.setText(producto.getCategoria());
    }
    
    /**
     * Guarda o actualiza el producto.
     */
    private void guardar() {
        try {
            String nombre = campoNombre.getText().trim();
            String descripcion = campoDescripcion.getText().trim();
            String tallas = campoTallas.getText().trim();
            double precio = Double.parseDouble(campoPrecio.getText().trim());
            int cantidad = Integer.parseInt(campoCantidad.getText().trim());
            String codigo = campoCodigo.getText().trim();
            String categoria = campoCategoria.getText().trim();
            
            if (nombre.isEmpty() || codigo.isEmpty() || tallas.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Complete todos los campos requeridos", 
                        "Campos incompletos", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            if (productoOriginal == null) {
                // Crear nuevo producto
                controlador.crearProducto(nombre, descripcion, tallas, precio, cantidad, codigo, categoria);
            } else {
                // Actualizar producto
                controlador.actualizarProducto(productoOriginal.getId(), nombre, descripcion, tallas, precio, cantidad, categoria);
            }
            
            guardado = true;
            JOptionPane.showMessageDialog(this, "Producto guardado correctamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "El precio y cantidad deben ser números válidos", 
                    "Error de formato", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Indica si el producto fue guardado.
     * 
     * @return true si fue guardado
     */
    public boolean getProductoGuardado() {
        return guardado;
    }
}
