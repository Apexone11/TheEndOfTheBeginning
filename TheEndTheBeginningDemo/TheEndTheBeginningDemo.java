
import java.util.Scanner;

/*
 Simple Dungeon Escape (text-based)
 This is a small RPG / dungeon escape prototype with room events,
 basic combat, and pickup items. The code is kept simple so it's easy
 to follow and refactor later into classes (Player, Monster, Item).
*/

public class TheEndTheBeginningDemo {

    // Display player stats in a clear, readable format.
    public static void playerStats(int health, int defense, int attack, int level) {
        System.out.println();
        System.out.println("=== Player Stats ===");
        System.out.println("Health : " + health);
        System.out.println("Defense: " + defense);
        System.out.println("Attack : " + attack);
        System.out.println("Level  : " + level);
        System.out.println();
    }

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        /*
         TODO (short, actionable):
         1) Create `Player` and `Monster` classes to hold stats and behaviors.
         2) Implement an NPC/dialogue system (simple text choices -> responses).
         3) Add items that modify stats and show their name/amount when picked up.
         4) Add save/load (simple text file) so players can continue later.
         5) Consider moving to JavaFX for a simple GUI (see PROJECT_GUIDE.txt).
        */

        // Basic player stats
        boolean isPlaying = true;
        int health = 100;
        int defense = 0;
        int attack = 0;

        // Random values used for easy-mode pickups (kept simple)
        int easyMultiplier = (int) (Math.random() * 3); // 0, 1, or 2
        int HEALTHChangeEasy = (int) (Math.random() * 36); // 0-35
        int defenseEasy = (int) (Math.random() * 41); // 0-40
        int attackEasy = (int) (Math.random() * 46); // 0-45

        System.out.println();
        System.out.println("Welcome to DUNGEON ESCAPE (text prototype)");
        System.out.println();

        // Ask whether the user wants to play. Keep re-prompting for valid input.
        String startGame = "";
        while (isPlaying) {
            System.out.print("Do you want to play? (YES/NO): ");
            startGame = input.next();

            if (startGame.equalsIgnoreCase("YES")) {

                // Ask for game difficulty
                System.out.println();
                System.out.println("Choose difficulty:");
                System.out.println(" 1: Easy\n 2: Medium\n 3: Hard\n 4: Death");
                System.out.print("Enter 1-4: ");
                int difficulty = input.nextInt();

                // Player name and short intro
                System.out.println();
                System.out.print("What's your name? ");
                String name = input.next();

                System.out.println();
                System.out.println("You wake up in a cold, dark dungeon. The air tastes of iron and old candles.");
                System.out.println("Hope you survive this ordeal, " + name + ". May fortune favor you.");
                System.out.println();

                System.out.println("--- Starting Stats ---");
                System.out.println("Name   : " + name);
                System.out.println("Health : " + health);
                System.out.println("Defense: " + defense);
                System.out.println("Attack : " + attack);
                System.out.println();

                switch (difficulty) {
                    case 1: // Easy mode
                        int level = 1;
                        int playerLevel = 1; // tracks progression

                        // Player must reach level 10 to escape
                        while (level < 10 && health > 0) {
                            System.out.println();
                            System.out.println("Room " + level + " :");

                            // limit to how many times you can search the room
                            int roomSearches = 0;
                            // random event (0,1,2 -> pickup, 3 -> monster)
                            int MoveEvent = (int) (Math.random() * 4);

                            if (MoveEvent != 3) {
                                System.out.println();
                                System.out.println("What do you want to do, " + name + "?");
                                System.out.println(" 1: Continue in the room\n 2: Move to next room\n 3: Check stats");
                                System.out.print("Enter 1-3: ");
                                int roomResponse = input.nextInt();

                                if (roomResponse == 1) {
                                    System.out.println("You search the room carefully...");
                                    if (roomSearches >= 2) {
                                        System.out.println("You've searched this room enough. Move on.");
                                    } else {
                                        roomSearches++;
                                    }
                                    // continue to event handling below
                                } else if (roomResponse == 2) {
                                    level++;
                                    System.out.println("You move to the next room.");
                                    continue;
                                } else if (roomResponse == 3) {
                                    playerStats(health, defense, attack, level);
                                    continue;
                                }
                            }

                            // Handle events
                            if (MoveEvent == 0) {
                                int gained = easyMultiplier * HEALTHChangeEasy;
                                health += gained;
                                System.out.println();
                                System.out.println("You found a health potion. Gained: " + gained + " health. Current health: " + health);
                            } else if (MoveEvent == 1) {
                                int gained = easyMultiplier * defenseEasy;
                                defense += gained;
                                System.out.println();
                                System.out.println("You found armor scraps. Defense increased by " + gained + ". Current defense: " + defense);
                            } else if (MoveEvent == 2) {
                                int gained = easyMultiplier * attackEasy;
                                attack += gained;
                                System.out.println();
                                System.out.println("You found a crude weapon. Attack increased by " + gained + ". Current attack: " + attack);
                            } else if (MoveEvent == 3) {
                                System.out.println();
                                System.out.println("A monster notices you! Prepare to act.");
                                System.out.println(" 1: Fight\n 2: Run\n 3: Hide\n 4: Do nothing");
                                System.out.print("Choose 1-4: ");
                                int monsterResponse = input.nextInt();

                                if (monsterResponse == 1) {
                                    System.out.println();
                                    System.out.println("You choose to fight the monster!");

                                    // Reset a fresh monster health for each encounter
                                    int monsterHealth = 40 + (int) (Math.random() * 61); // 40-100
                                    int monsterLevel = level; // Monster level scales with room level
                                    boolean fled = false;

                                    while (monsterHealth > 0 && health > 0 && !fled) {
                                        int minDamage = Math.max(1, attack / 2);
                                        int maxDamage = Math.max(minDamage, attack + (playerLevel * 2));
                                        int playerDamage = minDamage + (int) (Math.random() * (maxDamage - minDamage + 1));

                                        if (attack <= 0) {
                                            playerDamage = 2 + (int) (Math.random() * (attackEasy + 1));
                                        }

                                        monsterHealth -= playerDamage;
                                        System.out.println("You hit the monster for " + playerDamage + " damage. Monster health: " + Math.max(0, monsterHealth));

                                        if (monsterHealth <= 0) {
                                            System.out.println("The monster has been slain! You may move on.");
                                            level++;
                                            playerLevel++;
                                            break;
                                        }

                                        int monsterAttack = 5 + (int) (Math.random() * 11 * monsterLevel); // 5-15 damage more as level increases

                                        if (defense > 0) {
                                            int shieldAbsorb = Math.min(defense, monsterAttack);
                                            defense -= shieldAbsorb;
                                            monsterAttack -= shieldAbsorb;
                                            System.out.println("The monster strikes your shield for " + shieldAbsorb + " damage. Shield remaining: " + defense);
                                        }

                                        if (monsterAttack > 0) {
                                            health -= monsterAttack;
                                            System.out.println("The monster hits you for " + monsterAttack + " damage. Your health: " + Math.max(0, health));
                                        }

                                        if (health <= 0) {
                                            System.out.println();
                                            System.out.println("You have been defeated by the monster. Game over.");
                                            isPlaying = false;
                                            break;
                                        }

                                        System.out.println();
                                        System.out.println("What will you do next? 1: Continue fight  2: Attempt to run");
                                        System.out.print("Choose 1-2: ");
                                        int nextAction = input.nextInt();
                                        if (nextAction == 2) {
                                            if (Math.random() < 0.5) {
                                                System.out.println("You successfully escaped the monster!");
                                                fled = true;
                                            } else {
                                                System.out.println("You failed to escape. The fight continues.");
                                            }
                                        }
                                    }
                                } else if (monsterResponse == 2) {
                                    // Running: 50% chance to escape
                                    if (Math.random() < 0.5) {
                                        System.out.println("You managed to run away to the next room.");
                                        level++;
                                    } else {
                                        System.out.println("You failed to run. The monster attacks!");
                                        health -= 10; // small penalty for failing
                                        System.out.println("You lost 10 health. Current health: " + health);
                                    }
                                } else if (monsterResponse == 3) {
                                    System.out.println("You hide quietly. The monster moves on. You survive this encounter.");
                                } else {
                                    System.out.println("You do nothing. The monster attacks angrily.");
                                    health -= 8;
                                    System.out.println("You lost 8 health. Current health: " + health);
                                }
                            }
                        }

                        if (level >= 10) {
                            System.out.println();
                            System.out.println("You have escaped the dungeon. Congratulations!");
                            isPlaying = false;
                        }

                        break; // end of easy case

                    default:
                        System.out.println("Difficulty not yet implemented. Defaulting to Easy.");
                        break;
                }

            } else if (startGame.equalsIgnoreCase("NO")) {
                System.out.println();
                System.out.println("You gave up... The dungeon keeps its prisoners.");
                System.out.println();
                System.out.println("--- Credits ---");
                System.out.println("Game developed by: Abdul Fornah");
                System.out.println("Special thanks: ChatGPT (ideas) and playtesters");
                isPlaying = false;
            } else {
                System.out.println("Please enter a valid response: YES or NO.");
                // loop will repeat and re-prompt
            }
        }

        input.close();
    }
}
