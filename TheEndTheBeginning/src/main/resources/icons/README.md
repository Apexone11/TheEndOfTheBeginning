# Icon Directory

## Required Icon File

The application is configured to load an icon from:
- **File:** `game-icon.png`
- **Location:** This directory
- **Recommended Size:** 256x256 pixels or larger
- **Format:** PNG with transparency

## Icon Not Included

The icon image file is **not yet created**. The application will run with the default Java icon until this file is added.

## Creating the Icon

The icon should represent the game's dungeon/fantasy theme. Consider:
- A sword and shield
- A dungeon entrance
- A medieval door with runes
- A treasure chest
- A stylized "T" logo

## Icon Formats Needed

For full platform support, create these formats:

### Windows
- `game-icon.ico` - Multi-size icon (16x16, 32x32, 48x48, 256x256)

### macOS
- `game-icon.icns` - Apple icon format

### Linux
- `game-icon.png` - Standard PNG (256x256 or larger)

## Tools for Icon Creation

Free tools you can use:
- **GIMP** - Free image editor
- **Inkscape** - Vector graphics editor
- **IcoFX** - Icon editor (Windows)
- **Online converters** - Convert PNG to ICO/ICNS

## Temporary Solution

Until a proper icon is created, the application will:
1. Attempt to load the icon
2. Catch the exception if file is missing
3. Continue running with default Java icon
4. Print a message to console: "Could not load icon"

This does not affect functionality, only visual appearance.

---

**Status:** Icon file needed - framework in place  
**Priority:** Low (cosmetic only)
