package com.edu.tecnocomfenalco.modelo;

/**
 * Clase Encargado que representa un usuario encargado.
 * Hereda de Usuario aplicando POLIMORFISMO.
 * 
 * @author chancro
 */
public class Encargado extends Usuario {
    private static final long serialVersionUID = 1L;
    
    private String seccion;
    
    /**
     * Constructor para crear un encargado.
     * 
     * @param id identificador único
     * @param nombre nombre del encargado
     * @param correo correo electrónico
     * @param contraseña contraseña
     * @param seccion sección a cargo
     */
    public Encargado(int id, String nombre, String correo, String contraseña, String seccion) {
        super(id, nombre, correo, contraseña, Rol.ENCARGADO);
        this.seccion = seccion;
    }
    
    /**
     * Constructor vacío.
     */
    public Encargado() {
        super();
        this.rol = Rol.ENCARGADO;
    }
    
    public String getSeccion() {
        return seccion;
    }
    
    public void setSeccion(String seccion) {
        if (seccion != null && !seccion.trim().isEmpty()) {
            this.seccion = seccion;
        }
    }
    
    /**
     * Implementación del método abstracto obtenerPermisos.
     * Polimorfismo en acción.
     * 
     * @return descripción de los permisos
     */
    @Override
    public String obtenerPermisos() {
        return "ENCARGADO: Actualizar cantidad de productos, registrar clientes nuevos";
    }
}
