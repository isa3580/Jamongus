# Ejemplos Prácticos de Autómatas de Pila (AP)

Este repositorio contiene la documentación técnica y las trazas de ejecución detalladas para los ejemplos prácticos presentados en la exposición sobre **Autómatas de Pila**. Se demuestra visualmente cómo funciona la memoria auxiliar estructurada (LIFO) para validar lenguajes no regulares y sintaxis anidadas.

---

## Ejemplo 1: Reconocimiento del Lenguaje $L = \{a^n b^n \mid n \ge 1\}$

Este autómata valida que la cantidad de letras 'a' sea exactamente igual a la de letras 'b'. Utiliza la estrategia de hacer **PUSH** por cada 'a' y **POP** por cada 'b'.

### Traza de Ejecución para la cadena válida: `aabb`

| Paso | Entrada (Cinta) | Estado | Operación en Pila | Estado de la Pila | Resultado / Notas |
| :---: | :---: | :---: | :---: | :---: | :--- |
| **Inicio** | - | $q_0$ | - | `[$]` | $Z_0$ indica fondo de pila libre. |
| **1** | `a` | $q_0$ | PUSH(`A`) | `[$, A]` | Se lee la primera 'a' y se apila. |
| **2** | `a` | $q_0$ | PUSH(`A`) | `[$, A, A]` | Se lee la segunda 'a' y se apila. |
| **3** | `b` | $q_1$ | POP(`A`) | `[$, A]` | Cambia a $q_1$ al leer 'b'. Cancela una 'a'. |
| **4** | `b` | $q_1$ | POP(`A`) | `[$]` | Segunda 'b' cancela la última 'a'. |
| **Fin** | $\epsilon$ | $q_1$ | POP(`$`) | `[]` | Pila vacía. **¡Cadena Aceptada!** |

---

## Ejemplo 2: Validación de Paréntesis Balanceados

Simulación del comportamiento de un analizador sintáctico (*parser*) en la fase de compilación. Cada paréntesis de apertura `(` genera un **PUSH** y cada uno de cierre `)` genera un **POP**.

### Traza de Ejecución para la expresión válida: `(())`

| Paso | Entrada (Código) | Operación en Pila | Estado de la Pila | Diagnóstico Sintáctico |
| :---: | :---: | :---: | :---: | :--- |
| **Inicio** | - | - | `[$]` | Estado inicial con marcador base $Z_0$. |
| **1** | `(` | PUSH(`(`) | `[$, (]` | Abre bloque jerárquico. |
| **2** | `(` | PUSH(`(`) | `[$, (, (]` | Abre sub-bloque interno. |
| **3** | `)` | POP(`( `) | `[$, (]` | Cierra sub-bloque interno de forma correcta. |
| **4** | `)` | POP(`( `) | `[$]` | Cierra bloque jerárquico principal. |
| **Fin** | $\epsilon$ | POP(`$`) | `[]` | Criterio de pila vacía. **¡Sintaxis Correcta!** |

---

## Conclusiones Teóricas
* **Limitación de los AF:** Un Autómata Finito (AFD/AFND) no posee memoria, por lo que no puede contar ni emparejar estas estructuras al infinito.
* **Poder del AP:** Al incluir la pila, el modelo computacional asciende al **Tipo 2 (Lenguajes Libres de Contexto)** en la Jerarquía de Chomsky, permitiendo procesar la sintaxis de los lenguajes de programación modernos.
