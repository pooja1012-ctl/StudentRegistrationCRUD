
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<%@ page import="com.student.model.Student" %>

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
    Student student = (Student) request.getAttribute("student");

    if (student == null) {
        response.sendError(404, "Student not found.");
        return;
    }
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Edit Student</title>
    <style>
        body { font-family: Arial; max-width: 600px; margin: 40px auto; }
        label { display: block; margin-top: 15px; }
        input { width: 100%; padding: 10px; box-sizing: border-box; }
        button { margin-top: 20px; padding: 10px 18px; cursor: pointer; }
    </style>
</head>
<body>
    <h1>Edit Student</h1>

    <form action="${pageContext.request.contextPath}/edit-student"
          method="post">

        <input type="hidden" name="id" value="<%= student.getId() %>">

        <label for="name">Full Name *</label>
        <input id="name" type="text" name="name"
               maxlength="100" required
               value="<%= esc(student.getName()) %>">

        <label for="email">Email *</label>
        <input id="email" type="email" name="email"
               maxlength="150" required
               value="<%= esc(student.getEmail()) %>">

        <label for="course">Course *</label>
        <input id="course" type="text" name="course"
               maxlength="100" required
               value="<%= esc(student.getCourse()) %>">

        <label for="phone">Phone</label>
        <input id="phone" type="tel" name="phone"
               maxlength="20"
               value="<%= esc(student.getPhone()) %>">

        <button type="submit">Save Changes</button>
    </form>

    <p><a href="${pageContext.request.contextPath}/students">
        Cancel and Return</a></p>
</body>
</html>
