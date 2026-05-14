# JAVAES - Front-end de compilador (español)

Proyecto académico: analizador léxico y sintáctico con ANTLR4.

Estructura principal:
- grammar/: gramáticas ANTLR
- src/: código fuente del ejecutador y manejadores de error
- gen/: archivos generados por ANTLR (no commitear)
- resources/examples/: ejemplos válidos e inválidos
- docs/: manual y pruebas
- build/: salida de compilación

## Compilación y Ejecución

### Opción 1: Usar script (RECOMENDADO)
```batch
compilar.bat
ejecutar.bat
```

### Opción 2: Línea de comandos
```powershell
# Compilar
cd c:\Users\alexp\JavaES
.\compilar.bat

# Ejecutar
cd build
java -cp ".;..\antlr-4.9.2-complete.jar" Main
```

⚠️ **IMPORTANTE:** No ejecutar `Main.java` directamente desde Eclipse/IDE sin compilar primero con `compilar.bat`. Las clases ANTLR necesitan ser generadas.

Siga `docs/MANUAL.md` para más información.
