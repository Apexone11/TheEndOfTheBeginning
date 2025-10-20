# Music & Sound Attribution

This document lists all audio resources used in "The End The Beginning - Dungeon Escape" and their licensing information.

## 🎵 Background Music

All background music tracks are sourced from copyright-free, royalty-free libraries with appropriate Creative Commons licenses.

### Recommended Copyright-Free Music Sources

The following sources provide high-quality, copyright-free music suitable for this dungeon RPG:

1. **FreePD (Public Domain Music)**
   - Website: https://freepd.com/
   - License: Public Domain (CC0)
   - Recommended Tracks:
     - "Dark Descent" - Perfect for dungeon ambient music
     - "Medieval" - Great for main theme
     - "Battle Theme" - Ideal for combat sequences

2. **Incompetech (Kevin MacLeod)**
   - Website: https://incompetech.com/
   - License: Creative Commons Attribution 4.0
   - Recommended Tracks:
     - "Unholy Knight" - Boss battle theme
     - "Shadowlands" - Dungeon exploration
     - "Cipher" - Mystery and tension

3. **MusMus (Japanese Free Music)**
   - Website: https://musmus.main.jp/
   - License: Free for games (attribution required)
   - Recommended: Fantasy and dungeon-themed tracks

4. **OpenGameArt.org**
   - Website: https://opengameart.org/
   - License: Various (CC0, CC-BY, CC-BY-SA)
   - Search for: "dungeon music", "RPG battle", "fantasy ambient"

### Current Music Tracks (Placeholders)

The following tracks should be downloaded and placed in the respective directories:

#### Main Theme
- **File**: `main_theme.mp3`
- **Location**: `src/main/resources/audio/music/`
- **Recommended**: "Medieval" by Kevin MacLeod or similar fantasy theme
- **License**: Creative Commons Attribution 4.0
- **Attribution**: "Medieval" by Kevin MacLeod (incompetech.com), Licensed under CC BY 4.0

#### Dungeon Ambient
- **File**: `dungeon_ambient.mp3`
- **Location**: `src/main/resources/audio/music/`
- **Recommended**: "Dark Descent" from FreePD or "Shadowlands" by Kevin MacLeod
- **License**: Public Domain / CC BY 4.0
- **Attribution**: Required based on chosen track

#### Combat Theme
- **File**: `combat_theme.mp3`
- **Location**: `src/main/resources/audio/music/`
- **Recommended**: "Battle Theme" from FreePD or "Unholy Knight" by Kevin MacLeod
- **License**: Public Domain / CC BY 4.0
- **Attribution**: Required based on chosen track

#### Boss Theme
- **File**: `boss_theme.mp3`
- **Location**: `src/main/resources/audio/music/`
- **Recommended**: "Unholy Knight" by Kevin MacLeod
- **License**: Creative Commons Attribution 4.0
- **Attribution**: "Unholy Knight" by Kevin MacLeod (incompetech.com), Licensed under CC BY 4.0

#### Victory Theme
- **File**: `victory_theme.mp3`
- **Location**: `src/main/resources/audio/music/`
- **Recommended**: Triumphant fanfare from FreePD
- **License**: Public Domain
- **Attribution**: Not required for public domain

#### Game Over
- **File**: `game_over.mp3`
- **Location**: `src/main/resources/audio/music/`
- **Recommended**: Somber defeat music from FreePD
- **License**: Public Domain
- **Attribution**: Not required for public domain

## 🔊 Sound Effects

### Recommended Sound Effect Sources

1. **Freesound.org**
   - Website: https://freesound.org/
   - License: Various (CC0, CC-BY, CC-BY-SA)
   - Excellent for combat sounds, UI clicks, and ambient effects

2. **ZapSplat**
   - Website: https://www.zapsplat.com/
   - License: Free for games with attribution
   - Great for UI sounds and environmental effects

3. **Mixkit**
   - Website: https://mixkit.co/free-sound-effects/
   - License: Mixkit Free License
   - High-quality game sound effects

### Current Sound Effects (Placeholders)

#### Combat Sounds
- `sword_hit.wav` - Sword impact sound
- `sword_miss.wav` - Whoosh sound for missed attacks
- `critical_hit.wav` - Powerful critical hit impact
- `block.wav` - Shield block sound
- `dodge.wav` - Quick whoosh for dodge
- `spell_cast.wav` - Magic casting sound
- `fireball.wav` - Fire spell impact
- `heal_spell.wav` - Healing magic sound
- `monster_death.wav` - Monster defeat sound
- `player_hurt.wav` - Player damage sound

#### UI Sounds
- `button_click.wav` - Button press sound
- `menu_select.wav` - Menu selection
- `menu_back.wav` - Menu back/cancel
- `item_pickup.wav` - Item collected sound
- `item_use.wav` - Item usage sound
- `level_up.wav` - Level up fanfare
- `achievement.wav` - Achievement unlock sound
- `error.wav` - Error/invalid input sound

#### Environment Sounds
- `door_open.wav` - Door opening creak
- `footsteps.wav` - Walking footsteps
- `treasure_open.wav` - Chest opening sound
- `ambient_dungeon.wav` - Dungeon ambiance loop

## 📝 How to Add Audio Files

1. Download copyright-free audio from the recommended sources above
2. Place music files (MP3 format) in: `src/main/resources/audio/music/`
3. Place sound effects (WAV format) in their respective directories:
   - Combat: `src/main/resources/audio/combat/`
   - UI: `src/main/resources/audio/ui/`
   - Environment: `src/main/resources/audio/environment/`
4. Update the attribution section below with actual file details

## 🙏 Attribution Required

When distributing this game with audio files, include the following attribution:

```
Music Credits:
- [Track Name] by [Artist Name]
  Source: [Website]
  License: [License Type]
  
Sound Effects Credits:
- Sound effects from Freesound.org (various artists)
  Licensed under Creative Commons
```

## ⚠️ Important Notes

- **Always check license requirements** before distributing
- **Provide attribution** when required by the license
- **Keep this file updated** when adding new audio
- The AudioManager framework is in place and ready for audio integration
- Audio files are optional - the game works without them (framework mode)

## 🔧 Technical Implementation

The game uses a framework-based audio system (`AudioManager.java`) that:
- Registers audio file paths during initialization
- Provides methods for playing sounds and music
- Currently operates in "framework mode" (logs to console)
- Can be easily extended with JavaFX Media API for actual playback

To enable actual audio playback:
1. Add audio files to the resource directories
2. Implement JavaFX MediaPlayer in AudioManager
3. The existing structure will handle all audio triggers automatically

---

**Last Updated**: October 2025  
**Game Version**: 4.0.0
