@echo off
setlocal
set "PROJ_DIR=%~dp0"
set "BUILD_DIR=%PROJ_DIR%build"
set "ANTLR_JAR=%PROJ_DIR%antlr-4.13.2-complete.jar"

if not exist "%BUILD_DIR%" (
  echo Error: Carpeta build no encontrada. Ejecute compilar.bat primero.
  pause
  exit /b 1
)

if not exist "%ANTLR_JAR%" (
  echo Error: No se encontro antlr-4.13.2-complete.jar en la raiz del proyecto.
  pause
  exit /b 1
)

cd /d "%BUILD_DIR%"
echo Iniciando JAVAES...
java -cp "%BUILD_DIR%;%ANTLR_JAR%" Main

pause
endlocal
