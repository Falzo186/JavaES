# JAVAES — Analizador Léxico

JAVAES es un lenguaje académico inspirado en Java que utiliza palabras clave en español y un lexer construido con ANTLR4 para análisis léxico y sintáctico.

## Información del Curso

- Materia: PROGRAMACION DE SISTEMAS DE BASE 1
- Institución: Universidad Autonoma De Tamaulipas
- Semestre: 2026-1
- Profesor: Muñoz Quintero Dante Adolfo

## Integrantes del Equipo

| Nombre | Matrícula |
| Jesus Alejandro Murillo Zavala | 2223330180 |
| Adela Vega Ruiz  | 2223339021 |
|Clemente Pecina Jose Eduardo  | 2193217022 |
|Saldaña Nieto Pedro Daniel	| 2223330192 |



## Descripción del Lenguaje

JAVAES es un lenguaje de propósito académico para el curso de compiladores. Soporta tipos básicos en español, declaraciones de variables, expresiones aritméticas, control de flujo y construcción de árbol sintáctico.

## Tokens Reconocidos

Lista de categorías soportadas:

- Palabras reservadas: `entero`, `decimal`, `cadena`, `booleano`, `vacio`, `si`, `sino`, `mientras`, `para`, `hacer`, `retornar`, `clase`, `publico`, `privado`, `importar`, `paquete`, etc.
- Tipos de literales: `ENTERO`, `DECIMAL`, `CADENA`, `CARACTER`, `BOOLEANO`, `NULO`
- Identificadores con acentos y guion bajo
- Operadores aritméticos: `++`, `--`, `+`, `-`, `*`, `/`, `%`
- Operadores relacionales: `==`, `!=`, `<`, `>`, `<=`, `>=`
- Operadores lógicos: `&&`, `||`, `!`
- Asignación simple y compuesta: `=`, `+=`, `-=`, `*=`, `/=`, `%=`
- Delimitadores: `;`, `,`, `(`, `)`, `{`, `}`, `[`, `]`, `:`, `->`, `@`
- Comentarios: `//` y `/* ... */`

## Cómo ejecutar

## Requisitos
- Java 17 o superior de preferencia el mas actalizado por favor 
- Archivo `antlr-4.9.2-complete.jar` en la raíz del proyecto 

1. Abra PowerShell en la carpeta del proyecto, por ejemplo:
   ```powershell
   cd C:\Users\SuUsuario\JavaES
   ```
2. Compile el proyecto con el script:
   ```powershell
   .\compilar.bat
   ```
3. Ejecute el programa principal:
   ```powershell
   .\ejecutar.bat
   ```

### Alternativa directa (mas sencillo)

Si necesita ejecutar manualmente sin usar `ejecutar.bat`:
```powershell
cd C:\Users\SuUsuario\JavaES
.\compilar.bat
cd build
java -cp ".;..\antlr-4.9.2-complete.jar" Main
```

⚠️ **IMPORTANTE:** No ejecute `Main.java` directamente desde Eclipse u otro IDE sin compilar primero con `.\compilar.bat`.

## Ejemplos de uso

- `ejemplo_basico.javaes`: declaración de variables y asignación
- `ejemplo_clase.javaes`: definición de clase y método
- `ejemplo_if.javaes`: condicional `si / sino`
- `ejemplo_while.javaes`: bucle `mientras`
- `ejemplo_expandido.javaes`: operadores `++`, `+=`, `&&`, `||`
- `ejemplo_control_flujo.javaes`: `para`, `cambiar`, `caso`, `intentar / capturar`

## Archivos principales

- `grammar/JavaESLexer.g4`: definición completa del lexer
- `src/`: código de ejecución y manejo de errores
- `resources/examples/`: ejemplos de prueba
- `build/`: salida compilada

