# Reglas Globales del Sistema para Agente IA
ESTE ARCHIVO CONTIENE INSTRUCCIONES CRÍTICAS. LÉELO ANTES DE GENERAR CUALQUIER CÓDIGO.

## 1. Arquitectura (MVC Estricto)
- El proyecto usa MVC (Modelo, Vista, Controlador) + DAO.
- **PROHIBICIÓN ABSOLUTA:** El paquete `controlador` NO puede contener NINGÚN import de `javax.swing.*`, `java.awt.*` ni usar `JOptionPane`. 
- **PROHIBICIÓN ABSOLUTA:** El paquete `dao` NO puede contener lógica de interfaz gráfica.
- La Vista (Swing) NUNCA debe realizar lógica de negocio ni instanciar DAOs. Solo debe comunicarse con el Controlador.

## 2. Persistencia (DAO y JDBC)
- Las validaciones de negocio ya existen en el paquete `modelo`. El Controlador no debe reescribirlas, solo atrapar excepciones o evaluar retornos.
- El código SQL debe utilizar estrictamente `PreparedStatement` y `ResultSet`. NUNCA concatenar strings para queries.

## 3. Estilo de Código y Respuestas
- Genera respuestas útiles, accionables y densas en información. 
- Evita explicaciones redundantes o tutoriales genéricos. Dame solo el código modificado y una breve explicación técnica.
- Usa terminología técnica correcta (Java 17, POO, Patrones de Diseño).
- Si te pido algo que rompe la arquitectura MVC, CORRÍGEME y niégate a hacerlo, explicando por qué viola las reglas.