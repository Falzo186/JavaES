# JAVAES — Analizador Léxico

JAVAES es un lenguaje académico inspirado en Java que utiliza palabras clave en español y un lexer construido con ANTLR4 para análisis léxico y sintáctico.

## Información del Curso

- Materia: Compiladores
- Institución: [Nombre de la universidad]
- Semestre: 2026-1
- Profesor: [Nombre del profesor]

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

1. Abrir PowerShell en `c:\Users\SuUsuario\JavaES`
2. Ejecutar:
   ```powershell
   .\compilar.bat
   .\ejecutar.bat
   ```
3. Alternativa directa:
   ```powershell
   cd c:\Users\SuUsuario\JavaES
   .\compilar.bat
   cd build
   java -cp ".;..\antlr-4.9.2-complete.jar" Main
   ```

⚠️ **IMPORTANTE:** No ejecutar `Main.java` directamente desde Eclipse/IDE sin compilar primero con `.\compilar.bat`.

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

