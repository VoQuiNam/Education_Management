package com.joctopus.controller;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import org.apache.commons.validator.routines.EmailValidator;

import com.joctopus.dao.UserDao;
import com.joctopus.dao.UserDaoImpl;
import com.joctopus.model.User;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.PrintWriter;

@WebServlet("/UserController")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 5, maxFileSize = 1024 * 1024 * 50, maxRequestSize = 1024 * 1024
		* 200)
public class UserController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UserDao usersDAO;

	public void init() {
		this.usersDAO = new UserDaoImpl();
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
			case "/new":
				showNewForm(request, response);
				break;
			case "/insert":
				insertUser(request, response);
				break;
			case "/delete":
				deleteUser(request, response);
				break;
			case "/edit":
				showEditForm(request, response);
				break;
			case "/update":
				updateUser(request, response);
				break;
			case "/list":
				listUser(request, response);
				break;
			default:
				listUser(request, response);
				break;

			}
		} catch (SQLException ex) {
			throw new ServletException(ex);
		}
	}

	private void showNewForm(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher("User/user_form.jsp");
		dispatcher.forward(request, response);
	}

	private void listUser(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException, ServletException {
		List<User> listUser = usersDAO.selectAllUsers();
		request.setAttribute("listUser", listUser);
		RequestDispatcher dispatcher = request.getRequestDispatcher("User/user_list.jsp");
		dispatcher.forward(request, response);
	}

	private void updateUser(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException, ServletException {
		boolean hasError = false; // Biến để kiểm tra xem có lỗi nào được phát hiện không

		int id = Integer.parseInt(request.getParameter("id"));
		String first_name = request.getParameter("first_name");
		String last_name = request.getParameter("last_name");

		User currentUser = usersDAO.selectUser(id);

		// Kiểm tra điều kiện nếu first_name và last_name trống
		if (first_name.isEmpty() || last_name.isEmpty()) {
			// Kiểm tra first_name
			if (first_name.isEmpty()) {
				// Lưu thông điệp lỗi vào request attribute cho first_name
				request.setAttribute("first_name_error", "The first name cannot be blank.");
			}
			// Kiểm tra last_name
			if (last_name.isEmpty()) {
				// Lưu thông điệp lỗi vào request attribute cho last_name
				request.setAttribute("last_name_error", "The last name cannot be blank.");
			}
			// Đặt biến hasError thành true để biểu thị rằng có lỗi được phát hiện
			hasError = true;
		}

		String dobParam = request.getParameter("DOB");
		LocalDate DOB = null;
		if (dobParam != null && !dobParam.isEmpty()) {
			DOB = LocalDate.parse(dobParam);

			if (DOB.getYear() < 1950) {
				request.setAttribute("dob_error", "Date of birth cannot be earlier than 1950.");
				hasError = true;
			}
		} else {
			DOB = LocalDate.now(); // hoặc bất kỳ ngày mặc định nào khác phù hợp với bạn
		}
		String gender = request.getParameter("gender");
		String address = request.getParameter("address");
		String phone_number = request.getParameter("phone_number");
		String account = request.getParameter("account");
		String password = request.getParameter("password");
		String type = request.getParameter("type");
		String status = request.getParameter("status");

		// Kiểm tra xem tài khoản và số điện thoại mới có trùng với thông tin hiện tại
		// của người dùng không
		if (!account.equals(currentUser.getAccount()) && usersDAO.isAccountExists(account)) {
			// Lưu thông điệp lỗi vào request attribute cho tài khoản
			request.setAttribute("account_error", "This account already exists. Please choose another one.");
			// Đặt biến hasError thành true để biểu thị rằng có lỗi được phát hiện
			hasError = true;
		}

		if (!phone_number.equals(currentUser.getPhoneNumber()) && usersDAO.isPhoneNumberExists(phone_number)) {
			// Lưu thông điệp lỗi vào request attribute cho số điện thoại
			request.setAttribute("phone_number_error", "This phone number already exists. Please choose another one.");
			// Đặt biến hasError thành true để biểu thị rằng có lỗi được phát hiện
			hasError = true;
		}

		if (gender == null || gender.isEmpty()) {
			// Lưu thông điệp lỗi vào request attribute cho giới tính
			request.setAttribute("gender_error", "Please select a gender.");
			// Đặt biến hasError thành true để biểu thị rằng có lỗi được phát hiện
			hasError = true;
		}

		// Kiểm tra địa chỉ
		if (address.isEmpty()) {
			// Lưu thông điệp lỗi vào request attribute cho địa chỉ
			request.setAttribute("address_error", "The address cannot be blank.");
			// Đặt biến hasError thành true để biểu thị rằng có lỗi được phát hiện
			hasError = true;
		} else {
			// Kiểm tra chiều dài của địa chỉ
			if (address.length() < 5 || address.length() > 50) {
				// Lưu thông điệp lỗi vào request attribute cho địa chỉ
				request.setAttribute("address_error", "The address must be between 5 and 50 characters.");
				// Đặt biến hasError thành true để biểu thị rằng có lỗi được phát hiện
				hasError = true;
			}
		}

		// Kiểm tra số điện thoại
		if (phone_number.isEmpty()) {
			// Lưu thông điệp lỗi vào request attribute cho số điện thoại
			request.setAttribute("phone_number_error", "Phone number cannot be blank.");
			// Đặt biến hasError thành true để biểu thị rằng có lỗi được phát hiện
			hasError = true;
		} else {
			// Kiểm tra định dạng của số điện thoại
			if (!phone_number.matches("\\d{10,11}")) {
				// Lưu thông điệp lỗi vào request attribute cho số điện thoại
				request.setAttribute("phone_number_error", "Phone number must be between 10 and 11 digits.");
				// Đặt biến hasError thành true để biểu thị rằng có lỗi được phát hiện
				hasError = true;
			}
		}

		// Kiểm tra tài khoản
		if (account.isEmpty()) {
			// Lưu thông điệp lỗi vào request attribute cho tài khoản
			request.setAttribute("account_error", "Account cannot be blank.");
			// Đặt biến hasError thành true để biểu thị rằng có lỗi được phát hiện
			hasError = true;
		} else {
			// Kiểm tra định dạng của tài khoản
			if (!account.matches("\\b[\\w.%-]+@[-.\\w]+\\.[A-Za-z]{2,4}\\b")) {
				// Lưu thông điệp lỗi vào request attribute cho tài khoản
				request.setAttribute("account_error", "Invalid email format.");
				// Đặt biến hasError thành true để biểu thị rằng có lỗi được phát hiện
				hasError = true;
			}
		}

		// Kiểm tra mật khẩu
		if (password.isEmpty() || password.length() < 10 || password.length() > 30) {
			// Lưu thông điệp lỗi vào request attribute cho mật khẩu
			request.setAttribute("password_error", "Password must be between 10 and 30 characters.");
			// Đặt biến hasError thành true để biểu thị rằng có lỗi được phát hiện
			hasError = true;
		}

		// Kiểm tra loại người dùng
		if (type == null || type.isEmpty()) {
			// Lưu thông điệp lỗi vào request attribute cho loại người dùng
			request.setAttribute("type_error", "Please select a type.");
			// Đặt biến hasError thành true để biểu thị rằng có lỗi được phát hiện
			hasError = true;
		}

		// Đường dẫn để lưu ảnh
		String uploadPath = getServletContext().getRealPath("") + File.separator + "images";
		File uploadDir = new File(uploadPath);

		// Kiểm tra thư mục lưu ảnh, nếu chưa có thì tạo mới
		if (!uploadDir.exists()) {
			uploadDir.mkdir();
		}

		// Xử lý ảnh (nếu có)
		String dbFileName = null;
		Part filePart = request.getPart("image");
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

		// Lưu các giá trị đã nhập vào request
		request.setAttribute("id", id);
		request.setAttribute("first_name", first_name);
		request.setAttribute("last_name", last_name);
		request.setAttribute("dob", dobParam);
		request.setAttribute("gender", gender);
		request.setAttribute("address", address);
		request.setAttribute("phone_number", phone_number);
		request.setAttribute("account", account);
		request.setAttribute("password", password);
		request.setAttribute("type", type);
		request.setAttribute("image", filePart);
		request.setAttribute("status", filePart);


		// Nếu có lỗi, hiển thị lại form với thông báo lỗi
		if (hasError) {
			RequestDispatcher dispatcher = request.getRequestDispatcher("User/user_form.jsp");
			dispatcher.forward(request, response);
			return;
		}

		User updateUser = new User(id, first_name, last_name, DOB, gender, address, phone_number, account, password,
				type, dbFileName, status);

		usersDAO.updateUser(updateUser);

		response.sendRedirect("UserController?action=/list");
	}

	private void showEditForm(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, ServletException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		User existingAcc = usersDAO.selectUser(id);
		RequestDispatcher dispatcher = request.getRequestDispatcher("User/user_form.jsp");
		request.setAttribute("User", existingAcc);
		dispatcher.forward(request, response);
	}

	private void insertUser(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException, ServletException {

		String first_name = request.getParameter("first_name");
		String last_name = request.getParameter("last_name");
		String dobParam = request.getParameter("DOB");
		LocalDate DOB;

		boolean hasError = false; // Biến để kiểm tra xem có lỗi nào được phát hiện không

		// Kiểm tra điều kiện nếu first_name và last_name trống
		if (first_name.isEmpty() || last_name.isEmpty()) {
			// Kiểm tra first_name
			if (first_name.isEmpty()) {
				// Lưu thông điệp lỗi vào request attribute cho first_name
				request.setAttribute("first_name_error", "The first name cannot be blank.");
			}
			// Kiểm tra last_name
			if (last_name.isEmpty()) {
				// Lưu thông điệp lỗi vào request attribute cho last_name
				request.setAttribute("last_name_error", "The last name cannot be blank.");
			}
			// Đặt biến hasError thành true để biểu thị rằng có lỗi được phát hiện
			hasError = true;
		}

		// Kiểm tra điều kiện nếu DOB không được chọn
		if (dobParam.isEmpty()) {
			// Lưu thông điệp lỗi vào request attribute cho DOB
			request.setAttribute("dob_error", "Please select a date of birth.");
			// Đặt biến hasError thành true để biểu thị rằng có lỗi được phát hiện
			hasError = true;
		} else {
			// Kiểm tra nếu chuỗi DOB không rỗng và có định dạng hợp lệ
			if (!dobParam.matches("\\d{4}-\\d{2}-\\d{2}")) {
				// Xử lý lỗi khi chuỗi DOB không hợp lệ
				request.setAttribute("dob_error", "Please enter a valid date of birth.");
				// Đặt biến hasError thành true để biểu thị rằng có lỗi được phát hiện
				hasError = true;
			} else {
				// Chuyển đổi chuỗi DOB thành LocalDate
				DOB = LocalDate.parse(dobParam);

				// Kiểm tra điều kiện nếu năm không hợp lệ
				if (DOB.getYear() < 1900 || DOB.getYear() > LocalDate.now().getYear()) {
					// Lưu thông điệp lỗi vào request attribute cho DOB
					request.setAttribute("dob_error", "Please enter a valid year.");
					// Đặt biến hasError thành true để biểu thị rằng có lỗi được phát hiện
					hasError = true;
				}
			}
		}

		// Kiểm tra điều kiện nếu giá trị giới tính không được chọn
		String gender = request.getParameter("gender");
		if (gender == null || gender.isEmpty()) {
			// Lưu thông điệp lỗi vào request attribute cho giới tính
			request.setAttribute("gender_error", "Please select a gender.");
			// Đặt biến hasError thành true để biểu thị rằng có lỗi được phát hiện
			hasError = true;
		}

		// Kiểm tra địa chỉ
		String address = request.getParameter("address");
		if (address.isEmpty()) {
			// Lưu thông điệp lỗi vào request attribute cho địa chỉ
			request.setAttribute("address_error", "The address cannot be blank.");
			// Đặt biến hasError thành true để biểu thị rằng có lỗi được phát hiện
			hasError = true;
		} else {
			// Kiểm tra chiều dài của địa chỉ
			if (address.length() < 5 || address.length() > 50) {
				// Lưu thông điệp lỗi vào request attribute cho địa chỉ
				request.setAttribute("address_error", "The address must be between 5 and 50 characters.");
				// Đặt biến hasError thành true để biểu thị rằng có lỗi được phát hiện
				hasError = true;
			}
		}

		// Kiểm tra số điện thoại
		String phone_number = request.getParameter("phone_number");
		if (phone_number.isEmpty()) {
			// Lưu thông điệp lỗi vào request attribute cho số điện thoại
			request.setAttribute("phone_number_error", "Phone number cannot be blank.");
			// Đặt biến hasError thành true để biểu thị rằng có lỗi được phát hiện
			hasError = true;
		} else {
			// Kiểm tra định dạng của số điện thoại
			if (!phone_number.matches("\\d{10,11}")) {
				// Lưu thông điệp lỗi vào request attribute cho số điện thoại
				request.setAttribute("phone_number_error", "Phone number must be between 10 and 11 digits.");
				// Đặt biến hasError thành true để biểu thị rằng có lỗi được phát hiện
				hasError = true;
			}
		}

		// Kiểm tra tài khoản
		String account = request.getParameter("account");
		if (account.isEmpty()) {
			// Lưu thông điệp lỗi vào request attribute cho tài khoản
			request.setAttribute("account_error", "Account cannot be blank.");
			// Đặt biến hasError thành true để biểu thị rằng có lỗi được phát hiện
			hasError = true;
		} else {
			// Kiểm tra định dạng của tài khoản
			if (!account.matches("\\b[\\w.%-]+@[-.\\w]+\\.[A-Za-z]{2,4}\\b")) {
				// Lưu thông điệp lỗi vào request attribute cho tài khoản
				request.setAttribute("account_error", "Invalid email format.");
				// Đặt biến hasError thành true để biểu thị rằng có lỗi được phát hiện
				hasError = true;
			}
		}

		// Kiểm tra mật khẩu
		String password = request.getParameter("password");
		if (password.isEmpty() || password.length() < 10 || password.length() > 30) {
			// Lưu thông điệp lỗi vào request attribute cho mật khẩu
			request.setAttribute("password_error", "Password must be between 10 and 30 characters.");
			// Đặt biến hasError thành true để biểu thị rằng có lỗi được phát hiện
			hasError = true;
		}

		// Kiểm tra loại người dùng
		String type = request.getParameter("type");
		if (type == null || type.isEmpty()) {
			// Lưu thông điệp lỗi vào request attribute cho loại người dùng
			request.setAttribute("type_error", "Please select a type.");
			// Đặt biến hasError thành true để biểu thị rằng có lỗi được phát hiện
			hasError = true;
		}

		// Kiểm tra xem tài khoản đã tồn tại trong cơ sở dữ liệu chưa
		if (usersDAO.isAccountExists(account)) {
			// Lưu thông điệp lỗi vào request attribute cho tài khoản
			request.setAttribute("account_error", "This account already exists. Please choose another one.");
			// Đặt biến hasError thành true để biểu thị rằng có lỗi được phát hiện
			hasError = true;
		}

		if (usersDAO.isPhoneNumberExists(phone_number)) {
			// Lưu thông điệp lỗi vào request attribute cho tài khoản
			request.setAttribute("phone_number_error", "This phone number already exists. Please choose another one.");
			// Đặt biến hasError thành true để biểu thị rằng có lỗi được phát hiện
			hasError = true;
		}
		String status = request.getParameter("status");
		// Handle file upload
		String uploadPath = getServletContext().getRealPath("") + File.separator + "images";
		File uploadDir = new File(uploadPath);
		if (!uploadDir.exists()) {
			uploadDir.mkdir();
		}

		Part filePart = request.getPart("image");
		String fileName = extractFileName(filePart);

		// Nếu người dùng không tải ảnh lên, sử dụng ảnh mặc định
		String dbFileName;
		if (fileName == null || fileName.isEmpty()) {
		    dbFileName = "images/default.jpg"; // Đường dẫn ảnh mặc định
		} else {
		    String filePath = uploadPath + File.separator + fileName;
		    filePart.write(filePath); // Lưu file vào thư mục chỉ định
		    dbFileName = "images/" + fileName;
		}

		// Nếu có lỗi, hiển thị lại form với thông báo lỗi
		if (hasError) {
			RequestDispatcher dispatcher = request.getRequestDispatcher("User/user_form.jsp");
			dispatcher.forward(request, response);
			return;
		}

		// Nếu không có lỗi, tiến hành chèn người dùng vào cơ sở dữ liệu và chuyển hướng
		// đến trang danh sách
		LocalDate dob = LocalDate.parse(dobParam);
		User newUser = new User(first_name, last_name, dob, gender, address, phone_number, account, password, type,
				dbFileName, status);
		usersDAO.insertUser(newUser);
		response.sendRedirect("UserController?action=/list");
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

	private void deleteUser(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));

		// Kiểm tra nếu người dùng đang tạo lớp
		boolean hasClasses = usersDAO.checkIfUserHasClasses(id);

		// Kiểm tra nếu người dùng đang học trong lớp
		boolean isEnrolledInClass = usersDAO.checkIfUserIsEnrolledInClass(id);

		if (hasClasses) {
			// Nếu người dùng đang tạo lớp
			request.getSession().setAttribute("errorMessage", "User has created classes, cannot be deleted.");
		} else if (isEnrolledInClass) {
			// Nếu người dùng đang học trong lớp
			request.getSession().setAttribute("errorMessage", "User is enrolled in a class, cannot be deleted.");
		}

		if (hasClasses || isEnrolledInClass) {
			// Redirect to the list page with the error message
			response.sendRedirect("UserController?action=/list");
		} else {
			// Nếu không, tiến hành xóa người dùng
			usersDAO.deleteUser(id);
			response.sendRedirect("UserController?action=/list");
		}
	}

}
