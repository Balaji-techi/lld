package Practice.shoppping;

import java.sql.*;

public class UserOperations {

    public void register(String username, String password, boolean isAdmin) {
        String encryptedPassword = CaesarCipher.encrypt(password, 3);
        String query = "INSERT INTO Users (username, password, is_admin) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, username);
            stmt.setString(2, encryptedPassword);
            stmt.setBoolean(3, isAdmin);
            stmt.executeUpdate();
            System.out.println("User registered successfully!");
        } catch (SQLException e) {
            System.out.println("Error during registration: " + e.getMessage());
        }
    }

    public boolean login(String username, String password, boolean isAdmin) {
        String encryptedPassword = CaesarCipher.encrypt(password, 3);
        String query = "SELECT * FROM Users WHERE username = ? AND password = ? AND is_admin = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, username);
            stmt.setString(2, encryptedPassword);
            stmt.setBoolean(3, isAdmin);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            System.out.println("Error during login: " + e.getMessage());
            return false;
        }
    }
}
