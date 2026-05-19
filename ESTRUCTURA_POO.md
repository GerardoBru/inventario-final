# Estructura de Programación Orientada a Objetos (POO)

Este documento detalla cómo se implementan los 4 pilares de la POO en el Sistema de Gestión de Inventario.

## 1. HERENCIA

### Definición
La herencia permite que una clase herede atributos y métodos de otra clase, creando una jerarquía de clases.

### Implementación en el Proyecto

#### Jerarquía Principal: Entidad
```
Entidad (clase abstracta)
├── Producto
├── MovimientoInventario  
├── Proveedor
└── Usuario (clase abstracta)
    ├── Administrador
    ├── Bodeguero
    └── Encargado
```

#### Ejemplo: Clase Entidad (Superclase)
```java
public abstract class Entidad implements Serializable {
    protected int id;
    protected LocalDateTime fechaCreacion;
    
    public Entidad(int id) {
        this.id = id;
        this.fechaCreacion = LocalDateTime.now();
    }
    
    public abstract String obtenerDescripcion();
}
```

#### Ejemplo: Clase Producto (Subclase)
```java
public class Producto extends Entidad {
    private String nombre;
    private String descripcion;
    private double precio;
    private int cantidad;
    // ... más atributos
    
    public Producto(int id, String nombre, ...) {
        super(id);  // Llamar al constructor de la superclase
        this.nombre = nombre;
        // ... inicializar otros atributos
    }
    
    @Override
    public String obtenerDescripcion() {
        return "Producto: " + nombre + " - Código: " + codigo;
    }
}
```

#### Ejemplo: Jerarquía de Usuarios
```java
public abstract class Usuario extends Entidad {
    protected String nombre;
    protected String correo;
    
    public abstract String obtenerPermisos();
}

public class Administrador extends Usuario {
    private String departamento;
    
    @Override
    public String obtenerPermisos() {
        return "Crear/actualizar/eliminar productos...";
    }
}

public class Bodeguero extends Usuario {
    private String ubicacionBodega;
    
    @Override
    public String obtenerPermisos() {
        return "Ver stock de productos, ver existencia en bodega";
    }
}
```

#### Ventajas Implementadas
✓ Reutilización de código (id, fechaCreacion están en Entidad)
✓ Polimorfismo: Se pueden tratar todos como Entidad
✓ Mantenimiento centralizado de atributos comunes

---

## 2. ENCAPSULACIÓN

### Definición
La encapsulación oculta los detalles internos de una clase, permitiendo acceso controlado mediante métodos getter y setter.

### Implementación en el Proyecto

#### Principio: Atributos Privados
```java
public class Producto extends Entidad {
    // ATRIBUTOS PRIVADOS - No accesibles directamente
    private String nombre;
    private String descripcion;
    private double precio;
    private int cantidad;
    
    // GETTERS - Lectura controlada
    public String getNombre() {
        return nombre;
    }
    
    public double getPrecio() {
        return precio;
    }
    
    // SETTERS - Escritura validada
    public void setNombre(String nombre) {
        if (nombre != null && !nombre.trim().isEmpty()) {
            this.nombre = nombre;
        }
    }
    
    public void setPrecio(double precio) throws IllegalArgumentException {
        if (precio < 0) {
            throw new IllegalArgumentException("El precio no puede ser negativo");
        }
        this.precio = precio;
    }
    
    public void setCantidad(int cantidad) throws IllegalArgumentException {
        if (cantidad < 0) {
            throw new IllegalArgumentException("La cantidad no puede ser negativa");
        }
        this.cantidad = cantidad;
    }
}
```

#### Métodos que Encapsulan Lógica
```java
public boolean tieneSuficienteStock(int cantidad) {
    return this.cantidad >= cantidad;
}

public void reducirStock(int cantidad) throws IllegalArgumentException {
    if (!tieneSuficienteStock(cantidad)) {
        throw new IllegalArgumentException("Stock insuficiente");
    }
    this.cantidad -= cantidad;
}

public void aumentarStock(int cantidad) {
    this.cantidad += cantidad;
}
```

#### Ventajas Implementadas
✓ Validación de datos (precio no negativo, cantidad válida)
✓ Integridad: No se pueden asignar valores inválidos
✓ Modificación segura: Cambios internos no afectan el código cliente
✓ Lógica centralizada: Reglas de negocio en un solo lugar

---

## 3. POLIMORFISMO

### Definición
Polimorfismo permite que objetos de diferentes clases respondandiferentemente al mismo mensaje.

### Implementación en el Proyecto

#### Polimorfismo por Sobrescritura (Override)
```java
// Clase base abstracta define el contrato
public abstract class Entidad {
    public abstract String obtenerDescripcion();
}

// Diferentes implementaciones
public class Producto extends Entidad {
    @Override
    public String obtenerDescripcion() {
        return "Producto: " + nombre + " - Código: " + codigo + 
               " - Cantidad: " + cantidad + " - Precio: $" + precio;
    }
}

public class MovimientoInventario extends Entidad {
    @Override
    public String obtenerDescripcion() {
        return tipo.getDescripcion() + ": Producto ID " + productoId + 
               " - Cantidad: " + cantidad;
    }
}

public class Proveedor extends Entidad {
    @Override
    public String obtenerDescripcion() {
        return "Proveedor: " + nombre + " - Contacto: " + contacto + 
               " - Teléfono: " + telefono;
    }
}

// Uso polimórfico
List<Entidad> entidades = new ArrayList<>();
entidades.add(new Producto(...));
entidades.add(new MovimientoInventario(...));
entidades.add(new Proveedor(...));

// Cada uno responde diferente al mismo mensaje
for (Entidad e : entidades) {
    System.out.println(e.obtenerDescripcion()); // Diferentes respuestas
}
```

#### Polimorfismo en Usuarios
```java
public abstract class Usuario extends Entidad {
    public abstract String obtenerPermisos();
}

public class Administrador extends Usuario {
    @Override
    public String obtenerPermisos() {
        return "ADMIN: Crear/actualizar/eliminar productos, registrar movimientos...";
    }
}

public class Bodeguero extends Usuario {
    @Override
    public String obtenerPermisos() {
        return "BODEGUERO: Ver stock, ver existencia en bodega";
    }
}

public class Encargado extends Usuario {
    @Override
    public String obtenerPermisos() {
        return "ENCARGADO: Actualizar cantidad, registrar clientes";
    }
}

// Uso
Usuario usuario = controladorUsuario.autenticar(correo, pass);
System.out.println(usuario.obtenerPermisos()); // Diferentes según el tipo
```

#### Polimorfismo por Interfaz
```java
public interface GestorCSV<T> {
    void guardar(List<T> registros) throws Exception;
    List<T> cargar() throws Exception;
    String obtenerRutaArchivo();
}

public class ProductoCSV implements GestorCSV<Producto> {
    @Override
    public void guardar(List<Producto> productos) throws Exception {
        // Implementación específica para Producto
    }
}

public class UsuarioCSV implements GestorCSV<Usuario> {
    @Override
    public void guardar(List<Usuario> usuarios) throws Exception {
        // Implementación específica para Usuario
    }
}

// Uso polimórfico
GestorCSV<Producto> gestorProductos = new ProductoCSV();
GestorCSV<Usuario> gestorUsuarios = new UsuarioCSV();

gestorProductos.guardar(productos);  // Usa ProductoCSV.guardar()
gestorUsuarios.guardar(usuarios);    // Usa UsuarioCSV.guardar()
```

#### Ventajas Implementadas
✓ Flexibilidad: Nuevos tipos se pueden añadir sin cambiar código existente
✓ Extensibilidad: Fácil crear nuevos roles o tipos de entidades
✓ Mantenimiento: Código más limpio y modular
✓ Reutilización: Mismo código trabaja con diferentes tipos

---

## 4. ABSTRACCIÓN

### Definición
Abstracción es el proceso de ocultar detalles complejos y mostrar solo las características esenciales.

### Implementación en el Proyecto

#### Clase Abstracta: Entidad
```java
public abstract class Entidad implements Serializable {
    // Atributos comunes
    protected int id;
    protected LocalDateTime fechaCreacion;
    
    // Método concreto - mismo para todos
    public int getId() {
        return id;
    }
    
    // Método abstracto - cada subclase define su propia versión
    public abstract String obtenerDescripcion();
    
    // Los detalles de cómo obtener la descripción están ocultos
}
```

#### Clase Abstracta: Usuario
```java
public abstract class Usuario extends Entidad {
    // Atributos comunes a todos los usuarios
    protected String nombre;
    protected String correo;
    protected String contraseña;
    protected Rol rol;
    
    // Métodos concretos comunes
    public boolean verificarCredenciales(String correo, String contraseña) {
        return this.correo.equals(correo) && this.contraseña.equals(contraseña);
    }
    
    // Método abstracto - cada rol tiene diferentes permisos
    public abstract String obtenerPermisos();
}
```

#### Enum para Abstracción de Roles
```java
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
```

#### Enum para Tipos de Movimiento
```java
public enum TipoMovimiento {
    ENTRADA("Entrada"),
    SALIDA("Salida");
    
    private final String descripcion;
    
    TipoMovimiento(String descripcion) {
        this.descripcion = descripcion;
    }
}
```

#### Interfaz Genérica: GestorCSV
```java
public interface GestorCSV<T> {
    // Abstrae los detalles de cómo guardar/cargar
    void guardar(List<T> registros) throws Exception;
    List<T> cargar() throws Exception;
    String obtenerRutaArchivo();
}

// El cliente solo ve la interfaz, no los detalles de CSV
GestorCSV<Producto> gestor = new ProductoCSV();
List<Producto> productos = gestor.cargar(); // ¿Cómo se carga? Detalles ocultos
```

#### Excepciones Personalizadas
```java
// Abstracción de errores
public class ProductoException extends Exception {
    public ProductoException(String mensaje) {
        super(mensaje);
    }
}

// El código cliente solo maneja la excepción, no los detalles
try {
    controladorProducto.crearProducto(...);
} catch (ProductoException e) {
    // Sabe que hubo un error con producto, no necesita conocer detalles
    System.out.println(e.getMessage());
}
```

#### Ventajas Implementadas
✓ Complejidad oculta: Los usuarios ven una interfaz simple
✓ Cambios internos: Pueden cambiar la implementación sin afectar usuarios
✓ Modulación: Código más organizado y fácil de entender
✓ Contratos claros: Las interfaces definen qué es necesario implementar

---

## 5. COLECCIONES

### Definición
Uso de estructuras de datos para manejar grupos de objetos.

### Implementación en el Proyecto

#### ArrayList<Producto>
```java
public class ControladorProducto {
    private List<Producto> productos; // ArrayList internamente
    
    public ControladorProducto() {
        this.productos = new ArrayList<>();
    }
    
    public void crearProducto(...) {
        Producto p = new Producto(...);
        productos.add(p);  // Agregar a la colección
        guardarProductos();
    }
    
    public Producto obtenerProductoPorId(int id) {
        return productos.stream()
            .filter(p -> p.getId() == id)
            .findFirst()
            .orElse(null);
    }
    
    public List<Producto> obtenerTodos() {
        return new ArrayList<>(productos);  // Retornar copia
    }
}
```

#### Búsqueda con Streams
```java
public List<Producto> buscarPorNombre(String nombre) {
    return productos.stream()
        .filter(p -> p.getNombre().toLowerCase().contains(nombre.toLowerCase()))
        .collect(Collectors.toList());
}
```

#### Ordenamiento y Limitación
```java
public List<Producto> obtenerProductosMasVendidos(int cantidad) {
    return productos.stream()
        .sorted((p1, p2) -> Integer.compare(p2.getVentasTotal(), p1.getVentasTotal()))
        .limit(cantidad)
        .collect(Collectors.toList());
}
```

#### Filtrado Avanzado
```java
public List<Producto> obtenerProductosBajoStock(int minimo) {
    return productos.stream()
        .filter(p -> p.getCantidad() < minimo)
        .collect(Collectors.toList());
}
```

#### Agregaciones
```java
public double obtenerValorTotalInventario() {
    return productos.stream()
        .mapToDouble(p -> p.getPrecio() * p.getCantidad())
        .sum();
}

public int obtenerCantidadTotalProductos() {
    return productos.stream()
        .mapToInt(Producto::getCantidad)
        .sum();
}
```

#### Ventajas Implementadas
✓ Flexibilidad: Tamaño dinámico
✓ Operaciones comunes: add, remove, get, stream
✓ Functional Programming: Uso de lambdas y streams
✓ Rendimiento: Operaciones optimizadas

---

## 6. MANEJO DE EXCEPCIONES

### Definición
Mecanismo para manejar errores y situaciones excepcionales de forma controlada.

### Implementación en el Proyecto

#### Excepciones Personalizadas
```java
public class ProductoException extends Exception {
    public ProductoException(String mensaje) {
        super(mensaje);
    }
    
    public ProductoException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}

public class UsuarioException extends Exception {
    public UsuarioException(String mensaje) {
        super(mensaje);
    }
}
```

#### Try-Catch en Operaciones Críticas
```java
public Producto crearProducto(String nombre, ...) throws ProductoException {
    try {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new ProductoException("El nombre del producto es requerido");
        }
        if (precio < 0) {
            throw new ProductoException("El precio no puede ser negativo");
        }
        
        Producto producto = new Producto(...);
        productos.add(producto);
        guardarProductos();
        return producto;
    } catch (ProductoException e) {
        throw e;  // Re-lanzar excepción personalizada
    } catch (Exception e) {
        throw new ProductoException("Error al crear producto: " + e.getMessage(), e);
    }
}
```

#### Validación de Stock
```java
public MovimientoInventario registrarSalida(...) throws Exception {
    try {
        if (!producto.tieneSuficienteStock(cantidad)) {
            throw new Exception("Stock insuficiente. Disponible: " + producto.getCantidad());
        }
        
        producto.reducirStock(cantidad);  // Ya valida internamente
        // ... resto del código
    } catch (IllegalArgumentException e) {
        throw new Exception("Validación falló: " + e.getMessage());
    }
}
```

#### Persistencia con Excepciones
```java
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
```

#### Manejo en GUI
```java
try {
    controlador.crearProducto(nombre, descripcion, precio, ...);
    JOptionPane.showMessageDialog(this, "Producto creado correctamente");
} catch (ProductoException e) {
    JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
}
```

#### Ventajas Implementadas
✓ Separación de errores: Tipos específicos de excepciones
✓ Recuperación: Manejo controlado de errores
✓ Debugging: Trazas claras del origen del error
✓ Confiabilidad: Código más robusto

---

## Diagrama de Clases (Conceptual)

```
┌─────────────────────────────┐
│       Entidad (ABC)         │
│  - id: int                  │
│  - fechaCreacion: LocalDT   │
│  + getId(): int             │
│  + obtenerDescripcion(): S* │
└──────────────┬──────────────┘
       │
       ├─ Producto
       │  - nombre, precio, cantidad, código
       │  + obtenerDescripcion()
       │  + reducirStock(int)
       │  + aumentarStock(int)
       │
       ├─ MovimientoInventario
       │  - tipo, productoId, cantidad, motivo
       │  + obtenerDescripcion()
       │
       ├─ Proveedor
       │  - nombre, contacto, email
       │  + obtenerDescripcion()
       │
       └─ Usuario (ABC)
          - nombre, correo, contraseña, rol
          + obtenerPermisos(): S*
          │
          ├─ Administrador
          │  - departamento
          │  + obtenerPermisos()
          │
          ├─ Bodeguero
          │  - ubicacionBodega
          │  + obtenerPermisos()
          │
          └─ Encargado
             - seccion
             + obtenerPermisos()
```

---

## Conclusión

Este proyecto demuestra cómo los 4 pilares de la POO (Herencia, Encapsulación, Polimorfismo, Abstracción) trabajan juntos para crear un código:
- **Mantenible**: Fácil de cambiar y actualizar
- **Extensible**: Fácil de agregar nuevas funcionalidades
- **Reutilizable**: El código se puede usar en muchos lugares
- **Confiable**: Validaciones y manejo de errores
- **Escalable**: Puede crecer sin perder estructura
