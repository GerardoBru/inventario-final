package com.edu.tecnocomfenalco.gui;

import com.edu.tecnocomfenalco.controlador.ControladorProducto;
import com.edu.tecnocomfenalco.controlador.ControladorMovimiento;
import com.edu.tecnocomfenalco.modelo.Producto;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Panel para visualizar reportes del inventario.
 * Muestra información como productos más vendidos, bajo stock, valor total, etc.
 * 
 * @author chancro
 */
public class PanelReportes extends JPanel {
    private static final long serialVersionUID = 1L;
    
    private ControladorProducto controladorProducto;
    private ControladorMovimiento controladorMovimiento;
    private JTextArea areaReportes;
    
    /**
     * Constructor del panel de reportes.
     * 
     * @param controladorProducto controlador de productos
     * @param controladorMovimiento controlador de movimientos
     */
    public PanelReportes(ControladorProducto controladorProducto, ControladorMovimiento controladorMovimiento) {
        this.controladorProducto = controladorProducto;
        this.controladorMovimiento = controladorMovimiento;
        
        setLayout(new BorderLayout());
        construirGUI();
        generarReportes();
    }
    
    /**
     * Construye la interfaz gráfica del panel.
     */
    private void construirGUI() {
        // Panel de botones
        JPanel panelBotones = new JPanel();
        JButton btnActualizar = new JButton("Actualizar Reportes");
        btnActualizar.addActionListener(e -> generarReportes());
        panelBotones.add(btnActualizar);
        
        JButton btnExportar = new JButton("Exportar");
        btnExportar.addActionListener(e -> exportarReportes());
        panelBotones.add(btnExportar);
        
        // Área de reportes
        areaReportes = new JTextArea();
        areaReportes.setFont(new Font("Courier New", Font.PLAIN, 12));
        areaReportes.setEditable(false);
        areaReportes.setLineWrap(true);
        areaReportes.setWrapStyleWord(true);
        
        JScrollPane scrollPane = new JScrollPane(areaReportes);
        
        // Agregar componentes al panel
        add(panelBotones, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }
    
    /**
     * Genera los reportes del inventario.
     */
    private void generarReportes() {
        StringBuilder reporte = new StringBuilder();
        
        reporte.append("╔════════════════════════════════════════════════════════════════╗\n");
        reporte.append("║              REPORTES DEL INVENTARIO - STF GROUP               ║\n");
        reporte.append("╚════════════════════════════════════════════════════════════════╝\n\n");
        
        // Resumen General
        reporte.append("┌─ RESUMEN GENERAL ─────────────────────────────────────────────┐\n");
        reporte.append(String.format("Total de Productos: %d%n", controladorProducto.obtenerTodos().size()));
        reporte.append(String.format("Cantidad Total en Stock: %d unidades%n", controladorProducto.obtenerCantidadTotalProductos()));
        reporte.append(String.format("Valor Total del Inventario: $ %,.0f COP%n", controladorProducto.obtenerValorTotalInventario()));
        reporte.append(String.format("Total de Entradas: %d%n", controladorMovimiento.obtenerTotalEntradas()));
        reporte.append(String.format("Total de Salidas: %d%n", controladorMovimiento.obtenerTotalSalidas()));
        reporte.append("└───────────────────────────────────────────────────────────────┘\n\n");
        
        // Productos Más Vendidos
        reporte.append("┌─ PRODUCTOS MÁS VENDIDOS (Top 5) ──────────────────────────────┐\n");
        List<Producto> masVendidos = controladorProducto.obtenerProductosMasVendidos(5);
        if (masVendidos.isEmpty()) {
            reporte.append("No hay productos vendidos aún.\n");
        } else {
            for (int i = 0; i < masVendidos.size(); i++) {
                Producto p = masVendidos.get(i);
                reporte.append(String.format("%d. %s - Ventas: %d - Precio Unitario: $ %,.0f%n", 
                    i + 1, p.getNombre(), p.getVentasTotal(), p.getPrecio()));
            }
        }
        reporte.append("└───────────────────────────────────────────────────────────────┘\n\n");
        
        // Productos con Bajo Stock
        reporte.append("┌─ PRODUCTOS CON BAJO STOCK (< 10) ────────────────────────────┐\n");
        List<Producto> bajoStock = controladorProducto.obtenerProductosBajoStock(10);
        if (bajoStock.isEmpty()) {
            reporte.append("Todos los productos tienen stock suficiente.\n");
        } else {
            for (Producto p : bajoStock) {
                reporte.append(String.format("• %s - Código: %s - Stock: %d%n", 
                    p.getNombre(), p.getCodigo(), p.getCantidad()));
            }
        }
        reporte.append("└───────────────────────────────────────────────────────────────┘\n\n");
        
        // Productos sin Stock
        reporte.append("┌─ PRODUCTOS SIN STOCK ─────────────────────────────────────────┐\n");
        List<Producto> sinStock = controladorProducto.obtenerProductosBajoStock(1);
        int productosAgotados = (int) sinStock.stream().filter(p -> p.getCantidad() == 0).count();
        if (productosAgotados == 0) {
            reporte.append("No hay productos agotados.\n");
        } else {
            for (Producto p : sinStock) {
                if (p.getCantidad() == 0) {
                    reporte.append(String.format("• %s - Código: %s%n", p.getNombre(), p.getCodigo()));
                }
            }
        }
        reporte.append("└───────────────────────────────────────────────────────────────┘\n\n");
        
        // Todos los Productos
        reporte.append("┌─ LISTA COMPLETA DE PRODUCTOS ─────────────────────────────────┐\n");
        reporte.append(String.format("%-3s | %-30s | %-10s | %-10s | %-10s%n", 
            "ID", "Nombre", "Código", "Stock", "Precio"));
        reporte.append("────┼────────────────────────────────┼────────────┼────────────┼──────────────\n");
        
        for (Producto p : controladorProducto.obtenerTodos()) {
            String nombre = p.getNombre().length() > 28 ? p.getNombre().substring(0, 28) : p.getNombre();
            reporte.append(String.format("%-3d | %-30s | %-10s | %-10d | $ %,.0f%n", 
                p.getId(), nombre, p.getCodigo(), p.getCantidad(), p.getPrecio()));
        }
        reporte.append("└───────────────────────────────────────────────────────────────┘\n");
        
        areaReportes.setText(reporte.toString());
    }
    
    /**
     * Exporta los reportes a archivo PDF.
     */
    private void exportarReportes() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        
        // Filtro para archivos PDF
        FileNameExtensionFilter filtroPDF = new FileNameExtensionFilter("Documentos PDF (*.pdf)", "pdf");
        fileChooser.addChoosableFileFilter(filtroPDF);
        fileChooser.setFileFilter(filtroPDF);
        
        int resultado = fileChooser.showSaveDialog(this);
        
        if (resultado == JFileChooser.APPROVE_OPTION) {
            try {
                File archivo = fileChooser.getSelectedFile();
                
                // Asegurar que el archivo tenga extensión .pdf
                if (!archivo.getName().endsWith(".pdf")) {
                    archivo = new File(archivo.getAbsolutePath() + ".pdf");
                }
                
                generarPDF(archivo);
                JOptionPane.showMessageDialog(this, "Reporte exportado correctamente a:\n" + archivo.getAbsolutePath(), 
                        "Exito", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error al exportar: " + e.getMessage(), 
                        "Error", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        }
    }
    
    /**
     * Genera un documento PDF con el reporte del inventario.
     * @param archivo archivo destino
     * @throws Exception si ocurre error
     */
    private void generarPDF(File archivo) throws Exception {
        PDDocument document = new PDDocument();
        
        try {
            float margen = 40;
            float altoLinea = 12;
            float minYPos = margen + 20; // Minimo espacio antes de crear nueva pagina
            
            PDPage page = new PDPage();
            document.addPage(page);
            
            PDPageContentStream contentStream = new PDPageContentStream(document, page);
            
            try {
                float yPos = 750;
                
                PDFont fontTitulo = PDType1Font.HELVETICA_BOLD;
                PDFont fontSubtitulo = PDType1Font.HELVETICA_BOLD;
                PDFont fontNormal = PDType1Font.HELVETICA;
                PDFont fontMono = PDType1Font.COURIER;
                
                // Titulo principal
                contentStream.setFont(fontTitulo, 16);
                contentStream.beginText();
                contentStream.newLineAtOffset(margen, yPos);
                contentStream.showText("REPORTE DE INVENTARIO - STF GROUP");
                contentStream.endText();
                yPos -= altoLinea * 1.5f;
                
                // Fecha de generacion
                contentStream.setFont(fontNormal, 10);
                contentStream.beginText();
                contentStream.newLineAtOffset(margen, yPos);
                contentStream.showText("Generado: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")));
                contentStream.endText();
                yPos -= altoLinea * 2;
                
                // RESUMEN GENERAL
                contentStream.setFont(fontSubtitulo, 12);
                contentStream.beginText();
                contentStream.newLineAtOffset(margen, yPos);
                contentStream.showText("RESUMEN GENERAL");
                contentStream.endText();
                yPos -= altoLinea * 1.2f;
                
                contentStream.setFont(fontNormal, 10);
                yPos = agregarLinea(contentStream, margen, yPos, fontNormal, 10,
                    "Total de Productos: " + controladorProducto.obtenerTodos().size());
                yPos = agregarLinea(contentStream, margen, yPos, fontNormal, 10,
                    "Cantidad Total en Stock: " + controladorProducto.obtenerCantidadTotalProductos() + " unidades");
                yPos = agregarLinea(contentStream, margen, yPos, fontNormal, 10,
                    String.format("Valor Total del Inventario: $ %,.0f COP", controladorProducto.obtenerValorTotalInventario()));
                yPos = agregarLinea(contentStream, margen, yPos, fontNormal, 10,
                    "Total de Entradas: " + controladorMovimiento.obtenerTotalEntradas());
                yPos = agregarLinea(contentStream, margen, yPos, fontNormal, 10,
                    "Total de Salidas: " + controladorMovimiento.obtenerTotalSalidas());
                yPos -= altoLinea;
                
                // PRODUCTOS MAS VENDIDOS
                if (yPos < minYPos + (altoLinea * 8)) {
                    contentStream.close();
                    page = new PDPage();
                    document.addPage(page);
                    contentStream = new PDPageContentStream(document, page);
                    yPos = 750;
                }
                
                contentStream.setFont(fontSubtitulo, 12);
                contentStream.beginText();
                contentStream.newLineAtOffset(margen, yPos);
                contentStream.showText("PRODUCTOS MAS VENDIDOS (Top 5)");
                contentStream.endText();
                yPos -= altoLinea * 1.2f;
                
                List<Producto> masVendidos = controladorProducto.obtenerProductosMasVendidos(5);
                contentStream.setFont(fontNormal, 9);
                if (masVendidos.isEmpty()) {
                    yPos = agregarLinea(contentStream, margen, yPos, fontNormal, 9, "No hay productos vendidos aun.");
                } else {
                    for (int i = 0; i < masVendidos.size(); i++) {
                        if (yPos < minYPos) {
                            contentStream.close();
                            page = new PDPage();
                            document.addPage(page);
                            contentStream = new PDPageContentStream(document, page);
                            yPos = 750;
                        }
                        Producto p = masVendidos.get(i);
                        String linea = String.format("%d. %s - Ventas: %d - Precio: $ %,.0f",
                            i + 1, p.getNombre(), p.getVentasTotal(), p.getPrecio());
                        yPos = agregarLinea(contentStream, margen, yPos, fontNormal, 9, linea);
                    }
                }
                yPos -= altoLinea;
                
                // PRODUCTOS CON BAJO STOCK
                if (yPos < minYPos + (altoLinea * 8)) {
                    contentStream.close();
                    page = new PDPage();
                    document.addPage(page);
                    contentStream = new PDPageContentStream(document, page);
                    yPos = 750;
                }
                
                contentStream.setFont(fontSubtitulo, 12);
                contentStream.beginText();
                contentStream.newLineAtOffset(margen, yPos);
                contentStream.showText("PRODUCTOS CON BAJO STOCK (< 10 unidades)");
                contentStream.endText();
                yPos -= altoLinea * 1.2f;
                
                List<Producto> bajoStock = controladorProducto.obtenerProductosBajoStock(10);
                contentStream.setFont(fontNormal, 9);
                if (bajoStock.isEmpty()) {
                    yPos = agregarLinea(contentStream, margen, yPos, fontNormal, 9, "Todos los productos tienen stock suficiente.");
                } else {
                    for (Producto p : bajoStock) {
                        if (yPos < minYPos) {
                            contentStream.close();
                            page = new PDPage();
                            document.addPage(page);
                            contentStream = new PDPageContentStream(document, page);
                            yPos = 750;
                        }
                        String linea = String.format("- %s - Codigo: %s - Stock: %d",
                            p.getNombre(), p.getCodigo(), p.getCantidad());
                        yPos = agregarLinea(contentStream, margen, yPos, fontNormal, 9, linea);
                    }
                }
                yPos -= altoLinea;
                
                // PRODUCTOS SIN STOCK
                if (yPos < minYPos + (altoLinea * 6)) {
                    contentStream.close();
                    page = new PDPage();
                    document.addPage(page);
                    contentStream = new PDPageContentStream(document, page);
                    yPos = 750;
                }
                
                contentStream.setFont(fontSubtitulo, 12);
                contentStream.beginText();
                contentStream.newLineAtOffset(margen, yPos);
                contentStream.showText("PRODUCTOS SIN STOCK");
                contentStream.endText();
                yPos -= altoLinea * 1.2f;
                
                List<Producto> sinStock = controladorProducto.obtenerProductosBajoStock(1);
                int productosAgotados = (int) sinStock.stream().filter(p -> p.getCantidad() == 0).count();
                contentStream.setFont(fontNormal, 9);
                if (productosAgotados == 0) {
                    yPos = agregarLinea(contentStream, margen, yPos, fontNormal, 9, "No hay productos agotados.");
                } else {
                    for (Producto p : sinStock) {
                        if (p.getCantidad() == 0) {
                            if (yPos < minYPos) {
                                contentStream.close();
                                page = new PDPage();
                                document.addPage(page);
                                contentStream = new PDPageContentStream(document, page);
                                yPos = 750;
                            }
                            String linea = String.format("- %s - Codigo: %s", p.getNombre(), p.getCodigo());
                            yPos = agregarLinea(contentStream, margen, yPos, fontNormal, 9, linea);
                        }
                    }
                }
                yPos -= altoLinea;
                
                // LISTA COMPLETA DE PRODUCTOS
                if (yPos < minYPos + (altoLinea * 10)) {
                    contentStream.close();
                    page = new PDPage();
                    document.addPage(page);
                    contentStream = new PDPageContentStream(document, page);
                    yPos = 750;
                }
                
                contentStream.setFont(fontSubtitulo, 12);
                contentStream.beginText();
                contentStream.newLineAtOffset(margen, yPos);
                contentStream.showText("LISTA COMPLETA DE PRODUCTOS");
                contentStream.endText();
                yPos -= altoLinea * 1.2f;
                
                // Encabezados de tabla
                contentStream.setFont(fontMono, 8);
                yPos = agregarLinea(contentStream, margen, yPos, fontMono, 8,
                    "ID   Nombre                  Codigo    Tallas          Precio     Stock");
                yPos = agregarLinea(contentStream, margen, yPos, fontMono, 8,
                    "---  ----------------------  --------  ------  -----------  ------");
                
                contentStream.setFont(fontMono, 8);
                for (Producto p : controladorProducto.obtenerTodos()) {
                    if (yPos < minYPos) {
                        contentStream.close();
                        page = new PDPage();
                        document.addPage(page);
                        contentStream = new PDPageContentStream(document, page);
                        yPos = 750;
                    }
                    String nombre = p.getNombre().length() > 20 ? p.getNombre().substring(0, 20) : p.getNombre();
                    String tallas = p.getTallas().length() > 10 ? p.getTallas().substring(0, 10) : p.getTallas();
                    String linea = String.format("%-3d  %-22s  %-8s  %-10s  $ %,.0f  %5d",
                        p.getId(), nombre, p.getCodigo(), tallas, p.getPrecio(), p.getCantidad());
                    yPos = agregarLinea(contentStream, margen, yPos, fontMono, 8, linea);
                }
                
            } finally {
                contentStream.close();
            }
            
            document.save(archivo);
            
        } finally {
            document.close();
        }
    }
    
    /**
     * Agrega una línea de texto al documento PDF con fuente y tamaño especificados.
     */
    private float agregarLinea(PDPageContentStream contentStream, float margen, float yPos, 
                              PDFont font, int size, String texto) throws Exception {
        contentStream.setFont(font, size);
        contentStream.beginText();
        contentStream.newLineAtOffset(margen, yPos);
        contentStream.showText(texto);
        contentStream.endText();
        return yPos - 12;
    }
    
    /**
     * Verifica si necesita crear una nueva página.
     * Nota: PDFBox maneja múltiples páginas automáticamente, no es necesario cerrar/abrir streams
     */
    private float verificarNuevaPage(PDDocument document, PDPageContentStream contentStream, float yPos, float margen) throws Exception {
        // Simplemente retorna yPos sin modificar - el contenido se ajusta automáticamente
        // Si se necesitan múltiples páginas, usar una estrategia diferente
        if (yPos < 100) {
            yPos = 750; // Reiniciar posición vertical para nueva sección
        }
        return yPos;
    }
}
