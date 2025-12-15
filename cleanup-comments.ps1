# PowerShell script to remove JavaDoc and block comments from Java files

$files = @(
    "src/main/java/com/goop/models/User.java",
    "src/main/java/com/goop/models/Siswa.java",
    "src/main/java/com/goop/models/Guru.java",
    "src/main/java/com/goop/models/Materi.java",
    "src/main/java/com/goop/models/Modul.java",
    "src/main/java/com/goop/models/Proyek.java",
    "src/main/java/com/goop/models/TesKognitif.java",
    "src/main/java/com/goop/models/Soal.java",
    "src/main/java/com/goop/controllers/DashboardController.java",
    "src/main/java/com/goop/controllers/CreateProyekController.java",
    "src/main/java/com/goop/controllers/CreateMateriController.java",
    "src/main/java/com/goop/controllers/CreateModulController.java"
)

foreach ($file in $files) {
    if (Test-Path $file) {
        Write-Host "Cleaning: $file"
        
        $content = Get-Content $file -Raw
        
        # Remove JavaDoc comments
        $content = $content -replace '/\*\*[\s\S]*?\*/', ''
        
        # Remove block comments
        $content = $content -replace '/\*[\s\S]*?\*/', ''
        
        # Remove excessive blank lines
        $content = $content -replace '(\r?\n){3,}', "`r`n`r`n"
        
        Set-Content -Path $file -Value $content -NoNewline
        
        Write-Host "Done: $file"
    }
}

Write-Host "Cleanup complete!"
