@echo off
echo ========================================
echo Building The End The Beginning v4.0.0
echo ========================================
echo.

if not exist "mvnw.cmd" (
    echo ERROR: Maven Wrapper (mvnw.cmd) not found!
    echo Please ensure mvnw.cmd is in the current directory.
    pause
    exit /b 1
)

echo Step 1: Cleaning previous build...
call .\mvnw.cmd clean
if %ERRORLEVEL% NEQ 0 (
    echo ERROR: Clean failed!
    pause
    exit /b 1
)

echo.
echo Step 2: Compiling project...
call .\mvnw.cmd compile -DskipTests
if %ERRORLEVEL% NEQ 0 (
    echo ERROR: Compilation failed!
    pause
    exit /b 1
)

echo.
echo Step 3: Running tests...
call .\mvnw.cmd test
if %ERRORLEVEL% NEQ 0 (
    echo WARNING: Some tests failed, but continuing...
)

echo.
echo Step 4: Creating Windows installer (this may take several minutes)...
call .\mvnw.cmd jlink:jlink jpackage:jpackage
if %ERRORLEVEL% NEQ 0 (
    echo ERROR: Installer creation failed!
    echo Make sure you have JDK 17+ with jpackage tool installed.
    pause
    exit /b 1
)

echo.
echo ========================================
echo Build Complete!
echo ========================================
echo.
echo Installer location:
echo   target\jpackage\TheEndTheBeginning-Setup.exe
echo.
echo You can now distribute this installer to users.
echo It includes the JRE and all dependencies.
echo.
pause

