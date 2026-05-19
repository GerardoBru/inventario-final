package com.edu.tecnocomfenalco.modelo;

/**
 * Clase Bodeguero que representa un usuario bodeguero.
 * Hereda de Usuario aplicando POLIMORFISMO.
 * 
 * @author chancro
 */
public class Bodeguero extends Usuario {
    private static final long serialVersionUID = 1L;
    
    private String ubicacionBodega;
    
    /**
     * Constructor para crear un bodeguero.
     * 
     * @param id identificador único
     * @param nombre nombre del bodeguero
     * @param correo correo electrónico
     * @param contraseña contraseña
     * @param ubicacionBodega ubicación de la bodega
     */
    public Bodeguero(int id, String nombre, String correo, String contraseña, String ubicacionBodega) {
        super(id, nombre, correo, contraseña, Rol.BODEGUERO);
        this.ubicacionBodega = ubicacionBodega;
    }
    
    /**
     * Constructor vacío.
     */
    public Bodeguero() {
        super();
        this.rol = Rol.BODEGUERO;
    }
    
    public String getUbicacionBodega() {
        return ubicacionBodega;
    }
    
    public void setUbicacionBodega(String ubicacionBodega) {
        if (ubicacionBodega != null && !ubicacionBodega.trim().isEmpty()) {
            this.ubicacionBodega = ubicacionBodega;
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
        return "BODEGUERO: Ver stock de productos, ver existencia en bodega";
    }
}
