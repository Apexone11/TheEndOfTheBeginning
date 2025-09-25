package gameproject;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/*
Simple Dungeon Escape (JavaFX Edition)
This is a small RPG / dungeon escape prototype with room events,
basic combat, and pickup items. Now converted to JavaFX for a GUI experience.
*/

public class TheEndTheBeginning extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/game.fxml"));
        Parent root = loader.load();
        
        Scene scene = new Scene(root, 800, 600);
        primaryStage.setTitle("The End The Beginning - Dungeon Escape");
        primaryStage.setScene(scene);
        primaryStage.setResizable(true);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
    
    // Legacy text-based game code (kept for reference, but not used in JavaFX version)
    /*
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

    public static void mainOld(String[] args) {
        Scanner input = new Scanner(System.in);
        //import monster class
        main.model.monster monster = new main.model.monster();
        

        TODO (short, actionable):
        1) Create `Player` and `Monster` classes to hold stats and behaviors.
        2) Implement an NPC/dialogue system (simple text choices -> responses).
        ) Add items that modify stats and show their name/amount when picked up.
        4) Add save/load (simple text file) so players can continue later.
        5) Consider moving to JavaFX for a simple GUI (see PROJECT_GUIDE.txt).
        6) find a way to make it possible of the player to miss attacks
        7) make a end game fuction that will play the credits and end the game
        8) the player can choose who they want to fight ( if there is more then one monster)
        

        // Basic player stats
        boolean isPlaying = true;
        int health = 100;
        int defense = 1;
        int attack = 1;

        // Random values used for easy-mode pickups (kept simple)
        int easyMultiplier = (int) (Math.random() * 6); // 0-5
        int HEALTHChangeEasy = (int) (Math.random() * 10); // 0-10
        int defenseEasy = (int) (Math.random() * 10); // 0-10
        int attackEasy = (int) (Math.random() * 10); // 0-10

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
                        int level = 1; // room level
                        int playerLevel = 1; // tracks progression
                        int room = 0;
                        // how many times the player has searched the current room
                        int roomSearches = 0;

                        // Player must reach level 10 to escape
                        while (level < 10 && health > 0) {
                            System.out.println();
                            System.out.println("floor " + level + " :");
                            System.out.println("room " + room + " :");

                                // random event (0,1,2 -> pickup, 3 -> monster)
                                int gameEvent = (int) (Math.random() * 4);

                            if (gameEvent != 3) {
                                System.out.println();
                                System.out.println("What do you want to do, " + name + "?");
                                System.out.print("");
                                System.out.println(" 1: Continue in the room\n 2: Move to next room\n 3: Check stats");
                                System.out.print("Enter 1-3: ");
                                int roomResponse = input.nextInt();

                                if (roomResponse == 1) {
                                    if (roomSearches < 3) {
                                        System.out.println("You search the room carefully...");
                                        roomSearches++;
                                        room++;
                                    } else {
                                        System.out.println("You've searched enough. Better move on.");
                                        level++;
                                        room = 0; // reset room number for new floor
                                        roomSearches = 0; // reset searches for new room
                                    }
                                // continue to event handling below
                                } else if (roomResponse == 2) {
                                    System.out.println("You move to the next room.");
                                    level++;
                                    room = 0; // reset room number for new floor
                                    roomSearches = 0; // reset searches for new room
                                } else if (roomResponse == 3) {
                                    playerStats(health, defense, attack, level);
                                }
                            }

                            // Handle events
                            if (gameEvent == 0) {
                                int gained = easyMultiplier * HEALTHChangeEasy;
                                health += gained;
                                System.out.println();
                                System.out.println("You found a health potion. Gained: " + gained + " health.");
                            } else if (gameEvent == 1) {
                                int gained = easyMultiplier * defenseEasy;
                                defense += gained;
                                System.out.println();
                                System.out.println("You found armor scraps. Defense increased by " + gained + ".");
                            } else if (gameEvent == 2) {
                                int gained = easyMultiplier * attackEasy;
                                attack += gained;
                                System.out.println();
                                System.out.println("You found a crude weapon. Attack increased by " + gained + ".");
                            } else if (gameEvent == 3) {
                                //will pick a random monster from the monster class


                                System.out.println();
                                System.out.println("you have been noticed.");

                                
                                System.out.println(" 1: Fight\n 2: Run\n 3: Hide\n 4: Do nothing");
                                System.out.print("Choose 1-4: ");
                                int playerMonsterResponse = input.nextInt();

                                if (playerMonsterResponse == 1) {
                                    System.out.println();
                                    System.out.println("You choose to fight the monster!");

                                    // Reset a fresh monster health for each encounter
                                    int monsterHealth = 40 + (int) (Math.random() * 61); // 40-100
                                    int monsterLevel = Math.max(1, level); // Monster level scales with room level
                                    boolean fled = false;

                                    // Decide initial turn order: player slightly more likely to start
                                    boolean playerGoesFirst = Math.random() < 0.6;

                                    while (monsterHealth > 0 && health > 0 && !fled) {
                                        // Player attack (if player goes first this round)
                                        if (playerGoesFirst) {
                                            int minDamage = Math.max(1, attack / 2);
                                            int maxDamage = Math.max(minDamage, attack + (playerLevel * 2));
                                            int playerDamage = minDamage + (int) (Math.random() * (maxDamage - minDamage + 1));
                                            if (attack <= 0) {
                                                playerDamage = 2 + (int) (Math.random() * (attackEasy / 2));
                                            }
                                            monsterHealth -= playerDamage;
                                            System.out.println("\nYou hit the monster for " + playerDamage + " damage. Monster health: " + Math.max(0, monsterHealth));
                                            if (monsterHealth <= 0) {
                                                System.out.println("\nThe monster has been slain! You may move on.");
                                                level++;
                                                playerLevel++;
                                                break;
                                            }
                                        }

                                        // Monster attack
                                        int monsterAttack = 5 + (int) (Math.random() * (5 + monsterLevel)); // scaled but bounded
                                        if (defense > 0) {
                                            int shieldAbsorb = Math.min(defense, monsterAttack);
                                            defense -= shieldAbsorb;
                                            monsterAttack -= shieldAbsorb;
                                            System.out.println("\nThe monster strikes your shield for " + shieldAbsorb + " damage. Shield remaining: " + defense);
                                        }
                                        if (monsterAttack > 0) {
                                            health -= monsterAttack;
                                            System.out.println("\nThe monster hits you for " + monsterAttack + " damage. Your health: " + Math.max(0, health));
                                        }

                                        if (health <= 0) {
                                            System.out.println();
                                            System.out.println("\nYou have been defeated by the monster. Game over.");
                                            isPlaying = false;
                                            break;
                                        }

                                        // If monster went first, let player attack now
                                        if (!playerGoesFirst) {
                                            int minDamage = Math.max(1, attack / 2);
                                            int maxDamage = Math.max(minDamage, attack + (playerLevel * 2));
                                            int playerDamage = minDamage + (int) (Math.random() * (maxDamage - minDamage + 1));
                                            if (attack <= 0) {
                                                playerDamage = 2 + (int) (Math.random() * (attackEasy / 2));
                                            }
                                            monsterHealth -= playerDamage;
                                            System.out.println("\nYou hit the monster for " + playerDamage + " damage. Monster health: " + Math.max(0, monsterHealth));
                                            if (monsterHealth <= 0) {
                                                System.out.println("The monster has been slain! You may move on.");
                                                level++;
                                                playerLevel++;
                                                break;
                                            }
                                        }

                                        // Ask player to continue or try to run
                                        System.out.println();
                                        System.out.println("What will you do next? 1: Continue fight  2: Attempt to run");
                                        System.out.print("Choose 1-2: ");
                                        int nextAction = input.nextInt();
                                        if (nextAction == 2) {
                                            if (Math.random() < 0.5) {
                                                System.out.println("\nYou successfully escaped the monster!");
                                                fled = true;
                                                break;
                                            } else {
                                                System.out.println("\nYou failed to escape. The fight continues.");
                                            }
                                        }

                                        // Small chance to switch who goes first next round
                                        playerGoesFirst = Math.random() < 0.6;
                                    }
                                } else if (playerMonsterResponse == 2) {
                                    // Running: 50% chance to escape
                                    if (Math.random() < 0.5) {
                                        System.out.println("\nYou managed to run away to the next room.");
                                        level++;
                                    } else {
                                        System.out.println("\nYou failed to run. The monster attacks!");
                                        health -= 10; // small penalty for failing
                                        System.out.println("You lost 10 health. Current health: " + health);
                                    }
                                } else if (playerMonsterResponse == 3) {
                                    System.out.println("\nYou hide quietly. The monster moves on. You survive this encounter.");
                                } else {
                                    System.out.println("\nYou do nothing. The monster attacks angrily.");
                                    health -= 8;
                                    System.out.println("You lost 8 health. Current health: " + health);
                                }
                            }
                        }

                        if (level >= 10) {
                            System.out.println();
                            System.out.println("You have escaped the dungeon. Congratulations!");

                            // play credits
                            System.out.println("--- Credits ---");
                            System.out.println("Game developed by: Abdul Fornah");
                            System.out.println("Special thanks: ChatGPT (ideas) and playtesters");
                            isPlaying = false;
                        }

                        break; // end of easy case

                    default:
                        System.out.println("Difficulty not yet implemented. Defaulting to Easy.");
                        break;
                }

            } else if (startGame.equalsIgnoreCase("NO") ) {
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
    */
}

