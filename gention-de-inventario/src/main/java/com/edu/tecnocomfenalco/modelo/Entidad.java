package com.edu.tecnocomfenalco.modelo;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Clase abstracta base para todas las entidades del sistema.
 * Implementa los atributos comunes y métodos base.
 * 
 * @author chancro
 */
public abstract class Entidad implements Serializable {
    private static final long serialVersionUID = 1L;
    
    protected int id;
    protected LocalDateTime fechaCreacion;
    
    /**
     * Constructor base para la creación de entidades.
     * 
     * @param id identificador único
     */
    public Entidad(int id) {
        this.id = id;
        this.fechaCreacion = LocalDateTime.now();
    }
    
    /**
     * Constructor vacío para deserialización.
     */
    public Entidad() {
        this.fechaCreacion = LocalDateTime.now();
    }
    
    // Getters y Setters con encapsulación
    
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }
    
    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }
    
    /**
     * Método abstracto para obtener representación de la entidad.
     * Debe ser implementado por todas las subclases.
     * Ejemplo de Abstracción.
     * 
     * @return descripción de la entidad
     */
    public abstract String obtenerDescripcion();
    
    @Override
    public String toString() {
        return "Entidad{" +
                "id=" + id +
                ", fechaCreacion=" + fechaCreacion +
                '}';
    }
}
