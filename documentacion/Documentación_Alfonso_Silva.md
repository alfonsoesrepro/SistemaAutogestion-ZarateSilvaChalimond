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

## Herramienta utilizada

- IA como asistente de consulta sobre sintaxis y estructuras de datos en Java.
