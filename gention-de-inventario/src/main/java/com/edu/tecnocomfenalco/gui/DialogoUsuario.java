package com.edu.tecnocomfenalco.gui;

import com.edu.tecnocomfenalco.controlador.ControladorUsuario;
import com.edu.tecnocomfenalco.modelo.Usuario;
import javax.swing.*;
import java.awt.*;

/**
 * Diálogo para crear un nuevo usuario del sistema.
 * 
 * @author chancro
 */
public class DialogoUsuario extends JDialog {
    private static final long serialVersionUID = 1L;
    
    private JTextField campoNombre;
    private JTextField campoCorreo;
    private JPasswordField campoContraseña;
    private JComboBox<String> comboRol;
    private JTextField campoCampoAdicional;
    private JButton btnGuardar;
    private JButton btnCancelar;
    private ControladorUsuario controlador;
    private boolean usuarioCreado;
    
    /**
     * Constructor del diálogo de usuario.
     * 
     * @param parent ventana padre
     * @param controlador controlador de usuarios
     */
    public DialogoUsuario(Frame parent, ControladorUsuario controlador) {
        super(parent, "Nuevo Usuario", true);
        this.controlador = controlador;
        this.usuarioCreado = false;
        
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
        
        // Nombre
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Nombre:"), gbc);
        campoNombre = new JTextField(20);
        gbc.gridx = 1;
        gbc.weightx = 1;
        panel.add(campoNombre, gbc);
        
        // Correo
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0;
        panel.add(new JLabel("Correo:"), gbc);
        campoCorreo = new JTextField(20);
        gbc.gridx = 1;
        gbc.weightx = 1;
        panel.add(campoCorreo, gbc);
        
        // Contraseña
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0;
        panel.add(new JLabel("Contraseña:"), gbc);
        campoContraseña = new JPasswordField(20);
        gbc.gridx = 1;
        gbc.weightx = 1;
        panel.add(campoContraseña, gbc);
        
        // Rol
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weightx = 0;
        panel.add(new JLabel("Rol:"), gbc);
        comboRol = new JComboBox<>(new String[]{"Administrador", "Bodeguero", "Encargado"});
        comboRol.addActionListener(e -> actualizarEtiquetaCampoAdicional());
        gbc.gridx = 1;
        gbc.weightx = 1;
        panel.add(comboRol, gbc);
        
        // Campo Adicional
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.weightx = 0;
        panel.add(new JLabel("Departamento:"), gbc);
        campoCampoAdicional = new JTextField(20);
        gbc.gridx = 1;
        gbc.weightx = 1;
        panel.add(campoCampoAdicional, gbc);
        
        // Botones
        gbc.gridx = 0;
        gbc.gridy = 5;
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
        
        add(panel);
    }
    
    /**
     * Actualiza la etiqueta del campo adicional según el rol.
     */
    private void actualizarEtiquetaCampoAdicional() {
        // Cambiar la etiqueta según el rol seleccionado
        // Este es un cambio visual simple
    }
    
    /**
     * Guarda el nuevo usuario.
     */
    private void guardar() {
        try {
            String nombre = campoNombre.getText().trim();
            String correo = campoCorreo.getText().trim();
            String contraseña = new String(campoContraseña.getPassword());
            String rol = (String) comboRol.getSelectedItem();
            String campoAdicional = campoCampoAdicional.getText().trim();
            
            if (nombre.isEmpty() || correo.isEmpty() || contraseña.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Complete todos los campos requeridos", 
                        "Campos incompletos", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            if (rol.equals("Administrador")) {
                controlador.crearAdministrador(nombre, correo, contraseña, campoAdicional);
            } else if (rol.equals("Bodeguero")) {
                controlador.crearBodeguero(nombre, correo, contraseña, campoAdicional);
            } else if (rol.equals("Encargado")) {
                controlador.crearEncargado(nombre, correo, contraseña, campoAdicional);
            }
            
            usuarioCreado = true;
            JOptionPane.showMessageDialog(this, "Usuario creado correctamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Indica si el usuario fue creado.
     * 
     * @return true si fue creado
     */
    public boolean getUsuarioCreado() {
        return usuarioCreado;
    }
}
