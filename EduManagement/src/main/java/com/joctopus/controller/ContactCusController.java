package com.joctopus.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import com.joctopus.dao.ContactDao;
import com.joctopus.dao.ContactDaoImpl;
import com.joctopus.dao.NotificationAdminDao;
import com.joctopus.dao.NotificationAdminDaoImpl;
import com.joctopus.dao.NotificationDao;
import com.joctopus.dao.NotificationDaoImpl;
import com.joctopus.dao.UserDao;
import com.joctopus.dao.UserDaoImpl;
import com.joctopus.model.Classes;
import com.joctopus.model.Contacts;

@WebServlet("/ContactCusController")
public class ContactCusController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ContactDao contactDao;
	private UserDao userDao;
	private NotificationDao notificationDao;
	private NotificationAdminDao notificationadminDao;
       
	public void init() {
		this.contactDao = new ContactDaoImpl();
		this.userDao = new UserDaoImpl();
		this.notificationDao = new NotificationDaoImpl();
		this.notificationadminDao = new NotificationAdminDaoImpl();
	}


	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getParameter("action");
		try {

			switch (action) {
			case "/listContacts":
				listContact(request, response);
				break;
			default:
				break;
			}
		} catch (SQLException ex) {
			throw new ServletException(ex);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	private void listContact(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException, ServletException {
		List<Contacts> listContacts = contactDao.selectAllContact();

		request.setAttribute("listContacts", listContacts);

		RequestDispatcher dispatcher = request.getRequestDispatcher("ContactAdmin/contact_admin.jsp");
		dispatcher.forward(request, response);
	}


}
