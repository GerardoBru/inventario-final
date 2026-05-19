package com.edu.tecnocomfenalco.modelo;

import java.time.LocalDateTime;

/**
 * Clase MovimientoInventario que registra entradas y salidas de productos.
 * Hereda de Entidad aplicando herencia.
 * 
 * @author chancro
 */
public class MovimientoInventario extends Entidad {
    private static final long serialVersionUID = 1L;
    
    private TipoMovimiento tipo;
    private int productoId;
    private int cantidad;
    private String motivo;
    private String usuarioResponsable;
    
    /**
     * Enum para tipos de movimiento.
     */
    public enum TipoMovimiento {
        ENTRADA("Entrada"),
        SALIDA("Salida");
        
        private final String descripcion;
        
        TipoMovimiento(String descripcion) {
            this.descripcion = descripcion;
        }
        
        public String getDescripcion() {
            return descripcion;
        }
    }
    
    /**
     * Constructor para crear un movimiento.
     * 
     * @param id identificador único
     * @param tipo tipo de movimiento
     * @param productoId id del producto
     * @param cantidad cantidad movida
     * @param motivo motivo del movimiento
     * @param usuarioResponsable usuario que realiza el movimiento
     */
    public MovimientoInventario(int id, TipoMovimiento tipo, int productoId, 
                               int cantidad, String motivo, String usuarioResponsable) {
        super(id);
        this.tipo = tipo;
        this.productoId = productoId;
        this.cantidad = cantidad;
        this.motivo = motivo;
        this.usuarioResponsable = usuarioResponsable;
    }
    
    /**
     * Constructor vacío.
     */
    public MovimientoInventario() {
        super();
    }
    
    // Getters y Setters
    
    public TipoMovimiento getTipo() {
        return tipo;
    }
    
    public void setTipo(TipoMovimiento tipo) {
        this.tipo = tipo;
    }
    
    public int getProductoId() {
        return productoId;
    }
    
    public void setProductoId(int productoId) {
        this.productoId = productoId;
    }
    
    public int getCantidad() {
        return cantidad;
    }
    
    public void setCantidad(int cantidad) throws IllegalArgumentException {
        if (cantidad <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser mayor a 0");
        }
        this.cantidad = cantidad;
    }
    
    public String getMotivo() {
        return motivo;
    }
    
    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }
    
    public String getUsuarioResponsable() {
        return usuarioResponsable;
    }
    
    public void setUsuarioResponsable(String usuarioResponsable) {
        this.usuarioResponsable = usuarioResponsable;
    }
    
    /**
     * Implementación del método abstracto obtenerDescripcion.
     * Ejemplo de POLIMORFISMO.
     * 
     * @return descripción del movimiento
     */
    @Override
    public String obtenerDescripcion() {
        return tipo.getDescripcion() + ": Producto ID " + productoId + 
               " - Cantidad: " + cantidad + " - Motivo: " + motivo + 
               " - Usuario: " + usuarioResponsable;
    }
    
    @Override
    public String toString() {
        return "MovimientoInventario{" +
                "id=" + id +
                ", tipo=" + tipo +
                ", productoId=" + productoId +
                ", cantidad=" + cantidad +
                ", motivo='" + motivo + '\'' +
                ", usuarioResponsable='" + usuarioResponsable + '\'' +
                ", fechaCreacion=" + fechaCreacion +
                '}';
    }
}
