package com.goop.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Class Siswa - Merepresentasikan siswa dalam sistem GOOP
 * 
 * Konsep OOP yang digunakan:
 * - INHERITANCE: Siswa extends (mewarisi) dari class User
 * Siswa memiliki semua attributes dan methods dari User,
 * plus attributes tambahan yang spesifik untuk siswa
 * - ENCAPSULATION: Semua fields private dengan getter/setter
 * - POLYMORPHISM: Override method toString()
 * 
 * Relasi: Siswa IS-A User (inheritance relationship)
 * 
 * @author GOOP Development Team
 * @version 1.0
 */
public class Siswa extends User {

    // ==================== ATTRIBUTES TAMBAHAN ====================
    // Attributes khusus untuk Siswa (selain yang diwarisi dari User)

    /**
     * NIM (Nomor Induk Mahasiswa/Siswa)
     */
    private String nim;

    /**
     * Kelas siswa (contoh: "XII RPL 1")
     */
    private String kelas;

    /**
     * Skor kognitif siswa dari hasil tes
     * Default value: 0.0
     */
    private double skorKognitif;

    /**
     * Daftar proyek yang dimiliki siswa
     * Menggunakan ArrayList untuk menyimpan multiple Proyek
     * Relasi: Siswa HAS-MANY Proyek (composition relationship)
     */
    private List<Integer> proyekIds; // Menyimpan ID proyek yang dimiliki siswa

    // ==================== CONSTRUCTORS ====================

    /**
     * Constructor default
     * Diperlukan untuk JavaFX
     */
    public Siswa() {
        super(); // Memanggil constructor parent class (User)
        this.proyekIds = new ArrayList<>();
        this.skorKognitif = 0.0;
    }

    /**
     * Constructor dengan parameters
     * 
     * Menggunakan super() untuk memanggil constructor parent class
     * kemudian initialize attributes yang spesifik untuk Siswa
     * 
     * @param id          ID user (dari parent class)
     * @param username    Username (dari parent class)
     * @param password    Password (dari parent class)
     * @param email       Email (dari parent class)
     * @param namaLengkap Nama lengkap (dari parent class)
     * @param nim         NIM siswa
     * @param kelas       Kelas siswa
     */
    public Siswa(int id, String username, String password, String email,
            String namaLengkap, String nim, String kelas) {
        // Memanggil constructor parent class dengan super()
        super(id, username, password, email, namaLengkap, "SISWA");

        // Initialize attributes khusus Siswa
        this.nim = nim;
        this.kelas = kelas;
        this.skorKognitif = 0.0;
        this.proyekIds = new ArrayList<>();
    }

    // ==================== GETTERS & SETTERS ====================

    /**
     * Mendapatkan NIM siswa
     * 
     * @return NIM
     */
    public String getNim() {
        return nim;
    }

    /**
     * Set NIM siswa
     * 
     * @param nim NIM baru
     */
    public void setNim(String nim) {
        this.nim = nim;
    }

    /**
     * Mendapatkan kelas siswa
     * 
     * @return kelas
     */
    public String getKelas() {
        return kelas;
    }

    /**
     * Set kelas siswa
     * 
     * @param kelas kelas baru
     */
    public void setKelas(String kelas) {
        this.kelas = kelas;
    }

    /**
     * Mendapatkan skor kognitif siswa
     * 
     * @return skor kognitif
     */
    public double getSkorKognitif() {
        return skorKognitif;
    }

    /**
     * Set skor kognitif siswa
     * 
     * @param skorKognitif skor kognitif baru
     */
    public void setSkorKognitif(double skorKognitif) {
        this.skorKognitif = skorKognitif;
    }

    /**
     * Mendapatkan daftar ID proyek siswa
     * 
     * @return List of proyek IDs
     */
    public List<Integer> getProyekIds() {
        return proyekIds;
    }

    /**
     * Set daftar ID proyek
     * 
     * @param proyekIds List of proyek IDs baru
     */
    public void setProyekIds(List<Integer> proyekIds) {
        this.proyekIds = proyekIds;
    }

    // ==================== BUSINESS METHODS ====================

    /**
     * Menambahkan proyek ke daftar proyek siswa
     * 
     * @param proyekId ID proyek yang akan ditambahkan
     */
    public void addProyek(int proyekId) {
        if (!proyekIds.contains(proyekId)) {
            proyekIds.add(proyekId);
        }
    }

    /**
     * Menghapus proyek dari daftar proyek siswa
     * 
     * @param proyekId ID proyek yang akan dihapus
     */
    public void removeProyek(int proyekId) {
        proyekIds.remove(Integer.valueOf(proyekId));
    }

    /**
     * Menghitung rata-rata skor dari semua proyek
     * Method ini akan di-implement nanti setelah integrasi dengan DataStore
     * 
     * @return rata-rata skor proyek
     */
    public double hitungRataSkorProyek() {
        // TODO: Implement setelah integrasi dengan DataStore
        return 0.0;
    }

    /**
     * Update skor kognitif berdasarkan hasil tes terbaru
     * 
     * @param skorBaru Skor dari tes kognitif terbaru
     */
    public void updateSkorKognitif(double skorBaru) {
        this.skorKognitif = skorBaru;
    }

    /**
     * Override method toString() dari parent class
     * Ini adalah contoh POLYMORPHISM - method overriding
     * 
     * @return String representation dari Siswa object
     */
    @Override
    public String toString() {
        return "Siswa{" +
                "id=" + getId() + // Menggunakan getter dari parent class
                ", username='" + getUsername() + '\'' +
                ", namaLengkap='" + getNamaLengkap() + '\'' +
                ", nim='" + nim + '\'' +
                ", kelas='" + kelas + '\'' +
                ", skorKognitif=" + skorKognitif +
                ", jumlahProyek=" + proyekIds.size() +
                '}';
    }
}
