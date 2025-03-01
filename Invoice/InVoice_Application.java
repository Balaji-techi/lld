package Practice.Invoice;

import java.util.*;
class InvoiceService {
    private final Map<String, Customer> customers = new HashMap<>();
    private final Map<String, Invoice> invoices = new HashMap<>();

    public void addCustomer(String id, String name) {
        customers.put(id, new Customer(id, name));
        System.out.println("Customer has been successfully added!");
    }

    public void addInvoice(String id, String customerId) {
        if (!customers.containsKey(customerId)) {
            System.out.println("Error: Customer with ID " + customerId + " not found!. Please check the customer ID.");
            return;
        }
        invoices.put(id, new Invoice(id, customerId));
        System.out.println("Invoice has been successfully created! with " + customerId);
    }

    public void addItemToInvoice(String invoiceId, String itemName, int quantity, double price) {
        Invoice invoice = invoices.get(invoiceId);
        if (invoice == null) {
            System.out.println("Error: Invoice with ID " + invoiceId + " not found! Please check the invoice ID.");
            return;
        }
        invoice.items.add(new Item(itemName, quantity, price));
        System.out.println("IItem has been successfully added to the invoice. " + itemName);
    }

    public void listAllCustomers() {
        if (customers.isEmpty()) {
            System.out.println("No customers found in the system.");
            return;
        }
        System.out.println("\n***** List of Customers *******");
        for (Customer customer : customers.values()) {
            System.out.println("ID: " + customer.id + ", Name: " + customer.name);
        }    }

    public void listAllInvoices() {
        if (invoices.isEmpty()) {
            System.out.println("No invoices available in the system.");
            return;
        }
        System.out.println("\n****** List of All Invoices ******");
        for (Invoice invoice : invoices.values()) {
            System.out.println("Invoice ID: " + invoice.id + ", Customer ID: " + invoice.customerId);
        }
    }

    public void listInvoicesOfCustomer(String customerId) {
        boolean found = false;
        System.out.println("\n**** Invoices for Customer ID: " + customerId + " **** ");
        for (Invoice invoice : invoices.values()) {
            if (invoice.customerId.equals(customerId)) {
                System.out.printf("Invoice ID: ", invoice.id);
                found = true;
            }
        }
        if (!found) {
            System.out.println("No invoices found for this customer.");
        }
    }

    public void displayInvoiceDetails(String invoiceId) {
        Invoice invoice = invoices.get(invoiceId);
        if (invoice == null) {
            System.out.println("Error: Invoice with ID " + invoiceId + " not found!. Please check the invoice ID.");
            return;
        }
        System.out.println("--- Invoice Details ---");
        System.out.println("Invoice ID: " + invoice.id);
        System.out.println("Customer ID: " + invoice.customerId);
        System.out.println("Items in the Invoice:");
        for (Item item : invoice.items) {
            System.out.printf(" - %s: %d x %.2f = %.2f%n",
                    item.name, item.quantity, item.price, item.quantity * item.price);
        }
        System.out.printf("Total Amount: %.2f%n", invoice.calculateTotal());
    }
}

class Customer {
    String id;
    String name;

    Customer(String id, String name) {
        this.id = id;
        this.name = name;
    }
}

class Item {
    String name;
    int quantity;
    double price;

    Item(String name, int quantity, double price) {
        this.name = name;
        this.quantity = quantity;
        this.price = price;
    }

    double calculateTotal() {
        return quantity * price;
    }
}

class Invoice {
    String id;
    String customerId;
    List<Item> items;

    Invoice(String id, String customerId) {
        this.id = id;
        this.customerId = customerId;
        this.items = new ArrayList<>();
    }

    double calculateTotal() {
        return items.stream().mapToDouble(Item::calculateTotal).sum();
    }
}

public class InVoice_Application {
    private static final Scanner scanner = new Scanner(System.in);
    private static final InvoiceService service = new InvoiceService();

    public static void main(String[] args) {
        while (true) {
            displayMenu();
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> addCustomer();
                case 2 -> addInvoice();
                case 3 -> addItemsToInvoice();
                case 4 -> service.listAllCustomers();
                case 5 -> service.listAllInvoices();
                case 6 -> listInvoicesOfCustomer();
                case 7 -> displayInvoiceDetails();
                case 8 -> {
                    System.out.println("Thank you for using the system. Goodbye!");
                    return;
                }
                default -> System.out.println("Invalid option. Please try again.");
            }
        }
    }
    private static void displayMenu() {
        System.out.println("\n--- Welcome to the Invoice Management System ---");
        System.out.println("1. Add a new customer");
        System.out.println("2. Create a new invoice");
        System.out.println("3. Add items to an existing invoice");
        System.out.println("4. View all customers");
        System.out.println("5. View all invoices");
        System.out.println("6. View invoices for a specific customer");
        System.out.println("7. View detailed information of an invoice");
        System.out.println("8. Exit the system");
        System.out.print("Please select an option: ");

    }
    private static void addCustomer() {
        System.out.print("Enter the customer ID: ");
        String id = scanner.nextLine();
        System.out.print("Enter the customer name: ");
        String name = scanner.nextLine();
        service.addCustomer(id, name);
    }
    private static void addInvoice() {
        System.out.print("Enter the invoice ID: ");
        String id = scanner.nextLine();
        System.out.print("Enter the customer ID: ");
        String customerId = scanner.nextLine();
        service.addInvoice(id, customerId);
    }
    private static void addItemsToInvoice() {
        System.out.print("Enter the invoice ID: ");
        String invoiceId = scanner.nextLine();
        System.out.print("Enter the item name: ");
        String itemName = scanner.nextLine();
        System.out.print("Enter the item quantity: ");
        int quantity = scanner.nextInt();
        System.out.print("Enter the item price: ");
        double price = scanner.nextDouble();
        scanner.nextLine();
        service.addItemToInvoice(invoiceId, itemName, quantity, price);
    }
    private static void listInvoicesOfCustomer() {
        System.out.print("Enter the customer ID: ");
        String customerId = scanner.nextLine();
        service.listInvoicesOfCustomer(customerId);
    }
    private static void displayInvoiceDetails() {
        System.out.print("Enter the  invoice ID: ");
        String invoiceId = scanner.nextLine();
        service.displayInvoiceDetails(invoiceId);
    }
}