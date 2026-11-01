
package com.student.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static final String URL =
            "jdbc:postgresql://localhost:5432/student_registration_db";

    private static final String USER = "postgres";

    // Use your actual PostgreSQL password here.
    private static final String PASSWORD = "DATABASE-PASSWORD";

    public static Connection getConnection() throws SQLException {

        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new SQLException(
                    "PostgreSQL JDBC driver is missing from the application.",
                    e
            );
        }

        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
