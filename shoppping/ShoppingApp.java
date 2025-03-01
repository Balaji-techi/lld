package Practice.shoppping;

import java.util.Scanner;

public class ShoppingApp {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        UserOperations userOps = new UserOperations();
        AdminOperations adminOps = new AdminOperations();
        OrderOperations orderOps = new OrderOperations();

        System.out.println("Welcome to the Shopping Application!");
        while (true) {
            System.out.println("\n1. Register");
            System.out.println("2. Login as User");
            System.out.println("3. Login as Admin");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Enter username: ");
                    String username = scanner.nextLine();
                    System.out.print("Enter password: ");
                    String password = scanner.nextLine();
                    System.out.print("Is this an admin account? (yes/no): ");
                    boolean isAdmin = scanner.nextLine().equalsIgnoreCase("yes");
                    userOps.register(username, password, isAdmin);
                    break;

                case 2:
                    System.out.print("Enter username: ");
                    username = scanner.nextLine();
                    System.out.print("Enter password: ");
                    password = scanner.nextLine();
                    if (userOps.login(username, password, false)) {
                        System.out.println("Login successful!");
                        userDashboard(scanner, username, orderOps);
                    } else {
                        System.out.println("Invalid credentials.");
                    }
                    break;

                case 3:
                    System.out.print("Enter admin username: ");
                    username = scanner.nextLine();
                    System.out.print("Enter password: ");
                    password = scanner.nextLine();
                    if (userOps.login(username, password, true)) {
                        System.out.println("Admin login successful!");
                        adminDashboard(scanner, adminOps);
                    } else {
                        System.out.println("Invalid admin credentials.");
                    }
                    break;

                case 4:
                    System.out.println("Thank you for using the Shopping Application!");
                    scanner.close();
                    return;

                default:
                    System.out.println("Invalid choice! Please try again.");
            }
        }
    }

    private static void userDashboard(Scanner scanner, String username, OrderOperations orderOps) {
        while (true) {
            System.out.println("\nUser Dashboard:");
            System.out.println("1. Place an Order");
            System.out.println("2. View Order History");
            System.out.println("3. Logout");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1:
                    System.out.print("Enter product ID: ");
                    int productId = scanner.nextInt();
                    System.out.print("Enter quantity: ");
                    int quantity = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Enter promo code (or press Enter to skip): ");
                    String promoCode = scanner.nextLine();
                    orderOps.placeOrder(getUserId(username), productId, quantity, promoCode.isEmpty() ? null : promoCode);
                    break;

                case 2:
                    System.out.println("Feature under development! Stay tuned.");
                    break;

                case 3:
                    System.out.println("Logged out successfully!");
                    return;

                default:
                    System.out.println("Invalid choice! Please try again.");
            }
        }
    }

    private static void adminDashboard(Scanner scanner, AdminOperations adminOps) {
        while (true) {
            System.out.println("\nAdmin Dashboard:");
            System.out.println("1. Add Product");
            System.out.println("2. Update Stock");
            System.out.println("3. View Top 3 Selling Products");
            System.out.println("4. Logout");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Enter product name: ");
                    String productName = scanner.nextLine();
                    System.out.print("Enter price: ");
                    double price = scanner.nextDouble();
                    System.out.print("Enter stock: ");
                    int stock = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Is this product eligible for promo codes? (yes/no): ");
                    boolean isPromoApplicable = scanner.nextLine().equalsIgnoreCase("yes");
                    adminOps.addProduct(productName, price, stock, isPromoApplicable);
                    break;

                case 2:
                    System.out.print("Enter product ID: ");
                    int productId = scanner.nextInt();
                    System.out.print("Enter new stock quantity: ");
                    int newStock = scanner.nextInt();
                    adminOps.updateStock(productId, newStock);
                    break;

                case 3:
                    adminOps.viewTopSellingProducts();
                    break;

                case 4:
                    System.out.println("Logged out successfully!");
                    return;

                default:
                    System.out.println("Invalid choice! Please try again.");
            }
        }
    }

    private static int getUserId(String username) {
        return 1;
    }
}
