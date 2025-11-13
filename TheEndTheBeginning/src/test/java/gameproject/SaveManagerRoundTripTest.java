package gameproject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.AfterAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import main.model.Player;

/**
 * Test suite for SaveManager round-trip functionality.
 * Tests save and load operations using a temporary directory.
 */
public class SaveManagerRoundTripTest {
    
    private static Path tempSaveDir;
    
    @BeforeAll
    static void setupTempDirectory() throws IOException {
        // Create a temporary directory for test saves
        tempSaveDir = Files.createTempDirectory("theetb-test-saves-");
        // Set system property to override save directory
        System.setProperty("theetb.save.dir", tempSaveDir.toString());
    }
    
    @AfterAll
    static void cleanupTempDirectory() throws IOException {
        // Clean up temp directory
        if (tempSaveDir != null && Files.exists(tempSaveDir)) {
            // Delete all files in directory
            Files.walk(tempSaveDir)
                .sorted((a, b) -> b.compareTo(a)) // Delete files before directories
                .forEach(path -> {
                    try {
                        Files.deleteIfExists(path);
                    } catch (IOException e) {
                        // Ignore cleanup errors
                    }
                });
        }
        // Clear system property
        System.clearProperty("theetb.save.dir");
    }
    
    @BeforeEach
    void setUp() {
        // Ensure clean state - delete any existing save file
        if (SaveManager.saveExists()) {
            SaveManager.deleteSave();
        }
    }
    
    @Test
    void testSaveAndLoadBasicPlayerData() {
        // Create a test player
        Player player = new Player("TestHero", Player.PlayerClass.WARRIOR);
        player.setLevel(5);
        player.setCurrentHealth(80);
        player.setMaxMana(100); // Set max mana first
        player.setMana(50);     // Then set current mana
        player.setDungeonLevel(3);
        
        int dungeonLevel = 3;
        
        // Save the game
        boolean saveResult = SaveManager.saveGame(player, dungeonLevel);
        assertTrue(saveResult, "Save should succeed");
        assertTrue(SaveManager.saveExists(), "Save file should exist");
        
        // Load the game
        SaveManager.SaveData loadedData = SaveManager.loadGame();
        assertNotNull(loadedData, "Loaded data should not be null");
        
        // Verify basic player data
        assertEquals("TestHero", loadedData.name, "Player name should match");
        assertEquals("WARRIOR", loadedData.playerClass, "Player class should match");
        assertEquals(5, loadedData.level, "Level should match");
        assertEquals(80, loadedData.health, "Health should match");
        assertEquals(3, loadedData.dungeonLevel, "Dungeon level should match");
        assertEquals(50, loadedData.mana, "Mana should match");
        assertEquals(100, loadedData.maxMana, "Max mana should match");
    }
    
    @Test
    void testSaveAndLoadWithMage() {
        // Create a Mage player
        Player player = new Player("TestMage", Player.PlayerClass.MAGE);
        player.setLevel(10);
        player.setCurrentHealth(60);
        player.setMaxMana(150); // Set max mana first
        player.setMana(120);    // Then set current mana
        player.setDungeonLevel(7);
        
        // Save and load
        assertTrue(SaveManager.saveGame(player, 7), "Save should succeed");
        SaveManager.SaveData loadedData = SaveManager.loadGame();
        
        assertNotNull(loadedData);
        assertEquals("TestMage", loadedData.name);
        assertEquals("MAGE", loadedData.playerClass);
        assertEquals(10, loadedData.level);
        assertEquals(60, loadedData.health);
        assertEquals(120, loadedData.mana);
        assertEquals(150, loadedData.maxMana);
        assertEquals(7, loadedData.dungeonLevel);
    }
    
    @Test
    void testLoadNonExistentSave() {
        // Ensure no save exists
        if (SaveManager.saveExists()) {
            SaveManager.deleteSave();
        }
        
        SaveManager.SaveData loadedData = SaveManager.loadGame();
        assertNull(loadedData, "Loading non-existent save should return null");
    }
}

