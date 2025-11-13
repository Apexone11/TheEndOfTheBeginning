package gameproject.ui.menu;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import gameproject.MainControllerNew;
import gameproject.Settings;
import gameproject.audio.AudioManager;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller for the settings screen.
 */
public class SettingsController implements Initializable {
    
    @FXML private Slider masterVolumeSlider;
    @FXML private Slider musicVolumeSlider;
    @FXML private Slider sfxVolumeSlider;
    @FXML private Slider uiScaleSlider;
    @FXML private Label masterVolumeLabel;
    @FXML private Label musicVolumeLabel;
    @FXML private Label sfxVolumeLabel;
    @FXML private Label uiScaleLabel;
    @FXML private CheckBox highContrastCheckBox;
    @FXML private CheckBox reducedMotionCheckBox;
    @FXML private CheckBox colorBlindModeCheckBox;
    @FXML private CheckBox confirmationsCheckBox;
    @FXML private CheckBox autoSaveCheckBox;
    @FXML private Button closeButton;
    @FXML private Button resetButton;
    @FXML private Button saveButton;
    
    private MainControllerNew mainController;
    private Settings settings;
    private AudioManager audioManager;
    
    public void setMainController(MainControllerNew mainController) {
        this.mainController = mainController;
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        settings = Settings.load();
        audioManager = AudioManager.getInstance();
        
        // Load current settings
        loadSettings();
        
        // Setup slider listeners
        masterVolumeSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            int percent = (int)(newVal.doubleValue() * 100);
            masterVolumeLabel.setText(percent + "%");
        });
        
        musicVolumeSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            int percent = (int)(newVal.doubleValue() * 100);
            musicVolumeLabel.setText(percent + "%");
        });
        
        sfxVolumeSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            int percent = (int)(newVal.doubleValue() * 100);
            sfxVolumeLabel.setText(percent + "%");
        });
        
        uiScaleSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            int percent = (int)(newVal.doubleValue() * 100);
            uiScaleLabel.setText(percent + "%");
        });
    }
    
    private void loadSettings() {
        masterVolumeSlider.setValue(settings.masterVolume);
        musicVolumeSlider.setValue(settings.musicVolume);
        sfxVolumeSlider.setValue(settings.sfxVolume);
        uiScaleSlider.setValue(settings.uiScale);
        highContrastCheckBox.setSelected(settings.highContrast);
        reducedMotionCheckBox.setSelected(settings.reducedMotion);
        colorBlindModeCheckBox.setSelected(settings.colorBlindMode);
        confirmationsCheckBox.setSelected(settings.confirmations);
        autoSaveCheckBox.setSelected(settings.autoSaveEnabled);
        
        // Update labels
        masterVolumeLabel.setText((int)(settings.masterVolume * 100) + "%");
        musicVolumeLabel.setText((int)(settings.musicVolume * 100) + "%");
        sfxVolumeLabel.setText((int)(settings.sfxVolume * 100) + "%");
        uiScaleLabel.setText((int)(settings.uiScale * 100) + "%");
    }
    
    @FXML
    private void handleMasterVolumeChange() {
        settings.masterVolume = masterVolumeSlider.getValue();
        audioManager.setMasterVolume(settings.masterVolume);
    }
    
    @FXML
    private void handleMusicVolumeChange() {
        settings.musicVolume = musicVolumeSlider.getValue();
        audioManager.setMusicVolume(settings.musicVolume);
    }
    
    @FXML
    private void handleSfxVolumeChange() {
        settings.sfxVolume = sfxVolumeSlider.getValue();
        audioManager.setSoundVolume(settings.sfxVolume);
    }
    
    @FXML
    private void handleUiScaleChange() {
        settings.uiScale = uiScaleSlider.getValue();
        if (mainController != null) {
            mainController.applyUiScale(settings.uiScale);
        }
    }
    
    @FXML
    private void handleHighContrastToggle() {
        settings.highContrast = highContrastCheckBox.isSelected();
        if (mainController != null) {
            mainController.applySettings(); // Now public
        }
    }
    
    @FXML
    private void handleReducedMotionToggle() {
        settings.reducedMotion = reducedMotionCheckBox.isSelected();
    }
    
    @FXML
    private void handleColorBlindModeToggle() {
        settings.colorBlindMode = colorBlindModeCheckBox.isSelected();
        if (mainController != null) {
            mainController.applySettings(); // Now public
        }
    }
    
    @FXML
    private void handleConfirmationsToggle() {
        settings.confirmations = confirmationsCheckBox.isSelected();
    }
    
    @FXML
    private void handleAutoSaveToggle() {
        settings.autoSaveEnabled = autoSaveCheckBox.isSelected();
    }
    
    @FXML
    private void handleReset() {
        settings = new Settings(); // Reset to defaults
        loadSettings();
    }
    
    @FXML
    private void handleSave() {
        settings.save();
        if (mainController != null) {
            mainController.showMainMenu();
        }
    }
    
    @FXML
    private void handleClose() {
        if (mainController != null) {
            mainController.showMainMenu();
        }
    }
}

