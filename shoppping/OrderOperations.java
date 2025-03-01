package Practice.shoppping;

import java.sql.*;

public class OrderOperations {

    public void placeOrder(int userId, int productId, int quantity, String promoCode) {
        String productQuery = "SELECT price, stock, is_promo_applicable FROM Products WHERE product_id = ?";
        String promoQuery = "SELECT discount_percentage FROM PromoCodes WHERE code = ? AND used_by_user_id IS NULL";
        String orderInsert = "INSERT INTO Orders (user_id, product_id, quantity, total_price, promo_code_used) VALUES (?, ?, ?, ?, ?)";
        String promoUpdate = "UPDATE PromoCodes SET used_by_user_id = ? WHERE code = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement productStmt = conn.prepareStatement(productQuery);
             PreparedStatement promoStmt = conn.prepareStatement(promoQuery);
             PreparedStatement orderStmt = conn.prepareStatement(orderInsert);
             PreparedStatement promoUpdateStmt = conn.prepareStatement(promoUpdate)) {

            productStmt.setInt(1, productId);
            ResultSet productRs = productStmt.executeQuery();
            if (!productRs.next() || productRs.getInt("stock") < quantity) {
                System.out.println("Insufficient stock or invalid product.");
                return;
            }

            double price = productRs.getDouble("price");
            boolean isPromoApplicable = productRs.getBoolean("is_promo_applicable");
            double discount = 0;

            if (promoCode != null && isPromoApplicable) {
                promoStmt.setString(1, promoCode);
                ResultSet promoRs = promoStmt.executeQuery();
                if (promoRs.next()) {
                    discount = price * promoRs.getInt("discount_percentage") / 100;
                    promoUpdateStmt.setInt(1, userId);
                    promoUpdateStmt.setString(2, promoCode);
                    promoUpdateStmt.executeUpdate();
                }
            }

            double totalPrice = (price - discount) * quantity;

            orderStmt.setInt(1, userId);
            orderStmt.setInt(2, productId);
            orderStmt.setInt(3, quantity);
            orderStmt.setDouble(4, totalPrice);
            orderStmt.setBoolean(5, promoCode != null);
            orderStmt.executeUpdate();

            System.out.println("Order placed successfully!");
        } catch (SQLException e) {
            System.out.println("Error placing order: " + e.getMessage());
        }
    }
}
