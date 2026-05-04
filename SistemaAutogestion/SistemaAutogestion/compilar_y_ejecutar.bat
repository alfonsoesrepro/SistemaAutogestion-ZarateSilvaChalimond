@echo off
echo === COMPILANDO EL PROYECTO ===
cd src
javac sistemaautogestion/zaratesilvachalimond/interfaces/*.java sistemaautogestion/zaratesilvachalimond/modelos/*.java sistemaautogestion/zaratesilvachalimond/*.java

if %errorlevel% neq 0 (
    echo.
    echo ❌ ERROR DE COMPILACION. Revisa tu codigo.
    pause
    exit /b %errorlevel%
)

echo.
echo === EJECUTANDO EL SISTEMA ===
echo.
java sistemaautogestion.zaratesilvachalimond.SistemaAutogestionZarateSilvaChalimond

echo.
pause
