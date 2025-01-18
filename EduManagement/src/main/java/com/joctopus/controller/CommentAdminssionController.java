package com.joctopus.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import org.json.JSONObject;

import com.google.gson.JsonObject;
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
		 // Check if the user is logged in
        HttpSession session = request.getSession(false); // Get the session, don't create a new one
        if (session == null || session.getAttribute("loggedInUser") == null) {
            // Redirect to the login page if the user is not logged in
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
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
			case "/deleteComment": // Thêm xử lý cho getClassDetails
				deleteComment(request, response);
				break;
			case "/updateComment":
		        updateComment(request, response);
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

	private void postComments(HttpServletRequest request, HttpServletResponse response) throws IOException {
	    response.setContentType("application/json");
	    response.setCharacterEncoding("UTF-8");

	    HttpSession session = request.getSession();
	    User user = (User) session.getAttribute("loggedInUser");

	    if (user == null) {
	    	//Trong trường hợp này, các dấu gạch chéo ngược trước dấu ngoặc kép (\") được sử dụng để thoát dấu ngoặc kép bên trong chuỗi. 
	    	//Điều này là cần thiết vì dấu ngoặc kép được sử dụng để xác định ranh giới của chuỗi trong Java.
	        response.getWriter().write("{\"success\": false, \"error\": \"User not logged in.\"}");
	        return;
	    }

	    String content = request.getParameter("content");
	    if (content == null || content.trim().isEmpty()) {
	        response.getWriter().write("{\"success\": false, \"error\": \"Comment content cannot be empty.\"}");
	        return;
	    }

	    LocalDate created_at = LocalDate.now();
	    String classIdParam = request.getParameter("class_id");
	    int classId;
	    try {
	        classId = Integer.parseInt(classIdParam);
	    } catch (NumberFormatException e) {
	        response.getWriter().write("{\"success\": false, \"error\": \"Invalid class ID.\"}");
	        return;
	    }

	    Classes classes = classesDao.selectClasses(classId);
	    if (classes == null) {
	        response.getWriter().write("{\"success\": false, \"error\": \"Class not found.\"}");
	        return;
	    }

	    String parentCommentIdParam = request.getParameter("parent_comment_id");
	    Comments parentComment = null;
	    if (parentCommentIdParam != null && !parentCommentIdParam.isEmpty()) {
	        try {
	            int parentCommentId = Integer.parseInt(parentCommentIdParam);
	            parentComment = commentAdminssionDao.selectComments(parentCommentId);
	            if (parentComment == null) {
	                response.getWriter().write("{\"success\": false, \"error\": \"Parent comment not found.\"}");
	                return;
	            }
	        } catch (NumberFormatException e) {
	            response.getWriter().write("{\"success\": false, \"error\": \"Invalid parent comment ID.\"}");
	            return;
	        }
	    }

	    Comments newComment = new Comments(user, content, created_at, classes);
	    if (parentComment != null) {
	        newComment.setParentComment(parentComment);
	    }

	    try {
	        commentAdminssionDao.postComments(newComment);
	        System.out.println("Comment posted successfully.");
	        // Send response with success and class_id
	        String redirectUrl = "http://localhost:8080/EduManagement/CommentAdminssionController?action=/getClassDetails&class_id=" + classId;
	        response.setContentType("application/json");
	        System.out.println("Redirecting to: " + redirectUrl);
	        response.getWriter().write("{\"success\": true, \"class_id\": " + classId + ", \"redirectUrl\": \"" + redirectUrl + "\"}");
	    } catch (Exception e) {
	    	e.printStackTrace();  // In ra lỗi chi tiết để kiểm tra.
	        response.getWriter().write("{\"success\": false, \"error\": \"Failed to post comment.\"}");
	    }
	}



	private void deleteComment(HttpServletRequest request, HttpServletResponse response)
	        throws SQLException, IOException {
	    HttpSession session = request.getSession();

	    // Fetch and validate comment ID
	    String idParam = request.getParameter("id");
	    if (idParam == null || idParam.isEmpty()) {
	        session.setAttribute("errorMessage", "Missing or invalid comment ID.");
	        response.sendRedirect("CommentAdminssionController?action=/getClassDetails");
	        return;
	    }
	    int id = Integer.parseInt(idParam);

	    // Step 1: Fetch the comment to get its details before deleting it
	    Comments deletedComments = commentAdminssionDao.selectComments(id);

	    // Step 2: Delete the comment
	    commentAdminssionDao.deleteComments(id);

	    // Fetch and validate class ID
	    String classIdParam = request.getParameter("class_id");
	    if (classIdParam == null || classIdParam.isEmpty()) {
	        session.setAttribute("errorMessage", "Missing or invalid class ID.");
	        response.sendRedirect("CommentAdminssionController?action=/getClassDetails");
	        return;
	    }

	    int classId;
	    try {
	        classId = Integer.parseInt(classIdParam);
	    } catch (NumberFormatException e) {
	        session.setAttribute("errorMessage", "Invalid class ID format.");
	        response.sendRedirect("CommentAdminssionController?action=/getClassDetails");
	        return;
	    }

	    // Fetch class details
	    Classes classes = classesDao.selectClasses(classId);
	    if (classes == null) {
	        session.setAttribute("errorMessage", "Class not found for ID: " + classId);
	        response.sendRedirect("CommentAdminssionController?action=/getClassDetails");
	        return;
	    }

	    // Set class information
	    request.setAttribute("Classes", classes);

	    // Step 4: Set success message and redirect
	    session.setAttribute("successMessage", "Comment deleted successfully!");
	    response.sendRedirect("CommentAdminssionController?action=/getClassDetails&class_id=" + classId);
	}
	
	private void updateComment(HttpServletRequest request, HttpServletResponse response)
	        throws IOException {
		//Đặt loại nội dung của phản hồi HTTP là JSON
	    response.setContentType("application/json");
	    //Đặt mã hóa ký tự là UTF-8 để đảm bảo hỗ trợ các ký tự đặc biệt.
	    response.setCharacterEncoding("UTF-8");

	    try {
	        BufferedReader reader = request.getReader();
	        // Dùng để ghép các dòng dữ liệu từ request body.
	        StringBuilder sb = new StringBuilder();
	        String line;
	        while ((line = reader.readLine()) != null) {
	            sb.append(line);
	        }

	        JSONObject jsonObject = new JSONObject(sb.toString());
	        int id = jsonObject.getInt("id");
	        String newContent = jsonObject.getString("content");

	        Comments comment = commentAdminssionDao.selectComments(id);
	        if (comment == null) {
	            response.getWriter().write("{\"success\": false, \"error\": \"Comment not found.\"}");
	            return;
	        }

	        comment.setContent(newContent);
	        commentAdminssionDao.updateComments(comment);

	        response.getWriter().write("{\"success\": true}");
	    } catch (Exception e) {
	        response.getWriter().write("{\"success\": false, \"error\": \"" + e.getMessage() + "\"}");
	    }
	}

	

}
