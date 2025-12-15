package com.goop.models;

import java.util.ArrayList;
import java.util.List;

public class TesKognitif {

    // ==================== ATTRIBUTES ====================

    
    private int id;

    
    private String judul;

    
    private int durasiMenit;

    
    private List<Soal> daftarSoal;

    
    private String status;

    // ==================== CONSTRUCTORS ====================

    
    public TesKognitif() {
        this.daftarSoal = new ArrayList<>();
        this.durasiMenit = 30;
        this.status = "AKTIF";
    }

    
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

    
    public void addSoal(Soal soal) {
        // Set tesId pada soal
        soal.setTesId(this.id);
        // Tambahkan soal ke list
        this.daftarSoal.add(soal);
        System.out.println("Soal ditambahkan ke tes: " + this.judul);
    }

    
    public boolean removeSoal(int soalId) {
        for (int i = 0; i < daftarSoal.size(); i++) {
            if (daftarSoal.get(i).getId() == soalId) {
                daftarSoal.remove(i);
                return true;
            }
        }
        return false;
    }

    
    public int getJumlahSoal() {
        return daftarSoal.size();
    }

    
    public Soal getSoalByIndex(int index) {
        if (index >= 0 && index < daftarSoal.size()) {
            return daftarSoal.get(index);
        }
        return null;
    }

    
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

    
    public void aktivasi() {
        this.status = "AKTIF";
    }

    
    public void nonaktifkan() {
        this.status = "NONAKTIF";
    }

    
    public boolean isAktif() {
        return "AKTIF".equals(this.status);
    }

    
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
