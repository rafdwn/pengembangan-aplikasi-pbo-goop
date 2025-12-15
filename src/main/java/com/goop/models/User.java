package com.goop.models;

public class User {
    
    // ==================== ATTRIBUTES ====================
    // Semua attributes di-set private untuk ENCAPSULATION
    
    
    private int id;
    
    
    private String username;
    
    
    private String password;
    
    
    private String email;
    
    
    private String namaLengkap;
    
    
    private String role;
    
    
    // ==================== CONSTRUCTORS ====================
    
    
    public User() {
        // Constructor kosong
    }
    
    
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
    
    
    public int getId() {
        return id;
    }
    
    
    public void setId(int id) {
        this.id = id;
    }
    
    
    public String getUsername() {
        return username;
    }
    
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    
    public String getPassword() {
        return password;
    }
    
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    
    public String getEmail() {
        return email;
    }
    
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    
    public String getNamaLengkap() {
        return namaLengkap;
    }
    
    
    public void setNamaLengkap(String namaLengkap) {
        this.namaLengkap = namaLengkap;
    }
    
    
    public String getRole() {
        return role;
    }
    
    
    public void setRole(String role) {
        this.role = role;
    }
    
    
    // ==================== BUSINESS METHODS ====================
    
    
    public boolean validateLogin(String inputUsername, String inputPassword) {
        // Bandingkan username dan password
        return this.username.equals(inputUsername) && this.password.equals(inputPassword);
    }
    
    
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
