<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Search Results</title>
    <!-- Include your CSS and other links here -->
</head>

<body>
    <h1>Search Results</h1>

    <ul class="tutor-classes-list">
        <c:forEach var="classes" items="${listClass}">
            <c:if test="${classes.requeststatus ne 'Unapproved'}">
                <li class="tutor-class-item">
                    <h2>Class Name: ${classes.class_name}</h2>
                    <p><strong>Class:</strong> ${classes.eduClass}</p>
                    <p><strong>Study Time:</strong> ${classes.study_time}</p>
                    <p><strong>Subjects:</strong> ${classes.subject}</p>
                    <p><strong>Location:</strong> ${classes.address}</p>
                    <p><strong>Session:</strong> ${classes.session}</p>
                    <p><strong>Status:</strong> ${classes.status}</p>
                    <c:choose>
                        <c:when test="${classes.users.type eq 'Tutors'}">
                            <p><strong>Number of students:</strong> ${classes.numberOfStudents}</p>
                        </c:when>
                    </c:choose>
                    <!-- Include your forms for actions like register and cancel -->
                </li>
            </c:if>
        </c:forEach>
    </ul>
</body>

</html>
