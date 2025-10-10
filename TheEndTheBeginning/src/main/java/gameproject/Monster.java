package gameproject;

/**
 * Simple Monster class for combat encounters
 * This is a lightweight version for the MainController to use
 * 
 * @version 3.1.0
 */
public class Monster {
    private final String name;
    private int health;
    private final int maxHealth;
    private final int attack;
    private final String specialAbility;
    
    public Monster(String name, int health, int attack, String specialAbility) {
        this.name = name;
        this.health = health;
        this.maxHealth = health;
        this.attack = attack;
        this.specialAbility = specialAbility;
    }
    
    public String getName() { return name; }
    public int getHealth() { return health; }
    public int getMaxHealth() { return maxHealth; }
    public int getAttack() { return attack; }
    public String getSpecialAbility() { return specialAbility; }
    
    public void takeDamage(int damage) {
        // Clamp damage to safe range
        int clampedDamage = Balance.clampDamage(damage);
        health -= clampedDamage;
        health = Balance.clampHP(health, maxHealth);
    }
    
    public boolean isAlive() {
        return health > 0;
    }
    
    public int calculateDamage() {
        // Calculate damage with randomness, then clamp
        int baseDamage = attack + (int)(Math.random() * attack);
        return Balance.clampDamage(baseDamage);
    }
}