package com.joctopus.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import com.joctopus.dao.ClassesDao;
import com.joctopus.dao.ClassesDaoImpl;
import com.joctopus.dao.ClassesUserDao;
import com.joctopus.dao.ClassesUserDaoImpl;
import com.joctopus.dao.UserDao;
import com.joctopus.dao.UserDaoImpl;
import com.joctopus.model.Classes;
import com.joctopus.model.Ucl;
import com.joctopus.model.User;

@WebServlet("/ClassesUserController")
public class ClassesUserController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ClassesUserDao classesuserDao;
	private UserDao userDao;
	private ClassesDao classesDao;

	public void init() {

		this.classesDao = new ClassesDaoImpl();
		this.userDao = new UserDaoImpl();
		this.classesuserDao = new ClassesUserDaoImpl();
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
			case "/newUCL":
				showNewForm(request, response);
				break;
			case "/insertUCL":
				insertClassUser(request, response);
				break;
			case "/deleteUCL":
				deleteUcl(request, response);
				break;
			case "/listUCL":
				listUcl(request, response);
				break;
			default:
				listUcl(request, response);
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
		RequestDispatcher dispatcher = request.getRequestDispatcher("ClassesUser/classesuser_form.jsp");
		dispatcher.forward(request, response);
	}

	private void listUcl(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException, ServletException {
		List<Ucl> listUcl = classesuserDao.selectAllUcl();
		request.setAttribute("listUcl", listUcl);
		RequestDispatcher dispatcher = request.getRequestDispatcher("ClassesUser/classesuser_list.jsp");
		dispatcher.forward(request, response);
	}

	private void insertClassUser(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException, ServletException {
		boolean hasError = false; // Biến để kiểm tra xem có lỗi nào được phát hiện không

		// Lấy ID người dùng từ request
		String userIdParam = request.getParameter("id_user");

		if (userIdParam == null || userIdParam.isEmpty()) {
			request.setAttribute("userId_error", "Please select a user.");
			hasError = true;
		}

		// Reload users to be displayed in the form
		List<User> users = userDao.selectAllUsers();
		request.setAttribute("listUser", users);

		String classIdParam = request.getParameter("class_id");
		if (classIdParam == null || classIdParam.isEmpty()) {
			request.setAttribute("classId_error", "Please select a class.");
			hasError = true;
		}
		
		List<Classes> classes = classesDao.selectAllClasses();
		request.setAttribute("listClass", classes);
		

		if (hasError) {
			RequestDispatcher dispatcher = request.getRequestDispatcher("ClassesUser/classesuser_form.jsp");
			dispatcher.forward(request, response);
			return;
		}

		int userId = Integer.parseInt(userIdParam);
		User user = userDao.selectUser(userId);
		
		int classId = Integer.parseInt(classIdParam);
		Classes classess = classesDao.selectClasses(classId);

		Ucl newUserClasses = new Ucl(user, classess);
		classesuserDao.insertClassUser(newUserClasses);
		response.sendRedirect("ClassesUserController?action=/listUCL");
	}

//	private void deleteUcl(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
//		int id_user = Integer.parseInt(request.getParameter("id_user")); // Sử dụng long ở đây
//		userDao.deleteUser(id_user);
//		int class_id = Integer.parseInt(request.getParameter("class_id")); // Sử dụng long ở đây
//		classesDao.deleteClasses(class_id);
//		response.sendRedirect("ClassesUserController?action=/listUCL");
//	}

	private void deleteUcl(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
		int idUser = Integer.parseInt(request.getParameter("id_user"));
		int classId = Integer.parseInt(request.getParameter("class_id"));

		// Assuming your DAO is properly set up
		classesuserDao.deleteUcl(idUser, classId);

		response.sendRedirect("ClassesUserController?action=/listUCL");
	}

}
