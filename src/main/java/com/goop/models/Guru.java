package com.goop.models;

public class Guru extends User {

    // ==================== ATTRIBUTES TAMBAHAN ====================
    // No additional attributes - Guru differentiated by role and behavior

    // ==================== CONSTRUCTORS ====================

    
    public Guru() {
        super();
    }

    
    public Guru(int id, String username, String password, String email, String namaLengkap) {
        // Memanggil constructor parent class
        // Role di-set "GURU" secara otomatis
        super(id, username, password, email, namaLengkap, "GURU");
    }

    // ==================== GETTERS & SETTERS ====================
    // Guru uses inherited getters/setters from User class

    // ==================== BUSINESS METHODS ====================

    
    public boolean validasiProyek(int proyekId, double skor) {
        // TODO: Implement setelah integrasi dengan DataStore
        // Logika:
        // 1. Cek apakah proyek dengan ID tersebut ada
        // 2. Update skor proyek
        // 3. Update status proyek menjadi "TERVALIDASI"

        System.out
                .println("Guru " + getNamaLengkap() + " memvalidasi proyek ID: " + proyekId + " dengan skor: " + skor);
        return true;
    }

    
    public int buatTesBaru(String judulTes, int durasiMenit) {
        // TODO: Implement setelah integrasi dengan DataStore
        System.out.println("Guru " + getNamaLengkap() + " membuat tes baru: " + judulTes);
        return 0;
    }

    
    public int buatMateriBaru(String judul, String konten, String topik) {
        // TODO: Implement setelah integrasi dengan DataStore
        System.out.println("Guru " + getNamaLengkap() + " membuat materi: " + judul);
        return 0;
    }

    
    @Override
    public String toString() {
        return "Guru{" +
                "id=" + getId() +
                ", username='" + getUsername() + '\'' +
                ", namaLengkap='" + getNamaLengkap() + '\'' +
                ", role='" + getRole() + '\'' +
                '}';
    }
}
