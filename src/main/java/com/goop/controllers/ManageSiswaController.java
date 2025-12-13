package com.goop.controllers;

import com.goop.data.DataStore;
import com.goop.models.Siswa;
import com.goop.utils.SceneManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.util.List;

/**
 * ManageSiswaController - Controller untuk kelola data siswa (Guru)
 * Fitur: Tambah, Edit, Hapus siswa
 */
public class ManageSiswaController {

    @FXML
    private TableView<Siswa> siswaTable;

    @FXML
    private VBox detailPanel;

    @FXML
    private Text formTitle;

    @FXML
    private TextField txtNama, txtUsername, txtEmail;

    @FXML
    private PasswordField txtPassword;

    private DataStore dataStore;
    private Siswa selectedSiswa;
    private boolean isEditMode = false;

    @FXML
    public void initialize() {
        dataStore = DataStore.getInstance();
        loadSiswa();

        // Selection listener
        siswaTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            selectedSiswa = newSelection;
        });
    }

    private void loadSiswa() {
        List<Siswa> siswaList = dataStore.getAllSiswa();
        ObservableList<Siswa> observableList = FXCollections.observableArrayList(siswaList);
        siswaTable.setItems(observableList);
    }

    @FXML
    private void handleTambahSiswa() {
        isEditMode = false;
        formTitle.setText("Tambah Siswa Baru");
        clearForm();
        detailPanel.setVisible(true);
        detailPanel.setManaged(true);
    }

    @FXML
    private void handleSimpan() {
        String nama = txtNama.getText().trim();
        String username = txtUsername.getText().trim();
        String password = txtPassword.getText();
        String email = txtEmail.getText().trim();

        // Validation
        if (nama.isEmpty() || username.isEmpty() || password.isEmpty()) {
            SceneManager.showError("Error", "Nama, Username, dan Password harus diisi!");
            return;
        }

        if (!isEditMode) {
            // Tambah siswa baru
            int newId = dataStore.getAllSiswa().size() + 1;

            // Constructor: id, username, password, email, namaLengkap, nim, kelas
            Siswa siswa = new Siswa(newId, username, password, email, nama, "0000" + newId, "XII RPL");
            dataStore.addSiswa(siswa);
            SceneManager.showInfo("Berhasil", "Siswa baru berhasil ditambahkan!");
        } else {
            // Edit siswa existing
            if (selectedSiswa != null) {
                selectedSiswa.setNamaLengkap(nama);
                selectedSiswa.setUsername(username);
                if (!password.isEmpty()) {
                    selectedSiswa.setPassword(password);
                }
                selectedSiswa.setEmail(email);
                dataStore.updateSiswa(selectedSiswa);
                SceneManager.showInfo("Berhasil", "Data siswa berhasil diupdate!");
            }
        }

        loadSiswa();
        handleBatal();
    }

    @FXML
    private void handleBatal() {
        detailPanel.setVisible(false);
        detailPanel.setManaged(false);
        clearForm();
    }

    private void clearForm() {
        txtNama.clear();
        txtUsername.clear();
        txtPassword.clear();
        txtEmail.clear();
        selectedSiswa = null;
    }

    @FXML
    private void handleBack() {
        SceneManager.loadScene("dashboard.fxml");
    }
}
