package com.goop.controllers;

import com.goop.data.DataStore;
import com.goop.models.Proyek;
import com.goop.models.Siswa;
import com.goop.models.TesKognitif;
import com.goop.utils.SceneManager;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.List;

/**
 * MonitorNilaiController - Controller untuk monitor nilai dan histori siswa
 * (Guru)
 * Menampilkan skor tes, status proyek, dan histori pengerjaan
 */
public class MonitorNilaiController {

    @FXML
    private ComboBox<Siswa> siswaComboBox;

    @FXML
    private GridPane summaryGrid;

    @FXML
    private Label skorTesLabel, proyekSelesaiLabel, totalProyekLabel;

    @FXML
    private VBox historiTesContainer, statusProyekContainer, historiTesCard, statusProyekCard;

    private DataStore dataStore;
    private Siswa selectedSiswa;

    @FXML
    public void initialize() {
        dataStore = DataStore.getInstance();
        loadSiswaList();
    }

    private void loadSiswaList() {
        List<Siswa> siswaList = dataStore.getAllSiswa();
        siswaComboBox.getItems().addAll(siswaList);

        // Custom cell factory untuk display nama
        siswaComboBox.setButtonCell(new javafx.scene.control.ListCell<Siswa>() {
            @Override
            protected void updateItem(Siswa item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText("");
                } else {
                    setText(item.getNamaLengkap() + " (" + item.getUsername() + ")");
                }
            }
        });

        siswaComboBox.setCellFactory(param -> new javafx.scene.control.ListCell<Siswa>() {
            @Override
            protected void updateItem(Siswa item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText("");
                } else {
                    setText(item.getNamaLengkap() + " (" + item.getUsername() + ")");
                }
            }
        });
    }

    @FXML
    private void handleSiswaSelected() {
        selectedSiswa = siswaComboBox.getValue();
        if (selectedSiswa != null) {
            loadSiswaData();
        }
    }

    private void loadSiswaData() {
        // Show summary
        summaryGrid.setVisible(true);
        summaryGrid.setManaged(true);

        // Load skor tes
        double skorTes = selectedSiswa.getSkorKognitif();
        skorTesLabel.setText(String.format("%.0f", skorTes));

        // Load proyek
        List<Proyek> proyekList = dataStore.getProyekBySiswaId(selectedSiswa.getId());
        long proyekSelesai = proyekList.stream()
                .filter(p -> p.getStatus().equals(Proyek.STATUS_SELESAI)
                        || p.getStatus().equals(Proyek.STATUS_TERVALIDASI))
                .count();
        proyekSelesaiLabel.setText(String.valueOf(proyekSelesai));
        totalProyekLabel.setText(String.valueOf(proyekList.size()));

        // Load histori tes
        loadHistoriTes();

        // Load status proyek
        loadStatusProyek();
    }

    private void loadHistoriTes() {
        historiTesContainer.getChildren().clear();
        List<TesKognitif> tesList = dataStore.getAllTesKognitif();

        boolean hasData = false;
        for (TesKognitif tes : tesList) {
            double skor = dataStore.getHasilTes(selectedSiswa.getId(), tes.getId());

            if (skor > 0) {
                hasData = true;
                HBox tesCard = new HBox(20);
                tesCard.setPadding(new Insets(10));
                tesCard.setStyle("-fx-background-color: rgba(91, 79, 200, 0.05); -fx-background-radius: 8;");

                VBox info = new VBox(5);
                Label judul = new Label(tes.getJudul());
                judul.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");
                Label detail = new Label(tes.getJumlahSoal() + " soal • " + tes.getDurasiMenit() + " menit");
                detail.setStyle("-fx-text-fill: #747d8c;");
                info.getChildren().addAll(judul, detail);

                Label skorLabel = new Label(String.format("%.0f", skor));
                skorLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #5B4FC8;");

                tesCard.getChildren().addAll(info, new Label(), skorLabel);
                HBox.setHgrow(tesCard.getChildren().get(1), javafx.scene.layout.Priority.ALWAYS);

                historiTesContainer.getChildren().add(tesCard);
            }
        }

        if (!hasData) {
            Label empty = new Label("Siswa belum mengerjakan tes apapun");
            empty.setStyle("-fx-text-fill: #747d8c;");
            historiTesContainer.getChildren().add(empty);
        }

        historiTesCard.setVisible(true);
        historiTesCard.setManaged(true);
    }

    private void loadStatusProyek() {
        statusProyekContainer.getChildren().clear();
        List<Proyek> proyekList = dataStore.getProyekBySiswaId(selectedSiswa.getId());

        if (!proyekList.isEmpty()) {
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
                skor.setStyle("-fx-text-fill: #26de81; -fx-font-size: 16px; -fx-font-weight: bold;");
                statusBox.getChildren().addAll(status, skor);

                proyekCard.getChildren().addAll(info, new Label(), statusBox);
                HBox.setHgrow(proyekCard.getChildren().get(1), javafx.scene.layout.Priority.ALWAYS);

                statusProyekContainer.getChildren().add(proyekCard);
            }
        } else {
            Label empty = new Label("Belum ada proyek yang di-assign");
            empty.setStyle("-fx-text-fill: #747d8c;");
            statusProyekContainer.getChildren().add(empty);
        }

        statusProyekCard.setVisible(true);
        statusProyekCard.setManaged(true);
    }

    @FXML
    private void handleBack() {
        SceneManager.loadScene("dashboard.fxml");
    }
}
