package com.edu.tecnocomfenalco.controlador;

import com.edu.tecnocomfenalco.modelo.Producto;
import com.edu.tecnocomfenalco.modelo.MovimientoInventario;
import com.edu.tecnocomfenalco.excepciones.ProductoException;
import com.edu.tecnocomfenalco.persistencia.MovimientoCSV;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Controlador para gestionar movimientos de inventario.
 * Implementa el patrón Modelo-Vista-Controlador (MVC).
 * 
 * @author chancro
 */
public class ControladorMovimiento {
    
    private List<MovimientoInventario> movimientos;
    private MovimientoCSV gestorCSV;
    private ControladorProducto controladorProducto;
    private int proximoId;
    
    /**
     * Constructor que inicializa el controlador.
     * 
     * @param controladorProducto referencia al controlador de productos
     */
    public ControladorMovimiento(ControladorProducto controladorProducto) {
        this.movimientos = new ArrayList<>();
        this.gestorCSV = new MovimientoCSV();
        this.controladorProducto = controladorProducto;
        this.proximoId = 1;
    }
    
    /**
     * Carga los movimientos desde el archivo CSV.
     * 
     * @throws Exception si ocurre error en la carga
     */
    public void cargarMovimientos() throws Exception {
        try {
            movimientos = gestorCSV.cargar();
            if (!movimientos.isEmpty()) {
                proximoId = movimientos.stream()
                    .mapToInt(MovimientoInventario::getId)
                    .max()
                    .orElse(0) + 1;
            }
        } catch (Exception e) {
            throw new Exception("Error al cargar movimientos: " + e.getMessage(), e);
        }
    }
    
    /**
     * Guarda los movimientos en el archivo CSV.
     * 
     * @throws Exception si ocurre error en el guardado
     */
    public void guardarMovimientos() throws Exception {
        try {
            gestorCSV.guardar(movimientos);
        } catch (Exception e) {
            throw new Exception("Error al guardar movimientos: " + e.getMessage(), e);
        }
    }
    
    /**
     * Registra una entrada de productos.
     * 
     * @param productoId id del producto
     * @param cantidad cantidad a ingresar
     * @param motivo motivo de la entrada
     * @param usuarioResponsable usuario que realiza la entrada
     * @return el movimiento registrado
     * @throws Exception si hay error
     */
    public MovimientoInventario registrarEntrada(int productoId, int cantidad, String motivo, String usuarioResponsable) throws Exception {
        try {
            if (cantidad <= 0) {
                throw new Exception("La cantidad debe ser mayor a 0");
            }
            
            Producto producto = controladorProducto.obtenerProductoPorId(productoId);
            if (producto == null) {
                throw new Exception("Producto con ID " + productoId + " no encontrado");
            }
            
            // Aumentar stock del producto
            producto.aumentarStock(cantidad);
            
            // Crear el movimiento
            MovimientoInventario movimiento = new MovimientoInventario(
                proximoId++,
                MovimientoInventario.TipoMovimiento.ENTRADA,
                productoId,
                cantidad,
                motivo,
                usuarioResponsable
            );
            
            movimientos.add(movimiento);
            controladorProducto.guardarProductos();
            guardarMovimientos();
            
            return movimiento;
        } catch (Exception e) {
            throw e;
        }
    }
    
    /**
     * Registra una salida de productos.
     * 
     * @param productoId id del producto
     * @param cantidad cantidad a sacar
     * @param motivo motivo de la salida
     * @param usuarioResponsable usuario que realiza la salida
     * @return el movimiento registrado
     * @throws Exception si hay error o stock insuficiente
     */
    public MovimientoInventario registrarSalida(int productoId, int cantidad, String motivo, String usuarioResponsable) throws Exception {
        try {
            if (cantidad <= 0) {
                throw new Exception("La cantidad debe ser mayor a 0");
            }
            
            Producto producto = controladorProducto.obtenerProductoPorId(productoId);
            if (producto == null) {
                throw new Exception("Producto con ID " + productoId + " no encontrado");
            }
            
            // Verificar stock
            if (!producto.tieneSuficienteStock(cantidad)) {
                throw new Exception("Stock insuficiente. Disponible: " + producto.getCantidad());
            }
            
            // Reducir stock del producto
            producto.reducirStock(cantidad);
            producto.incrementarVentas(cantidad); // Incrementar ventas totales
            
            // Crear el movimiento
            MovimientoInventario movimiento = new MovimientoInventario(
                proximoId++,
                MovimientoInventario.TipoMovimiento.SALIDA,
                productoId,
                cantidad,
                motivo,
                usuarioResponsable
            );
            
            movimientos.add(movimiento);
            controladorProducto.guardarProductos();
            guardarMovimientos();
            
            return movimiento;
        } catch (Exception e) {
            throw e;
        }
    }
    
    /**
     * Obtiene todos los movimientos.
     * 
     * @return lista de todos los movimientos
     */
    public List<MovimientoInventario> obtenerTodos() {
        return new ArrayList<>(movimientos);
    }
    
    /**
     * Obtiene movimientos de un producto específico.
     * 
     * @param productoId id del producto
     * @return lista de movimientos del producto
     */
    public List<MovimientoInventario> obtenerMovimientosPorProducto(int productoId) {
        return movimientos.stream()
            .filter(m -> m.getProductoId() == productoId)
            .collect(Collectors.toList());
    }
    
    /**
     * Obtiene movimientos de un tipo específico.
     * 
     * @param tipo tipo de movimiento (ENTRADA o SALIDA)
     * @return lista de movimientos del tipo especificado
     */
    public List<MovimientoInventario> obtenerMovimientosPorTipo(MovimientoInventario.TipoMovimiento tipo) {
        return movimientos.stream()
            .filter(m -> m.getTipo() == tipo)
            .collect(Collectors.toList());
    }
    
    /**
     * Obtiene el total de entradas.
     * 
     * @return cantidad total de entradas
     */
    public int obtenerTotalEntradas() {
        return (int) movimientos.stream()
            .filter(m -> m.getTipo() == MovimientoInventario.TipoMovimiento.ENTRADA)
            .mapToLong(MovimientoInventario::getCantidad)
            .sum();
    }
    
    /**
     * Obtiene el total de salidas.
     * 
     * @return cantidad total de salidas
     */
    public int obtenerTotalSalidas() {
        return (int) movimientos.stream()
            .filter(m -> m.getTipo() == MovimientoInventario.TipoMovimiento.SALIDA)
            .mapToLong(MovimientoInventario::getCantidad)
            .sum();
    }
}
