package gameproject.achievements;

/**
 * Achievement event listener interface for handling achievement unlock events
 * 
 * @author Abdul Fornah
 * @version 4.0.0
 */
public interface AchievementListener {
    void onAchievementUnlocked(Achievement achievement);
}