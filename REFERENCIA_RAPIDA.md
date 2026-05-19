# Referencia Rápida

Guía de inicio rápido de una página para el Sistema de Gestión de Inventario.

## 🚀 Inicio Rápido (5 minutos)

### 1. Compilar
```bash
cd gention-de-inventario
javac -d target/classes -sourcepath src/main/java -encoding UTF-8 \
  src/main/java/com/edu/tecnocomfenalco/*.java \
  src/main/java/com/edu/tecnocomfenalco/modelo/*.java \
  src/main/java/com/edu/tecnocomfenalco/excepciones/*.java \
  src/main/java/com/edu/tecnocomfenalco/persistencia/*.java \
  src/main/java/com/edu/tecnocomfenalco/controlador/*.java \
  src/main/java/com/edu/tecnocomfenalco/gui/*.java
```

### 2. Ejecutar
```bash
java -cp target/classes com.edu.tecnocomfenalco.GentionDeInventario
```

### 3. Iniciar Sesión
- **Usuario:** admin@stf.com
- **Contraseña:** admin123

¡Listo! ✓

---

## 👥 Credenciales Predefinidas

| Correo | Contraseña | Rol |
|--------|-----------|-----|
| admin@stf.com | admin123 | Administrador |
| bodega@stf.com | bodega123 | Bodeguero |
| encargado@stf.com | encargado123 | Encargado |

---

## 📊 Características por Rol

### Administrador ⭐
- ✓ Crear/actualizar/eliminar productos
- ✓ Registrar entradas y salidas
- ✓ Ver reportes detallados
- ✓ Gestionar usuarios
- ✓ Exportar datos

### Bodeguero 📦
- ✓ Ver stock de productos
- ✓ Ver historial de movimientos
- ✗ No puede crear productos
- ✗ No puede eliminar datos

### Encargado 📋
- ✓ Actualizar cantidades
- ✓ Registrar ajustes
- ✗ Acceso limitado
- ✓ Mantener datos operacionales

---

## ⚡ Acciones Comunes

### Crear Producto
`Productos → Nuevo → Llenar datos → Guardar`

### Registrar Venta
`Movimientos → Salida → Seleccionar producto → Guardar`

### Recibir Compra
`Movimientos → Entrada → Seleccionar producto → Guardar`

### Ver Reporte
`Reportes → Se carga automático → Exportar (opcional)`

### Gestionar Usuarios
`Usuarios → Nuevo → Seleccionar rol → Guardar`

---

## 📁 Estructura de Carpetas

```
Inventario Final/
├── gention-de-inventario/
│   ├── src/main/java/...     (Código fuente)
│   ├── target/classes/...    (Compilado)
│   ├── datos/                (CSV files)
│   │   ├── productos.csv
│   │   ├── usuarios.csv
│   │   └── movimientos.csv
│   └── ejecutar.bat
├── README.md                 (Documentación completa)
├── INSTALACION.md            (Instalación paso a paso)
├── GUIA_USO.md              (Guía de usuario detallada)
├── ESTRUCTURA_POO.md        (Explicación de arquitectura)
├── EJEMPLOS_USO.md          (Casos de uso con ejemplos)
└── REFERENCIA_RAPIDA.md     (Este archivo)
```

---

## 🔑 Concepto: Validaciones de Seguridad

| Operación | Validaciones |
|-----------|-------------|
| Crear Producto | Nombre ≠ vacío, Precio ≥ 0, Código único |
| Registrar Salida | Stock suficiente, Cantidad > 0 |
| Registrar Usuario | Email único, Contiene @, Contraseña ≠ vacía |
| Actualizar Cantidad | Cantidad ≥ 0 |

---

## 🛠️ Solución de Problemas

| Problema | Solución |
|----------|----------|
| No compila | Verifica `target/classes` existe y Java 24+ instalado |
| No inicia | Verifica `datos/` existe y tiene permisos de lectura |
| Datos no guardan | Verifica permisos de escritura en `datos/` |
| Crash al login | Revisa que `datos/usuarios.csv` no esté vacío |

---

## 📚 Documentación Completa

- **README.md** - Resumen general del proyecto
- **INSTALACION.md** - Pasos detallados de instalación
- **GUIA_USO.md** - Manual de usuario (400+ líneas)
- **ESTRUCTURA_POO.md** - Explicación arquitectura POO
- **EJEMPLOS_USO.md** - Casos prácticos con ejemplos
- **actividad-inventario.md** - Requisitos originales

---

## 🎯 Principios de Diseño (POO)

El proyecto demuestra 6 conceptos fundamentales:

| Concepto | Ejemplo |
|----------|---------|
| **Herencia** | Usuario → Administrador, Bodeguero, Encargado |
| **Encapsulación** | Atributos privados + getters/setters validados |
| **Polimorfismo** | obtenerDescripcion() diferente por clase |
| **Abstracción** | Interfaz GestorCSV<T> oculta detalles CSV |
| **Colecciones** | ArrayList<T> con Stream operations |
| **Excepciones** | ProductoException, UsuarioException |

---

## 📊 Estadísticas del Proyecto

- **Archivos Java:** 26
- **Líneas de código:** ~3,500
- **Paquetes:** 6
- **Clases:** 20+
- **Interfaces:** 1
- **Enumeraciones:** 2

---

## ✅ Checklist de Uso

Antes de usar:
- [ ] Java 24+ instalado
- [ ] Compilación exitosa (`target/classes/` existe)
- [ ] Carpeta `datos/` existe
- [ ] Archivos CSV presentes
- [ ] Permisos de lectura/escritura en `datos/`

Primer uso:
- [ ] Inicia sesión con admin
- [ ] Crea un producto de prueba
- [ ] Registra una entrada
- [ ] Registra una salida
- [ ] Ve un reporte
- [ ] Exporta datos

---

## 🔗 Comandos Útiles

```bash
# Compilar
javac -d target/classes -sourcepath src/main/java -encoding UTF-8 \
  src/main/java/com/edu/tecnocomfenalco/**/*.java

# Ejecutar
java -cp target/classes com.edu.tecnocomfenalco.GentionDeInventario

# Con Maven (si está instalado)
mvn clean compile
mvn exec:java -Dexec.mainClass="com.edu.tecnocomfenalco.GentionDeInventario"

# Ver contenido CSV
cat datos/productos.csv

# Limpiar compilación
rm -rf target/classes
```

---

## 📞 Soporte Rápido

**Pregunta:** ¿Cómo reinicio con datos limpios?
**Respuesta:** Elimina archivos en `datos/` y reinicia la app

**Pregunta:** ¿Cómo cambio contraseña?
**Respuesta:** Edita `datos/usuarios.csv` o usa interfaz de admin

**Pregunta:** ¿Cómo agrego más productos iniciales?
**Respuesta:** Edita `datos/productos.csv` con formato CSV correcto

**Pregunta:** ¿Puedo usar desde otro computador?
**Respuesta:** Sí, compartiendo la carpeta `datos/` en red

---

## 🎓 Para Aprender

1. **Estudiar POO:** Lee [ESTRUCTURA_POO.md](ESTRUCTURA_POO.md)
2. **Entender arquitectura:** Revisa paquetes en `src/main/java/`
3. **Práctica:** Crea productos y movimientos
4. **Ejercicio:** Modifica código y recompila

---

## 📈 Capacidad del Sistema

- **Productos:** Ilimitados (recomendado: < 10,000 con CSV)
- **Usuarios:** Ilimitados
- **Movimientos:** Ilimitados
- **Memoria:** Mínimo 512 MB, óptimo 1 GB+
- **Concurrencia:** 1 usuario a la vez (multi-usuario requiere BD)

---

## ⏱️ Tiempos Típicos

| Operación | Tiempo |
|-----------|--------|
| Compilación inicial | 10-20 seg |
| Inicio aplicación | 3-5 seg |
| Cargar 1000 productos | 1-2 seg |
| Generar reporte | 1-2 seg |
| Exportar reporte | 2-3 seg |

---

## 🚨 Alertas Importantes

⚠️ **No eliminar carpeta `datos/`** - Se pierden todos los datos
⚠️ **Respalda datos regularmente** - Copia `datos/` a otro lugar
⚠️ **No edites CSV manualmente** - Corrompes el formato
⚠️ **Usa roles apropiados** - Cada usuario tiene permisos diferentes

---

## 🎁 Extras

- Datos de ejemplo precargados
- 3 usuarios de prueba
- 10 productos de muestra
- 10 movimientos de ejemplo
- Reportes automáticos
- Exportación a texto

---

**Versión:** 1.0
**Última actualización:** Mayo 2026
**Licencia:** Proyecto Educativo

Para más detalles, consulta la documentación completa.
