package gameproject;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * "The End The Beginning" - Main Application Class
 * 
 * A dungeon escape RPG prototype featuring room exploration, turn-based combat,
 * item collection, and character progression. The game challenges players to
 * navigate through 10 levels of a mysterious dungeon, battling monsters and
 * collecting items to enhance their abilities.
 * 
 * GAME CONCEPT:
 * Players wake up in a dark dungeon with no memory of how they arrived. They must
 * explore rooms, fight monsters, collect items, and progress through increasingly
 * difficult levels to escape. The game features a strategic combat system where
 * timing, resource management, and tactical decisions determine success.
 * 
 * TECHNICAL ARCHITECTURE:
 * - JavaFX Application: Modern GUI framework for cross-platform compatibility
 * - MVC Pattern: Separation of game logic (Model), UI (View), and control (Controller)
 * - FXML Integration: UI layout defined in XML for easy maintenance and modification
 * - Event-Driven Design: User interactions drive game state changes
 * 
 * KEY FEATURES:
 * - Turn-based combat with multiple monster types
 * - Progressive difficulty scaling
 * - Item collection and stat enhancement
 * - Room exploration with random events
 * - Player progression and leveling
 * - Multiple difficulty modes
 * 
 * DEVELOPMENT STATUS:
 * - Core gameplay: Implemented
 * - Monster system: Partially implemented (early game monsters done)
 * - Item system: Framework in place, needs full implementation
 * - Player progression: Basic system working
 * - UI/UX: Functional JavaFX interface
 * 
 * @author Abdul Fornah
 * @version 1.0
 * @since 2024
 */
public class TheEndTheBeginning extends Application {

    /**
     * JavaFX Application start method - initializes and displays the game window.
     * 
     * This method is called by the JavaFX Application Thread and sets up the
     * primary game window with all necessary UI components and styling.
     * 
     * INITIALIZATION PROCESS:
     * 1. Load FXML layout file (game.fxml) which defines UI structure
     * 2. Create Scene with specified dimensions (800x600 pixels)
     * 3. Configure window properties (title, resizability)
     * 4. Display the window to the user
     * 
     * @param primaryStage The primary window/stage for this JavaFX application
     * @throws Exception If FXML file cannot be loaded or UI initialization fails
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        // FXML LOADING: Load the UI layout from external FXML file
        // This separates UI design from Java code for better maintainability
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/game.fxml"));
        Parent root = loader.load();
        
        // SCENE CREATION: Set up the game window with appropriate dimensions
        // 900x700 provides better visibility for enhanced content
        Scene scene = new Scene(root, 900, 700);
        
        // WINDOW CONFIGURATION: Set up the main game window properties
        primaryStage.setTitle("The End The Beginning - Dungeon Escape v3.0");
        primaryStage.setScene(scene);
        primaryStage.setResizable(true);  // Allow players to resize for comfort
        primaryStage.setMinWidth(800);    // Set minimum dimensions
        primaryStage.setMinHeight(600);
        
        // Try to load and set window icon
        try {
            javafx.scene.image.Image icon = new javafx.scene.image.Image(
                getClass().getResourceAsStream("/icons/game-icon.png"));
            primaryStage.getIcons().add(icon);
        } catch (Exception e) {
            System.out.println("Could not load icon: " + e.getMessage());
        }
        
        primaryStage.show(); // Display the window to the user
    }

    /**
     * Application entry point - launches the JavaFX application.
     * 
     * This method is called when the program starts and initiates the JavaFX
     * application lifecycle. It should not be called directly in JavaFX applications.
     * 
     * @param args Command line arguments (currently unused)
     */
    public static void main(String[] args) {
        // Launch the JavaFX application - this will call start() method
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
        
    */
}

