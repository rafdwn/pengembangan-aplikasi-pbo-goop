package com.goop.utils;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Class SceneManager - Utility class untuk mengelola perpindahan scene/tampilan
 * 
 * Konsep yang diterapkan:
 * - UTILITY CLASS: Class dengan static methods untuk fungsi-fungsi umum
 * - ENCAPSULATION: Private primary stage, diakses via public methods
 * 
 * Cara kerja:
 * 1. Main app set primary stage via setPrimaryStage()
 * 2. Controller memanggil loadScene() untuk pindah halaman
 * 3. SceneManager load FXML file dan ganti scene
 * 
 * @author GOOP Development Team
 * @version 1.0
 */
public class SceneManager {

    /**
     * Primary stage aplikasi
     * Disimpan sebagai static agar bisa diakses dari mana saja
     */
    private static Stage primaryStage;

    /**
     * Base path untuk FXML files
     */
    private static final String FXML_PATH = "/fxml/";

    /**
     * Default width dan height untuk window
     */
    private static final int DEFAULT_WIDTH = 1200;
    private static final int DEFAULT_HEIGHT = 700;

    /**
     * Set primary stage aplikasi
     * Method ini dipanggil dari Main.java saat aplikasi start
     * 
     * @param stage Primary stage dari JavaFX application
     */
    public static void setPrimaryStage(Stage stage) {
        primaryStage = stage;

        // Set properties default untuk stage
        primaryStage.setTitle("GOOP - Game Object Oriented Programming");
        primaryStage.setWidth(DEFAULT_WIDTH);
        primaryStage.setHeight(DEFAULT_HEIGHT);
        primaryStage.setResizable(true);

        // Optional: Set minimum size
        primaryStage.setMinWidth(800);
        primaryStage.setMinHeight(600);
    }

    /**
     * Load dan tampilkan scene baru dari file FXML
     * 
     * Cara kerja:
     * 1. Load FXML file menggunakan FXMLLoader
     * 2. Create Scene baru dari root node
     * 3. Set scene ke primary stage
     * 4. Show stage
     * 
     * @param fxmlFileName Nama file FXML (contoh: "login.fxml")
     */
    public static void loadScene(String fxmlFileName) {
        try {
            // Log untuk debugging
            System.out.println("Loading scene: " + fxmlFileName);

            // Load FXML file
            String fxmlPath = FXML_PATH + fxmlFileName;
            FXMLLoader loader = new FXMLLoader(SceneManager.class.getResource(fxmlPath));
            Parent root = loader.load();

            // Create scene baru
            Scene scene = new Scene(root);

            // Load CSS stylesheet
            String cssPath = SceneManager.class.getResource("/css/style.css").toExternalForm();
            scene.getStylesheets().add(cssPath);

            // Set scene ke stage
            primaryStage.setScene(scene);

            // Center stage di screen
            primaryStage.centerOnScreen();

            // Show stage
            primaryStage.show();

            System.out.println("✓ Scene loaded successfully: " + fxmlFileName);

        } catch (IOException e) {
            System.err.println("ERROR: Failed to load scene: " + fxmlFileName);
            e.printStackTrace();

            // Show error dialog
            showError("Error Loading Scene",
                    "Failed to load " + fxmlFileName + "\n" + e.getMessage());
        }
    }

    /**
     * Load scene dengan custom width dan height
     * 
     * @param fxmlFileName Nama file FXML
     * @param width        Width window
     * @param height       Height window
     */
    public static void loadScene(String fxmlFileName, int width, int height) {
        primaryStage.setWidth(width);
        primaryStage.setHeight(height);
        loadScene(fxmlFileName);
    }

    /**
     * Get primary stage
     * Berguna jika controller perlu akses langsung ke stage
     * 
     * @return Primary stage
     */
    public static Stage getPrimaryStage() {
        return primaryStage;
    }

    /**
     * Set title window
     * 
     * @param title Title baru
     */
    public static void setTitle(String title) {
        if (primaryStage != null) {
            primaryStage.setTitle("GOOP - " + title);
        }
    }

    /**
     * Show error dialog
     * Simple error display menggunakan Alert
     * 
     * @param title   Title dialog
     * @param message Error message
     */
    public static void showError(String title, String message) {
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(
                javafx.scene.control.Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Show confirmation dialog
     * 
     * @param title   Title dialog
     * @param message Confirmation message
     * @return true jika user klik OK
     */
    public static boolean showConfirmation(String title, String message) {
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(
                javafx.scene.control.Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);

        javafx.scene.control.ButtonType result = alert.showAndWait().orElse(javafx.scene.control.ButtonType.CANCEL);
        return result == javafx.scene.control.ButtonType.OK;
    }

    /**
     * Show information dialog
     * 
     * @param title   Title dialog
     * @param message Information message
     */
    public static void showInfo(String title, String message) {
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(
                javafx.scene.control.Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
