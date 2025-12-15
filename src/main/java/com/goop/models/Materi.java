package com.goop.models;

public class Materi {

    // ==================== ATTRIBUTES ====================

    
    private int id;

    
    private String judul;

    
    private String konten;

    
    private String topik;

    
    private String urlResource;

    
    private int guruId;

    // ==================== CONSTRUCTORS ====================

    
    public Materi() {
        this.urlResource = "";
    }

    
    public Materi(int id, String judul, String konten, String topik, int guruId) {
        this.id = id;
        this.judul = judul;
        this.konten = konten;
        this.topik = topik;
        this.guruId = guruId;
        this.urlResource = "";
    }

    
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

    
    public String getPreview() {
        if (konten.length() <= 150) {
            return konten;
        }
        return konten.substring(0, 150) + "...";
    }

    
    public boolean hasResource() {
        return urlResource != null && !urlResource.isEmpty();
    }

    
    public int getEstimasiWaktuBaca() {
        // Hitung jumlah kata (split by space)
        String[] words = konten.split("\\s+");
        int jumlahKata = words.length;

        // Hitung waktu baca (200 kata/menit)
        int waktu = (int) Math.ceil(jumlahKata / 200.0);

        // Minimal 1 menit
        return Math.max(1, waktu);
    }

    
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
