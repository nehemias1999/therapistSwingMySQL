package com.application.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.stream.Collectors;

public class DataBaseManager {

    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/?allowMultiQueries=true";
    private static final String USER = "root";
    private static final String PASSWORD = "root";
    private static final String DDL_FILE_PATH = "/com/application/resources/db/therapist_db_struct.sql"; 

    public static void initializeDatabase() {
        try (Connection conn = DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
             Statement stmt = conn.createStatement()) {

            String sql = readSqlFileFromResources(DDL_FILE_PATH);
            stmt.execute(sql);

        } catch (SQLException | IOException e) {
            throw new RuntimeException("Error al ejecutar el script SQL de inicialización", e);
        }
    }
    
    private static String readSqlFileFromResources(String path) throws IOException {
        InputStream inputStream = DataBaseManager.class.getResourceAsStream(path);
        if (inputStream == null) {
            throw new IOException("No se encontró el archivo SQL en el path: " + path);
        }

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            return reader.lines().collect(Collectors.joining("\n"));
        }
    }
    
    public static boolean verifyDatabaseStructure() {
        try (Connection conn = DriverManager.getConnection(JDBC_URL, USER, PASSWORD)) {
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
    
    private static boolean tableExists(DatabaseMetaData metaData, String dbName, String tableName) throws SQLException {
        try (ResultSet rs = metaData.getTables(dbName, null, tableName, new String[]{"TABLE"})) {
            return rs.next(); 
        }
    }
}


