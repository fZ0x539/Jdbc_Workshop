package se.lexicon.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/world";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "1234";

    public static Connection getConnection () throws SQLException {
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }
}
