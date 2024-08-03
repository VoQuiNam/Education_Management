package com.joctopus.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

import com.joctopus.dao.ClassesDao;
import com.joctopus.dao.ClassesDaoImpl;
import com.joctopus.dao.NotificationDao;
import com.joctopus.dao.NotificationDaoImpl;
import com.joctopus.model.Classes;
import com.joctopus.model.Notification;

@WebServlet("/NotificationController")
public class NotificationController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private NotificationDao notificationDao;
	 private ClassesDao classesDao;

    public NotificationController() {
        super();
        notificationDao = new NotificationDaoImpl();
        classesDao = new ClassesDaoImpl();
    }
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 	HttpSession session = request.getSession();
	        String userType = (String) session.getAttribute("type");

	        if ("Admin".equals(userType)) {
	            List<Notification> notifications = notificationDao.selectAllNotifications();
	            request.setAttribute("notifications", notifications);
	            request.getRequestDispatcher(request.getContextPath() + "/WEB-INF/fragments/menus.jsp").forward(request, response);
	        } else {
	            response.sendRedirect(request.getContextPath() + "/error.jsp");
	        }
	}

	 @Override
	    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
	            throws ServletException, IOException {
	        String action = request.getParameter("action");
	        if ("remove".equals(action)) {
	            try {
	                int notificationId = Integer.parseInt(request.getParameter("id"));
	                notificationDao.removeNotification(notificationId);
	                response.setContentType("application/json");
	                response.getWriter().write("{\"status\":\"success\"}");
	            } catch (NumberFormatException e) {
	                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
	                response.getWriter().write("{\"status\":\"error\",\"message\":\"Invalid notification ID\"}");
	            } catch (Exception e) {
	                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
	                response.getWriter().write("{\"status\":\"error\",\"message\":\"Error removing notification\"}");
	            }
	        } else {
	            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
	            response.getWriter().write("{\"status\":\"error\",\"message\":\"Invalid action\"}");
	        }
	    }
}
