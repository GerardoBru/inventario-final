package com.edu.tecnocomfenalco.persistencia;

import com.edu.tecnocomfenalco.modelo.Producto;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase ProductoCSV que implementa GestorCSV para persistencia de productos.
 * Ejemplo de POLIMORFISMO e implementación de INTERFAZ.
 * 
 * @author chancro
 */
public class ProductoCSV implements GestorCSV<Producto> {
    
    private static final String RUTA = "datos/productos.csv";
    private static final String SEPARADOR = ",";
    private static final String ENCABEZADO = "ID,Nombre,Descripcion,Tallas,Precio,Cantidad,Codigo,Categoria,VentasTotal";
    
    /**
     * Constructor que verifica que la carpeta datos existe.
     */
    public ProductoCSV() {
        try {
            Files.createDirectories(Paths.get("datos"));
        } catch (IOException e) {
            System.err.println("Error al crear carpeta datos: " + e.getMessage());
        }
    }
    
    /**
     * Guarda los productos en archivo CSV.
     * Implementación del método de la interfaz.
     * 
     * @param productos lista de productos a guardar
     * @throws IOException si ocurre error en la escritura
     */
    @Override
    public void guardar(List<Producto> productos) throws Exception {
        try (BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream(RUTA), StandardCharsets.UTF_8))) {
            
            // Escribir encabezado
            writer.write(ENCABEZADO);
            writer.newLine();
            
            // Escribir productos
            for (Producto producto : productos) {
                StringBuilder linea = new StringBuilder();
                linea.append(producto.getId()).append(SEPARADOR)
                    .append(escaparCsv(producto.getNombre())).append(SEPARADOR)
                    .append(escaparCsv(producto.getDescripcion())).append(SEPARADOR)
                    .append(escaparCsv(producto.getTallas())).append(SEPARADOR)
                    .append(producto.getPrecio()).append(SEPARADOR)
                    .append(producto.getCantidad()).append(SEPARADOR)
                    .append(escaparCsv(producto.getCodigo())).append(SEPARADOR)
                    .append(escaparCsv(producto.getCategoria())).append(SEPARADOR)
                    .append(producto.getVentasTotal());
                
                writer.write(linea.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            throw new Exception("Error al guardar productos en CSV: " + e.getMessage(), e);
        }
    }
    
    /**
     * Carga los productos desde el archivo CSV.
     * Implementación del método de la interfaz.
     * 
     * @return lista de productos cargados
     * @throws IOException si ocurre error en la lectura
     */
    @Override
    public List<Producto> cargar() throws Exception {
        List<Producto> productos = new ArrayList<>();
        
        File archivo = new File(RUTA);
        if (!archivo.exists()) {
            return productos; // Retorna lista vacía si no existe el archivo
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
                if (datos.length >= 9) {
                    try {
                        Producto producto = new Producto(
                            Integer.parseInt(datos[0].trim()),
                            datos[1].trim(),
                            datos[2].trim(),
                            datos[3].trim(),
                            Double.parseDouble(datos[4].trim()),
                            Integer.parseInt(datos[5].trim()),
                            datos[6].trim(),
                            datos[7].trim()
                        );
                        producto.setVentasTotal(Integer.parseInt(datos[8].trim()));
                        productos.add(producto);
                    } catch (NumberFormatException e) {
                        System.err.println("Error al parsear línea: " + linea);
                    }
                }
            }
        } catch (IOException e) {
            throw new Exception("Error al cargar productos desde CSV: " + e.getMessage(), e);
        }
        
        return productos;
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
