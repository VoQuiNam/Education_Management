package com.joctopus.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import com.joctopus.util.EmailUtil;

@WebServlet("/verifyResetCode")
public class VerifyCodeController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public VerifyCodeController() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Lấy thông báo lỗi từ session (nếu có)
        String errorMessage = (String) request.getSession().getAttribute("errorMessage");
        request.setAttribute("errorMessage", errorMessage);

        // Xóa thông báo lỗi khỏi session để tránh hiển thị lại
        request.getSession().removeAttribute("errorMessage");

        // Chuyển tiếp đến trang verifyCode.jsp
        RequestDispatcher dispatcher = request.getRequestDispatcher("ForgotPassword/verifyCode.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        
        if ("verify".equals(action)) {
            handleVerifyCode(request, response);
        } else if ("resend".equals(action)) {
            handleResendCode(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid action");
        }
    }

    private void handleVerifyCode(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        String inputCode = request.getParameter("verificationCode");
        String sessionCode = (String) request.getSession().getAttribute("verificationCode");

        if (sessionCode != null && sessionCode.equals(inputCode)) {
            // Mã xác minh đúng, chuyển đến trang đặt lại mật khẩu
            response.sendRedirect(request.getContextPath() + "/resetPassword");
        } else {
            // Mã sai, lưu thông báo lỗi vào session
            request.getSession().setAttribute("errorMessage", "Invalid verification code. Please try again.");
            response.sendRedirect(request.getContextPath() + "/verifyResetCode");
        }
    }

    private void handleResendCode(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        // Lấy email từ session
        String email = (String) request.getSession().getAttribute("email");

        if (email == null) {
            // Nếu email không tồn tại trong session, chuyển hướng về trang quên mật khẩu
            response.sendRedirect(request.getContextPath() + "/forgotPassword");
            return;
        }

        // Tạo mã xác minh mới
        String newCode = String.valueOf((int) (Math.random() * 900000) + 100000);

        // Lưu mã xác minh mới vào session
        request.getSession().setAttribute("verificationCode", newCode);

        // Gửi email chứa mã xác minh
        boolean emailSent = EmailUtil.sendEmail(email, "Verification Code", "Your new verification code is: " + newCode);

        if (emailSent) {
            // Hiển thị thông báo thành công (nếu cần)
            request.getSession().setAttribute("successMessage", "A new verification code has been sent to your email.");
        } else {
            // Hiển thị lỗi nếu gửi email thất bại
            request.getSession().setAttribute("errorMessage", "Failed to resend code. Please try again.");
        }

        // Chuyển hướng trở lại trang xác minh mã
        response.sendRedirect(request.getContextPath() + "/verifyResetCode");
    }
}
