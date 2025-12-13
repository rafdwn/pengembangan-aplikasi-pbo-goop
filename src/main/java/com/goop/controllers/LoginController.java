package com.goop.controllers;

import com.goop.data.DataStore;
import com.goop.models.Guru;
import com.goop.models.Siswa;
import com.goop.models.User;
import com.goop.utils.SceneManager;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 * LoginController - Controller untuk halaman login
 * 
 * MVC PATTERN:
 * - Model: User, Siswa, Guru (data dan business logic)
 * - View: login.fxml (UI layout dan design)
 * - Controller: LoginController ini (mengatur interaksi user dengan UI)
 * 
 * Konsep yang diterapkan:
 * - SEPARATION OF CONCERNS: UI (FXML) terpisah dari logic (Controller)
 * - EVENT HANDLING: Handle button click dan keyboard input
 * - DEPENDENCY: Controller depend pada DataStore dan SceneManager
 * 
 * Cara kerja:
 * 1. User input username dan password
 * 2. User klik button Login (atau tekan Enter)
 * 3. Controller validasi via DataStore.login()
 * 4. Jika berhasil, navigate ke dashboard
 * 5. Jika gagal, tampilkan error message
 * 
 * @author GOOP Development Team
 * @version 1.0
 */
public class LoginController {

    // ==================== FXML INJECTED FIELDS ====================
    // @FXML annotation: inject/link elemen UI dari FXML file
    // fx:id di FXML harus sama dengan variable name di sini

    /**
     * TextField untuk input username
     * Linked dengan fx:id="usernameField" di login.fxml
     */
    @FXML
    private TextField usernameField;

    /**
     * PasswordField untuk input password
     * Linked dengan fx:id="passwordField" di login.fxml
     */
    @FXML
    private PasswordField passwordField;

    /**
     * Label untuk menampilkan error message
     * Linked dengan fx:id="errorLabel" di login.fxml
     */
    @FXML
    private Label errorLabel;

    /**
     * Button untuk submit login
     * Linked dengan fx:id="loginButton" di login.fxml
     */
    @FXML
    private Button loginButton;

    // ==================== INSTANCE VARIABLES ====================

    /**
     * Instance dari DataStore (Singleton)
     * Digunakan untuk autentikasi
     */
    private DataStore dataStore;

    // ==================== INITIALIZATION ====================

    /**
     * Method initialize() dipanggil otomatis oleh JavaFX
     * setelah FXML file di-load dan setelah @FXML fields di-inject
     * 
     * Digunakan untuk setup awal:
     * - Initialize DataStore
     * - Setup event handlers
     * - Set default values
     */
    @FXML
    public void initialize() {
        System.out.println("LoginController initialized");

        // Get DataStore instance (Singleton pattern)
        dataStore = DataStore.getInstance();

        // Setup keyboard event handler
        // Agar bisa login dengan tekan Enter
        setupKeyboardHandlers();

        // Set focus ke username field saat halaman load
        usernameField.requestFocus();
    }

    /**
     * Setup keyboard event handlers
     * User bisa tekan Enter untuk submit login tanpa klik button
     */
    private void setupKeyboardHandlers() {
        // Handler untuk password field
        // Jika user tekan Enter di password field, trigger login
        passwordField.setOnKeyPressed(this::handleKeyPress);

        // Handler untuk username field
        usernameField.setOnKeyPressed(this::handleKeyPress);
    }

    /**
     * Handle key press event
     * 
     * @param event KeyEvent dari keyboard
     */
    private void handleKeyPress(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            handleLogin();
        }
    }

    // ==================== EVENT HANDLERS ====================

    /**
     * Handle login button click
     * Method ini dipanggil saat user klik button "Login"
     * Linked via onAction="#handleLogin" di login.fxml
     * 
     * Alur login:
     * 1. Ambil input username dan password
     * 2. Validasi input tidak kosong
     * 3. Call DataStore.login() untuk autentikasi
     * 4. Jika berhasil, navigate ke dashboard
     * 5. Jika gagal, tampilkan error
     */
    @FXML
    private void handleLogin() {
        // Log untuk debugging
        System.out.println("Login button clicked");

        // Ambil input dari UI
        String username = usernameField.getText().trim();
        String password = passwordField.getText();

        // Validasi input tidak boleh kosong
        if (username.isEmpty() || password.isEmpty()) {
            showError("Username dan password tidak boleh kosong!");
            return;
        }

        // Disable button sementara untuk mencegah double click
        loginButton.setDisable(true);

        // Lakukan autentikasi via DataStore
        User user = dataStore.login(username, password);

        // Cek hasil login
        if (user != null) {
            // Login berhasil!
            System.out.println("✓ Login successful: " + user.getNamaLengkap() + " (" + user.getRole() + ")");

            // Hide error message jika ada
            hideError();

            // Navigate ke dashboard
            // Logika berbeda untuk Siswa dan Guru bisa ditambahkan di sini
            navigateToDashboard(user);

        } else {
            // Login gagal
            System.out.println("✗ Login failed: Invalid credentials");

            // Tampilkan error message
            showError("Username atau password salah!");

            // Clear password field for security
            passwordField.clear();

            // Re-enable button
            loginButton.setDisable(false);

            // Focus kembali ke username field
            usernameField.requestFocus();
        }
    }

    // ==================== HELPER METHODS ====================

    /**
     * Navigate ke dashboard berdasarkan role user
     * 
     * @param user User yang berhasil login
     */
    private void navigateToDashboard(User user) {
        try {
            // Check role user
            if (user instanceof Siswa) {
                // User adalah Siswa
                System.out.println("Loading dashboard for Siswa...");
                SceneManager.loadScene("dashboard.fxml");
                SceneManager.setTitle("Dashboard - " + user.getNamaLengkap());

            } else if (user instanceof Guru) {
                // User adalah Guru
                System.out.println("Loading dashboard for Guru...");
                SceneManager.loadScene("dashboard.fxml");
                SceneManager.setTitle("Dashboard - " + user.getNamaLengkap());

            } else {
                // Fallback (seharusnya tidak terjadi)
                System.out.println("Loading default dashboard...");
                SceneManager.loadScene("dashboard.fxml");
            }

        } catch (Exception e) {
            System.err.println("ERROR: Failed to navigate to dashboard");
            e.printStackTrace();
            showError("Gagal memuat dashboard. Silakan coba lagi.");
            loginButton.setDisable(false);
        }
    }

    /**
     * Tampilkan error message
     * 
     * @param message Error message yang akan ditampilkan
     */
    private void showError(String message) {
        errorLabel.setText(message);
        errorLabel.setVisible(true);
        errorLabel.setManaged(true);

        // Optional: Add animated effect (shake animation, red background, dll)
    }

    /**
     * Sembunyikan error message
     */
    private void hideError() {
        errorLabel.setVisible(false);
        errorLabel.setManaged(false);
    }
}
