package com.goop.controllers;

import com.goop.data.DataStore;
import com.goop.models.Siswa;
import com.goop.models.Soal;
import com.goop.models.TesKognitif;
import com.goop.models.User;
import com.goop.utils.SceneManager;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.util.List;

/**
 * TesKognitifController - Controller untuk mengerjakan tes kognitif
 */
public class TesKognitifController {

    @FXML
    private Text judulTes, soalText;
    @FXML
    private Label timerLabel, progressLabel, skorLabel, keteranganLabel;
    @FXML
    private ProgressBar progressBar;
    @FXML
    private RadioButton pilihanA, pilihanB, pilihanC, pilihanD;
    @FXML
    private ToggleGroup pilihanGroup;
    @FXML
    private Button btnPrev, btnNext, btnSubmit;
    @FXML
    private VBox soalContainer, hasilPanel;

    private DataStore dataStore;
    private TesKognitif tes;
    private List<Soal> daftarSoal;
    private String[] jawabanSiswa;
    private int currentSoalIndex = 0;
    private int waktuTersisa; // dalam detik
    private Timeline timer;

    @FXML
    public void initialize() {
        dataStore = DataStore.getInstance();
        loadTes();
    }

    private void loadTes() {
        // Ambil tes pertama yang aktif
        List<TesKognitif> tesList = dataStore.getActiveTesKognitif();
        if (tesList.isEmpty()) {
            SceneManager.showInfo("Tidak Ada Tes", "Tidak ada tes kognitif yang tersedia saat ini.");
            handleBack();
            return;
        }

        tes = tesList.get(0);
        daftarSoal = tes.getDaftarSoal();
        jawabanSiswa = new String[daftarSoal.size()];

        judulTes.setText(tes.getJudul());
        waktuTersisa = tes.getDurasiMenit() * 60; // convert ke detik

        startTimer();
        showSoal(0);
    }

    private void startTimer() {
        timer = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
            waktuTersisa--;
            updateTimerDisplay();

            if (waktuTersisa <= 0) {
                timer.stop();
                autoSubmit();
            }
        }));
        timer.setCycleCount(Timeline.INDEFINITE);
        timer.play();
    }

    private void updateTimerDisplay() {
        int minutes = waktuTersisa / 60;
        int seconds = waktuTersisa % 60;
        timerLabel.setText(String.format("Waktu: %02d:%02d", minutes, seconds));

        // Warning jika waktu tinggal 5 menit
        if (waktuTersisa == 300) {
            timerLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #ff4757;");
        }
    }

    private void showSoal(int index) {
        if (index < 0 || index >= daftarSoal.size())
            return;

        currentSoalIndex = index;
        Soal soal = daftarSoal.get(index);

        soalText.setText(String.format("%d. %s", index + 1, soal.getPertanyaan()));
        pilihanA.setText("A. " + soal.getPilihanA());
        pilihanB.setText("B. " + soal.getPilihanB());
        pilihanC.setText("C. " + soal.getPilihanC());
        pilihanD.setText("D. " + soal.getPilihanD());

        // Clear selection
        pilihanGroup.selectToggle(null);

        // Load jawaban siswa jika sudah ada
        if (jawabanSiswa[index] != null) {
            switch (jawabanSiswa[index]) {
                case "A":
                    pilihanGroup.selectToggle(pilihanA);
                    break;
                case "B":
                    pilihanGroup.selectToggle(pilihanB);
                    break;
                case "C":
                    pilihanGroup.selectToggle(pilihanC);
                    break;
                case "D":
                    pilihanGroup.selectToggle(pilihanD);
                    break;
            }
        }

        // Update progress
        progressBar.setProgress((double) (index + 1) / daftarSoal.size());
        progressLabel.setText(String.format("%d / %d", index + 1, daftarSoal.size()));

        // Update button states
        btnPrev.setDisable(index == 0);
        btnNext.setDisable(index == daftarSoal.size() - 1);
    }

    private void saveJawaban() {
        Toggle selected = pilihanGroup.getSelectedToggle();
        if (selected != null) {
            RadioButton rb = (RadioButton) selected;
            String jawaban = rb.getText().substring(0, 1); // Ambil A/B/C/D
            jawabanSiswa[currentSoalIndex] = jawaban;
        }
    }

    @FXML
    private void handlePrevious() {
        saveJawaban();
        showSoal(currentSoalIndex - 1);
    }

    @FXML
    private void handleNext() {
        saveJawaban();
        showSoal(currentSoalIndex + 1);
    }

    @FXML
    private void handleSubmit() {
        boolean confirm = SceneManager.showConfirmation(
                "Submit Jawaban",
                "Apakah Anda yakin ingin submit jawaban?\nJawaban tidak bisa diubah setelah di-submit.");

        if (confirm) {
            saveJawaban();
            submitTes();
        }
    }

    private void autoSubmit() {
        SceneManager.showInfo("Waktu Habis", "Waktu tes telah habis. Jawaban Anda akan di-submit otomatis.");
        submitTes();
    }

    private void submitTes() {
        if (timer != null) {
            timer.stop();
        }

        // Hitung skor
        double skor = tes.hitungSkor(jawabanSiswa);

        // Simpan hasil tes
        User currentUser = dataStore.getCurrentUser();
        if (currentUser instanceof Siswa) {
            Siswa siswa = (Siswa) currentUser;
            dataStore.saveHasilTes(siswa.getId(), tes.getId(), skor);
        }

        // Tampilkan hasil
        showHasil(skor);
    }

    private void showHasil(double skor) {
        soalContainer.setVisible(false);
        soalContainer.setManaged(false);

        hasilPanel.setVisible(true);
        hasilPanel.setManaged(true);

        skorLabel.setText(String.format("Skor: %.0f", skor));

        String kategori;
        if (skor >= 80) {
            kategori = "Sangat Baik! ⭐⭐⭐";
        } else if (skor >= 60) {
            kategori = "Baik ⭐⭐";
        } else if (skor >= 40) {
            kategori = "Cukup ⭐";
        } else {
            kategori = "Perlu Belajar Lebih Giat";
        }

        keteranganLabel.setText("Kategori: " + kategori);

        SceneManager.showInfo("Tes Selesai",
                String.format("Anda telah menyelesaikan tes!\n\nSkor Anda: %.0f\n%s", skor, kategori));
    }

    @FXML
    private void handleBack() {
        if (timer != null) {
            timer.stop();
        }
        SceneManager.loadScene("dashboard.fxml");
    }
}
