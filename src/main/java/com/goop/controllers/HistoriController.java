package com.goop.controllers;

import com.goop.data.DataStore;
import com.goop.models.Proyek;
import com.goop.models.Siswa;
import com.goop.utils.SceneManager;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class HistoriController {

    @FXML
    private VBox historiContainer;

    @FXML
    private ComboBox<Siswa> comboSiswa;

    private DataStore dataStore;

    @FXML
    public void initialize() {
        dataStore = DataStore.getInstance();
        loadSiswaList();
    }

    private void loadSiswaList() {
        List<Siswa> siswaList = dataStore.getAllSiswa();
        comboSiswa.getItems().addAll(siswaList);

        comboSiswa.setButtonCell(new ListCell<Siswa>() {
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

        comboSiswa.setCellFactory(param -> new ListCell<Siswa>() {
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

        comboSiswa.setOnAction(e -> showHistori());
    }

    private void showHistori() {
        Siswa selectedSiswa = comboSiswa.getValue();
        if (selectedSiswa == null)
            return;

        historiContainer.getChildren().clear();

        List<Proyek> proyekList = dataStore.getProyekBySiswaId(selectedSiswa.getId());

        if (proyekList.isEmpty()) {
            Label emptyLabel = new Label("Belum ada histori pengerjaan untuk siswa ini");
            emptyLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #666;");
            historiContainer.getChildren().add(emptyLabel);
            return;
        }

        for (Proyek proyek : proyekList) {
            VBox historiCard = createHistoriCard(proyek);
            historiContainer.getChildren().add(historiCard);
        }
    }

    private VBox createHistoriCard(Proyek proyek) {
        VBox card = new VBox(10);
        card.setStyle("-fx-background-color: white; -fx-padding: 15; " +
                "-fx-border-color: #ddd; -fx-border-radius: 5; -fx-background-radius: 5;");

        Label judulLabel = new Label(proyek.getJudul());
        judulLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        Label statusLabel = new Label("Status: " + proyek.getStatus());
        statusLabel.setStyle("-fx-font-size: 14px;");

        Label deadlineLabel = new Label("Deadline: " + proyek.getDeadlineFormatted());
        deadlineLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: #666;");

        Label skorLabel = new Label("Skor: " + proyek.getSkor());
        skorLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: #4CAF50;");

        card.getChildren().addAll(judulLabel, statusLabel, deadlineLabel, skorLabel);

        return card;
    }

    @FXML
    private void handleBack() {
        SceneManager.loadScene("dashboard.fxml");
    }
}
