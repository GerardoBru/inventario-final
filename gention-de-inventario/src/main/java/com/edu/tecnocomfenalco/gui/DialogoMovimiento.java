package com.edu.tecnocomfenalco.gui;

import com.edu.tecnocomfenalco.controlador.ControladorProducto;
import com.edu.tecnocomfenalco.controlador.ControladorMovimiento;
import com.edu.tecnocomfenalco.modelo.MovimientoInventario;
import com.edu.tecnocomfenalco.modelo.Producto;
import com.edu.tecnocomfenalco.modelo.Usuario;
import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * Diálogo para registrar movimientos de inventario (entrada o salida).
 * 
 * @author chancro
 */
public class DialogoMovimiento extends JDialog {
    private static final long serialVersionUID = 1L;
    
    private JComboBox<String> comboProducto;
    private JSpinner spinnerCantidad;
    private JTextArea campoMotivo;
    private JButton btnGuardar;
    private JButton btnCancelar;
    private ControladorProducto controladorProducto;
    private ControladorMovimiento controladorMovimiento;
    private MovimientoInventario.TipoMovimiento tipo;
    private Usuario usuario;
    private boolean movimientoRegistrado;
    private List<Producto> productos;
    
    /**
     * Constructor del diálogo de movimiento.
     * 
     * @param parent ventana padre
     * @param controladorProducto controlador de productos
     * @param controladorMovimiento controlador de movimientos
     * @param tipo tipo de movimiento (ENTRADA o SALIDA)
     * @param usuario usuario actual
     */
    public DialogoMovimiento(Frame parent, ControladorProducto controladorProducto, 
                            ControladorMovimiento controladorMovimiento, 
                            MovimientoInventario.TipoMovimiento tipo, Usuario usuario) {
        super(parent, "Registrar " + tipo.getDescripcion(), true);
        this.controladorProducto = controladorProducto;
        this.controladorMovimiento = controladorMovimiento;
        this.tipo = tipo;
        this.usuario = usuario;
        this.movimientoRegistrado = false;
        this.productos = controladorProducto.obtenerTodos();
        
        construirGUI();
        
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setSize(400, 300);
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
        
        // Producto
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Producto:"), gbc);
        
        String[] productosArray = new String[productos.size()];
        for (int i = 0; i < productos.size(); i++) {
            Producto p = productos.get(i);
            productosArray[i] = p.getId() + " - " + p.getNombre() + " (Cantidad: " + p.getCantidad() + ")";
        }
        
        comboProducto = new JComboBox<>(productosArray);
        gbc.gridx = 1;
        gbc.weightx = 1;
        panel.add(comboProducto, gbc);
        
        // Cantidad
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0;
        panel.add(new JLabel("Cantidad:"), gbc);
        
        spinnerCantidad = new JSpinner(new SpinnerNumberModel(1, 1, 9999, 1));
        gbc.gridx = 1;
        gbc.weightx = 1;
        panel.add(spinnerCantidad, gbc);
        
        // Motivo
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0;
        panel.add(new JLabel("Motivo:"), gbc);
        
        campoMotivo = new JTextArea(3, 20);
        campoMotivo.setLineWrap(true);
        campoMotivo.setWrapStyleWord(true);
        gbc.gridx = 1;
        gbc.weightx = 1;
        gbc.weighty = 1;
        panel.add(new JScrollPane(campoMotivo), gbc);
        
        // Botones
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.weightx = 1;
        gbc.weighty = 0;
        
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
     * Guarda el movimiento.
     */
    private void guardar() {
        try {
            if (productos.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No hay productos disponibles", 
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            Producto producto = productos.get(comboProducto.getSelectedIndex());
            int cantidad = (Integer) spinnerCantidad.getValue();
            String motivo = campoMotivo.getText().trim();
            
            if (motivo.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Ingrese un motivo", 
                        "Campo requerido", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            if (tipo == MovimientoInventario.TipoMovimiento.ENTRADA) {
                controladorMovimiento.registrarEntrada(producto.getId(), cantidad, motivo, usuario.getNombre());
            } else {
                controladorMovimiento.registrarSalida(producto.getId(), cantidad, motivo, usuario.getNombre());
            }
            
            movimientoRegistrado = true;
            JOptionPane.showMessageDialog(this, "Movimiento registrado correctamente", 
                    "Éxito", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Indica si el movimiento fue registrado.
     * 
     * @return true si fue registrado
     */
    public boolean getMovimientoRegistrado() {
        return movimientoRegistrado;
    }
}
