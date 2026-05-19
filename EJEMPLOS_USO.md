# Ejemplos de Uso Avanzado

Documento con ejemplos prácticos y casos de uso típicos del Sistema de Gestión de Inventario.

## Tabla de Contenidos

1. [Casos de Uso Básicos](#casos-de-uso-básicos)
2. [Casos de Uso de Administrador](#casos-de-uso-de-administrador)
3. [Casos de Uso de Bodeguero](#casos-de-uso-de-bodeguero)
4. [Casos de Uso de Encargado](#casos-de-uso-de-encargado)
5. [Escenarios Complejos](#escenarios-complejos)
6. [Reportes Avanzados](#reportes-avanzados)

---

## Casos de Uso Básicos

### 1. Iniciar Sesión

**Actor:** Cualquier usuario
**Objetivo:** Acceder al sistema con credenciales válidas

**Pasos:**
1. Inicia la aplicación: `java -cp target/classes com.edu.tecnocomfenalco.GentionDeInventario`
2. Se abre la ventana de login
3. Ingresa correo: `admin@stf.com`
4. Ingresa contraseña: `admin123`
5. Haz clic en "Aceptar"

**Resultado Esperado:**
- Se abre la interfaz principal
- Se muestran los paneles según el rol del usuario
- El nombre del usuario aparece en la barra de titulo

**Ejemplo de Respuesta del Sistema:**
```
Administrador Principal conectado
[Se carga el menú completo de administrador]
```

### 2. Cerrar Sesión

**Actor:** Cualquier usuario autenticado
**Objetivo:** Salir seguro del sistema

**Pasos:**
1. Haz clic en Archivo → Cerrar Sesión
2. Confirma la acción
3. Se retorna al diálogo de login

**Resultado Esperado:**
- Los datos se guardan automáticamente
- Se limpia la sesión del usuario

---

## Casos de Uso de Administrador

### 1. Crear un Nuevo Producto

**Rol Requerido:** Administrador
**Objetivo:** Registrar un producto en el inventario

**Pasos:**
1. Inicia sesión con `admin@stf.com / admin123`
2. Ve a la pestaña "Productos"
3. Haz clic en botón "Nuevo"
4. Se abre diálogo de creación
5. Completa los datos:
   - **Nombre:** Monitor LG 32"
   - **Descripción:** Monitor 4K ultrawide gaming
   - **Precio:** 2,400,000
   - **Cantidad:** 10
   - **Código:** MON-LG-32-001
   - **Categoría:** Accesorios
6. Haz clic en "Guardar"

**Resultado Esperado:**
```
✓ Producto creado correctamente
ID: 11
Monitor LG 32" - Código: MON-LG-32-001 - Cantidad: 10 - Precio: $ 2,400,000
[Se actualiza la tabla de productos]
```

**Validaciones que Realiza:**
- ✓ Nombre no vacío
- ✓ Precio numérico y ≥ 0
- ✓ Cantidad numérica y ≥ 0
- ✓ Código único (no duplicado)

### 2. Actualizar un Producto

**Rol Requerido:** Administrador
**Objetivo:** Modificar información de un producto existente

**Pasos:**
1. Ve a la pestaña "Productos"
2. Selecciona un producto de la tabla (ej: Laptop Dell XPS 13)
3. Haz clic en "Actualizar"
4. Modifica los datos:
   - **Precio:** 4,800,000 (antes 5,200,000)
   - **Cantidad:** 8 (antes 5)
5. Haz clic en "Guardar"

**Resultado Esperado:**
```
✓ Producto actualizado correctamente
Laptop Dell XPS 13 - Precio: $ 4,800,000 - Cantidad: 8
```

### 3. Buscar Producto

**Rol Requerido:** Administrador
**Objetivo:** Encontrar rápidamente un producto por nombre

**Pasos:**
1. Ve a la pestaña "Productos"
2. Ingresa en campo "Buscar": `Teclado`
3. La tabla se filtra automáticamente

**Resultado Esperado:**
```
Mostrando 1 resultado:
┌─────┬──────────────────────────┬───────┬────────┐
│ ID  │ Nombre                   │Precio │Cantidad│
├─────┼──────────────────────────┼───────┼────────┤
│  3  │ Teclado Mecánico RGB     │149.99 │   15   │
└─────┴──────────────────────────┴───────┴────────┘
```

### 4. Eliminar Producto

**Rol Requerido:** Administrador
**Objetivo:** Remover un producto del inventario

**Pasos:**
1. Ve a la pestaña "Productos"
2. Selecciona un producto descontinuado
3. Haz clic en "Eliminar"
4. Confirma la eliminación

**Resultado Esperado:**
```
✓ Producto eliminado correctamente
[Se actualiza la tabla]
```

**Nota de Advertencia:**
- No se pueden eliminar productos que tienen movimientos registrados
- Para gestionar sin eliminar, usa cantidad = 0

### 5. Crear Nuevo Usuario

**Rol Requerido:** Administrador
**Objetivo:** Registrar un nuevo usuario del sistema

**Pasos:**
1. Ve a la pestaña "Usuarios"
2. Haz clic en "Nuevo"
3. Completa el formulario:
   - **Nombre:** Juan Pérez
   - **Correo:** juan.perez@stf.com
   - **Contraseña:** juan2024
   - **Rol:** Bodeguero
   - **Campo Adicional:** Bodega Centro
4. Haz clic en "Guardar"

**Resultado Esperado:**
```
✓ Usuario creado correctamente
ID: 4
Nombre: Juan Pérez
Correo: juan.perez@stf.com
Rol: BODEGUERO
Ubicación: Bodega Centro
```

**Validaciones:**
- ✓ Correo debe contener @
- ✓ Correo debe ser único
- ✓ Contraseña no vacía

### 6. Registrar Entrada de Inventario

**Rol Requerido:** Administrador
**Objetivo:** Registrar compra o recepción de productos

**Pasos:**
1. Ve a la pestaña "Movimientos"
2. Haz clic en "Entrada"
3. Se abre diálogo
4. Completa:
   - **Producto:** Laptop Dell XPS 13
   - **Cantidad:** 5
   - **Motivo:** Compra a proveedor principal
5. Haz clic en "Guardar"

**Resultado Esperado:**
```
✓ Entrada registrada correctamente
Movimiento ID: 11
ENTRADA: Producto ID 1 - Cantidad: 5 - Motivo: Compra a proveedor principal
[Stock de Laptop: 5 → 10]
```

**Acciones Automáticas:**
- ✓ Aumenta stock del producto
- ✓ Crea registro en historial
- ✓ Guarda cambios en CSV

### 7. Registrar Salida de Inventario

**Rol Requerido:** Administrador
**Objetivo:** Registrar venta o consumo de productos

**Pasos:**
1. Ve a la pestaña "Movimientos"
2. Haz clic en "Salida"
3. Completa:
   - **Producto:** Teclado Mecánico RGB
   - **Cantidad:** 3
   - **Motivo:** Venta a cliente retail
4. Haz clic en "Guardar"

**Resultado Esperado:**
```
✓ Salida registrada correctamente
Movimiento ID: 12
SALIDA: Producto ID 3 - Cantidad: 3 - Motivo: Venta a cliente retail
[Stock de Teclado: 15 → 12]
[Ventas totales: 28 → 31]
```

**Validaciones de Seguridad:**
- ✓ Verifica stock suficiente
- ✓ Rechaza si cantidad > stock disponible
- ✓ Actualiza contador de ventas

### 8. Generar Reportes

**Rol Requerido:** Administrador
**Objetivo:** Obtener análisis del inventario

**Pasos:**
1. Ve a la pestaña "Reportes"
2. Se muestra automáticamente:
   - **Resumen General:** Total productos, cantidad total, valor total
   - **Top 5 Productos Más Vendidos:** Ranking de ventas
   - **Productos con Bajo Stock:** Lista de productos con < 10 unidades
   - **Productos Sin Stock:** Lista de productos con cantidad = 0
   - **Listado Completo:** Todos los productos con detalles
3. Haz clic en "Exportar Reportes"
4. Elige ubicación y haz clic en "Guardar"

**Resultado Esperado:**
```
Generando reporte...
┌─────────────────────────────────────┐
│       REPORTE DE INVENTARIO          │
├─────────────────────────────────────┤
│ RESUMEN GENERAL                     │
│ Total de Productos: 10              │
│ Cantidad Total: 139                 │
│ Valor Total del Inventario:         │
│ $ 50,320,000 COP                    │
├─────────────────────────────────────┤
│ TOP 5 PRODUCTOS MÁS VENDIDOS        │
│ 1. Mouse Logitech MX - 35 ventas    │
│ 2. Teclado Mecánico RGB - 31 ventas │
│ ...                                 │
└─────────────────────────────────────┘

Reporte exportado a: reportes_20260521.txt
```

---

## Casos de Uso de Bodeguero

### 1. Ver Stock de Productos

**Rol Requerido:** Bodeguero
**Objetivo:** Consultar disponibilidad de productos

**Pasos:**
1. Inicia sesión con `bodega@stf.com / bodega123`
2. Ve a pestaña "Productos"
3. Observa la columna "Cantidad" para cada producto

**Limitaciones del Rol:**
- ✗ No puede crear productos
- ✗ No puede modificar precios
- ✓ Puede ver stock actual
- ✓ Puede buscar productos

### 2. Ver Historial de Movimientos

**Rol Requerido:** Bodeguero
**Objetivo:** Consultar entradas y salidas de inventario

**Pasos:**
1. Ve a pestaña "Movimientos"
2. Observa la tabla con:
   - Tipo (ENTRADA/SALIDA)
   - Producto
   - Cantidad
   - Motivo
   - Usuario responsable
   - Fecha

**Información Disponible:**
- Quién realizó el movimiento
- Cuándo se realizó
- Qué cantidad se movió

---

## Casos de Uso de Encargado

### 1. Actualizar Cantidad de Producto

**Rol Requerido:** Encargado
**Objetivo:** Registrar ajustes de stock (ej: pérdida, daño, devolución)

**Pasos:**
1. Inicia sesión con `encargado@stf.com / encargado123`
2. Ve a pestaña "Productos"
3. Selecciona producto: Auriculares Sony WH-1000
4. Haz clic en "Actualizar"
5. Cambia cantidad: 7 → 6 (por daño)
6. Haz clic en "Guardar"

**Resultado Esperado:**
```
✓ Cantidad actualizada
Auriculares Sony WH-1000: 7 → 6 unidades
```

### 2. Registrar Cliente Nuevo

**Rol Requerido:** Encargado
**Objetivo:** Mantener registro de clientes (mediante nota en movimientos)

**Pasos:**
1. Ve a pestaña "Movimientos"
2. Haz clic en "Salida"
3. En motivo especifica: "Venta - Cliente: Carlos López, Empresa XYZ"
4. Completa información del movimiento
5. Haz clic en "Guardar"

**Resultado Esperado:**
```
✓ Movimiento registrado con información del cliente
```

---

## Escenarios Complejos

### Escenario 1: Reabastecimiento de Bodega

**Objetivo:** Registrar compra de reabastecimiento

**Proceso:**
1. **Admin crea orden de compra**
   - Identifica productos con bajo stock
   - Contacta proveedores

2. **Recibimiento de productos**
   - Admin registra entrada de 50 teclados
   - Sistema actualiza stock automáticamente

3. **Verificación en bodega**
   - Bodeguero verifica cantidad recibida
   - Consulta el historial de movimientos

4. **Resultado:**
```
Producto: Teclado Mecánico RGB
Stock anterior: 2
Entrada: 50 unidades
Stock nuevo: 52
Movimiento registrado en historial
```

### Escenario 2: Devolución de Cliente

**Objetivo:** Procesar devolución sin afectar contabilidad

**Proceso:**
1. Cliente devuelve 2 Mouse Logitech MX con defecto
2. Encargado registra salida inversa:
   - Ve a Movimientos → Entrada
   - Cantidad: 2
   - Motivo: "Devolución - Cliente #1234"
3. Sistema suma 2 unidades al stock
4. Encargado nota en movimiento: "Defectuoso - revisar calidad"

**Resultado:**
```
Devuelve 2 unidades al inventario
Stock: 20 → 22
Motivo documentado en historial
```

### Escenario 3: Auditoría de Inventario

**Objetivo:** Verificar exactitud del stock

**Proceso:**
1. **Conteo físico manual**
   - Se cuenta físicamente cada producto
   - Se registran diferencias

2. **Ajuste de discrepancias**
   - Diferencia encontrada: Monitor -2 unidades
   - Admin registra salida: 2 unidades
   - Motivo: "Ajuste por diferencia en conteo"

3. **Generación de reporte**
   - Se verifica que el sistema coincida con conteo

**Auditoría en Sistema:**
```
Antes: Monitor Samsung 27" - 8 unidades
Ajuste: -2 unidades (diferencia detectada)
Después: 6 unidades
Movimiento registrado: ID 28, Fecha: 2026-05-21
```

---

## Reportes Avanzados

### Reporte 1: Productos Críticos

**Pregunta:** ¿Qué productos necesitan reorden?

**Acciones:**
1. Ve a Reportes
2. Observa sección "Productos con Bajo Stock (< 10)"
3. Exporta reporte
4. Envía a equipo de compras

**Ejemplo de Salida:**
```
PRODUCTOS CON BAJO STOCK (< 10 unidades)
┌─────┬──────────────────────┬────────┬──────────┐
│ ID  │ Nombre               │Cantidad│Valoración│
├─────┼──────────────────────┼────────┼──────────┤
│  5  │ Webcam Logitech 4K   │   3    │ CRÍTICO  │
│  9  │ Fuente de Poder 750W │   6    │ BAJO     │
└─────┴──────────────────────┴────────┴──────────┘
Acción recomendada: Crear orden de compra
```

### Reporte 2: Análisis de Ventas

**Pregunta:** ¿Cuáles son nuestros productos estrella?

**Acciones:**
1. Ve a Reportes
2. Observa sección "Top 5 Productos Más Vendidos"
3. Identifica tendencias

**Ejemplo:**
```
PRODUCTOS MÁS VENDIDOS (Últimas transacciones)
1. Mouse Logitech MX - 35 ventas
2. Teclado Mecánico RGB - 31 ventas
3. Monitor Samsung 27" - 5 ventas
4. Auriculares Sony WH-1000 - 15 ventas
5. Almacenamiento SSD 1TB - 22 ventas

Insight: Productos periféricos (mouse, teclado) son líderes
```

### Reporte 3: Valor del Inventario

**Pregunta:** ¿Cuál es nuestro activo en inventario?

**Información en Reportes:**
```
VALOR TOTAL DEL INVENTARIO: $ 50,320,000 COP

Desglose:
- Laptops: $ 26,000,000 (5 unidades)
- Monitores: $ 12,800,000 (8 unidades)
- Periféricos: $ 36,720,000 (135+ unidades)
- Componentes: $ 4,960,000 (51 unidades)
- Almacenamiento: $ 6,240,000 (12 unidades)
- Audio: $ 9,800,000 (7 unidades)
- Fuente poder: $ 3,120,000 (6 unidades)

Alertas: 0 productos sin stock
Bajo stock: 2 productos
```

---

## Consejos de Uso

### Performance
- **Importar datos grandes:** Usa formato CSV para cargas masivas
- **Reportes extensos:** Exporta en texto plano para mejor manejo
- **Múltiples usuarios:** Evita acceso simultáneo a datos CSV

### Seguridad
- Cambia contraseñas periódicamente
- Realiza respaldos de `datos/` regularmente
- Revisa historial de movimientos para auditoría
- Utiliza roles apropiados para cada usuario

### Mantenimiento
- **Limpieza:** Elimina productos descontinuados anualmente
- **Organización:** Usa categorías consistentes
- **Documentación:** Mantén motivos de movimientos descriptivos
- **Validación:** Realiza auditoría de inventario cada trimestre

---

## Preguntas Frecuentes (FAQ)

**P: ¿Puedo deshacer una transacción?**
R: No hay deshacer automático. Para corregir, registra un movimiento inverso con motivo "Corrección".

**P: ¿Cuántos productos puedo manejar?**
R: Técnicamente ilimitado, pero se recomienda < 10,000 para performance óptima con CSV.

**P: ¿Cómo migro de un sistema anterior?**
R: Exporta tus datos en CSV con formato: ID, Nombre, Descripción, Precio, Cantidad, Código, Categoría, VentasTotal

**P: ¿Puedo acceder desde múltiples computadoras?**
R: No con CSV (requiere compartir carpeta en red). Para multiusuario, migra a Base de Datos.

**P: ¿Hay límite de usuarios?**
R: No. Crea todos los que necesites con diferentes roles.

---

## Próximos Pasos

1. Practica con los datos de ejemplo
2. Lee [ESTRUCTURA_POO.md](ESTRUCTURA_POO.md) para entender la arquitectura
3. Experimenta creando y eliminando datos
4. Revisa el código fuente para aprender POO

¡Disfruta usando el Sistema de Gestión de Inventario!
