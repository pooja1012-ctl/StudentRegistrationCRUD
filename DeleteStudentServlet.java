
package com.student.servlet;

import com.student.dao.StudentDAO;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/delete-student")
public class DeleteStudentServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private final StudentDAO studentDAO = new StudentDAO();

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
                          throws IOException {

        int id;

        try {
            id = Integer.parseInt(request.getParameter("id"));
            if (id <= 0) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException | NullPointerException e) {
            response.sendError(400, "Invalid student ID.");
            return;
        }

        try {
            if (!studentDAO.deleteStudent(id)) {
                response.sendError(404, "Student not found.");
                return;
            }

            response.sendRedirect(
                    request.getContextPath() + "/students");

        } catch (SQLException e) {
            getServletContext().log("Could not delete student", e);
            response.sendError(500, "Unable to delete student.");
        }
    }
}
