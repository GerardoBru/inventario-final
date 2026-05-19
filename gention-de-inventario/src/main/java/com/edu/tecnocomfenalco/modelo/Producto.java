package com.edu.tecnocomfenalco.modelo;

/**
 * Clase Producto que representa un artículo del inventario.
 * Hereda de Entidad aplicando el concepto de HERENCIA.
 * 
 * @author chancro
 */
public class Producto extends Entidad {
    private static final long serialVersionUID = 1L;
    
    private String nombre;
    private String descripcion;
    private String tallas;
    private double precio;
    private int cantidad;
    private String codigo;
    private String categoria;
    private int ventasTotal; // Para identificar productos más vendidos
    
    /**
     * Constructor completo para crear un producto.
     * 
     * @param id identificador único
     * @param nombre nombre del producto
     * @param descripcion descripción del producto
     * @param tallas tallas disponibles
     * @param precio precio unitario
     * @param cantidad cantidad disponible
     * @param codigo código del producto
     * @param categoria categoría del producto
     */
    public Producto(int id, String nombre, String descripcion, String tallas, double precio, 
                   int cantidad, String codigo, String categoria) {
        super(id);
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.tallas = tallas;
        this.precio = precio;
        this.cantidad = cantidad;
        this.codigo = codigo;
        this.categoria = categoria;
        this.ventasTotal = 0;
    }
    
    /**
     * Constructor vacío para deserialización.
     */
    public Producto() {
        super();
        this.ventasTotal = 0;
    }
    
    // Getters y Setters con encapsulación
    
    public String getNombre() {
        return nombre;
    }
    
    public void setNombre(String nombre) {
        if (nombre != null && !nombre.trim().isEmpty()) {
            this.nombre = nombre;
        }
    }
    
    public String getDescripcion() {
        return descripcion;
    }
    
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    public String getTallas() {
        return tallas;
    }
    
    public void setTallas(String tallas) {
        this.tallas = tallas;
    }
    
    public double getPrecio() {
        return precio;
    }
    
    public void setPrecio(double precio) throws IllegalArgumentException {
        if (precio < 0) {
            throw new IllegalArgumentException("El precio no puede ser negativo");
        }
        this.precio = precio;
    }
    
    public int getCantidad() {
        return cantidad;
    }
    
    public void setCantidad(int cantidad) throws IllegalArgumentException {
        if (cantidad < 0) {
            throw new IllegalArgumentException("La cantidad no puede ser negativa");
        }
        this.cantidad = cantidad;
    }
    
    public String getCodigo() {
        return codigo;
    }
    
    public void setCodigo(String codigo) {
        if (codigo != null && !codigo.trim().isEmpty()) {
            this.codigo = codigo;
        }
    }
    
    public String getCategoria() {
        return categoria;
    }
    
    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
    
    public int getVentasTotal() {
        return ventasTotal;
    }
    
    public void setVentasTotal(int ventasTotal) {
        this.ventasTotal = ventasTotal;
    }
    
    /**
     * Incrementa las ventas del producto.
     * 
     * @param cantidad cantidad vendida
     */
    public void incrementarVentas(int cantidad) {
        this.ventasTotal += cantidad;
    }
    
    /**
     * Verifica si hay suficiente stock disponible.
     * 
     * @param cantidad cantidad a verificar
     * @return true si hay suficiente stock
     */
    public boolean tieneSuficienteStock(int cantidad) {
        return this.cantidad >= cantidad;
    }
    
    /**
     * Reduce el stock del producto.
     * 
     * @param cantidad cantidad a restar
     * @throws IllegalArgumentException si no hay suficiente stock
     */
    public void reducirStock(int cantidad) throws IllegalArgumentException {
        if (!tieneSuficienteStock(cantidad)) {
            throw new IllegalArgumentException("Stock insuficiente. Disponible: " + this.cantidad);
        }
        this.cantidad -= cantidad;
    }
    
    /**
     * Aumenta el stock del producto.
     * 
     * @param cantidad cantidad a sumar
     */
    public void aumentarStock(int cantidad) {
        this.cantidad += cantidad;
    }
    
    /**
     * Implementación del método abstracto obtenerDescripcion.
     * Ejemplo de POLIMORFISMO.
     * 
     * @return descripción del producto
     */
    @Override
    public String obtenerDescripcion() {
        return "Producto: " + nombre + " - Código: " + codigo + 
               " - Tallas: " + tallas + " - Cantidad: " + cantidad + " - Precio: $" + precio;
    }
    
    @Override
    public String toString() {
        return "Producto{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", precio=" + precio +
                ", cantidad=" + cantidad +
                ", codigo='" + codigo + '\'' +
                ", categoria='" + categoria + '\'' +
                ", ventasTotal=" + ventasTotal +
                '}';
    }
}
