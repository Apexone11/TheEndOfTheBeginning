package gameproject.audio;

import java.util.HashMap;
import java.util.Map;

/**
 * Advanced Audio System for "The End The Beginning" v4.0.0
 * Handles all sound effects, background music, and audio feedback
 * 
 * Features:
 * - Sound effect management with caching
 * - Background music system with smooth transitions  
 * - Volume controls and audio settings
 * - Dynamic audio based on game events
 * - Audio feedback framework for UI and combat
 * 
 * Note: This is a framework implementation. For full audio functionality,
 * JavaFX Media API or other audio libraries would need to be integrated.
 * Audio files should be placed in resources/audio/ directory.
 * 
 * @author Abdul Fornah
 * @version 4.0.0 - Advanced Audio System Framework
 */
public class AudioManager {
    
    private static AudioManager instance;
    private boolean soundEnabled;
    private boolean musicEnabled;
    private double soundVolume;
    private double musicVolume;
    
    // Audio registries (paths to audio files)
    private final Map<String, String> soundRegistry;
    private final Map<String, String> musicRegistry;
    
    // Current music track
    private String currentTrack;
    private boolean isPlayingMusic;
    
    // Sound effect categories
    public enum SoundCategory {
        COMBAT,
        UI,
        ENVIRONMENT,
        MAGIC,
        ACHIEVEMENT
    }
    
    private AudioManager() {
        this.soundEnabled = true;
        this.musicEnabled = true;
        this.soundVolume = 0.7;
        this.musicVolume = 0.5;
        this.soundRegistry = new HashMap<>();
        this.musicRegistry = new HashMap<>();
        this.isPlayingMusic = false;
        
        loadAudioResources();
    }
    
    public static AudioManager getInstance() {
        if (instance == null) {
            instance = new AudioManager();
        }
        return instance;
    }
    
    /**
     * Initialize and register audio resource paths
     */
    private void loadAudioResources() {
        // Combat sounds
        registerSound("sword_hit", "/audio/combat/sword_hit.wav");
        registerSound("sword_miss", "/audio/combat/sword_miss.wav");
        registerSound("critical_hit", "/audio/combat/critical_hit.wav");
        registerSound("block", "/audio/combat/block.wav");
        registerSound("dodge", "/audio/combat/dodge.wav");
        registerSound("spell_cast", "/audio/combat/spell_cast.wav");
        registerSound("fireball", "/audio/combat/fireball.wav");
        registerSound("heal_spell", "/audio/combat/heal.wav");
        registerSound("monster_death", "/audio/combat/monster_death.wav");
        registerSound("player_hurt", "/audio/combat/player_hurt.wav");
        
        // UI sounds
        registerSound("button_click", "/audio/ui/button_click.wav");
        registerSound("menu_select", "/audio/ui/menu_select.wav");
        registerSound("menu_back", "/audio/ui/menu_back.wav");
        registerSound("item_pickup", "/audio/ui/item_pickup.wav");
        registerSound("item_use", "/audio/ui/item_use.wav");
        registerSound("level_up", "/audio/ui/level_up.wav");
        registerSound("achievement", "/audio/ui/achievement.wav");
        registerSound("error", "/audio/ui/error.wav");
        
        // Environment sounds
        registerSound("door_open", "/audio/environment/door_open.wav");
        registerSound("footsteps", "/audio/environment/footsteps.wav");
        registerSound("treasure_open", "/audio/environment/treasure_open.wav");
        registerSound("ambient_dungeon", "/audio/environment/ambient_dungeon.wav");
        
        // Background music
        registerMusic("main_theme", "/audio/music/main_theme.mp3");
        registerMusic("dungeon_ambient", "/audio/music/dungeon_ambient.mp3");
        registerMusic("combat_theme", "/audio/music/combat_theme.mp3");
        registerMusic("boss_theme", "/audio/music/boss_theme.mp3");
        registerMusic("victory_theme", "/audio/music/victory_theme.mp3");
        registerMusic("game_over", "/audio/music/game_over.mp3");
    }
    
    private void registerSound(String name, String path) {
        soundRegistry.put(name, path);
        System.out.println("Registered sound: " + name + " -> " + path);
    }
    
    private void registerMusic(String name, String path) {
        musicRegistry.put(name, path);
        System.out.println("Registered music: " + name + " -> " + path);
    }
    
    /**
     * Play a sound effect (framework implementation)
     */
    public void playSound(String soundName) {
        playSound(soundName, soundVolume);
    }
    
    public void playSound(String soundName, double volume) {
        if (!soundEnabled) return;
        
        String soundPath = soundRegistry.get(soundName);
        if (soundPath != null) {
            // Framework implementation - would integrate with audio library
            System.out.println("[AUDIO] Playing sound: " + soundName + 
                             " (volume: " + String.format("%.2f", volume) + ") -> " + soundPath);
        } else {
            System.out.println("[AUDIO] Sound not found: " + soundName);
        }
    }
    
    /**
     * Play background music (framework implementation)
     */
    public void playMusic(String musicName) {
        playMusic(musicName, true);
    }
    
    public void playMusic(String musicName, boolean loop) {
        if (!musicEnabled) return;
        
        // Don't restart the same track
        if (musicName.equals(currentTrack) && isPlayingMusic) {
            return;
        }
        
        String musicPath = musicRegistry.get(musicName);
        if (musicPath == null) {
            System.out.println("[AUDIO] Music not found: " + musicName);
            return;
        }
        
        // Stop current music
        if (isPlayingMusic) {
            stopMusic();
        }
        
        // Start new music
        currentTrack = musicName;
        isPlayingMusic = true;
        
        System.out.println("[AUDIO] Playing music: " + musicName + 
                         " (loop: " + loop + ", volume: " + String.format("%.2f", musicVolume) + 
                         ") -> " + musicPath);
    }
    
    /**
     * Stop current music
     */
    public void stopMusic() {
        if (isPlayingMusic) {
            System.out.println("[AUDIO] Stopping music: " + currentTrack);
            isPlayingMusic = false;
            currentTrack = null;
        }
    }
    
    // Combat-specific audio methods
    public void playCombatSound(String attackType, boolean hit, boolean critical) {
        if (critical) {
            playSound("critical_hit", soundVolume * 1.2);
        } else if (hit) {
            switch (attackType.toLowerCase()) {
                case "sword":
                case "weapon":
                    playSound("sword_hit");
                    break;
                case "magic":
                case "spell":
                    playSound("spell_cast");
                    break;
                default:
                    playSound("sword_hit");
                    break;
            }
        } else {
            playSound("sword_miss");
        }
    }
    
    public void playSpellSound(String spellName) {
        switch (spellName.toLowerCase()) {
            case "fireball":
            case "flame breath":
                playSound("fireball");
                break;
            case "heal":
            case "divine heal":
                playSound("heal_spell");
                break;
            default:
                playSound("spell_cast");
                break;
        }
    }
    
    public void playUISound(String action) {
        switch (action.toLowerCase()) {
            case "click":
            case "select":
                playSound("button_click");
                break;
            case "back":
                playSound("menu_back");
                break;
            case "error":
                playSound("error");
                break;
            case "level_up":
                playSound("level_up", soundVolume * 1.3);
                break;
            case "achievement":
                playSound("achievement", soundVolume * 1.5);
                break;
            case "item_pickup":
                playSound("item_pickup");
                break;
            case "item_use":
                playSound("item_use");
                break;
        }
    }
    
    public void playEnvironmentSound(String event) {
        switch (event.toLowerCase()) {
            case "door":
                playSound("door_open");
                break;
            case "treasure":
                playSound("treasure_open");
                break;
            case "footsteps":
                playSound("footsteps", soundVolume * 0.6);
                break;
        }
    }
    
    // Dynamic music system
    public void setGameStateMusic(String gameState) {
        switch (gameState.toLowerCase()) {
            case "menu":
                playMusic("main_theme");
                break;
            case "dungeon":
            case "exploration":
                playMusic("dungeon_ambient");
                break;
            case "combat":
                playMusic("combat_theme");
                break;
            case "boss":
                playMusic("boss_theme");
                break;
            case "victory":
                playMusic("victory_theme", false);
                break;
            case "game_over":
                playMusic("game_over", false);
                break;
            default:
                playMusic("dungeon_ambient");
                break;
        }
    }
    
    // Settings
    public void setSoundEnabled(boolean enabled) {
        this.soundEnabled = enabled;
        if (!enabled && isPlayingMusic) {
            stopMusic();
        }
        System.out.println("[AUDIO] Sound " + (enabled ? "enabled" : "disabled"));
    }
    
    public void setMusicEnabled(boolean enabled) {
        this.musicEnabled = enabled;
        if (!enabled && isPlayingMusic) {
            stopMusic();
        }
        System.out.println("[AUDIO] Music " + (enabled ? "enabled" : "disabled"));
    }
    
    public void setSoundVolume(double volume) {
        this.soundVolume = Math.max(0.0, Math.min(1.0, volume));
        System.out.println("[AUDIO] Sound volume set to: " + String.format("%.2f", this.soundVolume));
    }
    
    public void setMusicVolume(double volume) {
        this.musicVolume = Math.max(0.0, Math.min(1.0, volume));
        System.out.println("[AUDIO] Music volume set to: " + String.format("%.2f", this.musicVolume));
        
        // Apply volume change to current music if playing
        if (isPlayingMusic && currentTrack != null) {
            System.out.println("[AUDIO] Applying volume change to current track: " + currentTrack);
        }
    }
    
    // Getters
    public boolean isSoundEnabled() { return soundEnabled; }
    public boolean isMusicEnabled() { return musicEnabled; }
    public double getSoundVolume() { return soundVolume; }
    public double getMusicVolume() { return musicVolume; }
    public String getCurrentTrack() { return currentTrack; }
    public boolean isPlayingMusic() { return isPlayingMusic; }
    
    /**
     * Cleanup resources
     */
    public void dispose() {
        if (isPlayingMusic) {
            stopMusic();
        }
        
        soundRegistry.clear();
        musicRegistry.clear();
        System.out.println("[AUDIO] AudioManager disposed");
    }
}