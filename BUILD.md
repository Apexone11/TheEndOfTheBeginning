# Build Instructions

This document describes how to build and package "The End The Beginning" game.

## Prerequisites

- **Java Development Kit (JDK)** 17 or higher
- **Apache Maven** 3.6 or higher
- **Git** (for cloning the repository)

## Building from Source

### 1. Clone the Repository

```bash
git clone https://github.com/Apexone11/TheEndOfTheBeginning.git
cd TheEndOfTheBeginning/TheEndTheBeginning
```

### 2. Compile the Project

```bash
mvn clean compile
```

This will:
- Clean any previous build artifacts
- Compile all Java source files
- Copy resources to the target directory

### 3. Run the Game

```bash
mvn javafx:run
```

This launches the game directly using the JavaFX Maven plugin.

### 4. Package as JAR

```bash
mvn clean package
```

This creates an executable JAR file in the `target/` directory:
- `target/theendthebeginning-3.0.0.jar`

**Note:** The JAR requires JavaFX to be installed on the system to run.

## Creating a Standalone Executable

### Windows (EXE)

To create a Windows executable, you can use tools like:

1. **jpackage** (included with JDK 14+):
   ```bash
   jpackage --input target/ \
            --name "TheEndTheBeginning" \
            --main-jar theendthebeginning-3.0.0.jar \
            --main-class gameproject.TheEndTheBeginning \
            --type exe \
            --icon src/main/resources/icons/game-icon.ico \
            --win-menu \
            --win-shortcut
   ```

2. **Launch4j** (Alternative):
   - Download Launch4j from http://launch4j.sourceforge.net/
   - Create a wrapper EXE that launches the JAR
   - Include JRE if needed for standalone distribution

### macOS (DMG/APP)

```bash
jpackage --input target/ \
         --name "TheEndTheBeginning" \
         --main-jar theendthebeginning-3.0.0.jar \
         --main-class gameproject.TheEndTheBeginning \
         --type dmg \
         --icon src/main/resources/icons/game-icon.icns
```

### Linux (DEB/RPM)

```bash
jpackage --input target/ \
         --name "theendthebeginning" \
         --main-jar theendthebeginning-3.0.0.jar \
         --main-class gameproject.TheEndTheBeginning \
         --type deb  # or rpm for Red Hat-based systems
```

## Development Build

For development with hot-reload capabilities:

```bash
mvn javafx:run -Djavafx.args="--add-opens javafx.base/com.sun.javafx.runtime=ALL-UNNAMED"
```

## Testing

Currently, there are no automated tests. Manual testing checklist:

- [ ] Game launches without errors
- [ ] Character class selection works
- [ ] Combat system functions correctly
- [ ] Inventory system works
- [ ] Save/load functionality works
- [ ] All 50 levels accessible
- [ ] UI displays correctly at different window sizes

## Troubleshooting

### "JavaFX runtime components are missing"

**Solution:** Ensure JavaFX is properly installed. The Maven build should handle this automatically, but if running the JAR directly, you may need to add JavaFX to the module path:

```bash
java --module-path /path/to/javafx-sdk/lib --add-modules javafx.controls,javafx.fxml -jar target/theendthebeginning-3.0.0.jar
```

### Build fails with "package javafx.* does not exist"

**Solution:** Verify Maven is properly configured and can download dependencies. Try:

```bash
mvn dependency:purge-local-repository
mvn clean install
```

### Window icon doesn't appear

**Solution:** The icon file needs to be created at `src/main/resources/icons/game-icon.png`. The framework is in place but the actual image file is not yet included.

## Project Structure

```
TheEndTheBeginning/
├── src/
│   └── main/
│       ├── java/              # Java source files
│       │   ├── gameproject/   # Main game classes
│       │   └── main/model/    # Data models
│       └── resources/         # Resources (FXML, CSS, images)
│           ├── game.fxml      # UI layout
│           ├── game-style.css # Styling
│           └── icons/         # Icon directory (create icon here)
├── target/                    # Build output (generated)
├── pom.xml                    # Maven configuration
├── CHANGELOG.md               # Version history
├── BUGS.md                    # Known issues
└── README.md                  # Project documentation
```

## Version Information

- **Current Version:** 3.0.0
- **Java Version:** 17
- **JavaFX Version:** 20
- **Build Tool:** Maven 3.6+

## Release Checklist

When preparing a release:

1. [ ] Update version in `pom.xml`
2. [ ] Update `CHANGELOG.md` with new changes
3. [ ] Update `README.md` if needed
4. [ ] Run `mvn clean package` to build JAR
5. [ ] Test the JAR file thoroughly
6. [ ] Create platform-specific executables (optional)
7. [ ] Tag the release in Git
8. [ ] Create GitHub release with artifacts

## Support

For build issues or questions:
- Check the [BUGS.md](BUGS.md) for known issues
- Review [README.md](README.md) for usage instructions
- Open an issue on GitHub if you encounter problems

---

**Last Updated:** September 30, 2024
