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

import com.joctopus.dao.BannerDao;
import com.joctopus.dao.BannerDaoImpl;
import com.joctopus.dao.CommentsDao;
import com.joctopus.dao.CommentsDaoImpl;
import com.joctopus.model.Banner;
import com.joctopus.model.Comments;

@WebServlet("/CommentsController")
public class CommentsController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private CommentsDao commentsDao;

	public void init() {
		this.commentsDao = new CommentsDaoImpl();
	}   
    


	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");

		try {
			switch (action) {
			case "/listComments":
				listComments(request, response);
				break;
			default:
				listComments(request, response);
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
	
	private void listComments(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException, ServletException {
		List<Comments> listComments = commentsDao.selectAllComments();
	
		request.setAttribute("listComments", listComments);
		RequestDispatcher dispatcher = request.getRequestDispatcher("Comments/comments_list.jsp");
		dispatcher.forward(request, response);
	}
	 

}
