package com.goop.controllers;

import com.goop.data.DataStore;
import com.goop.models.Soal;
import com.goop.models.TesKognitif;
import com.goop.utils.SceneManager;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;

/**
 * CreateTesController - Form untuk Guru membuat tes kognitif dengan soal-soal
 */
public class CreateTesController {

    @FXML
    private TextField txtJudulTes, txtDurasi;

    @FXML
    private TextArea txtPertanyaan;

    @FXML
    private TextField txtPilihanA, txtPilihanB, txtPilihanC, txtPilihanD;

    @FXML
    private ComboBox<String> comboJawaban;

    @FXML
    private Label lblJumlahSoal;

    @FXML
    private VBox listSoalContainer, soalItemsContainer;

    private DataStore dataStore;
    private List<Soal> soalList;
    private int soalIdCounter = 1;

    @FXML
    public void initialize() {
        dataStore = DataStore.getInstance();
        soalList = new ArrayList<>();

        // Populate jawaban combo
        comboJawaban.getItems().addAll("A", "B", "C", "D");

        updateSoalCount();
    }

    @FXML
    private void handleTambahSoal() {
        String pertanyaan = txtPertanyaan.getText().trim();
        String pilA = txtPilihanA.getText().trim();
        String pilB = txtPilihanB.getText().trim();
        String pilC = txtPilihanC.getText().trim();
        String pilD = txtPilihanD.getText().trim();
        String jawaban = comboJawaban.getValue();

        // Validation
        if (pertanyaan.isEmpty() || pilA.isEmpty() || pilB.isEmpty() ||
                pilC.isEmpty() || pilD.isEmpty() || jawaban == null) {
            SceneManager.showError("Error", "Semua field pertanyaan harus diisi!");
            return;
        }

        // Create soal (tesId will be set later)
        Soal soal = new Soal(soalIdCounter++, 0, pertanyaan, pilA, pilB, pilC, pilD, jawaban);
        soalList.add(soal);

        // Add to list UI
        addSoalToList(soal);

        // Clear form
        handleClearSoal();

        updateSoalCount();
    }

    private void addSoalToList(Soal soal) {
        Label soalLabel = new Label((soalList.size()) + ". " + soal.getPertanyaan() +
                " (Jawaban: " + soal.getJawabanBenar() + ")");
        soalLabel.setWrapText(true);
        soalLabel.setStyle("-fx-padding: 5; -fx-background-color: rgba(91, 79, 200, 0.1); -fx-background-radius: 5;");
        soalItemsContainer.getChildren().add(soalLabel);

        listSoalContainer.setVisible(true);
        listSoalContainer.setManaged(true);
    }

    private void updateSoalCount() {
        lblJumlahSoal.setText("Jumlah soal: " + soalList.size());
    }

    @FXML
    private void handleClearSoal() {
        txtPertanyaan.clear();
        txtPilihanA.clear();
        txtPilihanB.clear();
        txtPilihanC.clear();
        txtPilihanD.clear();
        comboJawaban.setValue(null);
    }

    @FXML
    private void handleSimpanTes() {
        String judul = txtJudulTes.getText().trim();
        String durasiStr = txtDurasi.getText().trim();

        // Validation
        if (judul.isEmpty() || durasiStr.isEmpty()) {
            SceneManager.showError("Error", "Judul dan Durasi tes harus diisi!");
            return;
        }

        int durasi;
        try {
            durasi = Integer.parseInt(durasiStr);
        } catch (NumberFormatException e) {
            SceneManager.showError("Error", "Durasi harus berupa angka!");
            return;
        }

        if (soalList.isEmpty()) {
            SceneManager.showError("Error", "Minimal tambahkan 1 soal!");
            return;
        }

        // Create tes
        TesKognitif tes = new TesKognitif(0, judul, durasi);

        // Add all soal
        for (Soal soal : soalList) {
            tes.addSoal(soal);
        }

        dataStore.addTesKognitif(tes);

        SceneManager.showInfo("Berhasil!",
                "Tes berhasil dibuat!\n\n" +
                        "Judul: " + judul + "\n" +
                        "Durasi: " + durasi + " menit\n" +
                        "Jumlah Soal: " + soalList.size());

        handleResetAll();
    }

    @FXML
    private void handleResetAll() {
        txtJudulTes.clear();
        txtDurasi.clear();
        handleClearSoal();
        soalList.clear();
        soalItemsContainer.getChildren().clear();
        listSoalContainer.setVisible(false);
        listSoalContainer.setManaged(false);
        soalIdCounter = 1;
        updateSoalCount();
    }

    @FXML
    private void handleBack() {
        SceneManager.loadScene("dashboard.fxml");
    }
}
