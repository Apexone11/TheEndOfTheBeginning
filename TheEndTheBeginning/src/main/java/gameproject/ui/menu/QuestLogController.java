package gameproject.ui.menu;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import gameproject.MainControllerNew;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller for the quest log screen.
 */
public class QuestLogController implements Initializable {
    
    @FXML private Button activeTabButton;
    @FXML private Button completedTabButton;
    @FXML private Button allTabButton;
    @FXML private VBox questListBox;
    @FXML private VBox questDetailsBox;
    @FXML private Label questTitleLabel;
    @FXML private Label questDescLabel;
    @FXML private ProgressBar questProgressBar;
    @FXML private Label questProgressLabel;
    @FXML private Button closeButton;
    
    private MainControllerNew mainController;
    private String currentFilter = "ACTIVE";
    
    public void setMainController(MainControllerNew mainController) {
        this.mainController = mainController;
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        refreshQuestList();
    }
    
    @FXML
    private void handleFilterActive() {
        currentFilter = "ACTIVE";
        refreshQuestList();
    }
    
    @FXML
    private void handleFilterCompleted() {
        currentFilter = "COMPLETED";
        refreshQuestList();
    }
    
    @FXML
    private void handleFilterAll() {
        currentFilter = "ALL";
        refreshQuestList();
    }
    
    private void refreshQuestList() {
        if (questListBox == null) return;
        
        questListBox.getChildren().clear();
        
        // TODO: Get quests based on currentFilter and display them
        // This would require QuestManager to expose quest lists
        
        // Placeholder
        Label placeholder = new Label("No quests available");
        placeholder.setPadding(new Insets(20));
        questListBox.getChildren().add(placeholder);
    }
    
    @FXML
    private void handleClose() {
        if (mainController != null) {
            mainController.resumeGame();
        }
    }
}

