package com.goop.controllers;

import com.goop.data.DataStore;
import com.goop.models.Guru;
import com.goop.models.Siswa;
import com.goop.models.User;
import com.goop.utils.SceneManager;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

/**
 * DashboardController - Controller untuk halaman dashboard
 * 
 * Dashboard adalah halaman utama setelah login
 * Menampilkan:
 * - Info user yang login
 * - Score boxes (Skor Kognitif, Jumlah Proyek, dll)
 * - Menu navigasi untuk akses fitur lain
 * 
 * Konsep OOP:
 * - ENCAPSULATION: Private fields dengan public methods
 * - COMPOSITION: Controller menggunakan DataStore
 * - EVENT HANDLING: Handle button clicks dari menu
 * 
 * @author GOOP Development Team
 * @version 1.0
 */
public class DashboardController {

    // ==================== FXML INJECTED FIELDS ====================

    @FXML
    private Text welcomeText;

    @FXML
    private Label userNameLabel;

    @FXML
    private Label userRoleLabel;

    @FXML
    private Label skorKognitifLabel;

    @FXML
    private Label jumlahProyekLabel;

    @FXML
    private Label jumlahMateriLabel;

    @FXML
    private Label jumlahTesLabel;

    @FXML
    private VBox contentArea;

    @FXML
    private GridPane scoreGrid;

    @FXML
    private VBox infoCard;

    // Menu buttons
    @FXML
    private Button btnDashboard;

    @FXML
    private Button btnMateri;

    @FXML
    private Button btnProyek;

    @FXML
    private Button btnTes;

    @FXML
    private Button btnHasil;

    // Guru-only menu buttons
    @FXML
    private Button btnManageSiswa;

    @FXML
    private Button btnMonitorNilai;

    @FXML
    private Separator guruSeparator;

    @FXML
    private Button btnCreateProyek, btnCreateMateri, btnCreateTes;

    // ==================== INSTANCE VARIABLES ====================

    /**
     * DataStore instance
     */
    private DataStore dataStore;

    /**
     * Current logged in user
     */
    private User currentUser;

    // ==================== INITIALIZATION ====================

    /**
     * Initialize method - dipanggil otomatis setelah FXML loaded
     */
    @FXML
    public void initialize() {
        System.out.println("DashboardController initialized");

        // Get DataStore
        dataStore = DataStore.getInstance();

        // Get current user
        currentUser = dataStore.getCurrentUser();

        if (currentUser != null) {
            // Load user data ke UI
            loadUserData();

            // Load dashboard statistics
            loadStatistics();

            // Set active menu item
            setActiveMenuItem(btnDashboard);

        } else {
            // Tidak ada user login, redirect ke login
            System.err.println("ERROR: No user logged in!");
            SceneManager.loadScene("login.fxml");
        }
    }

    /**
     * Load data user ke UI elements
     */
    private void loadUserData() {
        // Set welcome text
        welcomeText.setText("Selamat Datang, " + currentUser.getNamaLengkap() + "!");

        // Set user info di sidebar
        userNameLabel.setText(currentUser.getNamaLengkap());
        userRoleLabel.setText(currentUser.getRole());

        System.out.println("✓ User data loaded: " + currentUser.getNamaLengkap());
    }

    /**
     * Load statistics untuk dashboard
     * Menghitung dan menampilkan berbagai metrics
     */
    private void loadStatistics() {
        try {
            // Jika user adalah siswa, tampilkan statistics siswa
            if (currentUser instanceof Siswa) {
                Siswa siswa = (Siswa) currentUser;

                // Skor kognitif siswa
                double skor = siswa.getSkorKognitif();
                skorKognitifLabel.setText(String.format("%.0f", skor));

                // Jumlah proyek siswa
                int jumlahProyek = dataStore.getProyekBySiswaId(siswa.getId()).size();
                jumlahProyekLabel.setText(String.valueOf(jumlahProyek));

                // Menu untuk siswa: semua menu visible
                btnTes.setVisible(true);
                btnTes.setManaged(true);
                btnHasil.setVisible(true);
                btnHasil.setManaged(true);
                btnProyek.setVisible(true);
                btnProyek.setManaged(true);

            } else if (currentUser instanceof Guru) {
                // Untuk guru, tampilkan statistik berbeda
                Guru guru = (Guru) currentUser;

                // Total siswa (ditampilkan di box "Skor Kognitif")
                int totalSiswa = dataStore.getAllSiswa().size();
                skorKognitifLabel.setText(String.valueOf(totalSiswa));

                // Total proyek semua siswa
                int totalProyek = dataStore.getAllProyek().size();
                jumlahProyekLabel.setText(String.valueOf(totalProyek));

                // Menu untuk guru: sembunyikan tes & hasil siswa
                btnTes.setVisible(false);
                btnTes.setManaged(false);
                btnHasil.setVisible(false);
                btnHasil.setManaged(false);

                // Show Guru-specific menus
                guruSeparator.setVisible(true);
                guruSeparator.setManaged(true);
                btnManageSiswa.setVisible(true);
                btnManageSiswa.setManaged(true);
                btnMonitorNilai.setVisible(true);
                btnMonitorNilai.setManaged(true);

                btnCreateProyek.setVisible(true);
                btnCreateProyek.setManaged(true);

                btnCreateMateri.setVisible(true);
                btnCreateMateri.setManaged(true);

                btnCreateTes.setVisible(true);
                btnCreateTes.setManaged(true);

                // Guru bisa lihat semua proyek dan materi
                btnProyek.setVisible(true);
                btnProyek.setManaged(true);
                btnMateri.setVisible(true);
                btnMateri.setManaged(true);
            }

            // Statistics yang sama untuk semua role

            // Jumlah materi
            int jumlahMateri = dataStore.getAllMateri().size();
            jumlahMateriLabel.setText(String.valueOf(jumlahMateri));

            // Jumlah tes kognitif
            int jumlahTes = dataStore.getActiveTesKognitif().size();
            jumlahTesLabel.setText(String.valueOf(jumlahTes));

            System.out.println("✓ Statistics loaded");

        } catch (Exception e) {
            System.err.println("ERROR loading statistics: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // ==================== MENU NAVIGATION HANDLERS ====================

    /**
     * Show dashboard view (current view)
     */
    @FXML
    private void showDashboard() {
        System.out.println("Dashboard menu clicked");
        setActiveMenuItem(btnDashboard);

        // Reload statistics
        loadStatistics();

        // Show score grid dan info card
        scoreGrid.setVisible(true);
        scoreGrid.setManaged(true);
        infoCard.setVisible(true);
        infoCard.setManaged(true);

        SceneManager.showInfo("Info", "Anda sudah berada di halaman Dashboard!");
    }

    /**
     * Show materi pembelajaran
     */
    @FXML
    private void showMateri() {
        System.out.println("Materi menu clicked");
        SceneManager.loadScene("materi.fxml");
        SceneManager.setTitle("Materi Pembelajaran");
    }

    /**
     * Show proyek management
     */
    @FXML
    private void showProyek() {
        System.out.println("Proyek menu clicked");
        SceneManager.loadScene("proyek.fxml");
        SceneManager.setTitle("Daftar Proyek");
    }

    /**
     * Show tes kognitif
     */
    @FXML
    private void showTes() {
        System.out.println("Tes menu clicked");
        SceneManager.loadScene("tes-kognitif.fxml");
        SceneManager.setTitle("Tes Kognitif");
    }

    /**
     * Show hasil belajar
     */
    @FXML
    private void showHasil() {
        System.out.println("Hasil menu clicked");
        SceneManager.loadScene("hasil.fxml");
        SceneManager.setTitle("Hasil Belajar");
    }

    /**
     * Show manajemen siswa (Guru only)
     */
    @FXML
    private void showManageSiswa() {
        System.out.println("Manage Siswa menu clicked");
        SceneManager.loadScene("manage-siswa.fxml");
        SceneManager.setTitle("Kelola Siswa");
    }

    /**
     * Show monitor nilai siswa (Guru only)
     */
    @FXML
    private void showMonitorNilai() {
        System.out.println("Monitor Nilai menu clicked");
        SceneManager.loadScene("monitor-nilai.fxml");
        SceneManager.setTitle("Monitor Nilai Siswa");
    }

    /**
     * Show create proyek (Guru only)
     */
    @FXML
    private void showCreateProyek() {
        SceneManager.loadScene("create-proyek.fxml");
        SceneManager.setTitle("Buat Proyek Baru");
    }

    /**
     * Show create materi (Guru only)
     */
    @FXML
    private void showCreateMateri() {
        SceneManager.loadScene("create-materi.fxml");
        SceneManager.setTitle("Buat Materi Baru");
    }

    /**
     * Show create tes (Guru only)
     */
    @FXML
    private void showCreateTes() {
        SceneManager.loadScene("create-tes.fxml");
        SceneManager.setTitle("Buat Tes Kognitif Baru");
    }

    // ==================== OTHER HANDLERS ====================

    /**
     * Handle logout button click
     */
    @FXML
    private void handleLogout() {
        System.out.println("Logout button clicked");

        // Konfirmasi logout
        boolean confirmed = SceneManager.showConfirmation(
                "Konfirmasi Logout",
                "Apakah Anda yakin ingin logout?");

        if (confirmed) {
            // Logout dari DataStore
            dataStore.logout();

            // Navigate kembali ke login
            SceneManager.loadScene("login.fxml");
            SceneManager.setTitle("Login");

            System.out.println("✓ Logout successful");
        }
    }

    // ==================== HELPER METHODS ====================

    /**
     * Set active menu item
     * Remove active class dari semua menu, lalu add ke menu yang dipilih
     * 
     * @param activeButton Button yang active
     */
    private void setActiveMenuItem(Button activeButton) {
        // Remove active class dari semua menu buttons
        btnDashboard.getStyleClass().remove("active");
        btnMateri.getStyleClass().remove("active");
        btnProyek.getStyleClass().remove("active");
        btnTes.getStyleClass().remove("active");
        btnHasil.getStyleClass().remove("active");

        // Add active class ke button yang dipilih
        if (!activeButton.getStyleClass().contains("active")) {
            activeButton.getStyleClass().add("active");
        }
    }
}
