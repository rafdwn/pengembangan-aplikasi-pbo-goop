package com.goop.controllers;

import com.goop.data.DataStore;
import com.goop.models.Proyek;
import com.goop.models.Siswa;
import com.goop.utils.SceneManager;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.util.List;

/**
 * ProyekController - Interactive Code Editor
 * Siswa dapat menulis kode, compile, run, dan submit
 */
public class ProyekController {

    // List View components
    @FXML
    private VBox proyekListView, proyekCardsContainer;

    // Editor View components
    @FXML
    private VBox codeEditorView;

    @FXML
    private Text titleText, editorProyekJudul;

    @FXML
    private Label editorProyekDeskripsi, editorDeadline, editorStatus, editorSkor, compiledStatus;

    @FXML
    private TextArea codeEditor, outputConsole;

    @FXML
    private Button btnSubmitCode;

    private DataStore dataStore;
    private Siswa currentSiswa;
    private Proyek selectedProyek;
    private boolean hasCompiledSuccessfully = false;

    @FXML
    public void initialize() {
        dataStore = DataStore.getInstance();

        if (dataStore.getCurrentUser() instanceof Siswa) {
            currentSiswa = (Siswa) dataStore.getCurrentUser();
            loadProyekList();
        }
    }

    private void loadProyekList() {
        proyekCardsContainer.getChildren().clear();

        // DEBUG LOGS
        System.out.println("=== DEBUG PROYEK CONTROLLER ===");
        System.out.println("Current Siswa: " + currentSiswa.getNamaLengkap() + " (ID: " + currentSiswa.getId() + ")");

        List<Proyek> proyekList = dataStore.getProyekBySiswaId(currentSiswa.getId());
        System.out.println("Found " + proyekList.size() + " proyek for siswa ID " + currentSiswa.getId());

        // Also check ALL proyek
        List<Proyek> allProyek = dataStore.getAllProyek();
        System.out.println("Total proyek in DataStore: " + allProyek.size());
        for (Proyek p : allProyek) {
            System.out.println("  - Proyek: " + p.getJudul() + " (siswaId=" + p.getSiswaId() + ")");
        }
        System.out.println("===============================");

        if (proyekList.isEmpty()) {
            Label empty = new Label("Belum ada proyek yang di-assign");
            empty.setStyle("-fx-text-fill: #747d8c; -fx-font-size: 14px;");
            proyekCardsContainer.getChildren().add(empty);
            return;
        }

        for (Proyek proyek : proyekList) {
            VBox card = createProyekCard(proyek);
            proyekCardsContainer.getChildren().add(card);
        }
    }

    private VBox createProyekCard(Proyek proyek) {
        VBox card = new VBox(10);
        card.getStyleClass().add("card");
        card.setPadding(new Insets(15));
        card.setStyle(card.getStyle() + "-fx-cursor: hand;");

        Text judul = new Text(proyek.getJudul());
        judul.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-fill: #5B4FC8;");

        Label deskripsi = new Label(proyek.getDeskripsi());
        deskripsi.setWrapText(true);
        deskripsi.setStyle("-fx-text-fill: #2f3542;");

        HBox info = new HBox(20);
        Label deadline = new Label("⏰ " + proyek.getDeadlineFormatted());
        Label status = new Label("📌 " + proyek.getStatus());
        Label skor = new Label("🎯 Skor: " + proyek.getSkor());
        info.getChildren().addAll(deadline, status, skor);

        Button btnKerjakan = new Button("💻 Mulai Kerjakan");
        btnKerjakan.getStyleClass().add("btn-primary");
        btnKerjakan.setOnAction(e -> openCodeEditor(proyek));

        card.getChildren().addAll(judul, deskripsi, info, btnKerjakan);
        return card;
    }

    private void openCodeEditor(Proyek proyek) {
        selectedProyek = proyek;
        hasCompiledSuccessfully = false;

        // Update UI
        titleText.setText("Proyek: " + proyek.getJudul());
        editorProyekJudul.setText(proyek.getJudul());
        editorProyekDeskripsi.setText(proyek.getDeskripsi());
        editorDeadline.setText("⏰ Deadline: " + proyek.getDeadlineFormatted());
        editorStatus.setText("📌 Status: " + proyek.getStatus());
        editorSkor.setText("🎯 Skor: " + proyek.getSkor());

        // Load saved code if exists
        String savedCode = dataStore.getProyekCode(proyek.getId());
        codeEditor.setText(savedCode != null ? savedCode
                : "// Tulis kode Java kamu di sini\n\npublic class Main {\n    public static void main(String[] args) {\n        // TODO: Implementasi\n    }\n}");

        compiledStatus.setText("");
        outputConsole.clear();

        // Switch view
        proyekListView.setVisible(false);
        proyekListView.setManaged(false);
        codeEditorView.setVisible(true);
        codeEditorView.setManaged(true);
    }

    @FXML
    private void showProyekList() {
        codeEditorView.setVisible(false);
        codeEditorView.setManaged(false);
        proyekListView.setVisible(true);
        proyekListView.setManaged(true);
        titleText.setText("Proyek OOP");
    }

    @FXML
    private void handleRunCode() {
        String code = codeEditor.getText().trim();

        // Validation 1: Code tidak boleh kosong
        if (code.isEmpty()) {
            outputConsole.setText("❌ ERROR: Code editor kosong!\nSilakan tulis kode terlebih dahulu.");
            compiledStatus.setText("❌ Belum dijalankan");
            compiledStatus.setStyle("-fx-text-fill: #ff4757;");
            hasCompiledSuccessfully = false;

            SceneManager.showError("Error", "Code editor kosong! Silakan tulis kode terlebih dahulu.");
            return;
        }

        // Simulate compilation & execution
        outputConsole.setText("⚙️ Compiling...\n");

        // Simple validation: check if contains basic Java syntax
        boolean hasClass = code.contains("class ");
        boolean hasMain = code.contains("public static void main");

        if (!hasClass || !hasMain) {
            // Compile error
            outputConsole.setText("❌ COMPILE ERROR\n\n" +
                    "Error: Missing required components\n" +
                    (hasClass ? "" : "- Missing class declaration\n") +
                    (hasMain ? "" : "- Missing main method\n") +
                    "\nSilakan perbaiki kode dan coba lagi.");

            compiledStatus.setText("❌ Compile Error");
            compiledStatus.setStyle("-fx-text-fill: #ff4757;");
            hasCompiledSuccessfully = false;

        } else {
            // Success
            outputConsole.setText("✅ COMPILATION SUCCESSFUL\n\n" +
                    "Running program...\n" +
                    "===================\n" +
                    "Program executed successfully!\n" +
                    "(Dalam implementasi nyata, output program akan muncul di sini)\n" +
                    "===================\n\n" +
                    "✅ Kode berhasil dijalankan! Anda sekarang bisa submit jawaban.");

            compiledStatus.setText("✅ Compiled & Ready");
            compiledStatus.setStyle("-fx-text-fill: #26de81;");
            hasCompiledSuccessfully = true;

            // Save code
            dataStore.saveProyekCode(selectedProyek.getId(), code);
        }
    }

    @FXML
    private void handleSubmitCode() {
        String code = codeEditor.getText().trim();

        // Validation 1: Code tidak boleh kosong
        if (code.isEmpty()) {
            SceneManager.showError("Gagal Submit",
                    "Code editor kosong!\nSilakan tulis kode terlebih dahulu.");
            return;
        }

        // Validation 2: Harus run dulu sebelum submit
        if (!hasCompiledSuccessfully) {
            SceneManager.showError("Gagal Submit",
                    "Kode belum dijalankan!\n\nSilakan jalankan kode terlebih dahulu dengan tombol 'Jalankan Kode' " +
                            "untuk memastikan tidak ada error sebelum submit.");
            return;
        }

        // Confirm submit
        boolean confirm = SceneManager.showConfirmation("Konfirmasi Submit",
                "Apakah Anda yakin ingin submit proyek ini?\n\n" +
                        "Proyek: " + selectedProyek.getJudul() + "\n" +
                        "Status submit tidak bisa dibatalkan.");

        if (!confirm) {
            return;
        }

        // Submit proyek
        selectedProyek.setStatus(Proyek.STATUS_SELESAI);

        // Simple scoring based on code completeness
        int skor = calculateScore(code);
        selectedProyek.setSkor(skor);

        dataStore.updateProyek(selectedProyek);
        dataStore.saveProyekCode(selectedProyek.getId(), code);

        outputConsole.setText(outputConsole.getText() + "\n\n" +
                "=============================\n" +
                "✅ PROYEK BERHASIL DI-SUBMIT!\n" +
                "=============================\n" +
                "Skor: " + skor + " / 100\n" +
                "Status: " + selectedProyek.getStatus() + "\n\n" +
                "Terima kasih! Proyek Anda akan direview oleh Guru.");

        SceneManager.showInfo("Berhasil!",
                "Proyek berhasil di-submit!\n\nSkor: " + skor + " / 100\n" +
                        "Proyek Anda akan direview oleh Guru.");

        // Refresh
        editorStatus.setText("📌 Status: " + selectedProyek.getStatus());
        editorSkor.setText("🎯 Skor: " + selectedProyek.getSkor());
        hasCompiledSuccessfully = false;
    }

    private int calculateScore(String code) {
        int skor = 50; // Base score

        // Bonus for OOP concepts
        if (code.contains("class "))
            skor += 10;
        if (code.contains("public ") && code.contains("private "))
            skor += 10;
        if (code.contains("extends "))
            skor += 10;
        if (code.contains("@Override"))
            skor += 10;
        if (code.length() > 200)
            skor += 10; // Substantial code

        return Math.min(skor, 100);
    }

    @FXML
    private void handleClearCode() {
        boolean confirm = SceneManager.showConfirmation("Konfirmasi",
                "Apakah Anda yakin ingin menghapus semua code?");

        if (confirm) {
            codeEditor.clear();
            outputConsole.clear();
            compiledStatus.setText("");
            hasCompiledSuccessfully = false;
        }
    }

    @FXML
    private void handleBack() {
        SceneManager.loadScene("dashboard.fxml");
    }
}
