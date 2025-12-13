package com.goop.data;

import com.goop.models.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class DataStore - Singleton class untuk menyimpan semua data aplikasi
 * 
 * DESIGN PATTERN: SINGLETON PATTERN
 * - Memastikan hanya ada 1 instance dari DataStore di seluruh aplikasi
 * - Semua controller mengakses data yang sama
 * - Data persist selama aplikasi running (in-memory storage)
 * 
 * Kenapa menggunakan Singleton?
 * - Menghindari duplikasi data
 * - Centralized data management
 * - Mudah diakses dari mana saja (getInstance())
 * 
 * Konsep OOP yang diterapkan:
 * - ENCAPSULATION: Semua data di-protect dengan private
 * - SINGLETON PATTERN: Private constructor + getInstance()
 * - COMPOSITION: DataStore HAS-MANY dari berbagai model (User, Proyek, dll)
 * 
 * @author GOOP Development Team
 * @version 1.0
 */
public class DataStore {

    // ==================== SINGLETON INSTANCE ====================

    /**
     * Single instance dari DataStore (Singleton pattern)
     * Static variable yang akan di-share oleh semua yang mengakses class ini
     */
    private static DataStore instance;

    // ==================== DATA STORAGE ====================
    // Menggunakan ArrayList untuk menyimpan data
    // Dalam aplikasi real, ini diganti dengan database

    /**
     * List untuk menyimpan semua siswa
     */
    private List<Siswa> siswaList;

    /**
     * List untuk menyimpan semua guru
     */
    private List<Guru> guruList;

    /**
     * List untuk menyimpan semua proyek
     */
    private List<Proyek> proyekList;

    /**
     * List untuk menyimpan semua tes kognitif
     */
    private List<TesKognitif> tesKognitifList;

    /**
     * List untuk menyimpan semua materi pembelajaran
     */
    private List<Materi> materiList;

    /**
     * Map untuk menyimpan hasil tes siswa
     * Key: siswaId, Value: Map<tesId, skor>
     */
    private Map<Integer, Map<Integer, Double>> hasilTesMap;

    /**
     * Map untuk menyimpan code yang ditulis siswa untuk proyek
     * Key: proyekId, Value: code (String)
     */
    private Map<Integer, String> proyekCodeMap;

    /**
     * User yang sedang login
     * Null jika belum ada yang login
     */
    private User currentUser;

    /**
     * Counter untuk auto-increment ID
     */
    private int nextProyekId = 1;
    private int nextTesId = 1;
    private int nextMateriId = 1;
    private int nextSoalId = 1;

    // ==================== CONSTRUCTOR ====================

    /**
     * Private constructor - KUNCI dari Singleton Pattern
     * Constructor dibuat private agar tidak bisa dipanggil dari luar class
     * Hanya bisa di-instantiate dari dalam class sendiri (via getInstance())
     */
    private DataStore() {
        // Initialize all lists
        siswaList = new ArrayList<>();
        guruList = new ArrayList<>();
        proyekList = new ArrayList<>();
        tesKognitifList = new ArrayList<>();
        materiList = new ArrayList<>();
        hasilTesMap = new HashMap<>();
        proyekCodeMap = new HashMap<>();

        // Initialize with sample data
        // Data dummy untuk testing dan demo
        initializeSampleData();
    }

    /**
     * Method untuk mendapatkan instance DataStore (Singleton pattern)
     * 
     * Cara kerja:
     * - Jika instance belum ada (null), buat instance baru
     * - Jika sudah ada, return instance yang sudah ada
     * - Dengan begitu, selalu hanya ada 1 instance di seluruh aplikasi
     * 
     * @return Instance tunggal dari DataStore
     */
    public static DataStore getInstance() {
        if (instance == null) {
            instance = new DataStore();
        }
        return instance;
    }

    // ==================== AUTHENTICATION METHODS ====================

    /**
     * Method untuk login
     * Memeriksa username dan password di list siswa dan guru
     * 
     * @param username Username yang diinput
     * @param password Password yang diinput
     * @return User object jika berhasil login, null jika gagal
     */
    public User login(String username, String password) {
        // Cek di list siswa
        for (Siswa siswa : siswaList) {
            if (siswa.validateLogin(username, password)) {
                currentUser = siswa;
                System.out.println("Login berhasil sebagai Siswa: " + siswa.getNamaLengkap());
                return siswa;
            }
        }

        // Cek di list guru
        for (Guru guru : guruList) {
            if (guru.validateLogin(username, password)) {
                currentUser = guru;
                System.out.println("Login berhasil sebagai Guru: " + guru.getNamaLengkap());
                return guru;
            }
        }

        // Login gagal
        System.out.println("Login gagal: Username atau password salah");
        return null;
    }

    /**
     * Method untuk logout
     */
    public void logout() {
        if (currentUser != null) {
            System.out.println("Logout: " + currentUser.getNamaLengkap());
            currentUser = null;
        }
    }

    /**
     * Mendapatkan user yang sedang login
     * 
     * @return User yang sedang login, null jika belum login
     */
    public User getCurrentUser() {
        return currentUser;
    }

    /**
     * Cek apakah ada user yang sedang login
     * 
     * @return true jika ada user login
     */
    public boolean isLoggedIn() {
        return currentUser != null;
    }

    // ==================== SISWA METHODS ====================

    /**
     * Mendapatkan semua siswa
     * 
     * @return List of all Siswa
     */
    public List<Siswa> getAllSiswa() {
        return new ArrayList<>(siswaList); // Return copy untuk encapsulation
    }

    /**
     * Mendapatkan siswa berdasarkan ID
     * 
     * @param id ID siswa
     * @return Siswa object atau null jika tidak ditemukan
     */
    public Siswa getSiswaById(int id) {
        for (Siswa siswa : siswaList) {
            if (siswa.getId() == id) {
                return siswa;
            }
        }
        return null;
    }

    /**
     * Menambah siswa baru
     * 
     * @param siswa Siswa object yang akan ditambahkan
     */
    public void addSiswa(Siswa siswa) {
        siswaList.add(siswa);
    }

    /**
     * Update siswa yang sudah ada
     * 
     * @param siswa Siswa object dengan data terbaru
     */
    public void updateSiswa(Siswa siswa) {
        for (int i = 0; i < siswaList.size(); i++) {
            if (siswaList.get(i).getId() == siswa.getId()) {
                siswaList.set(i, siswa);
                break;
            }
        }
    }

    // ==================== GURU METHODS ====================

    /**
     * Mendapatkan semua guru
     * 
     * @return List of all Guru
     */
    public List<Guru> getAllGuru() {
        return new ArrayList<>(guruList);
    }

    /**
     * Menambah guru baru
     * 
     * @param guru Guru object yang akan ditambahkan
     */
    public void addGuru(Guru guru) {
        guruList.add(guru);
    }

    // ==================== PROYEK METHODS ====================

    /**
     * Mendapatkan semua proyek
     * 
     * @return List of all Proyek
     */
    public List<Proyek> getAllProyek() {
        return new ArrayList<>(proyekList);
    }

    /**
     * Mendapatkan proyek berdasarkan siswa ID
     * Filter proyek yang dimiliki oleh siswa tertentu
     * 
     * @param siswaId ID siswa
     * @return List of Proyek milik siswa tersebut
     */
    public List<Proyek> getProyekBySiswaId(int siswaId) {
        List<Proyek> result = new ArrayList<>();
        for (Proyek proyek : proyekList) {
            if (proyek.getSiswaId() == siswaId) {
                result.add(proyek);
            }
        }
        return result;
    }

    /**
     * Mendapatkan proyek berdasarkan ID
     * 
     * @param id ID proyek
     * @return Proyek object atau null
     */
    public Proyek getProyekById(int id) {
        for (Proyek proyek : proyekList) {
            if (proyek.getId() == id) {
                return proyek;
            }
        }
        return null;
    }

    /**
     * Menambah proyek baru
     * 
     * @param proyek Proyek object yang akan ditambahkan
     */
    public void addProyek(Proyek proyek) {
        proyek.setId(nextProyekId++);
        proyekList.add(proyek);

        // Tambahkan ke list proyek siswa
        Siswa siswa = getSiswaById(proyek.getSiswaId());
        if (siswa != null) {
            siswa.addProyek(proyek.getId());
        }
    }

    /**
     * Update proyek yang sudah ada
     * 
     * @param proyek Proyek object dengan data terbaru
     */
    public void updateProyek(Proyek proyek) {
        for (int i = 0; i < proyekList.size(); i++) {
            if (proyekList.get(i).getId() == proyek.getId()) {
                proyekList.set(i, proyek);
                break;
            }
        }
    }

    /**
     * Hapus proyek berdasarkan ID
     * 
     * @param id ID proyek yang akan dihapus
     * @return true jika berhasil dihapus
     */
    public boolean deleteProyek(int id) {
        for (int i = 0; i < proyekList.size(); i++) {
            if (proyekList.get(i).getId() == id) {
                proyekList.remove(i);
                return true;
            }
        }
        return false;
    }

    // ==================== TES KOGNITIF METHODS ====================

    /**
     * Mendapatkan semua tes kognitif
     * 
     * @return List of all TesKognitif
     */
    public List<TesKognitif> getAllTesKognitif() {
        return new ArrayList<>(tesKognitifList);
    }

    /**
     * Mendapatkan tes kognitif yang aktif saja
     * 
     * @return List of active TesKognitif
     */
    public List<TesKognitif> getActiveTesKognitif() {
        List<TesKognitif> result = new ArrayList<>();
        for (TesKognitif tes : tesKognitifList) {
            if (tes.isAktif()) {
                result.add(tes);
            }
        }
        return result;
    }

    /**
     * Mendapatkan tes kognitif berdasarkan ID
     * 
     * @param id ID tes
     * @return TesKognitif object atau null
     */
    public TesKognitif getTesKognitifById(int id) {
        for (TesKognitif tes : tesKognitifList) {
            if (tes.getId() == id) {
                return tes;
            }
        }
        return null;
    }

    /**
     * Menambah tes kognitif baru
     * 
     * @param tes TesKognitif object
     */
    public void addTesKognitif(TesKognitif tes) {
        tes.setId(nextTesId++);
        tesKognitifList.add(tes);
    }

    /**
     * Menyimpan hasil tes siswa
     * 
     * @param siswaId ID siswa
     * @param tesId   ID tes
     * @param skor    Skor yang didapat
     */
    public void saveHasilTes(int siswaId, int tesId, double skor) {
        // Inisialisasi map jika belum ada
        if (!hasilTesMap.containsKey(siswaId)) {
            hasilTesMap.put(siswaId, new HashMap<>());
        }

        // Simpan skor
        hasilTesMap.get(siswaId).put(tesId, skor);

        // Update skor kognitif siswa (ambil rata-rata semua tes)
        Siswa siswa = getSiswaById(siswaId);
        if (siswa != null) {
            double totalSkor = 0;
            int count = 0;
            for (double skorTes : hasilTesMap.get(siswaId).values()) {
                totalSkor += skorTes;
                count++;
            }
            double rataSkor = totalSkor / count;
            siswa.setSkorKognitif(rataSkor);
        }
    }

    /**
     * Mendapatkan hasil tes siswa
     * 
     * @param siswaId ID siswa
     * @param tesId   ID tes
     * @return Skor atau 0 jika belum pernah mengerjakan
     */
    public double getHasilTes(int siswaId, int tesId) {
        if (hasilTesMap.containsKey(siswaId)) {
            return hasilTesMap.get(siswaId).getOrDefault(tesId, 0.0);
        }
        return 0.0;
    }

    // ==================== MATERI METHODS ====================

    /**
     * Mendapatkan semua materi
     * 
     * @return List of all Materi
     */
    public List<Materi> getAllMateri() {
        return new ArrayList<>(materiList);
    }

    /**
     * Mendapatkan materi berdasarkan topik
     * 
     * @param topik Topik materi
     * @return List of Materi dengan topik tersebut
     */
    public List<Materi> getMateriByTopik(String topik) {
        List<Materi> result = new ArrayList<>();
        for (Materi materi : materiList) {
            if (materi.getTopik().equalsIgnoreCase(topik)) {
                result.add(materi);
            }
        }
        return result;
    }

    /**
     * Mendapatkan materi berdasarkan ID
     * 
     * @param id ID materi
     * @return Materi object atau null
     */
    public Materi getMateriById(int id) {
        for (Materi materi : materiList) {
            if (materi.getId() == id) {
                return materi;
            }
        }
        return null;
    }

    /**
     * Menambah materi baru
     * 
     * @param materi Materi object
     */
    public void addMateri(Materi materi) {
        materi.setId(nextMateriId++);
        materiList.add(materi);
    }

    // ==================== PROYEK CODE METHODS ====================

    /**
     * Get code yang disimpan untuk proyek tertentu
     * 
     * @param proyekId ID proyek
     * @return Code string atau null jika belum ada
     */
    public String getProyekCode(int proyekId) {
        return proyekCodeMap.getOrDefault(proyekId, null);
    }

    /**
     * Simpan code yang ditulis siswa untuk proyek
     * 
     * @param proyekId ID proyek
     * @param code     Code yang ditulis siswa
     */
    public void saveProyekCode(int proyekId, String code) {
        proyekCodeMap.put(proyekId, code);
    }

    // ==================== INITIALIZE SAMPLE DATA ====================

    /**
     * Method untuk initialize sample data
     * Data dummy untuk testing dan demo aplikasi
     * 
     * Dipanggil otomatis saat DataStore pertama kali dibuat
     */
    private void initializeSampleData() {
        System.out.println("============================================");
        System.out.println("Initializing Sample Data...");
        System.out.println("============================================");

        // ===== SAMPLE USERS =====

        // Siswa 1 - Sandy Putra (sesuai dengan jurnal)
        Siswa siswa1 = new Siswa(1, "sandy", "123", "sandy@email.com",
                "Sandy Putra Pratama", "12345", "XII RPL");
        siswaList.add(siswa1);

        // Siswa 2 - Budi
        Siswa siswa2 = new Siswa(2, "budi", "123", "budi@email.com",
                "Budi Santoso", "12346", "XII RPL");
        siswaList.add(siswa2);

        // Siswa 3 - Ani
        Siswa siswa3 = new Siswa(3, "ani", "123", "ani@email.com",
                "Ani Wijaya", "12347", "XII RPL");
        siswaList.add(siswa3);

        // Guru 1 - Bambang Sujatmiko (sesuai dengan jurnal)
        Guru guru1 = new Guru(4, "bambang", "123", "bambang@email.com",
                "Bambang Sujatmiko", "98765");
        guruList.add(guru1);

        System.out.println("✓ Created " + siswaList.size() + " siswa and " + guruList.size() + " guru");

        // ===== SAMPLE PROYEK =====
        // Proyek sederhana untuk pemula

        // Proyek 1 - Hello World (untuk Sandy, siswa1 id=1)
        Proyek proyek1 = new Proyek(nextProyekId++, "Hello World - Program Pertama",
                "Buat program Java sederhana yang menampilkan teks 'Hello, World!' ke console.\n\n" +
                        "Instruksi:\n" +
                        "1. Buat class bernama HelloWorld\n" +
                        "2. Tambahkan method main\n" +
                        "3. Gunakan System.out.println() untuk print 'Hello, World!'\n\n" +
                        "Contoh output:\n" +
                        "Hello, World!",
                LocalDate.now().plusDays(7), Proyek.STATUS_BELUM_DIKERJAKAN, 0, 1);
        proyekList.add(proyek1);
        siswa1.addProyek(proyek1.getId());

        // Proyek 2 - Kalkulator Sederhana (untuk Sandy, siswa1 id=1)
        Proyek proyek2 = new Proyek(nextProyekId++, "Kalkulator Sederhana",
                "Buat program kalkulator yang bisa menjumlahkan dua angka.\n\n" +
                        "Instruksi:\n" +
                        "1. Buat class Calculator\n" +
                        "2. Buat method tambah(int a, int b) yang return hasil penjumlahan\n" +
                        "3. Test di method main dengan beberapa angka\n\n" +
                        "Contoh output:\n" +
                        "5 + 3 = 8",
                LocalDate.now().plusDays(14), Proyek.STATUS_BELUM_DIKERJAKAN, 0, 1);
        proyekList.add(proyek2);
        siswa1.addProyek(proyek2.getId());

        // Proyek 3 - Sapa Nama (untuk Budi, siswa2 id=2)
        Proyek proyek3 = new Proyek(nextProyekId++, "Program Sapa Nama",
                "Buat program yang menyapa pengguna dengan nama mereka.\n\n" +
                        "Instruksi:\n" +
                        "1. Buat class Sapa\n" +
                        "2. Buat method sapaNama(String nama)\n" +
                        "3. Method harus print 'Halo, [nama]! Selamat datang!'\n\n" +
                        "Contoh output:\n" +
                        "Halo, Budi! Selamat datang!",
                LocalDate.now().plusDays(10), Proyek.STATUS_BELUM_DIKERJAKAN, 0, 2);
        proyekList.add(proyek3);
        siswa2.addProyek(proyek3.getId());

        System.out.println("✓ Created " + proyekList.size() + " proyek");

        // ===== SAMPLE TES KOGNITIF =====

        TesKognitif tes1 = new TesKognitif(nextTesId++, "Tes Pemahaman OOP Dasar", 30);

        // Tambahkan soal-soal ke tes
        tes1.addSoal(new Soal(nextSoalId++, tes1.getId(),
                "Apa yang dimaksud dengan Class dalam OOP?",
                "Template atau blueprint untuk membuat object",
                "Variable untuk menyimpan data",
                "Function untuk menjalankan program",
                "Looping untuk mengulang proses",
                "A"));

        tes1.addSoal(new Soal(nextSoalId++, tes1.getId(),
                "Apa yang dimaksud dengan Object?",
                "Looping statement",
                "Instance atau realisasi dari sebuah class",
                "Method dalam class",
                "Variable global",
                "B"));

        tes1.addSoal(new Soal(nextSoalId++, tes1.getId(),
                "Apa itu Encapsulation?",
                "Pewarisan properties dari parent class",
                "Polymorphisme dalam OOP",
                "Pembungkusan data dan method dalam satu unit (class)",
                "Abstraksi dari object",
                "C"));

        tes1.addSoal(new Soal(nextSoalId++, tes1.getId(),
                "Keyword untuk inheritance di Java adalah?",
                "implements",
                "inherits",
                "extends",
                "inherit",
                "C"));

        tes1.addSoal(new Soal(nextSoalId++, tes1.getId(),
                "Apa keuntungan menggunakan Inheritance?",
                "Code lebih lambat",
                "Code reusability dan hierarki class",
                "Memerlukan lebih banyak memory",
                "Susah di-maintain",
                "B"));

        tes1.addSoal(new Soal(nextSoalId++, tes1.getId(),
                "Apa itu Polymorphism?",
                "Kemampuan object untuk mengambil banyak bentuk",
                "Membuat banyak class",
                "Menggunakan banyak variable",
                "Inheritance dari multiple class",
                "A"));

        tes1.addSoal(new Soal(nextSoalId++, tes1.getId(),
                "Manakah yang bukan pilar OOP?",
                "Encapsulation",
                "Inheritance",
                "Compilation",
                "Polymorphism",
                "C"));

        tes1.addSoal(new Soal(nextSoalId++, tes1.getId(),
                "Access modifier 'private' artinya?",
                "Bisa diakses dari mana saja",
                "Hanya bisa diakses dalam class yang sama",
                "Bisa diakses dari package yang sama",
                "Bisa diakses dari subclass",
                "B"));

        tes1.addSoal(new Soal(nextSoalId++, tes1.getId(),
                "Method yang memiliki nama sama dengan class disebut?",
                "Destructor",
                "Getter",
                "Constructor",
                "Setter",
                "C"));

        tes1.addSoal(new Soal(nextSoalId++, tes1.getId(),
                "Apa fungsi keyword 'super' dalam Java?",
                "Membuat variable super besar",
                "Memanggil constructor atau method dari parent class",
                "Membuat class menjadi abstract",
                "Mengakses static method",
                "B"));

        tesKognitifList.add(tes1);

        System.out.println("✓ Created " + tesKognitifList.size() + " tes with " + tes1.getJumlahSoal() + " soal");

        // ===== SAMPLE MATERI =====

        Materi materi1 = new Materi(nextMateriId++, "Pengenalan OOP",
                "Object-Oriented Programming (OOP) adalah paradigma pemrograman yang berfokus pada konsep object. " +
                        "Object adalah instance dari class yang memiliki attributes (data) dan methods (behavior). " +
                        "OOP memiliki 4 pilar utama: Encapsulation, Inheritance, Polymorphism, dan Abstraction. " +
                        "Dengan OOP, kita bisa membuat program yang lebih modular, terstruktur, dan mudah di-maintain.",
                "Dasar OOP");
        materiList.add(materi1);

        Materi materi2 = new Materi(nextMateriId++, "Class dan Object",
                "Class adalah template atau blueprint untuk membuat object. Class mendefinisikan attributes " +
                        "(properties/fields) dan methods (functions) yang akan dimiliki object. " +
                        "Object adalah instance atau realisasi konkret dari class. Satu class bisa digunakan untuk " +
                        "membuat banyak object. Contoh: Class 'Mobil' bisa membuat object mobil1, mobil2, dst.",
                "Dasar OOP");
        materiList.add(materi2);

        Materi materi3 = new Materi(nextMateriId++, "Encapsulation",
                "Encapsulation adalah pembungkusan data (attributes) dan methods yang bekerja pada data tersebut " +
                        "dalam satu unit (class). Tujuannya adalah untuk menyembunyikan implementasi internal dan hanya "
                        +
                        "mengekspos yang perlu diakses dari luar. Implementasi: menggunakan access modifiers (private, protected, public) "
                        +
                        "dan getter/setter methods.",
                "Dasar OOP");
        materiList.add(materi3);

        Materi materi4 = new Materi(nextMateriId++, "Inheritance (Pewarisan)",
                "Inheritance adalah mekanisme dimana sebuah class (child/subclass) dapat mewarisi attributes dan methods "
                        +
                        "dari class lain (parent/superclass). Keuntungan: code reusability, hierarki class yang jelas, dan "
                        +
                        "memudahkan maintenance. Di Java, menggunakan keyword 'extends'. Contoh: class Siswa extends User.",
                "Advanced OOP");
        materiList.add(materi4);

        Materi materi5 = new Materi(nextMateriId++, "Polymorphism",
                "Polymorphism berarti 'banyak bentuk'. Dalam OOP, polymorphism memungkinkan satu interface untuk " +
                        "digunakan dengan berbagai tipe data atau object yang berbeda. Ada 2 jenis: Compile-time polymorphism "
                        +
                        "(method overloading) dan Runtime polymorphism (method overriding). Contoh: method toString() yang "
                        +
                        "di-override di setiap class.",
                "Advanced OOP");
        materiList.add(materi5);

        Materi materi6 = new Materi(nextMateriId++, "Abstraction",
                "Abstraction adalah proses menyembunyikan detail implementasi dan hanya menampilkan fungsionalitas " +
                        "kepada user. Fokus pada 'apa yang dilakukan' bukan 'bagaimana melakukannya'. Implementasi menggunakan "
                        +
                        "abstract class atau interface. Contoh: kita tahu mobil bisa jalan, tapi tidak perlu tahu detail mesin.",
                "Advanced OOP");
        materiList.add(materi6);

        System.out.println("✓ Created " + materiList.size() + " materi pembelajaran");

        System.out.println("============================================");
        System.out.println("Sample Data Initialization Complete!");
        System.out.println("============================================\n");
        System.out.println("Login Credentials:");
        System.out.println("Siswa  - Username: sandy | Password: 123");
        System.out.println("Siswa  - Username: budi  | Password: 123");
        System.out.println("Guru   - Username: bambang | Password: 123");
        System.out.println("============================================\n");
    }
}
