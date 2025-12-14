# GOOP - Game Object-Oriented Programming

![License](https://img.shields.io/badge/license-MIT-blue.svg)
![JavaFX](https://img.shields.io/badge/JavaFX-17.0.2-green.svg)
![Java](https://img.shields.io/badge/Java-11-orange.svg)

**GOOP** adalah aplikasi pembelajaran interaktif yang dirancang untuk meningkatkan kompetensi **Object-Oriented Programming (OOP)** siswa menggunakan metode **Project Based Learning** pada mata pelajaran Pemrograman Berorientasi Objek.

Aplikasi ini dikembangkan berdasarkan jurnal penelitian dari Universitas Negeri Surabaya yang bertujuan membantu siswa memahami konsep OOP melalui pembelajaran berbasis proyek.

---

## 📋 Daftar Isi

- [Fitur Utama](#-fitur-utama)
- [Teknologi Yang Digunakan](#-teknologi-yang-digunakan)
- [Arsitektur Aplikasi](#-arsitektur-aplikasi)
- [Konsep OOP Yang Diterapkan](#-konsep-oop-yang-diterapkan)
- [Cara Install dan Menjalankan](#-cara-install-dan-menjalankan)
- [Struktur Project](#-struktur-project)
- [Panduan Pembelajaran](#-panduan-pembelajaran)
- [Login Credentials](#-login-credentials)
- [Troubleshooting](#-troubleshooting)

---

## 🚀 Fitur Utama

### 1. **Materi Pembelajaran OOP**
- Materi lengkap tentang konsep OOP: Class, Object, Encapsulation, Inheritance, Polymorphism, Abstraction
- Penjelasan dengan contoh code dan use case
- Estimasi waktu baca untuk setiap materi

### 2. **Manajemen Proyek**
- Daftar proyek OOP dengan deadline tracking
- Status proyek: Belum Dikerjakan → Dikerjakan → Selesai → Tervalidasi
- Upload dan submit hasil proyek
- Validasi dan pemberian skor oleh guru

### 3. **Tes Kognitif**
- Tes pilihan ganda untuk mengukur pemahaman OOP
- Timer countdown selama pengerjaan tes
- Automatic scoring dan feedback
- Histori hasil tes

### 4. **Dashboard Interaktif**
- Score boxes menampilkan: Skor Kognitif, Jumlah Proyek, Materi, dan Tes
- Navigasi sidebar yang user-friendly
- Statistik pembelajaran real-time

---

## 🛠 Teknologi Yang Digunakan

- **Java 11+** - Programming language
- **JavaFX 17.0.2** - Framework untuk GUI application
- **Maven** - Build tool dan dependency management
- **FXML** - XML-based markup untuk UI layout (MVC pattern)
- **CSS** - Styling untuk modern UI design
- **Java Collections (ArrayList, HashMap)** - In-memory data storage

---

## 🏗 Arsitektur Aplikasi

Aplikasi ini menggunakan **MVC (Model-View-Controller)** pattern dengan in-memory data storage menggunakan ArrayList.

### Design Patterns Yang Digunakan:

1. **MVC (Model-View-Controller)**
   - **Model**: Classes di package `com.goop.models` (User, Siswa, Guru, Proyek, dll)
   - **View**: FXML files di `resources/fxml` (login.fxml, dashboard.fxml)
   - **Controller**: Classes di `com.goop.controllers` (LoginController, DashboardController)

2. **Singleton Pattern** 
   - `DataStore.java` - Ensures only one instance of data storage exists
   - Private constructor + getInstance() method

3. **Facade Pattern**
   - `SceneManager.java` - Simplifies scene transitions and dialog management

---

## 💡 Konsep OOP Yang Diterapkan

Aplikasi ini adalah **showcase** dari konsep-konsep OOP. Setiap file di-comment dengan sangat detail untuk pembelajaran.

### 1. **Class dan Object**
```java
// User.java adalah CLASS (blueprint)
public class User {
    private String username;
    private String password;
}

// Membuat OBJECT dari class User
User siswa1 = new User("sandy", "123");  // siswa1 adalah OBJECT
```

### 2. **Encapsulation**
```java
public class User {
    private String password;  // Private field - ENCAPSULATION
    
    public String getPassword() {
        return password;
    }
}
```

### 3. **Inheritance (Pewarisan)**
```java
public class User { }

public class Siswa extends User {  // INHERITANCE
    private String nim;
}
```

### 4. **Polymorphism**
```java
@Override  // Method overriding
public String toString() {
    return "Siswa: " + username;
}
```

### 5. **Composition**
```java
public class TesKognitif {
    private List<Soal> daftarSoal;  // HAS-MANY relationship
}
```

---

## 📥 Cara Install dan Menjalankan

### Prerequisites

- ✅ **Java JDK 11 atau lebih tinggi**
- ✅ **Apache Maven 3.6+**

### Langkah-langkah:

1. **Navigate ke folder project**
```bash
cd GOOP-JavaFX
```

2. **Build dengan Maven**
```bash
mvn clean compile
```

3. **Jalankan aplikasi**
```bash
mvn javafx:run
```

---

## 📁 Struktur Project

```
GOOP-JavaFX/
├── src/main/java/com/goop/
│   ├── Main.java
│   ├── models/           # Domain models (User, Siswa, Proyek, dll)
│   ├── data/             # DataStore (Singleton)
│   ├── controllers/      # JavaFX Controllers
│   └── utils/            # SceneManager
│
├── src/main/resources/
│   ├── fxml/             # UI layouts
│   └── css/              # Stylesheets
│
└── pom.xml
```

---

## 📚 Panduan Pembelajaran

### Urutan yang Disarankan:

1. **Level 1**: `User.java`, `Siswa.java`, `Guru.java` - Konsep Dasar OOP
2. **Level 2**: `TesKognitif.java`, `DataStore.java` - Advanced Concepts
3. **Level 3**: `LoginController.java`, `DashboardController.java` - MVC Pattern
4. **Level 4**: `style.css`, `pom.xml` - Configuration

---

## 🔐 Login Credentials

**Siswa**: `sandy` / `123` atau `budi` / `123`  
**Guru**: `bambang` / `123`

---

## 🐛 Troubleshooting

### Error JavaFX missing
```bash
mvn clean compile
mvn javafx:run
```

### CSS tidak applied
Pastikan `style.css` ada di `src/main/resources/css/`

---

## 👥 Referensi Jurnal

- "Pengembangan Aplikasi GOOP" - Sandy Putra Pratama, Bambang Sujatmiko
- Universitas Negeri Surabaya
- [Link Jurnal](https://ejournal.unesa.ac.id/index.php/it-edu/article/view/61262)

---

**Selamat Belajar OOP! 🚀**
