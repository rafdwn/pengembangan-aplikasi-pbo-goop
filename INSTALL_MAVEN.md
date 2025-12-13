# Panduan Install Maven di Windows

## Langkah 1: Download Maven

1. Buka browser Anda
2. Kunjungi: **https://maven.apache.org/download.cgi**
3. Scroll ke bawah, cari bagian **"Files"**
4. Download: **`apache-maven-3.9.6-bin.zip`** (Binary zip archive)
5. Tunggu sampai download selesai di folder **Downloads**

## Langkah 2: Extract Maven

Setelah download selesai, jalankan command ini di PowerShell **sebagai Administrator**:

```powershell
# Extract Maven ke Program Files
Expand-Archive -Path "$env:USERPROFILE\Downloads\apache-maven-3.9.6-bin.zip" -DestinationPath "C:\Program Files\Apache" -Force
```

**ATAU** extract manual:
- Right-click file `apache-maven-3.9.6-bin.zip` → Extract All
- Extract ke: `C:\Program Files\Apache\`

## Langkah 3: Set Environment Variable

### Cara Otomatis (PowerShell):

Jalankan command berikut **sebagai Administrator**:

```powershell
# Set MAVEN_HOME
[System.Environment]::SetEnvironmentVariable("MAVEN_HOME", "C:\Program Files\Apache\apache-maven-3.9.6", [System.EnvironmentVariableTarget]::Machine)

# Add Maven bin ke PATH
$currentPath = [System.Environment]::GetEnvironmentVariable("Path", [System.EnvironmentVariableTarget]::Machine)
$newPath = $currentPath + ";C:\Program Files\Apache\apache-maven-3.9.6\bin"
[System.Environment]::SetEnvironmentVariable("Path", $newPath, [System.EnvironmentVariableTarget]::Machine)
```

### Cara Manual (GUI):

1. **Buka System Properties:**
   - Tekan `Win + R`
   - Ketik: `sysdm.cpl`
   - Klik OK

2. **Edit Environment Variables:**
   - Tab "Advanced" → Klik "Environment Variables"
   
3. **Tambah MAVEN_HOME:**
   - Di "System variables" → Klik "New"
   - Variable name: `MAVEN_HOME`
   - Variable value: `C:\Program Files\Apache\apache-maven-3.9.6`
   - Klik OK

4. **Edit PATH:**
   - Di "System variables" → Pilih "Path" → Klik "Edit"
   - Klik "New"
   - Tambahkan: `C:\Program Files\Apache\apache-maven-3.9.6\bin`
   - Klik OK → OK → OK

## Langkah 4: Verify Installation

1. **Tutup dan buka ulang CMD/PowerShell** (penting!)
2. Jalankan command:

```bash
mvn -version
```

3. Jika berhasil, akan muncul output seperti:
```
Apache Maven 3.9.6
Maven home: C:\Program Files\Apache\apache-maven-3.9.6
Java version: 11.x.x
```

## Langkah 5: Run GOOP Application

Setelah Maven terinstall, jalankan aplikasi:

```bash
cd "c:\Users\Mughni Haunan\Documents\PBO\GOOP-JavaFX"
mvn clean javafx:run
```

---

## Troubleshooting

### Error: "mvn tidak dikenali"
- **Solusi**: Restart CMD/PowerShell setelah set environment variable
- Atau restart komputer

### Error: "JAVA_HOME not set"
- **Solusi**: Install JDK terlebih dahulu, lalu set JAVA_HOME
- Download JDK: https://www.oracle.com/java/technologies/downloads/

### Maven download dependencies lambat
- **Solusi**: Tunggu saja, pertama kali akan download banyak dependencies
- Koneksi internet harus stabil

---

## Alternative: Gunakan IDE (Lebih Mudah!)

Jika install Maven terlalu rumit, **gunakan IntelliJ IDEA** saja:
1. Download IntelliJ IDEA Community (gratis): https://www.jetbrains.com/idea/download/
2. Install IntelliJ
3. Open Project → Pilih folder `GOOP-JavaFX`
4. IntelliJ akan auto-install Maven dependencies
5. Klik Run button

**IntelliJ IDEA sudah include Maven built-in**, jadi tidak perlu install Maven manual! ✅
