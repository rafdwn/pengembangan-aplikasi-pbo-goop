/**
 * Module descriptor untuk GOOP Application
 * 
 * File ini diperlukan oleh Java Module System (JPMS) sejak Java 9+
 * Mendefinisikan module dependencies dan apa yang di-export
 * 
 * Konsep:
 * - requires: Module lain yang dibutuhkan oleh module ini
 * - opens: Package yang dibuka untuk reflection (diperlukan oleh JavaFX FXML)
 * - exports: Package yang di-export untuk digunakan module lain (optional)
 */
module com.goop {
    // Require JavaFX modules
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;

    // Open packages untuk JavaFX FXML reflection
    // FXML perlu akses ke controller class via reflection
    opens com.goop to javafx.fxml;
    opens com.goop.controllers to javafx.fxml;

    // Open models untuk property binding (jika diperlukan)
    opens com.goop.models to javafx.base;

    // Export packages (optional, jika ada module lain yang perlu akses)
    exports com.goop;
    exports com.goop.controllers;
    exports com.goop.models;
    exports com.goop.data;
    exports com.goop.utils;
}
