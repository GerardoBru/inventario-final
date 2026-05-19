package com.edu.tecnocomfenalco.excepciones;

/**
 * Excepción personalizada para operaciones con productos.
 * Manejo de excepciones según requerimientos.
 * 
 * @author chancro
 */
public class ProductoException extends Exception {
    private static final long serialVersionUID = 1L;
    
    public ProductoException(String mensaje) {
        super(mensaje);
    }
    
    public ProductoException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}
