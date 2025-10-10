package gameproject;

/**
 * Utility class for input normalization and validation.
 * Centralizes all input cleaning operations to ensure consistent behavior.
 * 
 * @version 3.1.0
 */
public final class InputUtil {
    
    private InputUtil() {
        // Private constructor to prevent instantiation
    }
    
    /**
     * Normalizes user input by trimming, collapsing spaces, and converting to uppercase.
     * 
     * @param s The input string to normalize
     * @return Normalized string, or empty string if input is null
     */
    public static String norm(String s) {
        return s == null ? "" : s.trim().replaceAll("\\s+", " ").toUpperCase();
    }
    
    /**
     * Checks if a string is empty or null after normalization.
     * 
     * @param s The string to check
     * @return true if the string is empty or null, false otherwise
     */
    public static boolean isEmpty(String s) {
        return norm(s).isEmpty();
    }
}
