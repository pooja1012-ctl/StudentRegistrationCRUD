
package com.student.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import com.student.dao.StudentDAO;
import com.student.model.Student;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/students")
public class StudentListServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        StudentDAO dao = new StudentDAO();

        List<Student> students = null;
		try {
			students = dao.getAllStudents();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        request.setAttribute("students", students);

        request.getRequestDispatcher("/students.jsp")
               .forward(request, response);
    }
}
