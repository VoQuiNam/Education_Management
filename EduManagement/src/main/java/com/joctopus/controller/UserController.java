package com.joctopus.controller;

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
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

@WebServlet("/UserController")
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
		} else {
			DOB = LocalDate.now(); // hoặc bất kỳ ngày mặc định nào khác phù hợp với bạn
		}
		String gender = request.getParameter("gender");
		String address = request.getParameter("address");
		String phone_number = request.getParameter("phone_number");
		String account = request.getParameter("account");
		String password = request.getParameter("password");
		String type = request.getParameter("type");

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

		// Nếu có lỗi, hiển thị lại form với thông báo lỗi
		if (hasError) {
			RequestDispatcher dispatcher = request.getRequestDispatcher("User/user_form.jsp");
			dispatcher.forward(request, response);
			return;
		}

		User updateUser = new User(id, first_name, last_name, DOB, gender, address, phone_number, account, password,
				type);

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

		// Nếu có lỗi, hiển thị lại form với thông báo lỗi
		if (hasError) {
			RequestDispatcher dispatcher = request.getRequestDispatcher("User/user_form.jsp");
			dispatcher.forward(request, response);
			return;
		}

		// Nếu không có lỗi, tiến hành chèn người dùng vào cơ sở dữ liệu và chuyển hướng
		// đến trang danh sách
		LocalDate dob = LocalDate.parse(dobParam);
		User newUser = new User(first_name, last_name, dob, gender, address, phone_number, account, password, type);
		usersDAO.insertUser(newUser);
		response.sendRedirect("UserController?action=/list");
	}

//	private void insertUser(HttpServletRequest request, HttpServletResponse response)
//			throws SQLException, IOException, ServletException {
//
//		String first_name = request.getParameter("first_name");
//		String last_name = request.getParameter("last_name");
////		LocalDate DOB = LocalDate.parse(request.getParameter("DOB"));
//		String dobParam = request.getParameter("DOB");
//		LocalDate DOB;
//
//		// Kiểm tra nếu chuỗi DOB không rỗng và có định dạng hợp lệ
//		if (!dobParam.isEmpty() && dobParam.matches("\\d{4}-\\d{2}-\\d{2}")) {
//			// Chuyển đổi chuỗi DOB thành LocalDate
//			DOB = LocalDate.parse(dobParam);
//		} else {
//			// Xử lý lỗi khi chuỗi DOB không hợp lệ
//			request.setAttribute("dob_error", "Please enter a valid date of birth.");
//			RequestDispatcher dispatcher = request.getRequestDispatcher("User/user_form.jsp");
//			dispatcher.forward(request, response);
//			return;
//		}
//
//		// Kiểm tra điều kiện nếu năm không hợp lệ
//		if (DOB.getYear() < 1900 || DOB.getYear() > LocalDate.now().getYear()) {
//			// Lưu thông điệp lỗi vào request attribute cho DOB
//			request.setAttribute("dob_error", "Please enter a valid year.");
//			// Forward lại request đến trang form để hiển thị lại form với thông báo lỗi
//			RequestDispatcher dispatcher = request.getRequestDispatcher("User/user_form.jsp");
//			dispatcher.forward(request, response);
//			return; // Dừng phương thức
//		}
//
//		String gender = request.getParameter("gender");
//		String address = request.getParameter("address");
//		String phone_number = request.getParameter("phone_number");
//		String account = request.getParameter("account");
//		String password = request.getParameter("password");
//		String type = request.getParameter("type");
//
//		// Kiểm tra điều kiện nếu first_name ít hơn 3 ký tự
//		if (first_name.isEmpty() || last_name.isEmpty()) {
//			// Kiểm tra first_name
//			if (first_name.isEmpty()) {
//				// Lưu thông điệp lỗi vào request attribute cho first_name
//				request.setAttribute("first_name_error", "The first name cannot be blank.");
//			}
//			// Kiểm tra last_name
//			if (last_name.isEmpty()) {
//				// Lưu thông điệp lỗi vào request attribute cho last_name
//				request.setAttribute("last_name_error", "The last name cannot be blank.");
//			}
//			// Forward lại request đến trang form để hiển thị lại form với thông báo lỗi
//			RequestDispatcher dispatcher = request.getRequestDispatcher("User/user_form.jsp");
//			dispatcher.forward(request, response);
//			return; // Dừng phương thức
//		}
//
//		// Kiểm tra điều kiện nếu first_name ít hơn 3 ký tự
//		if (first_name.length() < 3 || last_name.length() < 3) {
//			if (first_name.length() < 3) {
//				// Lưu thông điệp lỗi vào request attribute
//				request.setAttribute("first_name_error", "The first name must contain at least 3 characters.");
//			}
//
//			// Kiểm tra điều kiện nếu first_name ít hơn 3 ký tự
//			if (last_name.length() < 3) {
//				// Lưu thông điệp lỗi vào request attribute
//				request.setAttribute("last_name_error", "The last name must contain at least 3 characters.");
//			}
//
//			// Forward lại request đến trang form để hiển thị lại form với thông báo lỗi
//			RequestDispatcher dispatcher = request.getRequestDispatcher("User/user_form.jsp");
//			dispatcher.forward(request, response);
//			return; // Dừng phương thức
//		}
//
//		// Kiểm tra điều kiện nếu first_name nhiều hơn 20 kí tự
//		if (first_name.length() > 20 || last_name.length() > 20) {
//			if (first_name.length() >= 20) {
//				// Lưu thông điệp lỗi vào request attribute
//				request.setAttribute("first_name_error", "The first name cannot exceed 20 characters.");
//			}
//
//			// Kiểm tra điều kiện nếu first_name ít hơn 3 ký tự
//			if (last_name.length() >= 20) {
//				// Lưu thông điệp lỗi vào request attribute
//				request.setAttribute("last_name_error", "The first name cannot exceed 20 characters.");
//			}
//
//			// Forward lại request đến trang form để hiển thị lại form với thông báo lỗi
//			RequestDispatcher dispatcher = request.getRequestDispatcher("User/user_form.jsp");
//			dispatcher.forward(request, response);
//			return; // Dừng phương thức
//		}
//
//		// Kiểm tra điều kiện nếu first_name chứa số hoặc ký tự đặc biệt(chứa ít nhất
//		// một ký tự không phải là chữ cái từ A-Z hoặc a-z)
//		if (!first_name.matches("[a-zA-Z]+") || !last_name.matches("[a-zA-Z]+")) {
//			if (!first_name.matches("[a-zA-Z]+")) {
//				// Lưu thông điệp lỗi vào request attribute
//				request.setAttribute("first_name_error",
//						"The first name cannot contain numbers or special characters.");
//			}
//
//			// Kiểm tra điều kiện nếu last_name chứa số hoặc ký tự đặc biệt
//			if (!last_name.matches("[a-zA-Z]+")) {
//				// Lưu thông điệp lỗi vào request attribute
//				request.setAttribute("last_name_error", "The last name cannot contain numbers or special characters.");
//			}
//
//			// Forward lại request đến trang form để hiển thị lại form với thông báo lỗi
//			RequestDispatcher dispatcher = request.getRequestDispatcher("User/user_form.jsp");
//			dispatcher.forward(request, response);
//			return; // Dừng phương thức
//		}
//
//		// Kiểm tra nếu giá trị giới tính không được chọn
//		if (gender == null || gender.isEmpty()) {
//			// Lưu thông điệp lỗi vào request attribute cho giới tính
//			request.setAttribute("gender_error", "Please select a gender.");
//			// Forward lại request đến trang form để hiển thị lại form với thông báo lỗi
//			RequestDispatcher dispatcher = request.getRequestDispatcher("User/user_form.jsp");
//			dispatcher.forward(request, response);
//			return; // Dừng phương thức
//		}
//
//		if (address.isEmpty()) {
//			// Lưu thông điệp lỗi vào request attribute cho last_name
//			request.setAttribute("address_error", "The address cannot be blank.");
//			// Forward lại request đến trang form để hiển thị lại form với thông báo lỗi
//			RequestDispatcher dispatcher = request.getRequestDispatcher("User/user_form.jsp");
//			dispatcher.forward(request, response);
//			return; // Dừng phương thức
//		}
//
//		if (address.length() < 5) {
//			// Lưu thông điệp lỗi vào request attribute cho last_name
//			request.setAttribute("address_error", "The address least 5 characters.");
//			// Forward lại request đến trang form để hiển thị lại form với thông báo lỗi
//			RequestDispatcher dispatcher = request.getRequestDispatcher("User/user_form.jsp");
//			dispatcher.forward(request, response);
//			return; // Dừng phương thức
//		}
//
//		if (address.length() > 50) {
//			// Lưu thông điệp lỗi vào request attribute cho last_name
//			request.setAttribute("address_error", "The address cannot exceed 50 characters.");
//			// Forward lại request đến trang form để hiển thị lại form với thông báo lỗi
//			RequestDispatcher dispatcher = request.getRequestDispatcher("User/user_form.jsp");
//			dispatcher.forward(request, response);
//			return; // Dừng phương thức
//		}
//
//		// Kiểm tra điều kiện nếu last_name chứa số hoặc ký tự đặc biệt
//		if (!address.matches("[a-zA-Z]+")) {
//			// Lưu thông điệp lỗi vào request attribute
//			request.setAttribute("address_error", "The address cannot contain numbers or special characters.");
//			// Forward lại request đến trang form để hiển thị lại form với thông báo lỗi
//			RequestDispatcher dispatcher = request.getRequestDispatcher("User/user_form.jsp");
//			dispatcher.forward(request, response);
//			return; // Dừng phương thức
//		}
//
//		if (phone_number.isEmpty()) {
//			// Lưu thông điệp lỗi vào request attribute cho số điện thoại
//			request.setAttribute("phone_number_error", "Phone number cannot be blank.");
//
//			// Forward lại request đến trang form để hiển thị lại form với thông báo lỗi
//			RequestDispatcher dispatcher = request.getRequestDispatcher("User/user_form.jsp");
//			dispatcher.forward(request, response);
//			return; // Dừng phương thức
//		}
//
//		if (phone_number.length() > 11 || phone_number.length() < 10) {
//			// Lưu thông điệp lỗi vào request attribute cho số điện thoại
//			request.setAttribute("phone_number_error", "Phone number invalid.");
//
//			// Forward lại request đến trang form để hiển thị lại form với thông báo lỗi
//			RequestDispatcher dispatcher = request.getRequestDispatcher("User/user_form.jsp");
//			dispatcher.forward(request, response);
//			return; // Dừng phương thức
//		}
//
//		// Kiểm tra điều kiện nếu số điện thoại không chỉ chứa số
//		if (!phone_number.matches("\\d+")) {
//			// Lưu thông điệp lỗi vào request attribute cho số điện thoại
//			request.setAttribute("phone_number_error", "Phone number must contain only digits.");
//
//			// Forward lại request đến trang form để hiển thị lại form với thông báo lỗi
//			RequestDispatcher dispatcher = request.getRequestDispatcher("User/user_form.jsp");
//			dispatcher.forward(request, response);
//			return; // Dừng phương thức
//		}
//
//		if (account.isEmpty()) {
//			// Lưu thông điệp lỗi vào request attribute cho số điện thoại
//			request.setAttribute("account_error", "Account cannot be blank.");
//
//			// Forward lại request đến trang form để hiển thị lại form với thông báo lỗi
//			RequestDispatcher dispatcher = request.getRequestDispatcher("User/user_form.jsp");
//			dispatcher.forward(request, response);
//			return; // Dừng phương thức
//		}
//
//		if (account.length() < 10) {
//			// Lưu thông điệp lỗi vào request attribute cho last_name
//			request.setAttribute("account_error", "The account least 10 characters.");
//			// Forward lại request đến trang form để hiển thị lại form với thông báo lỗi
//			RequestDispatcher dispatcher = request.getRequestDispatcher("User/user_form.jsp");
//			dispatcher.forward(request, response);
//			return; // Dừng phương thức
//		}
//
//		if (account.length() >= 30) {
//			// Lưu thông điệp lỗi vào request attribute cho last_name
//			request.setAttribute("account_error", "The address cannot exceed 30 characters.");
//			// Forward lại request đến trang form để hiển thị lại form với thông báo lỗi
//			RequestDispatcher dispatcher = request.getRequestDispatcher("User/user_form.jsp");
//			dispatcher.forward(request, response);
//			return; // Dừng phương thức
//		}
//
//		// Kiểm tra định dạng của địa chỉ email
//		if (!EmailValidator.getInstance().isValid(account)) {
//			// Lưu thông điệp lỗi vào request attribute cho tài khoản
//			request.setAttribute("account_error", "Invalid email format.");
//
//			// Forward lại request đến trang form để hiển thị lại form với thông báo lỗi
//			RequestDispatcher dispatcher = request.getRequestDispatcher("User/user_form.jsp");
//			dispatcher.forward(request, response);
//			return; // Dừng phương thức
//		}
//
//		if (account.isEmpty()) {
//			// Lưu thông điệp lỗi vào request attribute cho số điện thoại
//			request.setAttribute("account_error", "Account cannot be blank.");
//
//			// Forward lại request đến trang form để hiển thị lại form với thông báo lỗi
//			RequestDispatcher dispatcher = request.getRequestDispatcher("User/user_form.jsp");
//			dispatcher.forward(request, response);
//			return; // Dừng phương thức
//		}
//
//		if (password.length() < 10) {
//			// Lưu thông điệp lỗi vào request attribute cho last_name
//			request.setAttribute("password_error", "The account least 10 characters.");
//			// Forward lại request đến trang form để hiển thị lại form với thông báo lỗi
//			RequestDispatcher dispatcher = request.getRequestDispatcher("User/user_form.jsp");
//			dispatcher.forward(request, response);
//			return; // Dừng phương thức
//		}
//
//		if (password.length() > 10) {
//			// Lưu thông điệp lỗi vào request attribute cho last_name
//			request.setAttribute("password_error", "The address cannot exceed 10 characters.");
//			// Forward lại request đến trang form để hiển thị lại form với thông báo lỗi
//			RequestDispatcher dispatcher = request.getRequestDispatcher("User/user_form.jsp");
//			dispatcher.forward(request, response);
//			return; // Dừng phương thức
//		}
//
//		// Kiểm tra nếu giá trị giới tính không được chọn
//		if (type == null || type.isEmpty()) {
//			// Lưu thông điệp lỗi vào request attribute cho giới tính
//			request.setAttribute("type_error", "Please select a type.");
//			// Forward lại request đến trang form để hiển thị lại form với thông báo lỗi
//			RequestDispatcher dispatcher = request.getRequestDispatcher("User/user_form.jsp");
//			dispatcher.forward(request, response);
//			return; // Dừng phương thức
//		}
//
//		User newUser = new User(first_name, last_name, DOB, gender, address, phone_number, account, password, type);
//		usersDAO.insertUser(newUser);
//		response.sendRedirect("list");
//	}

	private void deleteUser(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
		int id = Integer.parseInt(request.getParameter("id")); // Sử dụng long ở đây
		usersDAO.deleteUser(id);
		response.sendRedirect("UserController?action=/list");
	}

}
