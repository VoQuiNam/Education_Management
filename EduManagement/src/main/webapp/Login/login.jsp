<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="vi">
<head>
<meta charset="UTF-8">
<title>Login Form</title>
<style>
    body {
        font-family: Arial, sans-serif;
        background: linear-gradient(135deg, rgba(109, 131, 242, 0.7), rgba(31, 60, 136, 0.7)), url('<c:url value='/images/books.jpg'/>') no-repeat center center fixed;
        background-size: cover;
        margin: 0;
        height: 100vh;
        display: flex;
        justify-content: center;
        align-items: center;
    }

    .login-container {
        background-color: rgba(255, 255, 255, 0.9);
        padding: 30px 40px;
        border-radius: 10px;
        box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
        width: 100%;
        max-width: 360px;
        text-align: center;
    }

    .login-container h2 {
        margin-bottom: 20px;
        color: #333;
        font-size: 24px;
        font-weight: 600;
    }

    .login-container input[type="text"], 
    .login-container input[type="password"] {
        width: calc(100% - 20px);
        padding: 12px;
        margin: 10px 0;
        border: 1px solid #ddd;
        border-radius: 5px;
        font-size: 16px;
    }

    .login-container button {
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

    .login-container button:hover {
        background-color: #45a049;
    }

    .login-container a {
        display: block;
        margin-top: 10px;
        color: #4CAF50;
        text-decoration: none;
        font-size: 14px;
    }

    .login-container a:hover {
        text-decoration: underline;
    }

    .login-container .register-link {
        margin-top: 20px;
        font-size: 14px;
    }
</style>
</head>
<body>
     <div class="login-container">
        <h2>Login</h2>
        <form action="<%=request.getContextPath()%>/login" method="post">
            <input type="text" name="account" placeholder="Username" required>
            <input type="password" name="password" placeholder="Password" required>
            <button type="submit">Login</button>
        </form>
        <c:if test="${not empty errorMessage}">
            <p style="color: red;"><c:out value="${errorMessage}"/></p>
        </c:if>
        <a href="#">Forgot password?</a>
        <div class="register-link">
            <span>Don't have an account?</span> <a href="<%=request.getContextPath()%>/register">Register</a>
        </div>
    </div>
</body>
</html>
