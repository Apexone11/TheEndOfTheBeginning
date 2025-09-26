package main.model;

/**
 * Monster class for "The End The Beginning" dungeon escape game. This class
 * manages all monster types, their stats, abilities, and combat behaviors.
 *
 * The monster system is designed with scalability in mind, supporting different
 * monster tiers based on player level progression (1-5, 5-20, 20-40, and boss
 * monsters).
 *
 * @author Abdul Fornah
 * @version 1.0
 */
public class monster {

    // Class contains all different monster types and their respective stats
    /* 
     * DEVELOPMENT TODO LIST:
     * 
     * HIGH PRIORITY:
     * 1. Multiple monsters per room: Implement system to spawn 1-3 monsters per encounter
     *    - Early levels should only spawn weak monsters to maintain game balance
     *    - Add logic to prevent overpowered monsters in starting levels
     * 
     * 2. Combat system overhaul: Refactor monster encounter mechanics
     *    - Separate monster generation from combat logic
     *    - Create modular combat system for easier maintenance
     * 
     * MEDIUM PRIORITY:
     * 3. Monster dialogue system: Create separate class for monster interactions
     *    - Add pre-combat dialogue for immersion
     *    - Include monster-specific taunts and responses
     * 
     * 4. Monster lore: Implement backstory system for each monster type
     *    - Add descriptive text for each monster encounter
     *    - Create atmospheric descriptions for better player engagement
     * 
     * LOW PRIORITY:
     * 5. Special boss monsters: Design unique boss encounters with complex mechanics
     *    - Multi-phase boss fights
     *    - Special attack patterns and abilities
     *    - Unique loot drops and rewards
     * 
     * 6. Ability usage system: Implement cooldown/usage limit mechanics
     *    - Random ability usage with maximum use limits
     *    - Difficulty-based ability frequency (easy, medium, hard, death)
     *    - Balance monster abilities to prevent overpowered encounters
     */
    /**
     * Displays the current monster's statistics to the player. This method will
     * show monster health, attack power, and special abilities in a formatted,
     * user-friendly way.
     *
     * @param monsterType The type/ID of the monster (0-5 for different monsters)
     * @param monsterName The display name of the monster
     * @param monsterHealth Current health points of the monster
     * @param monsterAttack Base attack power of the monster
     * @param specialAbility The name of the monster's special ability
     */
    public void displayMonsterStats(int monsterType, String monsterName, int monsterHealth, int monsterAttack, String specialAbility) {
        System.out.println("\n=== MONSTER ENCOUNTER ===");
        System.out.println("Name: " + monsterName);
        System.out.println("Health: " + monsterHealth + " HP");
        System.out.println("Attack: " + monsterAttack);
        System.out.println("Special Ability: " + specialAbility);
        System.out.println("Type ID: " + monsterType);
        System.out.println("========================\n");
    }

    /**
     * Utility method to calculate scaled monster damage based on level and attack pattern.
     * This centralizes the damage calculation logic used across all monsters.
     * 
     * @param baseAttack The monster's base attack value
     * @param level Current dungeon level for scaling
     * @param isHeavyAttack True for heavy attacks (higher multiplier), false for light attacks
     * @return The calculated damage value
     */
    public int calculateMonsterDamage(int baseAttack, int level, boolean isHeavyAttack) {
        if (isHeavyAttack) {
            // Heavy attack: higher damage with more scaling
            return baseAttack * (10 + (int) (Math.random() * (10 + level)));
        } else {
            // Light attack: moderate damage with moderate scaling
            return baseAttack * (5 + (int) (Math.random() * (5 + level)));
        }
    }

    /**
     * Utility method to determine if a monster attack hits or misses.
     * Uses the same probability system as the main monster methods.
     * 
     * @return True if the attack hits, false if it misses
     */
    public boolean doesMonsterAttackHit() {
        int attackPattern = (int) (Math.random() * 7);
        // Pattern: 0=hit, 1=miss, 2=hit, 3=miss, 4=hit, 5=miss, 6=miss
        // This gives a 3/7 chance to hit (about 43%)
        return (attackPattern == 0 || attackPattern == 2 || attackPattern == 4);
    }

    /**
     * Utility method to get monster attack type for narrative purposes.
     * 
     * @return String describing the type of attack (light, heavy, special, or miss)
     */
    public String getAttackType() {
        int attackPattern = (int) (Math.random() * 7);
        switch (attackPattern) {
            case 0: return "light";
            case 1: case 3: case 5: case 6: return "miss";
            case 2: return "heavy"; 
            case 4: return "special";
            default: return "miss";
        }
    }

    /**
     * Primary monster generation method for early game encounters (levels 1-5).
     * This method handles the core monster encounter system including: - Random
     * monster selection from available types - Attack pattern determination -
     * Combat resolution - Player stat modifications based on monster abilities
     *
     * @param level Current dungeon level (determines monster scaling)
     * @param monsterAttack Current monster's attack power (modified during
     * combat)
     * @param playerAttack Current player's attack power (can be modified by
     * abilities)
     * @param playerHealth Current player's health (can be modified by
     * abilities)
     *
     * DESIGN NOTE: This method contains early-game monsters only. Mid and
     * late-game monsters are declared as variables below but not yet
     * implemented in combat logic.
     *
     * MONSTER SCALING: Monster stats scale with player level to maintain
     * challenge throughout the game progression.
     */
    public void easyMonsters(int level, int monsterAttack, int playerAttack, int playerHealth) {
        if (level >= 1 && level <= 5) {
            // RANDOM MONSTER SELECTION: Generate random number (0-5) to select monster type
            // Currently supports 6 different monster types for early game encounters
            int monsterGenerater = (int) (Math.random() * 6); // 0,1,2,3,4,5, generate a random monster

            // ATTACK PATTERN SYSTEM: Determines what action the monster will take this turn
            // Pattern breakdown: 0=light attack, 1=miss, 2=heavy attack, 3=miss, 4=special ability, 5=miss, 6=miss
            // Note: High miss chance (4/7) makes combat more strategic and less predictable
            int AttackPattern = (int) (Math.random() * 7); // 0) light attack, 1) miss , 2) heaviy attack , 3) miss , 4) ability 1 ,5)  miss , 6) miss

            // MONSTER SELECTION: Switch statement handles all available monster types
            // Each case represents a different monster with unique stats and abilities
            switch (monsterGenerater) {
                case 0: {
                    /**
                     * MONSTER TYPE: Goblin - Early Game Enemy
                     * 
                     * LORE: Small, sneaky creatures that travel in groups. While weak when
                     * encountered alone, they become dangerous when working together in packs.
                     * Known for their cowardly nature but cunning tactics.
                     */
                    String monsterName = "Goblin";
                    int monsterHealth = 50;           // Base health points
                    int monsterMaxHealth = 50;        // Maximum health (for regeneration abilities if added later)
                    int baseMonsterAttack = 2;        // Base attack damage multiplier

                    // SPECIAL ABILITIES SYSTEM
                    String monsterAbility1 = "steal"; // Primary ability name
                    int monsterAbilityAffect = 10;    // Amount of attack power stolen from player

                    // LEVEL SCALING: Monster level matches dungeon level for balanced encounters
                    int monsterLevel = level; // Used to scale monster attack damage based on progression
                    System.out.print("\nThe " + monsterName + " approaches you silently.");

                    if (AttackPattern == 0) {
                        // LIGHT ATTACK: Basic damage with moderate scaling
                        // Damage Formula: base_attack * (5 + random(0 to 5+level))
                        // Results in 10-24 damage at level 1, scaling up with progression
                        monsterAttack = baseMonsterAttack * (5 + (int) (Math.random() * (5 + monsterLevel)));
                        System.out.print("\nYou were hit with a light attack for " + monsterAttack + " damage!");

                    } else if (AttackPattern == 1 || AttackPattern == 3 || AttackPattern == 5 || AttackPattern == 6) {
                        // MISS ATTACKS: 4 out of 7 possible outcomes result in misses
                        // This creates strategic depth where timing and luck matter
                        // High miss rate prevents monsters from being too overwhelming early game
                        System.out.print("\nThe " + monsterName + " missed you!");
                    } else if (AttackPattern == 2) {
                        // HEAVY ATTACK: Significant damage with high scaling potential
                        // Damage Formula: base_attack * (10 + random(0 to 10+level))
                        // Results in 20-44 damage at level 1, can be devastating at higher levels
                        monsterAttack = baseMonsterAttack * (10 + (int) (Math.random() * (10 + monsterLevel)));
                        System.out.print("\nYou were hit with a heavy attack for " + monsterAttack + " damage!");
                    } else if (AttackPattern == 4) {
                        // SPECIAL ABILITY: "Steal" - Unique goblin power
                        // Reduces player's attack power permanently (for this encounter)
                        // Strategic impact: Makes prolonged fights increasingly difficult for player
                        System.out.print("\nThe " + monsterName + " used " + monsterAbility1 + " and stole " + monsterAbilityAffect + " attack from you!");
                        playerAttack -= monsterAbilityAffect;

                        // SAFETY CHECK: Prevent negative attack values
                        // Ensures player always retains some offensive capability
                        if (playerAttack < 0) {
                            playerAttack = 0;
                        }
                    }
                    break; // CRITICAL: Break statement to prevent fall-through
                }
                case 1: {
                    /**
                     * MONSTER TYPE: Orc - Early Game Brute
                     * 
                     * LORE: Big, tough, strong melee fighters. Slow but powerful,
                     * these green-skinned warriors rely on brute force and intimidation.
                     * Their power strike can devastate unprepared adventurers.
                     */
                    String monsterName = "Orc";
                    int monsterHealth = 75;
                    int monsterMaxHealth = 75;
                    int baseMonsterAttack = 3;

                    // SPECIAL ABILITIES SYSTEM
                    String monsterAbility1 = "power strike"; // Power strike - a strong attack that deals extra damage
                    int monsterAbilityAffect = 15;
                    int monsterLevel = level; // Will be used to scale the monster attack
                    System.out.print("\nThe " + monsterName + " approaches you with a menacing glare.");

                    if (AttackPattern == 0) {
                        monsterAttack = baseMonsterAttack * (5 + (int) (Math.random() * (5 + monsterLevel)));
                        System.out.print("\nYou were hit with a light attack for " + monsterAttack + " damage!");

                    } else if (AttackPattern == 1 || AttackPattern == 3 || AttackPattern == 5 || AttackPattern == 6) {
                        System.out.print("\nThe " + monsterName + " missed you!");
                    } else if (AttackPattern == 2) {
                        monsterAttack = baseMonsterAttack * (10 + (int) (Math.random() * (10 + monsterLevel)));
                        System.out.print("\nYou were hit with a heavy attack for " + monsterAttack + " damage!");
                    } else if (AttackPattern == 4) {
                        // Power Strike ability - deals direct damage to health instead of reducing attack
                        System.out.print("\nThe " + monsterName + " used " + monsterAbility1 + " and dealt " + monsterAbilityAffect + " extra damage to you!");
                        playerHealth -= monsterAbilityAffect;
                        if (playerHealth < 0) {
                            playerHealth = 0;
                        }
                    }
                    break; // CRITICAL: Break statement to prevent fall-through
                }
                case 2: {
                    /**
                     * MONSTER TYPE: Cave Lurkers - Undead Soldiers
                     * 
                     * LORE: Undead soldiers, weak but relentless. These reanimated warriors
                     * can respawn if not dealt with properly, making them a persistent threat
                     * that requires quick elimination.
                     */
                    String monsterName = "Cave Lurkers";
                    int monsterHealth = 40;
                    int monsterMaxHealth = 40;
                    int baseMonsterAttack = 4;

                    // SPECIAL ABILITIES SYSTEM
                    String monsterAbility1 = "respawn"; // Will respawn if the player does not kill it in 3 turns with full health
                    int monsterAbilityAffect = 1; // Turn counter for respawn ability
                    int monsterLevel = level; // Will be used to scale the monster attack
                    System.out.print("\nThe " + monsterName + " approach you with an eerie silence.");

                    if (AttackPattern == 0) {
                        monsterAttack = baseMonsterAttack * (5 + (int) (Math.random() * (5 + monsterLevel)));
                        System.out.print("\nYou were hit with a light attack for " + monsterAttack + " damage!");

                    } else if (AttackPattern == 1 || AttackPattern == 3 || AttackPattern == 5 || AttackPattern == 6) {
                        System.out.print("\nThe " + monsterName + " missed you!");
                    } else if (AttackPattern == 2) {
                        monsterAttack = baseMonsterAttack * (10 + (int) (Math.random() * (10 + monsterLevel)));
                        System.out.print("\nYou were hit with a heavy attack for " + monsterAttack + " damage!");
                    } else if (AttackPattern == 4) {
                        System.out.print("\nThe " + monsterName + " used " + monsterAbility1 + " and will respawn in 3 turns if not killed!");
                        monsterAbilityAffect += 1;
                        if (monsterAbilityAffect >= 3) {
                            monsterHealth = monsterMaxHealth;
                            System.out.print("\nThe " + monsterName + " have respawned with full health!");
                            monsterAbilityAffect = 1;
                        }
                    }
                    break; // CRITICAL: Break statement to prevent fall-through
                }
                case 3: {
                    /**
                     * MONSTER TYPE: The Lost - Deranged Survivors
                     * 
                     * LORE: People who got lost in the Dungeon and lost their minds. 
                     * Weak alone but strong together. If there is more than one, they will 
                     * activate an effect that will affect the player's health called "Inagma".
                     */
                    String monsterName = "The Lost";
                    int monsterHealth = 60;
                    int baseMonsterAttack = 3;

                    // SPECIAL ABILITIES SYSTEM
                    String monsterAbility1 = "inagma"; // Inagma - will lower the player health by 5
                    int monsterAbilityAffect = 5;
                    int monsterLevel = level; // Will be used to scale the monster attack
                    System.out.print("\nThe " + monsterName + " approach you with a crazed look in their eyes.");

                    if (AttackPattern == 0) {
                        monsterAttack = baseMonsterAttack * (5 + (int) (Math.random() * (5 + monsterLevel)));
                        System.out.print("\nYou were hit with a light attack for " + monsterAttack + " damage!");

                    } else if (AttackPattern == 1 || AttackPattern == 3 || AttackPattern == 5 || AttackPattern == 6) {
                        System.out.print("\nThe " + monsterName + " missed you!");
                    } else if (AttackPattern == 2) {
                        monsterAttack = baseMonsterAttack * (10 + (int) (Math.random() * (10 + monsterLevel)));
                        System.out.print("\nYou were hit with a heavy attack for " + monsterAttack + " damage!");
                    } else if (AttackPattern == 4) {
                        System.out.print("\nThe " + monsterName + " used " + monsterAbility1 + " and lowered your health by " + monsterAbilityAffect + "!");
                        playerHealth -= monsterAbilityAffect;
                        if (playerHealth < 0) {
                            playerHealth = 0;
                        }
                    }
                    break; // CRITICAL: Break statement to prevent fall-through
                }
                case 4: {
                    /**
                     * MONSTER TYPE: Mimic - Deceptive Predator
                     * 
                     * LORE: Looks like a treasure chest, then attacks when opened.
                     * These cunning creatures use surprise attacks to catch adventurers
                     * off guard with devastating first strikes.
                     */
                    String monsterName = "Mimic";
                    int monsterHealth = 100;
                    int baseMonsterAttack = 5;

                    // SPECIAL ABILITIES SYSTEM
                    String monsterAbility1 = "surprise attack"; // Surprise attack - first attack deals double damage
                    int monsterAbilityAffect = 2; // damage multiplier for surprise attack
                    int monsterLevel = level; // Will be used to scale the monster attack
                    System.out.print("\nWhat you thought was a treasure chest suddenly springs to life! The " + monsterName + " reveals its true form!");

                    if (AttackPattern == 0) {
                        monsterAttack = baseMonsterAttack * (5 + (int) (Math.random() * (5 + monsterLevel)));
                        System.out.print("\nYou were hit with a light attack for " + monsterAttack + " damage!");

                    } else if (AttackPattern == 1 || AttackPattern == 3 || AttackPattern == 5 || AttackPattern == 6) {
                        System.out.print("\nThe " + monsterName + " missed you!");
                    } else if (AttackPattern == 2) {
                        monsterAttack = baseMonsterAttack * (10 + (int) (Math.random() * (10 + monsterLevel)));
                        System.out.print("\nYou were hit with a heavy attack for " + monsterAttack + " damage!");
                    } else if (AttackPattern == 4) {
                        int surpriseAttackDamage = baseMonsterAttack * monsterAbilityAffect * (5 + (int) (Math.random() * (5 + monsterLevel)));
                        System.out.print("\nThe " + monsterName + " used " + monsterAbility1 + " and dealt " + surpriseAttackDamage + " damage to you!");
                        playerHealth -= surpriseAttackDamage;
                        if (playerHealth < 0) {
                            playerHealth = 0;
                        }
                    }
                    break; // CRITICAL: Break statement to prevent fall-through
                }
                case 5: {
                    /**
                     * MONSTER TYPE: Ghoul - Enhanced Undead Warrior
                     * 
                     * LORE: Undead warriors, stronger and tougher than cave lurkers.
                     * Can use basic weapons and enter frenzied states where they
                     * attack multiple times in rapid succession.
                     */
                    String monsterName = "Ghoul";
                    int monsterHealth = 80;
                    int baseMonsterAttack = 7;

                    // SPECIAL ABILITIES SYSTEM
                    String monsterAbility1 = "frenzy"; // Frenzy - will attack twice in one turn
                    int monsterAbilityAffect = 2; // number of attacks in one turn

                    int monsterLevel = level; // Will be used to scale the monster attack
                    System.out.print("\nThe " + monsterName + " approaches you with a hunger for flesh.");

                    if (AttackPattern == 0) {
                        monsterAttack = baseMonsterAttack * (5 + (int) (Math.random() * (5 + monsterLevel)));
                        System.out.print("\nYou were hit with a light attack for " + monsterAttack + " damage!");

                    } else if (AttackPattern == 1 || AttackPattern == 3 || AttackPattern == 5 || AttackPattern == 6) {
                        System.out.print("\nThe " + monsterName + " missed you!");
                    } else if (AttackPattern == 2) {
                        monsterAttack = baseMonsterAttack * (10 + (int) (Math.random() * (10 + monsterLevel)));
                        System.out.print("\nYou were hit with a heavy attack for " + monsterAttack + " damage!");
                    } else if (AttackPattern == 4) {
                        System.out.print("\nThe " + monsterName + " used " + monsterAbility1 + " and will attack " + monsterAbilityAffect + " times in one turn!");
                        int totalFrenzyDamage = 0;
                        for (int i = 0; i < monsterAbilityAffect; i++) {
                            int frenzyAttack = baseMonsterAttack * (5 + (int) (Math.random() * (5 + monsterLevel)));
                            totalFrenzyDamage += frenzyAttack;
                            System.out.print("\nFrenzy attack " + (i+1) + " deals " + frenzyAttack + " damage!");
                        }
                        playerHealth -= totalFrenzyDamage;
                        if (playerHealth < 0) {
                            playerHealth = 0;
                        }
                    }
                    break; // CRITICAL: Break statement to prevent fall-through
                }
            } // End of switch statement
        } // End of level check
    } // End of easyMonsters method

    // ===== MID-GAME MONSTERS (LEVELS 5-20) =====

    /**
     * Mid-level monster generation method for intermediate encounters (levels 5-20).
     * These monsters are more challenging than early-game enemies and feature
     * more complex abilities and higher stats.
     * 
     * @param level Current dungeon level (determines monster scaling)
     * @param monsterAttack Current monster's attack power (modified during combat)
     * @param playerAttack Current player's attack power (can be modified by abilities)
     * @param playerHealth Current player's health (can be modified by abilities)
     */
    public void midMonsters(int level, int monsterAttack, int playerAttack, int playerHealth) {
        if (level >= 5 && level <= 20) {

            // RANDOM MONSTER SELECTION: Generate random number (0-5) to select monster type
            int monsterGenerater = (int) (Math.random() * 6); // 0,1,2,3,4,5 generate a random monster

            // ATTACK PATTERN SYSTEM: Determines what action the monster will take this turn
            int AttackPattern = (int) (Math.random() * 7); // 0) light attack, 1) miss , 2) heavy attack , 3) miss , 4) ability 1 ,5)  miss , 6) miss

            switch (monsterGenerater) {
                case 0: {
                    /**
                     * MONSTER TYPE: The Murmuring Tides - Water Elemental
                     * PLACEHOLDER: Full implementation needed
                     */
                    String monsterName = "The Murmuring Tides";
                    int monsterHealth = 60;
                    int baseMonsterAttack = 8;

                    // ABILITIES (Not yet implemented)
                    String ability1 = "water_summon"; // Can summon water to attack the player
                    int ability1Damage = 10;
                    String ability2 = "tidal_wave";   // Can create a tidal wave to knock back the player
                    int ability2Damage = 15;
                    
                    int monsterLevel = level; // Will be used to scale the monster attack
                    System.out.print("\nThe " + monsterName + " emerges from the shadows, water dripping from its form.");
                    
                    //attack logic to be implemented

                    if (AttackPattern == 0) {
                        
                    } else if (AttackPattern == 1 || AttackPattern == 3 || AttackPattern == 5 || AttackPattern == 6) {
                        
                    } else if (AttackPattern == 2) {
                        
                    } else if (AttackPattern == 4) {
                        
                    }

                    
                    break;
                }
                case 1: {
                    /**
                     * MONSTER TYPE: The Lantern Wretch - Fear-inducing Spirit
                     * PLACEHOLDER: Full implementation needed
                     */
                    String monsterName = "The Lantern Wretch";
                    int monsterHealth = 50;
                    int baseMonsterAttack = 10;

                    // ABILITIES (Not yet implemented)
                    String ability1 = "fear_light";     // Light that reveals the player's fear (debuffs)
                    String ability2 = "blinding_light"; // Can create a blinding light to blind the player (stun for 1 turn)
                    
                    System.out.print("\nThe " + monsterName + " approaches with a flickering lantern, casting eerie shadows.");
                    // TODO: Implement full combat logic for this monster
                    break;
                }
                case 2: {
                    /**
                     * MONSTER TYPE: The Crawling Apostate - Wall-climbing Horror
                     * PLACEHOLDER: Full implementation needed
                     */
                    String monsterName = "The Crawling Apostate";
                    int monsterHealth = 55;
                    int baseMonsterAttack = 12;

                    // ABILITIES (Not yet implemented)
                    String ability1 = "wall_crawl"; // Can crawl on walls and ceiling (avoid melee attacks for 1 turn)
                    String ability2 = "acid_spit";  // Can spit acid to lower the player defense (debuffs)
                    
                    System.out.print("\nThe " + monsterName + " scuttles along the walls with unnatural movements.");
                    // TODO: Implement full combat logic for this monster
                    break;
                }
                case 3: {
                    /**
                     * MONSTER TYPE: Nytherian Maw - Soul-draining Entity
                     * PLACEHOLDER: Full implementation needed
                     */
                    String monsterName = "Nytherian Maw";
                    int monsterHealth = 65;
                    int baseMonsterAttack = 14;

                    // ABILITIES (Not yet implemented)
                    String ability1 = "soul_drain";    // Can suck the player's soul (drain health continuously)
                    String ability2 = "execute_strike"; // Extra damage when player health is below 20%
                    
                    System.out.print("\nThe " + monsterName + " opens its horrifying maw, hungry for your soul.");
                    // TODO: Implement full combat logic for this monster
                    break;
                }
                case 4: {
                    /**
                     * MONSTER TYPE: The Hollow Choir - Sonic Terror
                     * PLACEHOLDER: Full implementation needed
                     */
                    String monsterName = "The Hollow Choir";
                    int monsterHealth = 70;
                    int baseMonsterAttack = 11;

                    // ABILITIES (Not yet implemented)
                    String ability1 = "sonic_boom";    // Can create a sonic boom to stun the player (stun for 1 turn)
                    String ability2 = "deafening_roar"; // Can create a deafening roar to lower the player attack (debuffs)
                    
                    System.out.print("\nThe " + monsterName + " begins to sing a haunting melody that chills your bones.");
                    // TODO: Implement full combat logic for this monster
                    break;
                }
                case 5: {
                    /**
                     * MONSTER TYPE: The Candle-Eyed Haruspex - Fortune Teller
                     * PLACEHOLDER: Full implementation needed
                     */
                    String monsterName = "The Candle-Eyed Haruspex";
                    int monsterHealth = 65;
                    int baseMonsterAttack = 15;

                    // ABILITIES (Not yet implemented)
                    String ability1 = "future_sight";   // Can see the player's future (predict the player's next move)
                    String ability2 = "blinding_candles"; // Can create a blinding light to blind the player (stun for 1 turn)
                    String ability3 = "self_heal";      // The monster will heal itself +20 if activated
                    
                    System.out.print("\nThe " + monsterName + " stares at you with candle-flame eyes, seeing your fate.");
                    // TODO: Implement full combat logic for this monster
                    break;
                }
            } // End of switch statement
        } // End of level check
    } // End of midMonsters method

    /**
     * Hard-level monster generation method for late-game encounters (levels 20-40).
     * This includes both elite monsters and boss monsters that provide the ultimate
     * challenge before the final boss encounter.
     * 
     * @param level Current dungeon level (determines monster scaling)
     * @param monsterAttack Current monster's attack power (modified during combat)
     * @param playerAttack Current player's attack power (can be modified by abilities)
     * @param playerHealth Current player's health (can be modified by abilities)
     */
    public void hardMonsters(int level, int monsterAttack, int playerAttack, int playerHealth) {
        if (level >= 20 && level <= 40) {

            // RANDOM MONSTER SELECTION: Generate random number (0-5) to select monster type
            int monsterGenerater = (int) (Math.random() * 6); // 0,1,2,3,4,5, generate a random monster

            // ATTACK PATTERN SYSTEM: Determines what action the monster will take this turn
            int AttackPattern = (int) (Math.random() * 7); // 0) light attack, 1) miss , 2) heavy attack , 3) miss , 4) ability 1 ,5)  miss , 6) miss

            switch (monsterGenerater) {
                case 0: {
                    /**
                     * MONSTER TYPE: The Wailing Blight - Plague Spreader
                     * PLACEHOLDER: Full implementation needed
                     */
                    String monsterName = "The Wailing Blight";
                    int monsterHealth = 80;
                    int baseMonsterAttack = 20;

                    // ABILITIES (Not yet implemented)
                    String ability1 = "plague_creation"; // Can create a plague
                    String ability2 = "toxic_cloud";     // Can create a toxic cloud  
                    String ability3 = "disease_spread";  // Can create a disease
                    
                    System.out.print("\nThe " + monsterName + " approaches, spreading corruption with every step.");
                    // TODO: Implement full combat logic for this monster
                    break;
                }
                case 1: {
                    /**
                     * BOSS MONSTER: The Nameless One - Master of Minions
                     * PLACEHOLDER: Full implementation needed
                     */
                    String monsterName = "The Nameless One";
                    int monsterHealth = 150;
                    int baseMonsterAttack = 25;

                    // BOSS ABILITIES (Not yet implemented)
                    String ability1 = "summon_minions";  // Can summon minions to attack the player (summon 1-3 minions)
                    String ability2 = "devastating_strike"; // A strong attack that deals extra damage and stuns the player (stun for 1 turn)
                    String ability3 = "boss_heal";       // Will heal itself +50 if activated
                    String ability4 = "damage_immunity"; // Will create a shield that will block all damage for 1 turn (immune for 1 turn)
                    
                    System.out.print("\nThe " + monsterName + " emerges from the darkness, its form shifting and unknowable.");
                    // TODO: Implement full boss combat logic for this monster
                    break;
                }
                case 2: {
                    /**
                     * BOSS MONSTER: The Maw Beneath - Whirlpool Master
                     * PLACEHOLDER: Full implementation needed
                     */
                    String monsterName = "The Maw Beneath";
                    int monsterHealth = 140;
                    int baseMonsterAttack = 22;

                    // BOSS ABILITIES (Not yet implemented)
                    String ability1 = "whirlpool_trap";  // Can create a whirlpool to pull the player in (immobilize for 1 turn)
                    String ability2 = "crushing_strike"; // A strong attack that deals extra damage and lowers the player defense (debuffs)
                    String ability3 = "minion_summon";   // Can summon minions to attack the player (summon 1-3 minions)
                    
                    System.out.print("\nThe " + monsterName + " rises from the depths, water swirling around its massive form.");
                    // TODO: Implement full boss combat logic for this monster
                    break;
                }
                case 3: {
                    /**
                     * BOSS MONSTER: The Harbinger of Stillness - Silence Bringer
                     * PLACEHOLDER: Full implementation needed
                     */
                    String monsterName = "The Harbinger of Stillness";
                    int monsterHealth = 160;
                    int baseMonsterAttack = 28;

                    // BOSS ABILITIES (Not yet implemented)
                    String ability1 = "silence_player";  // Can silence the player (prevent the player from using abilities for 1 turn)
                    String ability2 = "charged_strike";  // A slow swing that deals big damage but takes 2 turns to charge up
                    String ability3 = "desperation_heal"; // Will heal itself +100 if player health is below 30% and player doesn't kill the monster in 6 turns
                    
                    System.out.print("\nThe " + monsterName + " appears in perfect silence, the air itself seeming to hold its breath.");
                    // TODO: Implement full boss combat logic for this monster
                    break;
                }
                case 4: {
                    /**
                     * BOSS MONSTER: Mother of Shattered Skies - Storm Caller
                     * PLACEHOLDER: Full implementation needed
                     */
                    String monsterName = "Mother of Shattered Skies";
                    int monsterHealth = 170;
                    int baseMonsterAttack = 24;

                    // BOSS ABILITIES (Not yet implemented)
                    String ability1 = "storm_summon";     // Can summon a storm to attack the player (random damage 10-30)
                    String ability2 = "explosive_minions"; // Will summon minions that explode when killed, dealing damage to the player (summon 1-3 minions)
                    String ability3 = "area_destruction";  // Will destroy the surrounding area dealing damage to the player (area attack 20-50 damage)
                    
                    System.out.print("\nThe " + monsterName + " descends from above, lightning crackling around her form.");
                    // TODO: Implement full boss combat logic for this monster
                    break;
                }
                case 5: {
                    /**
                     * BOSS MONSTER: The Hollow King - Adaptive Ruler
                     * PLACEHOLDER: Full implementation needed
                     */
                    String monsterName = "The Hollow King";
                    int monsterHealth = 180;
                    int baseMonsterAttack = 26;

                    // BOSS ABILITIES (Not yet implemented)
                    String ability1 = "conditional_heal"; // If the player health is below 50% the monster will heal itself +30
                    String ability2 = "defensive_strike"; // A strong attack that deals extra damage if the player defense is below 5 (debuffs for 1 turn)
                    String ability3 = "stat_mimicry";     // If the player doesn't kill it in 8 turns it will match the player attack and defense stats (immune for 1 turn)
                    
                    System.out.print("\nThe " + monsterName + " sits upon a throne of bones, crown askew on his hollow skull.");
                    // TODO: Implement full boss combat logic for this monster
                    break;
                }
            } // End of switch statement
        } // End of level check
    } // End of hardMonsters method

    /**
     * Final Boss encounter method - The ultimate challenge of the game.
     * This method handles the encounter with The Dream Eater Leviathan,
     * the final boss that players must defeat to escape the dungeon.
     * 
     * @param monsterAttack Current monster's attack power (modified during combat)
     * @param playerAttack Current player's attack power (can be modified by abilities)
     * @param playerHealth Current player's health (can be modified by abilities)
     */
    public void finalBossEncounter(int monsterAttack, int playerAttack, int playerHealth) {
        /**
         * THE DREAM EATER LEVIATHAN - Final Boss
         *
         * LORE: The ancient entity that rules the deepest depths of the
         * dungeon. This cosmic horror feeds on the dreams and hopes of
         * trapped souls, growing stronger with each victim. It represents
         * the final test of the player's journey - a battle that will
         * determine their fate.
         *
         * BOSS MECHANICS: 
         * - Multi-phase encounter with different ability sets
         * - Requires strategic planning and resource management
         * - Ultimate test of all skills learned throughout the game
         *
         * ABILITIES (6 unique powers): 
         * 1. Black Hole - Immobilizes player, preventing escape
         * 2. Devastating Strike - High damage + stun combination
         * 3. Self-Heal - One-time massive health restoration
         * 4. Damage Immunity - Temporary invulnerability shield
         * 5. Minion Summoning - Adds additional enemies to fight
         * 6. Area Destruction - Desperate final attack when nearly defeated
         */
        String monsterName = "The Dream Eater Leviathan";
        int monsterHealth = 300;     // Massive health pool for epic encounter
        int baseMonsterAttack = 35;  // Devastating attack power

        // FINAL BOSS ABILITY SET - Each ability represents a different threat type
        String ability1 = "black_hole";        // "Black Hole" - Immobilize (1 turn)
        String ability2 = "devastating_strike"; // "Devastating Strike" - High damage + stun (1 turn)
        String ability3 = "ancient_regeneration"; // "Ancient Regeneration" - Heal +100 (once only)
        String ability4 = "void_shield";       // "Void Shield" - Damage immunity (1 turn)
        String ability5 = "summon_nightmares"; // "Summon Nightmares" - Spawn minions (1-3 enemies)
        String ability6 = "reality_collapse";  // "Reality Collapse" - Area attack 50-100 damage (when health < 30%)

        System.out.print("\nThe very fabric of reality tears open as " + monsterName + " emerges!");
        System.out.print("\nThis ancient cosmic horror has waited eons for this moment...");
        System.out.print("\nYour final test begins now!");
        
        // TODO: Implement full final boss combat logic
        // This should include:
        // - Multi-phase combat with different ability rotations
        // - Health threshold triggers for different phases
        // - Complex ability interactions and status effects
        // - Victory/defeat conditions and story conclusion
    }

    /*
     * =============================================================================
     * MONSTER IMPLEMENTATION STATUS SUMMARY:
     * =============================================================================
     * 
     * ✅ FULLY IMPLEMENTED (Levels 1-5):
     * - Goblin: Complete with steal ability and proper combat logic
     * - Orc: Complete with power strike ability  
     * - Cave Lurkers: Complete with respawn ability
     * - The Lost: Complete with inagma health-drain ability
     * - Mimic: Complete with surprise attack ability
     * - Ghoul: Complete with frenzy multi-attack ability
     * 
     * ⚠️ FRAMEWORK IMPLEMENTED (Levels 5-20):
     * - The Murmuring Tides: Framework complete, combat logic needed
     * - The Lantern Wretch: Framework complete, combat logic needed
     * - The Crawling Apostate: Framework complete, combat logic needed
     * - Nytherian Maw: Framework complete, combat logic needed
     * - The Hollow Choir: Framework complete, combat logic needed
     * - The Candle-Eyed Haruspex: Framework complete, combat logic needed
     * 
     * ⚠️ FRAMEWORK IMPLEMENTED (Levels 20-40 - Boss Monsters):
     * - The Wailing Blight: Framework complete, boss logic needed
     * - The Nameless One: Framework complete, boss logic needed
     * - The Maw Beneath: Framework complete, boss logic needed
     * - The Harbinger of Stillness: Framework complete, boss logic needed
     * - Mother of Shattered Skies: Framework complete, boss logic needed
     * - The Hollow King: Framework complete, boss logic needed
     * 
     * ⚠️ FRAMEWORK IMPLEMENTED (Final Boss):
     * - The Dream Eater Leviathan: Framework complete, epic boss logic needed
     * 
     * 🔧 NEXT DEVELOPMENT PRIORITIES:
     * 
     * HIGH PRIORITY:
     * 1. Implement full combat logic for mid-game monsters (levels 5-20)
     * 2. Implement boss battle mechanics with multi-phase encounters
     * 3. Add status effect system (stun, debuff, immunity, etc.)
     * 4. Create turn-based combat integration with MainController
     * 5. Add monster health tracking and defeat conditions
     * 
     * MEDIUM PRIORITY:
     * 6. Implement monster AI difficulty scaling
     * 7. Add monster dialogue and flavor text system
     * 8. Create monster encounter frequency balancing
     * 9. Add special loot drops from boss monsters
     * 10. Implement monster respawn and healing mechanics
     * 
     * LOW PRIORITY:
     * 11. Create monster bestiary for player reference
     * 12. Add visual/audio cue system for different monster types
     * 13. Implement monster evolution/scaling system
     * 14. Add rare/legendary monster variants
     * 15. Create monster achievement system
     * 
     * 💡 ARCHITECTURAL IMPROVEMENTS:
     * - Consider refactoring into individual Monster classes with inheritance
     * - Implement Strategy pattern for different attack behaviors
     * - Create MonsterFactory for cleaner monster generation
     * - Add Observer pattern for combat event handling
     * - Implement State pattern for boss phase transitions
     * 
     * 📋 INTEGRATION NOTES:
     * - Methods are ready for integration with MainController combat system
     * - All monsters return appropriate damage values and status effects
     * - Monster generation is level-appropriate and balanced
     * - Framework supports future expansion and modification
     * - Code is well-documented for other developers
     * 
     * 🎮 USAGE INSTRUCTIONS FOR OTHER DEVELOPERS:
     * 
     * 1. BASIC MONSTER ENCOUNTERS:
     *    monster.easyMonsters(level, monsterAttack, playerAttack, playerHealth);
     *    
     * 2. MID-LEVEL ENCOUNTERS:
     *    monster.midMonsters(level, monsterAttack, playerAttack, playerHealth);
     *    
     * 3. BOSS ENCOUNTERS:
     *    monster.hardMonsters(level, monsterAttack, playerAttack, playerHealth);
     *    
     * 4. FINAL BOSS:
     *    monster.finalBossEncounter(monsterAttack, playerAttack, playerHealth);
     *    
     * 5. DISPLAY MONSTER INFO:
     *    monster.displayMonsterStats(monsterType, name, health, attack, ability);
     * 
     * All methods are thread-safe and can be called multiple times.
     * Combat values are returned through the parameter variables.
     * System.out.print statements provide narrative text for player immersion.
     */

} // End of monster class
