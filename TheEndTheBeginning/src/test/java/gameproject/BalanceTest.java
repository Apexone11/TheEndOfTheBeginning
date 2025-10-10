package gameproject;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for Balance class (difficulty constants and safety methods).
 * 
 * @version 3.1.0
 */
class BalanceTest {
    
    @Test
    void testEasyDifficultyConstants() {
        assertEquals(0.80, Balance.EASY_HP, 0.01);
        assertEquals(0.85, Balance.EASY_ATK, 0.01);
        assertEquals(0.10, Balance.EASY_DEF_BONUS, 0.01);
        assertEquals(1.00, Balance.EASY_HEAL_MOD, 0.01);
    }
    
    @Test
    void testNormalDifficultyConstants() {
        assertEquals(1.00, Balance.NORM_HP, 0.01);
        assertEquals(1.00, Balance.NORM_ATK, 0.01);
        assertEquals(0.00, Balance.NORM_DEF_BONUS, 0.01);
        assertEquals(1.00, Balance.NORM_HEAL_MOD, 0.01);
    }
    
    @Test
    void testHardDifficultyConstants() {
        assertEquals(1.20, Balance.HARD_HP, 0.01);
        assertEquals(1.15, Balance.HARD_ATK, 0.01);
        assertEquals(0.00, Balance.HARD_DEF_BONUS, 0.01);
        assertEquals(0.95, Balance.HARD_HEAL_MOD, 0.01);
    }
    
    @Test
    void testClampDamage_NormalValues() {
        assertEquals(100, Balance.clampDamage(100));
        assertEquals(500, Balance.clampDamage(500));
    }
    
    @Test
    void testClampDamage_NegativeValues() {
        assertEquals(0, Balance.clampDamage(-1));
        assertEquals(0, Balance.clampDamage(-100));
    }
    
    @Test
    void testClampDamage_OverflowValues() {
        assertEquals(1_000_000, Balance.clampDamage(1_000_001));
        assertEquals(1_000_000, Balance.clampDamage(Integer.MAX_VALUE));
    }
    
    @Test
    void testClampHP_NormalValues() {
        assertEquals(50, Balance.clampHP(50, 100));
        assertEquals(100, Balance.clampHP(100, 100));
    }
    
    @Test
    void testClampHP_BelowZero() {
        assertEquals(0, Balance.clampHP(-10, 100));
        assertEquals(0, Balance.clampHP(-1, 100));
    }
    
    @Test
    void testClampHP_AboveMax() {
        assertEquals(100, Balance.clampHP(150, 100));
        assertEquals(100, Balance.clampHP(999, 100));
    }
}
