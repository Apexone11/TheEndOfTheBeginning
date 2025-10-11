package gameproject.achievements;

import java.time.LocalDateTime;

/**
 * Achievement data class representing a single achievement
 * 
 * @author Abdul Fornah
 * @version 4.0.0
 */
public class Achievement {
    private final String id;
    private final String name;
    private final String description;
    private final AchievementManager.AchievementCategory category;
    private final AchievementManager.AchievementRarity rarity;
    private final String icon;
    private LocalDateTime unlockedDate;
    
    public Achievement(String id, String name, String description, 
                      AchievementManager.AchievementCategory category, 
                      AchievementManager.AchievementRarity rarity, String icon) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.category = category;
        this.rarity = rarity;
        this.icon = icon;
    }
    
    // Getters
    public String getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public AchievementManager.AchievementCategory getCategory() { return category; }
    public AchievementManager.AchievementRarity getRarity() { return rarity; }
    public String getIcon() { return icon; }
    public LocalDateTime getUnlockedDate() { return unlockedDate; }
    
    public void setUnlockedDate(LocalDateTime date) {
        this.unlockedDate = date;
    }
    
    @Override
    public String toString() {
        return String.format("%s %s [%s] - %s", 
            icon, name, rarity.getName(), description);
    }
}