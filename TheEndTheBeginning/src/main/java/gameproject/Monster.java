package gameproject;

import java.util.*;
import gameproject.combat.CombatEngine;

/**
 * Enhanced Monster class for "The End The Beginning" v4.0.0
 * Supports advanced combat mechanics including:
 * - Special abilities and attacks
 * - Status effects system
 * - Intelligent AI behaviors
 * - Boss monster mechanics
 * - Environmental interactions
 * 
 * @author Abdul Fornah
 * @version 4.0.0 - Advanced Combat System
 */
public class Monster {
    
    public enum MonsterType {
        BASIC,          // Regular monsters
        ELITE,          // Stronger variants
        BOSS,           // Boss monsters every 10 levels
        LEGENDARY       // Ultra-rare encounters
    }
    
    public enum MonsterFamily {
        GOBLIN,
        UNDEAD,
        BEAST,
        ELEMENTAL,
        DEMON,
        DRAGON,
        CONSTRUCT,
        ABERRATION
    }
    
    // Core stats
    private final String name;
    private final MonsterType type;
    private final MonsterFamily family;
    private int health;
    private final int maxHealth;
    private final int baseAttack;
    private final int defense;
    private final int agility;
    
    // Combat mechanics
    private final double accuracy;
    private final String[] specialAbilities;
    private final double specialAttackChance;
    private final double specialAttackMultiplier;
    private int specialAttackCooldown;
    private final int maxSpecialCooldown;
    
    // Status effects
    private Map<CombatEngine.StatusEffect, Integer> statusEffects;
    
    // AI behavior
    private final String behavior; // "aggressive", "defensive", "cunning"
    private int turnsInCombat;
    
    /**
     * Constructor for creating a monster
     */
    public Monster(String name, MonsterType type, MonsterFamily family, int health, int attack, 
                   int defense, int agility, double accuracy, String[] specialAbilities, 
                   double specialChance, double specialMultiplier, String behavior) {
        this.name = name;
        this.type = type;
        this.family = family;
        this.health = health;
        this.maxHealth = health;
        this.baseAttack = attack;
        this.defense = defense;
        this.agility = agility;
        this.accuracy = accuracy;
        this.specialAbilities = specialAbilities.clone();
        this.specialAttackChance = specialChance;
        this.specialAttackMultiplier = specialMultiplier;
        this.maxSpecialCooldown = 3;
        this.specialAttackCooldown = 0;
        this.behavior = behavior;
        this.statusEffects = new HashMap<>();
        this.turnsInCombat = 0;
    }
    
    // Backward compatibility constructor
    public Monster(String name, int health, int attack, String specialAbility) {
        this(name, MonsterType.BASIC, MonsterFamily.GOBLIN, health, attack, 2, 10, 0.8,
             new String[]{specialAbility}, 0.15, 1.5, "aggressive");
    }
    
    // Getters
    public String getName() { return name; }
    public MonsterType getType() { return type; }
    public MonsterFamily getFamily() { return family; }
    public int getHealth() { return health; }
    public int getMaxHealth() { return maxHealth; }
    public int getAttack() { return baseAttack; }
    public int getDefense() { return defense; }
    public int getAgility() { return agility; }
    public double getAccuracy() { return accuracy; }
    public String getBehavior() { return behavior; }
    
    // Combat methods
    public void takeDamage(int damage) {
        int clampedDamage = Balance.clampDamage(damage);
        health -= clampedDamage;
        health = Balance.clampHP(health, maxHealth);
    }
    
    public void heal(int amount) {
        health = Math.min(maxHealth, health + amount);
    }
    
    public boolean isAlive() {
        return health > 0;
    }
    
    public int calculateDamage() {
        // Base damage with some randomness
        int baseDamage = baseAttack + (int)(Math.random() * (baseAttack / 2));
        
        // Apply status effect modifiers
        if (hasStatusEffect(CombatEngine.StatusEffect.RAGE)) {
            baseDamage += 8;
        }
        if (hasStatusEffect(CombatEngine.StatusEffect.CURSED)) {
            baseDamage -= 5;
        }
        
        return Balance.clampDamage(baseDamage);
    }
    
    /**
     * Determines if monster uses special attack this turn
     */
    public boolean useSpecialAttack() {
        turnsInCombat++;
        
        if (specialAttackCooldown > 0) {
            specialAttackCooldown--;
            return false;
        }
        
        // Adjust chance based on behavior and health
        double adjustedChance = specialAttackChance;
        
        if (behavior.equals("aggressive")) {
            adjustedChance += 0.1;
        } else if (behavior.equals("cunning")) {
            // More likely to use special when low on health
            if (getHealthPercentage() < 0.3) {
                adjustedChance += 0.3;
            }
        }
        
        // Boss monsters use specials more often
        if (type == MonsterType.BOSS && turnsInCombat >= 3) {
            adjustedChance += 0.2;
        }
        
        boolean willUseSpecial = Math.random() < adjustedChance;
        if (willUseSpecial) {
            specialAttackCooldown = maxSpecialCooldown;
        }
        
        return willUseSpecial;
    }
    
    public double getSpecialAttackMultiplier() {
        return specialAttackMultiplier;
    }
    
    public String getSpecialAbility() {
        if (specialAbilities == null || specialAbilities.length == 0) {
            return "Strike";
        }
        return specialAbilities[(int)(Math.random() * specialAbilities.length)];
    }
    
    public List<CombatEngine.StatusEffect> getSpecialAttackEffects() {
        List<CombatEngine.StatusEffect> effects = new ArrayList<>();
        
        String ability = getSpecialAbility();
        switch (ability) {
            case "Poison Bite":
                effects.add(CombatEngine.StatusEffect.POISON);
                break;
            case "Flame Breath":
                effects.add(CombatEngine.StatusEffect.BURN);
                break;
            case "Ice Shard":
                effects.add(CombatEngine.StatusEffect.FREEZE);
                break;
            case "Thunder Strike":
                effects.add(CombatEngine.StatusEffect.STUN);
                break;
            case "Curse":
                effects.add(CombatEngine.StatusEffect.CURSED);
                break;
            case "Intimidate":
                // Reduces player accuracy (implemented in combat engine)
                break;
        }
        
        return effects;
    }
    
    private double getHealthPercentage() {
        return (double) health / maxHealth;
    }
    
    // Status effect system
    public Map<CombatEngine.StatusEffect, Integer> getStatusEffects() {
        return new HashMap<>(statusEffects);
    }
    
    public boolean hasStatusEffect(CombatEngine.StatusEffect effect) {
        return statusEffects.containsKey(effect);
    }
    
    public void addStatusEffect(CombatEngine.StatusEffect effect, int duration) {
        statusEffects.put(effect, Math.max(duration, statusEffects.getOrDefault(effect, 0)));
    }
    
    public void removeStatusEffect(CombatEngine.StatusEffect effect) {
        statusEffects.remove(effect);
    }
    
    public void updateStatusEffect(CombatEngine.StatusEffect effect, int duration) {
        if (duration <= 0) {
            removeStatusEffect(effect);
        } else {
            statusEffects.put(effect, duration);
        }
    }
    
    // Factory methods for creating different monsters
    public static Monster createGoblin(int level) {
        int scaledHealth = 25 + (level * 8);
        int scaledAttack = 4 + (level * 2);
        int scaledDefense = 1 + (level / 2);
        
        String[] abilities = {"Dirty Fighting", "Quick Strike"};
        return new Monster("Goblin", MonsterType.BASIC, MonsterFamily.GOBLIN,
                         scaledHealth, scaledAttack, scaledDefense, 12, 0.8,
                         abilities, 0.15, 1.3, "aggressive");
    }
    
    public static Monster createOrc(int level) {
        int scaledHealth = 40 + (level * 12);
        int scaledAttack = 6 + (level * 3);
        int scaledDefense = 3 + (level / 2);
        
        String[] abilities = {"Battle Rage", "Heavy Strike"};
        return new Monster("Orc Warrior", MonsterType.BASIC, MonsterFamily.GOBLIN,
                         scaledHealth, scaledAttack, scaledDefense, 8, 0.75,
                         abilities, 0.2, 1.8, "aggressive");
    }
    
    public static Monster createSkeleton(int level) {
        int scaledHealth = 30 + (level * 6);
        int scaledAttack = 5 + (level * 2);
        int scaledDefense = 2 + (level / 3);
        
        String[] abilities = {"Bone Throw", "Rattle"};
        return new Monster("Skeleton", MonsterType.BASIC, MonsterFamily.UNDEAD,
                         scaledHealth, scaledAttack, scaledDefense, 10, 0.85,
                         abilities, 0.12, 1.4, "defensive");
    }
    
    public static Monster createZombie(int level) {
        int scaledHealth = 60 + (level * 15);
        int scaledAttack = 4 + (level * 2);
        int scaledDefense = 1 + (level / 4);
        
        String[] abilities = {"Infectious Bite", "Shamble"};
        return new Monster("Zombie", MonsterType.BASIC, MonsterFamily.UNDEAD,
                         scaledHealth, scaledAttack, scaledDefense, 5, 0.7,
                         abilities, 0.18, 1.2, "aggressive");
    }
    
    public static Monster createWolf(int level) {
        int scaledHealth = 35 + (level * 10);
        int scaledAttack = 7 + (level * 3);
        int scaledDefense = 2 + (level / 3);
        
        String[] abilities = {"Pack Hunt", "Howl"};
        return new Monster("Dire Wolf", MonsterType.BASIC, MonsterFamily.BEAST,
                         scaledHealth, scaledAttack, scaledDefense, 15, 0.9,
                         abilities, 0.22, 1.6, "cunning");
    }
    
    public static Monster createSpider(int level) {
        int scaledHealth = 20 + (level * 5);
        int scaledAttack = 3 + (level * 2);
        int scaledDefense = 1 + (level / 4);
        
        String[] abilities = {"Poison Bite", "Web"};
        return new Monster("Giant Spider", MonsterType.BASIC, MonsterFamily.BEAST,
                         scaledHealth, scaledAttack, scaledDefense, 18, 0.85,
                         abilities, 0.3, 1.1, "cunning");
    }
    
    public static Monster createFireElemental(int level) {
        int scaledHealth = 45 + (level * 8);
        int scaledAttack = 8 + (level * 4);
        int scaledDefense = 1 + (level / 5);
        
        String[] abilities = {"Flame Breath", "Ignite"};
        return new Monster("Fire Elemental", MonsterType.ELITE, MonsterFamily.ELEMENTAL,
                         scaledHealth, scaledAttack, scaledDefense, 12, 0.8,
                         abilities, 0.35, 2.0, "aggressive");
    }
    
    public static Monster createIceElemental(int level) {
        int scaledHealth = 50 + (level * 9);
        int scaledAttack = 6 + (level * 3);
        int scaledDefense = 4 + (level / 2);
        
        String[] abilities = {"Ice Shard", "Freeze"};
        return new Monster("Ice Elemental", MonsterType.ELITE, MonsterFamily.ELEMENTAL,
                         scaledHealth, scaledAttack, scaledDefense, 8, 0.75,
                         abilities, 0.25, 1.7, "defensive");
    }
    
    public static Monster createDemon(int level) {
        int scaledHealth = 70 + (level * 12);
        int scaledAttack = 10 + (level * 4);
        int scaledDefense = 3 + (level / 2);
        
        String[] abilities = {"Dark Magic", "Curse", "Intimidate"};
        return new Monster("Lesser Demon", MonsterType.ELITE, MonsterFamily.DEMON,
                         scaledHealth, scaledAttack, scaledDefense, 14, 0.85,
                         abilities, 0.4, 2.2, "cunning");
    }
    
    public static Monster createBossMonster(int level) {
        String bossName;
        MonsterFamily family;
        String[] abilities;
        String behavior = "cunning";
        
        // Different bosses based on level ranges
        if (level <= 10) {
            bossName = "Goblin King";
            family = MonsterFamily.GOBLIN;
            abilities = new String[]{"Royal Command", "Berserker Rage", "Summon Guards"};
        } else if (level <= 20) {
            bossName = "Lich Lord";
            family = MonsterFamily.UNDEAD;
            abilities = new String[]{"Death Ray", "Raise Dead", "Dark Shield"};
        } else if (level <= 30) {
            bossName = "Ancient Wyrm";
            family = MonsterFamily.DRAGON;
            abilities = new String[]{"Dragon Breath", "Wing Buffet", "Terrifying Roar"};
        } else if (level <= 40) {
            bossName = "Demon Lord";
            family = MonsterFamily.DEMON;
            abilities = new String[]{"Hellfire", "Soul Drain", "Demonic Aura"};
        } else {
            bossName = "The Void Incarnate";
            family = MonsterFamily.ABERRATION;
            abilities = new String[]{"Reality Tear", "Void Storm", "Existence Drain"};
        }
        
        int scaledHealth = 150 + (level * 25);
        int scaledAttack = 15 + (level * 5);
        int scaledDefense = 8 + (level / 2);
        
        return new Monster(bossName, MonsterType.BOSS, family,
                         scaledHealth, scaledAttack, scaledDefense, 12, 0.9,
                         abilities, 0.6, 3.0, behavior);
    }
    
    /**
     * Factory method to create appropriate monster for given level
     */
    public static Monster createForLevel(int level) {
        // Boss monsters every 10 levels
        if (level % 10 == 0) {
            return createBossMonster(level);
        }
        
        // Regular monsters with level-appropriate scaling
        Monster[] basicMonsters;
        if (level <= 10) {
            basicMonsters = new Monster[]{
                createGoblin(level),
                createSkeleton(level),
                createWolf(level)
            };
        } else if (level <= 20) {
            basicMonsters = new Monster[]{
                createOrc(level),
                createZombie(level),
                createSpider(level),
                createGoblin(level)
            };
        } else if (level <= 30) {
            basicMonsters = new Monster[]{
                createFireElemental(level),
                createIceElemental(level),
                createDemon(level),
                createOrc(level)
            };
        } else {
            // High level monsters
            basicMonsters = new Monster[]{
                createDemon(level),
                createFireElemental(level),
                createIceElemental(level)
            };
        Random rand = new Random();
        return basicMonsters[rand.nextInt(basicMonsters.length)];
        
        return basicMonsters[(int)(Math.random() * basicMonsters.length)];
    }
    
    @Override
    public String toString() {
        return String.format("%s (%s %s) - HP: %d/%d, ATK: %d, DEF: %d", 
                           name, type, family, health, maxHealth, baseAttack, defense);
    }
}