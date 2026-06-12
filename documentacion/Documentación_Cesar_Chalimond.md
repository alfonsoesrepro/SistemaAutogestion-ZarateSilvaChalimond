# Documentacion de Uso de IA - Cesar Nicolas Chalimond

A continuación se detallan los prompts utilizados durante el desarrollo de la
lógica avanzada, reportes y refactorización del proyecto. Se utilizó la IA como
un asistente de consulta técnica y revisión de código.

---

### Prompt 1: Refactorización de Arquitectura

> **Mi Prompt:** "Estoy armando el proyecto de la facultad en Java y quiero
> separar bien las responsabilidades. Tengo todas mis clases (Estudiante,
> Materia, Inscripcion) y mis interfaces (Consultable, Evaluable) en la raíz.
> ¿Cuál es la mejor convención de paquetes en Java para separar esto y cómo
> debería nombrar a las interfaces para seguir buenas prácticas?"
>
> **Resumen de la respuesta de la IA:** Me sugirió crear los paquetes `modelos`
> e `interfaces`, y renombrar las interfaces agregando una 'I' mayúscula al
> inicio (ej. `IConsultable`), indicándome que debía actualizar las
> declaraciones `implements` y los `imports` en el Main.

---

### Prompt 2: Lógica Recursiva

> **Mi Prompt:** "Tengo un método `getPromedioGeneral()` en mi clase
> `Estudiante` que usa un bucle `for` para sumar los promedios de las materias.
> La consigna me pide hacerlo de forma recursiva (Bonus). dime cómo estructurar
> la función auxiliar recursiva en Java sin que se me rompa el índice de la
> lista `materias`"
>
> **Resumen de la respuesta de la IA:** Me recomendó crear un método
> `private double getSumaPromediosRecursivo(int index)` y evaluar si
> `index >= materias.size()` como condición de corte, retornando 0.0, y en caso
> contrario sumar el promedio actual y llamarse recursivamente con `index + 1`.

---

### Prompt 3: Algoritmos de Ordenamiento (Comparator)

> **Mi Prompt:** "Estoy haciendo un reporte en la clase Main que debe listar las
> materias en riesgo ordenadas de menor a mayor asistencia. Tengo un
> `ArrayList<InscripcionMateria>`. ¿Cómo utilizo `Collections.sort()` con un
> `Comparator` para ordenar objetos basándome en un atributo `double` (que sería
> el porcentaje) en Java?"
>
> **Resumen de la respuesta de la IA:** Me explicó cómo instanciar un
> `Comparator` anónimo dentro del `sort()` y utilizar el método
> `Double.compare(i1.getPorcentajeAsistencia(), i2.getPorcentajeAsistencia())`
> para lograr el ordenamiento ascendente.

---

### Prompt 4: Polimorfismo y Herencia Avanzada

> **Mi Prompt:** "Creé las clases `MateriaAnual` y `MateriaCuatrimestral` que
> heredan de `Materia`. La materia anual necesita que la condición de 'Regular'
> sea 70% en vez de 75%. crea un método `getPorcentajeRegularidad()` en la clase
> padre y sobrescribirlo en las hijas para luego llamarlo desde
> `InscripcionMateria`"
>
> **Resumen de la respuesta de la IA:** Confirmó que es el enfoque correcto y
> implementó la llamada dinámica en el método `getCondicion()`.

---

### Prompt 5: Interfaz Adicional (Rankeable)

> **Mi Prompt:** "Escribí esta interfaz `IRankeable` con el método
> `getPuntajeRanking()`. Mi fórmula matemática es
> `(promedio * 0.6) + (asistencia * 0.4)`. Revisa este fragmento de código que
> escribí en `InscripcionMateria`:
> `public double getPuntajeRanking() { return (getPromedio() * 0.6) + (getPorcentajeAsistencia() * 0.4); }`
> ¿Está bien aplicado matemáticamente según las convenciones de Java?"
>
> **Resumen de la respuesta de la IA:** Validó que el fragmento era correcto y
> que la sintaxis y los tipos de retorno (`double`) coincidían perfectamente.

---

# IE2 - Interfaz Grafica y Persistencia

**Integrante:** Cesar Nicolas Chalimond
**Herramienta:** Antigravity (IDE) + Gemini 2.5 Pro (agente)

---

### Prompt IE2-1: PreparedStatement con RETURN_GENERATED_KEYS

> **Mi Prompt:** "Escribe la estructura base del codigo en JDBC para hacer un
> PreparedStatement que inserte un registro en la tabla estudiantes y recupere
> el ID autogenerado usando Statement.RETURN_GENERATED_KEYS. Solo necesito la
> estructura del try-catch con recursos."
>
> **Resumen de la respuesta de la IA:** Genero la estructura del try-with-resources
> pasando `Statement.RETURN_GENERATED_KEYS` como segundo parametro de
> `prepareStatement()`, y mostro como recuperar el ID generado con
> `ps.getGeneratedKeys()` y `rs.getLong(1)` dentro de un segundo try-with-resources.

---

### Prompt IE2-2: Mapear ResultSet de INNER JOIN a lista de InscripcionDTO

> **Mi Prompt:** "Genera un metodo en Java que reciba un ResultSet proveniente
> de un INNER JOIN entre Estudiantes y Materias, y mapee esos datos a una lista
> de objetos InscripcionDTO."
>
> **Resumen de la respuesta de la IA:** Mostro como iterar el ResultSet con
> `while (rs.next())`, acceder a las columnas del JOIN por nombre
> (`rs.getString("nombre")`, `rs.getString("codigo")`, etc.) y construir objetos
> `InscripcionDTO` que encapsulan los datos combinados de ambas tablas en una lista.

---

### Prompt IE2-3: Expresion regular para validar el campo Legajo

> **Mi Prompt:** "Proporciona una expresion regular en Java para validar desde la
> Vista que el campo 'Legajo' tenga el formato correcto antes de enviar la peticion
> al Controlador. Debe aceptar exactamente 5 numeros."
>
> **Resumen de la respuesta de la IA:** Proporciono el patron `"\\d{5}"` y mostro
> como usarlo con `legajo.matches("\\d{5}")` desde la Vista para validar el input
> antes de llamar al controlador, manteniendo la logica de validacion de formato en
> la capa correcta.

---

### Prompt IE2-4: JComboBox con Materia: toString vs ListCellRenderer

> **Mi Prompt:** "Para cargar un JComboBox con objetos de la clase Materia en mi
> interfaz grafica, me conviene sobrescribir el metodo toString() de la entidad o
> es mas escalable implementar un ListCellRenderer?"
>
> **Resumen de la respuesta de la IA:** Explico que sobrescribir `toString()` es
> mas simple y suficiente para la mayoria de los casos academicos, pero que
> `ListCellRenderer` es preferible cuando se necesita mostrar texto diferente al
> que imprime `toString()` en otros contextos del sistema (logs, debugs, etc.).
> Recomendo `toString()` para este proyecto dado el alcance.
