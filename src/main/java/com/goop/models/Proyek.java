package com.goop.models;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Proyek {

    // ==================== CONSTANTS ====================
    // Konstanta untuk status proyek
    // Menggunakan public static final - ini adalah best practice untuk constants

    public static final String STATUS_BELUM_DIKERJAKAN = "BELUM_DIKERJAKAN";
    public static final String STATUS_DIKERJAKAN = "DIKERJAKAN";
    public static final String STATUS_SELESAI = "SELESAI";
    public static final String STATUS_TERVALIDASI = "TERVALIDASI";

    // ==================== ATTRIBUTES ====================

    
    private int id;

    
    private String judul;

    
    private String deskripsi;

    
    private LocalDate deadline;

    
    private String status;

    
    private double skor;

    
    private int siswaId;

    
    private int guruId;

    
    private String filePath;

    // ==================== CONSTRUCTORS ====================

    
    public Proyek() {
        this.status = STATUS_BELUM_DIKERJAKAN;
        this.skor = 0.0;
    }

    
    public Proyek(int id, String judul, String deskripsi, LocalDate deadline,
            String status, double skor, int siswaId, int guruId) {
        this.id = id;
        this.judul = judul;
        this.deskripsi = deskripsi;
        this.deadline = deadline;
        this.status = status;
        this.skor = skor;
        this.siswaId = siswaId;
        this.guruId = guruId;
        this.filePath = "";
    }

    // ==================== GETTERS & SETTERS ====================

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getSkor() {
        return skor;
    }

    public void setSkor(double skor) {
        this.skor = skor;
    }

    public int getSiswaId() {
        return siswaId;
    }

    public void setSiswaId(int siswaId) {
        this.siswaId = siswaId;
    }

    public int getGuruId() {
        return guruId;
    }

    public void setGuruId(int guruId) {
        this.guruId = guruId;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    // ==================== BUSINESS METHODS ====================

    
    public void mulaiKerjakan() {
        if (this.status.equals(STATUS_BELUM_DIKERJAKAN)) {
            this.status = STATUS_DIKERJAKAN;
            System.out.println("Proyek '" + judul + "' dimulai!");
        } else {
            System.out.println("Proyek sudah dalam status: " + status);
        }
    }

    
    public boolean submitProyek(String filePath) {
        if (this.status.equals(STATUS_DIKERJAKAN)) {
            this.status = STATUS_SELESAI;
            this.filePath = filePath;
            System.out.println("Proyek '" + judul + "' berhasil di-submit!");
            return true;
        } else {
            System.out.println("Proyek belum dalam status DIKERJAKAN!");
            return false;
        }
    }

    
    public boolean validasi(double skorDiberikan) {
        if (this.status.equals(STATUS_SELESAI)) {
            this.status = STATUS_TERVALIDASI;
            this.skor = skorDiberikan;
            System.out.println("Proyek '" + judul + "' tervalidasi dengan skor: " + skorDiberikan);
            return true;
        } else {
            System.out.println("Proyek harus dalam status SELESAI untuk divalidasi!");
            return false;
        }
    }

    
    public boolean isTerlambat() {
        LocalDate today = LocalDate.now();
        return today.isAfter(deadline) && !status.equals(STATUS_TERVALIDASI);
    }

    
    public long getHariTersisa() {
        LocalDate today = LocalDate.now();
        return java.time.temporal.ChronoUnit.DAYS.between(today, deadline);
    }

    
    public String getDeadlineFormatted() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return deadline.format(formatter);
    }

    
    @Override
    public String toString() {
        return "Proyek{" +
                "id=" + id +
                ", judul='" + judul + '\'' +
                ", deadline=" + getDeadlineFormatted() +
                ", status='" + status + '\'' +
                ", skor=" + skor +
                ", siswaId=" + siswaId +
                ", hariTersisa=" + getHariTersisa() +
                '}';
    }
}
