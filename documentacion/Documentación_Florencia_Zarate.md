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

## Herramienta utilizada

- **Claude Code** (Anthropic) - Asistente de desarrollo con acceso directo al
  codigo del proyecto. Se uso para implementacion guiada paso a paso,
  explicacion de conceptos de POO, deteccion de bugs en codigo de companeros
  y resolucion de problemas de configuracion de Git/IDE.
