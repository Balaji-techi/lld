package Practice.StockManagement;

import java.util.*;

public class StockManagementSystem {
    public static void main(String[] args) {
        while (true) {
            System.out.println("\nPractice.StockManagement.Stock Management System:");
            System.out.println("1. Add Practice.StockManagement.Product");
            System.out.println("2. Update Practice.StockManagement.Stock");
            System.out.println("3. Restock Practice.StockManagement.Product");
            System.out.println("4. Make Sale");
            System.out.println("5. View Practice.StockManagement.Product List");
            System.out.println("6. View Practice.StockManagement.Stock List");
            System.out.println("7. View Transaction History");
            System.out.println("8. Exit");

            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    addProduct();
                    break;
                case 2:
                    updateStock();
                    break;
                case 3:
                    restockProduct();
                    break;
                case 4:
                    makeSale();
                    break;
                case 5:
                    viewProductList();
                    break;
                case 6:
                    viewStockList();
                    break;
                case 7:
                    viewTransactionHistory();
                    break;
                case 8:
                    System.out.println("Exiting system...");
                    return;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }

    static ArrayList<Product> products = new ArrayList<>();
    static ArrayList<Stock> stocks = new ArrayList<>();
    static ArrayList<Sales> salesList = new ArrayList<>();
    static ArrayList<TransactionHistory> transactionHistoryList = new ArrayList<>();
    static Scanner scanner = new Scanner(System.in);
    static int productCounter = 1;
    static int stockCounter = 1;
    static int saleCounter = 1;

    static int transactionCounter = 1;

    public static void addProduct() {
        scanner.nextLine(); // consume newline
        System.out.print("Enter product name: ");
        String name = scanner.nextLine();
        System.out.print("Enter product price: ");
        double price = scanner.nextDouble();
        System.out.print("Enter supplier ID: ");
        int supplierId = scanner.nextInt();
        Supplier supplier = new Supplier(supplierId, "Practice.StockManagement.Supplier" + supplierId); // Simple supplier name

        Product product = new Product(productCounter++, name, price, supplier);
        products.add(product);
        System.out.println("Practice.StockManagement.Product added: " + product);
    }
    public static void updateStock() {
        System.out.print("Enter product ID to update stock: ");
        int productId = scanner.nextInt();
        Product product = findProductById(productId);
        if (product == null) {
            System.out.println("Practice.StockManagement.Product not found.");
            return;
        }

        System.out.print("Enter quantity: ");
        int quantity = scanner.nextInt();
        System.out.print("Enter threshold quantity: ");
        int thresholdQuantity = scanner.nextInt();

        Stock stock = findStockByProductId(productId);
        if (stock == null) {
            Stock newStock = new Stock(stockCounter++, product, quantity, thresholdQuantity);
            stocks.add(newStock);
            transactionHistoryList.add(new TransactionHistory(transactionCounter++, newStock, "Update", quantity));
            System.out.println("New stock created: " + newStock);
        } else {
            stock.quantity = quantity;
            stock.thresholdQuantity = thresholdQuantity;
            transactionHistoryList.add(new TransactionHistory(transactionCounter++, stock, "Update", quantity));
            System.out.println("Practice.StockManagement.Stock updated: " + stock);
        }
    }

    public static void restockProduct() {
        System.out.print("Enter product ID to restock: ");
        int productId = scanner.nextInt();
        Stock stock = findStockByProductId(productId);
        if (stock == null) {
            System.out.println("Practice.StockManagement.Stock not found for this product.");
            return;
        }

        System.out.print("Enter quantity to restock: ");
        int restockQty = scanner.nextInt();
        stock.restock(restockQty);
        transactionHistoryList.add(new TransactionHistory(transactionCounter++, stock, "Restock", restockQty));
        System.out.println("Restocked successfully: " + stock);
    }

    public static void makeSale() {
        System.out.print("Enter product ID to make sale: ");
        int productId = scanner.nextInt();
        Product product = findProductById(productId);
        if (product == null) {
            System.out.println("Practice.StockManagement.Product not found.");
            return;
        }

        Stock stock = findStockByProductId(productId);
        if (stock == null) {
            System.out.println("No stock available for this product.");
            return;
        }

        System.out.print("Enter quantity sold: ");
        int quantitySold = scanner.nextInt();
        if (!stock.checkStockAvailability(quantitySold)) {
            System.out.println("Not enough stock.");
            return;
        }

        stock.updateStock(quantitySold);
        double salePrice = product.price * quantitySold;
        Sales sale = new Sales(saleCounter++, product, quantitySold, salePrice);
        salesList.add(sale);

        transactionHistoryList.add(new TransactionHistory(transactionCounter++, stock, "Sale", quantitySold));
        System.out.println("Sale completed: " + sale);
    }

    public static void viewProductList() {
        System.out.println("\nProducts List:");
        if (products.isEmpty()) {
            System.out.println("No products available.");
        }
        for (Product product : products) {
            System.out.println(product);
        }
    }

    public static void viewStockList() {
        System.out.println("\nPractice.StockManagement.Stock List:");
        if (stocks.isEmpty()) {
            System.out.println("No stock entries.");
        }

        for (Stock stock : stocks) {
            System.out.println(stock);
            if (stock.quantity <= stock.thresholdQuantity) {
                System.out.println("ALERT: Low stock for product '" + stock.product.productName + "'. Please restock!");
            }
        }
    }

    public static void viewTransactionHistory() {
        System.out.println("\nTransaction History:");
        if (transactionHistoryList.isEmpty()) {
            System.out.println("No transactions found.");
        }
        for (TransactionHistory transaction : transactionHistoryList) {
            System.out.println(transaction);
        }
    }

    public static Product findProductById(int productId) {
        for (Product product : products) {
            if (product.productId == productId) {
                return product;
            }
        }
        return null;
    }

    public static Stock findStockByProductId(int productId) {
        for (Stock stock : stocks) {
            if (stock.product.productId == productId) {
                return stock;
            }
        }
        return null;
    }
}
class Product {
    int productId;
    String productName;
    double price;

    Supplier supplier;

    Product(int productId, String productName, double price, Supplier supplier) {
        this.productId = productId;
        this.productName = productName;
        this.price = price;
        this.supplier = supplier;
    }
    @Override
    public String toString() {
        return "Practice.StockManagement.Product ID: " + productId + ", Name: " + productName + ", Price: " + price;
    }
}
class Supplier {
    int supplierId;

    String supplierName;

    Supplier(int supplierId, String supplierName) {
        this.supplierId = supplierId;
        this.supplierName = supplierName;
    }
    @Override
    public String toString() {
        return "Practice.StockManagement.Supplier ID: " + supplierId + ", Name: " + supplierName;
    }

}
class Stock {
    int stockId;
    Product product;
    int quantity;

    int thresholdQuantity;

    Stock(int stockId, Product product, int quantity, int thresholdQuantity) {
        this.stockId = stockId;
        this.product = product;
        this.quantity = quantity;
        this.thresholdQuantity = thresholdQuantity;
    }

    public boolean checkStockAvailability(int quantityRequested) {
        return quantity >= quantityRequested;
    }

    public void updateStock(int quantitySold) {
        this.quantity -= quantitySold;
    }

    public void restock(int quantityAdded) {
        this.quantity += quantityAdded;
    }
    @Override
    public String toString() {
        return "Practice.StockManagement.Stock ID: " + stockId + ", Practice.StockManagement.Product: " + product.productName + ", Quantity: " + quantity + ", Threshold: " + thresholdQuantity;
    }

}
class Sales {
    int saleId;
    Product product;
    int quantitySold;

    double salePrice;

    Sales(int saleId, Product product, int quantitySold, double salePrice) {
        this.saleId = saleId;
        this.product = product;
        this.quantitySold = quantitySold;
        this.salePrice = salePrice;
    }
    @Override
    public String toString() {
        return "Sale ID: " + saleId + ", Practice.StockManagement.Product: " + product.productName + ", Quantity Sold: " + quantitySold + ", Total Sale Price: " + salePrice;
    }

}
class TransactionHistory {
    int transactionId;
    Stock stock;
    String transactionType;  // Sale, Restock, Update

    int quantityChanged;

    TransactionHistory(int transactionId, Stock stock, String transactionType, int quantityChanged) {
        this.transactionId = transactionId;
        this.stock = stock;
        this.transactionType = transactionType;
        this.quantityChanged = quantityChanged;
    }
    @Override
    public String toString() {
        return "Transaction ID: " + transactionId + ", Practice.StockManagement.Stock ID: " + stock.stockId + ", Type: " + transactionType + ", Quantity Changed: " + quantityChanged;
    }

}
