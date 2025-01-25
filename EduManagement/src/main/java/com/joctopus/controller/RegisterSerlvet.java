package com.joctopus.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.joctopus.dao.UserDao;
import com.joctopus.dao.UserDaoImpl;
import com.joctopus.model.User;

@WebServlet("/register")
public class RegisterSerlvet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private UserDao userDao;

    @Override
    public void init() {
        userDao = new UserDaoImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("Register/register.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String firstName = request.getParameter("first_name");
        String lastName = request.getParameter("last_name");
        String dob = request.getParameter("DOB");
        LocalDate DOB = null;
        if (dob != null && !dob.isEmpty()) {
            DOB = LocalDate.parse(dob);
        } else {
            DOB = LocalDate.now(); // or any default date that suits your needs
        }
        String gender = request.getParameter("gender");
        String address = request.getParameter("address");
        String phoneNumber = request.getParameter("phone_number");
        String account = request.getParameter("account");
        String password = request.getParameter("password");
        String type = request.getParameter("type");

        // Default status to "Unapproved"
        String status = "Unapproved";
        // Set a default image URL or path
        String defaultImage = "images/default-user.png";

        List<String> errors = new ArrayList<>();

        // Validation logic...
        if (firstName == null || firstName.trim().isEmpty()) {
            errors.add("First name is required.");
        } else if (firstName.length() > 7 || firstName.length() < 2) {
            errors.add("First name must not exceed 7 characters.");
        }

        // Other validations...

        try {
            // Check if the email and phone number already exist
            if (userDao.isAccountExists(account)) {
                errors.add("Email already exists.");
            }
            if (userDao.isPhoneNumberExists(phoneNumber)) {
                errors.add("Phone number already exists.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new ServletException("Database error during validation", e);
        }

        if (!errors.isEmpty()) {
            request.setAttribute("errors", errors);
            RequestDispatcher dispatcher = request.getRequestDispatcher("Register/register.jsp");
            dispatcher.forward(request, response);
            return;
        }

        // Create a new user with the default status of "Unapproved"
        User newUser = new User(firstName, lastName, DOB, gender, address, phoneNumber, account, password, type,defaultImage, status);

        try {
            userDao.insertUser(newUser);

            // Redirect to the registration page with a success parameter
            response.sendRedirect(request.getContextPath() + "/register?success=true");

        } catch (SQLException e) {
            e.printStackTrace();
            throw new ServletException("Database error", e);
        }
    }

}
