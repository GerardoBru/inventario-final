package com.edu.tecnocomfenalco.controlador;

import com.edu.tecnocomfenalco.modelo.Producto;
import com.edu.tecnocomfenalco.excepciones.ProductoException;
import com.edu.tecnocomfenalco.persistencia.ProductoCSV;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Controlador para gestionar productos.
 * Implementa el patrón Modelo-Vista-Controlador (MVC).
 * 
 * @author chancro
 */
public class ControladorProducto {
    
    private List<Producto> productos;
    private ProductoCSV gestorCSV;
    private int proximoId;
    
    /**
     * Constructor que inicializa el controlador.
     */
    public ControladorProducto() {
        this.productos = new ArrayList<>();
        this.gestorCSV = new ProductoCSV();
        this.proximoId = 1;
    }
    
    /**
     * Carga los productos desde el archivo CSV.
     * 
     * @throws ProductoException si ocurre error en la carga
     */
    public void cargarProductos() throws ProductoException {
        try {
            productos = gestorCSV.cargar();
            if (!productos.isEmpty()) {
                proximoId = productos.stream()
                    .mapToInt(Producto::getId)
                    .max()
                    .orElse(0) + 1;
            }
        } catch (Exception e) {
            throw new ProductoException("Error al cargar productos: " + e.getMessage(), e);
        }
    }
    
    /**
     * Guarda los productos en el archivo CSV.
     * 
     * @throws ProductoException si ocurre error en el guardado
     */
    public void guardarProductos() throws ProductoException {
        try {
            gestorCSV.guardar(productos);
        } catch (Exception e) {
            throw new ProductoException("Error al guardar productos: " + e.getMessage(), e);
        }
    }
    
    /**
     * Registra un nuevo producto.
     * 
     * @param nombre nombre del producto
     * @param descripcion descripción del producto
     * @param precio precio unitario
     * @param cantidad cantidad disponible
     * @param codigo código del producto
     * @param categoria categoría del producto
     * @return el producto creado
     * @throws ProductoException si los datos son inválidos
     */
    public Producto crearProducto(String nombre, String descripcion, String tallas, double precio, 
                                 int cantidad, String codigo, String categoria) throws ProductoException {
        try {
            if (nombre == null || nombre.trim().isEmpty()) {
                throw new ProductoException("El nombre del producto es requerido");
            }
            if (precio < 0) {
                throw new ProductoException("El precio no puede ser negativo");
            }
            if (cantidad < 0) {
                throw new ProductoException("La cantidad no puede ser negativa");
            }
            
            // Verificar que el código sea único
            if (productos.stream().anyMatch(p -> p.getCodigo().equals(codigo))) {
                throw new ProductoException("El código " + codigo + " ya existe");
            }
            
            Producto producto = new Producto(proximoId++, nombre, descripcion, tallas, precio, cantidad, codigo, categoria);
            productos.add(producto);
            guardarProductos();
            return producto;
        } catch (ProductoException e) {
            throw e;
        } catch (Exception e) {
            throw new ProductoException("Error al crear producto: " + e.getMessage(), e);
        }
    }
    
    /**
     * Actualiza un producto existente.
     * 
     * @param id id del producto
     * @param nombre nuevo nombre
     * @param descripcion nueva descripción
     * @param tallas nuevas tallas
     * @param precio nuevo precio
     * @param cantidad nueva cantidad
     * @param categoria nueva categoría
     * @throws ProductoException si el producto no existe o hay error
     */
    public void actualizarProducto(int id, String nombre, String descripcion, String tallas,
                                  double precio, int cantidad, String categoria) throws ProductoException {
        try {
            Producto producto = obtenerProductoPorId(id);
            if (producto == null) {
                throw new ProductoException("Producto con ID " + id + " no encontrado");
            }
            
            producto.setNombre(nombre);
            producto.setDescripcion(descripcion);
            producto.setTallas(tallas);
            producto.setPrecio(precio);
            producto.setCantidad(cantidad);
            producto.setCategoria(categoria);
            
            guardarProductos();
        } catch (ProductoException e) {
            throw e;
        } catch (Exception e) {
            throw new ProductoException("Error al actualizar producto: " + e.getMessage(), e);
        }
    }
    
    /**
     * Elimina un producto del inventario.
     * 
     * @param id id del producto a eliminar
     * @throws ProductoException si el producto no existe
     */
    public void eliminarProducto(int id) throws ProductoException {
        try {
            Producto producto = obtenerProductoPorId(id);
            if (producto == null) {
                throw new ProductoException("Producto con ID " + id + " no encontrado");
            }
            
            productos.remove(producto);
            guardarProductos();
        } catch (ProductoException e) {
            throw e;
        } catch (Exception e) {
            throw new ProductoException("Error al eliminar producto: " + e.getMessage(), e);
        }
    }
    
    /**
     * Obtiene un producto por su ID.
     * 
     * @param id id del producto
     * @return el producto o null si no existe
     */
    public Producto obtenerProductoPorId(int id) {
        return productos.stream()
            .filter(p -> p.getId() == id)
            .findFirst()
            .orElse(null);
    }
    
    /**
     * Busca productos por nombre (búsqueda parcial).
     * 
     * @param nombre nombre a buscar
     * @return lista de productos encontrados
     */
    public List<Producto> buscarPorNombre(String nombre) {
        return productos.stream()
            .filter(p -> p.getNombre().toLowerCase().contains(nombre.toLowerCase()))
            .collect(Collectors.toList());
    }
    
    /**
     * Obtiene todos los productos.
     * 
     * @return lista de todos los productos
     */
    public List<Producto> obtenerTodos() {
        return new ArrayList<>(productos);
    }
    
    /**
     * Obtiene los productos más vendidos.
     * 
     * @param cantidad cantidad de productos a retornar
     * @return lista de productos más vendidos
     */
    public List<Producto> obtenerProductosMasVendidos(int cantidad) {
        return productos.stream()
            .sorted((p1, p2) -> Integer.compare(p2.getVentasTotal(), p1.getVentasTotal()))
            .limit(cantidad)
            .collect(Collectors.toList());
    }
    
    /**
     * Obtiene los productos con bajo stock.
     * 
     * @param minimo cantidad mínima de stock
     * @return lista de productos con stock bajo
     */
    public List<Producto> obtenerProductosBajoStock(int minimo) {
        return productos.stream()
            .filter(p -> p.getCantidad() < minimo)
            .collect(Collectors.toList());
    }
    
    /**
     * Obtiene el total del inventario en valor.
     * 
     * @return valor total del inventario
     */
    public double obtenerValorTotalInventario() {
        return productos.stream()
            .mapToDouble(p -> p.getPrecio() * p.getCantidad())
            .sum();
    }
    
    /**
     * Obtiene la cantidad total de productos.
     * 
     * @return cantidad total
     */
    public int obtenerCantidadTotalProductos() {
        return productos.stream()
            .mapToInt(Producto::getCantidad)
            .sum();
    }
}
