# 📊 Resumen Visual - Diagramas del Sistema

## Diagrama de Clases - Vista General

```mermaid
graph LR
    subgraph Modelo["MODELO DE DATOS"]
        Entidad["Entidad (Base)"]
        Usuario["Usuario (Abstract)"]
        Producto["Producto"]
        Movimiento["MovimientoInventario"]
        Proveedor["Proveedor"]
        
        Entidad -->|hereda| Usuario
        Entidad -->|hereda| Producto
        Entidad -->|hereda| Movimiento
        Entidad -->|hereda| Proveedor
        
        Rol["Rol: ADMIN/BODEGUERO/ENCARGADO"]
        
        Admin["Administrador"]
        Bodeguero["Bodeguero"]
        Encargado["Encargado"]
        
        Usuario -->|hereda| Admin
        Usuario -->|hereda| Bodeguero
        Usuario -->|hereda| Encargado
        Usuario -->|usa| Rol
    end
    
    subgraph Controladores["LÓGICA DE NEGOCIO"]
        CtrlProducto["ControladorProducto"]
        CtrlUsuario["ControladorUsuario"]
        CtrlMovimiento["ControladorMovimiento"]
        
        CtrlProducto -->|gestiona| Producto
        CtrlUsuario -->|gestiona| Usuario
        CtrlMovimiento -->|gestiona| Movimiento
        CtrlMovimiento -->|depende de| CtrlProducto
    end
    
    subgraph Persistencia["ALMACENAMIENTO"]
        GestorCSV["GestorCSV<T>"]
        ProductoCSV["ProductoCSV"]
        UsuarioCSV["UsuarioCSV"]
        MovimientoCSV["MovimientoCSV"]
        
        GestorCSV -->|implementa| ProductoCSV
        GestorCSV -->|implementa| UsuarioCSV
        GestorCSV -->|implementa| MovimientoCSV
    end
    
    subgraph GUI["INTERFAZ GRÁFICA"]
        VentanaPrincipal["VentanaPrincipal"]
        Login["DialogoLogin"]
        PanelProd["PanelProductos"]
        PanelMov["PanelMovimientos"]
        PanelRep["PanelReportes"]
        PanelUsr["PanelUsuarios"]
        
        VentanaPrincipal -->|contiene| Login
        VentanaPrincipal -->|contiene| PanelProd
        VentanaPrincipal -->|contiene| PanelMov
        VentanaPrincipal -->|contiene| PanelRep
        VentanaPrincipal -->|contiene| PanelUsr
    end
    
    Controladores -->|usa| Persistencia
    GUI -->|usa| Controladores
    
    style Modelo fill:#e1f5ff
    style Controladores fill:#f3e5f5
    style Persistencia fill:#e8f5e9
    style GUI fill:#fff3e0
```

---

## Casos de Uso por Rol - Vista Simplificada

```mermaid
graph TB
    subgraph Administrador["👨‍💼 ADMINISTRADOR - Acceso Total"]
        CA1["✅ Crear Productos"]
        CA2["✅ Actualizar Productos"]
        CA3["✅ Eliminar Productos"]
        CA4["✅ Crear Usuarios"]
        CA5["✅ Actualizar Usuarios"]
        CA6["✅ Eliminar Usuarios"]
        CA7["✅ Ver Reportes"]
        CA8["✅ Exportar PDF"]
        CA9["✅ Registrar Movimientos"]
        CA10["✅ Ver Movimientos"]
    end
    
    subgraph Bodeguero["👨‍🔧 BODEGUERO - Gestión de Stock"]
        CB1["✅ Ver Productos"]
        CB2["✅ Registrar Entrada"]
        CB3["✅ Registrar Salida"]
        CB4["✅ Ver Reportes"]
        CB5["✅ Ver Bajo Stock"]
    end
    
    subgraph Encargado["👨‍💻 ENCARGADO - Operaciones"]
        CE1["✅ Ver Productos"]
        CE2["✅ Actualizar Cantidad"]
        CE3["✅ Registrar Movimientos"]
        CE4["✅ Ver Reportes (Limitados)"]
    end
    
    style Administrador fill:#bbdefb
    style Bodeguero fill:#c8e6c9
    style Encargado fill:#ffe0b2
```

---

## Ciclo de Vida de Transacciones

### Entrada de Producto

```mermaid
sequenceDiagram
    actor User as Usuario (Admin/Bodeguero)
    participant GUI as PanelMovimientos
    participant Ctrl as ControladorMovimiento
    participant Prod as ControladorProducto
    participant CSV as MovimientoCSV
    
    User->>GUI: Selecciona "Registrar Entrada"
    GUI->>Ctrl: registrarEntrada(productoId, cantidad)
    Ctrl->>Prod: obtenerProductoPorId(id)
    Prod-->>Ctrl: Producto
    Ctrl->>Prod: aumentarStock(cantidad)
    Prod->>Prod: setCantidad(nueva)
    Ctrl->>CSV: guardar(movimientos)
    CSV-->>Ctrl: ✓ Guardado
    Ctrl-->>GUI: MovimientoInventario
    GUI->>User: ✓ Entrada registrada
```

### Salida de Producto (Venta)

```mermaid
sequenceDiagram
    actor User as Usuario (Admin/Bodeguero/Encargado)
    participant GUI as PanelMovimientos
    participant Ctrl as ControladorMovimiento
    participant Prod as ControladorProducto
    participant Val as Validación
    participant CSV as MovimientoCSV
    
    User->>GUI: Selecciona "Registrar Salida"
    GUI->>Ctrl: registrarSalida(productoId, cantidad)
    Ctrl->>Prod: obtenerProductoPorId(id)
    Prod-->>Ctrl: Producto
    Ctrl->>Val: tieneSuficienteStock(cantidad)?
    Val-->>Ctrl: true/false
    alt Stock Suficiente
        Ctrl->>Prod: reducirStock(cantidad)
        Prod->>Prod: setCantidad(nueva)
        Prod->>Prod: incrementarVentas(cantidad)
        Ctrl->>CSV: guardar(movimientos)
        CSV-->>Ctrl: ✓ Guardado
    else Stock Insuficiente
        Ctrl-->>GUI: ❌ Error: Stock insuficiente
    end
    Ctrl-->>GUI: MovimientoInventario
    GUI->>User: ✓ Salida registrada
```

---

## Flujo de Autenticación

```mermaid
sequenceDiagram
    actor User as Usuario
    participant GUI as DialogoLogin
    participant Ctrl as ControladorUsuario
    participant CSV as UsuarioCSV
    
    GUI->>GUI: Mostrar formulario login
    User->>GUI: Ingresa correo y contraseña
    GUI->>Ctrl: autenticar(correo, password)
    Ctrl->>CSV: cargar() - obtener usuarios
    CSV-->>Ctrl: Lista de usuarios
    Ctrl->>Ctrl: Buscar credenciales válidas
    alt Encontrado
        Ctrl->>Ctrl: setUsuarioActual(usuario)
        Ctrl-->>GUI: Usuario autenticado
        GUI->>GUI: Cerrar diálogo
        GUI->>User: ✓ Bienvenido al sistema
    else No Encontrado
        Ctrl-->>GUI: ❌ Credenciales inválidas
        GUI->>User: ❌ Error de autenticación
    end
```

---

## Flujo de Generación de Reportes y PDF

```mermaid
sequenceDiagram
    actor Admin as Administrador
    participant GUI as PanelReportes
    participant CtrlP as ControladorProducto
    participant CtrlM as ControladorMovimiento
    participant PDF as PDFBox
    
    Admin->>GUI: Hace clic "Exportar a PDF"
    GUI->>GUI: Abre diálogo de guardado
    Admin->>GUI: Selecciona ubicación
    GUI->>CtrlP: obtenerTodos()
    GUI->>CtrlP: obtenerProductosMasVendidos(5)
    GUI->>CtrlP: obtenerProductosBajoStock(10)
    GUI->>CtrlM: obtenerTotalEntradas()
    GUI->>CtrlM: obtenerTotalSalidas()
    
    GUI->>PDF: crear PDDocument
    PDF-->>GUI: Documento creado
    
    GUI->>PDF: Agregar título
    GUI->>PDF: Agregar resumen general
    GUI->>PDF: Agregar más vendidos
    GUI->>PDF: Agregar bajo stock
    GUI->>PDF: Agregar tabla de productos
    
    par Verificación de páginas
        GUI->>GUI: ¿Espacio disponible?
        GUI->>PDF: Si no, crear nueva página
    end
    
    GUI->>PDF: Guardar archivo
    PDF-->>GUI: ✓ PDF generado
    GUI->>Admin: ✓ Reporte exportado a [ruta]
```

---

## Estructura de Datos - Relaciones

```mermaid
erDiagram
    USUARIO ||--o{ MOVIMIENTO : "crea"
    PRODUCTO ||--o{ MOVIMIENTO : "participa"
    USUARIO }o--|| PROVEEDOR : "gestiona"
    PRODUCTO }o--|| PROVEEDOR : "proviene"
    
    USUARIO {
        int id
        string nombre
        string correo
        string rol
        datetime fechaCreacion
    }
    
    PRODUCTO {
        int id
        string nombre
        string codigo
        double precio
        int cantidad
        int ventasTotal
        datetime fechaCreacion
    }
    
    MOVIMIENTO {
        int id
        int productoId
        string tipo
        int cantidad
        string motivo
        datetime fecha
    }
    
    PROVEEDOR {
        int id
        string nombre
        string contacto
        string telefono
    }
```

---

## Matriz de Características por Versión

### ✅ v1.0 Actual

| Feature | Estado | Descripción |
|---------|--------|-------------|
| CRUD Productos | ✅ | Crear, leer, actualizar, eliminar |
| CRUD Usuarios | ✅ | Gestión de usuarios por roles |
| Autenticación | ✅ | Login con correo/contraseña |
| Movimientos | ✅ | Entrada/Salida de inventario |
| Reportes | ✅ | Dashboard de estadísticas |
| Exportar PDF | ✅ | Generar reportes en PDF |
| CSV Persistence | ✅ | Almacenamiento en archivos CSV |

### 🔮 v2.0 Planeado

| Feature | Estado | Descripción |
|---------|--------|-------------|
| Base de datos SQL | ⏳ | Migrar de CSV a SQL |
| Gráficos | ⏳ | Visualización de datos |
| Backup automático | ⏳ | Respaldos programados |
| API REST | ⏳ | Integración con otros sistemas |
| Mobile app | ⏳ | Aplicación móvil |

---

## Métricas del Proyecto

```
📊 ESTADÍSTICAS DEL CÓDIGO

Total de Clases: 26
├── Modelos: 8
├── Controladores: 3
├── GUI: 8
├── Persistencia: 4
└── Excepciones: 2

Líneas de Código: ~3,500
├── Java: ~3,400
├── XML (pom.xml): ~100
└── Documentación: ~2,500

Dependencias: 1
└── Apache PDFBox 2.0.27

Archivos de Datos: 3
├── productos.csv
├── usuarios.csv
└── movimientos.csv
```

---

## 🎯 Conclusión

Este sistema proporciona una solución completa para la gestión de inventarios con:

✅ **Arquitectura clara** - Capas bien definidas  
✅ **Seguridad** - Autenticación y roles  
✅ **Persistencia** - Almacenamiento en CSV  
✅ **Reportes** - Análisis con exportación a PDF  
✅ **Escalabilidad** - Preparado para mejoras futuras  

---

**Generado: 18 de Mayo de 2026**  
**Sistema: Gestión de Inventario STF GROUP v1.0**
