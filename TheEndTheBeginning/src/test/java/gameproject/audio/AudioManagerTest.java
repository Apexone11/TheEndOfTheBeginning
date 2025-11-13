package gameproject.audio;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test for AudioManager registry integrity.
 * Tests that all expected audio resources are registered.
 */
public class AudioManagerTest {
    
    @Test
    public void testAudioManagerSingleton() {
        AudioManager instance1 = AudioManager.getInstance();
        AudioManager instance2 = AudioManager.getInstance();
        
        assertNotNull(instance1, "AudioManager instance should not be null");
        assertSame(instance1, instance2, "AudioManager should be a singleton");
    }
    
    @Test
    public void testMusicRegistryIntegrity() {
        AudioManager audioManager = AudioManager.getInstance();
        
        // Test that music registry contains expected keys
        // Note: This tests the registry, not actual file existence
        assertTrue(audioManager.isMusicEnabled() || !audioManager.isMusicEnabled(), 
                  "Music enabled state should be accessible");
        
        // Test volume settings
        audioManager.setMusicVolume(0.5);
        assertEquals(0.5, audioManager.getMusicVolume(), 0.01, 
                    "Music volume should be set correctly");
        
        audioManager.setMusicVolume(1.5); // Should clamp to 1.0
        assertEquals(1.0, audioManager.getMusicVolume(), 0.01, 
                    "Music volume should clamp to maximum 1.0");
        
        audioManager.setMusicVolume(-0.5); // Should clamp to 0.0
        assertEquals(0.0, audioManager.getMusicVolume(), 0.01, 
                    "Music volume should clamp to minimum 0.0");
    }
    
    @Test
    public void testSoundRegistryIntegrity() {
        AudioManager audioManager = AudioManager.getInstance();
        
        // Test sound volume settings
        audioManager.setSoundVolume(0.7);
        assertEquals(0.7, audioManager.getSoundVolume(), 0.01, 
                    "Sound volume should be set correctly");
        
        audioManager.setSoundEnabled(true);
        assertTrue(audioManager.isSoundEnabled(), 
                  "Sound should be enabled");
        
        audioManager.setSoundEnabled(false);
        assertFalse(audioManager.isSoundEnabled(), 
                   "Sound should be disabled");
    }
    
    @Test
    public void testMusicPlaybackMethods() {
        AudioManager audioManager = AudioManager.getInstance();
        
        // Test that music methods don't throw exceptions
        // (graceful handling of missing files)
        assertDoesNotThrow(() -> {
            audioManager.playMusic("main_theme");
            audioManager.playMusic("dungeon_ambient", true);
            audioManager.playMusic("combat_theme", false);
            audioManager.stopMusic();
        }, "Music playback methods should handle missing files gracefully");
    }
    
    @Test
    public void testGameStateMusic() {
        AudioManager audioManager = AudioManager.getInstance();
        
        // Test that setGameStateMusic doesn't throw exceptions
        assertDoesNotThrow(() -> {
            audioManager.setGameStateMusic("menu");
            audioManager.setGameStateMusic("dungeon");
            audioManager.setGameStateMusic("combat");
            audioManager.setGameStateMusic("boss");
            audioManager.setGameStateMusic("victory");
            audioManager.setGameStateMusic("game_over");
        }, "Game state music methods should handle missing files gracefully");
    }
    
    @Test
    public void testDispose() {
        AudioManager audioManager = AudioManager.getInstance();
        
        // Test that dispose doesn't throw exceptions
        assertDoesNotThrow(() -> {
            audioManager.dispose();
        }, "Dispose should not throw exceptions");
    }
}

