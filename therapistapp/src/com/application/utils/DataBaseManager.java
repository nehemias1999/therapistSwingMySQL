package com.application.utils;

import com.application.exceptions.runtimeExceptions.dataAccessException.DataAccessException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.stream.Collectors;

public class DataBaseManager {

    private static final String DDL_FILE_PATH = "/com/application/resources/db/therapist_db_struct.sql"; 

    /**
     * Ejecuta el script de creación de base de datos y estructura desde el archivo SQL
     */
    public static void initializeDatabase() {
        try (Connection conn = DataBaseConnection.getConnectionWithoutSchema();
             Statement stmt = conn.createStatement()) {

            String sql = readSqlFileFromResources(DDL_FILE_PATH);
            stmt.execute(sql);

        } catch (SQLException | IOException e) {
            throw new RuntimeException("Error al ejecutar el script SQL de inicialización", e);
        }
    }
    
    /**
     * Lee el archivo SQL como texto desde el classpath
     */
    private static String readSqlFileFromResources(String path) throws IOException {
        InputStream inputStream = DataBaseManager.class.getResourceAsStream(path);
        if (inputStream == null) {
            throw new IOException("No se encontró el archivo SQL en el path: " + path);
        }

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            return reader.lines().collect(Collectors.joining("\n"));
        }
    }
    
    /**
     * Verifica si la base de datos principal existe
     * @return Boolean
     */
    public static boolean verifyDatabaseStructure() {
        try (Connection conn = DataBaseConnection.getConnectionWithoutSchema()) {
            ResultSet rs = conn.getMetaData().getCatalogs();
            while (rs.next()) {
                String dbName = rs.getString(1);
                if ("therapist_db".equalsIgnoreCase(dbName)) {
                    return true;
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al verificar existencia de base de datos: " + e.getMessage());
        }
        return false;
    }
    
    /**
     * Verifica si una tabla existe dentro de la base de datos
     * @param tableName
     * @return Boolean
     */
    public static boolean tableExists(String tableName) {
        try (Connection conn = DataBaseConnection.getConnection()) {
            DatabaseMetaData metaData = conn.getMetaData();
            try (ResultSet rs = metaData.getTables("therapist_db", null, tableName, new String[]{"TABLE"})) {
                return rs.next();
            }
        } catch (SQLException e) {
            System.err.println("Error al verificar existencia de tabla: " + e.getMessage());
            return false;
        }
    }
}


