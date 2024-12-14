package com.joctopus.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import com.joctopus.dao.BannerDao;
import com.joctopus.dao.BannerDaoImpl;
import com.joctopus.dao.ClassesDao;
import com.joctopus.dao.ClassesDaoImpl;
import com.joctopus.dao.NotificationDaoImpl;
import com.joctopus.dao.UserDaoImpl;
import com.joctopus.model.Banner;
import com.joctopus.model.Classes;
import com.joctopus.model.User;

@WebServlet("/BannerController")

@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 5, maxFileSize = 1024 * 1024 * 50, maxRequestSize = 1024 * 1024
		* 200)
public class BannerController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private BannerDao bannerDao;

	public void init() {
		this.bannerDao = new BannerDaoImpl();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getParameter("action");

		try {
			switch (action) {
			case "/newBanner":
				showNewForm(request, response);
				break;
			case "/editBanner":
				showEditForm(request, response);
				break;
			case "/insert":
				insertBanners(request, response);
				break;
			case "/update":
				updateUser(request, response);
				break;
			case "/deleteBanner":
				deleteBanner(request, response);
				break;
			case "/listBanner":
				listBanners(request, response);
				break;
			default:
				listBanners(request, response);
				break;
			}
		} catch (SQLException ex) {
			throw new ServletException(ex);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	private void showNewForm(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		List<Banner> listBanner = bannerDao.selectAllBanners();
		request.setAttribute("listBanner", listBanner);

		RequestDispatcher dispatcher = request.getRequestDispatcher("Banner/Banner_form.jsp");
		dispatcher.forward(request, response);
	}

	private void listBanners(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException, ServletException {
		List<Banner> listBanner = bannerDao.selectAllBanners();
		System.out.println("Banners retrieved: " + listBanner.size()); // chuyển dữ liệu từ serlvet qua jsp để hiển thị
																		// dữ liệu cho người dùng

		/*
		 * // Sắp xếp danh sách listBanner.sort((b1, b2) -> { // Nếu cả hai đều có
		 * isActive giống nhau, sắp xếp theo orderIndex if (b1.isIsActive() ==
		 * b2.isIsActive()) { return Integer.compare(b1.getOrderIndex(),
		 * b2.getOrderIndex()); } // Ưu tiên isActive == true lên trước return
		 * b1.isIsActive() ? -1 : 1; });
		 */
		request.setAttribute("listBanner", listBanner);
		RequestDispatcher dispatcher = request.getRequestDispatcher("Banner/BannerList.jsp");
		dispatcher.forward(request, response);
	}

	private void insertBanners(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException, ServletException {
		String Title = request.getParameter("Title");
		String Description = request.getParameter("Description");
		boolean IsActive = Boolean.parseBoolean(request.getParameter("IsActive"));
		String Position = request.getParameter("Position");


		// Handle file upload
		String uploadPath = getServletContext().getRealPath("") + File.separator + "images";
		File uploadDir = new File(uploadPath);
		if (!uploadDir.exists()) {
			uploadDir.mkdir();
		}

		Part filePart = request.getPart("ImageUrl");
		String fileName = extractFileName(filePart);
		String filePath = uploadPath + File.separator + fileName;

		filePart.write(filePath); // Save file to the specified directory
		String dbFileName = "images/" + fileName;


		// Save to the database using Hibernate or your DAO
		Banner newBanner = new Banner(Title, Description, dbFileName, IsActive, Position);
		bannerDao.insertBanners(newBanner);

		response.sendRedirect("BannerController?action=/listBanner");
	}

	private String extractFileName(Part part) { // chứa thông tin dữ liệu về cách xử lí
		String contentDisp = part.getHeader("content-disposition");
		for (String content : contentDisp.split(";")) {
			// kiem tra xem chuỗi có bắt đầu tữ khóa file name ko
			if (content.trim().startsWith("filename")) {
				return content.substring(content.indexOf("=") + 2, content.length() - 1);
			}
		}
		return "";
	}

	private void deleteBanner(HttpServletRequest request, HttpServletResponse response)
	        throws SQLException, IOException {
	    int id = Integer.parseInt(request.getParameter("BannerID"));

	    // Step 1: Fetch the banner to get its OrderIndex before deleting it
	    Banner deletedBanner = bannerDao.selectBanners(id);

	    // Step 2: Delete the banner
	    bannerDao.deleteBanner(id);

	    // Step 4: Redirect to the list of banners
	    response.sendRedirect("BannerController?action=/listBanner");
	}

	
	private void updateUser(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException, ServletException {

		// Biến kiểm tra lỗi
		boolean hasError = false;

		// Lấy các tham số từ form
		int id = Integer.parseInt(request.getParameter("BannerID"));
		String Title = request.getParameter("Title");
		String Description = request.getParameter("Description");


		// Xử lý các tham số khác
		Boolean IsActive = Boolean.parseBoolean(request.getParameter("IsActive"));
		String Position = request.getParameter("Position");
		// Đường dẫn để lưu ảnh
		String uploadPath = getServletContext().getRealPath("") + File.separator + "images";
		File uploadDir = new File(uploadPath);

		// Kiểm tra thư mục lưu ảnh, nếu chưa có thì tạo mới
		if (!uploadDir.exists()) {
			uploadDir.mkdir();
		}

		// Xử lý ảnh (nếu có)
		String dbFileName = null;
		Part filePart = request.getPart("ImageUrl");
		if (filePart != null && filePart.getSize() > 0) {
			// Nếu có ảnh mới, lưu ảnh mới và lấy tên file
			String fileName = extractFileName(filePart);
			String filePath = uploadPath + File.separator + fileName;
			filePart.write(filePath); // Lưu file vào thư mục
			dbFileName = "images/" + fileName;
		} else {
			// Nếu không có ảnh mới, sử dụng ảnh cũ
			dbFileName = request.getParameter("currentImage"); // Ảnh cũ từ input hidden
		}


		// Kiểm tra xem đường dẫn ảnh có hợp lệ không
		if (dbFileName == null || dbFileName.isEmpty()) {
			throw new ServletException(
					"ImageUrl cannot be null. Please upload an image or provide a valid current image.");
		}

		// Nếu có lỗi, chuyển hướng về form và thông báo lỗi
		if (hasError) {
			RequestDispatcher dispatcher = request.getRequestDispatcher("Banner/Banner_form.jsp");
			dispatcher.forward(request, response);
			return; // Dừng xử lý nếu phát hiện lỗi
		}

		// Tạo đối tượng Banner từ thông tin đã nhận
		Banner updateBanner = new Banner(id, Title, Description, dbFileName, IsActive, Position);

		// Cập nhật banner thông qua DAO
		bannerDao.updateBanner(updateBanner);

		// Chuyển hướng về danh sách banner sau khi cập nhật thành công
		response.sendRedirect("BannerController?action=/listBanner");
	}

	private void showEditForm(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, ServletException, IOException {
		int id = Integer.parseInt(request.getParameter("BannerID"));
		Banner existingAcc = bannerDao.selectBanners(id);
		RequestDispatcher dispatcher = request.getRequestDispatcher("Banner/Banner_form.jsp");
		request.setAttribute("Banner", existingAcc);
		dispatcher.forward(request, response);
	}

}
