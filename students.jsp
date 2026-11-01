
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<%@ page import="java.util.List, com.student.model.Student" %>

<%!
    private String esc(String value) {
        if (value == null) return "";
        return value.replace("&", "&amp;")
                    .replace("<", "&lt;")
                    .replace(">", "&gt;")
                    .replace("\"", "&quot;")
                    .replace("'", "&#x27;");
    }
%>

<%
    List<Student> students =
        (List<Student>) request.getAttribute("students");
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Student Directory | Student Portal</title>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css"
          rel="stylesheet">

    <style>
        body {
            background: #f1f5f9;
            min-height: 100vh;
        }

        .navbar-custom {
            background: #172554;
        }

        .page-title {
            color: #172554;
        }

        .table thead th {
            background: #172554;
            color: white;
            font-weight: 500;
            white-space: nowrap;
            padding: 15px;
        }

        .table tbody td {
            padding: 14px 15px;
            vertical-align: middle;
        }

        .student-card {
            border: none;
            border-radius: 14px;
            overflow: hidden;
        }

        .btn {
            border-radius: 8px;
        }
    </style>
</head>

<body>

<nav class="navbar navbar-dark navbar-custom shadow-sm">
    <div class="container">
        <a class="navbar-brand fw-bold"
           href="${pageContext.request.contextPath}/students">
            Student Portal
        </a>
        <span class="navbar-text text-white">
            Student Management
        </span>
    </div>
</nav>

<div class="container py-5">

    <div class="d-flex flex-wrap justify-content-between
                align-items-center gap-3 mb-4">
        <div>
            <h2 class="page-title fw-bold mb-1">Registered Students</h2>
            <p class="text-secondary mb-0">
                View and manage student records
            </p>
        </div>

        <a href="${pageContext.request.contextPath}/register.jsp"
           class="btn btn-primary px-4 py-2">
            + Add Student
        </a>
    </div>

    <div class="card student-card shadow-sm">
        <div class="card-body p-0">

            <div class="table-responsive">
                <table class="table table-hover mb-0">

                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Full Name</th>
                            <th>Email Address</th>
                            <th>Course</th>
                            <th>Phone</th>
                            <th class="text-center">Actions</th>
                        </tr>
                    </thead>

                    <tbody>
                    <% if (students == null || students.isEmpty()) { %>
                        <tr>
                            <td colspan="6" class="text-center py-5">
                                <h5 class="text-secondary">
                                    No students registered yet
                                </h5>
                                <p class="text-secondary mb-3">
                                    Add your first student to get started.
                                </p>
                                <a href="${pageContext.request.contextPath}/register.jsp"
                                   class="btn btn-primary">
                                    Register Student
                                </a>
                            </td>
                        </tr>
                    <% } else {
                        for (Student s : students) { %>
                        <tr>
                            <td class="fw-semibold">
                                <%= s.getId() %>
                            </td>

                            <td class="fw-semibold">
                                <%= esc(s.getName()) %>
                            </td>

                            <td>
                                <%= esc(s.getEmail()) %>
                            </td>

                            <td>
                                <span class="badge text-bg-light border">
                                    <%= esc(s.getCourse()) %>
                                </span>
                            </td>

                            <td>
                                <%= esc(s.getPhone()) %>
                            </td>

                            <td>
                                <div class="d-flex justify-content-center
                                            align-items-center gap-2">

                                    <a href="${pageContext.request.contextPath}/edit-student?id=<%= s.getId() %>"
                                       class="btn btn-sm btn-outline-primary">
                                        Edit
                                    </a>

                                    <form class="m-0"
                                          action="${pageContext.request.contextPath}/delete-student"
                                          method="post"
                                          onsubmit="return confirm('Are you sure you want to delete this student?');">

                                        <input type="hidden" name="id"
                                               value="<%= s.getId() %>">

                                        <button type="submit"
                                                class="btn btn-sm btn-outline-danger">
                                            Delete
                                        </button>
                                    </form>

                                </div>
                            </td>
                        </tr>
                    <%  }
                       } %>
                    </tbody>

                </table>
            </div>
        </div>

        <div class="card-footer bg-white text-secondary small py-3">
            Student Registration Portal
        </div>
    </div>

    <div class="text-center mt-4">
        <a href="${pageContext.request.contextPath}/register.jsp"
           class="text-decoration-none">
            ← Back to Registration
        </a>
    </div>

</div>

</body>
</html>
