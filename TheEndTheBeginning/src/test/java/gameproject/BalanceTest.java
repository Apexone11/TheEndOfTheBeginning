package gameproject;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class BalanceTest {
    
    @Test
    void testClampDamage() {
        int result = Balance.clampDamage(50);
        assertTrue(result >= 0);
        assertTrue(result <= 50);
    }
    
    @Test
    void testClampHP() {
        int result = Balance.clampHP(80, 100);
        assertTrue(result >= 0);
        assertTrue(result <= 100);
    }
}