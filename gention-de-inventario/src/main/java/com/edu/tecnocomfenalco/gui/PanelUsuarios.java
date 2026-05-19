package com.edu.tecnocomfenalco.gui;

import com.edu.tecnocomfenalco.controlador.ControladorUsuario;
import com.edu.tecnocomfenalco.modelo.Usuario;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

/**
 * Panel para gestionar usuarios del sistema.
 * Permite crear, ver y eliminar usuarios.
 * 
 * @author chancro
 */
public class PanelUsuarios extends JPanel {
    private static final long serialVersionUID = 1L;
    
    private ControladorUsuario controlador;
    private JTable tablaUsuarios;
    private DefaultTableModel modeloTabla;
    private JButton btnNuevo, btnEliminar;
    private int usuarioSeleccionado;
    
    /**
     * Constructor del panel de usuarios.
     * 
     * @param controlador controlador de usuarios
     */
    public PanelUsuarios(ControladorUsuario controlador) {
        this.controlador = controlador;
        this.usuarioSeleccionado = -1;
        
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
        btnNuevo = new JButton("Nuevo Usuario");
        btnNuevo.addActionListener(e -> nuevoUsuario());
        panelBotones.add(btnNuevo);
        
        btnEliminar = new JButton("Eliminar");
        btnEliminar.addActionListener(e -> eliminarUsuario());
        panelBotones.add(btnEliminar);
        
        // Tabla de usuarios
        String[] columnasEncabezado = {"ID", "Nombre", "Correo", "Rol", "Campo Adicional"};
        modeloTabla = new DefaultTableModel(columnasEncabezado, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        tablaUsuarios = new JTable(modeloTabla);
        tablaUsuarios.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tablaUsuarios.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                usuarioSeleccionado = tablaUsuarios.getSelectedRow();
            }
        });
        
        JScrollPane scrollPane = new JScrollPane(tablaUsuarios);
        
        // Agregar componentes al panel
        add(panelBotones, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }
    
    /**
     * Actualiza la tabla con todos los usuarios.
     */
    private void actualizarTabla() {
        modeloTabla.setRowCount(0);
        for (Usuario u : controlador.obtenerTodos()) {
            agregarFilaTabla(u);
        }
    }
    
    /**
     * Agrega una fila a la tabla.
     * 
     * @param usuario usuario a agregar
     */
    private void agregarFilaTabla(Usuario usuario) {
        String campoAdicional = "";
        
        if (usuario instanceof com.edu.tecnocomfenalco.modelo.Administrador) {
            com.edu.tecnocomfenalco.modelo.Administrador admin = 
                (com.edu.tecnocomfenalco.modelo.Administrador) usuario;
            campoAdicional = admin.getDepartamento();
        } else if (usuario instanceof com.edu.tecnocomfenalco.modelo.Bodeguero) {
            com.edu.tecnocomfenalco.modelo.Bodeguero bodeguero = 
                (com.edu.tecnocomfenalco.modelo.Bodeguero) usuario;
            campoAdicional = bodeguero.getUbicacionBodega();
        } else if (usuario instanceof com.edu.tecnocomfenalco.modelo.Encargado) {
            com.edu.tecnocomfenalco.modelo.Encargado encargado = 
                (com.edu.tecnocomfenalco.modelo.Encargado) usuario;
            campoAdicional = encargado.getSeccion();
        }
        
        Object[] fila = {
            usuario.getId(),
            usuario.getNombre(),
            usuario.getCorreo(),
            usuario.getRol().getDescripcion(),
            campoAdicional
        };
        modeloTabla.addRow(fila);
    }
    
    /**
     * Crea un nuevo usuario.
     */
    private void nuevoUsuario() {
        DialogoUsuario dialogo = new DialogoUsuario((Frame) SwingUtilities.getWindowAncestor(this), controlador);
        if (dialogo.getUsuarioCreado()) {
            actualizarTabla();
        }
    }
    
    /**
     * Elimina un usuario.
     */
    private void eliminarUsuario() {
        if (usuarioSeleccionado < 0) {
            JOptionPane.showMessageDialog(this, "Seleccione un usuario", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int id = (Integer) modeloTabla.getValueAt(usuarioSeleccionado, 0);
        String nombre = (String) modeloTabla.getValueAt(usuarioSeleccionado, 1);
        
        int confirmacion = JOptionPane.showConfirmDialog(this, 
                "¿Eliminar el usuario " + nombre + "?", 
                "Confirmar eliminación", JOptionPane.YES_NO_OPTION);
        
        if (confirmacion == JOptionPane.YES_OPTION) {
            try {
                controlador.eliminarUsuario(id);
                actualizarTabla();
                JOptionPane.showMessageDialog(this, "Usuario eliminado correctamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
