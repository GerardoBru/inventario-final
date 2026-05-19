# Sistema de Gestión de Inventario - STF Group

## Descripción General
Sistema educativo de Gestión de Inventario desarrollado en Java con interfaz Swing y persistencia en CSV. Aplicación completa que demuestra los principios de la Programación Orientada a Objetos (POO).

## Características Implementadas

### 1. Gestión de Productos
- ✅ Registrar nuevos productos
- ✅ Actualizar información de productos
- ✅ Eliminar productos
- ✅ Búsqueda de productos por nombre
- ✅ Ver detalles completos de cada producto
- ✅ Mostrar cantidad y precio total del inventario

### 2. Control de Inventario
- ✅ Registrar entradas de productos
- ✅ Registrar salidas de productos
- ✅ Validación de stock disponible
- ✅ Historial de movimientos
- ✅ Cálculo de ventas totales por producto

### 3. Reportes
- ✅ Productos más vendidos (Top 5)
- ✅ Productos con bajo stock
- ✅ Productos sin stock (agotados)
- ✅ Valor total del inventario
- ✅ Resumen general de estadísticas
- ✅ Exportación de reportes a archivo

### 4. Gestión de Usuarios
- ✅ Autenticación de usuarios
- ✅ Sistema de roles (Administrador, Bodeguero, Encargado)
- ✅ Creación de nuevos usuarios
- ✅ Eliminación de usuarios
- ✅ Permisos específicos por rol

### 5. Persistencia
- ✅ Almacenamiento en CSV
- ✅ Carga automática de datos al iniciar
- ✅ Guardado automático después de cambios
- ✅ Manejo de caracteres especiales en CSV

## Conceptos POO Implementados

### 1. Herencia
```
Entidad (clase abstracta)
├── Producto
├── Usuario (clase abstracta)
│   ├── Administrador
│   ├── Bodeguero
│   └── Encargado
├── MovimientoInventario
└── Proveedor
```

### 2. Encapsulación
- Todos los atributos son privados
- Acceso controlado mediante getters/setters
- Validaciones en los setters (precios positivos, cantidades válidas, etc.)

### 3. Polimorfismo
- Método abstracto `obtenerDescripcion()` implementado en todas las entidades
- Método `obtenerPermisos()` implementado diferente para cada tipo de usuario
- Interfaz `GestorCSV<T>` implementada por ProductoCSV, UsuarioCSV, MovimientoCSV

### 4. Abstracción
- Clase abstracta `Entidad`: define atributos y métodos comunes
- Clase abstracta `Usuario`: define estructura base para usuarios
- Interfaz `GestorCSV<T>`: define contrato para persistencia

### 5. Colecciones
- ArrayList<Producto> para gestionar productos
- ArrayList<Usuario> para gestionar usuarios
- ArrayList<MovimientoInventario> para registrar movimientos
- Uso de Streams para filtrado y transformación de datos

### 6. Manejo de Excepciones
- `ProductoException`: excepciones relacionadas con productos
- `UsuarioException`: excepciones relacionadas con usuarios
- Try-catch en operaciones críticas
- Validación de datos antes de procesar

## Estructura del Proyecto

```
gention-de-inventario/
├── src/main/java/
│   └── com/edu/tecnocomfenalco/
│       ├── GentionDeInventario.java (Clase principal)
│       ├── modelo/
│       │   ├── Entidad.java (Clase abstracta)
│       │   ├── Producto.java
│       │   ├── Usuario.java (Clase abstracta)
│       │   ├── Administrador.java
│       │   ├── Bodeguero.java
│       │   ├── Encargado.java
│       │   ├── MovimientoInventario.java
│       │   └── Proveedor.java
│       ├── persistencia/
│       │   ├── GestorCSV.java (Interfaz)
│       │   ├── ProductoCSV.java
│       │   ├── UsuarioCSV.java
│       │   └── MovimientoCSV.java
│       ├── controlador/
│       │   ├── ControladorProducto.java
│       │   ├── ControladorUsuario.java
│       │   └── ControladorMovimiento.java
│       ├── excepciones/
│       │   ├── ProductoException.java
│       │   └── UsuarioException.java
│       └── gui/
│           ├── VentanaPrincipal.java
│           ├── DialogoLogin.java
│           ├── PanelProductos.java
│           ├── DialogoProducto.java
│           ├── PanelMovimientos.java
│           ├── DialogoMovimiento.java
│           ├── PanelReportes.java
│           ├── PanelUsuarios.java
│           └── DialogoUsuario.java
├── target/
│   └── classes/ (Archivos compilados)
├── datos/
│   ├── productos.csv
│   ├── usuarios.csv
│   └── movimientos.csv
├── pom.xml
└── ejecutar.bat
```

## Usuarios Predeterminados para Pruebas

Antes de usar la aplicación, debe crear usuarios. Los datos se guardan en CSV:

**Para crear un administrador desde la interfaz:**
- Correo: admin@stf.com
- Contraseña: admin123
- Departamento: Administración

**Para crear un bodeguero:**
- Correo: bodega@stf.com
- Contraseña: bodega123
- Ubicación: Bodega Principal

**Para crear un encargado:**
- Correo: encargado@stf.com
- Contraseña: encargado123
- Sección: Ventas

## Cómo Ejecutar

### Opción 1: Ejecutar el script (Windows)
```bash
cd "c:\Users\chancro\Desktop\Inventario Final\gention-de-inventario"
ejecutar.bat
```

### Opción 2: Ejecutar directamente desde la terminal
```bash
cd "c:\Users\chancro\Desktop\Inventario Final\gention-de-inventario"
java -cp target/classes com.edu.tecnocomfenalco.GentionDeInventario
```

### Opción 3: Usar Maven (si está instalado)
```bash
cd "c:\Users\chancro\Desktop\Inventario Final\gention-de-inventario"
mvn clean compile exec:java -Dexec.mainClass="com.edu.tecnocomfenalco.GentionDeInventario"
```

## Funcionalidades por Rol

### Administrador
- Gestionar productos (crear, actualizar, eliminar)
- Registrar movimientos (entradas y salidas)
- Ver reportes completos
- Gestionar usuarios
- Ver perfil y permisos

### Bodeguero
- Ver productos y stock disponible
- Visualizar existencia en bodega
- Ver perfil

### Encargado
- Ver productos y stock
- Registrar movimientos
- Actualizar cantidades
- Ver perfil

## Datos Persistentes

Los datos se guardan automáticamente en archivos CSV en la carpeta `datos/`:

- **productos.csv**: Contiene todos los productos registrados
- **usuarios.csv**: Contiene todos los usuarios del sistema
- **movimientos.csv**: Contiene el historial de entradas y salidas

Estos archivos se cargan automáticamente al iniciar la aplicación.

## Ejemplo de Uso

1. **Iniciar la aplicación**
2. **Crear un usuario administrativo** (usar el panel de usuarios)
3. **Autenticarse con ese usuario**
4. **Crear productos** (nombre, código, precio, cantidad, categoría)
5. **Registrar movimientos** (entradas y salidas)
6. **Ver reportes** para analizar el inventario

## Validaciones Implementadas

- ✅ Precio no puede ser negativo
- ✅ Cantidad no puede ser negativa
- ✅ Correo debe contener @
- ✅ Código de producto debe ser único
- ✅ Validación de stock insuficiente en salidas
- ✅ Campos requeridos no pueden estar vacíos

## Requisitos del Sistema

- Java 24.0.2 o superior
- 100 MB de espacio en disco
- SO: Windows 7+, Linux, macOS

## Tecnologías Utilizadas

- **Lenguaje**: Java 24
- **GUI**: Swing
- **Persistencia**: CSV
- **Patrón de Diseño**: Modelo-Vista-Controlador (MVC)

## Mejoras Futuras Sugeridas

- Implementar base de datos SQL (MySQL/PostgreSQL)
- Agregar más tipos de reportes
- Implementar autenticación encriptada
- Agregar gestión de clientes y proveedores
- Exportación a Excel
- Gráficos estadísticos
- API REST

## Notas Importantes

- Los datos se guardan en archivos CSV en la carpeta `datos/`
- Asegúrese de tener permisos de lectura/escritura en esa carpeta
- Los cambios se guardan automáticamente
- La aplicación carga los datos al iniciar

## Autor

Desarrollado como proyecto educativo para el curso de Programación Orientada a Objetos

## Licencia

Uso educativo - STF Group 2026
