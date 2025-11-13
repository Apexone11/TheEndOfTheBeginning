package gameproject.ui;

import javafx.animation.FadeTransition;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import gameproject.Settings;
import gameproject.MainControllerNew;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * UI Router for managing screen navigation and transitions.
 * Handles loading FXML screens and switching between them with animations.
 * 
 * @version 5.0.0
 */
public class UiRouter {
    
    private final StackPane rootContainer;
    private final Map<String, Node> screenCache;
    private final Settings settings;
    private MainControllerNew mainController;
    private String currentScreen;
    
    public UiRouter(StackPane rootContainer, Settings settings) {
        this.rootContainer = rootContainer;
        this.settings = settings;
        this.screenCache = new HashMap<>();
        this.currentScreen = null;
    }
    
    public void setMainController(MainControllerNew mainController) {
        this.mainController = mainController;
    }
    
    /**
     * Navigate to a screen by name.
     * 
     * @param screenName The name of the screen (without .fxml extension)
     * @return true if navigation was successful
     */
    public boolean navigateTo(String screenName) {
        try {
            Node screen = getOrLoadScreen(screenName);
            if (screen == null) {
                System.err.println("Failed to load screen: " + screenName);
                return false;
            }
            
            // Transition to new screen
            transitionToScreen(screen);
            currentScreen = screenName;
            return true;
        } catch (Exception e) {
            System.err.println("Error navigating to screen " + screenName + ": " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Get or load a screen from cache or disk.
     */
    private Node getOrLoadScreen(String screenName) throws IOException {
        // Check cache first
        if (screenCache.containsKey(screenName)) {
            return screenCache.get(screenName);
        }
        
        // Load from FXML
        String fxmlPath = "/ui/" + screenName + ".fxml";
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
        Node screen = loader.load();
        
        // Wire up button handlers directly if mainController is available
        if (mainController != null) {
            mainController.wireScreenButtons(screenName, screen);
        }
        
        // Cache the screen
        screenCache.put(screenName, screen);
        
        return screen;
    }
    
    /**
     * Transition to a new screen with animation.
     */
    private void transitionToScreen(Node newScreen) {
        // Respect reduced motion setting
        boolean useAnimations = settings != null && !settings.reducedMotion;
        
        if (!useAnimations || rootContainer.getChildren().isEmpty()) {
            // No animation - instant switch
            rootContainer.getChildren().clear();
            rootContainer.getChildren().add(newScreen);
            return;
        }
        
        // Fade transition
        Node oldScreen = rootContainer.getChildren().isEmpty() ? null : 
                        rootContainer.getChildren().get(0);
        
        if (oldScreen != null) {
            // Fade out old screen
            FadeTransition fadeOut = new FadeTransition(Duration.millis(200), oldScreen);
            fadeOut.setFromValue(1.0);
            fadeOut.setToValue(0.0);
            fadeOut.setOnFinished(e -> {
                rootContainer.getChildren().remove(oldScreen);
            });
            fadeOut.play();
        }
        
        // Fade in new screen
        newScreen.setOpacity(0.0);
        rootContainer.getChildren().add(newScreen);
        
        FadeTransition fadeIn = new FadeTransition(Duration.millis(200), newScreen);
        fadeIn.setFromValue(0.0);
        fadeIn.setToValue(1.0);
        fadeIn.play();
    }
    
    /**
     * Clear screen cache (useful for reloading screens).
     */
    public void clearCache() {
        screenCache.clear();
    }
    
    /**
     * Clear cache for a specific screen.
     */
    public void clearCache(String screenName) {
        screenCache.remove(screenName);
    }
    
    /**
     * Get the current screen name.
     */
    public String getCurrentScreen() {
        return currentScreen;
    }
    
    /**
     * Get the root container.
     */
    public StackPane getRootContainer() {
        return rootContainer;
    }
}

