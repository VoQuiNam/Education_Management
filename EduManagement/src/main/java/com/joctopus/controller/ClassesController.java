package com.joctopus.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.joctopus.dao.ClassesDao;
import com.joctopus.dao.ClassesDaoImpl;
import com.joctopus.dao.UserDao;
import com.joctopus.dao.UserDaoImpl;
import com.joctopus.model.Classes;
import com.joctopus.model.User;

@WebServlet("/ClassesController")
public class ClassesController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ClassesDao classesDao;
	private UserDao userDao;

	public void init() {
		this.classesDao = new ClassesDaoImpl();
		this.userDao = new UserDaoImpl();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getParameter("action");

		try {
			switch (action) {
			case "/newCl":
				showNewForm(request, response);
				break;
			case "/insertCl":
				insertClasses(request, response);
				break;
			case "/deleteCl":
				deleteClasses(request, response);
				break;
			case "/editCl":
				showEditForm(request, response);
				break;
			case "/updateCl":
				updateClasses(request, response);
				break;
			case "/listCl": // Added case for /listCl
				listClasses(request, response);
				break;
			default:
				listClasses(request, response);
				break;
			}
		} catch (SQLException ex) {
			throw new ServletException(ex);
		}
	}

	private void showNewForm(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		List<Classes> listClass = classesDao.selectAllClasses();
		request.setAttribute("listClass", listClass);
		// Lấy danh sách người dùng và đặt nó trong thuộc tính của request
		List<User> listUser = userDao.selectAllUsers();
		request.setAttribute("listUser", listUser);
		RequestDispatcher dispatcher = request.getRequestDispatcher("Classes/classes_form.jsp");
		dispatcher.forward(request, response);
	}

	private void listClasses(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException, ServletException {
		List<Classes> listClass = classesDao.selectAllClasses();
		request.setAttribute("listClass", listClass);
		RequestDispatcher dispatcher = request.getRequestDispatcher("Classes/classes_list.jsp");
		dispatcher.forward(request, response);
	}

	private void insertClasses(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException, ServletException {
		boolean hasError = false; // Flag to check if there are any errors

		// Fetch parameters
		String class_name = request.getParameter("class_name");
		String eduClass = request.getParameter("class");
		String timeParam = request.getParameter("study_time");
		String subject = request.getParameter("subject");
		String address = request.getParameter("address");
		String session = request.getParameter("session");
		String status = request.getParameter("status");
		String requeststatus = request.getParameter("requeststatus");
		String userIdParam = request.getParameter("users");

		// Validate parameters
		if (class_name.isEmpty()) {
			request.setAttribute("class_name_error", "Class name cannot be blank.");
			hasError = true;
		} else {
			if (class_name.length() < 2 || class_name.length() > 50) {
				request.setAttribute("class_name_error", "The class name must be between 2 and 50 characters.");
				hasError = true;
			} /*
				 * else if (class_name.matches(".*\\d.*")) {
				 * request.setAttribute("class_name_error",
				 * "Class name cannot contain numbers."); hasError = true; }
				 */
		}

		if (classesDao.isClassNameExists(class_name)) {
			request.setAttribute("class_name_error", "This class name already exists. Please choose another one.");
			hasError = true;
		}

		if (eduClass.isEmpty()) {
			request.setAttribute("class_error", "Class cannot be blank.");
			hasError = true;
		} /*
			 * else if (eduClass.matches(".*\\d.*")) { request.setAttribute("class_error",
			 * "Class cannot contain numbers."); hasError = true; }
			 */

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
		} /*
			 * else if (subject.matches(".*\\d.*")) { request.setAttribute("subject_error",
			 * "Subject cannot contain numbers."); hasError = true; }
			 */

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

		if (userIdParam == null || userIdParam.isEmpty()) {
			request.setAttribute("userId_error", "Please select a user.");
			hasError = true;
		}

		// Reload users to be displayed in the form
		List<User> users = userDao.selectAllUsers();
		request.setAttribute("listUser", users);

		if (hasError) {
			RequestDispatcher dispatcher = request.getRequestDispatcher("Classes/classes_form.jsp");
			dispatcher.forward(request, response);
			return;
		}

		int userId = Integer.parseInt(userIdParam);
		User user = userDao.selectUser(userId);

		Classes newClasses = new Classes(class_name, eduClass, study_time, subject, address, session, status, requeststatus, user);
		classesDao.insertClasses(newClasses);

		response.sendRedirect("ClassesController?action=/listCl");
	}

	
	private void updateClasses(HttpServletRequest request, HttpServletResponse response)
	        throws SQLException, IOException, ServletException {
	    boolean hasError = false; // Variable to check if any errors were detected

	    int id = Integer.parseInt(request.getParameter("id"));
	    String class_name = request.getParameter("class_name");
	    Classes currentClass = classesDao.selectClasses(id);
	    String eduClass = request.getParameter("class");
	    String timeParam = request.getParameter("study_time");
	    String subject = request.getParameter("subject");
	    String address = request.getParameter("address");
	    String session = request.getParameter("session");
	    String status = request.getParameter("status");
	    String requeststatus = request.getParameter("requeststatus");
	    String numberOfStudentsParam = request.getParameter("numberOfStudents");
	    String userIdParam = request.getParameter("users");

	    if (class_name.isEmpty()) {
	        // Store error message in request attribute for class name
	        request.setAttribute("class_name_error", "Class name cannot be blank.");
	        // Set hasError to true to indicate an error was detected
	        hasError = true;
	    } else {
	        // Check the length of the class name
	        if (class_name.length() < 2 || class_name.length() > 50) {
	            // Store error message in request attribute for class name
	            request.setAttribute("class_name_error", "The class name must be between 2 and 50 characters.");
	            // Set hasError to true to indicate an error was detected
	            hasError = true;
	        }
	    }

	    if (!class_name.equals(currentClass.getClass_name()) && classesDao.isClassNameExists(class_name)) {
	        // Store error message in request attribute for class name
	        request.setAttribute("class_name_error", "This class name already exists. Please choose another one.");
	        // Set hasError to true to indicate an error was detected
	        hasError = true;
	    }

	    if (eduClass.isEmpty()) {
	        // Store error message in request attribute for eduClass
	        request.setAttribute("class_error", "Class cannot be blank.");
	        // Set hasError to true to indicate an error was detected
	        hasError = true;
	    }

	    LocalDate study_time;
	    if (timeParam != null && !timeParam.isEmpty()) {
	        study_time = LocalDate.parse(timeParam);
	    } else {
	        study_time = LocalDate.now(); // or any other default date you prefer
	    }

	    if (subject.isEmpty()) {
	        // Store error message in request attribute for subject
	        request.setAttribute("subject_error", "Subject cannot be blank.");
	        // Set hasError to true to indicate an error was detected
	        hasError = true;
	    } else {
	        // Check the length of the subject
	        if (subject.length() < 2 || subject.length() > 50) {
	            // Store error message in request attribute for subject
	            request.setAttribute("subject_error", "The subject must be between 2 and 50 characters.");
	            // Set hasError to true to indicate an error was detected
	            hasError = true;
	        }
	        if (subject.matches(".*\\d.*")) {
	            // Check if subject contains only numbers
	            request.setAttribute("subject_error", "Subject cannot be a number.");
	            hasError = true;
	        }
	    }

	    if (address.isEmpty()) {
	        // Store error message in request attribute for address
	        request.setAttribute("address_error", "Address cannot be blank.");
	        // Set hasError to true to indicate an error was detected
	        hasError = true;
	    } else {
	        // Check the length of the address
	        if (address.length() < 2 || address.length() > 50) {
	            // Store error message in request attribute for address
	            request.setAttribute("address_error", "The address must be between 2 and 50 characters.");
	            // Set hasError to true to indicate an error was detected
	            hasError = true;
	        }
	    }

	    if (session == null || session.isEmpty()) {
	        // Store error message in request attribute for session
	        request.setAttribute("session_error", "Please select a session.");
	        // Set hasError to true to indicate an error was detected
	        hasError = true;
	    }

	    if (status == null || status.isEmpty()) {
	        // Store error message in request attribute for status
	        request.setAttribute("status_error", "Please select a status.");
	        // Set hasError to true to indicate an error was detected
	        hasError = true;
	    }

	    int numberOfStudents = 0;
	    if (numberOfStudentsParam != null && !numberOfStudentsParam.isEmpty()) {
	        try {
	            numberOfStudents = Integer.parseInt(numberOfStudentsParam);
	        } catch (NumberFormatException e) {
	            // Store error message in request attribute for numberOfStudents
	            request.setAttribute("numberOfStudents_error", "Number of students must be a valid number.");
	            hasError = true;
	        }
	    }

	    // Get user ID from request
	    if (userIdParam == null || userIdParam.isEmpty()) {
	        // Store error message in request attribute for user ID
	        request.setAttribute("userId_error", "Please select a user.");
	        // Set hasError to true to indicate an error was detected
	        hasError = true;
	    }

	    if (hasError) {
	        List<User> listUser = userDao.selectAllUsers();
	        request.setAttribute("listUser", listUser);
	        request.setAttribute("class", currentClass);
	        RequestDispatcher dispatcher = request.getRequestDispatcher("Classes/classes_form.jsp");
	        dispatcher.forward(request, response);
	        return;
	    }

	    int userId = Integer.parseInt(userIdParam);
	    User user = userDao.selectUser(userId);
	    Classes updateClass = new Classes(id, class_name, eduClass, study_time, subject, address, session, status, numberOfStudents,requeststatus, user);

	    classesDao.updateClasses(updateClass);

	    response.sendRedirect("ClassesController?action=/listCl");
	}


	private void showEditForm(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, ServletException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		Classes existingcls = classesDao.selectClasses(id);
		List<User> listUser = userDao.selectAllUsers();
		request.setAttribute("Classes", existingcls);
		request.setAttribute("listUser", listUser);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("/Classes/classes_form.jsp");
		dispatcher.forward(request, response);
	}

	private void deleteClasses(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException {
		int id = Integer.parseInt(request.getParameter("id")); // Sử dụng long ở đây
		classesDao.deleteClasses(id);
		response.sendRedirect("ClassesController?action=/listCl");
	}

}
