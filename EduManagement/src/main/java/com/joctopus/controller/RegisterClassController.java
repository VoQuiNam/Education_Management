package com.joctopus.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

import com.joctopus.dao.ClassesDao;
import com.joctopus.dao.ClassesDaoImpl;
import com.joctopus.dao.NotificationDao;
import com.joctopus.dao.NotificationDaoImpl;
import com.joctopus.model.Classes;
import com.joctopus.model.Notification;

@WebServlet("/RegisterClassController")
public class RegisterClassController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ClassesDao classesDao;
	private NotificationDao notificationDao;

	public void init() {
		classesDao = new ClassesDaoImpl();
		notificationDao = new NotificationDaoImpl();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

}
