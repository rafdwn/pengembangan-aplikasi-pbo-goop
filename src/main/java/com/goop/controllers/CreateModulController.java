package com.goop.controllers;

import com.goop.data.DataStore;
import com.goop.models.Guru;
import com.goop.models.Modul;
import com.goop.utils.SceneManager;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class CreateModulController {

    @FXML
    private TextField txtJudul;

    @FXML
    private TextArea txtKonten;

    @FXML
    private TextField txtTopik;

    private DataStore dataStore;
    private Guru currentGuru;

    public void initialize() {
        dataStore = DataStore.getInstance();
        currentGuru = (Guru) dataStore.getCurrentUser();
    }

    @FXML
    private void handleCreateModul() {
        String judul = txtJudul.getText().trim();
        String konten = txtKonten.getText().trim();
        String topik = txtTopik.getText().trim();

        // Validation
        if (judul.isEmpty() || konten.isEmpty() || topik.isEmpty()) {
            SceneManager.showError("Error", "Semua field harus diisi!");
            return;
        }

        // Create modul
        int modulId = dataStore.createModul(judul, konten, topik, currentGuru.getId());

        if (modulId > 0) {
            SceneManager.showInfo("Sukses", "Modul '" + judul + "' berhasil dibuat!");
            handleReset();
        } else {
            SceneManager.showError("Error", "Gagal membuat modul. Silakan coba lagi.");
        }
    }

    @FXML
    private void handleReset() {
        txtJudul.clear();
        txtKonten.clear();
        txtTopik.clear();
    }

    @FXML
    private void handleBack() {
        SceneManager.loadScene("dashboard.fxml");
    }
}
