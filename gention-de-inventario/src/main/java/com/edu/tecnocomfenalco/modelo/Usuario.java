package com.edu.tecnocomfenalco.modelo;

/**
 * Clase abstracta Usuario que representa los usuarios del sistema.
 * Define el tipo de usuario mediante la enum Rol.
 * Ejemplo de ABSTRACCIÓN.
 * 
 * @author chancro
 */
public abstract class Usuario extends Entidad {
    private static final long serialVersionUID = 1L;
    
    protected String nombre;
    protected String correo;
    protected String contraseña;
    protected Rol rol;
    
    /**
     * Enum que define los roles disponibles en el sistema.
     */
    public enum Rol {
        ADMINISTRADOR("Administrador"),
        BODEGUERO("Bodeguero"),
        ENCARGADO("Encargado");
        
        private final String descripcion;
        
        Rol(String descripcion) {
            this.descripcion = descripcion;
        }
        
        public String getDescripcion() {
            return descripcion;
        }
    }
    
    /**
     * Constructor para crear un usuario.
     * 
     * @param id identificador único
     * @param nombre nombre del usuario
     * @param correo correo electrónico
     * @param contraseña contraseña
     * @param rol rol del usuario
     */
    public Usuario(int id, String nombre, String correo, String contraseña, Rol rol) {
        super(id);
        this.nombre = nombre;
        this.correo = correo;
        this.contraseña = contraseña;
        this.rol = rol;
    }
    
    /**
     * Constructor vacío.
     */
    public Usuario() {
        super();
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
    
    public String getCorreo() {
        return correo;
    }
    
    public void setCorreo(String correo) {
        if (correo != null && correo.contains("@")) {
            this.correo = correo;
        }
    }
    
    public String getContraseña() {
        return contraseña;
    }
    
    public void setContraseña(String contraseña) {
        if (contraseña != null && !contraseña.isEmpty()) {
            this.contraseña = contraseña;
        }
    }
    
    public Rol getRol() {
        return rol;
    }
    
    public void setRol(Rol rol) {
        this.rol = rol;
    }
    
    /**
     * Verifica las credenciales del usuario.
     * 
     * @param correo correo a verificar
     * @param contraseña contraseña a verificar
     * @return true si las credenciales coinciden
     */
    public boolean verificarCredenciales(String correo, String contraseña) {
        return this.correo.equals(correo) && this.contraseña.equals(contraseña);
    }
    
    /**
     * Método abstracto que debe implementar cada subclase.
     * 
     * @return descripción de los permisos del usuario
     */
    public abstract String obtenerPermisos();
    
    /**
     * Implementación del método abstracto obtenerDescripcion.
     * Ejemplo de POLIMORFISMO.
     * 
     * @return descripción del usuario
     */
    @Override
    public String obtenerDescripcion() {
        return "Usuario: " + nombre + " (" + rol.getDescripcion() + ") - " + correo;
    }
    
    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", correo='" + correo + '\'' +
                ", rol=" + rol +
                ", fechaCreacion=" + fechaCreacion +
                '}';
    }
}
