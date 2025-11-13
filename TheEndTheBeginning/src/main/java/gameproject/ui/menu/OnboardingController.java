package gameproject.ui.menu;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import main.model.Player;
import gameproject.MainControllerNew;

import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;

/**
 * Controller for character creation/onboarding screen.
 */
public class OnboardingController implements Initializable {
    
    @FXML private TextField nameField;
    @FXML private Button generateNameButton;
    @FXML private Button warriorButton;
    @FXML private Button mageButton;
    @FXML private Button rogueButton;
    @FXML private VBox difficultyBox;
    @FXML private Button easyButton;
    @FXML private Button normalButton;
    @FXML private Button hardButton;
    @FXML private Button deathButton;
    @FXML private Button startButton;
    
    private MainControllerNew mainController;
    private Player.PlayerClass selectedClass;
    private String selectedDifficulty = "NORMAL";
    private final Random random = new Random();
    
    private static final String[] NAME_PREFIXES = {
        "Aether", "Shadow", "Crimson", "Azure", "Void", "Storm", "Frost", "Flame",
        "Dark", "Light", "Iron", "Steel", "Silver", "Golden", "Ancient", "Eternal"
    };
    
    private static final String[] NAME_SUFFIXES = {
        "blade", "heart", "soul", "spirit", "ward", "guard", "strike", "fury",
        "bane", "wrath", "flame", "frost", "storm", "shadow", "light", "dark"
    };
    
    public void setMainController(MainControllerNew mainController) {
        this.mainController = mainController;
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Enable start button when name is entered
        nameField.textProperty().addListener((obs, oldVal, newVal) -> {
            updateStartButton();
        });
    }
    
    @FXML
    private void handleGenerateName() {
        String prefix = NAME_PREFIXES[random.nextInt(NAME_PREFIXES.length)];
        String suffix = NAME_SUFFIXES[random.nextInt(NAME_SUFFIXES.length)];
        String generatedName = prefix + suffix;
        nameField.setText(generatedName);
        updateStartButton();
    }
    
    @FXML
    private void handleClassSelection(javafx.event.ActionEvent event) {
        Object source = event.getSource();
        
        if (source == warriorButton) {
            selectedClass = Player.PlayerClass.WARRIOR;
        } else if (source == mageButton) {
            selectedClass = Player.PlayerClass.MAGE;
        } else if (source == rogueButton) {
            selectedClass = Player.PlayerClass.ROGUE;
        }
        
        // Show difficulty selection
        difficultyBox.setVisible(true);
        updateStartButton();
    }
    
    @FXML
    private void handleDifficulty(javafx.event.ActionEvent event) {
        Object source = event.getSource();
        
        if (source == easyButton) {
            selectedDifficulty = "EASY";
        } else if (source == normalButton) {
            selectedDifficulty = "NORMAL";
        } else if (source == hardButton) {
            selectedDifficulty = "HARD";
        } else if (source == deathButton) {
            selectedDifficulty = "DEATH";
        }
        
        updateStartButton();
    }
    
    @FXML
    private void handleStart() {
        String playerName = nameField.getText().trim();
        if (playerName.isEmpty() || selectedClass == null) {
            return;
        }
        
        if (mainController != null) {
            mainController.startNewGame(playerName, selectedClass, selectedDifficulty);
        }
    }
    
    private void updateStartButton() {
        boolean canStart = !nameField.getText().trim().isEmpty() && 
                          selectedClass != null && 
                          selectedDifficulty != null;
        if (startButton != null) {
            startButton.setDisable(!canStart);
        }
    }
}

