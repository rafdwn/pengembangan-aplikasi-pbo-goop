package com.goop.models;

public class Soal {

    // ==================== ATTRIBUTES ====================

    
    private int id;

    
    private int tesId;

    
    private String pertanyaan;

    
    private String pilihanA;

    
    private String pilihanB;

    
    private String pilihanC;

    
    private String pilihanD;

    
    private String jawabanBenar;

    // ==================== CONSTRUCTORS ====================

    
    public Soal() {
    }

    
    public Soal(int id, String pertanyaan, String pilihanA, String pilihanB,
            String pilihanC, String pilihanD, String jawabanBenar) {
        this.id = id;
        this.pertanyaan = pertanyaan;
        this.pilihanA = pilihanA;
        this.pilihanB = pilihanB;
        this.pilihanC = pilihanC;
        this.pilihanD = pilihanD;
        this.jawabanBenar = jawabanBenar.toUpperCase(); // Konversi ke uppercase untuk konsistensi
    }

    
    public Soal(int id, int tesId, String pertanyaan, String pilihanA, String pilihanB,
            String pilihanC, String pilihanD, String jawabanBenar) {
        this(id, pertanyaan, pilihanA, pilihanB, pilihanC, pilihanD, jawabanBenar);
        this.tesId = tesId;
    }

    // ==================== GETTERS & SETTERS ====================

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTesId() {
        return tesId;
    }

    public void setTesId(int tesId) {
        this.tesId = tesId;
    }

    public String getPertanyaan() {
        return pertanyaan;
    }

    public void setPertanyaan(String pertanyaan) {
        this.pertanyaan = pertanyaan;
    }

    public String getPilihanA() {
        return pilihanA;
    }

    public void setPilihanA(String pilihanA) {
        this.pilihanA = pilihanA;
    }

    public String getPilihanB() {
        return pilihanB;
    }

    public void setPilihanB(String pilihanB) {
        this.pilihanB = pilihanB;
    }

    public String getPilihanC() {
        return pilihanC;
    }

    public void setPilihanC(String pilihanC) {
        this.pilihanC = pilihanC;
    }

    public String getPilihanD() {
        return pilihanD;
    }

    public void setPilihanD(String pilihanD) {
        this.pilihanD = pilihanD;
    }

    public String getJawabanBenar() {
        return jawabanBenar;
    }

    public void setJawabanBenar(String jawabanBenar) {
        this.jawabanBenar = jawabanBenar.toUpperCase();
    }

    // ==================== BUSINESS METHODS ====================

    
    public String getPilihanByLetter(String pilihan) {
        switch (pilihan.toUpperCase()) {
            case "A":
                return pilihanA;
            case "B":
                return pilihanB;
            case "C":
                return pilihanC;
            case "D":
                return pilihanD;
            default:
                return "";
        }
    }

    
    public boolean cekJawaban(String jawaban) {
        return this.jawabanBenar.equalsIgnoreCase(jawaban);
    }

    
    @Override
    public String toString() {
        return "Soal{" +
                "id=" + id +
                ", pertanyaan='" + pertanyaan + '\'' +
                ", jawabanBenar='" + jawabanBenar + '\'' +
                '}';
    }
}
