package com.example.thi_m3.service;

import com.example.thi_m3.entities.Product;
import com.example.thi_m3.model.ProductModel;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ProductService {

    private static final List<Integer> VALID_DISCOUNTS = Arrays.asList(0, 5, 10, 15);

    public static List<Product> getAllProducts() {
        try {
            ResultSet resultSet = ProductModel.getAllProducts();
            List<Product> list_products = new ArrayList<>();
            while (resultSet.next()) {
                int pId = resultSet.getInt("product_id");
                String pName = resultSet.getString("name");
                BigDecimal pPrice = resultSet.getBigDecimal("price");
                int pDiscount = resultSet.getInt("discount");
                int pStock = resultSet.getInt("stock");

                Product product = new Product(pName, pPrice, pDiscount, pStock);
                product.setId(pId);

                list_products.add(product);
            }
            return list_products;
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>(); // Trả về danh sách rỗng thay vì null
        }
    }

    public static void createProduct(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
        String name = request.getParameter("name");
        String priceParam = request.getParameter("price");
        String discountParam = request.getParameter("discount");
        String stockParam = request.getParameter("stock");

        // Lưu dữ liệu đã nhập để hiển thị lại nếu có lỗi
        request.setAttribute("name", name);
        request.setAttribute("price", priceParam);
        request.setAttribute("discount", discountParam);
        request.setAttribute("stock", stockParam);

        // Kiểm tra tham số bắt buộc
        if (name == null || priceParam == null || discountParam == null || stockParam == null) {
            request.setAttribute("error", "Vui lòng điền đầy đủ thông tin.");
            request.getRequestDispatcher("/views/create_product.jsp").forward(request, response);
            return;
        }

        // Kiểm tra định dạng số
        BigDecimal price;
        int discount;
        int stock;
        try {
            price = new BigDecimal(priceParam.trim());
            discount = Integer.parseInt(discountParam.trim());
            stock = Integer.parseInt(stockParam.trim());
        } catch (NumberFormatException e) {
            request.setAttribute("error", "Giá, giảm giá hoặc tồn kho không đúng định dạng.");
            request.getRequestDispatcher("/views/create_product.jsp").forward(request, response);
            return;
        }

        // Kiểm tra giá trị hợp lệ
        if (name.trim().length() < 2) {
            request.setAttribute("error", "Tên sản phẩm phải có ít nhất 2 ký tự.");
            request.getRequestDispatcher("/views/create_product.jsp").forward(request, response);
            return;
        }
        if (price.compareTo(new BigDecimal("1000")) < 0) { // Giá tối thiểu 1000
            request.setAttribute("error", "Giá sản phẩm phải lớn hơn hoặc bằng 1000.");
            request.getRequestDispatcher("/views/create_product.jsp").forward(request, response);
            return;
        }
        if (!VALID_DISCOUNTS.contains(discount)) {
            request.setAttribute("error", "Giảm giá chỉ được chọn trong các giá trị: 0, 5, 10, 15.");
            request.getRequestDispatcher("/views/create_product.jsp").forward(request, response);
            return;
        }
        if (stock < 0) { // Tồn kho không âm
            request.setAttribute("error", "Tồn kho không được nhỏ hơn 0.");
            request.getRequestDispatcher("/views/create_product.jsp").forward(request, response);
            return;
        }

        Product product = new Product(name.trim(), price, discount, stock);
        ProductModel.createProduct(product);
        response.sendRedirect("/management?success=created"); // Chuyển hướng đến danh sách sản phẩm
    }

    public static List<Product> getTopSellingProducts(int limit) {
        try {
            return ProductModel.getTopSellingProducts(limit);
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>(); // Trả về danh sách rỗng nếu có lỗi
        }
    }


    public static boolean deleteProduct(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        String productIdStr = request.getParameter("id");
        if (productIdStr == null || productIdStr.isEmpty()) {
            request.setAttribute("error", "Thiếu thông tin sản phẩm để xóa.");
            request.getRequestDispatcher("/views/list.jsp").forward(request, response);
            return false;
        }

        int productId;
        try {
            productId = Integer.parseInt(productIdStr);
        } catch (NumberFormatException e) {
            request.setAttribute("error", "Mã sản phẩm không hợp lệ.");
            request.getRequestDispatcher("/views/list.jsp").forward(request, response);
            return false;
        }

        if (ProductModel.hasRelatedOrders(productId)) {
            request.setAttribute("error", "Không thể xóa sản phẩm vì đã có đơn hàng liên quan.");
            request.getRequestDispatcher("/views/list.jsp").forward(request, response);
            return false;
        }

        if (ProductModel.deleteProduct(productId)) {
            response.sendRedirect(request.getContextPath() + "/management?success=deleted");
            return true;
        } else {
            request.setAttribute("error", "Xóa sản phẩm thất bại. Vui lòng thử lại.");
            request.getRequestDispatcher("/views/list.jsp").forward(request, response);
            return false;
        }
    }

}