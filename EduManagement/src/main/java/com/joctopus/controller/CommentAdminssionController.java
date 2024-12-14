package com.joctopus.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import com.joctopus.dao.BannerDao;
import com.joctopus.dao.BannerDaoImpl;
import com.joctopus.dao.ClassesDao;
import com.joctopus.dao.ClassesDaoImpl;
import com.joctopus.dao.CommentAdminssionDao;
import com.joctopus.dao.CommentAdminssionDaoImpl;
import com.joctopus.dao.CommentsDao;
import com.joctopus.dao.CommentsDaoImpl;
import com.joctopus.dao.UserDao;
import com.joctopus.dao.UserDaoImpl;
import com.joctopus.model.Banner;
import com.joctopus.model.Classes;
import com.joctopus.model.Comments;
import com.joctopus.model.User;

@WebServlet("/CommentAdminssionController")
public class CommentAdminssionController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private CommentAdminssionDao commentAdminssionDao;
	private ClassesDao classesDao;
	private UserDao userDao;

	public void init() {
		this.classesDao = new ClassesDaoImpl();
		this.userDao = new UserDaoImpl();
		this.commentAdminssionDao = new CommentAdminssionDaoImpl();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getParameter("action");
		System.out.println("Action received: " + action); // Kiểm tra giá trị action
		try {
			switch (action) {
			case "/listComment":
				listComment(request, response);
				break;
			case "/getClassDetails": // Thêm xử lý cho getClassDetails
				getClassDetails(request, response);
				break;
			case "/postComment": // Thêm xử lý cho getClassDetails
				postComments(request, response);
				break;
			default:
				listComment(request, response);
				break;
			}
		} catch (SQLException ex) {
			throw new ServletException(ex);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

	// Thêm phương thức getClassDetails
	private void getClassDetails(HttpServletRequest request, HttpServletResponse response)
	        throws SQLException, IOException, ServletException {
	    String classIdParam = request.getParameter("class_id");
	    
	    if (classIdParam == null || classIdParam.isEmpty()) {
	        response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing or invalid class ID");
	        return;
	    }

	    int classId;
	    try {
	        classId = Integer.parseInt(classIdParam);
	    } catch (NumberFormatException e) {
	        response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid class ID format");
	        return;
	    }

	    Classes classDetails = commentAdminssionDao.getClassDetails(classId);
	    if (classDetails == null) {
	        response.sendError(HttpServletResponse.SC_NOT_FOUND, "Class not found");
	        return;
	    }

	    List<Comments> listComment = commentAdminssionDao.selectCommentsByClassId(classId);
	    request.setAttribute("classDetails", classDetails);
	    request.setAttribute("listComment", listComment);

	    RequestDispatcher dispatcher = request.getRequestDispatcher("AdminssionClass/comment_adminssion.jsp");
	    dispatcher.forward(request, response);
	}



	private void listComment(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException, ServletException {
		List<Comments> listComment = commentAdminssionDao.selectAllComments();

		request.setAttribute("listComment", listComment);

		RequestDispatcher dispatcher = request.getRequestDispatcher("AdminssionClass/comment_adminssion.jsp");
		dispatcher.forward(request, response);
	}
	
	private void postComments(HttpServletRequest request, HttpServletResponse response)
	        throws SQLException, IOException, ServletException {
	    // Lấy thông tin User từ session
	    HttpSession session = request.getSession();
	    User user = (User) session.getAttribute("loggedInUser");

	    if (user == null) {
	        // Nếu người dùng chưa đăng nhập, chuyển hướng đến trang login
	        response.sendRedirect("Login/login.jsp");
	        return;
	    }

	    // Lấy nội dung bình luận từ form
	    String content = request.getParameter("content");

	    // Tự động lấy ngày hiện tại
	    LocalDate created_at = LocalDate.now();

	    // Lấy thông tin class
	    String classIdParam = request.getParameter("class_id");
	    System.out.println("class_id parameter: " + classIdParam);
	    int classId;
	    try {
	        classId = Integer.parseInt(classIdParam);
	    } catch (NumberFormatException e) {
	    	response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid class ID: " + classIdParam);
	        return;
	    }
	    
		
	    Classes classes = classesDao.selectClasses(classId);
	    if (classes == null) {
	        response.sendError(HttpServletResponse.SC_NOT_FOUND, "Class not found for ID: " + classId);
	        return;
	    }
	    request.setAttribute("Classes", classes);
	    // Tạo đối tượng Comments
	    Comments newComment = new Comments(user, content, created_at, classes);

	    // Lưu vào database bằng Hibernate hoặc DAO
	    commentAdminssionDao.postComments(newComment);

	    // Chuyển hướng sau khi thêm thành công
	    response.sendRedirect("CommentAdminssionController?action=/getClassDetails&class_id="  + classId);
	}
}
