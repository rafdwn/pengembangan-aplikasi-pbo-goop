package com.goop.models;

import java.time.LocalDateTime;

public class Modul {

    private int id;
    private String judul;
    private String konten;
    private String topik;
    private int guruId; // ID guru yang membuat modul
    private LocalDateTime tanggalDibuat;

    
    public Modul(int id, String judul, String konten, String topik, int guruId) {
        this.id = id;
        this.judul = judul;
        this.konten = konten;
        this.topik = topik;
        this.guruId = guruId;
        this.tanggalDibuat = LocalDateTime.now();
    }

    // Getters and Setters

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

    public int getGuruId() {
        return guruId;
    }

    public void setGuruId(int guruId) {
        this.guruId = guruId;
    }

    public LocalDateTime getTanggalDibuat() {
        return tanggalDibuat;
    }

    public void setTanggalDibuat(LocalDateTime tanggalDibuat) {
        this.tanggalDibuat = tanggalDibuat;
    }

    @Override
    public String toString() {
        return "Modul{" +
                "id=" + id +
                ", judul='" + judul + '\'' +
                ", topik='" + topik + '\'' +
                ", guruId=" + guruId +
                '}';
    }
}
