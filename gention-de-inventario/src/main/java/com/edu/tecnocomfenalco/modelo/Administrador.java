package com.edu.tecnocomfenalco.modelo;

/**
 * Clase Administrador que representa un usuario administrador.
 * Hereda de Usuario aplicando POLIMORFISMO.
 * 
 * @author chancro
 */
public class Administrador extends Usuario {
    private static final long serialVersionUID = 1L;
    
    private String departamento;
    
    /**
     * Constructor para crear un administrador.
     * 
     * @param id identificador único
     * @param nombre nombre del administrador
     * @param correo correo electrónico
     * @param contraseña contraseña
     * @param departamento departamento del administrador
     */
    public Administrador(int id, String nombre, String correo, String contraseña, String departamento) {
        super(id, nombre, correo, contraseña, Rol.ADMINISTRADOR);
        this.departamento = departamento;
    }
    
    /**
     * Constructor vacío.
     */
    public Administrador() {
        super();
        this.rol = Rol.ADMINISTRADOR;
    }
    
    public String getDepartamento() {
        return departamento;
    }
    
    public void setDepartamento(String departamento) {
        if (departamento != null && !departamento.trim().isEmpty()) {
            this.departamento = departamento;
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
        return "ADMINISTRADOR: Crear/actualizar/eliminar productos, " +
               "registrar movimientos, ver reportes, gestionar usuarios";
    }
}
