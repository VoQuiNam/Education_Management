package com.joctopus.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import com.joctopus.dao.ClassesDao;
import com.joctopus.dao.ClassesDaoImpl;
import com.joctopus.dao.NotificationAdminDao;
import com.joctopus.dao.NotificationAdminDaoImpl;
import com.joctopus.dao.NotificationDao;
import com.joctopus.dao.NotificationDaoImpl;

@WebServlet("/NotificationAdminController")
public class NotificationAdminController extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	private NotificationAdminDao notificationDao;
	private ClassesDao classesDao;
   
    public NotificationAdminController() {
        super();
        notificationDao = new NotificationAdminDaoImpl();
        classesDao = new ClassesDaoImpl();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
