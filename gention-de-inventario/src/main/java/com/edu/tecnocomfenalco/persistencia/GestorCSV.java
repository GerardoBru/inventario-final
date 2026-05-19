package com.edu.tecnocomfenalco.persistencia;

import java.util.List;

/**
 * Interfaz GestorCSV que define los métodos para persistencia.
 * Ejemplo de ABSTRACCIÓN e INTERFACES.
 * 
 * @author chancro
 * @param <T> tipo genérico de entidad
 */
public interface GestorCSV<T> {
    
    /**
     * Guarda todos los registros en el archivo CSV.
     * 
     * @param registros lista de registros a guardar
     * @throws Exception si ocurre error en la escritura
     */
    void guardar(List<T> registros) throws Exception;
    
    /**
     * Carga todos los registros desde el archivo CSV.
     * 
     * @return lista de registros cargados
     * @throws Exception si ocurre error en la lectura
     */
    List<T> cargar() throws Exception;
    
    /**
     * Obtiene la ruta del archivo CSV.
     * 
     * @return ruta del archivo
     */
    String obtenerRutaArchivo();
}
