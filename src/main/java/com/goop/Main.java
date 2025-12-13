package com.goop;

import com.goop.data.DataStore;
import com.goop.utils.SceneManager;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Main Class - Entry point aplikasi GOOP
 * 
 * Class ini extends javafx.application.Application
 * untuk membuat JavaFX application
 * 
 * Lifecycle JavaFX Application:
 * 1. init() - Initialize (optional, tidak override di sini)
 * 2. start(Stage) - Main entry point, dipanggil otomatis
 * 3. stop() - Cleanup saat aplikasi ditutup (optional)
 * 
 * @author GOOP Development Team
 * @version 1.0
 */
public class Main extends Application {

    /**
     * Method start() - Entry point JavaFX application
     * Dipanggil otomatis oleh JavaFX framework
     * 
     * @param primaryStage Primary stage yang disediakan JavaFX
     */
    @Override
    public void start(Stage primaryStage) {
        try {
            System.out.println("╔════════════════════════════════════════════╗");
            System.out.println("║     GOOP Application Starting...           ║");
            System.out.println("║  Game Object-Oriented Programming System   ║");
            System.out.println("╚════════════════════════════════════════════╝\n");

            // Initialize DataStore (Singleton pattern)
            // Ini akan men-trigger initializeSampleData()
            DataStore dataStore = DataStore.getInstance();
            System.out.println("✓ DataStore initialized\n");

            // Set primary stage ke SceneManager
            SceneManager.setPrimaryStage(primaryStage);
            System.out.println("✓ SceneManager initialized");

            // Load scene pertama (Login)
            SceneManager.loadScene("login.fxml");
            System.out.println("✓ Login scene loaded\n");

            System.out.println("╔════════════════════════════════════════════╗");
            System.out.println("║     GOOP Application Started Successfully!  ║");
            System.out.println("╚════════════════════════════════════════════╝");

        } catch (Exception e) {
            System.err.println("ERROR: Failed to start application");
            e.printStackTrace();
        }
    }

    /**
     * Method stop() - Dipanggil saat aplikasi ditutup
     * Override untuk cleanup jika diperlukan
     */
    @Override
    public void stop() {
        System.out.println("\n╔════════════════════════════════════════════╗");
        System.out.println("║     GOOP Application Shutting Down...       ║");
        System.out.println("╚════════════════════════════════════════════╝");

        // Logout user jika masih login
        DataStore dataStore = DataStore.getInstance();
        if (dataStore.isLoggedIn()) {
            dataStore.logout();
        }

        System.out.println("✓ Application closed successfully");
    }

    /**
     * Main method - Standard Java entry point
     * 
     * Method ini memanggil launch() yang akan:
     * 1. Mendeploy JavaFX runtime
     * 2. Membuat instance dari Main class
     * 3. Memanggil method start()
     * 
     * @param args Command line arguments
     */
    public static void main(String[] args) {
        // Launch JavaFX application
        // Ini akan memanggil start() method
        launch(args);
    }
}
