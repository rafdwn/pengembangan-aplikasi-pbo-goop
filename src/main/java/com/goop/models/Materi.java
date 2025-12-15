package com.goop.models;

/**
 * Class Materi - Merepresentasikan materi pembelajaran OOP
 * 
 * Konsep OOP:
 * - ENCAPSULATION: Data materi di-protect dengan private fields
 * - OBJECT: Instance dari class ini adalah satu materi pembelajaran
 * 
 * Materi berisi konten pembelajaran tentang konsep OOP seperti:
 * - Class dan Object
 * - Inheritance
 * - Polymorphism
 * - Encapsulation
 * - Abstraction
 * 
 * @author GOOP Development Team
 * @version 1.0
 */
public class Materi {

    // ==================== ATTRIBUTES ====================

    /**
     * ID unik materi
     */
    private int id;

    /**
     * Judul materi
     * Contoh: "Pengenalan Class dan Object"
     */
    private String judul;

    /**
     * Konten/isi materi pembelajaran
     * Berupa text HTML atau plain text
     */
    private String konten;

    /**
     * Topik/kategori materi
     * Contoh: "Dasar OOP", "Advanced OOP", "Design Pattern"
     */
    private String topik;

    /**
     * URL ke resource tambahan (optional)
     * Bisa berupa link ke video, dokumentasi, dll
     */
    private String urlResource;

    /**
     * ID Guru yang membuat materi ini
     * Untuk tracking ownership dan audit
     */
    private int guruId;

    // ==================== CONSTRUCTORS ====================

    /**
     * Constructor default
     */
    public Materi() {
        this.urlResource = "";
    }

    /**
     * Constructor dengan parameters utama
     * 
     * @param id     ID materi
     * @param judul  Judul materi
     * @param konten Konten pembelajaran
     * @param topik  Topik/kategori materi
     * @param guruId ID Guru yang membuat materi
     */
    public Materi(int id, String judul, String konten, String topik, int guruId) {
        this.id = id;
        this.judul = judul;
        this.konten = konten;
        this.topik = topik;
        this.guruId = guruId;
        this.urlResource = "";
    }

    /**
     * Constructor lengkap dengan URL resource
     * 
     * @param id          ID materi
     * @param judul       Judul materi
     * @param konten      Konten pembelajaran
     * @param topik       Topik/kategori materi
     * @param guruId      ID Guru yang membuat materi
     * @param urlResource URL resource tambahan
     */
    public Materi(int id, String judul, String konten, String topik, int guruId, String urlResource) {
        this.id = id;
        this.judul = judul;
        this.konten = konten;
        this.topik = topik;
        this.guruId = guruId;
        this.urlResource = urlResource;
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

    public String getKonten() {
        return konten;
    }

    public void setKonten(String konten) {
        this.konten = konten;
    }

    public String getTopik() {
        return topik;
    }

    public void setTopik(String topik) {
        this.topik = topik;
    }

    public String getUrlResource() {
        return urlResource;
    }

    public void setUrlResource(String urlResource) {
        this.urlResource = urlResource;
    }

    public int getGuruId() {
        return guruId;
    }

    public void setGuruId(int guruId) {
        this.guruId = guruId;
    }

    // ==================== BUSINESS METHODS ====================

    /**
     * Mendapatkan preview konten (150 karakter pertama)
     * Berguna untuk ditampilkan di list view
     * 
     * @return Preview text konten
     */
    public String getPreview() {
        if (konten.length() <= 150) {
            return konten;
        }
        return konten.substring(0, 150) + "...";
    }

    /**
     * Cek apakah materi memiliki resource URL
     * 
     * @return true jika ada URL resource
     */
    public boolean hasResource() {
        return urlResource != null && !urlResource.isEmpty();
    }

    /**
     * Mendapatkan estimasi waktu baca dalam menit
     * Asumsi: rata-rata orang membaca 200 kata per menit
     * 
     * @return estimasi waktu baca dalam menit
     */
    public int getEstimasiWaktuBaca() {
        // Hitung jumlah kata (split by space)
        String[] words = konten.split("\\s+");
        int jumlahKata = words.length;

        // Hitung waktu baca (200 kata/menit)
        int waktu = (int) Math.ceil(jumlahKata / 200.0);

        // Minimal 1 menit
        return Math.max(1, waktu);
    }

    /**
     * Override toString() untuk debugging
     * 
     * @return String representation dari Materi
     */
    @Override
    public String toString() {
        return "Materi{" +
                "id=" + id +
                ", judul='" + judul + '\'' +
                ", topik='" + topik + '\'' +
                ", hasResource=" + hasResource() +
                ", estimasiWaktu=" + getEstimasiWaktuBaca() + " menit" +
                '}';
    }
}
