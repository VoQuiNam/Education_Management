<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="vi">
<head>
<meta charset="UTF-8">
<title>Register Form</title>
<style>
    body {
        font-family: Arial, sans-serif;
        background: linear-gradient(135deg, rgba(109, 131, 242, 0.7), rgba(31, 60, 136, 0.7)), url('<c:url value="/images/educations.jpg"/>') no-repeat center center fixed;
        background-size: cover;
        margin: 0;
        height: 100vh;
        display: flex;
        justify-content: center;
        align-items: center;
    }

    .register-container {
        background-color: rgba(255, 255, 255, 0.9);
        padding: 30px 40px;
        border-radius: 10px;
        box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
        width: 100%;
        max-width: 360px;
        text-align: center;
    }

    .register-container h2 {
        margin-bottom: 20px;
        color: #333;
        font-size: 24px;
        font-weight: 600;
    }

    .register-container input[type="text"],
    .register-container input[type="password"],
    .register-container input[type="date"],
    .register-container input[type="tel"],
    .register-container select,
    .register-container textarea {
        width: calc(100% - 20px);
        padding: 12px;
        margin: 10px 0;
        border: 1px solid #ddd;
        border-radius: 5px;
        font-size: 16px;
        box-sizing: border-box;
    }

    .register-container button {
        width: 100%;
        padding: 12px;
        background-color: #4CAF50;
        border: none;
        border-radius: 5px;
        color: #fff;
        font-size: 16px;
        cursor: pointer;
        transition: background-color 0.3s ease;
    }

    .register-container button:hover {
        background-color: #45a049;
    }

    .register-container a {
        display: block;
        margin-top: 10px;
        color: #4CAF50;
        text-decoration: none;
        font-size: 14px;
    }

    .register-container a:hover {
        text-decoration: underline;
    }

    /* Modal styles */
    .modal {
        display: none;
        position: fixed;
        z-index: 1;
        left: 0;
        top: 0;
        width: 100%;
        height: 100%;
        overflow: auto;
        background-color: rgba(0, 0, 0, 0.5);
        transition: opacity 0.3s ease;
    }

    .modal-content {
        background-color: #fff;
        margin: 10% auto;
        padding: 20px;
        border: 1px solid #888;
        width: 80%;
        max-width: 400px;
        border-radius: 10px;
        text-align: center;
        box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
        position: relative;
    }

    .modal-header {
        padding-bottom: 15px;
        border-bottom: 1px solid #ddd;
        margin-bottom: 15px;
        font-size: 18px;
        font-weight: 600;
        color: #333;
    }

    .close {
        color: #aaa;
        position: absolute;
        top: 10px;
        right: 20px;
        font-size: 24px;
        font-weight: bold;
        cursor: pointer;
    }

    .close:hover,
    .close:focus {
        color: #000;
    }

    .modal-button {
        background-color: #4CAF50;
        color: white;
        padding: 10px 20px;
        border: none;
        border-radius: 5px;
        cursor: pointer;
        margin-top: 10px;
        font-size: 16px;
    }

    .modal-button:hover {
        background-color: #45a049;
    }

    .error-messages {
        margin-bottom: 10px;
        color: red;
        font-size: 14px;
        text-align: left;
    }

    .error-messages ul {
        padding: 0;
        list-style-type: none;
        margin: 0;
    }

    .error-messages li {
        padding: 5px 0;
    }

</style>
<script>
    window.onload = function() {
        const urlParams = new URLSearchParams(window.location.search);
        if (urlParams.has('success')) {
            document.getElementById('successModal').style.display = 'block';
        }
        if (document.getElementById('errorModal')) {
            document.getElementById('errorModal').style.display = 'block';
        }
    };

    function closeModal() {
        document.getElementById('successModal').style.display = 'none';
        window.location.href = '<%=request.getContextPath()%>/login';
    }

    function closeErrorModal() {
        document.getElementById('errorModal').style.display = 'none';
    }
</script>
</head>
<body>
    <div class="register-container">
        <h2>Register</h2>
        <form action="<%=request.getContextPath()%>/register" method="post">
            <input type="text" name="first_name" placeholder="First Name" value="${param.first_name}">
            <input type="text" name="last_name" placeholder="Last Name" value="${param.last_name}">
            <input type="date" name="DOB" placeholder="Date of Birth" value="${param.DOB}">
            <select name="gender">
                <option value="" disabled ${param.gender == '' ? 'selected' : ''}>Select Gender</option>
                <option value="Male" ${param.gender == 'Male' ? 'selected' : ''}>Male</option>
                <option value="Female" ${param.gender == 'Female' ? 'selected' : ''}>Female</option>
            </select>
            <input type="text" name="address" placeholder="Address" value="${param.address}">
            <input type="tel" name="phone_number" placeholder="Phone Number" value="${param.phone_number}">
            <input type="text" name="account" placeholder="Username" value="${param.account}">
            <input type="password" name="password" placeholder="Password">
            <select name="type">
                <option value="" disabled ${param.type == '' ? 'selected' : ''}>Select Type</option>
                <option value="Parents" ${param.type == 'Parents' ? 'selected' : ''}>Parents</option>
                <option value="Tutors" ${param.type == 'Tutors' ? 'selected' : ''}>Tutors</option>
            </select>
            <button type="submit">Register</button>
        </form>
        <a href="<%=request.getContextPath()%>/login">Already have an account? Login</a>
    </div>

    <!-- Modal for Success -->
    <div id="successModal" class="modal">
        <div class="modal-content">
            <div class="modal-header">
                Registration Success
                <span class="close" onclick="closeModal()">&times;</span>
            </div>
            <p>Registration successful! Please log in.</p>
            <button class="modal-button" onclick="closeModal()">OK</button>
        </div>
    </div>

    <!-- Modal for Errors -->
    <c:if test="${not empty errors}">
        <div id="errorModal" class="modal">
            <div class="modal-content">
                <div class="modal-header">
                    Registration Errors
                    <span class="close" onclick="closeErrorModal()">&times;</span>
                </div>
                <div class="error-messages">
                    <p>There were errors in your submission:</p>
                    <ul>
                        <c:forEach var="error" items="${errors}">
                            <li>${error}</li>
                        </c:forEach>
                    </ul>
                </div>
                <button class="modal-button" onclick="closeErrorModal()">OK</button>
            </div>
        </div>
    </c:if>
</body>
</html>
