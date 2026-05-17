@echo off
setlocal
cd /d "%~dp0"
set ANTLR_JAR=antlr-4.13.2-complete.jar
if not exist %ANTLR_JAR% (
  echo No se encontro %ANTLR_JAR%. Descargue ANTLR4 y coloque el jar en la raiz del proyecto.
  pause
  exit /b 1
)

if not exist gen mkdir gen
if not exist build mkdir build

echo Generando clases ANTLR en gen\ ...
java -jar %ANTLR_JAR% -Dlanguage=Java -o gen grammar\JavaESLexer.g4
if errorlevel 1 (
  echo Error al generar el lexer con ANTLR
  pause
  exit /b 1
)

REM Generar parser usando tokens generados (buscar en gen)
java -jar %ANTLR_JAR% -Dlanguage=Java -o gen -lib gen grammar\JavaESParser.g4

if errorlevel 1 (
  echo Error al generar el parser con ANTLR
  pause
  exit /b 1
)

echo Compilando fuentes Java...
set "SOURCES_FILE=%TEMP%\javaes_sources.txt"
REM Crear listado temporal de fuentes
>"%SOURCES_FILE%" (
  for /r gen %%f in (*.java) do @echo %%f
  for /r src %%f in (*.java) do @echo %%f
)
javac -encoding UTF-8 -cp %ANTLR_JAR% -d build @"%SOURCES_FILE%"
set "JAVAC_EXIT=%errorlevel%"
del "%SOURCES_FILE%" >nul 2>nul
if not "%JAVAC_EXIT%"=="0" (
  echo Error de compilacion
  pause
  exit /b 1
)

echo Compilacion completada. Clases en build\
pause
endlocal
