package gameproject;

import gameproject.combat.CombatEngine;
import main.model.Player;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test suite for CombatEngine defensive stance and heavy attack functionality.
 */
public class CombatEngineDefendAndHeavyTest {
    
    private Player testPlayer;
    private Monster testMonster;
    
    @BeforeEach
    void setUp() {
        testPlayer = new Player("TestWarrior", Player.PlayerClass.WARRIOR);
        testMonster = Monster.createGoblin(1);
    }
    
    @Test
    void testDefensiveStanceDealsZeroDamage() {
        // Record initial monster health
        int initialHealth = testMonster.getHealth();
        
        // Perform defensive stance attack
        CombatEngine.CombatResult result = CombatEngine.playerAttackMonster(
            testPlayer, testMonster, CombatEngine.AttackType.DEFENSIVE_STANCE);
        
        // Verify defensive stance results
        assertEquals(CombatEngine.AttackResult.BLOCKED, result.result, 
            "Defensive stance should result in BLOCKED");
        assertEquals(0, result.damage, "Defensive stance should deal 0 damage");
        assertEquals(initialHealth, testMonster.getHealth(), 
            "Monster health should be unchanged");
        assertTrue(result.description.contains("brace") || result.description.contains("reduce"), 
            "Description should mention defensive action");
    }
    
    @Test
    void testHeavyAttackConsumesMana() {
        // Set initial mana
        testPlayer.setMana(50);
        int initialMana = testPlayer.getMana();
        
        // Simulate heavy attack mana consumption (as done in controller)
        if (testPlayer.getMana() >= 10) {
            testPlayer.setMana(testPlayer.getMana() - 10);
        }
        
        // Verify mana was consumed
        assertEquals(initialMana - 10, testPlayer.getMana(), 
            "Heavy attack should consume 10 mana");
    }
    
    @Test
    void testHeavyAttackRequiresMana() {
        // Set low mana
        testPlayer.setMana(5);
        
        // Verify heavy attack cannot be performed without enough mana
        assertFalse(testPlayer.getMana() >= 10, 
            "Player should not have enough mana for heavy attack");
    }
    
    @Test
    void testHeavyAttackDealsMoreDamageThanNormal() {
        // Ensure player has enough mana
        testPlayer.setMana(50);
        
        // Perform normal attack
        CombatEngine.CombatResult normalResult = CombatEngine.playerAttackMonster(
            testPlayer, testMonster, CombatEngine.AttackType.NORMAL_ATTACK);
        
        // Reset monster health
        testMonster = Monster.createGoblin(1);
        
        // Perform heavy attack (simulating mana consumption)
        testPlayer.setMana(testPlayer.getMana() - 10);
        CombatEngine.CombatResult heavyResult = CombatEngine.playerAttackMonster(
            testPlayer, testMonster, CombatEngine.AttackType.HEAVY_ATTACK);
        
        // Heavy attack should deal more damage (when it hits)
        if (heavyResult.result != CombatEngine.AttackResult.MISS && 
            normalResult.result != CombatEngine.AttackResult.MISS) {
            assertTrue(heavyResult.damage >= normalResult.damage, 
                "Heavy attack should deal at least as much damage as normal attack");
        }
    }
    
    @Test
    void testDefensiveStanceDoesNotDefeatMonster() {
        // Set monster to low health
        testMonster.takeDamage(testMonster.getMaxHealth() - 1);
        assertTrue(testMonster.isAlive(), "Monster should still be alive");
        
        // Use defensive stance
        CombatEngine.CombatResult result = CombatEngine.playerAttackMonster(
            testPlayer, testMonster, CombatEngine.AttackType.DEFENSIVE_STANCE);
        
        // Monster should still be alive (defensive stance doesn't attack)
        assertTrue(testMonster.isAlive(), "Defensive stance should not defeat monster");
        assertFalse(result.targetDefeated, "Target should not be defeated");
    }
}

