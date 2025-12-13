package com.goop.models;

/**
 * Class User - Base class untuk semua pengguna aplikasi GOOP
 * 
 * Konsep OOP yang digunakan:
 * - ENCAPSULATION: Semua fields private dengan getter/setter
 * - INHERITANCE: Class ini akan di-extend oleh Siswa dan Guru
 * - ABSTRACTION: Representasi abstrak dari user
 * 
 * @author GOOP Development Team
 * @version 1.0
 */
public class User {
    
    // ==================== ATTRIBUTES ====================
    // Semua attributes di-set private untuk ENCAPSULATION
    
    /**
     * ID unik untuk setiap user
     */
    private int id;
    
    /**
     * Username untuk login
     */
    private String username;
    
    /**
     * Password untuk autentikasi
     * Note: Dalam aplikasi real, password harus di-hash (MD5, SHA, bcrypt)
     * Untuk pembelajaran, kita simpan plain text
     */
    private String password;
    
    /**
     * Email user
     */
    private String email;
    
    /**
     * Nama lengkap user
     */
    private String namaLengkap;
    
    /**
     * Role user: "SISWA" atau "GURU"
     */
    private String role;
    
    
    // ==================== CONSTRUCTORS ====================
    
    /**
     * Constructor default (no-argument constructor)
     * Diperlukan untuk JavaFX property binding
     */
    public User() {
        // Constructor kosong
    }
    
    /**
     * Constructor dengan semua parameters
     * 
     * @param id ID unik user
     * @param username Username untuk login
     * @param password Password user
     * @param email Email user
     * @param namaLengkap Nama lengkap user
     * @param role Role user (SISWA atau GURU)
     */
    public User(int id, String username, String password, String email, String namaLengkap, String role) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.namaLengkap = namaLengkap;
        this.role = role;
    }
    
    
    // ==================== GETTERS & SETTERS ====================
    // Getter dan Setter adalah implementasi dari ENCAPSULATION
    // Kita kontrol akses ke private fields melalui public methods
    
    /**
     * Mendapatkan ID user
     * @return ID user
     */
    public int getId() {
        return id;
    }
    
    /**
     * Set ID user
     * @param id ID baru
     */
    public void setId(int id) {
        this.id = id;
    }
    
    /**
     * Mendapatkan username
     * @return username
     */
    public String getUsername() {
        return username;
    }
    
    /**
     * Set username
     * @param username username baru
     */
    public void setUsername(String username) {
        this.username = username;
    }
    
    /**
     * Mendapatkan password
     * @return password
     */
    public String getPassword() {
        return password;
    }
    
    /**
     * Set password
     * @param password password baru
     */
    public void setPassword(String password) {
        this.password = password;
    }
    
    /**
     * Mendapatkan email
     * @return email
     */
    public String getEmail() {
        return email;
    }
    
    /**
     * Set email
     * @param email email baru
     */
    public void setEmail(String email) {
        this.email = email;
    }
    
    /**
     * Mendapatkan nama lengkap
     * @return nama lengkap
     */
    public String getNamaLengkap() {
        return namaLengkap;
    }
    
    /**
     * Set nama lengkap
     * @param namaLengkap nama lengkap baru
     */
    public void setNamaLengkap(String namaLengkap) {
        this.namaLengkap = namaLengkap;
    }
    
    /**
     * Mendapatkan role user
     * @return role (SISWA atau GURU)
     */
    public String getRole() {
        return role;
    }
    
    /**
     * Set role user
     * @param role role baru
     */
    public void setRole(String role) {
        this.role = role;
    }
    
    
    // ==================== BUSINESS METHODS ====================
    
    /**
     * Method untuk validasi login
     * Membandingkan username dan password dengan credentials yang diberikan
     * 
     * @param inputUsername Username yang diinput
     * @param inputPassword Password yang diinput
     * @return true jika credentials cocok, false jika tidak
     */
    public boolean validateLogin(String inputUsername, String inputPassword) {
        // Bandingkan username dan password
        return this.username.equals(inputUsername) && this.password.equals(inputPassword);
    }
    
    /**
     * Override method toString() untuk debugging
     * 
     * @return String representation dari User object
     */
    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", namaLengkap='" + namaLengkap + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}
