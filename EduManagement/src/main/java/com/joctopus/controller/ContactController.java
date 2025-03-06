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
import java.time.LocalDate;
import java.util.List;

import com.joctopus.dao.ClassesDao;
import com.joctopus.dao.ClassesDaoImpl;
import com.joctopus.dao.CommentsDao;
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
import com.joctopus.model.Notification_clients;
import com.joctopus.model.User;

@WebServlet("/ContactController")
public class ContactController extends HttpServlet {
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

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getParameter("action");
		// Check if the user is logged in
		HttpSession session = request.getSession(false); // Get the session, don't create a new one
		if (session == null || session.getAttribute("loggedInUser") == null) {
			// Redirect to the login page if the user is not logged in
			response.sendRedirect(request.getContextPath() + "/login");
			return;
		}
		try {

			switch (action) {
			case "/newCon":
				showNewForm(request, response);
				break;
			case "/post":
				PostContact(request, response);
				break;
			default:
				break;
			}
		} catch (SQLException ex) {
			throw new ServletException(ex);
		}
	}

	private void showNewForm(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher("Contact/contactHome.jsp");
		dispatcher.forward(request, response);
	}

	private void PostContact(HttpServletRequest request, HttpServletResponse response)
	        throws SQLException, IOException, ServletException {
	    boolean hasError = false;

	    String title = request.getParameter("title");
	    String content = request.getParameter("content");
	    String userIdParam = request.getParameter("user_id");

	    HttpSession httpSession = request.getSession();
	    User loggedInUser = (User) httpSession.getAttribute("loggedInUser");

	    List<User> users = userDao.selectAllUsers();
	    request.setAttribute("listUser", users);

	    int userId = Integer.parseInt(userIdParam);
	    User user = userDao.selectUser(userId);

	    LocalDate create_time = LocalDate.now();

	    Contacts newContacts = new Contacts(title, content, create_time, user);

	    if (!hasError) {
	        contactDao.postContact(newContacts);

	        // Gửi thông báo đến Admin
	        List<User> admins = userDao.selectAdminUsers();
	        for (User admin : admins) {
	            Notification_clients notification = new Notification_clients();
	            notification.setMessage("New Contact by " + loggedInUser.getFirstName() + " "
	                    + loggedInUser.getLastName() + " (" + loggedInUser.getType() + ")");
	            notification.setUserId(admin);
	            notificationadminDao.insertNotification(notification);
	        }

	        // Đặt thông báo thành công
	        request.getSession().setAttribute("successMessage", "Contact was sent successfully!");
	        response.sendRedirect(request.getContextPath() + "/ContactController?action=/newCon");
	    } else {
	        httpSession.setAttribute("errorMessage", "An error occurred while sending the contact form.");
	        request.getRequestDispatcher("AdminssionClass/CreateClass.jsp").forward(request, response);
	    }
	}


}
