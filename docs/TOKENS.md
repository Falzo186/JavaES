# Catálogo de Tokens (JAVAES)

Este documento unifica el catálogo de tokens del proyecto JAVAES. Contiene
el listado completo de tokens en español académico que usa el lexer.

TOTAL: 87 tokens (+ EOF)

Categorías y tokens principales
--------------------------------

1) Comentarios y espacios
	- LINE_COMMENT  : '//' ...
	- BLOCK_COMMENT : '/* ... */'
	- WS            : espacios y saltos (ignorados)

2) Tipos de datos
	- ENTERO_TIPO, DECIMAL_TIPO, CADENA_TIPO, BOOLEANO_TIPO, VACIO_TIPO, CARACTER_TIPO

3) Control de flujo
	- SI, SINO, MIENTRAS, PARA, HACER, CAMBIAR, CASO, DEFECTO, ROMPER, CONTINUAR, RETORNAR

4) Manejo de excepciones
	- INTENTAR, CAPTURAR, FINALMENTE, LANZAR

5) Clases y objetos
	- CLASE, INTERFAZ, NUEVO, ESTE, SUPER

6) Modificadores
	- PUBLICO, PRIVADO, PROTEGIDO, ESTATICO, FINAL

7) Valores especiales
	- VERDADERO, FALSO, NULO

8) Importación y paquetes
	- IMPORTAR, PAQUETE

9) Entrada / Salida
	- IMPRIMIR, LEER

10) Literales (cadenas/caracteres)
	- CADENA, CARACTER (soportan escapes vía fragmento ESC)

11) Literales numéricos
	- DECIMAL_CIENTIFICO, DECIMAL, ENTERO, ENTERO_HEX, ENTERO_OCTAL

12) Identificadores
	- IDENTIFICADOR: admite letras, dígitos, guion bajo y caracteres acentuados (UTF-8)

13) Operadores aritméticos
	- INCREMENTO (++), DECREMENTO (--), SUMA (+), RESTA (-), MULTIPLICACION (*), DIVISION (/), MODULO (%)

14) Operadores relacionales
	- IGUALDAD (==), DESIGUALDAD (!=), MAYOR_IGUAL_QUE (>=), MENOR_IGUAL_QUE (<=), MAYOR_QUE (>), MENOR_QUE (<)

15) Operadores lógicos
	- Y_LOGICO (&&), O_LOGICO (||), NEGACION (!)

16) Operadores de asignación
	- SUMA_ASIGNACION (+=), RESTA_ASIGNACION (-=), MULTIPLICACION_ASIGNACION (*=), DIVISION_ASIGNACION (/=), MODULO_ASIGNACION (%=), ASIGNACION (=)

17) Delimitadores
	- PARENTESIS_ABIERTO, PARENTESIS_CERRADO, LLAVE_ABIERTA, LLAVE_CERRADA, CORCHETE_ABIERTO, CORCHETE_CERRADO
	- PUNTO_Y_COMA (;), COMA (,), PUNTO (.), DOS_PUNTOS (:), DOS_PUNTOS_DOBLE (::), FLECHA (->), ARROBA (@)

18) Operadores especiales
	- INTERROGACION (?), AMPERSAND (&), PIPE (|), CIRCUNFLEJO (^), TILDE (~)

19) Manejo de errores
	- ERROR_LEXICO: cualquier carácter no reconocido

Notas importantes
------------------
- Orden de prioridad: Los tokens de múltiples caracteres (ej. '++', '+=', '==') se definen antes
  que sus equivalentes de un solo carácter para evitar ambigüedades.
- Las palabras reservadas están definidas antes que `IDENTIFICADOR` para que se reconozcan correctamente.
- Identificadores soportan caracteres españoles (rango Unicode) y `_`.
- Las cadenas y caracteres permiten secuencias de escape (fragmento `ESC`).

Ejemplos rápidos
----------------
- `contador++` → `IDENTIFICADOR` + `INCREMENTO`
- `total += 10;` → `IDENTIFICADOR` + `SUMA_ASIGNACION` + `ENTERO` + `PUNTO_Y_COMA`
- `si (a && b) { ... }` → `SI` + `PARENTESIS_ABIERTO` + `IDENTIFICADOR` + `Y_LOGICO` + `IDENTIFICADOR` + `PARENTESIS_CERRADO`

Detalles y uso
---------------
El lexer genera una tabla profesional con columnas: `TOKEN | LEXEMA | LÍNEA | COLUMNA`.
Consulte `README.md` y el código fuente en `grammar/JavaESLexer.g4` para la definición exacta.

-- Documento unificado: `docs/TOKENS.md`
