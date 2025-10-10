package gameproject;

import main.model.Player;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for SaveManager class (save/load functionality).
 * 
 * @version 3.1.0
 */
class SaveManagerTest {
    
    @AfterEach
    void cleanup() {
        // Clean up test save files
        SaveManager.deleteSave();
    }
    
    @Test
    void testSaveAndLoad_BasicPlayer() {
        // Create a test player
        Player player = new Player("TestHero", Player.PlayerClass.WARRIOR);
        
        // Save the player
        boolean saveSuccess = SaveManager.saveGame(player, 5);
        assertTrue(saveSuccess, "Save should succeed");
        
        // Verify save exists
        assertTrue(SaveManager.saveExists(), "Save file should exist");
        
        // Load the player
        SaveManager.SaveData loadedData = SaveManager.loadGame();
        assertNotNull(loadedData, "Loaded data should not be null");
        assertEquals("TestHero", loadedData.name);
        assertEquals("WARRIOR", loadedData.playerClass);
        assertEquals(5, loadedData.dungeonLevel);
    }
    
    @Test
    void testSaveExists_NoSave() {
        // Clean up first
        SaveManager.deleteSave();
        
        // Check that save doesn't exist
        assertFalse(SaveManager.saveExists(), "Save should not exist initially");
    }
    
    @Test
    void testDeleteSave() {
        // Create and save a player
        Player player = new Player("TestHero", Player.PlayerClass.MAGE);
        SaveManager.saveGame(player, 1);
        
        // Verify it exists
        assertTrue(SaveManager.saveExists(), "Save should exist after saving");
        
        // Delete it
        boolean deleteSuccess = SaveManager.deleteSave();
        assertTrue(deleteSuccess, "Delete should succeed");
        
        // Verify it's gone
        assertFalse(SaveManager.saveExists(), "Save should not exist after deletion");
    }
    
    @Test
    void testGetLatestSave() {
        // Create and save a player
        Player player = new Player("LatestHero", Player.PlayerClass.ROGUE);
        SaveManager.saveGame(player, 10);
        
        // Get latest save
        SaveManager.SaveData latest = SaveManager.getLatestSave();
        assertNotNull(latest, "Latest save should not be null");
        assertEquals("LatestHero", latest.name);
        assertEquals(10, latest.dungeonLevel);
    }
    
    @Test
    void testLoadGame_NoSave() {
        // Clean up first
        SaveManager.deleteSave();
        
        // Try to load when no save exists
        SaveManager.SaveData data = SaveManager.loadGame();
        assertNull(data, "Load should return null when no save exists");
    }
}
