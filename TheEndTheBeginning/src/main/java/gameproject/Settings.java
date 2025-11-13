package gameproject;

import java.io.*;
import java.nio.file.*;

/**
 * Settings management for the game.
 * Persists configuration to user's home directory.
 * 
 * @version 3.1.0
 */
public final class Settings {
    
    private static final String CONFIG_DIR = System.getProperty("user.home") + "/.the-end-the-beginning";
    private static final String CONFIG_FILE = CONFIG_DIR + "/config.properties";
    
    // Settings fields
    public boolean highContrast = false;
    public int textSpeedMs = 0; // 0 = instant
    public boolean confirmations = true;
    public boolean sfxEnabled = false; // Default off
    
    // v5.0.0 - New UI and Audio settings
    public double uiScale = 1.0;
    public boolean reducedMotion = false;
    public boolean colorBlindMode = false;
    public double masterVolume = 1.0;
    public double musicVolume = 0.5;
    public double sfxVolume = 0.7;
    public boolean autoSaveEnabled = true;
    
    /**
     * Loads settings from disk.
     * 
     * @return Settings object with loaded values, or defaults if load fails
     */
    public static Settings load() {
        Settings settings = new Settings();
        
        try {
            Path configPath = Paths.get(CONFIG_FILE);
            if (Files.exists(configPath)) {
                try (BufferedReader reader = Files.newBufferedReader(configPath)) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        String[] parts = line.split("=", 2);
                        if (parts.length == 2) {
                            String key = parts[0].trim();
                            String value = parts[1].trim();
                            
                            switch (key) {
                                case "highContrast" -> settings.highContrast = Boolean.parseBoolean(value);
                                case "textSpeedMs" -> settings.textSpeedMs = Integer.parseInt(value);
                                case "confirmations" -> settings.confirmations = Boolean.parseBoolean(value);
                                case "sfxEnabled" -> settings.sfxEnabled = Boolean.parseBoolean(value);
                                case "uiScale" -> settings.uiScale = Double.parseDouble(value);
                                case "reducedMotion" -> settings.reducedMotion = Boolean.parseBoolean(value);
                                case "colorBlindMode" -> settings.colorBlindMode = Boolean.parseBoolean(value);
                                case "masterVolume" -> settings.masterVolume = Double.parseDouble(value);
                                case "musicVolume" -> settings.musicVolume = Double.parseDouble(value);
                                case "sfxVolume" -> settings.sfxVolume = Double.parseDouble(value);
                                case "autoSaveEnabled" -> settings.autoSaveEnabled = Boolean.parseBoolean(value);
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Error loading settings: " + e.getMessage());
        }
        
        return settings;
    }
    
    /**
     * Saves settings to disk.
     * 
     * @return true if save was successful, false otherwise
     */
    public boolean save() {
        try {
            // Create config directory if it doesn't exist
            Path configDir = Paths.get(CONFIG_DIR);
            Files.createDirectories(configDir);
            
            // Write settings
            Path configPath = Paths.get(CONFIG_FILE);
            try (BufferedWriter writer = Files.newBufferedWriter(configPath)) {
                writer.write("# The End The Beginning - Configuration v5.0.0\n");
                writer.write("highContrast=" + highContrast + "\n");
                writer.write("textSpeedMs=" + textSpeedMs + "\n");
                writer.write("confirmations=" + confirmations + "\n");
                writer.write("sfxEnabled=" + sfxEnabled + "\n");
                writer.write("uiScale=" + uiScale + "\n");
                writer.write("reducedMotion=" + reducedMotion + "\n");
                writer.write("colorBlindMode=" + colorBlindMode + "\n");
                writer.write("masterVolume=" + masterVolume + "\n");
                writer.write("musicVolume=" + musicVolume + "\n");
                writer.write("sfxVolume=" + sfxVolume + "\n");
                writer.write("autoSaveEnabled=" + autoSaveEnabled + "\n");
            }
            
            return true;
        } catch (IOException e) {
            System.err.println("Error saving settings: " + e.getMessage());
            return false;
        }
    }
}
