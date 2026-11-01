
package com.student.servlet;

import com.student.dao.StudentDAO;
import com.student.model.Student;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/edit-student")
public class EditStudentServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private final StudentDAO studentDAO = new StudentDAO();

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
                         throws ServletException, IOException {

        int id = parseId(request.getParameter("id"));

        if (id <= 0) {
            response.sendError(400, "Invalid student ID.");
            return;
        }

        try {
            Student student = studentDAO.getStudentById(id);

            if (student == null) {
                response.sendError(404, "Student not found.");
                return;
            }

            request.setAttribute("student", student);

            request.getRequestDispatcher("/edit-student.jsp")
                   .forward(request, response);

        } catch (SQLException e) {
            getServletContext().log("Could not load student", e);
            response.sendError(500, "Unable to load student.");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
                          throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        int id = parseId(request.getParameter("id"));
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String course = request.getParameter("course");
        String phone = request.getParameter("phone");

        if (id <= 0 || isBlank(name)
                || isBlank(email) || isBlank(course)) {
            response.sendError(400, "Invalid student details.");
            return;
        }

        Student student = new Student(
                id, name.trim(), email.trim(),
                course.trim(),
                isBlank(phone) ? null : phone.trim()
        );

        try {
            if (!studentDAO.updateStudent(student)) {
                response.sendError(404, "Student not found.");
                return;
            }

            response.sendRedirect(
                    request.getContextPath() + "/students");

        } catch (SQLException e) {
            getServletContext().log("Could not update student", e);

            if ("23505".equals(e.getSQLState())) {
                response.sendError(409,
                        "That email address belongs to another student.");
            } else {
                response.sendError(500,
                        "Unable to update student.");
            }
        }
    }

    private int parseId(String value) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException | NullPointerException e) {
            return -1;
        }
    }

    private boolean isBlank(String value) {
        return value == null || value.isBlank();
    }
}
