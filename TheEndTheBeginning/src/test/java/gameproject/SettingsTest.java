package gameproject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test class for Settings functionality.
 * Tests game settings persistence and configuration.
 */
class SettingsTest {

    private Settings settings;

    @BeforeEach
    void setUp() {
        settings = new Settings();
    }

    @Test
    void testDefaultSettings() {
        // Test default settings values
        assertFalse(settings.highContrast, "High contrast should be false by default");
        assertEquals(0, settings.textSpeedMs, "Text speed should be 0 (instant) by default");
        assertTrue(settings.confirmations, "Confirmations should be true by default");
        assertFalse(settings.sfxEnabled, "SFX should be disabled by default");
    }

    @Test
    void testHighContrastSetting() {
        // Test high contrast toggle
        settings.highContrast = true;
        assertTrue(settings.highContrast, "High contrast should be enabled when set to true");
        
        settings.highContrast = false;
        assertFalse(settings.highContrast, "High contrast should be disabled when set to false");
    }

    @Test
    void testTextSpeedSetting() {
        // Test text speed setting
        settings.textSpeedMs = 50;
        assertEquals(50, settings.textSpeedMs, "Text speed should be set correctly");
        
        settings.textSpeedMs = 0;
        assertEquals(0, settings.textSpeedMs, "Text speed should allow instant (0ms)");
        
        settings.textSpeedMs = 1000;
        assertEquals(1000, settings.textSpeedMs, "Text speed should allow slow speeds");
    }

    @Test
    void testConfirmationsSetting() {
        // Test confirmations toggle
        settings.confirmations = false;
        assertFalse(settings.confirmations, "Confirmations should be disabled when set to false");
        
        settings.confirmations = true;
        assertTrue(settings.confirmations, "Confirmations should be enabled when set to true");
    }

    @Test
    void testSfxEnabledSetting() {
        // Test SFX enabled toggle
        settings.sfxEnabled = true;
        assertTrue(settings.sfxEnabled, "SFX should be enabled when set to true");
        
        settings.sfxEnabled = false;
        assertFalse(settings.sfxEnabled, "SFX should be disabled when set to false");
    }

    @Test
    void testSettingsPersistence() {
        // Set some custom values
        settings.highContrast = true;
        settings.textSpeedMs = 75;
        settings.confirmations = false;
        settings.sfxEnabled = true;
        
        // Save settings
        boolean saveResult = settings.save();
        assertTrue(saveResult, "Settings save should succeed");
        
        // Load new settings instance
        Settings loadedSettings = Settings.load();
        
        // Values should match what we saved
        assertEquals(settings.highContrast, loadedSettings.highContrast, "High contrast should persist");
        assertEquals(settings.textSpeedMs, loadedSettings.textSpeedMs, "Text speed should persist");
        assertEquals(settings.confirmations, loadedSettings.confirmations, "Confirmations should persist");
        assertEquals(settings.sfxEnabled, loadedSettings.sfxEnabled, "SFX enabled should persist");
    }

    @Test
    void testLoadDefaultsOnMissingFile() {
        // Backup existing config file if it exists
        Path configDir = Paths.get(System.getProperty("user.home"), ".the-end-the-beginning");
        Path configFile = configDir.resolve("config.properties");
        Path backupFile = configDir.resolve("config.properties.backup");
        
        try {
            // Backup existing file
            if (Files.exists(configFile)) {
                Files.copy(configFile, backupFile, StandardCopyOption.REPLACE_EXISTING);
                Files.delete(configFile);
            }
            
            // Load settings when no file exists
            Settings loadedSettings = Settings.load();
            
            // Should have default values
            assertFalse(loadedSettings.highContrast, "High contrast should be false by default");
            assertEquals(0, loadedSettings.textSpeedMs, "Text speed should be 0 by default");
            assertTrue(loadedSettings.confirmations, "Confirmations should be true by default");
            assertFalse(loadedSettings.sfxEnabled, "SFX should be false by default");
            
        } catch (IOException e) {
            fail("Failed to manage config file for testing: " + e.getMessage());
        } finally {
            // Restore backup if it exists
            try {
                if (Files.exists(backupFile)) {
                    Files.move(backupFile, configFile, StandardCopyOption.REPLACE_EXISTING);
                }
            } catch (IOException e) {
                System.err.println("Warning: Failed to restore config backup: " + e.getMessage());
            }
        }
    }

    @Test
    void testNegativeTextSpeed() {
        // Test that negative text speed doesn't break anything
        settings.textSpeedMs = -10;
        assertEquals(-10, settings.textSpeedMs, "Negative text speed should be allowed (handled by game logic)");
    }

    @Test
    void testSettingsSaveAndLoad() {
        // Test complete save/load cycle
        settings.highContrast = true;
        settings.textSpeedMs = 100;
        settings.confirmations = false;
        settings.sfxEnabled = true;
        
        // Save and verify successful save
        assertTrue(settings.save(), "Settings should save successfully");
        
        // Create new instance and load
        Settings newSettings = Settings.load();
        
        // Verify all settings loaded correctly
        assertTrue(newSettings.highContrast, "High contrast should load correctly");
        assertEquals(100, newSettings.textSpeedMs, "Text speed should load correctly");
        assertFalse(newSettings.confirmations, "Confirmations should load correctly");
        assertTrue(newSettings.sfxEnabled, "SFX enabled should load correctly");
    }
}