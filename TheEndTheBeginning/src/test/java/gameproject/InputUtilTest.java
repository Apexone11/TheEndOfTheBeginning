package gameproject;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class InputUtilTest {
    
    @Test
    void testNorm() {
        assertEquals("TEST", InputUtil.norm(" test "));  // InputUtil.norm returns uppercase
        assertEquals("HELLO", InputUtil.norm("HELLO"));
    }
    
    @Test
    void testIsEmpty() {
        assertTrue(InputUtil.isEmpty(""));
        assertTrue(InputUtil.isEmpty(null));
        assertFalse(InputUtil.isEmpty("test"));
    }
}