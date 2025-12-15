package com.goop.controllers;

import com.goop.data.DataStore;
import com.goop.models.Proyek;
import com.goop.models.Siswa;
import com.goop.utils.SceneManager;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

public class CreateProyekController {

    @FXML
    private TextField txtJudul;

    @FXML
    private TextArea txtDeskripsi;

    @FXML
    private DatePicker dateDeadline;

    @FXML
    private ComboBox<Siswa> comboSiswa;

    private DataStore dataStore;

    @FXML
    public void initialize() {
        dataStore = DataStore.getInstance();
        loadSiswaList();

        // Set default deadline (7 hari dari sekarang)
        dateDeadline.setValue(LocalDate.now().plusDays(7));
    }

    private void loadSiswaList() {
        List<Siswa> siswaList = dataStore.getAllSiswa();
        comboSiswa.getItems().addAll(siswaList);

        // Custom cell factory untuk display nama
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
    }

    @FXML
    private void handleCreateProyek() {
        String judul = txtJudul.getText().trim();
        String deskripsi = txtDeskripsi.getText().trim();
        LocalDate deadline = dateDeadline.getValue();
        Siswa siswa = comboSiswa.getValue();

        // Validation
        if (judul.isEmpty() || deskripsi.isEmpty()) {
            SceneManager.showError("Error", "Judul dan Deskripsi harus diisi!");
            return;
        }

        if (deadline == null) {
            SceneManager.showError("Error", "Deadline harus dipilih!");
            return;
        }

        if (siswa == null) {
            SceneManager.showError("Error", "Pilih siswa yang akan mengerjakan proyek!");
            return;
        }

        // Create proyek with current guru's ID
        int guruId = dataStore.getCurrentUser().getId();
        Proyek proyek = new Proyek(0, judul, deskripsi, deadline,
                Proyek.STATUS_BELUM_DIKERJAKAN, 0, siswa.getId(), guruId);
        dataStore.addProyek(proyek);

        SceneManager.showInfo("Berhasil!",
                "Proyek berhasil dibuat!\n\n" +
                        "Judul: " + judul + "\n" +
                        "Siswa: " + siswa.getNamaLengkap() + "\n" +
                        "Deadline: " + deadline);

        handleReset();
    }

    @FXML
    private void handleReset() {
        txtJudul.clear();
        txtDeskripsi.clear();
        dateDeadline.setValue(LocalDate.now().plusDays(7));
        comboSiswa.setValue(null);
    }

    @FXML
    private void handleBack() {
        SceneManager.loadScene("dashboard.fxml");
    }
}
