# Guía de Uso - Sistema de Gestión de Inventario

## Paso 1: Iniciar la Aplicación

Para iniciar el sistema, ejecuta el archivo `ejecutar.bat` o usa la terminal:

```bash
cd c:\Users\chancro\Desktop\Inventario Final\gention-de-inventario
java -cp target/classes com.edu.tecnocomfenalco.GentionDeInventario
```

## Paso 2: Pantalla de Login

Al iniciar, verás una ventana de login. Esta es la pantalla de autenticación del sistema.

### Primera Vez: Crear un Administrador

Como primera vez, necesitas crear un usuario. Pero el login requiere credenciales válidas. Para resolver esto:

1. **Opción A**: Crear un archivo `datos/usuarios.csv` con datos iniciales
2. **Opción B**: Crear un usuario administrativo manualmente (ver paso 3)

### Archivo Inicial de Usuarios (usuarios.csv)

Crea la carpeta `datos` en el proyecto y el archivo `datos/usuarios.csv` con:

```csv
ID,Nombre,Correo,Contraseña,Rol,CampoAdicional
1,Administrador Principal,admin@stf.com,admin123,ADMINISTRADOR,Administración
```

Luego reinicia la aplicación y podrás usar:
- Correo: `admin@stf.com`
- Contraseña: `admin123`

## Paso 3: Interfaz Principal

Una vez autenticado, verás la interfaz principal con:

### Barra de Menú
- **Archivo → Salir**: Cierra la aplicación
- **Sesión → Ver Perfil**: Muestra tu información y permisos
- **Sesión → Cerrar Sesión**: Regresa al login
- **Ayuda → Acerca de**: Información de la aplicación

### Pestañas (según tu rol)

#### Para Administrador:
1. **Productos** - Gestionar inventario
2. **Movimientos** - Registrar entradas/salidas
3. **Reportes** - Ver estadísticas
4. **Usuarios** - Gestionar usuarios del sistema

#### Para Bodeguero:
1. **Productos** - Ver stock disponible

#### Para Encargado:
1. **Productos** - Ver stock
2. **Movimientos** - Registrar movimientos

## Guía Detallada por Sección

### 1. Panel de Productos

**Acciones disponibles:**

#### Crear Nuevo Producto
1. Click en botón "Nuevo Producto"
2. Completa los campos:
   - **Nombre**: Nombre del producto (requerido)
   - **Descripción**: Detalles del producto
   - **Precio**: Precio unitario (debe ser positivo)
   - **Cantidad**: Stock inicial (debe ser ≥ 0)
   - **Código**: Código único (requerido)
   - **Categoría**: Categoría del producto
3. Click en "Guardar"

#### Buscar Productos
1. Ingresa en el campo "Buscar" el nombre o parte del nombre
2. Click en "Buscar"
3. La tabla mostrará solo los productos coincidentes

#### Actualizar Producto
1. Selecciona un producto de la tabla (click en la fila)
2. Click en "Actualizar"
3. Modifica los datos
4. Click en "Guardar"

#### Eliminar Producto
1. Selecciona un producto de la tabla
2. Click en "Eliminar"
3. Confirma la eliminación

**Información mostrada en la tabla:**
- ID: Identificador único
- Nombre: Nombre del producto
- Código: Código de referencia
- Precio: Precio unitario
- Cantidad: Stock disponible
- Categoría: Clasificación
- Ventas: Cantidad total vendida

### 2. Panel de Movimientos

**Registrar Entrada**
1. Click en "Registrar Entrada"
2. Selecciona el producto del combo
3. Ingresa la cantidad a ingresar
4. Escribe el motivo (ej: "Compra a proveedor", "Devolución del cliente")
5. Click en "Guardar"

**Registrar Salida**
1. Click en "Registrar Salida"
2. Selecciona el producto
3. Ingresa la cantidad a vender
4. Escribe el motivo (ej: "Venta al cliente", "Rotura")
5. Click en "Guardar"

**Notas:**
- El sistema valida que haya stock suficiente
- Las salidas incrementan las ventas totales del producto
- Todos los movimientos se registran con fecha y usuario responsable

### 3. Panel de Reportes

**Ver Reportes**
- Los reportes se cargan automáticamente
- Click en "Actualizar Reportes" para refrescar

**Secciones del Reporte:**
- **Resumen General**: Total de productos, cantidad en stock, valor total
- **Productos Más Vendidos**: Top 5 con mayor cantidad vendida
- **Bajo Stock**: Productos con cantidad < 10
- **Sin Stock**: Productos agotados
- **Lista Completa**: Todos los productos con detalles

**Exportar Reportes**
1. Click en "Exportar"
2. Selecciona la ubicación para guardar
3. El archivo se descargará como texto

### 4. Panel de Usuarios (Solo Administrador)

**Crear Nuevo Usuario**
1. Click en "Nuevo Usuario"
2. Completa:
   - **Nombre**: Nombre del usuario (requerido)
   - **Correo**: Email válido con @ (requerido)
   - **Contraseña**: Contraseña (requerido)
   - **Rol**: Selecciona el tipo de usuario
   - **Campo Adicional**: Departamento, Ubicación o Sección según el rol
3. Click en "Guardar"

**Roles Disponibles:**

#### Administrador
- Permisos: Gestionar todo (productos, movimientos, reportes, usuarios)
- Campo adicional: Departamento

#### Bodeguero
- Permisos: Ver stock y existencia en bodega
- Campo adicional: Ubicación de la bodega

#### Encargado
- Permisos: Actualizar cantidades y registrar clientes
- Campo adicional: Sección a cargo

**Eliminar Usuario**
1. Selecciona un usuario
2. Click en "Eliminar"
3. Confirma

## Validaciones Implementadas

El sistema valida automáticamente:

✓ **Productos:**
- Precio no puede ser negativo
- Cantidad no puede ser negativa
- Código debe ser único
- Nombre no puede estar vacío

✓ **Usuarios:**
- Correo debe ser válido (contener @)
- Nombre requerido
- Contraseña requerida
- No se pueden registrar dos usuarios con el mismo correo

✓ **Movimientos:**
- No se puede sacar más stock del disponible
- La cantidad debe ser mayor a 0
- Se requiere un motivo

## Ejemplos de Uso

### Ejemplo 1: Crear un Producto

```
1. Click "Nuevo Producto"
2. Nombre: Laptop HP
3. Descripción: Laptop HP 15.6 pulgadas
4. Precio: 1200.00
5. Cantidad: 10
6. Código: LAP-HP-001
7. Categoría: Electrónica
8. Click "Guardar"
```

### Ejemplo 2: Registrar una Venta

```
1. Click "Registrar Salida"
2. Selecciona "1 - Laptop HP (Cantidad: 10)"
3. Cantidad: 2
4. Motivo: Venta al cliente Juan García
5. Click "Guardar"
```

Resultado: La cantidad de Laptop HP bajará de 10 a 8, y se incrementarán las ventas en 2.

### Ejemplo 3: Ver Reportes

```
1. Click en pestaña "Reportes"
2. Verás automáticamente:
   - Total de 15 productos
   - 150 unidades en stock total
   - Valor total: $ 200,000,000 COP
   - Laptop HP: 2 ventas (en top 5)
```

## Solución de Problemas

### "Credenciales inválidas"
- Verifica que el correo y contraseña sean correctos
- Los datos son sensibles a mayúsculas/minúsculas
- Crea un nuevo usuario si olvidaste la contraseña

### "Stock insuficiente"
- Primero registra una entrada del producto
- O reduce la cantidad de la salida

### "El código ya existe"
- Usa un código diferente para cada producto
- Cada código debe ser único en el sistema

### Archivo de datos no se guarda
- Verifica que tengas permisos en la carpeta `datos/`
- Crea la carpeta manualmente si no existe

## Estructura de Datos Guardados

### datos/productos.csv
```csv
ID,Nombre,Descripcion,Precio,Cantidad,Codigo,Categoria,VentasTotal
1,Laptop HP,Laptop...,1200.00,8,LAP-HP-001,Electrónica,2
```

### datos/usuarios.csv
```csv
ID,Nombre,Correo,Contraseña,Rol,CampoAdicional
1,Admin,admin@stf.com,admin123,ADMINISTRADOR,Administración
```

### datos/movimientos.csv
```csv
ID,Tipo,ProductoID,Cantidad,Motivo,UsuarioResponsable,Fecha
1,SALIDA,1,2,Venta al cliente,Admin,2026-05-18T10:30:00
```

## Tips y Recomendaciones

✓ Crea primero varios productos antes de registrar movimientos
✓ Usa códigos descriptivos (ej: LAP-001, MON-001)
✓ Revisa los reportes regularmente para gestionar el stock
✓ Crea usuarios con roles específicos según sus funciones
✓ Realiza regularmente exportación de reportes para respaldo

## Más Información

Para información técnica detallada, consulta el archivo README.md en la carpeta del proyecto.
