package com.edu.tecnocomfenalco.controlador;

import com.edu.tecnocomfenalco.modelo.*;
import com.edu.tecnocomfenalco.excepciones.UsuarioException;
import com.edu.tecnocomfenalco.persistencia.UsuarioCSV;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Controlador para gestionar usuarios del sistema.
 * Implementa el patrón Modelo-Vista-Controlador (MVC).
 * 
 * @author chancro
 */
public class ControladorUsuario {
    
    private List<Usuario> usuarios;
    private UsuarioCSV gestorCSV;
    private int proximoId;
    private Usuario usuarioActual; // Usuario autenticado
    
    /**
     * Constructor que inicializa el controlador.
     */
    public ControladorUsuario() {
        this.usuarios = new ArrayList<>();
        this.gestorCSV = new UsuarioCSV();
        this.proximoId = 1;
        this.usuarioActual = null;
    }
    
    /**
     * Carga los usuarios desde el archivo CSV.
     * 
     * @throws UsuarioException si ocurre error en la carga
     */
    public void cargarUsuarios() throws UsuarioException {
        try {
            usuarios = gestorCSV.cargar();
            if (!usuarios.isEmpty()) {
                proximoId = usuarios.stream()
                    .mapToInt(Usuario::getId)
                    .max()
                    .orElse(0) + 1;
            }
        } catch (Exception e) {
            throw new UsuarioException("Error al cargar usuarios: " + e.getMessage(), e);
        }
    }
    
    /**
     * Guarda los usuarios en el archivo CSV.
     * 
     * @throws UsuarioException si ocurre error en el guardado
     */
    public void guardarUsuarios() throws UsuarioException {
        try {
            gestorCSV.guardar(usuarios);
        } catch (Exception e) {
            throw new UsuarioException("Error al guardar usuarios: " + e.getMessage(), e);
        }
    }
    
    /**
     * Autentica a un usuario.
     * 
     * @param correo correo del usuario
     * @param contraseña contraseña del usuario
     * @return el usuario autenticado
     * @throws UsuarioException si las credenciales son inválidas
     */
    public Usuario autenticar(String correo, String contraseña) throws UsuarioException {
        try {
            Usuario usuario = usuarios.stream()
                .filter(u -> u.verificarCredenciales(correo, contraseña))
                .findFirst()
                .orElse(null);
            
            if (usuario == null) {
                throw new UsuarioException("Credenciales inválidas");
            }
            
            this.usuarioActual = usuario;
            return usuario;
        } catch (UsuarioException e) {
            throw e;
        } catch (Exception e) {
            throw new UsuarioException("Error al autenticar usuario: " + e.getMessage(), e);
        }
    }
    
    /**
     * Cierra la sesión actual.
     */
    public void cerrarSesion() {
        usuarioActual = null;
    }
    
    /**
     * Obtiene el usuario autenticado actual.
     * 
     * @return usuario actual o null si no hay sesión activa
     */
    public Usuario obtenerUsuarioActual() {
        return usuarioActual;
    }
    
    /**
     * Crea un nuevo administrador.
     * 
     * @param nombre nombre del administrador
     * @param correo correo electrónico
     * @param contraseña contraseña
     * @param departamento departamento
     * @return el usuario creado
     * @throws UsuarioException si hay error
     */
    public Administrador crearAdministrador(String nombre, String correo, String contraseña, String departamento) throws UsuarioException {
        try {
            validarDatos(nombre, correo, contraseña);
            
            if (usuarios.stream().anyMatch(u -> u.getCorreo().equals(correo))) {
                throw new UsuarioException("El correo " + correo + " ya está registrado");
            }
            
            Administrador admin = new Administrador(proximoId++, nombre, correo, contraseña, departamento);
            usuarios.add(admin);
            guardarUsuarios();
            return admin;
        } catch (UsuarioException e) {
            throw e;
        } catch (Exception e) {
            throw new UsuarioException("Error al crear administrador: " + e.getMessage(), e);
        }
    }
    
    /**
     * Crea un nuevo bodeguero.
     * 
     * @param nombre nombre del bodeguero
     * @param correo correo electrónico
     * @param contraseña contraseña
     * @param ubicacionBodega ubicación de la bodega
     * @return el usuario creado
     * @throws UsuarioException si hay error
     */
    public Bodeguero crearBodeguero(String nombre, String correo, String contraseña, String ubicacionBodega) throws UsuarioException {
        try {
            validarDatos(nombre, correo, contraseña);
            
            if (usuarios.stream().anyMatch(u -> u.getCorreo().equals(correo))) {
                throw new UsuarioException("El correo " + correo + " ya está registrado");
            }
            
            Bodeguero bodeguero = new Bodeguero(proximoId++, nombre, correo, contraseña, ubicacionBodega);
            usuarios.add(bodeguero);
            guardarUsuarios();
            return bodeguero;
        } catch (UsuarioException e) {
            throw e;
        } catch (Exception e) {
            throw new UsuarioException("Error al crear bodeguero: " + e.getMessage(), e);
        }
    }
    
    /**
     * Crea un nuevo encargado.
     * 
     * @param nombre nombre del encargado
     * @param correo correo electrónico
     * @param contraseña contraseña
     * @param seccion sección a cargo
     * @return el usuario creado
     * @throws UsuarioException si hay error
     */
    public Encargado crearEncargado(String nombre, String correo, String contraseña, String seccion) throws UsuarioException {
        try {
            validarDatos(nombre, correo, contraseña);
            
            if (usuarios.stream().anyMatch(u -> u.getCorreo().equals(correo))) {
                throw new UsuarioException("El correo " + correo + " ya está registrado");
            }
            
            Encargado encargado = new Encargado(proximoId++, nombre, correo, contraseña, seccion);
            usuarios.add(encargado);
            guardarUsuarios();
            return encargado;
        } catch (UsuarioException e) {
            throw e;
        } catch (Exception e) {
            throw new UsuarioException("Error al crear encargado: " + e.getMessage(), e);
        }
    }
    
    /**
     * Obtiene un usuario por su ID.
     * 
     * @param id id del usuario
     * @return el usuario o null si no existe
     */
    public Usuario obtenerUsuarioPorId(int id) {
        return usuarios.stream()
            .filter(u -> u.getId() == id)
            .findFirst()
            .orElse(null);
    }
    
    /**
     * Obtiene todos los usuarios de un rol específico.
     * 
     * @param rol rol a filtrar
     * @return lista de usuarios con ese rol
     */
    public List<Usuario> obtenerPorRol(Usuario.Rol rol) {
        return usuarios.stream()
            .filter(u -> u.getRol() == rol)
            .collect(Collectors.toList());
    }
    
    /**
     * Obtiene todos los usuarios.
     * 
     * @return lista de todos los usuarios
     */
    public List<Usuario> obtenerTodos() {
        return new ArrayList<>(usuarios);
    }
    
    /**
     * Elimina un usuario.
     * 
     * @param id id del usuario a eliminar
     * @throws UsuarioException si el usuario no existe
     */
    public void eliminarUsuario(int id) throws UsuarioException {
        try {
            Usuario usuario = obtenerUsuarioPorId(id);
            if (usuario == null) {
                throw new UsuarioException("Usuario con ID " + id + " no encontrado");
            }
            
            usuarios.remove(usuario);
            guardarUsuarios();
        } catch (UsuarioException e) {
            throw e;
        } catch (Exception e) {
            throw new UsuarioException("Error al eliminar usuario: " + e.getMessage(), e);
        }
    }
    
    /**
     * Valida los datos comunes de un usuario.
     * 
     * @param nombre nombre del usuario
     * @param correo correo del usuario
     * @param contraseña contraseña del usuario
     * @throws UsuarioException si algún dato es inválido
     */
    private void validarDatos(String nombre, String correo, String contraseña) throws UsuarioException {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new UsuarioException("El nombre es requerido");
        }
        if (correo == null || !correo.contains("@")) {
            throw new UsuarioException("El correo debe ser válido");
        }
        if (contraseña == null || contraseña.isEmpty()) {
            throw new UsuarioException("La contraseña es requerida");
        }
    }
}
