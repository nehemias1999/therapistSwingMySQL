package com.application.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBaseConnection {

    private static final String SCHEMA = "therapist_db";
    private static final String URL = "jdbc:mysql://localhost:3306/" + SCHEMA + "?useSSL=false&serverTimezone=UTC";
    private static final String URL_NO_SCHEMA = "jdbc:mysql://localhost:3306/?allowMultiQueries=true";
    private static final String USER = "root";
    private static final String PASSWORD = "root";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static Connection getConnectionWithoutSchema() throws SQLException {
        return DriverManager.getConnection(URL_NO_SCHEMA, USER, PASSWORD);
    }
}
