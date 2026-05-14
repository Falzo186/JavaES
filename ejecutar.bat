@echo off
setlocal
cd /d "%~dp0"

if not exist build (
  echo Error: Carpeta build no encontrada. Ejecute compilar.bat primero.
  pause
  exit /b 1
)

cd build
echo Iniciando JAVAES...
java -cp ".;..\antlr-4.9.2-complete.jar" Main

pause
endlocal
