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

        List<String> errors = new ArrayList<>();

        // Validate input
        // Validate input
        if (firstName == null || firstName.trim().isEmpty()) {
            errors.add("First name is required.");
        } else if (firstName.length() > 7 || firstName.length() < 2) {
            errors.add("First name must not exceed 7 characters.");
        }
        
        if (lastName == null || lastName.trim().isEmpty()) {
            errors.add("Last name is required.");
        } else if (lastName.length() > 7 || lastName.length() < 2) {
            errors.add("Last name must not exceed 7 characters.");
        }

        if (dob == null || dob.trim().isEmpty()) {
            errors.add("Date of birth is required.");
        }
        if (gender == null || gender.trim().isEmpty()) {
            errors.add("Gender is required.");
        }
        if (address == null || address.trim().isEmpty()) {
            errors.add("Address is required.");
        }else if (address.length() > 20 || address.length() < 5) {
            errors.add("Adress must not exceed 20 characters.");
        }
        
        
        if (phoneNumber == null || phoneNumber.trim().isEmpty()) {
            errors.add("Phone number is required.");
        }else if (phoneNumber.length() > 11 || phoneNumber.length() < 9) {
            errors.add("Phone number must not exceed 11 characters.");
        }
        
        if (account == null || account.trim().isEmpty()) {
            errors.add("Username is required.");
        }else if (account.length() > 25 || account.length() < 5) {
            errors.add("Account must not exceed 25 characters.");
        }
        else if (!account.matches("\\b[\\w.%-]+@[-.\\w]+\\.[A-Za-z]{2,4}\\b")) {
            errors.add("Invalid email format.");
        }
        
        if (password == null || password.trim().isEmpty()) {
            errors.add("Password is required.");
        }else if (password.length() > 15 || password.length() < 5) {
            errors.add("Password must not exceed 15 characters.");
        }
        
        
        if (type == null || type.trim().isEmpty()) {
            errors.add("Type is required.");
        }
        
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

        User newUser = new User(firstName, lastName, DOB, gender, address, phoneNumber, account, password, type);

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
