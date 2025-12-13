package com.goop.controllers;

import com.goop.data.DataStore;
import com.goop.models.Materi;
import com.goop.utils.SceneManager;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.util.List;

/**
 * MateriController - Controller untuk halaman materi pembelajaran
 */
public class MateriController {

    @FXML
    private VBox materiContainer;

    private DataStore dataStore;

    @FXML
    public void initialize() {
        dataStore = DataStore.getInstance();
        loadMateri();
    }

    private void loadMateri() {
        List<Materi> materiList = dataStore.getAllMateri();

        for (Materi materi : materiList) {
            VBox materiCard = createMateriCard(materi);
            materiContainer.getChildren().add(materiCard);
        }
    }

    private VBox createMateriCard(Materi materi) {
        VBox card = new VBox(10);
        card.getStyleClass().add("card");
        card.setPadding(new Insets(20));

        // Judul
        Text judul = new Text(materi.getJudul());
        judul.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-fill: #2f3542;");

        // Topik badge
        Label topik = new Label("📚 " + materi.getTopik());
        topik.setStyle(
                "-fx-background-color: rgba(91, 79, 200, 0.1); -fx-padding: 5 10; -fx-background-radius: 5; -fx-text-fill: #5B4FC8; -fx-font-weight: bold;");

        // Estimasi waktu
        Label waktu = new Label("⏱️ " + materi.getEstimasiWaktuBaca() + " menit");
        waktu.setStyle("-fx-text-fill: #747d8c;");

        // Konten
        Text konten = new Text(materi.getKonten());
        konten.setWrappingWidth(700);
        konten.setStyle("-fx-fill: #2f3542; -fx-font-size: 14px;");

        card.getChildren().addAll(judul, topik, waktu, new Label(), konten);

        return card;
    }

    @FXML
    private void handleBack() {
        SceneManager.loadScene("dashboard.fxml");
    }
}
