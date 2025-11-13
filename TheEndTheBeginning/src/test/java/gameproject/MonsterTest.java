package gameproject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test class for Monster functionality.
 * Tests monster creation, stats, and combat interactions.
 */
class MonsterTest {

    private Monster goblin;
    private Monster orc;
    private Monster boss;

    @BeforeEach
    void setUp() {
        // Create different monster types for testing
        goblin = new Monster("Goblin Warrior", Monster.MonsterType.BASIC, Monster.MonsterFamily.GOBLIN, 
                           50, 15, 5, 10, 0.8, new String[]{"Quick Strike"}, 0.2, 1.3, "Aggressive");
        orc = new Monster("Orc Berserker", Monster.MonsterType.ELITE, Monster.MonsterFamily.BEAST, 
                        120, 25, 10, 12, 0.75, new String[]{"Rage", "Slam"}, 0.25, 1.5, "Berserker");
        boss = new Monster("Ancient Dragon", Monster.MonsterType.BOSS, Monster.MonsterFamily.DRAGON, 
                         500, 60, 25, 20, 0.9, new String[]{"Dragon Breath", "Wing Attack"}, 0.4, 2.0, "Strategic");
    }

    @Test
    void testMonsterCreation() {
        assertNotNull(goblin, "Goblin should be created");
        assertNotNull(orc, "Orc should be created");
        assertNotNull(boss, "Boss should be created");
        
        assertEquals(Monster.MonsterType.BASIC, goblin.getType(), "Goblin type should match");
        assertEquals(Monster.MonsterType.ELITE, orc.getType(), "Orc type should match");
        assertEquals(Monster.MonsterType.BOSS, boss.getType(), "Boss type should match");
    }

    @Test
    void testMonsterHealth() {
        assertTrue(goblin.getHealth() > 0, "Goblin should have positive health");
        assertTrue(orc.getHealth() > 0, "Orc should have positive health");
        assertTrue(boss.getHealth() > 0, "Boss should have positive health");
        
        // Boss should have more health than basic monsters
        assertTrue(boss.getHealth() > goblin.getHealth(), "Boss should have more health than goblin");
        assertTrue(boss.getHealth() > orc.getHealth(), "Boss should have more health than orc");
    }

    @Test
    void testMonsterAttack() {
        int goblinAttack = goblin.getAttack();
        int orcAttack = orc.getAttack();
        int bossAttack = boss.getAttack();
        
        assertTrue(goblinAttack > 0, "Goblin should have positive attack");
        assertTrue(orcAttack > 0, "Orc should have positive attack");
        assertTrue(bossAttack > 0, "Boss should have positive attack");
        
        // Higher tier monsters should generally have higher attack
        assertTrue(bossAttack >= goblinAttack, "Boss attack should be >= goblin attack");
        assertTrue(orcAttack >= goblinAttack, "Elite attack should be >= basic attack");
    }

    @Test
    void testMonsterDefense() {
        int goblinDefense = goblin.getDefense();
        int orcDefense = orc.getDefense();
        int bossDefense = boss.getDefense();
        
        assertTrue(goblinDefense >= 0, "Goblin should have non-negative defense");
        assertTrue(orcDefense >= 0, "Orc should have non-negative defense");
        assertTrue(bossDefense >= 0, "Boss should have non-negative defense");
    }

    @Test
    void testMonsterDamage() {
        int initialHealth = goblin.getHealth();
        int damage = 10;
        
        goblin.takeDamage(damage);
        assertEquals(Math.max(0, initialHealth - damage), goblin.getHealth(), "Health should decrease by damage amount");
        assertTrue(goblin.isAlive(), "Goblin should still be alive");
    }

    @Test
    void testMonsterDeath() {
        int lethalDamage = goblin.getHealth() + 10;
        goblin.takeDamage(lethalDamage);
        
        assertEquals(0, goblin.getHealth(), "Health should be 0");
        assertFalse(goblin.isAlive(), "Monster should be dead");
    }

    @Test
    void testMonsterFamily() {
        assertEquals(Monster.MonsterFamily.GOBLIN, goblin.getFamily(), "Goblin family should be GOBLIN");
        assertEquals(Monster.MonsterFamily.BEAST, orc.getFamily(), "Orc family should be BEAST");
        assertEquals(Monster.MonsterFamily.DRAGON, boss.getFamily(), "Boss family should be DRAGON");
    }

    @Test
    void testMonsterName() {
        assertNotNull(goblin.getName(), "Goblin should have a name");
        assertNotNull(orc.getName(), "Orc should have a name");
        assertNotNull(boss.getName(), "Boss should have a name");
        
        assertFalse(goblin.getName().isEmpty(), "Goblin name should not be empty");
        assertFalse(orc.getName().isEmpty(), "Orc name should not be empty");
        assertFalse(boss.getName().isEmpty(), "Boss name should not be empty");
    }

    @Test
    void testNegativeDamage() {
        int initialHealth = goblin.getHealth();
        goblin.takeDamage(-5); // Negative damage
        
        assertEquals(initialHealth, goblin.getHealth(), "Negative damage should not heal monster");
    }

    @Test
    void testZeroDamage() {
        int initialHealth = goblin.getHealth();
        goblin.takeDamage(0);
        
        assertEquals(initialHealth, goblin.getHealth(), "Zero damage should not change health");
    }

    @Test
    void testMonsterAgility() {
        assertTrue(goblin.getAgility() >= 0, "Goblin agility should be non-negative");
        assertTrue(orc.getAgility() >= 0, "Orc agility should be non-negative");
        assertTrue(boss.getAgility() >= 0, "Boss agility should be non-negative");
    }

    @Test
    void testMonsterAccuracy() {
        assertTrue(goblin.getAccuracy() > 0, "Goblin accuracy should be positive");
        assertTrue(orc.getAccuracy() > 0, "Orc accuracy should be positive");
        assertTrue(boss.getAccuracy() > 0, "Boss accuracy should be positive");
        
        assertTrue(goblin.getAccuracy() <= 1.0, "Accuracy should not exceed 100%");
        assertTrue(orc.getAccuracy() <= 1.0, "Accuracy should not exceed 100%");
        assertTrue(boss.getAccuracy() <= 1.0, "Accuracy should not exceed 100%");
    }

    @Test
    void testMonsterBehavior() {
        assertNotNull(goblin.getBehavior(), "Goblin should have a behavior");
        assertNotNull(orc.getBehavior(), "Orc should have a behavior");
        assertNotNull(boss.getBehavior(), "Boss should have a behavior");
    }

    @Test
    void testMonsterSpecialAbilities() {
        assertNotNull(goblin.getSpecialAbility(), "Goblin should have a special ability");
        assertNotNull(orc.getSpecialAbility(), "Orc should have a special ability");
        assertNotNull(boss.getSpecialAbility(), "Boss should have a special ability");
    }
}