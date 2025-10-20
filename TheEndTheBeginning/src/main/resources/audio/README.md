# Audio Resources Directory

This directory contains all audio files for "The End The Beginning - Dungeon Escape".

## Directory Structure

```
audio/
├── music/          # Background music tracks (MP3 format)
├── combat/         # Combat sound effects (WAV format)
├── ui/             # User interface sounds (WAV format)
└── environment/    # Environmental sounds (WAV format)
```

## Adding Audio Files

**Note**: Audio files are not included in the repository to keep the package size small and avoid licensing issues.

To add audio to the game:

1. Download copyright-free audio from sources listed in `MUSIC_ATTRIBUTION.md`
2. Place files in the appropriate directories:
   - Music files (MP3): `music/`
   - Combat sounds (WAV): `combat/`
   - UI sounds (WAV): `ui/`
   - Environment sounds (WAV): `environment/`

3. Ensure file names match those registered in `AudioManager.java`:

### Music Files Required:
- `main_theme.mp3` - Main menu theme
- `dungeon_ambient.mp3` - Dungeon exploration
- `combat_theme.mp3` - Combat encounters
- `boss_theme.mp3` - Boss battles
- `victory_theme.mp3` - Victory screen
- `game_over.mp3` - Game over screen

### Sound Effect Files:
See `MUSIC_ATTRIBUTION.md` for the complete list of required sound effect files.

## Copyright-Free Sources

Recommended sources for free, royalty-free audio:
- **FreePD**: https://freepd.com/ (Public Domain)
- **Incompetech**: https://incompetech.com/ (CC BY 4.0)
- **Freesound**: https://freesound.org/ (Various CC licenses)
- **OpenGameArt**: https://opengameart.org/ (Various licenses)

## File Format Guidelines

- **Music**: MP3 format, 128-192 kbps, stereo
- **Sound Effects**: WAV format, 16-bit, 44.1 kHz, mono or stereo

## Audio Framework

The game includes a complete audio framework (`AudioManager.java`) that:
- ✅ Registers all audio file paths
- ✅ Provides methods for playing sounds and music
- ✅ Manages volume and audio settings
- ⏳ Currently in "framework mode" (logs only)

**Framework Mode**: Without audio files, the game will log audio events to the console instead of playing sounds. The game is fully playable without audio.

## Integration Status

- [x] Audio framework implemented
- [x] Audio triggers added throughout game
- [x] Volume controls in settings
- [x] Music state management
- [ ] Actual audio files (to be added by users)
- [ ] JavaFX MediaPlayer integration (optional enhancement)

## License Compliance

When adding audio files:
1. **Check the license** of each file
2. **Keep attribution** in `MUSIC_ATTRIBUTION.md`
3. **Follow license requirements** for distribution
4. **Use only copyright-free or properly licensed audio**

---

For detailed attribution requirements and recommended tracks, see `MUSIC_ATTRIBUTION.md` in the project root.
