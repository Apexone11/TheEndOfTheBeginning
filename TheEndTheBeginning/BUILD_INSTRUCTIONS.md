# Build Instructions for The End The Beginning

## Prerequisites

1. **Java Development Kit (JDK) 17 or higher**
   - Download from: https://adoptium.net/ or https://www.oracle.com/java/technologies/downloads/
   - Verify installation: `java -version` and `javac -version`

2. **Apache Maven 3.6+**
   - Download from: https://maven.apache.org/download.cgi
   - Add Maven to your PATH
   - Verify installation: `mvn -version`

3. **Windows SDK (for jpackage)**
   - Usually included with Visual Studio Build Tools
   - Required for creating Windows installers

## Quick Build (Automated)

Run the provided batch script:
```batch
build-and-package.bat
```

This will:
1. Clean previous builds
2. Compile the project
3. Run tests
4. Create Windows installer (.exe)

## Manual Build Steps

### Step 1: Clean and Compile
```batch
cd TheEndTheBeginning
mvn clean compile
```

### Step 2: Run Tests
```batch
mvn test
```

### Step 3: Create Windows Installer
```batch
mvn jlink jpackage
```

The installer will be created at:
```
target\jpackage\TheEndTheBeginning-Setup.exe
```

## Running the Game

### Development Mode
```batch
mvn javafx:run
```

### Using the Installer
1. Run `TheEndTheBeginning-Setup.exe`
2. Follow the installation wizard
3. Launch from Start Menu or desktop shortcut

## Troubleshooting

### Maven Not Found
- Add Maven bin directory to your PATH
- Or use full path: `C:\Program Files\Apache\maven\bin\mvn`

### jpackage Not Found
- Ensure you're using JDK 17+ (not just JRE)
- jpackage is included with JDK 17+

### Build Fails with "jpackage not available"
- Install Windows SDK
- Or use JDK that includes jpackage support

### Music Files Missing
- Music files should be in: `src\main\resources\audio\music\`
- Files should be named: `main_theme.mp3`, `dungeon_ambient.mp3`, etc.
- See `MUSIC_ATTRIBUTION.md` for details

## File Structure After Build

```
target/
├── classes/          # Compiled classes
├── image/            # Custom JRE image
└── jpackage/
    └── TheEndTheBeginning-Setup.exe  # Windows installer
```

## Distribution

The installer (`TheEndTheBeginning-Setup.exe`) is self-contained and includes:
- Custom JRE (Java Runtime Environment)
- JavaFX runtime
- All game resources (including music)
- Application launcher

Users do NOT need to install Java separately!

