package ua.com.elcentr.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Logger;

public class ConnectionToDB {

    private static final Logger LOG = Logger.getLogger(ConnectionToDB.class.getName());

    private static final String URL = "jdbc:postgresql://localhost:5432/bd-elcentr";
    private static final String USER_NAME = "postgres";
    private static final String USER_PASSWORD = "1823";

    protected static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USER_NAME, USER_PASSWORD);
        } catch (SQLException e) {
            LOG.severe(String.format("Access to DB with URL %s denied! User - %s", URL, USER_NAME));
        }
        throw new RuntimeException("Connection was not created!");
    }
}
