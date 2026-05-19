package com.edu.tecnocomfenalco.persistencia;

import com.edu.tecnocomfenalco.modelo.MovimientoInventario;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase MovimientoCSV que implementa GestorCSV para persistencia de movimientos.
 * Ejemplo de POLIMORFISMO e implementación de INTERFAZ.
 * 
 * @author chancro
 */
public class MovimientoCSV implements GestorCSV<MovimientoInventario> {
    
    private static final String RUTA = "datos/movimientos.csv";
    private static final String SEPARADOR = ",";
    private static final String ENCABEZADO = "ID,Tipo,ProductoID,Cantidad,Motivo,UsuarioResponsable,Fecha";
    
    /**
     * Constructor que verifica que la carpeta datos existe.
     */
    public MovimientoCSV() {
        try {
            Files.createDirectories(Paths.get("datos"));
        } catch (IOException e) {
            System.err.println("Error al crear carpeta datos: " + e.getMessage());
        }
    }
    
    /**
     * Guarda los movimientos en archivo CSV.
     * Implementación del método de la interfaz.
     * 
     * @param movimientos lista de movimientos a guardar
     * @throws IOException si ocurre error en la escritura
     */
    @Override
    public void guardar(List<MovimientoInventario> movimientos) throws Exception {
        try (BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream(RUTA), StandardCharsets.UTF_8))) {
            
            // Escribir encabezado
            writer.write(ENCABEZADO);
            writer.newLine();
            
            // Escribir movimientos
            for (MovimientoInventario movimiento : movimientos) {
                StringBuilder linea = new StringBuilder();
                linea.append(movimiento.getId()).append(SEPARADOR)
                    .append(movimiento.getTipo().name()).append(SEPARADOR)
                    .append(movimiento.getProductoId()).append(SEPARADOR)
                    .append(movimiento.getCantidad()).append(SEPARADOR)
                    .append(escaparCsv(movimiento.getMotivo())).append(SEPARADOR)
                    .append(escaparCsv(movimiento.getUsuarioResponsable())).append(SEPARADOR)
                    .append(movimiento.getFechaCreacion());
                
                writer.write(linea.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            throw new Exception("Error al guardar movimientos en CSV: " + e.getMessage(), e);
        }
    }
    
    /**
     * Carga los movimientos desde el archivo CSV.
     * Implementación del método de la interfaz.
     * 
     * @return lista de movimientos cargados
     * @throws IOException si ocurre error en la lectura
     */
    @Override
    public List<MovimientoInventario> cargar() throws Exception {
        List<MovimientoInventario> movimientos = new ArrayList<>();
        
        File archivo = new File(RUTA);
        if (!archivo.exists()) {
            return movimientos;
        }
        
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new FileInputStream(RUTA), StandardCharsets.UTF_8))) {
            
            String linea;
            boolean esEncabezado = true;
            
            while ((linea = reader.readLine()) != null) {
                if (esEncabezado) {
                    esEncabezado = false;
                    continue;
                }
                
                String[] datos = linea.split(SEPARADOR, -1);
                if (datos.length >= 6) {
                    try {
                        MovimientoInventario movimiento = new MovimientoInventario(
                            Integer.parseInt(datos[0].trim()),
                            MovimientoInventario.TipoMovimiento.valueOf(datos[1].trim()),
                            Integer.parseInt(datos[2].trim()),
                            Integer.parseInt(datos[3].trim()),
                            datos[4].trim(),
                            datos[5].trim()
                        );
                        movimientos.add(movimiento);
                    } catch (Exception e) {
                        System.err.println("Error al parsear linea: " + linea);
                    }
                }
            }
        } catch (IOException e) {
            throw new Exception("Error al cargar movimientos desde CSV: " + e.getMessage(), e);
        }
        
        return movimientos;
    }
    
    /**
     * Obtiene la ruta del archivo CSV.
     * 
     * @return ruta del archivo
     */
    @Override
    public String obtenerRutaArchivo() {
        return RUTA;
    }
    
    /**
     * Escapa caracteres especiales para CSV.
     * 
     * @param valor valor a escapar
     * @return valor escapado
     */
    private String escaparCsv(String valor) {
        if (valor == null) {
            return "";
        }
        if (valor.contains(SEPARADOR) || valor.contains("\"") || valor.contains("\n")) {
            return "\"" + valor.replace("\"", "\"\"") + "\"";
        }
        return valor;
    }
}
