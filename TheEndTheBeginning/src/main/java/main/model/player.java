package main.model;

import java.util.*;

/**
 * Represents a player character in the dungeon escape game.
 * Features comprehensive RPG mechanics including character classes, 
 * stats progression, inventory management, and achievements tracking.
 * 
 * @author Game Development Team
 * @version 2.0
 */
public class player {
    
    // Core player properties
    private String name;
    private PlayerClass playerClass;
    private int level;
    private int experience;
    private int experienceToNextLevel;
    
    // Combat stats
    private int maxHealth;
    private int currentHealth;
    private int baseAttack;
    private int baseDefense;
    private int baseMagic;
    
    // Game progression
    private int dungeonLevel;
    private List<Item> inventory;
    private final int maxInventorySize = 20;
    
    // Achievement and statistics tracking
    private Set<String> achievements;
    private int roomsExplored;
    private int monstersDefeated;
    private int itemsFound;
    private int potionsUsed;
    private long totalPlayTime;
    
    // Status effects and temporary modifiers
    private Map<String, Integer> statusEffects;
    private Map<String, Integer> temporaryStats;
    
    /**
     * Enumeration of available player classes with unique stat distributions.
     */
    public enum PlayerClass {
        WARRIOR("Warrior", 120, 15, 8, 5),
        MAGE("Mage", 80, 8, 5, 18),
        ROGUE("Rogue", 100, 12, 12, 10);
        
        private final String displayName;
        private final int baseHealth;
        private final int baseAttack;
        private final int baseDefense;
        private final int baseMagic;
        
        PlayerClass(String displayName, int health, int attack, int defense, int magic) {
            this.displayName = displayName;
            this.baseHealth = health;
            this.baseAttack = attack;
            this.baseDefense = defense;
            this.baseMagic = magic;
        }
        
        public String getDisplayName() { return displayName; }
        public int getBaseHealth() { return baseHealth; }
        public int getBaseAttack() { return baseAttack; }
        public int getBaseDefense() { return baseDefense; }
        public int getBaseMagic() { return baseMagic; }
    }
    
    /**
     * Constructor for creating a new player with specified name and class.
     * 
     * @param name The player's chosen name
     * @param playerClass The selected character class
     */
    public player(String name, PlayerClass playerClass) {
        this.name = name;
        this.playerClass = playerClass;
        this.level = 1;
        this.experience = 0;
        this.experienceToNextLevel = 100;
        this.dungeonLevel = 1;
        
        // Initialize stats based on class
        this.maxHealth = playerClass.getBaseHealth();
        this.currentHealth = maxHealth;
        this.baseAttack = playerClass.getBaseAttack();
        this.baseDefense = playerClass.getBaseDefense();
        this.baseMagic = playerClass.getBaseMagic();
        
        // Initialize collections
        this.inventory = new ArrayList<>();
        this.achievements = new HashSet<>();
        this.statusEffects = new HashMap<>();
        this.temporaryStats = new HashMap<>();
        
        // Initialize statistics
        this.roomsExplored = 0;
        this.monstersDefeated = 0;
        this.itemsFound = 0;
        this.potionsUsed = 0;
        this.totalPlayTime = 0;
    }
    
    /**
     * Default constructor creates a Warrior named "Hero".
     */
    public player() {
        this("Hero", PlayerClass.WARRIOR);
    }
    
    // Experience and Leveling System
    
    /**
     * Awards experience points to the player and handles level up if threshold is reached.
     * 
     * @param exp Experience points to award
     * @return true if player leveled up, false otherwise
     */
    public boolean gainExperience(int exp) {
        this.experience += exp;
        
        if (this.experience >= experienceToNextLevel) {
            return levelUp();
        }
        return false;
    }
    
    /**
     * Handles player level up, increasing stats and updating experience thresholds.
     * 
     * @return true when level up occurs
     */
    private boolean levelUp() {
        level++;
        experience -= experienceToNextLevel;
        experienceToNextLevel = (int) (experienceToNextLevel * 1.2); // 20% increase each level
        
        // Stat increases vary by class
        int healthIncrease = switch (playerClass) {
            case WARRIOR -> 20 + (int) (Math.random() * 10);
            case MAGE -> 10 + (int) (Math.random() * 8);
            case ROGUE -> 15 + (int) (Math.random() * 8);
        };
        
        int attackIncrease = switch (playerClass) {
            case WARRIOR -> 3 + (int) (Math.random() * 3);
            case MAGE -> 1 + (int) (Math.random() * 2);
            case ROGUE -> 2 + (int) (Math.random() * 3);
        };
        
        int defenseIncrease = switch (playerClass) {
            case WARRIOR -> 2 + (int) (Math.random() * 2);
            case MAGE -> 1 + (int) (Math.random() * 1);
            case ROGUE -> 2 + (int) (Math.random() * 2);
        };
        
        int magicIncrease = switch (playerClass) {
            case WARRIOR -> (int) (Math.random() * 2);
            case MAGE -> 4 + (int) (Math.random() * 4);
            case ROGUE -> 1 + (int) (Math.random() * 2);
        };
        
        // Apply increases
        maxHealth += healthIncrease;
        currentHealth += healthIncrease; // Heal on level up
        baseAttack += attackIncrease;
        baseDefense += defenseIncrease;
        baseMagic += magicIncrease;
        
        // Check for level-based achievements
        checkLevelAchievements();
        
        return true;
    }
    
    // Combat System
    
    /**
     * Calculates total attack power including bonuses from equipment and status effects.
     * 
     * @return Total attack value
     */
    public int getAttackPower() {
        int totalAttack = baseAttack + getEquipmentBonus("attack") + getStatusBonus("attack");
        
        // Class-specific bonuses at certain levels
        if (playerClass == PlayerClass.WARRIOR && level >= 5) {
            totalAttack += level / 3; // Warrior bonus scaling
        }
        
        return Math.max(1, totalAttack); // Minimum 1 attack
    }
    
    /**
     * Calculates total defense power including bonuses.
     * 
     * @return Total defense value
     */
    public int getDefensePower() {
        int totalDefense = baseDefense + getEquipmentBonus("defense") + getStatusBonus("defense");
        
        if (playerClass == PlayerClass.WARRIOR && level >= 3) {
            totalDefense += level / 4; // Warrior defense bonus
        }
        
        return Math.max(0, totalDefense);
    }
    
    /**
     * Calculates total magic power including bonuses.
     * 
     * @return Total magic value
     */
    public int getMagicPower() {
        int totalMagic = baseMagic + getEquipmentBonus("magic") + getStatusBonus("magic");
        
        if (playerClass == PlayerClass.MAGE) {
            totalMagic += level / 2; // Mage magic scaling
        }
        
        return Math.max(0, totalMagic);
    }
    
    /**
     * Applies damage to the player, factoring in defense and resistances.
     * 
     * @param damage Raw damage amount
     * @return Actual damage taken after reductions
     */
    public int takeDamage(int damage) {
        // Calculate damage reduction from defense
        int reducedDamage = Math.max(1, damage - (getDefensePower() / 2));
        
        // Apply class-specific damage reduction
        if (playerClass == PlayerClass.ROGUE && Math.random() < 0.15) {
            reducedDamage = 0; // Rogue dodge chance
        }
        
        currentHealth = Math.max(0, currentHealth - reducedDamage);
        
        return reducedDamage;
    }
    
    /**
     * Heals the player by the specified amount, not exceeding max health.
     * 
     * @param healAmount Amount to heal
     * @return Actual amount healed
     */
    public int heal(int healAmount) {
        int actualHealing = Math.min(healAmount, maxHealth - currentHealth);
        currentHealth += actualHealing;
        return actualHealing;
    }
    
    /**
     * Checks if player is alive.
     * 
     * @return true if current health > 0
     */
    public boolean isAlive() {
        return currentHealth > 0;
    }
    
    // Inventory Management
    
    /**
     * Attempts to add an item to the player's inventory.
     * 
     * @param item The item to add
     * @return true if item was added successfully, false if inventory full
     */
    public boolean addItem(Item item) {
        if (inventory.size() < maxInventorySize) {
            inventory.add(item);
            itemsFound++;
            checkItemAchievements();
            return true;
        }
        return false;
    }
    
    /**
     * Uses an item from inventory if available.
     * 
     * @param itemName Name of item to use
     * @return true if item was found and used, false otherwise
     */
    public boolean useItem(String itemName) {
        for (int i = 0; i < inventory.size(); i++) {
            Item item = inventory.get(i);
            if (item.getName().equalsIgnoreCase(itemName)) {
                // Apply item effects
                item.use(this);
                
                // Remove item if it's consumable
                if (item.isConsumable()) {
                    inventory.remove(i);
                    if (item.getType() == Item.ItemType.CONSUMABLE) {
                        potionsUsed++;
                    }
                }
                
                checkItemUsageAchievements();
                return true;
            }
        }
        return false;
    }
    
    /**
     * Gets equipment bonus for a specific stat type.
     * 
     * @param statType The stat type to check ("attack", "defense", "magic", "health")
     * @return Total bonus from equipped items (simplified implementation)
     */
    private int getEquipmentBonus(String statType) {
        int bonus = 0;
        for (Item item : inventory) {
            // Simplified equipment bonus system - weapons boost attack, armor boosts defense
            if (item.getType() == Item.ItemType.WEAPON && statType.equals("attack")) {
                bonus += Math.max(1, item.getValue() / 20); // Basic attack bonus from weapon value
            } else if (item.getType() == Item.ItemType.ARMOR && statType.equals("defense")) {
                bonus += Math.max(1, item.getValue() / 25); // Basic defense bonus from armor value
            } else if (item.getType() == Item.ItemType.ACCESSORY) {
                bonus += Math.max(1, item.getValue() / 30); // Small bonus from accessories
            }
        }
        return bonus;
    }
    
    // Status Effects System
    
    /**
     * Applies a status effect to the player.
     * 
     * @param effect Name of the effect
     * @param duration Duration in turns
     */
    public void applyStatusEffect(String effect, int duration) {
        statusEffects.put(effect, duration);
    }
    
    /**
     * Processes status effects, reducing duration and applying effects.
     */
    public void processStatusEffects() {
        Iterator<Map.Entry<String, Integer>> iterator = statusEffects.entrySet().iterator();
        
        while (iterator.hasNext()) {
            Map.Entry<String, Integer> entry = iterator.next();
            String effect = entry.getKey();
            int duration = entry.getValue();
            
            // Apply effect
            applyStatusEffect(effect);
            
            // Reduce duration
            if (duration <= 1) {
                iterator.remove();
            } else {
                entry.setValue(duration - 1);
            }
        }
    }
    
    /**
     * Applies the immediate effect of a status condition.
     * 
     * @param effect The status effect name
     */
    private void applyStatusEffect(String effect) {
        switch (effect.toLowerCase()) {
            case "regeneration" -> heal(5 + level);
            case "poison" -> takeDamage(3 + dungeonLevel);
            case "strength" -> temporaryStats.put("attack", 5);
            case "weakness" -> temporaryStats.put("attack", -3);
            case "shield" -> temporaryStats.put("defense", 7);
        }
    }
    
    /**
     * Gets temporary stat bonus from status effects.
     * 
     * @param statType The stat type to check
     * @return Temporary bonus value
     */
    private int getStatusBonus(String statType) {
        return temporaryStats.getOrDefault(statType, 0);
    }
    
    // Achievement System
    
    /**
     * Records that a room has been explored and checks related achievements.
     */
    public void recordRoomExplored() {
        roomsExplored++;
        checkExplorationAchievements();
    }
    
    /**
     * Records monster defeat and checks combat achievements.
     */
    public void recordMonsterDefeated() {
        monstersDefeated++;
        checkCombatAchievements();
    }
    
    /**
     * Checks and awards level-based achievements.
     */
    private void checkLevelAchievements() {
        if (level >= 5) achievements.add("Level 5 Adventurer");
        if (level >= 10) achievements.add("Experienced Explorer");
        if (level >= 15) achievements.add("Dungeon Master");
        if (level >= 20) achievements.add("Legendary Hero");
        if (level >= 30) achievements.add("Elite Warrior");
        if (level >= 40) achievements.add("Dungeon Conqueror");
        if (level >= 50) achievements.add("Supreme Champion");
    }
    
    /**
     * Checks and awards exploration achievements.
     */
    private void checkExplorationAchievements() {
        if (roomsExplored >= 10) achievements.add("Room Explorer");
        if (roomsExplored >= 25) achievements.add("Thorough Searcher");
        if (roomsExplored >= 50) achievements.add("Master Explorer");
    }
    
    /**
     * Checks and awards combat achievements.
     */
    private void checkCombatAchievements() {
        if (monstersDefeated >= 5) achievements.add("Monster Slayer");
        if (monstersDefeated >= 15) achievements.add("Beast Hunter");
        if (monstersDefeated >= 30) achievements.add("Legendary Warrior");
    }
    
    /**
     * Checks and awards item-related achievements.
     */
    private void checkItemAchievements() {
        if (itemsFound >= 5) achievements.add("Treasure Hunter");
        if (itemsFound >= 15) achievements.add("Collector");
        if (inventory.size() >= maxInventorySize - 2) achievements.add("Pack Rat");
    }
    
    /**
     * Checks item usage achievements.
     */
    private void checkItemUsageAchievements() {
        if (potionsUsed >= 10) achievements.add("Potion Master");
    }
    
    // Additional method aliases for compatibility with existing code
    public int getHealth() { return getCurrentHealth(); }
    public int getAttack() { return getAttackPower(); }
    public int getDefense() { return getDefensePower(); }
    public int getExperienceToNext() { return getExperienceToNextLevel(); }
    
    public void recordMonsterKill() { recordMonsterDefeated(); }
    
    public int calculateDamage() {
        // Calculate damage based on class and level
        int baseDamage = getAttackPower();
        int randomFactor = (int) (Math.random() * (baseDamage / 3 + 1));
        return baseDamage + randomFactor;
    }
    
    public String getDetailedStats() {
        return toString();
    }
    
    public String getInventoryString() {
        if (inventory.isEmpty()) {
            return "Empty";
        }
        
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < inventory.size(); i++) {
            Item item = inventory.get(i);
            sb.append((i + 1)).append(". ").append(item.getDisplayName()).append("\\n");
        }
        return sb.toString();
    }
    
    // Methods for Item compatibility
    public void setAttack(int attack) { 
        // Temporary stat modification - store as status effect
        temporaryStats.put("attack", attack - baseAttack);
    }
    
    public void setDefense(int defense) { 
        // Temporary stat modification - store as status effect  
        temporaryStats.put("defense", defense - baseDefense);
    }
    
    public void setMaxHealth(int health) {
        this.maxHealth = health;
        if (currentHealth > maxHealth) {
            currentHealth = maxHealth;
        }
    }
    
    public void applyShield(int shieldAmount) {
        // Apply temporary shield effect
        applyStatusEffect("shield", 3); // 3 turns of shield
        temporaryStats.put("defense", temporaryStats.getOrDefault("defense", 0) + shieldAmount);
    }
    
    // Getters and Setters
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public PlayerClass getPlayerClass() { return playerClass; }
    public void setPlayerClass(PlayerClass playerClass) { this.playerClass = playerClass; }
    
    public int getLevel() { return level; }
    public void setLevel(int level) { this.level = level; }
    
    public int getExperience() { return experience; }
    public int getExperienceToNextLevel() { return experienceToNextLevel; }
    
    public int getMaxHealth() { return maxHealth; }
    public int getCurrentHealth() { return currentHealth; }
    public void setCurrentHealth(int currentHealth) { 
        this.currentHealth = Math.max(0, Math.min(currentHealth, maxHealth)); 
    }
    
    public int getBaseAttack() { return baseAttack; }
    public int getBaseDefense() { return baseDefense; }
    public int getBaseMagic() { return baseMagic; }
    
    public int getDungeonLevel() { return dungeonLevel; }
    public void setDungeonLevel(int dungeonLevel) { this.dungeonLevel = dungeonLevel; }
    
    public List<Item> getInventory() { return new ArrayList<>(inventory); }
    public int getMaxInventorySize() { return maxInventorySize; }
    
    public Set<String> getAchievements() { return new HashSet<>(achievements); }
    
    // Statistics getters
    public int getRoomsExplored() { return roomsExplored; }
    public int getMonstersDefeated() { return monstersDefeated; }
    public int getItemsFound() { return itemsFound; }
    public int getPotionsUsed() { return potionsUsed; }
    public long getTotalPlayTime() { return totalPlayTime; }
    public void addPlayTime(long milliseconds) { this.totalPlayTime += milliseconds; }
    
    /**
     * Restores player state from loaded save data.
     * Used by SaveManager to load saved games.
     * 
     * @param level Player level
     * @param exp Current experience
     * @param health Current health
     * @param maxHp Maximum health
     * @param atk Attack stat
     * @param def Defense stat
     * @param mag Magic stat
     * @param roomsExp Rooms explored count
     * @param monstersKilled Monsters defeated count
     */
    public void restoreSaveData(int level, int exp, int health, int maxHp, 
                                int atk, int def, int mag, 
                                int roomsExp, int monstersKilled) {
        this.level = level;
        this.experience = exp;
        this.currentHealth = health;
        this.maxHealth = maxHp;
        this.baseAttack = atk;
        this.baseDefense = def;
        this.baseMagic = mag;
        this.roomsExplored = roomsExp;
        this.monstersDefeated = monstersKilled;
        
        // Recalculate experience to next level
        this.experienceToNextLevel = (int) (100 * Math.pow(1.2, level - 1));
        
        // Recheck achievements
        checkLevelAchievements();
        checkExplorationAchievements();
        checkCombatAchievements();
    }
    
    /**
     * Gets the Magic power stat for compatibility.
     */
    public int getMagic() {
        return getMagicPower();
    }
    
    /**
     * Gets the player's health as a percentage.
     * 
     * @return Health percentage (0.0 to 1.0)
     */
    public double getHealthPercentage() {
        return (double) currentHealth / maxHealth;
    }
    
    /**
     * Gets experience progress as a percentage to next level.
     * 
     * @return Experience percentage (0.0 to 1.0)
     */
    public double getExperiencePercentage() {
        return (double) experience / experienceToNextLevel;
    }
    
    /**
     * Provides a detailed string representation of the player's current status.
     * 
     * @return Formatted status string
     */
    @Override
    public String toString() {
        return String.format("%s the %s (Level %d)\\n" +
                           "Health: %d/%d (%.1f%%)\\n" +
                           "Stats: ATK %d, DEF %d, MAG %d\\n" +
                           "Experience: %d/%d (%.1f%%)\\n" +
                           "Dungeon Level: %d\\n" +
                           "Inventory: %d/%d items\\n" +
                           "Achievements: %d earned",
                           name, playerClass.getDisplayName(), level,
                           currentHealth, maxHealth, getHealthPercentage() * 100,
                           getAttackPower(), getDefensePower(), getMagicPower(),
                           experience, experienceToNextLevel, getExperiencePercentage() * 100,
                           dungeonLevel, inventory.size(), maxInventorySize,
                           achievements.size());
    }
}