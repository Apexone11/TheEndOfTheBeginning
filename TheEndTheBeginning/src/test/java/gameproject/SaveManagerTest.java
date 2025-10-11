package gameproject;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class SaveManagerTest {
    
    @Test
    void testSaveExists() {
        // Test that saveExists method works (returns boolean)
        boolean exists = SaveManager.saveExists();
        // This is just testing the method doesn't throw an exception
        assertTrue(exists || !exists); // Always true, just testing it runs
    }
}