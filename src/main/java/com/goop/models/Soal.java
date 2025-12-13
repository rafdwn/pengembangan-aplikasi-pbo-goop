package com.goop.models;

/**
 * Class Soal - Merepresentasikan satu soal dalam tes kognitif
 * 
 * Konsep OOP:
 * - ENCAPSULATION: Semua data soal di-protect dengan private fields
 * - OBJECT: Instance dari class ini adalah satu soal pilihan ganda
 * 
 * Format soal: Pilihan ganda dengan 4 opsi (A, B, C, D)
 * 
 * @author GOOP Development Team
 * @version 1.0
 */
public class Soal {

    // ==================== ATTRIBUTES ====================

    /**
     * ID unik untuk soal
     */
    private int id;

    /**
     * ID tes kognitif yang memiliki soal ini
     * Relasi: Soal BELONGS-TO TesKognitif
     */
    private int tesId;

    /**
     * Pertanyaan/soal yang ditampilkan
     * Contoh: "Apa itu Class dalam OOP?"
     */
    private String pertanyaan;

    /**
     * Pilihan jawaban A
     */
    private String pilihanA;

    /**
     * Pilihan jawaban B
     */
    private String pilihanB;

    /**
     * Pilihan jawaban C
     */
    private String pilihanC;

    /**
     * Pilihan jawaban D
     */
    private String pilihanD;

    /**
     * Jawaban yang benar (A/B/C/D)
     */
    private String jawabanBenar;

    // ==================== CONSTRUCTORS ====================

    /**
     * Constructor default
     */
    public Soal() {
    }

    /**
     * Constructor dengan semua parameters
     * 
     * @param id           ID soal
     * @param pertanyaan   Teks pertanyaan
     * @param pilihanA     Pilihan A
     * @param pilihanB     Pilihan B
     * @param pilihanC     Pilihan C
     * @param pilihanD     Pilihan D
     * @param jawabanBenar Jawaban benar (A/B/C/D)
     */
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

    /**
     * Constructor dengan tesId
     * 
     * @param id           ID soal
     * @param tesId        ID tes kognitif
     * @param pertanyaan   Teks pertanyaan
     * @param pilihanA     Pilihan A
     * @param pilihanB     Pilihan B
     * @param pilihanC     Pilihan C
     * @param pilihanD     Pilihan D
     * @param jawabanBenar Jawaban benar (A/B/C/D)
     */
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

    /**
     * Method untuk mendapatkan pilihan berdasarkan huruf (A/B/C/D)
     * 
     * @param pilihan Huruf pilihan (A/B/C/D)
     * @return Text pilihan yang sesuai
     */
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

    /**
     * Method untuk mengecek apakah jawaban yang diberikan benar
     * 
     * @param jawaban Jawaban siswa (A/B/C/D)
     * @return true jika jawaban benar, false jika salah
     */
    public boolean cekJawaban(String jawaban) {
        return this.jawabanBenar.equalsIgnoreCase(jawaban);
    }

    /**
     * Override toString() untuk debugging
     * 
     * @return String representation dari Soal
     */
    @Override
    public String toString() {
        return "Soal{" +
                "id=" + id +
                ", pertanyaan='" + pertanyaan + '\'' +
                ", jawabanBenar='" + jawabanBenar + '\'' +
                '}';
    }
}
