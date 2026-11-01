
package com.student.dao;

import com.student.model.Student;
import com.student.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO {

    // CREATE: insert a student
    public boolean addStudent(Student student) throws SQLException {
        String sql = """
            INSERT INTO students (name, email, course, phone)
            VALUES (?, ?, ?, ?)
            """;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, student.getName());
            ps.setString(2, student.getEmail());
            ps.setString(3, student.getCourse());
            ps.setString(4, student.getPhone());

            return ps.executeUpdate() == 1;
        }
    }

    // READ: retrieve all students
    public List<Student> getAllStudents() throws SQLException {
        List<Student> students = new ArrayList<>();

        String sql = """
            SELECT id, name, email, course, phone
            FROM students
            ORDER BY id DESC
            """;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                students.add(mapStudent(rs));
            }
        }

        return students;
    }

    // READ: retrieve one student by ID
    public Student getStudentById(int id) throws SQLException {
        String sql = """
            SELECT id, name, email, course, phone
            FROM students WHERE id = ?
            """;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapStudent(rs);
                }
            }
        }

        return null;
    }

    // UPDATE: edit a student
    public boolean updateStudent(Student student) throws SQLException {
        String sql = """
            UPDATE students
            SET name = ?, email = ?, course = ?, phone = ?
            WHERE id = ?
            """;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, student.getName());
            ps.setString(2, student.getEmail());
            ps.setString(3, student.getCourse());
            ps.setString(4, student.getPhone());
            ps.setInt(5, student.getId());

            return ps.executeUpdate() == 1;
        }
    }

    // DELETE: remove a student
    public boolean deleteStudent(int id) throws SQLException {
        String sql = "DELETE FROM students WHERE id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() == 1;
        }
    }

    private Student mapStudent(ResultSet rs) throws SQLException {
        return new Student(
            rs.getInt("id"),
            rs.getString("name"),
            rs.getString("email"),
            rs.getString("course"),
            rs.getString("phone")
        );
    }
}
