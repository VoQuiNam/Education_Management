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

import com.joctopus.dao.UserDao;
import com.joctopus.dao.UserDaoImpl;
import com.joctopus.model.User;

@WebServlet("/ProfileUserController")
public class ProfileUserController extends HttpServlet {
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
		 // Check if the user is logged in
        HttpSession session = request.getSession(false); // Get the session, don't create a new one
        if (session == null || session.getAttribute("loggedInUser") == null) {
            // Redirect to the login page if the user is not logged in
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
		String action = request.getParameter("action");
		try {
			switch (action) {
			case "/editProfile":
				showEditForm(request, response);
				break;
			case "/update":
				updateUser(request, response);
				break;
			default:
				break;
			}
		} catch (SQLException ex) {
			throw new ServletException(ex);
		}
	}

	private void showEditForm(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, ServletException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		User existingAcc = usersDAO.selectUser(id);
		RequestDispatcher dispatcher = request.getRequestDispatcher("ProfileUser/index.jsp");
		request.setAttribute("User", existingAcc);
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
		// Kiểm tra lỗi
		if (first_name.isEmpty()) {
			request.setAttribute("first_name_error", "The first name cannot be blank.");
			hasError = true;
		} else if (first_name.length() > 10) {
			request.setAttribute("first_name_error", "The first name cannot contain more than 10 characters.");
			hasError = true;
		} else {
			request.setAttribute("input_first_name", first_name); // Lưu giá trị hợp lệ
		}

		if (last_name.isEmpty()) {
			request.setAttribute("last_name_error", "The last name cannot be blank.");
			hasError = true;
		} else if (last_name.length() > 10) {
			request.setAttribute("last_name_error", "The last name cannot contain more than 10 characters.");
			hasError = true;
		} else {
			request.setAttribute("input_last_name", last_name); // Lưu giá trị hợp lệ
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
		if (gender == null || gender.isEmpty()) {
			request.setAttribute("gender_error", "Please select a valid gender.");
			hasError = true;
		}

		String address = request.getParameter("address");
		if (address.isEmpty()) {
			request.setAttribute("address_error", "The address cannot be blank.");
			hasError = true;
		} else if (address.length() > 30) {
			request.setAttribute("address_error", "The address cannot contain more than 30 characters.");
			hasError = true;
		} else {
			request.setAttribute("input_address", address); // Lưu giá trị hợp lệ
		}

		String phone_number = request.getParameter("phone_number");
		if (phone_number.isEmpty()) {
			request.setAttribute("phone_number_error", "The phone number cannot be blank.");
			hasError = true;
		} else if (!phone_number.matches("[0-9]+")) { // Kiểm tra xem phone_number có phải là chỉ số không
			request.setAttribute("phone_number_error", "The phone number must contain only numbers.");
			hasError = true;
		} else if (!phone_number.equals(currentUser.getPhoneNumber()) && usersDAO.isPhoneNumberExists(phone_number)) {
			// Lưu thông điệp lỗi vào request attribute cho số điện thoại
			request.setAttribute("phone_number_error", "This phone number already exists. Please choose another one.");
			// Đặt biến hasError thành true để biểu thị rằng có lỗi được phát hiện
			hasError = true;
		} else if (phone_number.length() > 11) { // Kiểm tra số điện thoại có dài hơn 11 ký tự không
			request.setAttribute("phone_number_error", "The phone number cannot be longer than 11 digits.");
			hasError = true;
		} else {
			request.setAttribute("input_phone_number", phone_number); // Lưu giá trị hợp lệ
		}

		String account = request.getParameter("account");
		String password = request.getParameter("password");
		String type = request.getParameter("type");
		 String status = request.getParameter("status");

		boolean isUnchanged = first_name.equals(currentUser.getFirstName())
				&& last_name.equals(currentUser.getLastName())
				&& ((DOB == null && currentUser.getDob() == null) || (DOB != null && DOB.equals(currentUser.getDob())))
				&& gender.equals(currentUser.getGender()) && address.equals(currentUser.getAddress())
				&& phone_number.equals(currentUser.getPhoneNumber()) && account.equals(currentUser.getAccount())
				&& password.equals(currentUser.getPassword()) && type.equals(currentUser.getType());

		if (isUnchanged) {
			// If no fields are changed, set error message and forward to the form
			request.getSession().setAttribute("errorMessage", "No changes detected. Please modify at least one field.");
			// Chuyển hướng đến URL gốc (sử dụng sendRedirect để đảm bảo URL được làm mới)
			response.sendRedirect(request.getContextPath() + "/ProfileUserController?action=/editProfile&id=" + id);
			return;
		}

		// Nếu có lỗi, hiển thị lại form với thông báo lỗi
		if (hasError) {

			// Lưu lại các giá trị người dùng nhập vào request
			request.setAttribute("input_first_name", first_name);
			request.setAttribute("input_last_name", last_name);
			request.setAttribute("input_dob", dobParam);
			request.setAttribute("input_gender", gender);
			request.setAttribute("input_address", address);
			request.setAttribute("input_phone_number", phone_number);
			request.setAttribute("input_account", account);
			request.setAttribute("input_password", password);
			request.setAttribute("input_type", type);
			RequestDispatcher dispatcher = request
					.getRequestDispatcher("ProfileUserController?action=/editProfile&id=" + id);
			dispatcher.forward(request, response);
			return;
		}

		User updateUser = new User(id, first_name, last_name, DOB, gender, address, phone_number, account, password,
				type);

		usersDAO.updateUser(updateUser);

		// update the session with the new values to display on website
		request.getSession().setAttribute("firstName", first_name);
		request.getSession().setAttribute("lastName", last_name);
		request.getSession().setAttribute("successMessage", "User updated successfully.");
		response.sendRedirect("ProfileUserController?action=/editProfile&id=" + id);
	}

}
