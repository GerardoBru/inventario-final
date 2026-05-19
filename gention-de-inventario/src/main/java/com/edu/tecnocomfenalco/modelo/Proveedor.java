package com.edu.tecnocomfenalco.modelo;

/**
 * Clase Proveedor que representa a los proveedores de productos.
 * Hereda de Entidad aplicando herencia.
 * 
 * @author chancro
 */
public class Proveedor extends Entidad {
    private static final long serialVersionUID = 1L;
    
    private String nombre;
    private String contacto;
    private String correo;
    private String telefono;
    private String direccion;
    
    /**
     * Constructor para crear un proveedor.
     * 
     * @param id identificador único
     * @param nombre nombre del proveedor
     * @param contacto persona de contacto
     * @param correo correo electrónico
     * @param telefono teléfono
     * @param direccion dirección
     */
    public Proveedor(int id, String nombre, String contacto, String correo, String telefono, String direccion) {
        super(id);
        this.nombre = nombre;
        this.contacto = contacto;
        this.correo = correo;
        this.telefono = telefono;
        this.direccion = direccion;
    }
    
    /**
     * Constructor vacío.
     */
    public Proveedor() {
        super();
    }
    
    // Getters y Setters
    
    public String getNombre() {
        return nombre;
    }
    
    public void setNombre(String nombre) {
        if (nombre != null && !nombre.trim().isEmpty()) {
            this.nombre = nombre;
        }
    }
    
    public String getContacto() {
        return contacto;
    }
    
    public void setContacto(String contacto) {
        this.contacto = contacto;
    }
    
    public String getCorreo() {
        return correo;
    }
    
    public void setCorreo(String correo) {
        if (correo != null && correo.contains("@")) {
            this.correo = correo;
        }
    }
    
    public String getTelefono() {
        return telefono;
    }
    
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
    
    public String getDireccion() {
        return direccion;
    }
    
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
    
    /**
     * Implementación del método abstracto obtenerDescripcion.
     * Ejemplo de POLIMORFISMO.
     * 
     * @return descripción del proveedor
     */
    @Override
    public String obtenerDescripcion() {
        return "Proveedor: " + nombre + " - Contacto: " + contacto + 
               " - Teléfono: " + telefono + " - Correo: " + correo;
    }
    
    @Override
    public String toString() {
        return "Proveedor{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", contacto='" + contacto + '\'' +
                ", correo='" + correo + '\'' +
                ", telefono='" + telefono + '\'' +
                ", direccion='" + direccion + '\'' +
                '}';
    }
}
