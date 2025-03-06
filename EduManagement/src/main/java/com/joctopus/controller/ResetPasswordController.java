package com.joctopus.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

import com.joctopus.dao.ClassesDaoImpl;
import com.joctopus.dao.NotificationAdminDaoImpl;
import com.joctopus.dao.NotificationDaoImpl;
import com.joctopus.dao.UserDao;
import com.joctopus.dao.UserDaoImpl;

@WebServlet("/resetPassword")
public class ResetPasswordController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UserDao userDao;
    
	public void init() {
		this.userDao = new UserDaoImpl();
	}

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 // Lấy thông báo lỗi từ session (nếu có)
        String errorMessage = (String) request.getSession().getAttribute("errorMessage");
        request.setAttribute("errorMessage", errorMessage);

        // Xóa thông báo lỗi khỏi session để tránh hiển thị lại
        request.getSession().removeAttribute("errorMessage");
		RequestDispatcher dispatcher = request.getRequestDispatcher("ForgotPassword/resetPassword.jsp");
	    dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    String email = (String) request.getSession().getAttribute("email");
	    String newPassword = request.getParameter("newPassword");
	    String confirmPassword = request.getParameter("confirmPassword");

	    if (newPassword.equals(confirmPassword)) {
	        try {
	            boolean updated = userDao.updatePassword(email, newPassword);

	            if (updated) {
	                request.getSession().invalidate(); // Clear session
	                response.sendRedirect(request.getContextPath() + "/login");
	            } else {
	                request.getSession().setAttribute("errorMessage", "Failed to reset password. Please try again.");
	                response.sendRedirect(request.getContextPath() + "/resetPassword");
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	            request.setAttribute("errorMessage", "An error occurred. Please try again.");
	            response.sendRedirect(request.getContextPath() + "/resetPassword");
	        }
	    } else {
	        request.getSession().setAttribute("errorMessage", "Passwords do not match.");
	        response.sendRedirect(request.getContextPath() + "/resetPassword");
	    }
	}


}
