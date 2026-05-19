Programación Orientada a Objetos en Java | Proyecto Final — inventario

# **PROYECTO FINAL**
## Gestion de Inventario para la Tienda Stf Group

_Programación Orientada a Objetos — Java POO_


**Descripción general**


Cada grupo diseñará, desarrollará y presentará un sistema de inventario funcional
en Java, aplicando los principios de la Programación Orientada a Objetos.










### **1. Objetivo**

Que cada grupo desarrolle un sistema de inventario completo en Java,
demostrando la correcta aplicación de los cuatro pilares de la Programación Orientada a
Objetos —herencia, encapsulación, polimorfismo y abstracción—, así como el uso de
colecciones, manejo de excepciones e interfaces gráficas o web funcionales con persistencia
de datos.

### **2. Descripción de la actividad**


La actividad se desarrolla en dos etapas sucesivas:


**Etapa 1 — Desarrollo del proyecto por grupo**


Cada grupo construye su propio sistema de inventario en Java. El proyecto debe ser
funcional, estar publicado en un repositorio público de GitHub y cumplir con los requisitos
técnicos y de documentación descritos en este documento.




### **3. Requisitos técnicos del sistema**


**3.1 Funcionalidades mínimas obligatorias**


El sistema debe implementar, como mínimo, las siguientes funcionalidades:


   - Como administrador, quiero registrar nuevos productos en el sistema,
para mantener actualizado el inventario de la tienda.
Como encargado, quiero actualizar la cantidad disponible de los productos, para llevar un control exacto del stock existente.
Como administrador, quiero registrar entradas y salidas de productos, para mantener actualizado el movimiento del inventario.
Como administrador quiero identificar los productos más vendidos para mejorar la gestión y reposición del inventario.
Como administrador, quiero visualizar reportes básicos del inventario, para conocer el estado actual de los productos disponibles.
Como administrador, quiero eliminar productos que ya no se comercializan, para mantener organizado el inventario.
Como bodeguero quiero ver el stock de los productos su debida existencia en la bodega
Como administrador, quiero registrar los clientes proveedores y empleados para el control de usuarios.
Como encargada, quiero registrar clientes nuevos.



**3.2 Conceptos de POO obligatorios**


El código del proyecto debe evidenciar de forma explícita y funcional los siguientes conceptos:


   - Herencia: al menos una jerarquía de clases con relación padre–hijo significativa
dentro del dominio del sistema. 

- controladores: es la pieza central del patrón de arquitectura Modelo-Vista-Controlador (MVC)

   - Encapsulación: todos los atributos de las entidades del modelo deben declararse
como privados, con acceso controlado mediante métodos getter y setter.

   - Polimorfismo: al menos un método sobrescrito con @Override que participe
activamente en un flujo funcional del sistema.

   - Abstracción: uso de al menos una clase abstracta o interfaz que modele un concepto
general del dominio (por ejemplo, una clase abstracta Pago con subclases para
distintos métodos de pago).

   - Colecciones: uso de ArrayList u otra colección Java para gestionar listas de
entidades (productos, pedidos, usuarios, etc.).

   - Manejo de excepciones: uso de bloques try/catch en operaciones que puedan fallar
(stock insuficiente, datos de entrada inválidos, errores de conexión a BD, etc.).



**3.3 Tecnología permitida**


Documento académico — Uso interno del curso 2 / 5


Programación Orientada a Objetos en Java | Proyecto Final — inventario


No existe restricción sobre las tecnologías asociadas al ecosistema Java. Los grupos pueden
elegir libremente su stack tecnológico dentro de las siguientes categorías:







|Categoría|Opciones permitidas|
|---|---|
|**Interfaz de usuario**|Swing, JavaFX, Spring Boot + Thymeleaf, Spring Boot + API REST<br>con frontend propio|
|**Persistencia**|Archivos (.dat, .txt, .csv), SQLite, MySQL, PostgreSQL, H2,<br>JPA/Hibernate|
|**Build / Gestión de**<br>**dependencias**|Sin build tool, Maven, Gradle|
|**IDE**|NetBeans, IntelliJ IDEA, Eclipse, VS Code con extensiones Java|
|**Control de versiones**|Git + GitHub (repositorio público obligatorio)|


_Nota: el uso de Spring Boot u otros frameworks está permitido siempre que las entidades del_
_modelo de dominio estén implementadas en Java con POO explícita. No se acepta como modelo_
_de dominio un conjunto de clases compuestas únicamente por anotaciones de framework sin_
_jerarquía, abstracción ni polimorfismo reales._

### **4. Entregables**


Cada grupo debe publicar en su repositorio de GitHub los siguientes entregables antes de la
fecha límite indicada por el docente:






|Entregable|Descripción|Obligatorio|
|---|---|---|
|**Repositorio GitHub público**|Código fuente completo, historial de commits<br>visible, repositorio accesible.|Sí|
|**README.md**|Descripción del proyecto, integrantes,<br>instrucciones de ejecución y tecnologías usadas.|Sí|
|**Diagrama de clases UML**|Exportado como imagen PNG en la carpeta<br>docs/uml/ del repositorio.|Sí|
|**Diagrama de casos de uso**|Cubre los flujos principales del sistema. Incluido en<br>docs/uml/.|Sí|
|**Script SQL (si aplica)**|Script de creación de base de datos en<br>db/schema.sql.|Si usan BD|



El repositorio debe ser público y estar accesible sin autenticación. Un integrante del grupo
comparte el enlace en el grupo de WhatsApp del curso con el siguiente formato:


👉 **`Grupo [N] — [Nombre del proyecto] —`**
```
           https://github.com/usuario/repo

```

Documento académico — Uso interno del curso 3 / 5


Programación Orientada a Objetos en Java | Proyecto Final — inventario


**Repositorio base del proyecto:**
`https://github.com/Antomaker/competencia.git` _— Clona este repositorio como_
_punto de partida y construye tu proyecto sobre él._

### **5. Proceso de votación**


**5.1 Votación interna por curso**


Una vez publicados todos los repositorios del curso, el docente abre la votación para elegir el
proyecto representante. Las reglas son las siguientes:

|Aspecto|Detalle|
|---|---|
|**Votos por grupo**|Cada grupo dispone de 3 votos para distribuir.|
|**Voto propio**|Exactamente 1 voto debe destinarse al proyecto del propio grupo. Es<br>obligatorio y no puede omitirse.|
|**Votos externos**|Los 2 votos restantes pueden destinarse al mismo proyecto externo o a dos<br>proyectos distintos. No se permite votar dos veces por el propio grupo.|
|**Desempate**|En caso de empate en votos totales, se aplica como criterio de desempate la<br>cantidad de votos externos recibidos. Si el empate persiste, el docente<br>decide.|
|**Representante**|El proyecto con mayor puntuación representa al curso en la etapa final entre<br>los cuatro cursos.|



**5.2 Etapa final entre cursos**


Los cuatro proyectos ganadores —uno por cada curso— compiten entre sí aplicando
exactamente las mismas reglas de votación. El resultado de esta etapa determina el mejor
proyecto de inventario Java del semestre.

### **6. Restricciones y condiciones**


   - El repositorio debe contener únicamente código fuente y recursos necesarios para
ejecutar el proyecto. No se deben incluir archivos compilados (.class, .jar),
credenciales de bases de datos ni carpetas privadas generadas por el IDE.

   - Todos los integrantes del grupo deben figurar en el README con su nombre
completo y usuario de GitHub.

   - El historial de commits del repositorio debe reflejar trabajo continuo del equipo. Un
único commit con todo el proyecto no es aceptable.

   - El proyecto debe ejecutarse correctamente siguiendo únicamente las instrucciones
del README. Si no puede ejecutarse, no participa en la votación.

   - Queda prohibido el uso de código generado íntegramente por herramientas de
inteligencia artificial sin comprensión ni adaptación por parte del grupo. El docente
puede realizar preguntas sobre cualquier parte del código durante la presentación.


Documento académico — Uso interno del curso 4 / 5


Programación Orientada a Objetos en Java | Proyecto Final — inventario





