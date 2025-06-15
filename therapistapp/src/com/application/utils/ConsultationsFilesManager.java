package com.application.utils;

import java.awt.Desktop;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

/**
 * Gestiona la creación y movimiento de archivos relacionados a las consultas.
 * Por defecto en Windows usa "%USERPROFILE%\Documents\therapistapp".
 */
public class ConsultationsFilesManager {
    private final Path baseDir;

    /**
     * Crea un FileManager con directorio base por defecto:
     * %USERPROFILE%\Documents\therapistapp o, si no existe,
     * System.getProperty("user.home")\Documents\therapistapp.
     */
    public ConsultationsFilesManager() {
        String userProfile = System.getenv("USERPROFILE");
        if (userProfile == null || userProfile.isEmpty()) {
            userProfile = System.getProperty("user.home");
        }
        this.baseDir = Paths.get(userProfile, "Documents", "therapistapp", "consultations");
    }

    /**
     * Crea un FileManager con directorio base personalizado.
     * @param baseDirPath ruta al directorio base
     */
    public ConsultationsFilesManager(String baseDirPath) {
        if (baseDirPath == null || baseDirPath.isEmpty()) {
            String userProfile = System.getenv("USERPROFILE");
            if (userProfile == null || userProfile.isEmpty()) {
                userProfile = System.getProperty("user.home");
            }
            this.baseDir = Paths.get(userProfile, "Documents", "therapistapp");
        } else {
            this.baseDir = Paths.get(baseDirPath);
        }
    }

    /**
     * @return el directorio base donde se almacenan los archivos
     */
    public Path getBaseDir() {
        return baseDir;
    }

    /**
     * Inicializa las carpetas base para una consulta:
     * /notes
     * @param consultationId
     * @throws java.io.IOException
     */
    public void initConsultationFolders(UUID consultationId) throws IOException {
        Path root = baseDir.resolve(consultationId.toString());
        Files.createDirectories(root.resolve("notes"));
    }
    
    /**
     * Obtiene la ruta de las notas de la consulta si existe.
     * @param consultationId Identificador de la consulta
     * @return Path al archivo de las notas, o null si no hay ninguna
     * @throws IOException si hay error de E/S
     */
    public Path getConsultationNotes(UUID consultationId) throws IOException {
        Path notesDir = baseDir.resolve(consultationId.toString()).resolve("notes");
        if (!Files.exists(notesDir) || !Files.isDirectory(notesDir)) {
            return null;
        }
        try (DirectoryStream<Path> ds = Files.newDirectoryStream(notesDir)) {
            for (Path entry : ds) {
                if (Files.isRegularFile(entry)) {
                    return entry;
                }
            }
        }
        return null;
    }
    
    /**
     * Devuelve el path absoluto de las notas de la consulta en formato String.
     * @param consultationId Identidicador de la consulta
     * @return ruta absoluta como String, o null si no existe
     * @throws IOException si hay error de E/S
     */
    public String getConsultationNotesPath(UUID consultationId) throws IOException {
        Path notesPath = getConsultationNotes(consultationId);
        return notesPath != null ? notesPath.toAbsolutePath().toString() : "";
    }
        
    public Path createNotesFile(UUID consultationId) throws IOException {
        Path dir = baseDir.resolve(consultationId.toString()).resolve("notes");
        Files.createDirectories(dir);

        String fileName = "notas_de_consulta.docx";
        Path notesFile = dir.resolve(fileName);

        return Files.createFile(notesFile);
    }
    
    public void openNotesFile(Path notesFilePath) throws IOException {
        if (Desktop.isDesktopSupported()) {
            Desktop desktop = Desktop.getDesktop();
            if (Files.exists(notesFilePath)) {
                desktop.open(notesFilePath.toFile());
            } else {
                throw new IOException("El archivo no existe: " + notesFilePath.toString());
            }
        } else {
            throw new UnsupportedOperationException("El sistema no soporta Desktop.open()");
        }
    }

    /**
     * Extrae la extensión del archivo (ej. ".docx"), o cadena vacía si no tiene.
     */
    private String getExtension(Path file) {
        String name = file.getFileName().toString();
        int idx = name.lastIndexOf('.');
        return (idx == -1) ? "" : name.substring(idx);
    }
}

