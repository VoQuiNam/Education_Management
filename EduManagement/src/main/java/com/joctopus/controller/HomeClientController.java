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
import com.joctopus.model.Banner;

@WebServlet("/HomeClientController")
public class HomeClientController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private BannerDao bannerDao;

    // Khởi tạo BannerDao
    public void init() {
        this.bannerDao = new BannerDaoImpl();
    }

    // Xử lý các yêu cầu GET
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action"); // Lấy hành động từ tham số request
        try {
            if (action != null) {
                switch (action) {
                    case "/listBanner":
                        listBanners(request, response);
                        break;
                    default:
                        listBanners(request, response); // Hành động mặc định
                        break;
                }
            } else {
                listBanners(request, response); // Nếu không có hành động nào, hiển thị danh sách banner
            }
            System.out.println("Action: " + action); // Log hành động để kiểm tra
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }

    // Xử lý các yêu cầu POST (chuyển hướng đến doGet)
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    // Lấy danh sách banners từ database và chuyển đến JSP
    private void listBanners(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        // Gọi DAO để lấy danh sách banners
        List<Banner> listBanner = bannerDao.selectAllBanners();

        // Ghi log để kiểm tra dữ liệu
        System.out.println("Result from selectAllBanners: " + listBanner);

        // Đặt danh sách banners làm thuộc tính của request
        request.setAttribute("listBanner", listBanner);

        // Chuyển tiếp đến JSP để hiển thị danh sách banners
        RequestDispatcher dispatcher = request.getRequestDispatcher("HomeClient/index.jsp");
        dispatcher.forward(request, response);
    }
}
