package gameproject;

import java.util.*;
import java.util.stream.Collectors;

/**
 * QuestManager handles all quest-related functionality including branching storylines,
 * dynamic objectives, and quest tracking for The End The Beginning game.
 * 
 * @author The End The Beginning Team
 * @version 4.0.0
 */
public class QuestManager {
    
    // ===== QUEST ENUMS =====
    
    public enum QuestType {
        MAIN_STORY("Main Story"),
        SIDE_QUEST("Side Quest"),
        DAILY_CHALLENGE("Daily Challenge"),
        EXPLORATION("Exploration"),
        COMBAT_TRIAL("Combat Trial"),
        COLLECTION("Collection"),
        RESCUE_MISSION("Rescue Mission"),
        SURVIVAL("Survival"),
        PUZZLE("Puzzle"),
        BOSS_HUNT("Boss Hunt");
        
        private final String displayName;
        
        QuestType(String displayName) {
            this.displayName = displayName;
        }
        
        public String getDisplayName() {
            return displayName;
        }
    }
    
    public enum QuestStatus {
        NOT_STARTED("Not Started"),
        AVAILABLE("Available"),
        ACTIVE("Active"),
        IN_PROGRESS("In Progress"),
        COMPLETED("Completed"),
        FAILED("Failed"),
        TURNED_IN("Turned In"),
        LOCKED("Locked");
        
        private final String displayName;
        
        QuestStatus(String displayName) {
            this.displayName = displayName;
        }
        
        public String getDisplayName() {
            return displayName;
        }
    }
    
    public enum QuestDifficulty {
        TRIVIAL(1, "Trivial"),
        EASY(2, "Easy"),
        NORMAL(3, "Normal"),
        HARD(4, "Hard"),
        EXTREME(5, "Extreme"),
        LEGENDARY(10, "Legendary");
        
        private final int difficultyLevel;
        private final String displayName;
        
        QuestDifficulty(int difficultyLevel, String displayName) {
            this.difficultyLevel = difficultyLevel;
            this.displayName = displayName;
        }
        
        public int getDifficultyLevel() {
            return difficultyLevel;
        }
        
        public String getDisplayName() {
            return displayName;
        }
    }
    
    // ===== QUEST CLASSES =====
    
    public static class Quest {
        private final String questId;
        private final String title;
        private final String description;
        private final QuestType type;
        private final QuestDifficulty difficulty;
        private QuestStatus status;
        private final List<String> prerequisites;
        private final List<QuestObjective> objectives;
        private final QuestReward reward;
        private final Map<String, Object> questData;
        private final List<String> followUpQuests;
        private int completionProgress;
        private String currentNarrative;
        
        public Quest(String questId, String title, String description, QuestType type, 
                    QuestDifficulty difficulty, QuestReward reward) {
            this.questId = questId;
            this.title = title;
            this.description = description;
            this.type = type;
            this.difficulty = difficulty;
            this.status = QuestStatus.NOT_STARTED;
            this.prerequisites = new ArrayList<>();
            this.objectives = new ArrayList<>();
            this.reward = reward;
            this.questData = new HashMap<>();
            this.followUpQuests = new ArrayList<>();
            this.completionProgress = 0;
            this.currentNarrative = description;
        }
        
        // Getters and setters
        public String getQuestId() { return questId; }
        public String getTitle() { return title; }
        public String getDescription() { return description; }
        public QuestType getType() { return type; }
        public QuestDifficulty getDifficulty() { return difficulty; }
        public QuestStatus getStatus() { return status; }
        public void setStatus(QuestStatus status) { this.status = status; }
        public List<String> getPrerequisites() { return prerequisites; }
        public List<QuestObjective> getObjectives() { return objectives; }
        public QuestReward getReward() { return reward; }
        public Map<String, Object> getQuestData() { return questData; }
        public List<String> getFollowUpQuests() { return followUpQuests; }
        public int getCompletionProgress() { return completionProgress; }
        public void setCompletionProgress(int progress) { this.completionProgress = progress; }
        public String getCurrentNarrative() { return currentNarrative; }
        public void setCurrentNarrative(String narrative) { this.currentNarrative = narrative; }
        
        public void addPrerequisite(String questId) {
            prerequisites.add(questId);
        }
        
        public void addObjective(QuestObjective objective) {
            objectives.add(objective);
        }
        
        public void addFollowUpQuest(String questId) {
            followUpQuests.add(questId);
        }
        
        public boolean isCompleted() {
            return objectives.stream().allMatch(QuestObjective::isCompleted);
        }
        
        public int calculateTotalProgress() {
            if (objectives.isEmpty()) return 0;
            int completedObjectives = (int) objectives.stream().mapToInt(obj -> obj.isCompleted() ? 1 : 0).sum();
            return (completedObjectives * 100) / objectives.size();
        }
    }
    
    public static class QuestObjective {
        private final String objectiveId;
        private final String description;
        private final String targetType;
        private final String targetId;
        private final int requiredAmount;
        private int currentAmount;
        private boolean completed;
        private final Map<String, Object> objectiveData;
        
        public QuestObjective(String objectiveId, String description, String targetType, 
                            String targetId, int requiredAmount) {
            this.objectiveId = objectiveId;
            this.description = description;
            this.targetType = targetType;
            this.targetId = targetId;
            this.requiredAmount = requiredAmount;
            this.currentAmount = 0;
            this.completed = false;
            this.objectiveData = new HashMap<>();
        }
        
        // Getters and setters
        public String getObjectiveId() { return objectiveId; }
        public String getDescription() { return description; }
        public String getTargetType() { return targetType; }
        public String getTargetId() { return targetId; }
        public int getRequiredAmount() { return requiredAmount; }
        public int getCurrentAmount() { return currentAmount; }
        public void setCurrentAmount(int amount) { 
            this.currentAmount = Math.min(amount, requiredAmount);
            checkCompletion();
        }
        public boolean isCompleted() { return completed; }
        public Map<String, Object> getObjectiveData() { return objectiveData; }
        
        public void incrementProgress(int amount) {
            setCurrentAmount(currentAmount + amount);
        }
        
        private void checkCompletion() {
            completed = currentAmount >= requiredAmount;
        }
        
        public String getProgressText() {
            return currentAmount + "/" + requiredAmount;
        }
        
        public double getProgressPercentage() {
            return requiredAmount > 0 ? (double) currentAmount / requiredAmount : 0.0;
        }
    }
    
    public static class QuestReward {
        private final int experiencePoints;
        private final int goldReward;
        private final List<String> itemRewards;
        private final Map<String, Integer> statBonuses;
        private final String specialReward;
        
        public QuestReward(int experiencePoints, int goldReward) {
            this.experiencePoints = experiencePoints;
            this.goldReward = goldReward;
            this.itemRewards = new ArrayList<>();
            this.statBonuses = new HashMap<>();
            this.specialReward = null;
        }
        
        public QuestReward(int experiencePoints, int goldReward, String specialReward) {
            this.experiencePoints = experiencePoints;
            this.goldReward = goldReward;
            this.itemRewards = new ArrayList<>();
            this.statBonuses = new HashMap<>();
            this.specialReward = specialReward;
        }
        
        // Getters
        public int getExperiencePoints() { return experiencePoints; }
        public int getGoldReward() { return goldReward; }
        public List<String> getItemRewards() { return itemRewards; }
        public Map<String, Integer> getStatBonuses() { return statBonuses; }
        public String getSpecialReward() { return specialReward; }
        
        public void addItemReward(String itemId) {
            itemRewards.add(itemId);
        }
        
        public void addStatBonus(String statName, int bonus) {
            statBonuses.put(statName, bonus);
        }
    }
    
    // ===== MANAGER INSTANCE =====
    
    private static QuestManager instance;
    private final Map<String, Quest> allQuests;
    private final Map<String, Quest> activeQuests;
    private final Map<String, Quest> completedQuests;
    private final List<String> questChain;
    private String currentMainQuestId;
    private final Random questRandom;
    
    private QuestManager() {
        this.allQuests = new HashMap<>();
        this.activeQuests = new HashMap<>();
        this.completedQuests = new HashMap<>();
        this.questChain = new ArrayList<>();
        this.questRandom = new Random();
        initializeQuests();
    }
    
    public static QuestManager getInstance() {
        if (instance == null) {
            instance = new QuestManager();
        }
        return instance;
    }
    
    // ===== QUEST MANAGEMENT METHODS =====
    
    /**
     * Initialize all game quests with branching storylines
     */
    private void initializeQuests() {
        // Main Story Quests
        createMainStoryQuests();
        
        // Side Quests
        createSideQuests();
        
        // Daily Challenges
        createDailyChallenges();
        
        // Special Quests
        createSpecialQuests();
    }
    
    private void createMainStoryQuests() {
        // Chapter 1: The Escape Begins
        Quest escapeQuest = new Quest("main_001", "The Great Escape", 
            "You awaken in a dark dungeon. Find a way to escape before the guards return.",
            QuestType.MAIN_STORY, QuestDifficulty.EASY, 
            new QuestReward(100, 50, "Rusty Key"));
        
        escapeQuest.addObjective(new QuestObjective("obj_001", "Find a way out", "explore", "exit", 1));
        escapeQuest.addObjective(new QuestObjective("obj_002", "Avoid detection", "stealth", "guards", 3));
        escapeQuest.addFollowUpQuest("main_002");
        
        // Chapter 2: The Surface World
        Quest surfaceQuest = new Quest("main_002", "Breathing Free", 
            "You've escaped the dungeon, but the surface world holds new dangers and mysteries.",
            QuestType.MAIN_STORY, QuestDifficulty.NORMAL, 
            new QuestReward(200, 100));
        
        surfaceQuest.addPrerequisite("main_001");
        surfaceQuest.addObjective(new QuestObjective("obj_003", "Explore the forest", "explore", "forest", 1));
        surfaceQuest.addObjective(new QuestObjective("obj_004", "Find civilization", "discover", "town", 1));
        surfaceQuest.addFollowUpQuest("main_003a");
        surfaceQuest.addFollowUpQuest("main_003b");
        
        // Branching Chapter 3A: The Peaceful Path
        Quest peacefulQuest = new Quest("main_003a", "Diplomatic Solutions", 
            "You choose to negotiate with the local inhabitants rather than fight.",
            QuestType.MAIN_STORY, QuestDifficulty.NORMAL, 
            new QuestReward(150, 200, "Diplomatic Immunity"));
        
        peacefulQuest.addPrerequisite("main_002");
        peacefulQuest.addObjective(new QuestObjective("obj_005a", "Negotiate with village elder", "talk", "elder", 1));
        peacefulQuest.addObjective(new QuestObjective("obj_006a", "Complete peace offering", "collect", "peace_items", 3));
        
        // Branching Chapter 3B: The Combat Path
        Quest combatQuest = new Quest("main_003b", "Trial by Combat", 
            "You choose to prove your worth through strength and combat prowess.",
            QuestType.MAIN_STORY, QuestDifficulty.HARD, 
            new QuestReward(300, 150, "Warrior's Mark"));
        
        combatQuest.addPrerequisite("main_002");
        combatQuest.addObjective(new QuestObjective("obj_005b", "Defeat the arena champion", "combat", "champion", 1));
        combatQuest.addObjective(new QuestObjective("obj_006b", "Survive the trials", "survive", "arena_trials", 5));
        
        allQuests.put(escapeQuest.getQuestId(), escapeQuest);
        allQuests.put(surfaceQuest.getQuestId(), surfaceQuest);
        allQuests.put(peacefulQuest.getQuestId(), peacefulQuest);
        allQuests.put(combatQuest.getQuestId(), combatQuest);
        
        questChain.addAll(Arrays.asList("main_001", "main_002", "main_003a", "main_003b"));
        currentMainQuestId = "main_001";
    }
    
    private void createSideQuests() {
        // Exploration Side Quest
        Quest treasureHunt = new Quest("side_001", "Lost Treasure", 
            "Rumors speak of a hidden treasure somewhere in the ancient ruins.",
            QuestType.EXPLORATION, QuestDifficulty.NORMAL, 
            new QuestReward(80, 300));
        
        treasureHunt.addObjective(new QuestObjective("obj_s001", "Find ancient ruins", "explore", "ruins", 1));
        treasureHunt.addObjective(new QuestObjective("obj_s002", "Solve the puzzle", "puzzle", "treasure_puzzle", 1));
        treasureHunt.getReward().addItemReward("treasure_map");
        treasureHunt.getReward().addItemReward("golden_amulet");
        
        // Combat Side Quest
        Quest monsterHunt = new Quest("side_002", "Monster Extermination", 
            "The local village is plagued by dangerous monsters. Help clear them out.",
            QuestType.COMBAT_TRIAL, QuestDifficulty.HARD, 
            new QuestReward(120, 80));
        
        monsterHunt.addObjective(new QuestObjective("obj_s003", "Eliminate goblins", "kill", "goblin", 5));
        monsterHunt.addObjective(new QuestObjective("obj_s004", "Defeat the goblin chief", "kill", "goblin_chief", 1));
        monsterHunt.getReward().addStatBonus("attack", 5);
        monsterHunt.getReward().addItemReward("monster_trophy");
        
        // Collection Side Quest
        Quest herbGathering = new Quest("side_003", "Alchemist's Request", 
            "The village alchemist needs rare herbs for a powerful healing potion.",
            QuestType.COLLECTION, QuestDifficulty.EASY, 
            new QuestReward(60, 120));
        
        herbGathering.addObjective(new QuestObjective("obj_s005", "Collect moonflower petals", "collect", "moonflower", 3));
        herbGathering.addObjective(new QuestObjective("obj_s006", "Gather dragon root", "collect", "dragon_root", 2));
        herbGathering.addObjective(new QuestObjective("obj_s007", "Find crystal tears", "collect", "crystal_tears", 1));
        herbGathering.getReward().addItemReward("healing_potion");
        herbGathering.getReward().addItemReward("mana_potion");
        
        allQuests.put(treasureHunt.getQuestId(), treasureHunt);
        allQuests.put(monsterHunt.getQuestId(), monsterHunt);
        allQuests.put(herbGathering.getQuestId(), herbGathering);
    }
    
    private void createDailyChallenges() {
        // Daily Combat Challenge
        Quest dailyCombat = new Quest("daily_001", "Daily Combat Trial", 
            "Test your combat skills against randomly selected opponents.",
            QuestType.DAILY_CHALLENGE, QuestDifficulty.NORMAL, 
            new QuestReward(50, 25));
        
        dailyCombat.addObjective(new QuestObjective("obj_d001", "Win battles", "combat_wins", "any", 3));
        dailyCombat.getReward().addStatBonus("experience", 25);
        
        // Daily Exploration Challenge
        Quest dailyExplore = new Quest("daily_002", "Daily Explorer", 
            "Discover new areas and hidden secrets in the world.",
            QuestType.DAILY_CHALLENGE, QuestDifficulty.EASY, 
            new QuestReward(40, 30));
        
        dailyExplore.addObjective(new QuestObjective("obj_d002", "Explore new areas", "explore", "new_areas", 2));
        dailyExplore.getReward().addItemReward("explorer_token");
        
        allQuests.put(dailyCombat.getQuestId(), dailyCombat);
        allQuests.put(dailyExplore.getQuestId(), dailyExplore);
    }
    
    private void createSpecialQuests() {
        // Boss Hunt
        Quest dragonSlayer = new Quest("special_001", "The Ancient Dragon", 
            "An ancient dragon has awakened and threatens the entire realm. Only the bravest dare to face it.",
            QuestType.BOSS_HUNT, QuestDifficulty.LEGENDARY, 
            new QuestReward(1000, 500, "Dragon Slayer Title"));
        
        dragonSlayer.addPrerequisite("main_003a");
        dragonSlayer.addPrerequisite("main_003b");
        dragonSlayer.addObjective(new QuestObjective("obj_sp001", "Locate the dragon's lair", "explore", "dragon_lair", 1));
        dragonSlayer.addObjective(new QuestObjective("obj_sp002", "Defeat the Ancient Dragon", "kill", "ancient_dragon", 1));
        dragonSlayer.getReward().addItemReward("dragon_scale_armor");
        dragonSlayer.getReward().addItemReward("dragonbane_sword");
        dragonSlayer.getReward().addStatBonus("all_stats", 10);
        
        allQuests.put(dragonSlayer.getQuestId(), dragonSlayer);
    }
    
    // ===== QUEST OPERATION METHODS =====
    
    /**
     * Start a quest if prerequisites are met
     */
    public boolean startQuest(String questId) {
        Quest quest = allQuests.get(questId);
        if (quest == null) return false;
        
        // Check prerequisites
        for (String prereqId : quest.getPrerequisites()) {
            if (!isQuestCompleted(prereqId)) {
                return false;
            }
        }
        
        quest.setStatus(QuestStatus.ACTIVE);
        activeQuests.put(questId, quest);
        
        return true;
    }
    
    /**
     * Update quest progress based on game actions
     */
    public void updateQuestProgress(String actionType, String targetId, int amount) {
        for (Quest quest : activeQuests.values()) {
            for (QuestObjective objective : quest.getObjectives()) {
                if (objective.getTargetType().equals(actionType) && 
                    (objective.getTargetId().equals(targetId) || objective.getTargetId().equals("any"))) {
                    objective.incrementProgress(amount);
                    
                    if (objective.isCompleted()) {
                        checkQuestCompletion(quest);
                    }
                }
            }
        }
    }
    
    /**
     * Check if a quest is completed and handle completion
     */
    private void checkQuestCompletion(Quest quest) {
        if (quest.isCompleted() && quest.getStatus() == QuestStatus.ACTIVE) {
            completeQuest(quest.getQuestId());
        }
    }
    
    /**
     * Complete a quest and give rewards
     */
    public boolean completeQuest(String questId) {
        Quest quest = activeQuests.get(questId);
        if (quest == null || !quest.isCompleted()) return false;
        
        quest.setStatus(QuestStatus.COMPLETED);
        activeQuests.remove(questId);
        completedQuests.put(questId, quest);
        
        // Award rewards
        giveQuestRewards(quest);
        
        // Unlock follow-up quests
        for (String followUpId : quest.getFollowUpQuests()) {
            Quest followUp = allQuests.get(followUpId);
            if (followUp != null && followUp.getStatus() == QuestStatus.NOT_STARTED) {
                followUp.setStatus(QuestStatus.AVAILABLE);
            }
        }
        
        return true;
    }
    
    /**
     * Give quest rewards to the player
     */
    private void giveQuestRewards(Quest quest) {
        QuestReward reward = quest.getReward();
        
        // Award experience and gold
        // Note: This would integrate with the Player class
        System.out.println("Quest completed: " + quest.getTitle());
        System.out.println("Rewards: " + reward.getExperiencePoints() + " XP, " + reward.getGoldReward() + " gold");
        
        // Award items
        for (String itemId : reward.getItemRewards()) {
            System.out.println("Item reward: " + itemId);
        }
        
        // Award stat bonuses
        for (Map.Entry<String, Integer> bonus : reward.getStatBonuses().entrySet()) {
            System.out.println("Stat bonus: +" + bonus.getValue() + " " + bonus.getKey());
        }
        
        // Special rewards
        if (reward.getSpecialReward() != null) {
            System.out.println("Special reward: " + reward.getSpecialReward());
        }
    }
    
    // ===== QUERY METHODS =====
    
    public Quest getQuest(String questId) {
        return allQuests.get(questId);
    }
    
    public boolean isQuestCompleted(String questId) {
        return completedQuests.containsKey(questId);
    }
    
    public boolean isQuestActive(String questId) {
        return activeQuests.containsKey(questId);
    }
    
    public List<Quest> getActiveQuests() {
        return new ArrayList<>(activeQuests.values());
    }
    
    public List<Quest> getAvailableQuests() {
        return allQuests.values().stream()
            .filter(quest -> quest.getStatus() == QuestStatus.AVAILABLE)
            .collect(Collectors.toList());
    }
    
    public List<Quest> getCompletedQuests() {
        return new ArrayList<>(completedQuests.values());
    }
    
    public List<Quest> getQuestsByType(QuestType type) {
        return allQuests.values().stream()
            .filter(quest -> quest.getType() == type)
            .collect(Collectors.toList());
    }
    
    public Quest getCurrentMainQuest() {
        return allQuests.get(currentMainQuestId);
    }
    
    public String getQuestSummary() {
        StringBuilder summary = new StringBuilder();
        summary.append("=== QUEST SUMMARY ===\n");
        summary.append("Active Quests: ").append(activeQuests.size()).append("\n");
        summary.append("Completed Quests: ").append(completedQuests.size()).append("\n");
        summary.append("Total Quests: ").append(allQuests.size()).append("\n");
        
        if (!activeQuests.isEmpty()) {
            summary.append("\nCurrent Active Quests:\n");
            for (Quest quest : activeQuests.values()) {
                summary.append("- ").append(quest.getTitle())
                       .append(" (").append(quest.calculateTotalProgress()).append("% complete)\n");
            }
        }
        
        return summary.toString();
    }
    
    /**
     * Generate dynamic quest based on player's current state
     */
    public Quest generateDynamicQuest(main.model.Player player) {
        // This would use player's level, location, and preferences to create appropriate quests
        QuestType[] dynamicTypes = {QuestType.COMBAT_TRIAL, QuestType.EXPLORATION, QuestType.COLLECTION};
        QuestType selectedType = dynamicTypes[questRandom.nextInt(dynamicTypes.length)];
        
        String questId = "dynamic_" + System.currentTimeMillis();
        String title = "Dynamic " + selectedType.getDisplayName();
        String description = "A dynamically generated quest tailored to your current progress.";
        
        QuestDifficulty difficulty = player.getLevel() < 5 ? QuestDifficulty.EASY :
                                   player.getLevel() < 15 ? QuestDifficulty.NORMAL :
                                   player.getLevel() < 25 ? QuestDifficulty.HARD : QuestDifficulty.EXTREME;
        
        int baseReward = difficulty.getDifficultyLevel() * 20;
        QuestReward reward = new QuestReward(baseReward, baseReward / 2);
        
        Quest dynamicQuest = new Quest(questId, title, description, selectedType, difficulty, reward);
        
        // Add appropriate objectives based on type
        switch (selectedType) {
            case COMBAT_TRIAL:
                dynamicQuest.addObjective(new QuestObjective("dyn_combat", "Defeat enemies", "kill", "any", 3 + player.getLevel() / 5));
                break;
            case EXPLORATION:
                dynamicQuest.addObjective(new QuestObjective("dyn_explore", "Explore new areas", "explore", "new_areas", 2));
                break;
            case COLLECTION:
                dynamicQuest.addObjective(new QuestObjective("dyn_collect", "Collect items", "collect", "any", 5));
                break;
            case MAIN_STORY:
            case SIDE_QUEST:
            case DAILY_CHALLENGE:
            case RESCUE_MISSION:
            case SURVIVAL:
            case PUZZLE:
            case BOSS_HUNT:
            default:
                dynamicQuest.addObjective(new QuestObjective("dyn_general", "Complete objective", "general", "any", 1));
                break;
        }
        
        allQuests.put(questId, dynamicQuest);
        return dynamicQuest;
    }
}