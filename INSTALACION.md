# Instalación y Configuración

## Requisitos del Sistema

- **Java**: 24.0.2 o superior
- **RAM**: Mínimo 2 GB
- **Espacio en disco**: 200 MB
- **SO**: Windows 7+, Linux, macOS
- **Compilador**: javac (incluido en Java)

## Verificar Instalación de Java

### Windows
```cmd
java -version
javac -version
```

### Linux/macOS
```bash
java -version
javac -version
```

Si no está instalado, descárgalo desde: https://www.oracle.com/java/technologies/downloads/

## Estructura del Proyecto

```
Inventario Final/
├── gention-de-inventario/          # Código fuente
│   ├── src/
│   │   └── main/
│   │       └── java/
│   │           └── com/edu/tecnocomfenalco/
│   │               ├── GentionDeInventario.java
│   │               ├── modelo/
│   │               ├── persistencia/
│   │               ├── controlador/
│   │               ├── excepciones/
│   │               └── gui/
│   ├── target/                     # Archivos compilados
│   │   └── classes/
│   ├── datos/                      # Archivos CSV
│   │   ├── productos.csv
│   │   ├── usuarios.csv
│   │   └── movimientos.csv
│   ├── pom.xml                     # Configuración Maven (opcional)
│   └── ejecutar.bat                # Script de ejecución
├── README.md                       # Documentación principal
├── GUIA_USO.md                     # Guía de uso detallada
├── ESTRUCTURA_POO.md               # Explicación de POO
├── INSTALACION.md                  # Este archivo
└── actividad-inventario.md         # Requisitos del proyecto
```

## Instalación

### Paso 1: Descargar/Clonar el Proyecto

```bash
# Si está en un repositorio Git
git clone <url-del-repositorio>

# O descargarlo como ZIP y extraerlo
cd "Inventario Final"
```

### Paso 2: Compilar el Código

#### Opción A: Compilación Manual (Recomendado)

Abre una terminal en la carpeta `gention-de-inventario` y ejecuta:

**Windows (CMD o PowerShell):**
```cmd
cd gention-de-inventario
mkdir target\classes
javac -d target/classes -sourcepath src/main/java -encoding UTF-8 ^
  src/main/java/com/edu/tecnocomfenalco/*.java ^
  src/main/java/com/edu/tecnocomfenalco/modelo/*.java ^
  src/main/java/com/edu/tecnocomfenalco/excepciones/*.java ^
  src/main/java/com/edu/tecnocomfenalco/persistencia/*.java ^
  src/main/java/com/edu/tecnocomfenalco/controlador/*.java ^
  src/main/java/com/edu/tecnocomfenalco/gui/*.java
```

**Linux/macOS (Bash):**
```bash
cd gention-de-inventario
mkdir -p target/classes
javac -d target/classes -sourcepath src/main/java -encoding UTF-8 \
  src/main/java/com/edu/tecnocomfenalco/*.java \
  src/main/java/com/edu/tecnocomfenalco/modelo/*.java \
  src/main/java/com/edu/tecnocomfenalco/excepciones/*.java \
  src/main/java/com/edu/tecnocomfenalco/persistencia/*.java \
  src/main/java/com/edu/tecnocomfenalco/controlador/*.java \
  src/main/java/com/edu/tecnocomfenalco/gui/*.java
```

#### Opción B: Usar Maven (si está instalado)

```bash
cd gention-de-inventario
mvn clean compile
```

### Paso 3: Verificar la Compilación

Después de compilar, verifica que existe la carpeta `target/classes` con los archivos `.class`.

```bash
# Windows
dir target\classes\com\edu\tecnocomfenalco

# Linux/macOS
ls -la target/classes/com/edu/tecnocomfenalco
```

## Ejecución

### Opción A: Script Batch (Windows)

```cmd
cd gention-de-inventario
ejecutar.bat
```

### Opción B: Línea de Comandos

**Windows:**
```cmd
cd gention-de-inventario
java -cp target/classes com.edu.tecnocomfenalco.GentionDeInventario
```

**Linux/macOS:**
```bash
cd gention-de-inventario
java -cp target/classes com.edu.tecnocomfenalco.GentionDeInventario
```

### Opción C: Con Maven

```bash
cd gention-de-inventario
mvn exec:java -Dexec.mainClass="com.edu.tecnocomfenalco.GentionDeInventario"
```

## Primera Ejecución

En la primera ejecución, la aplicación:
1. Cargará los usuarios predefinidos desde `datos/usuarios.csv`
2. Cargará los productos de ejemplo desde `datos/productos.csv`
3. Cargará el historial de movimientos desde `datos/movimientos.csv`

### Credenciales Predefinidas

| Usuario | Correo | Contraseña | Rol |
|---------|--------|-----------|-----|
| Admin | admin@stf.com | admin123 | Administrador |
| Bodeguero | bodega@stf.com | bodega123 | Bodeguero |
| Encargado | encargado@stf.com | encargado123 | Encargado |

## Solución de Problemas

### Error: "Class not found: com.edu.tecnocomfenalco.GentionDeInventario"

**Solución:**
1. Verifica estar en la carpeta correcta: `cd gention-de-inventario`
2. Verifica que `target/classes` existe: `ls target/classes`
3. Recompila: `javac -d target/classes ...`

### Error: "Invalid file path" (al compilar en Windows)

**Solución:**
Usa directamente con caret de escape:
```cmd
javac -d target/classes src/main/java/com/edu/tecnocomfenalco/*.java
```

O usa PowerShell con backticks:
```powershell
javac -d target/classes -sourcepath src/main/java -encoding UTF-8 `
  src/main/java/com/edu/tecnocomfenalco/*.java `
  ...
```

### Error: "Credentials invalid" en el login

**Solución:**
1. Verifica que exista `datos/usuarios.csv`
2. Verifica el contenido del archivo
3. Si está vacío, vuelve a copiar los usuarios predefinidos

### Error: "No data persistence" (no guarda datos)

**Solución:**
1. Verifica que exista la carpeta `datos/`
2. Verifica permisos de lectura/escritura en `datos/`
3. En Linux/macOS: `chmod 755 datos/`

### Error: "GUI doesn't show"

**Solución:**
1. Asegúrate de tener display (en Linux remoto necesitas X11 forwarding)
2. Verifica que no hay errores en la consola
3. Espera unos segundos (Swing puede ser lento al iniciar)

### La aplicación se cierra al iniciar

**Solución:**
1. Ejecuta con más verbose:
   ```bash
   java -cp target/classes -Djava.util.logging.config.file=logging.properties \
     com.edu.tecnocomfenalco.GentionDeInventario
   ```
2. Revisa los logs en la consola

## Configuración

### Cambiar Ubicación de Datos

Por defecto, los archivos CSV se guardan en `datos/`. Para cambiar:

1. Modifica en `ProductoCSV.java`:
   ```java
   private static final String RUTA = "tu/nueva/ruta/productos.csv";
   ```

2. Recompila y ejecuta

### Aumentar Memoria

Para proyectos grandes, aumenta la memoria JVM:

```bash
java -Xmx1024m -cp target/classes com.edu.tecnocomfenalco.GentionDeInventario
```

Donde:
- `-Xmx1024m` = Memoria máxima de 1GB

## Desarrollo

### Modificar Código

1. Edita los archivos en `src/main/java/`
2. Recompila
3. Ejecuta

### Agregar una Nueva Funcionalidad

1. Crea la clase en el paquete apropiado
2. Recompila
3. Actualiza los controladores si es necesario

### Estructura de Paquetes

- `modelo/` - Clases de datos
- `persistencia/` - Acceso a datos (CSV)
- `controlador/` - Lógica de negocio
- `gui/` - Interfaz gráfica
- `excepciones/` - Excepciones personalizadas

## Mantenimiento

### Limpiar Archivos Compilados

```bash
rm -rf target/classes  # Linux/macOS
rmdir /s target\classes  # Windows
```

### Respaldar Datos

```bash
# Copia la carpeta datos a un lugar seguro
cp -r datos datos_backup  # Linux/macOS
xcopy datos datos_backup /E  # Windows
```

### Reiniciar (Eliminar todos los Datos)

```bash
# Elimina archivos CSV para empezar de cero
rm datos/*.csv  # Linux/macOS
del datos\*.csv  # Windows
```

## Verificación Final

Antes de ejecutar, verifica:

- [ ] Java 24+ instalado: `java -version`
- [ ] Compilación exitosa: `ls target/classes` (archivos .class existen)
- [ ] Carpeta `datos/` existe
- [ ] Archivos CSV existen: `productos.csv`, `usuarios.csv`, `movimientos.csv`
- [ ] Permisos de lectura/escritura en `datos/`

## Próximos Pasos

1. Lee [GUIA_USO.md](GUIA_USO.md) para aprender a usar la aplicación
2. Lee [ESTRUCTURA_POO.md](ESTRUCTURA_POO.md) para entender la arquitectura
3. Consulta [README.md](README.md) para más información general

## Soporte

Si encuentras problemas:

1. Revisa los logs en la consola
2. Consulta la sección de Solución de Problemas
3. Verifica que todos los requisitos estén cumplidos
4. Intenta recompilar desde cero

## Autores

Proyecto educativo desarrollado con Java POO

Fecha: Mayo 2026
