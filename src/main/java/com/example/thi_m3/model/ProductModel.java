package com.example.thi_m3.model;

import com.example.thi_m3.entities.Product;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductModel {
    private static final Connection conn = DatabaseModel.getConnection();

    private static final String GET_ALL_PRODUCTS = "CALL getAllProduct()";
    private static final String CREATE_PRODUCT = "INSERT INTO Products (name, price, discount, stock) VALUES (?,?,?,?)";
    private static final String TOP_SELLING_PRODUCTS = "CALL TopSellingProducts(?)";
    private static final String CHECK_ORDER_ITEMS = "SELECT COUNT(*) FROM Order_Items WHERE product_id = ?";
    private static final String DELETE_PRODUCT = "DELETE FROM Products WHERE product_id = ?";
    private static final String FIND_BY_ID = "SELECT * FROM products WHERE product_id = ?";
    private static final String UPDATE = "UPDATE set name=?, price=?, discount=?, stock=? WHERE product_id=?";



    public static ResultSet getAllProducts() throws SQLException {
        PreparedStatement preparedStatement = conn.prepareStatement(GET_ALL_PRODUCTS);
        return preparedStatement.executeQuery();
    }

    public static void createProduct(Product product) throws SQLException {
        PreparedStatement preparedStatement = conn.prepareStatement(CREATE_PRODUCT);
        preparedStatement.setString(1,product.getName());
        preparedStatement.setBigDecimal(2,product.getPrice());
        preparedStatement.setInt(3,product.getDiscount());
        preparedStatement.setInt(4,product.getStock());

        preparedStatement.executeUpdate();
        System.out.println("Create product successful!!!");
    }

    public static List<Product> getTopSellingProducts(int limit) throws SQLException {
        List<Product> list = new ArrayList<>();
        PreparedStatement preparedStatement = conn.prepareStatement(TOP_SELLING_PRODUCTS);

        try {
            preparedStatement.setInt(1, limit); // Đặt tham số limit cho stored procedure
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int pId = resultSet.getInt("product_id");
                String pName = resultSet.getString("name");
                BigDecimal pPrice = resultSet.getBigDecimal("price");
                int pDiscount = resultSet.getInt("discount");
                // Tồn kho không có trong kết quả, đặt mặc định là 0
                int pStock = resultSet.getInt("stock"); // Có thể truy vấn thêm từ Products nếu cần

                Product product = new Product(pName, pPrice, pDiscount, pStock);
                product.setId(pId);
                list.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e; // Ném lại ngoại lệ để xử lý ở tầng trên
        } finally {
            preparedStatement.close(); // Đóng PreparedStatement để tránh rò rỉ tài nguyên
        }
        return list;
    }



    public static boolean hasRelatedOrders(int productId) throws SQLException {
        PreparedStatement preparedStatement = conn.prepareStatement(CHECK_ORDER_ITEMS);
        try {
            preparedStatement.setInt(1, productId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1) > 0;
            }
        } finally {
            preparedStatement.close();
        }
        return false;
    }

    public static boolean deleteProduct(int productId) throws SQLException {
        PreparedStatement preparedStatement = conn.prepareStatement(DELETE_PRODUCT);
        try {
            preparedStatement.setInt(1, productId);
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        } finally {
            preparedStatement.close();
        }
    }

    public static Product getProductById(int id){
        try{
            PreparedStatement preparedStatement = conn.prepareStatement(FIND_BY_ID);
            preparedStatement.setInt(1,id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                return new Product(
                        resultSet.getInt("product_id"),
                        resultSet.getString("name"),
                        resultSet.getBigDecimal("price"),
                        resultSet.getInt("discount"),
                        resultSet.getInt("stock")
                );
            }
        } catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    public static void updateProduct(Product product){
//UPDATE set name=?, price=?, discount=?, stock=? WHERE product_id=?
        try{
            PreparedStatement preparedStatement = conn.prepareStatement(UPDATE);
            preparedStatement.setString(1,product.getName());
            preparedStatement.setBigDecimal(2,product.getPrice());
            preparedStatement.setInt(3,product.getDiscount());
            preparedStatement.setInt(4,product.getStock());
            preparedStatement.setInt(5,product.getId());
            preparedStatement.executeUpdate();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
}
