package gameproject;

/**
 * Centralized balance constants for difficulty settings.
 * Contains multipliers and bonuses for different difficulty levels.
 * 
 * @version 3.1.0
 */
public final class Balance {
    
    // Easy difficulty constants
    public static final double EASY_HP = 0.80;
    public static final double EASY_ATK = 0.85;
    public static final double EASY_DEF_BONUS = 0.10;
    public static final double EASY_HEAL_MOD = 1.00;
    
    // Normal difficulty constants  
    public static final double NORM_HP = 1.00;
    public static final double NORM_ATK = 1.00;
    public static final double NORM_DEF_BONUS = 0.00;
    public static final double NORM_HEAL_MOD = 1.00;
    
    // Hard difficulty constants
    public static final double HARD_HP = 1.20;
    public static final double HARD_ATK = 1.15;
    public static final double HARD_DEF_BONUS = 0.00;
    public static final double HARD_HEAL_MOD = 0.95;
    
    private Balance() {
        // Private constructor to prevent instantiation
    }
    
    /**
     * Clamps damage value to safe range [0, 1,000,000].
     * 
     * @param dmg The damage value to clamp
     * @return Clamped damage value
     */
    public static int clampDamage(int dmg) {
        if (dmg < 0) return 0;
        if (dmg > 1_000_000) return 1_000_000;
        return dmg;
    }
    
    /**
     * Clamps HP value to safe range [0, maxHP].
     * 
     * @param hp Current HP
     * @param maxHP Maximum HP
     * @return Clamped HP value
     */
    public static int clampHP(int hp, int maxHP) {
        if (hp < 0) return 0;
        if (hp > maxHP) return maxHP;
        return hp;
    }
}
