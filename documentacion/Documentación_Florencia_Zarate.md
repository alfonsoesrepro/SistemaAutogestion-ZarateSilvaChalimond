# Documentacion de Uso de IA - Florencia Agustina Zarate

Se utilizo Claude Code (Anthropic) como asistente de desarrollo para
implementar la parte de interfaz y menus del sistema. El desarrollo se realizo
de forma incremental: cada funcionalidad se implemento, se probo, se commiteo
y recien despues se paso a la siguiente.

---

### Prompt 1: Analisis del proyecto y planificacion

> **Mi Prompt:** "Necesito entender la estructura del proyecto que armaron mis
> companeros antes de empezar a implementar mi parte. Explicame que hace cada
> clase, como se relacionan entre si, y detallame que es lo que tengo que
> implementar yo en los puntos marcados con TODO."
>
> **Resumen de la respuesta de la IA:** Analizo todos los archivos del proyecto,
> identifico los 4 puntos pendientes y me explico la jerarquia de clases
> (PersonaAcademica -> Estudiante, Materia -> MateriaAnual/Cuatrimestral),
> las interfaces (IConsultable, IEvaluable, IRankeable) y como se conectaban
> entre si. Tambien detecto dos bugs en el codigo existente.

---

### Prompt 2: Implementar la opcion Ver Perfil usando herencia

> **Mi Prompt:** "Quiero implementar la opcion 1 del menu principal que muestra
> el perfil del estudiante. Como uso el metodo mostrarResumen() considerando
> que Estudiante hereda de PersonaAcademica y a la vez implementa IConsultable?"
>
> **Resumen de la respuesta de la IA:** Me explico que `mostrarResumen()` cumple
> dos contratos simultaneamente: la clase abstracta padre y la interface. Me
> detallo como funciona la sobreescritura (@Override) y por que no se puede
> instanciar una clase abstracta.

---

### Prompt 3: Implementar Registrar Calificacion con validaciones

> **Mi Prompt:** "Necesito implementar el registro de calificaciones. El metodo
> agregarNota() es void y no me devuelve si la nota fue aceptada o rechazada.
> Como puedo saber desde afuera si la nota se agrego correctamente?"
>
> **Resumen de la respuesta de la IA:** Me enseno a comparar el tamanio del
> ArrayList antes y despues de llamar al metodo para verificar si el elemento
> fue agregado. Tambien me guio en el uso de try-catch con
> NumberFormatException para validar la entrada del usuario y en el null check
> de getInscripcion() para evitar NullPointerException.

---

### Prompt 4: Implementar Registrar Asistencia con advertencias escalonadas

> **Mi Prompt:** "Para la opcion de registrar asistencia necesito mostrar dos
> niveles de advertencia: una si la asistencia baja del 80% y otra critica si
> baja del 75%. Como estructuro los condicionales para que no se pisen entre si?"
>
> **Resumen de la respuesta de la IA:** Me explico que el orden de los if-else if
> es clave: primero evaluar `< 75` (alerta critica) y despues `< 80` como
> else-if (zona de riesgo), porque cualquier valor menor a 75 tambien es menor
> a 80 y entraria en la condicion equivocada si se invierte el orden. Tambien
> me explico la conversion de int a boolean para el parametro de
> registrarAsistencia().

---

### Prompt 5: Implementar Sobrecarga de Metodos (Requisito POO obligatorio)

> **Mi Prompt:** "Tengo que cumplir el requisito de sobrecarga de metodos.
> Mi companero ya hizo buscarMateriaRecursiva(String, int). Necesito crear
> metodos sobrecargados para buscar materias de distintas formas. Cual es la
> diferencia entre sobrecarga y sobreescritura?"
>
> **Resumen de la respuesta de la IA:** Me explico que sobrecarga es mismo nombre
> con distinta firma dentro de la misma clase, mientras que sobreescritura es
> misma firma entre clase padre e hija. Implemente dos metodos `buscarMateria`:
> uno que recibe String para busqueda parcial por nombre (usando toLowerCase y
> contains), y otro que recibe int para filtrar por cuatrimestre. Java decide
> cual ejecutar segun el tipo del argumento en tiempo de compilacion.

---

### Prompt 6: Configuracion de Git y entorno de desarrollo

> **Mi Prompt:** "Necesito configurar Git para trabajar con NetBeans. Al hacer
> commit me aparecen errores por archivos bloqueados del IDE y mis companeros
> no ven mis cambios en GitHub."
>
> **Resumen de la respuesta de la IA:** Me ayudo a configurar el .gitignore para
> excluir archivos del IDE, me explico la diferencia entre commit (local) y
> push (remoto), y me guio en la autenticacion con GitHub usando Personal
> Access Token. Tambien me explico cuando usar Clean and Build en NetBeans para
> forzar la recompilacion.

---

### Prompt 7: Mejorar la experiencia de usuario del sistema

> **Mi Prompt:** "Quiero que desde cualquier punto del sistema el usuario pueda
> volver al menu anterior escribiendo 0. Hay partes donde ya esta implementado
> pero otras donde no."
>
> **Resumen de la respuesta de la IA:** Analizo los 5 puntos del programa donde
> faltaba la opcion de cancelar e implemento la funcionalidad usando `break`
> para salir de un case del switch y `return` para salir de un metodo,
> dependiendo del contexto.

---

### Prompt 8: Documentacion y README del proyecto

> **Mi Prompt:** "Necesito armar el README.md del proyecto con las secciones que
> pide la consigna (instrucciones de ejecucion, estructura, integrantes, desafios,
> uso de IA). Que secciones deberia incluir y como las estructuro para que quede
> completo y profesional?"
>
> **Resumen de la respuesta de la IA:** Me sugirio la estructura de secciones
> recomendada para un README de proyecto academico y me ayudo a redactar cada
> seccion con la informacion que le fui proporcionando sobre los roles de cada
> integrante, las herramientas utilizadas y los desafios que enfrentamos. Tambien
> me ayudo a organizar la carpeta de documentacion con los archivos de prompts
> individuales de cada integrante.

---

## Herramienta utilizada (IE1)

- **Claude Code** (Anthropic) - Asistente de desarrollo con acceso directo al
  codigo del proyecto. Se uso para implementacion guiada paso a paso,
  explicacion de conceptos de POO, deteccion de bugs en codigo de companeros
  y resolucion de problemas de configuracion de Git/IDE.

---

# IE2 - Interfaz Grafica y Persistencia

**Integrante:** Florencia Agustina Zarate
**Herramienta:** Claude Code (Anthropic) - conversacion completa disponible en el historial de la sesion

---

### Prompt IE2-1: Comprension de la arquitectura MVC+DAO del proyecto

> **Mi Prompt:** "Necesito entender la estructura que armaron mis companeros antes
> de empezar. Lees el README y el schema.sql y me explicás como esta dividido el
> proyecto en capas, que hace cada paquete y qué me toca implementar a mi en la
> Vista?"
>
> **Resumen de la respuesta de la IA:** Leyo el README.md y schema.sql del proyecto,
> explico la separacion en capas (modelos, vista, controlador, daos), identifico que
> mi responsabilidad era `AutogestionView.java` y describio como la vista se comunica
> con el controlador sin tocar directamente la BD ni los modelos.

---

### Prompt IE2-2: Diseno de la interfaz en Figma

> **Mi Prompt:** "Necesito disenar la interfaz del sistema en Figma antes de
> implementarla en Swing. Quiero una app oscura estilo Dracula con una barra lateral
> de navegacion, una seccion de perfil y pestanas para materias, asistencia y notas.
> Como estructuro los frames y el flow de navegacion?"
>
> **Resumen de la respuesta de la IA:** Ayudo a pensar la estructura de frames
> (Frame principal, Mis materias-Materias, Mis materias-Asistencia, Mis materias-Notas,
> Reportes, modales para agregar/dar de baja). Sugiry el uso de componentes de Figma
> para mantener consistencia en botones y colores del tema Dracula a lo largo de todos
> los frames.

---

### Prompt IE2-3: Implementar listener de seleccion en JTable y cargarDatosEnFormulario

> **Mi Prompt:** "Quiero que cuando el usuario haga click en una fila de la tabla de
> materias, los campos de texto de abajo se llenen automaticamente con los datos de
> esa fila. Como agrego un ListSelectionListener a una JTable en Swing y como accedo
> al valor de cada columna de la fila seleccionada?"
>
> **Resumen de la respuesta de la IA:** Explico como obtener el `ListSelectionModel`
> de la tabla, agregar el listener con `addListSelectionListener`, y recuperar el valor
> de cada celda con `tblMaterias.getValueAt(fila, columna)`. Guio la implementacion de
> `cargarDatosEnFormulario()` que populaba los JTextField de nombre, codigo,
> cuatrimestre y anio al seleccionar una fila.

---

### Prompt IE2-4: Implementar guardarCambiosMateria y actualizar la tabla

> **Mi Prompt:** "Tengo el boton Actualizar que debe leer los campos de texto,
> llamar al controlador para persistir los cambios y luego refrescar la tabla.
> Como implemento guardarCambiosMateria() y como recargo los datos en la JTable
> despues de guardar sin que se desincronice con la BD?"
>
> **Resumen de la respuesta de la IA:** Mostro como leer los valores de los JTextField,
> validar que no esten vacios, llamar a `controlador.actualizarMateria()` y usar
> `DefaultTableModel.setValueAt()` para actualizar la fila en pantalla sin recargar
> toda la tabla, o bien llamar a `actualizarTablas()` para recargar desde la BD.

---

### Prompt IE2-5: Depuracion de tablas que no cargaban datos correctamente

> **Mi Prompt:** "Las tablas no me estan cargando los datos. Solo carga la primera
> materia que ingreso, las demas no aparecen. Y cuando selecciono una fila para
> editar, los campos de texto no se llenan con el nombre y codigo correctos."
>
> **Resumen de la respuesta de la IA:** Reviso el codigo de `AutogestionView.java`
> y el `AutogestionController` para identificar donde se perdia la sincronizacion.
> Analizo el metodo `actualizarTablas()`, el cache del controlador y la logica de
> `cargarDatosEnFormulario()`. Se identificaron y corrigieron varios problemas
> relacionados con el orden de llamadas y el filtrado incorrecto de datos.

---

## Herramienta utilizada (IE2)

- **Claude Code** (Anthropic) - Asistente de desarrollo con acceso directo al
  codigo del proyecto. Se uso para implementacion de la Vista Swing, diseno del
  flow de Figma, depuracion de la sincronizacion entre la JTable y la BD, y
  documentacion del proyecto.
