/*
create database BankingSystem;

use BankingSystem;

create table users(
        id int primary key auto_increment,
        name varchar(50) NOT NULL,
        email varchar(50) unique not null,
        password varchar(200) not null,
        balance double default 0
        );

CREATE TABLE transactions (
        id INT PRIMARY KEY AUTO_INCREMENT,
        user_id INT,
        type ENUM('DEPOSIT', 'WITHDRAW', 'TRANSFER'),
        amount DOUBLE NOT NULL,
        recipient_id INT NULL,
        timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
        FOREIGN KEY (user_id) REFERENCES users(id),
        FOREIGN KEY (recipient_id) REFERENCES users(id)
        );
*/
package Practice.BankingSystem;

import java.sql.*;
import java.util.Scanner;

public class BankingSystem {
    private static final String URL = "jdbc:mysql://localhost:3306/BankingSystem";
    private static final String USER = "root";
    private static final String PASSWORD = "Balajikalai12#";
    private static Connection conn;
    private static Scanner scanner = new Scanner(System.in);
    private static int currentUserId = -1;
    public static void main(String[] args) {
        try {
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Connected to Banking System!");
            mainMenu();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void mainMenu() {
        while (true) {
            System.out.println("\n==== Banking System ====");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.print("Enter choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> register();
                case 2 -> login();
                case 3 -> System.exit(0);
                default -> System.out.println("Invalid choice!");
            }
        }
    }

    private static void register() {
        System.out.print("Enter Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Email: ");
        String email = scanner.nextLine();
        System.out.print("Enter Password: ");
        String password = scanner.nextLine();

        try (PreparedStatement stmt = conn.prepareStatement("INSERT INTO users (name, email, password) VALUES (?, ?, ?)")) {
            stmt.setString(1, name);
            stmt.setString(2, email);
            stmt.setString(3, password);
            stmt.executeUpdate();
            System.out.println("Registration successful! Please login.");
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void login() {
        System.out.print("Enter Email: ");
        String email = scanner.nextLine();
        System.out.print("Enter Password: ");
        String password = scanner.nextLine();

        try (PreparedStatement stmt = conn.prepareStatement("SELECT id FROM users WHERE email = ? AND password = ?")) {
            stmt.setString(1, email);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                currentUserId = rs.getInt("id");
                System.out.println("Login successful!");
                userMenu();
            } else {
                System.out.println("Invalid credentials.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void userMenu() {
        while (true) {
            System.out.println("\n==== Banking Menu ====");
            System.out.println("1. Check Balance");
            System.out.println("2. Deposit Money");
            System.out.println("3. Withdraw Money");
            System.out.println("4. Transfer Money");
            System.out.println("5. Transaction History");
            System.out.println("6. Logout");
            System.out.print("Enter choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1 -> checkBalance();
                case 2 -> depositMoney();
                case 3 -> withdrawMoney();
                case 4 -> transferMoney();
                case 5 -> transactionHistory();
                case 6 -> {
                    currentUserId = -1;
                    System.out.println("Logged out!");
                    mainMenu();
                }
                default -> System.out.println("Invalid choice!");
            }
        }
    }

    private static void checkBalance() {
        try (PreparedStatement stmt = conn.prepareStatement("SELECT balance FROM users WHERE id = ?")) {
            stmt.setInt(1, currentUserId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                System.out.println("Your Balance: ₹" + rs.getDouble("balance"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void depositMoney() {
        System.out.print("Enter amount to deposit: ₹");
        double amount = scanner.nextDouble();

        try (PreparedStatement stmt = conn.prepareStatement("UPDATE users SET balance = balance + ? WHERE id = ?")) {
            stmt.setDouble(1, amount);
            stmt.setInt(2, currentUserId);
            stmt.executeUpdate();
            recordTransaction("DEPOSIT", amount, null);
            System.out.println("₹" + amount + " deposited successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void withdrawMoney() {
        System.out.print("Enter amount to withdraw: ₹");
        double amount = scanner.nextDouble();

        try (PreparedStatement stmt = conn.prepareStatement("UPDATE users SET balance = balance - ? WHERE id = ? AND balance >= ?")) {
            stmt.setDouble(1, amount);
            stmt.setInt(2, currentUserId);
            stmt.setDouble(3, amount);
            int rowsUpdated = stmt.executeUpdate();

            if (rowsUpdated > 0) {
                recordTransaction("WITHDRAW", amount, null);
                System.out.println("₹" + amount + " withdrawn successfully.");
            } else {
                System.out.println("Insufficient balance!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void transferMoney() {
        System.out.print("Enter recipient email: ");
        String recipientEmail = scanner.next();
        System.out.print("Enter amount to transfer: ₹");
        double amount = scanner.nextDouble();

        try {
            conn.setAutoCommit(false);

            int recipientId = -1;
            try (PreparedStatement stmt = conn.prepareStatement("SELECT id FROM users WHERE email = ?")) {
                stmt.setString(1, recipientEmail);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    recipientId = rs.getInt("id");
                } else {
                    System.out.println("Recipient not found!");
                    return;
                }
            }

            try (PreparedStatement stmt = conn.prepareStatement("UPDATE users SET balance = balance - ? WHERE id = ? AND balance >= ?")) {
                stmt.setDouble(1, amount);
                stmt.setInt(2, currentUserId);
                stmt.setDouble(3, amount);
                if (stmt.executeUpdate() > 0) {
                    try (PreparedStatement stmt2 = conn.prepareStatement("UPDATE users SET balance = balance + ? WHERE id = ?")) {
                        stmt2.setDouble(1, amount);
                        stmt2.setInt(2, recipientId);
                        stmt2.executeUpdate();
                        recordTransaction("TRANSFER", amount, recipientId);
                        System.out.println("₹" + amount + " transferred successfully.");
                    }
                } else {
                    System.out.println("Insufficient balance!");
                }
            }
            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
    private static void transactionHistory() {
        try (PreparedStatement stmt = conn.prepareStatement(
                "SELECT type, amount, recipient_id, timestamp FROM transactions WHERE user_id = ? ORDER BY timestamp DESC")) {
            stmt.setInt(1, currentUserId);
            ResultSet rs = stmt.executeQuery();

            System.out.println("\n==== Transaction History ====");
            if (!rs.isBeforeFirst()) {
                System.out.println("No transactions found.");
                return;
            }
            while (rs.next()) {
                String type = rs.getString("type");
                double amount = rs.getDouble("amount");
                int recipientId = rs.getInt("recipient_id");    
                Timestamp timestamp = rs.getTimestamp("timestamp");

                if (type.equals("TRANSFER")) {
                    System.out.println(type + " - ₹" + amount + " to User ID: " + recipientId + " on " + timestamp);
                } else {
                    System.out.println(type + " - ₹" + amount + " on " + timestamp);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private static void recordTransaction(String type, double amount, Integer recipientId) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement("INSERT INTO transactions (user_id, type, amount, recipient_id) VALUES (?, ?, ?, ?)")) {
            stmt.setInt(1, currentUserId);
            stmt.setString(2, type);
            stmt.setDouble(3, amount);
            stmt.setObject(4, recipientId);
            stmt.executeUpdate();
        }
    }
}