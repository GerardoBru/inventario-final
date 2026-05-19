# Documentación - Sistema de Gestión de Inventario

## 📚 Indice de Documentación

Este directorio contiene toda la documentación del sistema de gestión de inventario STF GROUP.

### 📊 Diagramas UML

#### 1. [Diagrama de Clases](DIAGRAMA_CLASES.md)
Descripción de todas las clases del sistema, sus atributos, métodos y relaciones.

**Contenido:**
- 📦 Estructura de capas (Presentación, Control, Modelo, Persistencia)
- 🔗 Relaciones de herencia e implementación
- 📋 Tabla de componentes y responsabilidades
- ✅ Notas de diseño y patrones utilizados

**Clases Principales:**
- **Modelo**: Entidad, Usuario, Administrador, Bodeguero, Encargado, Producto, MovimientoInventario, Proveedor
- **Controladores**: ControladorProducto, ControladorUsuario, ControladorMovimiento
- **GUI**: VentanaPrincipal, DialogoLogin, PanelProductos, PanelMovimientos, PanelReportes, PanelUsuarios
- **Persistencia**: GestorCSV, ProductoCSV, UsuarioCSV, MovimientoCSV

---

#### 2. [Diagrama de Casos de Uso](DIAGRAMA_CASOS_DE_USO.md)
Representación de las interacciones entre actores (usuarios) y el sistema.

**Contenido:**
- 👥 Actores del sistema (Administrador, Bodeguero, Encargado)
- 🎯 Casos de uso organizados por módulo
- 🔐 Autenticación
- 📦 Gestión de Productos
- 👤 Gestión de Usuarios
- 📊 Reportes y Análisis
- 📋 Movimientos de Inventario
- ✅ Matriz de permisos por rol
- 🔄 Flujo principal de la aplicación

---

## 🏗️ Arquitectura del Sistema

### Capas Arquitectónicas

```
┌─────────────────────────────────────────┐
│     CAPA DE PRESENTACIÓN (GUI)          │
│  VentanaPrincipal, Paneles, Diálogos   │
└────────────────┬────────────────────────┘
                 │
┌────────────────▼────────────────────────┐
│     CAPA DE CONTROL (Controladores)     │
│ ControladorProducto, Usuario, Movimiento│
└────────────────┬────────────────────────┘
                 │
┌────────────────▼────────────────────────┐
│   CAPA DE MODELO (Lógica de Negocio)    │
│ Entidad, Usuario, Producto, Movimiento  │
└────────────────┬────────────────────────┘
                 │
┌────────────────▼────────────────────────┐
│   CAPA DE PERSISTENCIA (Almacenamiento) │
│       GestorCSV, ProductoCSV, etc.      │
└─────────────────────────────────────────┘
```

### Patrones de Diseño Utilizados

1. **MVC (Model-View-Controller)**
   - Modelo: Clases de entidades
   - Vista: Componentes GUI
   - Controlador: Lógica de negocio

2. **Herencia Polimórfica**
   - Diferentes roles (Administrador, Bodeguero, Encargado) heredan de Usuario

3. **Genericidad**
   - GestorCSV<T> para reutilizar lógica de persistencia

4. **Excepciones Personalizadas**
   - ProductoException, UsuarioException

---

## 📋 Módulos Funcionales

### 🔐 **Autenticación**
- Iniciar sesión con correo y contraseña
- Validación de credenciales
- Cerrar sesión
- Recuperación de contraseña (opcional)

### 📦 **Gestión de Productos**
- Crear, leer, actualizar, eliminar (CRUD) productos
- Buscar productos por nombre o código
- Registro de entrada (compras, devoluciones)
- Registro de salida (ventas)
- Validación de stock disponible

### 👥 **Gestión de Usuarios**
- Crear usuarios con diferentes roles
- Administrar permisos por rol
- Ver historial de actividades
- Actualizar datos de usuario
- Eliminar usuarios

### 📊 **Reportes y Análisis**
- Dashboard de estadísticas
- Top 5 productos más vendidos
- Alertas de bajo stock
- Identificación de productos agotados
- **Exportación a PDF** (nuevo)

### 📋 **Movimientos de Inventario**
- Registro de todas las transacciones
- Historial completo de movimientos
- Filtros por producto, tipo y fecha
- Trazabilidad de responsables

---

## 👥 Roles y Permisos

### 👨‍💼 **ADMINISTRADOR**
✅ Control total del sistema  
✅ Crear/Modificar/Eliminar productos y usuarios  
✅ Ver reportes y exportar PDF  
✅ Gestionar permisos y roles  

### 👨‍🔧 **BODEGUERO**
✅ Ver inventario disponible  
✅ Registrar entrada/salida de productos  
✅ Ver reportes de movimientos  
❌ No puede crear usuarios ni productos  

### 👨‍💻 **ENCARGADO**
✅ Ver inventario  
✅ Registrar movimientos  
✅ Actualizar cantidades de productos  
❌ No puede crear productos nuevos  
❌ No puede gestionar usuarios  

---

## 🗂️ Estructura de Archivos de Datos

```
datos/
  ├── productos.csv         # Almacenamiento de productos
  ├── usuarios.csv          # Almacenamiento de usuarios
  └── movimientos.csv       # Historial de movimientos
```

### Formato CSV

**productos.csv:**
```
ID,Nombre,Descripcion,Tallas,Precio,Cantidad,Codigo,Categoria,VentasTotal
1,Camisa Polo,Camisa de algodón,S-M-L-XL,45000,100,CAM001,Ropa,25
```

**usuarios.csv:**
```
ID,Nombre,Correo,Contraseña,Rol,DatoAdicional
1,Juan Admin,admin@stf.com,pass123,ADMINISTRADOR,Gerencia
```

**movimientos.csv:**
```
ID,Tipo,ProductoId,Cantidad,Motivo,UsuarioResponsable,Fecha
1,ENTRADA,1,50,Compra a proveedor,admin@stf.com,2025-05-15 10:30
```

---

## 🚀 Guía de Uso Rápido

### Instalación y Ejecución

1. **Compilar el proyecto:**
   ```bash
   cd "c:\Users\chancro\Desktop\Inventario Final\gention-de-inventario"
   mvn clean compile
   ```

2. **Ejecutar la aplicación:**
   ```bash
   mvn exec:java -Dexec.mainClass="com.edu.tecnocomfenalco.GentionDeInventario"
   ```

3. **Credenciales de prueba:**
   - Usuario: `admin@stf.com`
   - Contraseña: `admin123`

---

## 📞 Contacto y Soporte

**Aplicación:** Gestión de Inventario STF GROUP  
**Versión:** 1.0-SNAPSHOT  
**Java Version:** 24  
**Framework:** Java Swing  
**Base de Datos:** CSV  
**Dependencias:** Apache PDFBox 2.0.27

---

## ✅ Checklist de Funcionalidades

- [x] Autenticación de usuarios
- [x] Gestión de productos (CRUD)
- [x] Registro de movimientos
- [x] Reportes estadísticos
- [x] **Exportación a PDF**
- [x] Gestión de usuarios por roles
- [x] Persistencia en CSV
- [x] Validaciones de negocio
- [x] Interface gráfica amigable
- [ ] Gráficos estadísticos avanzados
- [ ] Integración con base de datos SQL
- [ ] Sistema de respaldos automáticos

---

## 📝 Últimas Actualizaciones

**v1.0 (18-May-2026)**
- ✨ Implementación de exportación a PDF
- ✨ Diagrama de clases UML
- ✨ Diagrama de casos de uso UML
- 🐛 Corrección de formato de números en reportes
- 🐛 Manejo de múltiples páginas en PDF

---

## 📖 Referencias y Documentación Adicional

Para más información sobre los diagramas, consulta:
- [Diagrama de Clases Detallado](DIAGRAMA_CLASES.md)
- [Casos de Uso del Sistema](DIAGRAMA_CASOS_DE_USO.md)
- [Guía de Uso](../GUIA_USO.md)

---

**Documento generado automáticamente - 18 de Mayo de 2026**
