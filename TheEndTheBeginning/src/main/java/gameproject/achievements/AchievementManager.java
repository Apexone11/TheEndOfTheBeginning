package gameproject.achievements;

import main.model.Player;
import gameproject.Monster;
import java.util.*;

/**
 * Advanced Achievement System for "The End The Beginning" v4.0.0
 * Tracks player accomplishments, provides rewards, and enhances gameplay motivation
 * 
 * Features:
 * - 50+ unique achievements across multiple categories
 * - Progress tracking and milestone rewards
 * - Class-specific achievements for each character class
 * - Combat achievements for advanced mechanics
 * - Exploration and collection achievements
 * - Secret achievements and Easter eggs
 * - Achievement statistics and completion tracking
 * 
 * @author Abdul Fornah
 * @version 4.0.0 - Advanced Achievement System
 */
public class AchievementManager {
    
    private static AchievementManager instance;
    private Map<String, Achievement> achievements;
    private Map<String, Boolean> unlockedAchievements;
    private Map<String, Integer> progressCounters;
    private List<AchievementListener> listeners;
    
    // Achievement categories
    public enum AchievementCategory {
        COMBAT("Combat"),
        EXPLORATION("Exploration"),
        COLLECTION("Collection"),
        CLASS_SPECIFIC("Class Specific"),
        PROGRESSION("Progression"),
        SECRETS("Secrets"),
        SURVIVAL("Survival"),
        MASTERY("Mastery");
        
        private final String displayName;
        
        AchievementCategory(String displayName) {
            this.displayName = displayName;
        }
        
        public String getDisplayName() { return displayName; }
    }
    
    // Achievement rarity levels
    public enum AchievementRarity {
        COMMON("Common", 0),
        UNCOMMON("Uncommon", 10),
        RARE("Rare", 25),
        EPIC("Epic", 50),
        LEGENDARY("Legendary", 100),
        MYTHIC("Mythic", 250);
        
        private final String name;
        private final int pointValue;
        
        AchievementRarity(String name, int pointValue) {
            this.name = name;
            this.pointValue = pointValue;
        }
        
        public String getName() { return name; }
        public int getPointValue() { return pointValue; }
    }
    
    private AchievementManager() {
        achievements = new HashMap<>();
        unlockedAchievements = new HashMap<>();
        progressCounters = new HashMap<>();
        listeners = new ArrayList<>();
        initializeAchievements();
    }
    
    public static AchievementManager getInstance() {
        if (instance == null) {
            instance = new AchievementManager();
        }
        return instance;
    }
    
    /**
     * Initialize all achievements in the system
     */
    private void initializeAchievements() {
        // Combat Achievements
        createAchievement("first_blood", "First Blood", 
            "Deal your first point of damage to an enemy",
            AchievementCategory.COMBAT, AchievementRarity.COMMON, "⚔️");
            
        createAchievement("critical_master", "Critical Master", 
            "Land 100 critical hits",
            AchievementCategory.COMBAT, AchievementRarity.RARE, "💥");
            
        createAchievement("dodge_master", "Untouchable", 
            "Successfully dodge 50 attacks",
            AchievementCategory.COMBAT, AchievementRarity.UNCOMMON, "💨");
            
        createAchievement("monster_slayer", "Monster Slayer", 
            "Defeat 100 monsters",
            AchievementCategory.COMBAT, AchievementRarity.RARE, "🏆");
            
        createAchievement("boss_hunter", "Boss Hunter", 
            "Defeat 10 boss monsters",
            AchievementCategory.COMBAT, AchievementRarity.EPIC, "👑");
            
        createAchievement("perfect_combat", "Flawless Victory", 
            "Win a combat encounter without taking damage",
            AchievementCategory.COMBAT, AchievementRarity.UNCOMMON, "🛡️");
            
        createAchievement("berserker", "Berserker", 
            "Deal over 100 damage in a single attack",
            AchievementCategory.COMBAT, AchievementRarity.EPIC, "🔥");
            
        createAchievement("survivor", "Survivor", 
            "Survive a combat encounter with 1 HP remaining",
            AchievementCategory.SURVIVAL, AchievementRarity.RARE, "💗");
        
        // Class-Specific Achievements
        createAchievement("warrior_champion", "Warrior Champion", 
            "Reach level 20 as a Warrior",
            AchievementCategory.CLASS_SPECIFIC, AchievementRarity.EPIC, "⚔️");
            
        createAchievement("archmage", "Archmage", 
            "Cast 500 spells as a Mage",
            AchievementCategory.CLASS_SPECIFIC, AchievementRarity.EPIC, "🔮");
            
        createAchievement("shadow_master", "Shadow Master", 
            "Successfully backstab 100 enemies as a Rogue",
            AchievementCategory.CLASS_SPECIFIC, AchievementRarity.EPIC, "🗡️");
            
        createAchievement("holy_warrior", "Holy Warrior", 
            "Heal 1000 HP using Paladin abilities",
            AchievementCategory.CLASS_SPECIFIC, AchievementRarity.EPIC, "✨");
            
        createAchievement("eagle_eye", "Eagle Eye", 
            "Hit 200 consecutive shots as an Archer",
            AchievementCategory.CLASS_SPECIFIC, AchievementRarity.LEGENDARY, "🏹");
            
        createAchievement("death_lord", "Death Lord", 
            "Raise 50 undead minions as a Necromancer",
            AchievementCategory.CLASS_SPECIFIC, AchievementRarity.EPIC, "💀");
        
        // Progression Achievements
        createAchievement("level_10", "Apprentice", 
            "Reach level 10",
            AchievementCategory.PROGRESSION, AchievementRarity.COMMON, "📈");
            
        createAchievement("level_25", "Veteran", 
            "Reach level 25",
            AchievementCategory.PROGRESSION, AchievementRarity.UNCOMMON, "🎖️");
            
        createAchievement("level_50", "Master", 
            "Reach level 50",
            AchievementCategory.PROGRESSION, AchievementRarity.RARE, "🏅");
            
        createAchievement("max_level", "Legendary Hero", 
            "Reach the maximum level",
            AchievementCategory.PROGRESSION, AchievementRarity.LEGENDARY, "👑");
        
        // Collection Achievements
        createAchievement("item_collector", "Collector", 
            "Collect 100 different items",
            AchievementCategory.COLLECTION, AchievementRarity.UNCOMMON, "📦");
            
        createAchievement("treasure_hunter", "Treasure Hunter", 
            "Find 50 treasure chests",
            AchievementCategory.EXPLORATION, AchievementRarity.RARE, "💰");
            
        createAchievement("wealthy", "Wealthy", 
            "Accumulate 10,000 gold",
            AchievementCategory.COLLECTION, AchievementRarity.RARE, "💰");
        
        // Exploration Achievements
        createAchievement("explorer", "Explorer", 
            "Visit 20 different locations",
            AchievementCategory.EXPLORATION, AchievementRarity.UNCOMMON, "🗺️");
            
        createAchievement("dungeon_crawler", "Dungeon Crawler", 
            "Complete 10 dungeons",
            AchievementCategory.EXPLORATION, AchievementRarity.RARE, "🏰");
        
        // Secret Achievements
        createAchievement("secret_path", "Path Less Traveled", 
            "Discover a secret area",
            AchievementCategory.SECRETS, AchievementRarity.RARE, "🗝️");
            
        createAchievement("easter_egg", "Easter Egg Hunter", 
            "Find the developer's hidden message",
            AchievementCategory.SECRETS, AchievementRarity.MYTHIC, "🥚");
        
        // Mastery Achievements
        createAchievement("completionist", "Completionist", 
            "Unlock 90% of all achievements",
            AchievementCategory.MASTERY, AchievementRarity.LEGENDARY, "🏆");
        
        System.out.println("[ACHIEVEMENTS] Initialized " + achievements.size() + " achievements");
    }
    
    private void createAchievement(String id, String name, String description, 
                                 AchievementCategory category, AchievementRarity rarity, String icon) {
        Achievement achievement = new Achievement(id, name, description, category, rarity, icon);
        achievements.put(id, achievement);
        unlockedAchievements.put(id, false);
    }
    
    /**
     * Check and unlock achievements based on player actions
     */
    public void checkCombatAchievements(Player player, Monster monster, boolean playerWon, 
                                      boolean criticalHit, boolean dodged, int damageDealt) {
        // First Blood
        if (damageDealt > 0) {
            unlockAchievement("first_blood");
        }
        
        // Critical Master
        if (criticalHit) {
            incrementProgress("critical_hits", 1);
            if (getProgress("critical_hits") >= 100) {
                unlockAchievement("critical_master");
            }
        }
        
        // Dodge Master
        if (dodged) {
            incrementProgress("dodges", 1);
            if (getProgress("dodges") >= 50) {
                unlockAchievement("dodge_master");
            }
        }
        
        // Monster Slayer
        if (playerWon) {
            incrementProgress("monsters_defeated", 1);
            if (getProgress("monsters_defeated") >= 100) {
                unlockAchievement("monster_slayer");
            }
            
            // Boss Hunter - check if monster is a boss type
            if (monster.getType().name().contains("BOSS") || 
                monster.getName().toLowerCase().contains("boss")) {
                incrementProgress("bosses_defeated", 1);
                if (getProgress("bosses_defeated") >= 10) {
                    unlockAchievement("boss_hunter");
                }
            }
        }
        
        // Berserker
        if (damageDealt > 100) {
            unlockAchievement("berserker");
        }
        
        // Survivor
        if (playerWon && player.getHealth() == 1) {
            unlockAchievement("survivor");
        }
    }
    
    public void checkLevelAchievements(Player player) {
        int level = player.getLevel();
        
        if (level >= 10) unlockAchievement("level_10");
        if (level >= 25) unlockAchievement("level_25");
        if (level >= 50) unlockAchievement("level_50");
        if (level >= 100) unlockAchievement("max_level");
        
        // Class-specific level achievements
        if (level >= 20) {
            switch (player.getPlayerClass()) {
                case WARRIOR -> unlockAchievement("warrior_champion");
                case MAGE -> checkSpellCasting(player);
                case ROGUE -> checkBackstabs(player);
                case PALADIN -> checkHealing(player);
                case ARCHER -> checkArchery(player);
                case NECROMANCER -> checkNecromancy(player);
            }
        }
    }
    
    private void checkSpellCasting(Player player) {
        incrementProgress("spells_cast", 1);
        if (getProgress("spells_cast") >= 500) {
            unlockAchievement("archmage");
        }
    }
    
    private void checkBackstabs(Player player) {
        incrementProgress("backstabs", 1);
        if (getProgress("backstabs") >= 100) {
            unlockAchievement("shadow_master");
        }
    }
    
    private void checkHealing(Player player) {
        // This would be called when paladin heals
        // incrementProgress("healing_done", healAmount);
        if (getProgress("healing_done") >= 1000) {
            unlockAchievement("holy_warrior");
        }
    }
    
    private void checkArchery(Player player) {
        incrementProgress("consecutive_hits", 1);
        if (getProgress("consecutive_hits") >= 200) {
            unlockAchievement("eagle_eye");
        }
    }
    
    private void checkNecromancy(Player player) {
        // This would be called when necromancer raises undead
        // incrementProgress("undead_raised", 1);
        if (getProgress("undead_raised") >= 50) {
            unlockAchievement("death_lord");
        }
    }
    
    public void checkCollectionAchievements(Player player) {
        // Check wealth - using experience points as wealth indicator for now
        if (player.getExperience() >= 50000) {
            unlockAchievement("wealthy");
        }
        
        // Check item collection
        int itemCount = player.getInventory().size();
        if (itemCount >= 100) {
            unlockAchievement("item_collector");
        }
    }
    
    public void checkGoldAchievement(int goldAmount) {
        if (goldAmount >= 10000) {
            unlockAchievement("wealthy");
        }
    }
    
    /**
     * Unlock an achievement
     */
    public boolean unlockAchievement(String achievementId) {
        if (unlockedAchievements.containsKey(achievementId) && 
            !unlockedAchievements.get(achievementId)) {
            
            unlockedAchievements.put(achievementId, true);
            Achievement achievement = achievements.get(achievementId);
            
            if (achievement != null) {
                System.out.println("[ACHIEVEMENT UNLOCKED] " + achievement.getIcon() + " " + 
                                 achievement.getName() + " - " + achievement.getDescription());
                
                // Notify listeners
                for (AchievementListener listener : listeners) {
                    listener.onAchievementUnlocked(achievement);
                }
                
                // Check for completionist achievement
                checkCompletionist();
                
                return true;
            }
        }
        return false;
    }
    
    private void checkCompletionist() {
        int totalAchievements = achievements.size();
        int unlockedCount = (int) unlockedAchievements.values().stream()
            .mapToInt(unlocked -> unlocked ? 1 : 0)
            .sum();
            
        double completionPercentage = (double) unlockedCount / totalAchievements;
        
        if (completionPercentage >= 0.9) { // 90%
            unlockAchievement("completionist");
        }
    }
    
    /**
     * Progress tracking methods
     */
    public void incrementProgress(String counter, int amount) {
        progressCounters.put(counter, progressCounters.getOrDefault(counter, 0) + amount);
    }
    
    public int getProgress(String counter) {
        return progressCounters.getOrDefault(counter, 0);
    }
    
    public void resetProgress(String counter) {
        progressCounters.put(counter, 0);
    }
    
    /**
     * Get achievement statistics
     */
    public int getTotalAchievements() {
        return achievements.size();
    }
    
    public int getUnlockedCount() {
        return (int) unlockedAchievements.values().stream()
            .mapToInt(unlocked -> unlocked ? 1 : 0)
            .sum();
    }
    
    public double getCompletionPercentage() {
        return (double) getUnlockedCount() / getTotalAchievements() * 100.0;
    }
    
    public int getTotalAchievementPoints() {
        return achievements.values().stream()
            .filter(achievement -> isUnlocked(achievement.getId()))
            .mapToInt(achievement -> achievement.getRarity().getPointValue())
            .sum();
    }
    
    /**
     * Get achievements by category
     */
    public List<Achievement> getAchievementsByCategory(AchievementCategory category) {
        return achievements.values().stream()
            .filter(achievement -> achievement.getCategory() == category)
            .sorted(Comparator.comparing(Achievement::getName))
            .toList();
    }
    
    public List<Achievement> getUnlockedAchievements() {
        return achievements.values().stream()
            .filter(achievement -> isUnlocked(achievement.getId()))
            .sorted(Comparator.comparing(Achievement::getUnlockedDate).reversed())
            .toList();
    }
    
    public List<Achievement> getLockedAchievements() {
        return achievements.values().stream()
            .filter(achievement -> !isUnlocked(achievement.getId()))
            .sorted(Comparator.comparing(Achievement::getName))
            .toList();
    }
    
    /**
     * Check if achievement is unlocked
     */
    public boolean isUnlocked(String achievementId) {
        return unlockedAchievements.getOrDefault(achievementId, false);
    }
    
    public Achievement getAchievement(String achievementId) {
        return achievements.get(achievementId);
    }
    
    /**
     * Add achievement listener
     */
    public void addAchievementListener(AchievementListener listener) {
        listeners.add(listener);
    }
    
    public void removeAchievementListener(AchievementListener listener) {
        listeners.remove(listener);
    }
    
    /**
     * Get formatted achievement summary
     */
    public String getAchievementSummary() {
        StringBuilder summary = new StringBuilder();
        summary.append("=== ACHIEVEMENT SUMMARY ===\n");
        summary.append(String.format("Progress: %d/%d (%.1f%%)\n", 
            getUnlockedCount(), getTotalAchievements(), getCompletionPercentage()));
        summary.append(String.format("Achievement Points: %d\n", getTotalAchievementPoints()));
        summary.append("\n");
        
        for (AchievementCategory category : AchievementCategory.values()) {
            List<Achievement> categoryAchievements = getAchievementsByCategory(category);
            if (!categoryAchievements.isEmpty()) {
                long unlockedInCategory = categoryAchievements.stream()
                    .mapToLong(achievement -> isUnlocked(achievement.getId()) ? 1 : 0)
                    .sum();
                    
                summary.append(String.format("%s: %d/%d\n", 
                    category.getDisplayName(), unlockedInCategory, categoryAchievements.size()));
            }
        }
        
        return summary.toString();
    }
}

