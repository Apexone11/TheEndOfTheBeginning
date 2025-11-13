@echo off
title The End The Beginning v3.0 - Dungeon Escape
echo.
echo ========================================
echo  The End The Beginning v3.0
echo  Dungeon Escape Game
echo ========================================
echo.
echo Starting game using Maven JavaFX plugin...
echo This may take a moment to initialize...
echo.

cd /d "%~dp0"

.\mvnw.cmd javafx:run

echo.
if errorlevel 1 (
    echo Error: Failed to start the game!
    echo.
    echo Trying alternative method...
    echo.
    .\mvnw.cmd compile javafx:run
    if errorlevel 1 (
        echo.
        echo Still failed. Please check if Java and Maven are properly installed.
        echo.
    )
) else (
    echo Game closed normally.
)

echo.
echo Press any key to close this window...
pause >nul