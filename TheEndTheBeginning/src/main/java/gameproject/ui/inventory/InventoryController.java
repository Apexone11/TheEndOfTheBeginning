package gameproject.ui.inventory;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import gameproject.MainControllerNew;
import main.model.Item;
import main.model.Player;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Controller for the inventory screen.
 */
public class InventoryController implements Initializable {
    
    @FXML private Button allTabButton;
    @FXML private Button weaponsTabButton;
    @FXML private Button armorTabButton;
    @FXML private Button consumablesTabButton;
    @FXML private Button miscTabButton;
    @FXML private GridPane inventoryGrid;
    @FXML private VBox itemInfoBox;
    @FXML private Label selectedItemNameLabel;
    @FXML private Label selectedItemDescLabel;
    @FXML private Button useItemButton;
    @FXML private Button equipItemButton;
    @FXML private Button dropItemButton;
    @FXML private Button closeButton;
    
    private MainControllerNew mainController;
    private Player player;
    private String currentFilter = "ALL";
    private Item selectedItem;
    
    public void setMainController(MainControllerNew mainController) {
        this.mainController = mainController;
    }
    
    public void setPlayer(Player player) {
        this.player = player;
        refreshInventory();
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Controller initialization
    }
    
    @FXML
    private void handleFilterAll() {
        currentFilter = "ALL";
        refreshInventory();
    }
    
    @FXML
    private void handleFilterWeapons() {
        currentFilter = "WEAPON";
        refreshInventory();
    }
    
    @FXML
    private void handleFilterArmor() {
        currentFilter = "ARMOR";
        refreshInventory();
    }
    
    @FXML
    private void handleFilterConsumables() {
        currentFilter = "CONSUMABLE";
        refreshInventory();
    }
    
    @FXML
    private void handleFilterMisc() {
        currentFilter = "MISC";
        refreshInventory();
    }
    
    private void refreshInventory() {
        if (inventoryGrid == null || player == null) return;
        
        inventoryGrid.getChildren().clear();
        
        List<Item> items = player.getInventory();
        int col = 0;
        int row = 0;
        int colsPerRow = 4;
        
        for (Item item : items) {
            if (shouldShowItem(item)) {
                Button itemButton = createItemButton(item);
                inventoryGrid.add(itemButton, col, row);
                col++;
                if (col >= colsPerRow) {
                    col = 0;
                    row++;
                }
            }
        }
    }
    
    private boolean shouldShowItem(Item item) {
        if ("ALL".equals(currentFilter)) return true;
        // TODO: Implement proper item type filtering
        return true;
    }
    
    private Button createItemButton(Item item) {
        Button button = new Button(item.getName());
        button.setPrefWidth(120);
        button.setPrefHeight(120);
        button.setStyle("-fx-background-color: -color-surface-elevated; -fx-border-color: -color-border;");
        button.setOnAction(e -> selectItem(item));
        return button;
    }
    
    private void selectItem(Item item) {
        selectedItem = item;
        if (selectedItemNameLabel != null) {
            selectedItemNameLabel.setText(item.getName());
        }
        if (selectedItemDescLabel != null) {
            selectedItemDescLabel.setText(item.getDescription());
        }
        if (itemInfoBox != null) {
            itemInfoBox.setVisible(true);
        }
        
        // Update button states
        if (useItemButton != null) {
            // Items are usable if they're consumables or can be equipped
            boolean usable = item.getType() == Item.ItemType.CONSUMABLE || 
                           item.getType() == Item.ItemType.WEAPON || 
                           item.getType() == Item.ItemType.ARMOR ||
                           item.getType() == Item.ItemType.ACCESSORY;
            useItemButton.setDisable(!usable);
        }
        if (equipItemButton != null) {
            // Items are equippable if they're weapons, armor, or accessories
            boolean equippable = item.getType() == Item.ItemType.WEAPON || 
                               item.getType() == Item.ItemType.ARMOR ||
                               item.getType() == Item.ItemType.ACCESSORY;
            equipItemButton.setDisable(!equippable);
        }
    }
    
    @FXML
    private void handleUseItem() {
        if (selectedItem != null && player != null) {
            player.useItem(selectedItem.getName());
            refreshInventory();
            itemInfoBox.setVisible(false);
            selectedItem = null;
        }
    }
    
    @FXML
    private void handleEquipItem() {
        if (selectedItem != null && player != null) {
            // TODO: Implement equip logic
            refreshInventory();
            itemInfoBox.setVisible(false);
            selectedItem = null;
        }
    }
    
    @FXML
    private void handleDropItem() {
        if (selectedItem != null && player != null) {
            // Remove item from inventory
            List<Item> inventory = player.getInventory();
            inventory.remove(selectedItem);
            refreshInventory();
            itemInfoBox.setVisible(false);
            selectedItem = null;
        }
    }
    
    @FXML
    private void handleClose() {
        if (mainController != null) {
            mainController.resumeGame();
        }
    }
}

