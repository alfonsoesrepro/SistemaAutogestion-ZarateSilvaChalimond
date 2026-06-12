# Documentacion de Uso de IA - Alfonso Silva

Se utilizo IA como herramienta de consulta para resolver dudas sobre sintaxis
y estructuras de datos en Java. A continuacion se detallan las consultas
realizadas durante el desarrollo del modelado POO del proyecto.

---

### Prompt 1: Inicializacion de un ArrayList en el constructor

> **Mi Prompt:** "Como inicializo un ArrayList dentro del constructor de una
> subclase en Java?"
>
> **Resumen de la respuesta de la IA:** Explico que el ArrayList puede
> inicializarse en la declaracion del atributo o explicitamente en el constructor
> con `new ArrayList<>()`, y que tambien puede recibirse como parametro.

---

### Prompt 2: Convertir una lista a un arreglo

> **Mi Prompt:** "Java tiene un equivalente al .ToArray() de C#?"
>
> **Resumen de la respuesta de la IA:** Explico el metodo `.toArray(new Tipo[0])`
> y el camino inverso con `Arrays.asList()`.

---

### Prompt 3: Agregar y eliminar datos de una lista

> **Mi Prompt:** "Como manipulo los datos de un ArrayList?"
>
> **Resumen de la respuesta de la IA:** Explico los metodos `.add()`, `.remove()`,
> `.get()`, `.set()`, `.size()`, `.contains()`, `.clear()` e `.isEmpty()`.

---

### Prompt 4: El operador ternario

> **Mi Prompt:** "Como funciona el operador ternario en Java?"
>
> **Resumen de la respuesta de la IA:** Explico la sintaxis
> `condicion ? valorSiVerdadero : valorSiFalso` comparandolo con un if-else
> tradicional, con ejemplos aplicados al proyecto.

---

### Prompt 5: Recorrer una lista sin convertirla a un arreglo

> **Mi Prompt:** "Cuales son las formas de iterar un ArrayList en Java?"
>
> **Resumen de la respuesta de la IA:** Explico tres metodos: for-each, for
> clasico e Iterator, comparando cuando conviene cada uno.

---

### Prompt 6: Buscar un atributo en la lista

> **Mi Prompt:** "Que metodo de recorrido conviene para buscar una Materia dentro
> de la lista de InscripcionMateria?"
>
> **Resumen de la respuesta de la IA:** Recomendo el for-each y explico el uso
> de `.equals()` para comparar objetos y Strings.

---

### Prompt 7: Calcular el promedio de la lista de notas

> **Mi Prompt:** "Como calculo el promedio de los elementos de una lista de
> doubles?"
>
> **Resumen de la respuesta de la IA:** Recomendo el for-each y explico la
> diferencia entre `double` (primitivo) y `Double` (wrapper), ademas de la
> importancia de verificar si la lista esta vacia antes de dividir.

---

### Prompt 8: Inicializacion en constructores con y sin parametros

> **Mi Prompt:** "Debo inicializar las listas en ambos tipos de constructores?"
>
> **Resumen de la respuesta de la IA:** Explico que cuando la lista se recibe
> como parametro se asigna directamente, y cuando no se recibe se inicializa
> con `new ArrayList<>()`.

---

## Herramienta utilizada (IE1)

- IA como asistente de consulta sobre sintaxis y estructuras de datos en Java.

---

# IE2 - Interfaz Grafica y Persistencia

**Integrante:** Alfonso Silva
**Herramienta:** ChatGPT y Claude (Anthropic)

---

### Prompt IE2-1: Serializacion y deserializacion con fromTexto

> **Mi Prompt:** "Como implemento fromTexto() en Estudiante e InscripcionMateria
> para reconstruir los objetos desde un CSV guardado en archivo de texto? Necesito
> convertir un parametro int guardado como String y reconstruir el objeto Materia
> inline para pasarselo al constructor de InscripcionMateria."
>
> **Resumen de la respuesta de la IA:** Explico el uso de `Integer.parseInt()` y
> `String.valueOf()` para convertir entre tipos primitivos y String. Mostro como
> en `fromTexto()` de `InscripcionMateria` se construye primero el objeto `Materia`
> a partir de columnas del CSV y luego se pasa directamente al constructor externo,
> sin necesidad de una variable intermedia.

---

### Prompt IE2-2: Resolver error de dependencias FlatLaf en NetBeans

> **Mi Prompt:** "El import FlatDraculaIJTheme me da error porque los JARs estan
> referenciados desde la PC de mi companera y no desde mi entorno. Como lo resuelvo
> para que funcione en cualquier maquina del grupo?"
>
> **Resumen de la respuesta de la IA:** Indico que habia que descargar los 3 JARs
> (`flatlaf`, `flatlaf-intellij-themes`, `mysql-connector-j`), guardarlos en una
> carpeta `lib/` dentro del proyecto, luego en NetBeans eliminar las referencias
> rotas desde Project Properties > Libraries y volver a agregarlas apuntando a la
> nueva carpeta local. De esta forma los JARs viajan con el proyecto y funcionan
> en cualquier entorno.

---

### Prompt IE2-3: Implementar listener de seleccion y cargarDatosEnFormulario en la vista

> **Mi Prompt:** "Necesito que al seleccionar una fila en tblMaterias1 se carguen
> automaticamente los datos en los campos de texto para poder editarlos. Como
> implemento el listener de seleccion y el metodo cargarDatosEnFormulario()?"
>
> **Resumen de la respuesta de la IA:** Mostro como agregar el listener con
> `tblMaterias1.getSelectionModel().addListSelectionListener()`, obtener el indice
> de la fila seleccionada con `getSelectedRow()` y recuperar cada valor de celda
> con `getValueAt(fila, columna)` para poblar los JTextField correspondientes.
> Tambien explico donde posicionar exactamente estos fragmentos dentro de
> `AutogestionView.java`.

---

### Prompt IE2-4: Metodo actualizarMateria en el controlador

> **Mi Prompt:** "Necesito agregar el metodo actualizarMateria() al controlador
> para que la vista pueda persistir los cambios de edicion. Que validaciones debe
> hacer y como refresco el cache local de materias despues de actualizar?"
>
> **Resumen de la respuesta de la IA:** Genero el metodo `actualizarMateria()` en
> `AutogestionController` ubicandolo entre `inscribirMateria()` y `listarInscripciones()`.
> El metodo valida que el codigo no este vacio y que el cuatrimestre sea 1 o 2,
> construye el objeto `Materia`, llama a `materiaDAO.actualizar()` y si el resultado
> es exitoso refresca `materiasCache` con una nueva consulta a la BD.

---

### Prompt IE2-5: Metodo actualizar en MateriaDAO (archivos txt y JDBC)

> **Mi Prompt:** "Como implemento el metodo actualizar() en MateriaDAO para la
> version de archivos de texto y para la version de base de datos?"
>
> **Resumen de la respuesta de la IA:** Para la version txt: cargo la lista completa,
> busco la materia por codigo con un for y la reemplazo usando `lista.set(indice, nueva)`,
> luego reescribo el archivo completo. Para la version JDBC: agrego la firma a la
> interfaz `MateriaDAO` e implemento en `MateriaDAOMySQL` un `UPDATE materias SET
> nombre = ?, cuatrimestre = ? WHERE codigo = ?`, sin incluir la columna `anio`
> porque no existe en la BD (esta hardcodeada como 1 en el modelo).

---

## Herramienta utilizada (IE2)

- **ChatGPT** y **Claude** (Anthropic) - Asistentes de consulta para implementacion
  de persistencia en archivos de texto, resolucion de dependencias de IDE y
  desarrollo de la capa DAO.
