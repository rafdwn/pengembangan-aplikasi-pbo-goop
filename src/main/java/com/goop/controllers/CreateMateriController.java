package com.goop.controllers;

import com.goop.data.DataStore;
import com.goop.models.Materi;
import com.goop.utils.SceneManager;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class CreateMateriController {

    @FXML
    private TextField txtJudul, txtTopik;

    @FXML
    private TextArea txtKonten;

    private DataStore dataStore;

    @FXML
    public void initialize() {
        dataStore = DataStore.getInstance();
    }

    @FXML
    private void handleCreateMateri() {
        String judul = txtJudul.getText().trim();
        String topik = txtTopik.getText().trim();
        String konten = txtKonten.getText().trim();

        // Validation
        if (judul.isEmpty() || topik.isEmpty() || konten.isEmpty()) {
            SceneManager.showError("Error", "Semua field harus diisi!");
            return;
        }

        // Create materi with current guru's ID
        int guruId = dataStore.getCurrentUser().getId();
        Materi materi = new Materi(0, judul, konten, topik, guruId);
        dataStore.addMateri(materi);

        SceneManager.showInfo("Berhasil!",
                "Materi berhasil dibuat!\n\n" +
                        "Judul: " + judul + "\n" +
                        "Topik: " + topik);

        handleReset();
    }

    @FXML
    private void handleReset() {
        txtJudul.clear();
        txtTopik.clear();
        txtKonten.clear();
    }

    @FXML
    private void handleBack() {
        SceneManager.loadScene("dashboard.fxml");
    }
}
