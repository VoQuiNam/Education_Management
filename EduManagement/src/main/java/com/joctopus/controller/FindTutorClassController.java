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
import com.joctopus.dao.NotificationAdminDao;
import com.joctopus.dao.NotificationAdminDaoImpl;
import com.joctopus.dao.NotificationDao;
import com.joctopus.dao.NotificationDaoImpl;
import com.joctopus.dao.UserDao;
import com.joctopus.dao.UserDaoImpl;
import com.joctopus.model.Classes;
import com.joctopus.model.Notification;
import com.joctopus.model.Notification_clients;
import com.joctopus.model.Ucl;
import com.joctopus.model.User;

@WebServlet("/FindTutorClassController")
public class FindTutorClassController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ClassesDao classesDao;
	private UserDao userDao;
	private NotificationDao notificationDao;
	private NotificationAdminDao notificationadminDao;

	public void init() {
        this.classesDao = new ClassesDaoImpl();
        this.userDao = new UserDaoImpl();
        this.notificationDao = new NotificationDaoImpl();
        this.notificationadminDao = new NotificationAdminDaoImpl();
    }
	
	
	
	 protected void doGet(HttpServletRequest request, HttpServletResponse response)
	            throws ServletException, IOException {
	        String action = request.getParameter("action");

	        try {
	            switch (action) {
	                case "/newClass":
	                    showNewForm(request, response);
	                    break;
	                case "/listClassParent":
	                    listClasses(request, response);
	                    break;
	                case "/createClassParent":
	                    createClasses(request, response);
	                    break;
	                case "registerClass": // Assuming this is the action for registering a class
	                    registerClass(request, response);
	                    break;
	                case "filterClasses":
	    	            filterClasses(request, response);
	    	            break;	
	            	case "searchClass":
	    				searchClasses(request, response);
	    				break;
	                default:
	                    listClasses(request, response);
	                    break;
	            }
	        } catch (SQLException ex) {
	            throw new ServletException(ex);
	        }
	    }

	 protected void doPost(HttpServletRequest request, HttpServletResponse response)
	            throws ServletException, IOException {
	        String action = request.getParameter("action");

	        try {
	            switch (action) {
	                case "ConfirmTeach":
	                    ConfirmTeach(request, response);
	                    break;
	                case "cancelClass":
	                    cancelClass(request, response);
	                    break;
	                default:
	                    doGet(request, response);
	                    break;
	            }
	        } catch (SQLException ex) {
	            throw new ServletException(ex);
	        }
	    }
	 
	 private void searchClasses(HttpServletRequest request, HttpServletResponse response)
				throws SQLException, IOException, ServletException {
			String searchQuery = request.getParameter("search");
			List<Classes> filteredClasses;

			if (searchQuery == null || searchQuery.isEmpty()) {
				filteredClasses = classesDao.selectAllClasses();
			} else {
				filteredClasses = classesDao.searchClassesByName(searchQuery);
			}

			request.setAttribute("listClass", filteredClasses);
			RequestDispatcher dispatcher = request.getRequestDispatcher("FindTutorClass/index.jsp");
			dispatcher.forward(request, response);
		}
	 
	 
	 private void filterClasses(HttpServletRequest request, HttpServletResponse response)
		        throws SQLException, IOException, ServletException {
		 	String status = request.getParameter("status");
		    List<Classes> filteredClasses;
		    if (status == null || status.isEmpty() || status.equalsIgnoreCase("All")) {
		        filteredClasses = classesDao.selectAllClasses();
		    } else {
		        filteredClasses = classesDao.selectClassesByStatus(status);
		    }
		    request.setAttribute("listClass", filteredClasses);
		    RequestDispatcher dispatcher = request.getRequestDispatcher("FindTutorClass/index.jsp");
		    dispatcher.forward(request, response);
	}
	
	 private void listClasses(HttpServletRequest request, HttpServletResponse response)
	            throws SQLException, IOException, ServletException {
	        List<Classes> listClass = classesDao.selectAllClasses();
	        request.setAttribute("listClass", listClass);
	        RequestDispatcher dispatcher = request.getRequestDispatcher("FindTutorClass/index.jsp");
	        dispatcher.forward(request, response);
	}
	 
	 private void showNewForm(HttpServletRequest request, HttpServletResponse response)
	            throws ServletException, IOException, SQLException {
	        List<Classes> listClass = classesDao.selectAllClasses();
	        request.setAttribute("listClass", listClass);
	        List<User> listUser = userDao.selectAllUsers();
	        request.setAttribute("listUser", listUser);
	        RequestDispatcher dispatcher = request.getRequestDispatcher("FindTutorClass/CreateClassFindTutor.jsp");
	        dispatcher.forward(request, response);
	    }

	   
		private void createClasses(HttpServletRequest request, HttpServletResponse response)
	            throws SQLException, IOException, ServletException {
	        boolean hasError = false;

	        String class_name = request.getParameter("class_name");
	        String eduClass = request.getParameter("class");
	        String timeParam = request.getParameter("study_time");
	        String subject = request.getParameter("subject");
	        String address = request.getParameter("address");
	        String session = request.getParameter("session");
	        String status = request.getParameter("status");
	        String requeststatus = request.getParameter("requeststatus");
	        String userIdParam = request.getParameter("users");

	        if (class_name.isEmpty()) {
	            request.setAttribute("class_name_error", "Class name cannot be blank.");
	            hasError = true;
	        } else {
	            if (class_name.length() < 2 || class_name.length() > 50) {
	                request.setAttribute("class_name_error", "The class name must be between 2 and 50 characters.");
	                hasError = true;
	            }
	        }

	        if (classesDao.isClassNameExists(class_name)) {
	            request.setAttribute("class_name_error", "This class name already exists. Please choose another one.");
	            hasError = true;
	        }

	        if (eduClass.isEmpty()) {
	            request.setAttribute("class_error", "Class cannot be blank.");
	            hasError = true;
	        }

	        LocalDate study_time = null;
	        if (timeParam.isEmpty()) {
	            request.setAttribute("study_time_error", "Please select a study time.");
	            hasError = true;
	        } else if (!timeParam.matches("\\d{4}-\\d{2}-\\d{2}")) {
	            request.setAttribute("study_time_error", "Please enter a valid date.");
	            hasError = true;
	        } else {
	            study_time = LocalDate.parse(timeParam);
	            if (study_time.getYear() < 1900 || study_time.getYear() > LocalDate.now().getYear()) {
	                request.setAttribute("study_time_error", "Please enter a valid year.");
	                hasError = true;
	            }
	        }

	        if (subject.isEmpty()) {
	            request.setAttribute("subject_error", "Subject cannot be blank.");
	            hasError = true;
	        } else if (subject.length() < 2 || subject.length() > 50) {
	            request.setAttribute("subject_error", "The subject must be between 2 and 50 characters.");
	            hasError = true;
	        }

	        if (address.isEmpty()) {
	            request.setAttribute("address_error", "Address cannot be blank.");
	            hasError = true;
	        } else if (address.length() < 2 || address.length() > 50) {
	            request.setAttribute("address_error", "The address must be between 2 and 50 characters.");
	            hasError = true;
	        }

	        if (session == null || session.isEmpty()) {
	            request.setAttribute("session_error", "Please select a session.");
	            hasError = true;
	        }

	        if (status == null || status.isEmpty()) {
	            request.setAttribute("status_error", "Please select a status.");
	            hasError = true;
	        }

	        HttpSession httpSession = request.getSession();
	        User loggedInUser = (User) httpSession.getAttribute("loggedInUser");
	        

	        List<User> users = userDao.selectAllUsers();
	        request.setAttribute("listUser", users);

	        int userId = Integer.parseInt(userIdParam);
	        User user = userDao.selectUser(userId);

	        Classes newClasses = new Classes(class_name, eduClass, study_time, subject, address, session, status,
	                requeststatus, user);

	        if (!hasError) {
	            classesDao.insertClasses(newClasses);

	         // Nếu người dùng là Tutors hoặc Parents, tạo thông báo cho admin
				
				  List<User> admins = userDao.selectAdminUsers(); for (User admin : admins) {
				  Notification_clients notification = new Notification_clients();
				  notification.setMessage("New class created by " + loggedInUser.getType() +
				  ": " + class_name + " (" + eduClass + ")"); notification.setUserId(admin); //
				  //Gửi thông báo cho admin
				 notificationadminDao.insertNotification(notification); }
				 

	            request.getSession().setAttribute("successMessage", "Class created successfully!");
	            response.sendRedirect(request.getContextPath() + "/FindTutorClassController?action=listClassParent");
	        } else {
	            request.getRequestDispatcher("FindTutorClass/CreateClassFindTutor.jsp").forward(request, response);
	        }
	    }
		
		private void registerClass(HttpServletRequest request, HttpServletResponse response)
		        throws SQLException, IOException, ServletException {
		    HttpSession session = request.getSession();
		    User loggedInUser = (User) session.getAttribute("loggedInUser");
		    int classId = Integer.parseInt(request.getParameter("id"));

		    Classes classToRegister = classesDao.selectClasses(classId);
		    Classes classes = classesDao.getClassesById(classId);
		    if (classToRegister != null) {
		        if (userDao.isUserRegisteredForClass(loggedInUser, classToRegister)) {
		            session.setAttribute("errorMessage", "You are already registered for this class.");
		        }else if (classToRegister.getNumberOfStudents() >= 5) { // Check if number of students is 5 or more
		            session.setAttribute("errorMessage", "Registration full. No more students can register for this class.");
		        }
		         else {
		            Ucl ucl = new Ucl(loggedInUser, classToRegister);
		            userDao.insertUcl(ucl);
		            
		            classToRegister.setNumberOfStudents(classToRegister.getNumberOfStudents() + 1);
		            classesDao.updateClasses(classToRegister);
		            
		            
		            // Send a notification to the class creator or admin
		            User classCreator = classes.getUsers(); // Assuming each class has a creator/owner
		            Notification notification = new Notification();
		            notification.setMessage("User \"" + loggedInUser.getAccount() + "\" has successfully registered for the class \"" + classToRegister.getClass_name() + "\".");
		            notification.setUserId(classCreator); // Send the notification to the class creator or admin
		            notificationDao.insertNotification(notification); // Insert the notification into the database
		            session.setAttribute("successMessage", "You have successfully registered for the class.");
		        }
		    }

		    response.sendRedirect(request.getContextPath() + "/FindTutorClassController?action=listClassParent");
		}


		private void ConfirmTeach(HttpServletRequest request, HttpServletResponse response)
		        throws SQLException, IOException {
		    int classId = Integer.parseInt(request.getParameter("id"));
		    Classes theClass = classesDao.getClassById(classId);

		    HttpSession httpSession = request.getSession();
		    User loggedInUser = (User) httpSession.getAttribute("loggedInUser");

		    if (theClass.getNumberOfStudents() == 0) {
		        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		        response.getWriter().write("Requires a registered instructor.");
		        return;
		    }

		    if (theClass.getUsers().getId() == loggedInUser.getId()) {

				Notification_clients notification = new Notification_clients();
				notification.setMessage("Waiting for approval request for class: " + theClass.getClass_name() + " ("
						+ theClass.getEduClass() + ")");
				notification.setUserId(loggedInUser);

				notificationadminDao.insertNotification(notification);

				response.setStatus(HttpServletResponse.SC_OK);
				response.getWriter().write("Notification sent");
			} else {
				response.setStatus(HttpServletResponse.SC_FORBIDDEN);
				response.getWriter().write("Only the creator of the class can confirm it.");
			}
		}

		
		private void cancelClass(HttpServletRequest request, HttpServletResponse response)
		        throws SQLException, IOException {
		    int classId = Integer.parseInt(request.getParameter("id"));
		    HttpSession httpSession = request.getSession();
		    User loggedInUser = (User) httpSession.getAttribute("loggedInUser");

		    Classes theClass = classesDao.getClassById(classId);

		    // Check if the logged-in user is the creator of the class
			if (theClass != null && theClass.getUsers().getId() == loggedInUser.getId()) {

				// Create a notification for the admin
				Notification_clients notification = new Notification_clients();
				notification.setMessage("Class canceled: " + theClass.getClass_name() + " (" + theClass.getEduClass() + ")");
				notification.setUserId(loggedInUser); // Assuming the notification is tied to the user who canceled the
														// class
				notificationadminDao.insertNotification(notification);

				request.getSession().setAttribute("successMessage",
						"Class canceled successfully. Notification sent to admin.");
			} else {
				request.getSession().setAttribute("errorMessage", "You can only cancel classes you created.");
			}

		    response.sendRedirect(request.getContextPath() + "/FindTutorClassController?action=listClassParent");
		}

}
