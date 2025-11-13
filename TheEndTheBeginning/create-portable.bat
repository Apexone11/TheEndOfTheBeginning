@echo off
REM Create Portable Windows Application
REM This creates a self-contained application directory

echo ========================================
echo Creating Portable Windows Application
echo ========================================
echo.

REM Check if jpackage is available
where jpackage >nul 2>&1
if %ERRORLEVEL% NEQ 0 (
    echo ERROR: jpackage not found!
    pause
    exit /b 1
)

echo Step 1: Building JAR file...
call mvnw.cmd clean package -DskipTests
if %ERRORLEVEL% NEQ 0 (
    echo ERROR: Build failed!
    pause
    exit /b 1
)

echo.
echo Step 2: Creating portable application...
echo This may take 5-10 minutes...
echo.

set JAR_FILE=theendthebeginning-4.0.0.jar
set APP_NAME=TheEndTheBeginning

jpackage ^
    --input target ^
    --name %APP_NAME% ^
    --main-jar %JAR_FILE% ^
    --main-class gameproject.TheEndTheBeginning ^
    --type app-image ^
    --dest target\jpackage ^
    --app-version 4.0.0 ^
    --vendor "Abdul Fornah" ^
    --copyright "Copyright 2024 Abdul Fornah"

if %ERRORLEVEL% EQU 0 (
    echo.
    echo ========================================
    echo Portable Application Created!
    echo ========================================
    echo.
    echo Application location:
    echo   target\jpackage\%APP_NAME%\
    echo.
    echo To run the game:
    echo   target\jpackage\%APP_NAME%\bin\%APP_NAME%.exe
    echo.
    echo You can zip this folder and distribute it.
    echo It includes the JRE and all dependencies.
) else (
    echo.
    echo ERROR: Application creation failed!
    echo Check the error messages above.
)

pause

