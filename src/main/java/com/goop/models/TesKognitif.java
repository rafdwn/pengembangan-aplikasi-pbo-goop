package com.goop.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Class TesKognitif - Merepresentasikan tes kognitif untuk mengukur pemahaman
 * OOP
 * 
 * Konsep OOP:
 * - ENCAPSULATION: Private fields dengan public methods
 * - COMPOSITION: TesKognitif HAS-MANY Soal (composition relationship)
 * Satu tes memiliki banyak soal
 * 
 * Cara kerja:
 * 1. Guru membuat TesKognitif dengan judul dan durasi
 * 2. Guru menambahkan beberapa Soal ke tes
 * 3. Siswa mengerjakan tes dan mendapat skor
 * 
 * @author GOOP Development Team
 * @version 1.0
 */
public class TesKognitif {

    // ==================== ATTRIBUTES ====================

    /**
     * ID unik tes kognitif
     */
    private int id;

    /**
     * Judul tes
     * Contoh: "Tes Pemahaman OOP Dasar"
     */
    private String judul;

    /**
     * Durasi pengerjaan tes dalam menit
     * Default: 30 menit
     */
    private int durasiMenit;

    /**
     * Daftar soal dalam tes ini
     * Menggunakan ArrayList untuk menyimpan multiple Soal objects
     * Relasi: TesKognitif HAS-MANY Soal (composition)
     */
    private List<Soal> daftarSoal;

    /**
     * Status tes: AKTIF atau NONAKTIF
     * Hanya tes AKTIF yang bisa dikerjakan siswa
     */
    private String status;

    // ==================== CONSTRUCTORS ====================

    /**
     * Constructor default
     */
    public TesKognitif() {
        this.daftarSoal = new ArrayList<>();
        this.durasiMenit = 30;
        this.status = "AKTIF";
    }

    /**
     * Constructor dengan parameters
     * 
     * @param id          ID tes
     * @param judul       Judul tes
     * @param durasiMenit Durasi pengerjaan dalam menit
     */
    public TesKognitif(int id, String judul, int durasiMenit) {
        this.id = id;
        this.judul = judul;
        this.durasiMenit = durasiMenit;
        this.daftarSoal = new ArrayList<>();
        this.status = "AKTIF";
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

    public int getDurasiMenit() {
        return durasiMenit;
    }

    public void setDurasiMenit(int durasiMenit) {
        this.durasiMenit = durasiMenit;
    }

    public List<Soal> getDaftarSoal() {
        return daftarSoal;
    }

    public void setDaftarSoal(List<Soal> daftarSoal) {
        this.daftarSoal = daftarSoal;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    // ==================== BUSINESS METHODS ====================

    /**
     * Menambahkan soal baru ke dalam tes
     * 
     * @param soal Object Soal yang akan ditambahkan
     */
    public void addSoal(Soal soal) {
        // Set tesId pada soal
        soal.setTesId(this.id);
        // Tambahkan soal ke list
        this.daftarSoal.add(soal);
        System.out.println("Soal ditambahkan ke tes: " + this.judul);
    }

    /**
     * Menghapus soal dari tes berdasarkan ID
     * 
     * @param soalId ID soal yang akan dihapus
     * @return true jika berhasil dihapus
     */
    public boolean removeSoal(int soalId) {
        for (int i = 0; i < daftarSoal.size(); i++) {
            if (daftarSoal.get(i).getId() == soalId) {
                daftarSoal.remove(i);
                return true;
            }
        }
        return false;
    }

    /**
     * Mendapatkan jumlah soal dalam tes
     * 
     * @return jumlah soal
     */
    public int getJumlahSoal() {
        return daftarSoal.size();
    }

    /**
     * Mendapatkan soal berdasarkan index (0-based)
     * 
     * @param index Index soal dalam list
     * @return Object Soal pada index tersebut
     */
    public Soal getSoalByIndex(int index) {
        if (index >= 0 && index < daftarSoal.size()) {
            return daftarSoal.get(index);
        }
        return null;
    }

    /**
     * Menghitung skor berdasarkan jawaban siswa
     * 
     * Cara kerja:
     * 1. Bandingkan setiap jawaban siswa dengan jawaban benar
     * 2. Hitung jumlah jawaban benar
     * 3. Skor = (jumlah benar / total soal) * 100
     * 
     * @param jawabanSiswa Array jawaban siswa (A/B/C/D untuk setiap soal)
     * @return Skor dalam range 0-100
     */
    public double hitungSkor(String[] jawabanSiswa) {
        if (daftarSoal.isEmpty()) {
            return 0.0;
        }

        int jumlahBenar = 0;
        int totalSoal = Math.min(daftarSoal.size(), jawabanSiswa.length);

        // Loop untuk mengecek setiap jawaban
        for (int i = 0; i < totalSoal; i++) {
            Soal soal = daftarSoal.get(i);
            if (soal.cekJawaban(jawabanSiswa[i])) {
                jumlahBenar++;
            }
        }

        // Hitung persentase skor
        double skor = ((double) jumlahBenar / totalSoal) * 100;
        return Math.round(skor * 100.0) / 100.0; // Round to 2 decimal places
    }

    /**
     * Mengaktifkan tes
     */
    public void aktivasi() {
        this.status = "AKTIF";
    }

    /**
     * Menonaktifkan tes
     */
    public void nonaktifkan() {
        this.status = "NONAKTIF";
    }

    /**
     * Cek apakah tes aktif
     * 
     * @return true jika tes aktif
     */
    public boolean isAktif() {
        return "AKTIF".equals(this.status);
    }

    /**
     * Override toString() untuk debugging
     * 
     * @return String representation dari TesKognitif
     */
    @Override
    public String toString() {
        return "TesKognitif{" +
                "id=" + id +
                ", judul='" + judul + '\'' +
                ", durasiMenit=" + durasiMenit +
                ", jumlahSoal=" + getJumlahSoal() +
                ", status='" + status + '\'' +
                '}';
    }
}
