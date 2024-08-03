package com.joctopus.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import com.joctopus.dao.ClassesDaoImpl;
import com.joctopus.dao.UserDao;
import com.joctopus.dao.UserDaoImpl;
import com.joctopus.model.User;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UserDao userDao;


	public void init() {
		this.userDao = new UserDaoImpl();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher("Login/login.jsp");
		dispatcher.forward(request, response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    String account = request.getParameter("account");
	    String password = request.getParameter("password");
	    String contextPath = request.getContextPath();
	    try {
	        User user = userDao.getUserByAccount(account);
	        if (user != null && user.getPassword().equals(password)) {
	            HttpSession session = request.getSession();
	            session.setAttribute("firstName", user.getFirstName());
	            session.setAttribute("lastName", user.getLastName());
	            session.setAttribute("type", user.getType());
	            session.setAttribute("loggedInUser", user);
	            if (user.getType().equals("Admin")) {
	                response.sendRedirect(contextPath + "/Home/index.jsp");
	            } else {
	                response.sendRedirect(contextPath + "/HomeClient/index.jsp");
	            }
	        } else {
	            request.setAttribute("errorMessage", "Invalid username or password");
	            RequestDispatcher dispatcher = request.getRequestDispatcher("Login/login.jsp");
	            dispatcher.forward(request, response); // Forward to login page with error message
	        }
	    } catch (SQLException e) {
	        throw new ServletException("Database error occurred", e);
	    }
	}

        
       

        
}

