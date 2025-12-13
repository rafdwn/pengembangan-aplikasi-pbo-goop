package com.goop.controllers;

import com.goop.data.DataStore;
import com.goop.models.Proyek;
import com.goop.models.Siswa;
import com.goop.models.TesKognitif;
import com.goop.models.User;
import com.goop.utils.SceneManager;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.List;

/**
 * HasilController - Controller untuk menampilkan hasil belajar
 */
public class HasilController {

    @FXML
    private Label rataTesLabel, totalTesLabel, proyekSelesaiLabel;
    @FXML
    private VBox historiTesContainer, statusProyekContainer;

    private DataStore dataStore;
    private Siswa siswa;

    @FXML
    public void initialize() {
        dataStore = DataStore.getInstance();

        User currentUser = dataStore.getCurrentUser();
        if (currentUser instanceof Siswa) {
            siswa = (Siswa) currentUser;
            loadData();
        } else {
            handleBack();
        }
    }

    private void loadData() {
        // Load summary statistics
        double rataTes = siswa.getSkorKognitif();
        rataTesLabel.setText(String.format("%.0f", rataTes));

        // Hitung total tes (sederhana, asumsi 1 tes)
        List<TesKognitif> tesList = dataStore.getAllTesKognitif();
        int totalTesDikerjakan = rataTes > 0 ? 1 : 0; // Simplified
        totalTesLabel.setText(String.valueOf(totalTesDikerjakan));

        // Hitung proyek selesai
        List<Proyek> proyekList = dataStore.getProyekBySiswaId(siswa.getId());
        long proyekSelesai = proyekList.stream()
                .filter(p -> p.getStatus().equals(Proyek.STATUS_SELESAI)
                        || p.getStatus().equals(Proyek.STATUS_TERVALIDASI))
                .count();
        proyekSelesaiLabel.setText(String.valueOf(proyekSelesai));

        // Load histori tes
        loadHistoriTes();

        // Load status proyek
        loadStatusProyek();
    }

    private void loadHistoriTes() {
        List<TesKognitif> tesList = dataStore.getAllTesKognitif();

        for (TesKognitif tes : tesList) {
            double skor = dataStore.getHasilTes(siswa.getId(), tes.getId());

            if (skor > 0) {
                HBox tesCard = new HBox(20);
                tesCard.setPadding(new Insets(10));
                tesCard.setStyle("-fx-background-color: rgba(91, 79, 200, 0.05); -fx-background-radius: 8;");

                VBox info = new VBox(5);
                Label judul = new Label(tes.getJudul());
                judul.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");
                Label detail = new Label(tes.getJumlahSoal() + " soal");
                detail.setStyle("-fx-text-fill: #747d8c;");
                info.getChildren().addAll(judul, detail);

                Label skorLabel = new Label(String.format("%.0f", skor));
                skorLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #5B4FC8;");

                tesCard.getChildren().addAll(info, new Label(), skorLabel);
                HBox.setHgrow(tesCard.getChildren().get(1), javafx.scene.layout.Priority.ALWAYS);

                historiTesContainer.getChildren().add(tesCard);
            }
        }

        if (historiTesContainer.getChildren().isEmpty()) {
            Label empty = new Label("Belum ada tes yang dikerjakan");
            empty.setStyle("-fx-text-fill: #747d8c;");
            historiTesContainer.getChildren().add(empty);
        }
    }

    private void loadStatusProyek() {
        List<Proyek> proyekList = dataStore.getProyekBySiswaId(siswa.getId());

        for (Proyek proyek : proyekList) {
            HBox proyekCard = new HBox(20);
            proyekCard.setPadding(new Insets(10));
            proyekCard.setStyle("-fx-background-color: rgba(91, 79, 200, 0.05); -fx-background-radius: 8;");

            VBox info = new VBox(5);
            Label judul = new Label(proyek.getJudul());
            judul.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");
            Label detail = new Label("Deadline: " + proyek.getDeadlineFormatted());
            detail.setStyle("-fx-text-fill: #747d8c;");
            info.getChildren().addAll(judul, detail);

            VBox statusBox = new VBox(5);
            Label status = new Label(proyek.getStatus());
            status.setStyle("-fx-font-weight: bold;");
            Label skor = new Label("Skor: " + proyek.getSkor());
            skor.setStyle("-fx-text-fill: #26de81;");
            statusBox.getChildren().addAll(status, skor);

            proyekCard.getChildren().addAll(info, new Label(), statusBox);
            HBox.setHgrow(proyekCard.getChildren().get(1), javafx.scene.layout.Priority.ALWAYS);

            statusProyekContainer.getChildren().add(proyekCard);
        }

        if (statusProyekContainer.getChildren().isEmpty()) {
            Label empty = new Label("Belum ada proyek");
            empty.setStyle("-fx-text-fill: #747d8c;");
            statusProyekContainer.getChildren().add(empty);
        }
    }

    @FXML
    private void handleBack() {
        SceneManager.loadScene("dashboard.fxml");
    }
}
