package com.goop.models;

import java.util.ArrayList;
import java.util.List;

public class Siswa extends User {

    // ==================== ATTRIBUTES TAMBAHAN ====================
    // Attributes khusus untuk Siswa (selain yang diwarisi dari User)

    
    private double skorKognitif;

    
    private List<Integer> proyekIds; // Menyimpan ID proyek yang dimiliki siswa

    // ==================== CONSTRUCTORS ====================

    
    public Siswa() {
        super(); // Memanggil constructor parent class (User)
        this.proyekIds = new ArrayList<>();
        this.skorKognitif = 0.0;
    }

    
    public Siswa(int id, String username, String password, String email, String namaLengkap) {
        // Memanggil constructor parent class dengan super()
        super(id, username, password, email, namaLengkap, "SISWA");

        // Initialize attributes khusus Siswa
        this.skorKognitif = 0.0;
        this.proyekIds = new ArrayList<>();
    }

    // ==================== GETTERS & SETTERS ====================

    
    public double getSkorKognitif() {
        return skorKognitif;
    }

    
    public void setSkorKognitif(double skorKognitif) {
        this.skorKognitif = skorKognitif;
    }

    
    public List<Integer> getProyekIds() {
        return proyekIds;
    }

    
    public void setProyekIds(List<Integer> proyekIds) {
        this.proyekIds = proyekIds;
    }

    // ==================== BUSINESS METHODS ====================

    
    public void addProyek(int proyekId) {
        if (!proyekIds.contains(proyekId)) {
            proyekIds.add(proyekId);
        }
    }

    
    public void removeProyek(int proyekId) {
        proyekIds.remove(Integer.valueOf(proyekId));
    }

    
    public double hitungRataSkorProyek() {
        // TODO: Implement setelah integrasi dengan DataStore
        return 0.0;
    }

    
    public void updateSkorKognitif(double skorBaru) {
        this.skorKognitif = skorBaru;
    }

    
    @Override
    public String toString() {
        return "Siswa{" +
                "id=" + getId() + // Menggunakan getter dari parent class
                ", username='" + getUsername() + '\'' +
                ", namaLengkap='" + getNamaLengkap() + '\'' +
                ", skorKognitif=" + skorKognitif +
                ", jumlahProyek=" + proyekIds.size() +
                '}';
    }
}
