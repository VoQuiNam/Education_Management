package com.joctopus.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/verifyResetCode")
public class VerifyCodeController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public VerifyCodeController() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher("ForgotPassword/verifyCode.jsp");
	     dispatcher.forward(request, response);
	}

	  @Override
	    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	        String inputCode = request.getParameter("verificationCode");
	        String sessionCode = (String) request.getSession().getAttribute("verificationCode");

	        if (sessionCode != null && sessionCode.equals(inputCode)) {
	            // Mã xác minh đúng, chuyển đến trang đặt lại mật khẩu
	            response.sendRedirect(request.getContextPath() + "/resetPassword");
	        } else {
	            // Mã sai, hiển thị lỗi
	            request.setAttribute("errorMessage", "Invalid verification code. Please try again.");
	            request.getRequestDispatcher("/verifyCode").forward(request, response);
	        }
	    }

}
