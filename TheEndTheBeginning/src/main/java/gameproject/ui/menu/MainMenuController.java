package gameproject.ui.menu;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import gameproject.MainControllerNew;
import gameproject.SaveManager;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller for the main menu screen.
 */
public class MainMenuController implements Initializable {
    
    @FXML private Button newGameButton;
    @FXML private Button continueButton;
    @FXML private Button loadButton;
    @FXML private Button settingsButton;
    @FXML private Button quitButton;
    
    private MainControllerNew mainController;
    
    public void setMainController(MainControllerNew mainController) {
        this.mainController = mainController;
        updateContinueButton();
    }
    
    // Called by FXML loader after initialization
    public void initializeController() {
        // Get main controller from scene
        if (newGameButton != null && newGameButton.getScene() != null) {
            Object controller = newGameButton.getScene().getUserData();
            if (controller instanceof MainControllerNew) {
                setMainController((MainControllerNew) controller);
            }
        }
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        updateContinueButton();
    }
    
    private void updateContinueButton() {
        if (continueButton != null) {
            continueButton.setDisable(!SaveManager.saveExists());
        }
    }
    
    @FXML
    private void handleNewGame() {
        if (mainController != null) {
            mainController.showOnboarding();
        }
    }
    
    @FXML
    private void handleContinue() {
        if (mainController != null && SaveManager.saveExists()) {
            mainController.loadGameAndStart();
        }
    }
    
    @FXML
    private void handleLoad() {
        if (mainController != null) {
            mainController.showSaveLoad(true);
        }
    }
    
    @FXML
    private void handleSettings() {
        if (mainController != null) {
            mainController.showSettings();
        }
    }
    
    @FXML
    private void handleQuit() {
        if (mainController != null) {
            mainController.handleQuit();
        }
    }
}

