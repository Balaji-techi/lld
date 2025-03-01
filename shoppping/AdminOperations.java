package Practice.shoppping;

import java.sql.*;

public class AdminOperations {

    public void addProduct(String productName, double price, int stock, boolean isPromoApplicable) {
        String query = "INSERT INTO Products (product_name, price, stock, is_promo_applicable) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, productName);
            stmt.setDouble(2, price);
            stmt.setInt(3, stock);
            stmt.setBoolean(4, isPromoApplicable);
            stmt.executeUpdate();
            System.out.println("Product added successfully!");
        } catch (SQLException e) {
            System.out.println("Error adding product: " + e.getMessage());
        }
    }

    public void updateStock(int productId, int newStock) {
        String query = "UPDATE Products SET stock = ? WHERE product_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, newStock);
            stmt.setInt(2, productId);
            stmt.executeUpdate();
            System.out.println("Stock updated successfully!");
        } catch (SQLException e) {
            System.out.println("Error updating stock: " + e.getMessage());
        }
    }

    public void viewTopSellingProducts() {
        String query = "SELECT product_id, SUM(quantity) AS total_sold FROM Orders GROUP BY product_id ORDER BY total_sold DESC LIMIT 3";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            System.out.println("Top 3 Selling Products:");
            while (rs.next()) {
                System.out.printf("Product ID: %d, Total Sold: %d\n", rs.getInt("product_id"), rs.getInt("total_sold"));
            }
        } catch (SQLException e) {
            System.out.println("Error viewing top-selling products: " + e.getMessage());
        }
    }
}
