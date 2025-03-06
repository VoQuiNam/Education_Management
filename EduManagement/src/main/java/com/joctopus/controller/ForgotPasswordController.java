package com.joctopus.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import com.joctopus.util.EmailUtil;

@WebServlet("/sendResetCode")
public class ForgotPasswordController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
    public ForgotPasswordController() {
        super();
        // TODO Auto-generated constructor stub
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 RequestDispatcher dispatcher = request.getRequestDispatcher("ForgotPassword/forgotPassword.jsp");
	     dispatcher.forward(request, response);
	}

	
	 @Override
	    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	        String email = request.getParameter("email");

	        // Sinh mã xác minh
	        String verificationCode = String.valueOf((int) (Math.random() * 900000) + 100000);

	        // Lưu mã xác minh vào session (hoặc cơ sở dữ liệu)
	        request.getSession().setAttribute("verificationCode", verificationCode);
	        request.getSession().setAttribute("email", email);

	        // Gửi email
	        boolean emailSent = EmailUtil.sendEmail(email, "Reset Password Code", "Your verification code is: " + verificationCode);

	        if (emailSent) {
	            response.sendRedirect(request.getContextPath() + "/verifyResetCode");
	        } else {
	            request.setAttribute("errorMessage", "Failed to send email. Please try again.");
	            request.getRequestDispatcher("ForgotPassword/forgotPassword").forward(request, response);
	        }
	    }

}
