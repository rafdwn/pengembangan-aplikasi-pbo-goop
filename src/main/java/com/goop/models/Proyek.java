package com.goop.models;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Class Proyek - Merepresentasikan proyek OOP yang dikerjakan siswa
 * 
 * Konsep yang diterapkan:
 * - ENCAPSULATION: Private fields dengan public getter/setter
 * - OBJECT: Ini adalah blueprint untuk membuat object Proyek
 * - COMPOSITION: Proyek "belongs to" satu Siswa
 * 
 * Lifecycle Proyek:
 * BELUM_DIKERJAKAN → DIKERJAKAN → SELESAI → TERVALIDASI
 * 
 * @author GOOP Development Team
 * @version 1.0
 */
public class Proyek {

    // ==================== CONSTANTS ====================
    // Konstanta untuk status proyek
    // Menggunakan public static final - ini adalah best practice untuk constants

    public static final String STATUS_BELUM_DIKERJAKAN = "BELUM_DIKERJAKAN";
    public static final String STATUS_DIKERJAKAN = "DIKERJAKAN";
    public static final String STATUS_SELESAI = "SELESAI";
    public static final String STATUS_TERVALIDASI = "TERVALIDASI";

    // ==================== ATTRIBUTES ====================

    /**
     * ID unik proyek
     */
    private int id;

    /**
     * Judul proyek (contoh: "Aplikasi Inventory OOP")
     */
    private String judul;

    /**
     * Deskripsi detail tentang proyek
     */
    private String deskripsi;

    /**
     * Deadline pengerjaan proyek
     * Menggunakan LocalDate dari Java 8+ untuk handling tanggal
     */
    private LocalDate deadline;

    /**
     * Status proyek saat ini
     * Harus salah satu dari: BELUM_DIKERJAKAN, DIKERJAKAN, SELESAI, TERVALIDASI
     */
    private String status;

    /**
     * Skor proyek yang diberikan guru (0-100)
     * Default: 0
     */
    private double skor;

    /**
     * ID siswa yang mengerjakan proyek ini
     * Relasi: Proyek BELONGS-TO Siswa (many-to-one relationship)
     */
    private int siswaId;

    /**
     * File path hasil proyek yang di-upload siswa
     * Bisa berupa path ke file .java, .zip, dll
     */
    private String filePath;

    // ==================== CONSTRUCTORS ====================

    /**
     * Constructor default
     */
    public Proyek() {
        this.status = STATUS_BELUM_DIKERJAKAN;
        this.skor = 0.0;
    }

    /**
     * Constructor dengan parameters utama
     * 
     * @param id        ID proyek
     * @param judul     Judul proyek
     * @param deskripsi Deskripsi proyek
     * @param deadline  Deadline pengerjaan
     * @param status    Status proyek
     * @param skor      Skor proyek
     * @param siswaId   ID siswa pemilik proyek
     */
    public Proyek(int id, String judul, String deskripsi, LocalDate deadline,
            String status, double skor, int siswaId) {
        this.id = id;
        this.judul = judul;
        this.deskripsi = deskripsi;
        this.deadline = deadline;
        this.status = status;
        this.skor = skor;
        this.siswaId = siswaId;
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

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    // ==================== BUSINESS METHODS ====================

    /**
     * Method untuk memulai mengerjakan proyek
     * Mengubah status dari BELUM_DIKERJAKAN ke DIKERJAKAN
     */
    public void mulaiKerjakan() {
        if (this.status.equals(STATUS_BELUM_DIKERJAKAN)) {
            this.status = STATUS_DIKERJAKAN;
            System.out.println("Proyek '" + judul + "' dimulai!");
        } else {
            System.out.println("Proyek sudah dalam status: " + status);
        }
    }

    /**
     * Method untuk submit proyek yang sudah selesai
     * Mengubah status dari DIKERJAKAN ke SELESAI
     * 
     * @param filePath Path ke file proyek yang disubmit
     * @return true jika berhasil submit
     */
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

    /**
     * Method untuk validasi proyek oleh guru
     * Mengubah status dari SELESAI ke TERVALIDASI dan memberikan skor
     * 
     * @param skorDiberikan Skor yang diberikan guru (0-100)
     * @return true jika berhasil divalidasi
     */
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

    /**
     * Cek apakah proyek sudah melewati deadline
     * 
     * @return true jika sudah lewat deadline
     */
    public boolean isTerlambat() {
        LocalDate today = LocalDate.now();
        return today.isAfter(deadline) && !status.equals(STATUS_TERVALIDASI);
    }

    /**
     * Mendapatkan jumlah hari tersisa sampai deadline
     * 
     * @return jumlah hari tersisa (negatif jika sudah lewat)
     */
    public long getHariTersisa() {
        LocalDate today = LocalDate.now();
        return java.time.temporal.ChronoUnit.DAYS.between(today, deadline);
    }

    /**
     * Format deadline menjadi String yang mudah dibaca
     * 
     * @return String tanggal deadline (format: dd/MM/yyyy)
     */
    public String getDeadlineFormatted() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return deadline.format(formatter);
    }

    /**
     * Override toString() untuk debugging
     * 
     * @return String representation dari Proyek
     */
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
