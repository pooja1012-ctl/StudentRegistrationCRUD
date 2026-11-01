
package com.student.servlet;

import com.student.dao.StudentDAO;
import com.student.model.Student;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private final StudentDAO studentDAO = new StudentDAO();

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
                          throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String course = request.getParameter("course");
        String phone = request.getParameter("phone");

        if (isBlank(name) || isBlank(email) || isBlank(course)) {
            response.sendError(400,
                    "Name, email, and course are required.");
            return;
        }

        Student student = new Student(
                name.trim(), email.trim(),
                course.trim(),
                isBlank(phone) ? null : phone.trim()
        );

        try {
            studentDAO.addStudent(student);
            response.sendRedirect(
                    request.getContextPath() + "/students");

        } catch (SQLException e) {
            getServletContext().log("Could not register student", e);

            if ("23505".equals(e.getSQLState())) {
                response.sendError(409,
                        "That email address is already registered.");
            } else {
                response.sendError(500,
                        "Unable to register student. Check the server log.");
            }
        }
    }

    private boolean isBlank(String value) {
        return value == null || value.isBlank();
    }
}
