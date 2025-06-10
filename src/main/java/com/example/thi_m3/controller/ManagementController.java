package com.example.thi_m3.controller;

import com.example.thi_m3.entities.Product;
import com.example.thi_m3.service.ProductService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "ManagementController", value = "/management/*")
public class ManagementController extends BaseController {

    @Override
    public void init() throws ServletException {
        super.init();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getPathInfo() != null ? req.getPathInfo() : "/";
        if (path == null) {
            path = "/";
        }

        switch (path) {
            case "":
            case "/":
                showListProduct(req, resp);
                break;
            case "/create":
                // Hiển thị thông báo thành công nếu có
                if ("created".equals(req.getParameter("success"))) {
                    req.setAttribute("success", "Sản phẩm đã được tạo thành công!");
                }
                renderView("/views/create_product.jsp", req, resp);
                break;
            case "/top-products":
                handleTopProducts(req, resp);
                break;
            case "/delete":
                try {
                    ProductService.deleteProduct(req, resp);
                } catch (SQLException e) {
                    req.setAttribute("error", "Lỗi hệ thống khi xóa sản phẩm.");
                    renderView("/views/list.jsp", req, resp);
                }
                break;
            default:
                resp.sendError(HttpServletResponse.SC_NOT_FOUND);
                break;
        }
    }

    private void showListProduct(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Product> listProduct = ProductService.getAllProducts();
        request.setAttribute("list_product", listProduct);
        renderView("/views/list.jsp", request, response);
    }

    private void handleTopProducts(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String limitStr = req.getParameter("limit");
        if (limitStr == null || limitStr.isEmpty()) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Thiếu tham số limit");
            return;
        }

        int limit;
        try {
            limit = Integer.parseInt(limitStr);
        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Tham số limit không hợp lệ");
            return;
        }

        List<Product> topProducts = ProductService.getTopSellingProducts(limit);
        req.setAttribute("list_product", topProducts); // Đặt danh sách top sản phẩm vào request
        renderView("/views/list.jsp", req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getPathInfo() != null ? req.getPathInfo() : "/";
        if (path == null) {
            path = "/";
        }

        switch (path) {
            case "/create":
                try {
                    ProductService.createProduct(req, resp);
                } catch (SQLException e) {
                    req.setAttribute("error", "Lỗi hệ thống khi tạo sản phẩm. Vui lòng thử lại.");
                    renderView("/views/create_product.jsp", req, resp);
                }
                break;
            default:
                resp.sendError(HttpServletResponse.SC_NOT_FOUND);
                break;
        }
    }
}