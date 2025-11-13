# Quick Start Guide - Build & Package

## ✅ Music Files Status

Music files have been organized and are ready:
- ✓ `main_theme.mp3` (Ghostpocalypse)
- ✓ `dungeon_ambient.mp3` (Darkest Child)
- ✓ `combat_theme.mp3` (Ritual)
- ✓ `boss_theme.mp3` (Unholy Knight)
- ✓ `victory_theme.mp3` (Local Forecast - Elevator)
- ✓ `game_over.mp3` (Dark Walk)

All files are in: `src\main\resources\audio\music\`

## 🚀 Part 2: Build and Test

### Option A: Use the Automated Script
```batch
build-and-package.bat
```

### Option B: Manual Commands
```batch
cd TheEndTheBeginning

REM Clean and compile
mvn clean compile

REM Run tests
mvn test

REM Test the game
mvn javafx:run
```

## 📦 Part 3: Create Windows Installer

### Quick Method
```batch
mvn jlink jpackage
```

### What This Does:
1. Creates a custom JRE image with only needed modules
2. Packages everything into a Windows installer (.exe)
3. Includes all dependencies and resources
4. Creates Start Menu shortcut

### Output Location:
```
target\jpackage\TheEndTheBeginning-Setup.exe
```

## ⚠️ Important Notes

1. **Maven Required**: Make sure Maven is installed and in your PATH
   - Check: `mvn -version`
   - Download: https://maven.apache.org/download.cgi

2. **JDK 17+ Required**: jpackage needs JDK 17 or higher
   - Check: `java -version`
   - Should show version 17 or higher

3. **Windows SDK**: Required for creating installers
   - Usually comes with Visual Studio Build Tools

4. **Build Time**: Creating the installer takes 5-10 minutes
   - First time may take longer (downloading dependencies)

## 🎮 Testing Checklist

After building, test these features:

- [ ] Game launches successfully
- [ ] Music plays in menu (main_theme.mp3)
- [ ] Theme toggle works (toolbar button)
- [ ] Help button shows keyboard shortcuts
- [ ] Start new game works
- [ ] Combat music switches (combat_theme.mp3)
- [ ] Save/Load shows notifications
- [ ] Achievement notifications appear
- [ ] Status bar updates correctly
- [ ] Icons display on buttons

## 📋 Installation Testing

After creating the installer:

1. **Test Installation**:
   - Run `TheEndTheBeginning-Setup.exe` on a clean system
   - Verify it installs without errors
   - Check Start Menu shortcut works

2. **Test Execution**:
   - Launch from Start Menu
   - Verify game runs without Java installed separately
   - Test all game features

3. **Distribution**:
   - The installer is self-contained
   - Users don't need Java installed
   - All dependencies are bundled

## 🐛 Troubleshooting

### "mvn not recognized"
- Add Maven to PATH or use full path
- Example: `C:\Program Files\Apache\maven\bin\mvn clean compile`

### "jpackage not found"
- Ensure JDK 17+ is installed (not just JRE)
- jpackage comes with JDK 17+

### Build fails
- Check Java version: `java -version`
- Check Maven version: `mvn -version`
- Ensure all dependencies downloaded: `mvn dependency:resolve`

### Music doesn't play
- Verify files are in `src\main\resources\audio\music\`
- Check file names match exactly (case-sensitive)
- Files should be MP3 format

## ✨ Features Included

- ✅ Modern GUI with toolbar and status bar
- ✅ Ikonli icons on all buttons
- ✅ ControlsFX notifications
- ✅ Background music system
- ✅ Theme toggle (normal/high-contrast)
- ✅ Keyboard shortcuts (Ctrl+S, Ctrl+L, F1)
- ✅ Self-contained Windows installer
- ✅ All dependencies bundled

---

**Ready to build!** Run `build-and-package.bat` or follow the manual steps above.

