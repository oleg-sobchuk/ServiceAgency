package com.serviceagency.dataSource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JdbcDataSource {
    private static final String DATA_SOURCE_URL = "jdbc:mysql://localhost:3306/service_agency";
    private static final String DRIVER = "com.mysql.jdbc.Driver";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";

    public static Connection getConnection(){

        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            return DriverManager.getConnection(DATA_SOURCE_URL, USERNAME, PASSWORD);
        } catch (SQLException ex) {
            throw new RuntimeException("Error connecting to the database", ex);
        }
    }
}
