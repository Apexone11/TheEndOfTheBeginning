package gameproject.ui.menu;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import gameproject.MainControllerNew;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller for the pause menu.
 */
public class PauseMenuController implements Initializable {
    
    @FXML private Button resumeButton;
    @FXML private Button inventoryButton;
    @FXML private Button settingsButton;
    @FXML private Button saveButton;
    @FXML private Button quitButton;
    
    private MainControllerNew mainController;
    
    public void setMainController(MainControllerNew mainController) {
        this.mainController = mainController;
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Controller initialization
    }
    
    @FXML
    private void handleResume() {
        if (mainController != null) {
            mainController.resumeGame();
        }
    }
    
    @FXML
    private void handleInventory() {
        if (mainController != null) {
            mainController.showInventory();
        }
    }
    
    @FXML
    private void handleSettings() {
        if (mainController != null) {
            mainController.showSettings();
        }
    }
    
    @FXML
    private void handleSave() {
        if (mainController != null) {
            mainController.showSaveLoad(false);
        }
    }
    
    @FXML
    private void handleQuit() {
        if (mainController != null) {
            mainController.quitToMenu();
        }
    }
}

