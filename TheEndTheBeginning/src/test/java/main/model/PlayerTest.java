package main.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for Player class.
 * 
 * @version 3.1.0
 */
class PlayerTest {
    
    private Player warrior;
    private Player mage;
    private Player rogue;
    
    @BeforeEach
    void setUp() {
        warrior = new Player("TestWarrior", Player.PlayerClass.WARRIOR);
        mage = new Player("TestMage", Player.PlayerClass.MAGE);
        rogue = new Player("TestRogue", Player.PlayerClass.ROGUE);
    }
    
    @Test
    void testPlayerCreation_Warrior() {
        assertEquals("TestWarrior", warrior.getName());
        assertEquals(Player.PlayerClass.WARRIOR, warrior.getPlayerClass());
        assertEquals(120, warrior.getMaxHealth());
        assertEquals(120, warrior.getHealth());
        assertTrue(warrior.isAlive());
    }
    
    @Test
    void testPlayerCreation_Mage() {
        assertEquals("TestMage", mage.getName());
        assertEquals(Player.PlayerClass.MAGE, mage.getPlayerClass());
        assertEquals(80, mage.getMaxHealth());
    }
    
    @Test
    void testPlayerCreation_Rogue() {
        assertEquals("TestRogue", rogue.getName());
        assertEquals(Player.PlayerClass.ROGUE, rogue.getPlayerClass());
        assertEquals(100, rogue.getMaxHealth());
    }
    
    @Test
    void testDefaultConstructor() {
        Player defaultPlayer = new Player();
        assertEquals("Hero", defaultPlayer.getName());
        assertEquals(Player.PlayerClass.WARRIOR, defaultPlayer.getPlayerClass());
    }
    
    @Test
    void testHeal_Normal() {
        // Damage the warrior first
        warrior.takeDamage(50);
        int currentHealth = warrior.getHealth();
        assertTrue(currentHealth < 120);
        
        // Heal
        int healed = warrior.heal(30);
        assertEquals(30, healed);
        assertEquals(currentHealth + 30, warrior.getHealth());
    }
    
    @Test
    void testHeal_OverMax() {
        // Damage the warrior
        warrior.takeDamage(20);
        
        // Try to heal more than needed
        int healed = warrior.heal(50);
        assertTrue(healed <= 20); // Can't heal more than damage taken
        assertEquals(120, warrior.getHealth()); // Back to max
    }
    
    @Test
    void testTakeDamage() {
        int initialHealth = warrior.getHealth();
        int damageTaken = warrior.takeDamage(30);
        
        assertTrue(damageTaken > 0);
        assertTrue(warrior.getHealth() < initialHealth);
        assertTrue(warrior.isAlive());
    }
    
    @Test
    void testTakeDamage_Fatal() {
        warrior.takeDamage(999);
        assertFalse(warrior.isAlive());
        assertEquals(0, warrior.getHealth());
    }
    
    @Test
    void testIsAlive() {
        assertTrue(warrior.isAlive());
        
        warrior.takeDamage(999);
        assertFalse(warrior.isAlive());
    }
    
    @Test
    void testLevelUp() {
        int initialLevel = warrior.getLevel();
        int initialMaxHealth = warrior.getMaxHealth();
        
        // Gain enough experience to level up
        boolean leveledUp = warrior.gainExperience(100);
        
        assertTrue(leveledUp);
        assertEquals(initialLevel + 1, warrior.getLevel());
        assertTrue(warrior.getMaxHealth() > initialMaxHealth);
    }
    
    @Test
    void testGainExperience_NoLevelUp() {
        int initialLevel = warrior.getLevel();
        
        // Gain small amount of experience
        boolean leveledUp = warrior.gainExperience(10);
        
        assertFalse(leveledUp);
        assertEquals(initialLevel, warrior.getLevel());
        assertEquals(10, warrior.getExperience());
    }
    
    @Test
    void testInventory_AddItem() {
        Item testItem = new Item("Test Potion", "A test item", Item.ItemType.CONSUMABLE, 20, true);
        
        assertTrue(warrior.addItem(testItem));
        assertEquals(1, warrior.getInventory().size());
    }
    
    @Test
    void testGetStats() {
        assertTrue(warrior.getAttack() > 0);
        assertTrue(warrior.getDefense() > 0);
        assertTrue(warrior.getHealth() > 0);
    }
}
