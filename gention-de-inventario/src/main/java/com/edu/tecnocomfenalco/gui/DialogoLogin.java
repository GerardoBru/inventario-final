package com.edu.tecnocomfenalco.gui;

import com.edu.tecnocomfenalco.controlador.ControladorUsuario;
import com.edu.tecnocomfenalco.modelo.Usuario;
import javax.swing.*;
import java.awt.*;

/**
 * Diálogo de login para autenticar usuarios en el sistema.
 * 
 * @author chancro
 */
public class DialogoLogin extends JDialog {
    private static final long serialVersionUID = 1L;
    
    private JTextField campoCorreo;
    private JPasswordField campoContraseña;
    private JButton btnIngresar;
    private JButton btnSalir;
    private ControladorUsuario controlador;
    private Usuario usuarioAutenticado;
    
    /**
     * Constructor del diálogo de login.
     * 
     * @param parent ventana padre
     * @param controlador controlador de usuarios
     */
    public DialogoLogin(Frame parent, ControladorUsuario controlador) {
        super(parent, "Login - Sistema de Inventario", true);
        this.controlador = controlador;
        this.usuarioAutenticado = null;
        
        construirGUI();
        setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        setSize(350, 200);
        setLocationRelativeTo(parent);
        setVisible(true);
    }
    
    /**
     * Construye la interfaz gráfica del login.
     */
    private void construirGUI() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        
        // Etiqueta y campo de correo
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Correo:"), gbc);
        
        campoCorreo = new JTextField(20);
        gbc.gridx = 1;
        panel.add(campoCorreo, gbc);
        
        // Etiqueta y campo de contraseña
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(new JLabel("Contraseña:"), gbc);
        
        campoContraseña = new JPasswordField(20);
        gbc.gridx = 1;
        panel.add(campoContraseña, gbc);
        
        // Botones
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        
        JPanel panelBotones = new JPanel();
        btnIngresar = new JButton("Ingresar");
        btnIngresar.addActionListener(e -> autenticar());
        panelBotones.add(btnIngresar);
        
        btnSalir = new JButton("Salir");
        btnSalir.addActionListener(e -> salir());
        panelBotones.add(btnSalir);
        
        panel.add(panelBotones, gbc);
        
        add(panel);
    }
    
    /**
     * Autentica al usuario.
     */
    private void autenticar() {
        String correo = campoCorreo.getText().trim();
        String contraseña = new String(campoContraseña.getPassword());
        
        if (correo.isEmpty() || contraseña.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor ingrese correo y contraseña", 
                    "Campos requeridos", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        try {
            usuarioAutenticado = controlador.autenticar(correo, contraseña);
            JOptionPane.showMessageDialog(this, "¡Bienvenido " + usuarioAutenticado.getNombre() + "!", 
                    "Inicio de sesión exitoso", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), 
                    "Error de autenticación", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Sale de la aplicación.
     */
    private void salir() {
        System.exit(0);
    }
    
    /**
     * Obtiene el usuario autenticado.
     * 
     * @return usuario autenticado o null si no se autenticó
     */
    public Usuario obtenerUsuario() {
        return usuarioAutenticado;
    }
}
