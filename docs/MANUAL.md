# MANUAL de uso - JAVAES

## Requisitos
- Java 11 o superior de preferencia el mas actalizado por favor 
- Archivo `antlr-4.9.2-complete.jar` en la raíz del proyecto 

## Compilación y Ejecución - Paso a Paso

### ⚠️ IMPORTANTE
**NO ejecutar `Main.java` directamente desde Eclipse/IDE u otros IDE**. Las clases ANTLR deben generarse primero.

### Opción 1: Usar Scripts 

1. Abra PowerShell o CMD en la carpeta del proyecto
2. Ejecute:
```batch
.\compilar.bat
```
Espere a que termine (genera las clases ANTLR en `gen/`)

3. Luego ejecute:
```batch
.\ejecutar.bat
```

### Opción 2: Línea de Comandos Manual (esta suele ser mas sencilla)

1. Compilar:
```powershell
cd c:\Users\SuUsuario\JavaES   debe ser la ruta donde puso el repositorio este es un ejemplo en mi caso es directo en la Carpeta Usuario
.\compilar.bat
```

2. Ejecutar:
```powershell
cd build
java -cp ".;..\antlr-4.9.2-complete.jar" Main
```

## Uso del Programa

Menú:
```
1. Ejecutar análisis léxico
   - Muestra tabla de tokens (Token, Lexema, Línea)
   - Pregunta si desea crear árbol sintáctico

2. Ejecutar análisis sintáctico
   - Valida programa
   - Muestra errores sintácticos (línea, columna, descripción)

3. Mostrar árbol sintáctico
   - Visualiza estructura parseada

4. Ejecutar ejemplo válido
   - Procesa todos los ejemplos válidos

5. Ejecutar ejemplo con error
   - Procesa ejemplos inválidos (muestra errores)

6. Salir
```

## Ejemplos Incluidos

**Válidos:**
- ejemplo_basico.javaes (variables)
- ejemplo_clase.javaes (clases)
- ejemplo_if.javaes (condicionales)
- ejemplo_metodo.javaes (métodos)
- ejemplo_while.javaes (bucles)

**Inválidos:**
- error_lexico.javaes
- error_lexico_operador.javaes
- error_sintactico.javaes

## Solución de Problemas

### Error: "Unresolved compilation problems"
**Causa:** Main.java se ejecuta sin compilar con ANTLR
**Solución:** 
1. Ejecute `compilar.bat`
2. Luego ejecute `ejecutar.bat` o `java -cp ".;..\antlr-4.9.2-complete.jar" Main` desde `build/`

### Error: "antlr-4.9.2-complete.jar no encontrado"
**Solución:** Descargue ANTLR4 desde https://www.antlr.org/download/ y coloque el jar en la raíz del proyecto

### Error: "No such file or directory: resources/examples"
**Solución:** Ejecute desde la carpeta `build/` (vea Opción 2 arriba)
