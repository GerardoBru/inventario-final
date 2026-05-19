package com.edu.tecnocomfenalco.persistencia;

import com.edu.tecnocomfenalco.modelo.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase UsuarioCSV que implementa GestorCSV para persistencia de usuarios.
 * Ejemplo de POLIMORFISMO e implementación de INTERFAZ.
 * 
 * @author chancro
 */
public class UsuarioCSV implements GestorCSV<Usuario> {
    
    private static final String RUTA = "datos/usuarios.csv";
    private static final String SEPARADOR = ",";
    private static final String ENCABEZADO = "ID,Nombre,Correo,Contraseña,Rol,CampoAdicional";
    
    /**
     * Constructor que verifica que la carpeta datos existe.
     */
    public UsuarioCSV() {
        try {
            Files.createDirectories(Paths.get("datos"));
        } catch (IOException e) {
            System.err.println("Error al crear carpeta datos: " + e.getMessage());
        }
    }
    
    /**
     * Guarda los usuarios en archivo CSV.
     * Implementación del método de la interfaz.
     * 
     * @param usuarios lista de usuarios a guardar
     * @throws IOException si ocurre error en la escritura
     */
    @Override
    public void guardar(List<Usuario> usuarios) throws Exception {
        try (BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream(RUTA), StandardCharsets.UTF_8))) {
            
            // Escribir encabezado
            writer.write(ENCABEZADO);
            writer.newLine();
            
            // Escribir usuarios
            for (Usuario usuario : usuarios) {
                StringBuilder linea = new StringBuilder();
                linea.append(usuario.getId()).append(SEPARADOR)
                    .append(escaparCsv(usuario.getNombre())).append(SEPARADOR)
                    .append(escaparCsv(usuario.getCorreo())).append(SEPARADOR)
                    .append(usuario.getContraseña()).append(SEPARADOR)
                    .append(usuario.getRol().name()).append(SEPARADOR);
                
                // Campo adicional según el tipo de usuario
                if (usuario instanceof Administrador) {
                    Administrador admin = (Administrador) usuario;
                    linea.append(escaparCsv(admin.getDepartamento()));
                } else if (usuario instanceof Bodeguero) {
                    Bodeguero bodeguero = (Bodeguero) usuario;
                    linea.append(escaparCsv(bodeguero.getUbicacionBodega()));
                } else if (usuario instanceof Encargado) {
                    Encargado encargado = (Encargado) usuario;
                    linea.append(escaparCsv(encargado.getSeccion()));
                }
                
                writer.write(linea.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            throw new Exception("Error al guardar usuarios en CSV: " + e.getMessage(), e);
        }
    }
    
    /**
     * Carga los usuarios desde el archivo CSV.
     * Implementación del método de la interfaz.
     * 
     * @return lista de usuarios cargados
     * @throws IOException si ocurre error en la lectura
     */
    @Override
    public List<Usuario> cargar() throws Exception {
        List<Usuario> usuarios = new ArrayList<>();
        
        File archivo = new File(RUTA);
        if (!archivo.exists()) {
            return usuarios;
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
                if (datos.length >= 5) {
                    try {
                        int id = Integer.parseInt(datos[0].trim());
                        String nombre = datos[1].trim();
                        String correo = datos[2].trim();
                        String contraseña = datos[3].trim();
                        Usuario.Rol rol = Usuario.Rol.valueOf(datos[4].trim());
                        String campoAdicional = datos.length > 5 ? datos[5].trim() : "";
                        
                        Usuario usuario = null;
                        switch (rol) {
                            case ADMINISTRADOR:
                                usuario = new Administrador(id, nombre, correo, contraseña, campoAdicional);
                                break;
                            case BODEGUERO:
                                usuario = new Bodeguero(id, nombre, correo, contraseña, campoAdicional);
                                break;
                            case ENCARGADO:
                                usuario = new Encargado(id, nombre, correo, contraseña, campoAdicional);
                                break;
                        }
                        
                        if (usuario != null) {
                            usuarios.add(usuario);
                        }
                    } catch (Exception e) {
                        System.err.println("Error al parsear linea: " + linea);
                    }
                }
            }
        } catch (IOException e) {
            throw new Exception("Error al cargar usuarios desde CSV: " + e.getMessage(), e);
        }
        
        return usuarios;
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
