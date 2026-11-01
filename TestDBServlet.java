
package com.student.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import com.student.util.DBConnection;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/test-db")
public class TestDBServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
                         throws IOException {

        response.setContentType("text/plain;charset=UTF-8");

        try (Connection connection = DBConnection.getConnection()) {

            response.getWriter().println(
                    "SUCCESS: PostgreSQL connection established!");

            response.getWriter().println(
                    "Database: " + connection.getCatalog());

        } catch (SQLException e) {

            getServletContext().log("Database connection failed", e);

            response.setStatus(
                    HttpServletResponse.SC_INTERNAL_SERVER_ERROR);

            response.getWriter().println(
                    "Database connection failed. Check the Eclipse Console.");
        }
    }
}
