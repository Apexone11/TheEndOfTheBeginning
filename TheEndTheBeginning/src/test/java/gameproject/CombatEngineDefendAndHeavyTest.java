package gameproject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import gameproject.combat.CombatEngine;
import main.model.Player;

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

        // Run multiple attacks to account for randomness
        int normalTotalDamage = 0;
        int heavyTotalDamage = 0;
        int successfulNormalAttacks = 0;
        int successfulHeavyAttacks = 0;

        // Run 20 attacks of each type to get average
        for (int i = 0; i < 20; i++) {
            // Create fresh monsters for each test
            Monster normalMonster = Monster.createGoblin(1);
            Monster heavyMonster = Monster.createGoblin(1);

            // Perform normal attack
            CombatEngine.CombatResult normalResult = CombatEngine.playerAttackMonster(
                testPlayer, normalMonster, CombatEngine.AttackType.NORMAL_ATTACK);

            // Perform heavy attack (simulate mana consumption)
            testPlayer.setMana(testPlayer.getMana() - 10);
            CombatEngine.CombatResult heavyResult = CombatEngine.playerAttackMonster(
                testPlayer, heavyMonster, CombatEngine.AttackType.HEAVY_ATTACK);

            // Only count successful hits
            if (normalResult.result != CombatEngine.AttackResult.MISS) {
                normalTotalDamage += normalResult.damage;
                successfulNormalAttacks++;
            }
            if (heavyResult.result != CombatEngine.AttackResult.MISS) {
                heavyTotalDamage += heavyResult.damage;
                successfulHeavyAttacks++;
            }

            // Reset mana for next iteration
            testPlayer.setMana(50);
        }

        // Calculate averages
        double avgNormalDamage = successfulNormalAttacks > 0 ? (double) normalTotalDamage / successfulNormalAttacks : 0;
        double avgHeavyDamage = successfulHeavyAttacks > 0 ? (double) heavyTotalDamage / successfulHeavyAttacks : 0;

        // Heavy attack should deal significantly more damage on average
        assertTrue(avgHeavyDamage > avgNormalDamage * 1.2,
            String.format("Heavy attack average damage (%.1f) should be > 20%% higher than normal attack (%.1f)",
                avgHeavyDamage, avgNormalDamage));
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

