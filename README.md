# JAVAES - Front-end de compilador (español)

Proyecto académico profesional: analizador léxico y sintáctico completo con ANTLR4.

**Características:**
- ✅ Lexer expandido con 87 tokens en español académico
- ✅ Parser sin ambigüedades con manejo de precedencia de operadores
- ✅ Análisis léxico y sintáctico interactivo
- ✅ Visualización de árbol sintáctico jerárquico
- ✅ Tabla profesional de tokens con formato Unicode
- ✅ Mensajes de error completamente en español
- ✅ Soporte para identificadores con acentos (UTF-8)

Estructura principal:
- `grammar/`: gramáticas ANTLR4 (JavaESLexer.g4, JavaESParser.g4)
- `src/`: código fuente del ejecutador, lexer/parser runner, visualizador
- `gen/`: archivos generados por ANTLR (no commitear)
- `resources/examples/`: 6 ejemplos válidos y 3 inválidos
- `docs/`: manual, tokens expandidos, pruebas
- `build/`: salida de compilación

## Compilación y Ejecución

### Opción 1: Usar script 
```batch
compilar.bat
ejecutar.bat
```

### Opción 2: Línea de comandos
```powershell
# Compilar (genera clases ANTLR)
cd c:\Users\SuUsuario\JavaES
.\compilar.bat

# Ejecutar menú interactivo
cd build
java -cp ".;..\antlr-4.9.2-complete.jar" Main
```

⚠️ **IMPORTANTE:** No ejecutar `Main.java` directamente desde Eclipse/IDE sin compilar primero con `.\compilar.bat`. Las clases ANTLR necesitan ser generadas.

## Token Set Completo

87 tokens organizados en 20 categorías:

**Tipos:** entero, decimal, cadena, booleano, vacio, caracter
**Control:** si, sino, mientras, para, hacer, cambiar, caso, defecto, romper, continuar, retornar
**Excepciones:** intentar, capturar, finalmente, lanzar
**OOP:** clase, interfaz, nuevo, este, super
**Modificadores:** publico, privado, protegido, estatico, final
**Especiales:** verdadero, falso, nulo, importar, paquete, imprimir, leer
**Operadores:** ++, --, +, -, *, /, %, ==, !=, >, <, >=, <=, &&, ||, !, =, +=, -=, *=, /=, %=
**Delimitadores:** (), {}, [], ;, ,, :, ->, @, etc.

Ver `docs/TOKENS_EXPANDIDOS.md` para detalles completos.

## Menú Interactivo

1. **Ejecutar análisis léxico** - Genera tabla de tokens
2. **Ejecutar análisis sintáctico** - Verifica sintaxis
3. **Mostrar árbol sintáctico** - Visualización jerárquica (esta en prueba)
4. **Ejemplos válidos** - Ejecutar uno de 6 ejemplos correctos
5. **Ejemplos con error** - Ejecutar uno de 3 ejemplos con errores
6. **Salir**

## Ejemplos Incluidos

**Válidos:**
- `ejemplo_basico.javaes` - Declaraciones de variables
- `ejemplo_clase.javaes` - Definición de clase
- `ejemplo_if.javaes` - Condicionales
- `ejemplo_metodo.javaes` - Métodos y parámetros
- `ejemplo_while.javaes` - Bucles while
- `ejemplo_expandido.javaes` - Nuevos tokens (++, +=, &&, ||, etc.)
- `ejemplo_control_flujo.javaes` - para, cambiar, case, intentar/capturar

**Inválidos (demostración de errores):**
- `error_lexico.javaes` - Carácter inválido ($)
- `error_lexico_operador.javaes` - Operador no reconocido (@)
- `error_sintactico.javaes` - Sintaxis incorrecta

Siga `docs/MANUAL.md` para más información.
