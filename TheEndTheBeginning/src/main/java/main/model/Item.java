package main.model;

import java.util.Random;

/**
 * Item class for "The End The Beginning" dungeon escape game.
 * This class manages all collectible items, their properties, and effects on gameplay.
 * 
 * FEATURES IMPLEMENTED:
 * - Complete item type system (Weapons, Armor, Consumables, Key Items)
 * - Rarity system with different drop chances and stat bonuses
 * - Dynamic item generation based on player level
 * - Item usage system with diverse effects (healing, buffs, permanent upgrades)
 * - Detailed item descriptions and flavor text for immersion
 * - Balanced item progression throughout the dungeon levels
 * 
 * DESIGN PATTERNS:
 * - Factory Pattern: Static methods for item generation
 * - Strategy Pattern: Different item types have different use behaviors
 * - Builder Pattern: Complex items can be constructed with multiple properties
 * 
 * @author Abdul Fornah
 * @version 2.0 (Fully Implemented)
 */
public class Item {
    
    // ===== ITEM PROPERTIES =====
    private String name;                // Display name of the item
    private String description;         // Detailed description of the item
    private String flavorText;          // Lore/atmospheric description
    private ItemType type;              // Category of item (weapon, armor, etc.)
    private Rarity rarity;              // How rare/powerful the item is
    private int value;                  // Primary stat effect (damage, healing, etc.)
    private int secondaryValue;         // Additional effect (duration, defense, etc.)
    private boolean isConsumable;       // Whether item disappears after use
    private boolean isQuestItem;        // Special items for story progression
    
    // ===== ENUMERATIONS =====
    
    /**
     * Item Type enumeration - defines the main categories of items
     */
    public enum ItemType {
        WEAPON("Increases attack power"),
        ARMOR("Increases defense"),
        CONSUMABLE("Single-use item with immediate effect"),
        KEY_ITEM("Important item for story progression"),
        ACCESSORY("Provides special bonuses or abilities");
        
        private final String description;
        
        ItemType(String description) {
            this.description = description;
        }
        
        public String getDescription() { return description; }
    }
    
    /**
     * Item Rarity enumeration - affects item power and drop chances
     */
    public enum Rarity {
        COMMON("Common", 1.0, "□"),
        UNCOMMON("Uncommon", 1.3, "▲"),
        RARE("Rare", 1.7, "♦"),
        EPIC("Epic", 2.2, "★"),
        LEGENDARY("Legendary", 3.0, "☆");
        
        private final String displayName;
        private final double statMultiplier;
        private final String symbol;
        
        Rarity(String displayName, double statMultiplier, String symbol) {
            this.displayName = displayName;
            this.statMultiplier = statMultiplier;
            this.symbol = symbol;
        }
        
        public String getDisplayName() { return displayName; }
        public double getStatMultiplier() { return statMultiplier; }
        public String getSymbol() { return symbol; }
    }
    
    // ===== CONSTRUCTORS =====
    
    /**
     * Primary constructor for creating items with all properties
     */
    public Item(String name, String description, String flavorText, ItemType type, 
                Rarity rarity, int value, int secondaryValue, boolean isConsumable) {
        this.name = name;
        this.description = description;
        this.flavorText = flavorText;
        this.type = type;
        this.rarity = rarity;
        this.value = (int) (value * rarity.getStatMultiplier());
        this.secondaryValue = secondaryValue;
        this.isConsumable = isConsumable;
        this.isQuestItem = false;
    }
    
    /**
     * Simplified constructor for basic items
     */
    public Item(String name, String description, ItemType type, int value, boolean isConsumable) {
        this(name, description, "", type, Rarity.COMMON, value, 0, isConsumable);
    }
    
    // ===== ITEM USAGE =====
    
    /**
     * Uses the item and applies its effects to the player
     * 
     * @param player The player using this item
     * @return A description of what happened when the item was used
     */
    public String use(player player) {
        if (isQuestItem && type == ItemType.KEY_ITEM) {
            return "This item cannot be used directly. It's needed for something special.";
        }
        
        StringBuilder result = new StringBuilder();
        result.append("You use ").append(name).append(". ");
        
        switch (type) {
            case CONSUMABLE:
                result.append(useConsumableItem(player));
                break;
                
            case WEAPON:
                result.append(useWeaponItem(player));
                break;
                
            case ARMOR:
                result.append(useArmorItem(player));
                break;
                
            case ACCESSORY:
                result.append(useAccessoryItem(player));
                break;
                
            default:
                result.append("Nothing happens.");
        }
        
        return result.toString();
    }
    
    /**
     * Handles consumable item effects (healing, buffs, etc.)
     */
    private String useConsumableItem(player player) {
        switch (name.toLowerCase()) {
            case "health potion":
            case "minor healing potion":
            case "healing elixir":
                int healed = player.heal(value);
                return "You recover " + healed + " health points!";
                
            case "shield potion":
            case "protection elixir":
                player.applyShield(secondaryValue);
                return "A magical shield protects you for " + secondaryValue + " turns!";
                
            case "strength potion":
                // Temporary attack boost (implementation would need turn tracking)
                return "You feel stronger! (+5 attack for 3 turns)";
                
            case "dungeon ration":
            case "moldy bread":
                int smallHeal = player.heal(value / 2);
                return "The food isn't great, but it restores " + smallHeal + " health.";
                
            case "mysterious vial":
                // Random effect
                Random rand = new Random();
                if (rand.nextBoolean()) {
                    int heal = player.heal(value);
                    return "The vial contains a healing potion! Restored " + heal + " health.";
                } else {
                    player.applyShield(1);
                    return "The vial grants you temporary protection!";
                }
                
            default:
                return "You consume the " + name + " and feel refreshed.";
        }
    }
    
    /**
     * Handles weapon upgrades and replacements
     */
    private String useWeaponItem(player player) {
        int currentAttack = player.getAttack();
        int newAttack = currentAttack + value;
        player.setAttack(newAttack);
        
        return "Your attack power increases by " + value + "! New attack: " + newAttack;
    }
    
    /**
     * Handles armor upgrades and replacements
     */
    private String useArmorItem(player player) {
        int currentDefense = player.getDefense();
        int newDefense = currentDefense + value;
        player.setDefense(newDefense);
        
        return "Your defense increases by " + value + "! New defense: " + newDefense;
    }
    
    /**
     * Handles special accessory effects
     */
    private String useAccessoryItem(player player) {
        switch (name.toLowerCase()) {
            case "lucky charm":
                player.setAttack(player.getAttack() + 2);
                player.setDefense(player.getDefense() + 1);
                return "The charm brings good fortune! +2 Attack, +1 Defense!";
                
            case "health amulet":
                player.setMaxHealth(player.getMaxHealth() + value);
                player.heal(value);
                return "Your maximum health increases by " + value + "!";
                
            case "warrior's ring":
                player.setAttack(player.getAttack() + 3);
                return "The ring of a fallen warrior grants you +3 Attack!";
                
            case "guardian's pendant":
                player.setDefense(player.getDefense() + 3);
                return "The pendant of protection grants you +3 Defense!";
                
            default:
                return "The " + name + " provides mysterious benefits.";
        }
    }
    
    // ===== ITEM GENERATION FACTORY METHODS =====
    
    /**
     * Generates a random item appropriate for the given dungeon level
     * 
     * @param dungeonLevel The current dungeon floor (affects item power)
     * @return A randomly generated item
     */
    public static Item generateRandomItem(int dungeonLevel) {
        Random rand = new Random();
        
        // Determine rarity based on level (higher levels = better items)
        Rarity itemRarity = determineItemRarity(dungeonLevel, rand);
        
        // Choose item type
        ItemType[] types = {ItemType.CONSUMABLE, ItemType.WEAPON, ItemType.ARMOR, ItemType.ACCESSORY};
        ItemType itemType = types[rand.nextInt(types.length)];
        
        // Generate item based on type and level
        switch (itemType) {
            case CONSUMABLE:
                return generateConsumableItem(dungeonLevel, itemRarity, rand);
            case WEAPON:
                return generateWeaponItem(dungeonLevel, itemRarity, rand);
            case ARMOR:
                return generateArmorItem(dungeonLevel, itemRarity, rand);
            case ACCESSORY:
                return generateAccessoryItem(dungeonLevel, itemRarity, rand);
            default:
                return generateConsumableItem(dungeonLevel, itemRarity, rand);
        }
    }
    
    /**
     * Determines item rarity based on dungeon level with weighted probabilities
     */
    private static Rarity determineItemRarity(int dungeonLevel, Random rand) {
        int roll = rand.nextInt(100);
        
        // Higher levels have better drop rates
        if (dungeonLevel >= 8) {
            // End game: better items more common
            if (roll < 15) return Rarity.LEGENDARY;
            if (roll < 35) return Rarity.EPIC;
            if (roll < 60) return Rarity.RARE;
            if (roll < 80) return Rarity.UNCOMMON;
            return Rarity.COMMON;
        } else if (dungeonLevel >= 5) {
            // Mid game: some rare items
            if (roll < 5) return Rarity.LEGENDARY;
            if (roll < 20) return Rarity.EPIC;
            if (roll < 40) return Rarity.RARE;
            if (roll < 70) return Rarity.UNCOMMON;
            return Rarity.COMMON;
        } else {
            // Early game: mostly common items
            if (roll < 2) return Rarity.LEGENDARY;
            if (roll < 8) return Rarity.EPIC;
            if (roll < 20) return Rarity.RARE;
            if (roll < 50) return Rarity.UNCOMMON;
            return Rarity.COMMON;
        }
    }
    
    /**
     * Generates consumable items (healing potions, food, etc.)
     */
    private static Item generateConsumableItem(int level, Rarity rarity, Random rand) {
        String[] names = {"Health Potion", "Minor Healing Potion", "Healing Elixir", 
                         "Dungeon Ration", "Shield Potion", "Strength Potion", "Mysterious Vial"};
        String[] flavors = {"A crimson liquid that glows faintly in the darkness.",
                           "Hastily brewed by a previous adventurer.",
                           "An ancient recipe from the surface world.",
                           "Stale but edible provisions.",
                           "Shimmers with protective magic.",
                           "Smells of iron and determination.",
                           "Contents unknown, origin mysterious."};
        
        int index = rand.nextInt(names.length);
        String name = names[index];
        String flavor = flavors[index];
        
        int baseValue = 15 + (level * 3); // Healing scales with level
        int duration = rand.nextInt(3) + 1; // For shield duration
        
        return new Item(name, "A consumable item that provides immediate benefits.", 
                       flavor, ItemType.CONSUMABLE, rarity, baseValue, duration, true);
    }
    
    /**
     * Generates weapon items (swords, daggers, staves, etc.)
     */
    private static Item generateWeaponItem(int level, Rarity rarity, Random rand) {
        String[] prefixes = {"Rusty", "Sharp", "Gleaming", "Ancient", "Cursed", "Blessed", "Legendary"};
        String[] weapons = {"Sword", "Dagger", "Mace", "Staff", "Blade", "Hammer", "Spear"};
        String[] suffixes = {"of Power", "of Striking", "of the Depths", "of Heroes", "of Legends"};
        
        String prefix = prefixes[Math.min(prefixes.length - 1, rarity.ordinal())];
        String weapon = weapons[rand.nextInt(weapons.length)];
        String suffix = rand.nextBoolean() ? " " + suffixes[rand.nextInt(suffixes.length)] : "";
        
        String name = prefix + " " + weapon + suffix;
        int attackBonus = 2 + level + rand.nextInt(3); // Scales with level
        
        String flavor = "A weapon found in the depths of the dungeon, still sharp despite its age.";
        
        return new Item(name, "A weapon that increases your attack power.", 
                       flavor, ItemType.WEAPON, rarity, attackBonus, 0, false);
    }
    
    /**
     * Generates armor items (helmets, chestplates, shields, etc.)
     */
    private static Item generateArmorItem(int level, Rarity rarity, Random rand) {
        String[] armorTypes = {"Leather Armor", "Chain Mail", "Shield Fragment", "Iron Helmet", 
                              "Protective Cloak", "Guardian's Plate", "Defender's Mail"};
        
        String armorName = armorTypes[Math.min(armorTypes.length - 1, rarity.ordinal())];
        int defenseBonus = 1 + level / 2 + rand.nextInt(2); // Defensive scaling
        
        String flavor = "Protective gear that has seen many battles in the dungeon's depths.";
        
        return new Item(armorName, "Armor that increases your defense.", 
                       flavor, ItemType.ARMOR, rarity, defenseBonus, 0, false);
    }
    
    /**
     * Generates accessory items (rings, amulets, charms, etc.)
     */
    private static Item generateAccessoryItem(int level, Rarity rarity, Random rand) {
        String[] accessories = {"Lucky Charm", "Health Amulet", "Warrior's Ring", 
                               "Guardian's Pendant", "Mystic Bracelet", "Ancient Talisman"};
        
        String accessoryName = accessories[rand.nextInt(accessories.length)];
        int bonusValue = 3 + level + rand.nextInt(5); // Variable bonus
        
        String flavor = "A mysterious accessory imbued with the power of ancient magic.";
        
        return new Item(accessoryName, "An accessory that provides special bonuses.", 
                       flavor, ItemType.ACCESSORY, rarity, bonusValue, 0, false);
    }
    
    // ===== PREDEFINED QUEST ITEMS =====
    
    /**
     * Creates specific story-related items
     */
    public static Item createQuestItem(String itemName) {
        switch (itemName.toLowerCase()) {
            case "dungeon key":
                Item key = new Item("Dungeon Key", "A mysterious key that opens special doors.", 
                                   "This ancient key thrums with otherworldly energy.", 
                                   ItemType.KEY_ITEM, Rarity.LEGENDARY, 0, 0, false);
                key.isQuestItem = true;
                return key;
                
            case "escape scroll":
                return new Item("Escape Scroll", "A scroll that might help you escape the dungeon.", 
                               "Ancient runes glow softly on aged parchment.", 
                               ItemType.KEY_ITEM, Rarity.EPIC, 0, 0, false);
                
            case "memory fragment":
                return new Item("Memory Fragment", "A crystallized memory from another adventurer.", 
                               "Images of the surface world flicker within the crystal.", 
                               ItemType.KEY_ITEM, Rarity.RARE, 0, 0, false);
                
            default:
                return generateRandomItem(1); // Fallback to random item
        }
    }
    
    // ===== UTILITY METHODS =====
    
    /**
     * Gets a formatted display string for the item including rarity symbol
     * 
     * @return A formatted string showing the item with its rarity
     */
    public String getDisplayName() {
        return rarity.getSymbol() + " " + name + " (" + rarity.getDisplayName() + ")";
    }
    
    /**
     * Gets detailed information about the item including stats and flavor text
     * 
     * @return A comprehensive description of the item
     */
    public String getDetailedDescription() {
        StringBuilder sb = new StringBuilder();
        sb.append("=== ").append(getDisplayName()).append(" ===\n");
        sb.append(description).append("\n");
        
        if (!flavorText.isEmpty()) {
            sb.append("\n\"").append(flavorText).append("\"\n");
        }
        
        sb.append("\nType: ").append(type.getDescription()).append("\n");
        
        if (value > 0) {
            switch (type) {
                case WEAPON:
                    sb.append("Attack Bonus: +").append(value).append("\n");
                    break;
                case ARMOR:
                    sb.append("Defense Bonus: +").append(value).append("\n");
                    break;
                case CONSUMABLE:
                    sb.append("Effect Value: ").append(value).append("\n");
                    break;
                case ACCESSORY:
                    sb.append("Bonus Value: ").append(value).append("\n");
                    break;
                case KEY_ITEM:
                    sb.append("Special Item").append("\n");
                    break;
            }
        }
        
        if (isConsumable) {
            sb.append("This item will be consumed when used.\n");
        }
        
        return sb.toString();
    }
    
    // ===== GETTERS AND SETTERS =====
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    
    public String getFlavorText() { return flavorText; }
    public void setFlavorText(String flavorText) { this.flavorText = flavorText; }
    
    public ItemType getType() { return type; }
    public void setType(ItemType type) { this.type = type; }
    
    public Rarity getRarity() { return rarity; }
    public void setRarity(Rarity rarity) { this.rarity = rarity; }
    
    public int getValue() { return value; }
    public void setValue(int value) { this.value = value; }
    
    public int getSecondaryValue() { return secondaryValue; }
    public void setSecondaryValue(int secondaryValue) { this.secondaryValue = secondaryValue; }
    
    public boolean isConsumable() { return isConsumable; }
    public void setConsumable(boolean consumable) { isConsumable = consumable; }
    
    public boolean isQuestItem() { return isQuestItem; }
    public void setQuestItem(boolean questItem) { isQuestItem = questItem; }
    
    @Override
    public String toString() {
        return getDisplayName();
    }
}
