@echo off
REM Create Windows Installer using jpackage directly
REM This script uses jpackage which comes with JDK 17+

echo ========================================
echo Creating Windows Installer
echo ========================================
echo.

REM Check if jpackage is available
where jpackage >nul 2>&1
if %ERRORLEVEL% NEQ 0 (
    echo ERROR: jpackage not found!
    echo jpackage comes with JDK 17+.
    echo Please ensure JDK 17 or higher is installed and in PATH.
    echo.
    java -version
    pause
    exit /b 1
)

echo Step 1: Building JAR file...
call .\mvnw.cmd clean package -DskipTests
if %ERRORLEVEL% NEQ 0 (
    echo ERROR: Build failed!
    pause
    exit /b 1
)

echo.
echo Step 2: Creating Windows installer with jpackage...
echo This may take 5-10 minutes...
echo.

set JAR_FILE=target\theendthebeginning-4.0.0.jar
set APP_NAME=TheEndTheBeginning
set INSTALLER_NAME=TheEndTheBeginning-Setup
set ICON_PATH=src\main\resources\icons\iconimage.ico

jpackage ^
    --input target ^
    --name %APP_NAME% ^
    --main-jar %JAR_FILE% ^
    --main-class gameproject.TheEndTheBeginning ^
    --type exe ^
    --dest target\jpackage ^
    --app-version 4.0.0 ^
    --vendor "Abdul Fornah" ^
    --copyright "Copyright 2024 Abdul Fornah" ^
    --win-shortcut ^
    --win-menu ^
    --win-dir-chooser ^
    --win-per-user-install

if %ERRORLEVEL% EQU 0 (
    echo.
    echo ========================================
    echo Installer Created Successfully!
    echo ========================================
    echo.
    echo Installer location:
    echo   target\jpackage\%INSTALLER_NAME%.exe
    echo.
    echo You can now distribute this installer.
    echo It includes the JRE and all dependencies.
) else (
    echo.
    echo ERROR: Installer creation failed!
    echo Check the error messages above.
)

pause

