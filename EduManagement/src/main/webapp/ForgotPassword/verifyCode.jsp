<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Verify Code</title>
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

        .verify-code-container {
            background-color: rgba(255, 255, 255, 0.9);
            padding: 30px 40px;
            border-radius: 10px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
            width: 100%;
            max-width: 360px;
            text-align: center;
        }

        .verify-code-container h2 {
            margin-bottom: 20px;
            color: #333;
            font-size: 24px;
            font-weight: 600;
        }

        .verify-code-container input[type="text"] {
            width: calc(100% - 20px);
            padding: 12px;
            margin: 10px 0;
            border: 1px solid #ddd;
            border-radius: 5px;
            font-size: 16px;
        }

        .verify-code-container button {
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

        .verify-code-container button:hover {
            background-color: #45a049;
        }

        .verify-code-container a {
            display: block;
            margin-top: 10px;
            color: #4CAF50;
            text-decoration: none;
            font-size: 14px;
        }

        .verify-code-container a:hover {
            text-decoration: underline;
        }
    </style>
</head>
<body>
    <div class="verify-code-container">
        <h2>Verify Code</h2>
        <form action="<%=request.getContextPath()%>/verifyResetCode" method="post">
            <input type="text" name="verificationCode" placeholder="Enter verification code" required>
            <button type="submit">Verify</button>
        </form>
        <a href="<%=request.getContextPath()%>/forgotPassword">Resend Code</a>
    </div>
</body>
</html>
