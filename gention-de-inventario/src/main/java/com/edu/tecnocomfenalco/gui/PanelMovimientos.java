package com.edu.tecnocomfenalco.gui;

import com.edu.tecnocomfenalco.controlador.ControladorProducto;
import com.edu.tecnocomfenalco.controlador.ControladorMovimiento;
import com.edu.tecnocomfenalco.modelo.MovimientoInventario;
import com.edu.tecnocomfenalco.modelo.Usuario;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * Panel para gestionar movimientos de inventario (entradas y salidas).
 * 
 * @author chancro
 */
public class PanelMovimientos extends JPanel {
    private static final long serialVersionUID = 1L;
    
    private ControladorProducto controladorProducto;
    private ControladorMovimiento controladorMovimiento;
    private Usuario usuario;
    private JTable tablaMovimientos;
    private DefaultTableModel modeloTabla;
    private JButton btnEntrada, btnSalida;
    
    /**
     * Constructor del panel de movimientos.
     * 
     * @param controladorProducto controlador de productos
     * @param controladorMovimiento controlador de movimientos
     * @param usuario usuario actual
     */
    public PanelMovimientos(ControladorProducto controladorProducto, ControladorMovimiento controladorMovimiento, Usuario usuario) {
        this.controladorProducto = controladorProducto;
        this.controladorMovimiento = controladorMovimiento;
        this.usuario = usuario;
        
        setLayout(new BorderLayout());
        construirGUI();
        actualizarTabla();
    }
    
    /**
     * Construye la interfaz gráfica del panel.
     */
    private void construirGUI() {
        // Panel de botones
        JPanel panelBotones = new JPanel();
        btnEntrada = new JButton("Registrar Entrada");
        btnEntrada.addActionListener(e -> registrarEntrada());
        panelBotones.add(btnEntrada);
        
        btnSalida = new JButton("Registrar Salida");
        btnSalida.addActionListener(e -> registrarSalida());
        panelBotones.add(btnSalida);
        
        // Tabla de movimientos
        String[] columnasEncabezado = {"ID", "Tipo", "Producto ID", "Cantidad", "Motivo", "Usuario", "Fecha"};
        modeloTabla = new DefaultTableModel(columnasEncabezado, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        tablaMovimientos = new JTable(modeloTabla);
        tablaMovimientos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        JScrollPane scrollPane = new JScrollPane(tablaMovimientos);
        
        // Agregar componentes al panel
        add(panelBotones, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }
    
    /**
     * Actualiza la tabla con todos los movimientos.
     */
    private void actualizarTabla() {
        modeloTabla.setRowCount(0);
        List<MovimientoInventario> movimientos = controladorMovimiento.obtenerTodos();
        for (MovimientoInventario m : movimientos) {
            agregarFilaTabla(m);
        }
    }
    
    /**
     * Agrega una fila a la tabla.
     * 
     * @param movimiento movimiento a agregar
     */
    private void agregarFilaTabla(MovimientoInventario movimiento) {
        Object[] fila = {
            movimiento.getId(),
            movimiento.getTipo().getDescripcion(),
            movimiento.getProductoId(),
            movimiento.getCantidad(),
            movimiento.getMotivo(),
            movimiento.getUsuarioResponsable(),
            movimiento.getFechaCreacion()
        };
        modeloTabla.addRow(fila);
    }
    
    /**
     * Registra una entrada de productos.
     */
    private void registrarEntrada() {
        DialogoMovimiento dialogo = new DialogoMovimiento(
            (Frame) SwingUtilities.getWindowAncestor(this),
            controladorProducto,
            controladorMovimiento,
            MovimientoInventario.TipoMovimiento.ENTRADA,
            usuario
        );
        if (dialogo.getMovimientoRegistrado()) {
            actualizarTabla();
        }
    }
    
    /**
     * Registra una salida de productos.
     */
    private void registrarSalida() {
        DialogoMovimiento dialogo = new DialogoMovimiento(
            (Frame) SwingUtilities.getWindowAncestor(this),
            controladorProducto,
            controladorMovimiento,
            MovimientoInventario.TipoMovimiento.SALIDA,
            usuario
        );
        if (dialogo.getMovimientoRegistrado()) {
            actualizarTabla();
        }
    }
}
