package gameproject.ui.menu;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import gameproject.MainControllerNew;
import gameproject.SaveManager;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller for save/load screen.
 */
public class SaveLoadController implements Initializable {
    
    @FXML private Label titleLabel;
    @FXML private GridPane saveSlotsGrid;
    @FXML private Button closeButton;
    @FXML private Button deleteButton;
    
    private MainControllerNew mainController;
    private boolean isLoadMode = true;
    private int selectedSlot = -1;
    
    private static final int MAX_SAVE_SLOTS = 6;
    
    public void setMainController(MainControllerNew mainController) {
        this.mainController = mainController;
    }
    
    public void setLoadMode(boolean loadMode) {
        this.isLoadMode = loadMode;
        if (titleLabel != null) {
            titleLabel.setText(loadMode ? "Load Game" : "Save Game");
        }
        refreshSlots();
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        refreshSlots();
    }
    
    private void refreshSlots() {
        if (saveSlotsGrid == null) return;
        
        saveSlotsGrid.getChildren().clear();
        
        for (int i = 0; i < MAX_SAVE_SLOTS; i++) {
            int slot = i;
            VBox slotBox = createSaveSlot(slot);
            int col = i % 3;
            int row = i / 3;
            saveSlotsGrid.add(slotBox, col, row);
        }
    }
    
    private VBox createSaveSlot(int slotIndex) {
        VBox slotBox = new VBox(5);
        slotBox.setPadding(new Insets(10));
        slotBox.setStyle("-fx-background-color: -color-surface; -fx-border-color: -color-border; -fx-border-width: 1px;");
        slotBox.setPrefWidth(200);
        slotBox.setPrefHeight(150);
        
        Label slotLabel = new Label("Slot " + (slotIndex + 1));
        slotLabel.setStyle("-fx-font-weight: bold;");
        
        Label infoLabel = new Label("Empty");
        infoLabel.setWrapText(true);
        
        Button actionButton = new Button(isLoadMode ? "Load" : "Save");
        actionButton.setPrefWidth(180);
        actionButton.setOnAction(e -> handleSlotAction(slotIndex));
        
        slotBox.getChildren().addAll(slotLabel, infoLabel, actionButton);
        
        // Load slot info if exists
        SaveManager.SaveSlotInfo slotInfo = SaveManager.getSlotInfo(slotIndex);
        if (slotInfo != null) {
            infoLabel.setText(slotInfo.toString());
        }
        
        return slotBox;
    }
    
    private void handleSlotAction(int slot) {
        if (isLoadMode) {
            if (mainController != null) {
                mainController.loadGameFromSlot(slot);
            }
        } else {
            if (mainController != null) {
                mainController.saveGameToSlot(slot);
            }
        }
    }
    
    @FXML
    private void handleDelete() {
        if (selectedSlot >= 0) {
            SaveManager.deleteSlot(selectedSlot);
            refreshSlots();
            selectedSlot = -1;
            deleteButton.setDisable(true);
        }
    }
    
    @FXML
    private void handleClose() {
        if (mainController != null) {
            mainController.showMainMenu();
        }
    }
}

