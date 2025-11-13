# Music & Sound Attribution

This document lists all audio resources used in "The End The Beginning - Dungeon Escape" and their licensing information.

## 🎵 Background Music

All background music tracks are sourced from copyright-free, royalty-free libraries with appropriate Creative Commons licenses.

### Music Tracks Used

#### Main Theme (`main_theme.mp3`)
- **Track**: "Ghostpocalypse" by Kevin MacLeod
- **Source**: https://incompetech.com/music/royalty-free/music.html
- **License**: Creative Commons Attribution 4.0 International
- **Attribution Required**: "Ghostpocalypse" by Kevin MacLeod (incompetech.com), Licensed under CC BY 4.0
- **Direct Link**: https://incompetech.com/music/royalty-free/index.html?isrc=USUAN1100347
- **Usage**: Main menu and title screen background music

#### Dungeon Ambient (`dungeon_ambient.mp3`)
- **Track**: "Darkest Child" by Kevin MacLeod
- **Source**: https://incompetech.com/music/royalty-free/music.html
- **License**: Creative Commons Attribution 4.0 International
- **Attribution Required**: "Darkest Child" by Kevin MacLeod (incompetech.com), Licensed under CC BY 4.0
- **Direct Link**: https://incompetech.com/music/royalty-free/index.html?isrc=USUAN1100485
- **Usage**: Background music during dungeon exploration

#### Combat Theme (`combat_theme.mp3`)
- **Track**: "Ritual" by Kevin MacLeod
- **Source**: https://incompetech.com/music/royalty-free/music.html
- **License**: Creative Commons Attribution 4.0 International
- **Attribution Required**: "Ritual" by Kevin MacLeod (incompetech.com), Licensed under CC BY 4.0
- **Direct Link**: https://incompetech.com/music/royalty-free/index.html?isrc=USUAN1100484
- **Usage**: Background music during combat encounters

#### Boss Theme (`boss_theme.mp3`)
- **Track**: "Unholy Knight" by Kevin MacLeod
- **Source**: https://incompetech.com/music/royalty-free/music.html
- **License**: Creative Commons Attribution 4.0 International
- **Attribution Required**: "Unholy Knight" by Kevin MacLeod (incompetech.com), Licensed under CC BY 4.0
- **Direct Link**: https://incompetech.com/music/royalty-free/index.html?isrc=USUAN1100482
- **Usage**: Background music during boss battles

#### Victory Theme (`victory_theme.mp3`)
- **Track**: "Local Forecast - Elevator" by Kevin MacLeod
- **Source**: https://incompetech.com/music/royalty-free/music.html
- **License**: Creative Commons Attribution 4.0 International
- **Attribution Required**: "Local Forecast - Elevator" by Kevin MacLeod (incompetech.com), Licensed under CC BY 4.0
- **Direct Link**: https://incompetech.com/music/royalty-free/index.html?isrc=USUAN1300012
- **Usage**: Victory fanfare after completing levels or defeating bosses

#### Game Over (`game_over.mp3`)
- **Track**: "Dark Walk" by Kevin MacLeod
- **Source**: https://incompetech.com/music/royalty-free/music.html
- **License**: Creative Commons Attribution 4.0 International
- **Attribution Required**: "Dark Walk" by Kevin MacLeod (incompetech.com), Licensed under CC BY 4.0
- **Direct Link**: https://incompetech.com/music/royalty-free/index.html?isrc=USUAN1100468
- **Usage**: Background music during game over screen

### How to Download Music Tracks

1. Visit https://incompetech.com/music/royalty-free/
2. Search for each track by name using the search box
3. Download the MP3 version of each track
4. Rename files to match the expected filenames:
   - `Ghostpocalypse.mp3` → `main_theme.mp3`
   - `Darkest Child.mp3` → `dungeon_ambient.mp3`
   - `Ritual.mp3` → `combat_theme.mp3`
   - `Unholy Knight.mp3` → `boss_theme.mp3`
   - `Local Forecast - Elevator.mp3` → `victory_theme.mp3`
   - `Dark Walk.mp3` → `game_over.mp3`
5. Place all files in: `TheEndTheBeginning/src/main/resources/audio/music/`

## 🔊 Sound Effects

Sound effects are currently using placeholder paths. Recommended sources:

1. **Freesound.org** (https://freesound.org/)
   - License: Various (CC0, CC-BY, CC-BY-SA)
   - Excellent for combat sounds, UI clicks, and ambient effects

2. **ZapSplat** (https://www.zapsplat.com/)
   - License: Free for games with attribution
   - Great for UI sounds and environmental effects

3. **Mixkit** (https://mixkit.co/free-sound-effects/)
   - License: Mixkit Free License
   - High-quality game sound effects

### Sound Effect Directories

- Combat: `src/main/resources/audio/combat/`
- UI: `src/main/resources/audio/ui/`
- Environment: `src/main/resources/audio/environment/`

## 🙏 Required Attribution

When distributing this game, include the following attribution in your game credits or documentation:

```
Music Credits:
- "Ghostpocalypse" by Kevin MacLeod (incompetech.com)
  Licensed under CC BY 4.0
- "Darkest Child" by Kevin MacLeod (incompetech.com)
  Licensed under CC BY 4.0
- "Ritual" by Kevin MacLeod (incompetech.com)
  Licensed under CC BY 4.0
- "Unholy Knight" by Kevin MacLeod (incompetech.com)
  Licensed under CC BY 4.0
- "Local Forecast - Elevator" by Kevin MacLeod (incompetech.com)
  Licensed under CC BY 4.0
- "Dark Walk" by Kevin MacLeod (incompetech.com)
  Licensed under CC BY 4.0

All music tracks are available at: https://incompetech.com/
```

## ⚠️ Important Notes

- **All music tracks require attribution** per Creative Commons Attribution 4.0 license
- **Kevin MacLeod's music is free** for commercial and non-commercial use with attribution
- **Sound effects are optional** - the game works without them
- **Music files must be downloaded** and placed in the correct directory
- The AudioManager will gracefully handle missing audio files (logs to console)

## 🔧 Technical Implementation

The game uses JavaFX Media API for audio playback:
- Music tracks are loaded from `resources/audio/music/`
- Sound effects are loaded from `resources/audio/combat/`, `ui/`, and `environment/`
- AudioManager handles all audio playback with volume control and looping
- Missing audio files are handled gracefully (no crashes)

---

**Last Updated**: December 2024  
**Game Version**: 4.0.0  
**Audio System**: JavaFX Media API with MediaPlayer
