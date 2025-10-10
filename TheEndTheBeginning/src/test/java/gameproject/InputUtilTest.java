package gameproject;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for InputUtil class (input normalization).
 * 
 * @version 3.1.0
 */
class InputUtilTest {
    
    @Test
    void testNorm_NullInput() {
        assertEquals("", InputUtil.norm(null));
    }
    
    @Test
    void testNorm_EmptyString() {
        assertEquals("", InputUtil.norm(""));
        assertEquals("", InputUtil.norm("   "));
    }
    
    @Test
    void testNorm_Trimming() {
        assertEquals("HELLO", InputUtil.norm("  hello  "));
        assertEquals("TEST", InputUtil.norm("test   "));
        assertEquals("WORD", InputUtil.norm("   word"));
    }
    
    @Test
    void testNorm_Uppercase() {
        assertEquals("HELLO WORLD", InputUtil.norm("hello world"));
        assertEquals("TEST123", InputUtil.norm("test123"));
    }
    
    @Test
    void testNorm_CollapseSpaces() {
        assertEquals("HELLO WORLD", InputUtil.norm("hello    world"));
        assertEquals("ONE TWO THREE", InputUtil.norm("one  two   three"));
    }
    
    @Test
    void testNorm_Combined() {
        assertEquals("USE POTION", InputUtil.norm("  use   potion  "));
        assertEquals("LOAD GAME", InputUtil.norm("load    game"));
    }
    
    @Test
    void testIsEmpty_NullAndEmpty() {
        assertTrue(InputUtil.isEmpty(null));
        assertTrue(InputUtil.isEmpty(""));
        assertTrue(InputUtil.isEmpty("   "));
    }
    
    @Test
    void testIsEmpty_NonEmpty() {
        assertFalse(InputUtil.isEmpty("hello"));
        assertFalse(InputUtil.isEmpty("  test  "));
    }
}
