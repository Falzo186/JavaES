@echo off
setlocal
set "PROJ_DIR=%~dp0"
set "BUILD_DIR=%PROJ_DIR%build"
set "ANTLR_JAR=%PROJ_DIR%antlr-4.13.2-complete.jar"
set "JAVA_EXE=%PROJ_DIR%oracleJdk-26\bin\java.exe"

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

if not exist "%JAVA_EXE%" (
  set "JAVA_EXE=java"
)

cd /d "%BUILD_DIR%"
echo Iniciando JAVAES...
"%JAVA_EXE%" -cp "%BUILD_DIR%;%ANTLR_JAR%" Main

pause
endlocal
