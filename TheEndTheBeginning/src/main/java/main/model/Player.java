package main.model;

import java.util.*;

/**
 * Represents a player character in the dungeon escape game.
 * Features comprehensive RPG mechanics including character classes, 
 * stats progression, inventory management, and achievements tracking.
 * 
 * @author Game Development Team
 * @version 3.1.0
 */
public class Player {
    
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
    private int agility;        // New stat for dodge/accuracy
    private int luck;           // New stat for critical hits
    
    // Advanced combat stats
    private int accuracy;       // Hit chance bonus
    private int criticalChance; // Critical hit chance
    private int blockChance;    // Damage reduction chance
    
    // Equipment slots
    private Item equippedWeapon;
    private Item equippedArmor;
    private Item equippedAccessory;
    
    // Magic system
    private int mana;
    private int maxMana;
    private List<String> knownSpells;
    
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
    
    // Status effects and temporary modifiers (enhanced)
    private Map<gameproject.combat.CombatEngine.StatusEffect, Integer> combatStatusEffects;
    private Map<String, Integer> statusEffects; // Keep for backward compatibility
    private Map<String, Integer> temporaryStats;
    
    /**
     * Enumeration of available player classes with unique stat distributions.
     */
    public enum PlayerClass {
        WARRIOR("Warrior", 120, 15, 8, 5, 8, 6, 85, 15, 25),
        MAGE("Mage", 80, 8, 5, 18, 12, 10, 90, 10, 5),
        ROGUE("Rogue", 100, 12, 12, 10, 15, 12, 95, 25, 15),
        PALADIN("Paladin", 130, 12, 10, 8, 6, 8, 80, 12, 30),
        ARCHER("Archer", 90, 18, 6, 6, 18, 14, 98, 20, 10),
        NECROMANCER("Necromancer", 70, 6, 4, 20, 10, 15, 88, 18, 8);
        
        private final String displayName;
        private final int baseHealth;
        private final int baseAttack;
        private final int baseDefense;
        private final int baseMagic;
        private final int baseAgility;
        private final int baseLuck;
        private final int baseAccuracy;
        private final int baseCritChance;
        private final int baseBlockChance;
        
        PlayerClass(String displayName, int health, int attack, int defense, int magic, 
                   int agility, int luck, int accuracy, int critChance, int blockChance) {
            this.displayName = displayName;
            this.baseHealth = health;
            this.baseAttack = attack;
            this.baseDefense = defense;
            this.baseMagic = magic;
            this.baseAgility = agility;
            this.baseLuck = luck;
            this.baseAccuracy = accuracy;
            this.baseCritChance = critChance;
            this.baseBlockChance = blockChance;
        }
        
        public String getDisplayName() { return displayName; }
        public int getBaseHealth() { return baseHealth; }
        public int getBaseAttack() { return baseAttack; }
        public int getBaseDefense() { return baseDefense; }
        public int getBaseMagic() { return baseMagic; }
        public int getBaseAgility() { return baseAgility; }
        public int getBaseLuck() { return baseLuck; }
        public int getBaseAccuracy() { return baseAccuracy; }
        public int getBaseCritChance() { return baseCritChance; }
        public int getBaseBlockChance() { return baseBlockChance; }
        
        public String getDescription() {
            switch (this) {
                case WARRIOR: return "Tank with high health and defense. Specializes in blocking and heavy attacks.";
                case MAGE: return "Master of magical arts with powerful spells but low physical defense.";
                case ROGUE: return "Agile fighter with high critical hit chance and dodge abilities.";
                case PALADIN: return "Holy warrior with healing abilities and strong defensive capabilities.";
                case ARCHER: return "Ranged expert with high accuracy and agility for hit-and-run tactics.";
                case NECROMANCER: return "Dark magic user who can summon minions and drain life from enemies.";
                default: return "Unknown class";
            }
        }
    }
    
    /**
     * Constructor for creating a new player with specified name and class.
     * 
     * @param name The player's chosen name
     * @param playerClass The selected character class
     */
    public Player(String name, PlayerClass playerClass) {
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
        this.agility = playerClass.getBaseAgility();
        this.luck = playerClass.getBaseLuck();
        this.accuracy = playerClass.getBaseAccuracy();
        this.criticalChance = playerClass.getBaseCritChance();
        this.blockChance = playerClass.getBaseBlockChance();
        
        // Initialize mana system
        this.maxMana = this.baseMagic * 2;
        this.mana = this.maxMana;
        this.knownSpells = new ArrayList<>();
        initializeStartingSpells();
        
        // Initialize equipment slots
        this.equippedWeapon = null;
        this.equippedArmor = null;
        this.equippedAccessory = null;
        
        // Initialize collections
        this.inventory = new ArrayList<>();
        this.achievements = new HashSet<>();
        this.statusEffects = new HashMap<>();
        this.combatStatusEffects = new HashMap<>();
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
    public Player() {
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
            case PALADIN -> 25 + (int) (Math.random() * 12);
            case ARCHER -> 12 + (int) (Math.random() * 6);
            case NECROMANCER -> 8 + (int) (Math.random() * 6);
        };
        
        int attackIncrease = switch (playerClass) {
            case WARRIOR -> 3 + (int) (Math.random() * 3);
            case MAGE -> 1 + (int) (Math.random() * 2);
            case ROGUE -> 2 + (int) (Math.random() * 3);
            case PALADIN -> 2 + (int) (Math.random() * 2);
            case ARCHER -> 4 + (int) (Math.random() * 3);
            case NECROMANCER -> 1 + (int) (Math.random() * 2);
        };
        
        int defenseIncrease = switch (playerClass) {
            case WARRIOR -> 2 + (int) (Math.random() * 2);
            case MAGE -> 1 + (int) (Math.random() * 1);
            case ROGUE -> 2 + (int) (Math.random() * 2);
            case PALADIN -> 3 + (int) (Math.random() * 2);
            case ARCHER -> 1 + (int) (Math.random() * 1);
            case NECROMANCER -> (int) (Math.random() * 1);
        };
        
        int magicIncrease = switch (playerClass) {
            case WARRIOR -> (int) (Math.random() * 2);
            case MAGE -> 4 + (int) (Math.random() * 4);
            case ROGUE -> 1 + (int) (Math.random() * 2);
            case PALADIN -> 2 + (int) (Math.random() * 2);
            case ARCHER -> 1 + (int) (Math.random() * 1);
            case NECROMANCER -> 5 + (int) (Math.random() * 4);
        };
        
        // Increase secondary stats too
        agility += switch (playerClass) {
            case WARRIOR -> 1;
            case MAGE -> 2;
            case ROGUE -> 3;
            case PALADIN -> 1;
            case ARCHER -> 4;
            case NECROMANCER -> 2;
        };
        
        luck += switch (playerClass) {
            case WARRIOR -> 1;
            case MAGE -> 2;
            case ROGUE -> 3;
            case PALADIN -> 2;
            case ARCHER -> 3;
            case NECROMANCER -> 3;
        };
        
        // Increase mana
        maxMana += switch (playerClass) {
            case WARRIOR -> 2;
            case MAGE -> 8;
            case ROGUE -> 3;
            case PALADIN -> 6;
            case ARCHER -> 2;
            case NECROMANCER -> 10;
        };
        mana = maxMana; // Restore mana on level up
        
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
        int totalAttack = baseAttack + (level * 2) + temporaryStats.getOrDefault("attack", 0);
        
        // Equipment bonuses
        if (equippedWeapon != null) {
            totalAttack += equippedWeapon.getValue();
        }
        
        // Status effect bonuses
        if (hasStatusEffect(gameproject.combat.CombatEngine.StatusEffect.RAGE)) {
            totalAttack += 10;
        }
        
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
        int totalDefense = baseDefense + (level * 1) + temporaryStats.getOrDefault("defense", 0);
        
        // Equipment bonuses
        if (equippedArmor != null) {
            totalDefense += equippedArmor.getValue();
        }
        
        // Status effect bonuses
        if (hasStatusEffect(gameproject.combat.CombatEngine.StatusEffect.SHIELD)) {
            totalDefense += 15;
        }
        
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
        int totalMagic = baseMagic + (level * 2);
        
        // Equipment bonuses
        if (equippedAccessory != null && equippedAccessory.getName().contains("Magic")) {
            totalMagic += equippedAccessory.getValue();
        }
        
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
    
    // ===== ADVANCED COMBAT SYSTEM METHODS =====
    
    /**
     * Initialize starting spells based on class
     */
    private void initializeStartingSpells() {
        switch (playerClass) {
            case MAGE:
                knownSpells.add("Fireball");
                knownSpells.add("Heal");
                break;
            case PALADIN:
                knownSpells.add("Holy Strike");
                knownSpells.add("Divine Heal");
                break;
            case NECROMANCER:
                knownSpells.add("Dark Bolt");
                knownSpells.add("Life Drain");
                break;
            default:
                // Other classes start with no spells
                break;
        }
    }
    
    // New stat getters and setters
    public int getAgility() { return agility + temporaryStats.getOrDefault("agility", 0); }
    public int getLuck() { return luck + temporaryStats.getOrDefault("luck", 0); }
    public int getAccuracy() { return accuracy + temporaryStats.getOrDefault("accuracy", 0); }
    public int getCriticalChance() { return criticalChance + temporaryStats.getOrDefault("criticalChance", 0); }
    public int getBlockChance() { return blockChance + temporaryStats.getOrDefault("blockChance", 0); }
    
    public void setAgility(int agility) { this.agility = agility; }
    public void setLuck(int luck) { this.luck = luck; }
    public void setAccuracy(int accuracy) { this.accuracy = accuracy; }
    public void setCriticalChance(int criticalChance) { this.criticalChance = criticalChance; }
    public void setBlockChance(int blockChance) { this.blockChance = blockChance; }
    
    public void setMana(int mana) { this.mana = Math.max(0, Math.min(mana, maxMana)); }
    public void setMaxMana(int maxMana) { this.maxMana = maxMana; }
    
    // Mana system
    public int getMana() { return mana; }
    public int getMaxMana() { return maxMana; }
    public boolean canCastSpell(String spellName) {
        int manaCost = getSpellManaCost(spellName);
        return mana >= manaCost && knownSpells.contains(spellName);
    }
    
    public boolean castSpell(String spellName) {
        if (!canCastSpell(spellName)) return false;
        
        int manaCost = getSpellManaCost(spellName);
        mana -= manaCost;
        return true;
    }
    
    public void restoreMana(int amount) {
        mana = Math.min(maxMana, mana + amount);
    }
    
    private int getSpellManaCost(String spellName) {
        switch (spellName) {
            case "Fireball": return 8;
            case "Heal": return 6;
            case "Holy Strike": return 10;
            case "Divine Heal": return 12;
            case "Dark Bolt": return 7;
            case "Life Drain": return 9;
            default: return 5;
        }
    }
    
    // Equipment system
    public Item getEquippedWeapon() { return equippedWeapon; }
    public Item getEquippedArmor() { return equippedArmor; }
    public Item getEquippedAccessory() { return equippedAccessory; }
    
    public boolean equipWeapon(Item weapon) {
        if (weapon.getType() != Item.ItemType.WEAPON) return false;
        
        if (equippedWeapon != null) {
            inventory.add(equippedWeapon);
        }
        equippedWeapon = weapon;
        inventory.remove(weapon);
        return true;
    }
    
    public boolean equipArmor(Item armor) {
        if (armor.getType() != Item.ItemType.ARMOR) return false;
        
        if (equippedArmor != null) {
            inventory.add(equippedArmor);
        }
        equippedArmor = armor;
        inventory.remove(armor);
        return true;
    }
    
    public boolean equipAccessory(Item accessory) {
        if (accessory.getType() != Item.ItemType.ACCESSORY) return false;
        
        if (equippedAccessory != null) {
            inventory.add(equippedAccessory);
        }
        equippedAccessory = accessory;
        inventory.remove(accessory);
        return true;
    }
    
    // Enhanced status effect system
    public Map<gameproject.combat.CombatEngine.StatusEffect, Integer> getStatusEffects() {
        return new HashMap<>(combatStatusEffects);
    }
    
    public boolean hasStatusEffect(gameproject.combat.CombatEngine.StatusEffect effect) {
        return combatStatusEffects.containsKey(effect);
    }
    
    public void addStatusEffect(gameproject.combat.CombatEngine.StatusEffect effect, int duration) {
        combatStatusEffects.put(effect, Math.max(duration, combatStatusEffects.getOrDefault(effect, 0)));
    }
    
    public void removeStatusEffect(gameproject.combat.CombatEngine.StatusEffect effect) {
        combatStatusEffects.remove(effect);
    }
    
    public void updateStatusEffect(gameproject.combat.CombatEngine.StatusEffect effect, int duration) {
        if (duration <= 0) {
            removeStatusEffect(effect);
        } else {
            combatStatusEffects.put(effect, duration);
        }
    }
    
    // Enhanced combat calculations will be handled by modifying existing methods
    
    public List<String> getKnownSpells() {
        return new ArrayList<>(knownSpells);
    }
    
    public void learnSpell(String spellName) {
        if (!knownSpells.contains(spellName)) {
            knownSpells.add(spellName);
        }
    }
    
    // Equipment methods for string compatibility (used by tests)
    public void equipWeapon(String weaponName) {
        // Create a basic Item for compatibility - using correct constructor
        Item weapon = new Item(weaponName, weaponName + " description", Item.ItemType.WEAPON, 10, false);
        equipWeapon(weapon);
    }
    
    public void equipArmor(String armorName) {
        // Create a basic Item for compatibility - using correct constructor  
        Item armor = new Item(armorName, armorName + " description", Item.ItemType.ARMOR, 8, false);
        equipArmor(armor);
    }
    
    public void equipAccessory(String accessoryName) {
        // Create a basic Item for compatibility - using correct constructor
        Item accessory = new Item(accessoryName, accessoryName + " description", Item.ItemType.ACCESSORY, 5, false);
        equipAccessory(accessory);
    }
    
    // Combat calculation methods
    public double calculateHitChance() {
        return Math.min(0.95, 0.75 + (accuracy / 100.0));
    }
    
    public double calculateCriticalChance() {
        return Math.min(0.50, (luck + criticalChance) / 200.0);
    }
    
    // Status effect compatibility methods
    public void addStatusEffect(String effectName, int duration) {
        statusEffects.put(effectName, duration);
    }
    
    public boolean hasStatusEffect(String effectName) {
        return statusEffects.containsKey(effectName);
    }
    
    public int getStatusEffectTurns(String effectName) {
        return statusEffects.getOrDefault(effectName, 0);
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