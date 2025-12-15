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

    // Score box subtitle labels (for dynamic text based on role)
    @FXML
    private Label skorKognitifTitle, skorKognitifSubLabel;
    @FXML
    private Label jumlahProyekSubLabel, jumlahMateriSubLabel, jumlahTesSubLabel;
    @FXML
    private Label jumlahModulLabel;

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
    private Button btnHistori;

    @FXML
    private Separator guruSeparator;

    @FXML
    private Button btnCreateProyek, btnCreateMateri, btnCreateTes, btnCreateModul;

    @FXML
    private Button btnModul;

    // ==================== INSTANCE VARIABLES ====================

    private DataStore dataStore;

    private User currentUser;

    // ==================== INITIALIZATION ====================

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

    private void loadUserData() {
        // Set welcome text
        welcomeText.setText("Selamat Datang, " + currentUser.getNamaLengkap() + "!");

        // Set user info di sidebar
        userNameLabel.setText(currentUser.getNamaLengkap());
        userRoleLabel.setText(currentUser.getRole());

        System.out.println("✓ User data loaded: " + currentUser.getNamaLengkap());
    }

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

                // Menu untuk siswa: hanya Dashboard, Tes Kognitif, dan Proyek (sesuai use case)
                btnTes.setVisible(true);
                btnTes.setManaged(true);

                btnProyek.setVisible(true);
                btnProyek.setManaged(true);

                // Hide Materi dan Hasil dari siswa (tidak ada di use case)
                btnMateri.setVisible(false);
                btnMateri.setManaged(false);

                btnHasil.setVisible(false);
                btnHasil.setManaged(false);

            } else if (currentUser instanceof Guru) {
                // Untuk guru, tampilkan statistik berbeda
                Guru guru = (Guru) currentUser;

                // Total siswa (ditampilkan di box "Skor Kognitif" → update jadi "Jumlah Siswa")
                int totalSiswa = dataStore.getAllSiswa().size();
                skorKognitifLabel.setText(String.valueOf(totalSiswa));

                // Update label text untuk Guru (ganti "Skor Kognitif" jadi "Jumlah Siswa")
                if (skorKognitifTitle != null) {
                    skorKognitifTitle.setText("Jumlah Siswa");
                }
                if (skorKognitifSubLabel != null) {
                    skorKognitifSubLabel.setText("siswa terdaftar");
                }

                // Total proyek semua siswa
                int totalProyek = dataStore.getAllProyek().size();
                jumlahProyekLabel.setText(String.valueOf(totalProyek));
                if (jumlahProyekSubLabel != null) {
                    jumlahProyekSubLabel.setText("proyek total");
                }

                // Menu untuk guru: sembunyikan menu siswa (tes untuk mengerjakan)
                btnTes.setVisible(false);
                btnTes.setManaged(false);

                // Show Guru-specific menus
                guruSeparator.setVisible(true);
                guruSeparator.setManaged(true);
                btnManageSiswa.setVisible(true);
                btnManageSiswa.setManaged(true);
                btnMonitorNilai.setVisible(true);
                btnMonitorNilai.setManaged(true);
                btnHistori.setVisible(true);
                btnHistori.setManaged(true);

                btnCreateProyek.setVisible(true);
                btnCreateProyek.setManaged(true);

                btnCreateMateri.setVisible(true);
                btnCreateMateri.setManaged(true);

                btnCreateTes.setVisible(true);
                btnCreateTes.setManaged(true);
                btnCreateModul.setVisible(true);
                btnCreateModul.setManaged(true);

                // Guru TIDAK perlu menu "Proyek" - hanya siswa yang lihat proyek mereka
                btnProyek.setVisible(false);
                btnProyek.setManaged(false);

                // Guru bisa lihat materi (Modul/Materi)
                btnMateri.setVisible(true);
                btnMateri.setManaged(true);
                btnModul.setVisible(true);
                btnModul.setManaged(true);

                // Display jumlah modul
                int totalModul = dataStore.getAllModul().size();
                if (jumlahModulLabel != null) {
                    jumlahModulLabel.setText(String.valueOf(totalModul));
                }

                // Guru TIDAK punya menu "Hasil Belajar" (tidak ada di use case diagram)
                btnHasil.setVisible(false);
                btnHasil.setManaged(false);
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

    @FXML
    private void showMateri() {
        System.out.println("Materi menu clicked");
        SceneManager.loadScene("materi.fxml");
        SceneManager.setTitle("Materi Pembelajaran");
    }

    @FXML
    private void showProyek() {
        System.out.println("Proyek menu clicked");
        SceneManager.loadScene("proyek.fxml");
        SceneManager.setTitle("Daftar Proyek");
    }

    @FXML
    private void showTes() {
        System.out.println("Tes menu clicked");
        SceneManager.loadScene("tes-kognitif.fxml");
        SceneManager.setTitle("Tes Kognitif");
    }

    @FXML
    private void showHasil() {
        System.out.println("Hasil menu clicked");
        SceneManager.loadScene("hasil.fxml");
        SceneManager.setTitle("Hasil Belajar");
    }

    @FXML
    private void showManageSiswa() {
        System.out.println("Manage Siswa menu clicked");
        SceneManager.loadScene("manage-siswa.fxml");
        SceneManager.setTitle("Kelola Siswa");
    }

    @FXML
    private void showMonitorNilai() {
        System.out.println("Monitor Nilai menu clicked");
        SceneManager.loadScene("monitor-nilai.fxml");
        SceneManager.setTitle("Monitor Nilai Siswa");
    }

    @FXML
    private void showCreateProyek() {
        SceneManager.loadScene("create-proyek.fxml");
        SceneManager.setTitle("Buat Proyek Baru");
    }

    @FXML
    private void showCreateMateri() {
        SceneManager.loadScene("create-materi.fxml");
        SceneManager.setTitle("Buat Materi Baru");
    }

    @FXML
    private void showCreateTes() {
        SceneManager.loadScene("create-tes.fxml");
        SceneManager.setTitle("Buat Tes Kognitif Baru");
    }

    @FXML
    private void showModul() {
        SceneManager.loadScene("modul.fxml");
        SceneManager.setTitle("Modul Pembelajaran");
    }

    @FXML
    private void showCreateModul() {
        SceneManager.loadScene("create-modul.fxml");
        SceneManager.setTitle("Buat Modul Baru");
    }

    @FXML
    private void handleHistoriPengerjaan() {
        setActiveMenuItem(btnHistori);
        SceneManager.loadScene("histori.fxml");
        SceneManager.setTitle("Histori Pengerjaan");
    }

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
