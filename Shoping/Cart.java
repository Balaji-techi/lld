package Practice.Shoping;

import java.util.*;
public class Cart {

    String category;
    String brand;
    String model;
    int quantity;

    public Cart(String category,String brand,String model,int quantity){
        this.category = category;
        this.brand = brand;
        this.model = model;
        this.quantity = quantity;
    }
}

class CartArea{

    private static List<Cart> cart = new ArrayList<>();
    private static Map<Integer, List<Cart>> orderHistory = new HashMap<>();
    static Scanner scan = new Scanner (System.in);
    public static void addCart(Cart obj) {
        cart.add(obj);
    }
    public static void printAllOrderHistory() {
        System.out.println("Id \t\t Category \t\t Brand \t\t Model \t\t Quantity \t\tBillAmount");
        for (Map.Entry<Integer, List<Cart>> entry : orderHistory.entrySet()) {
            int id = entry.getKey();
            List<Cart> items = entry.getValue();

            for (Cart item : items) {
                System.out.println(id+"\t\t"+item.category+"\t\t"+item.brand+"\t\t"+item.model+"\t\t"+item.quantity+"\t\t"+
                        InventoryDB.getPrice(item.category, item.brand, item.model)*item.quantity);
            }
        }

    }
    public static void addOrderHistory(int id, Cart order) {
        if (!orderHistory.containsKey(id)) {
            orderHistory.put(id, new ArrayList<>());
        }
        orderHistory.get(id).add(order);
    }

    public static void OrderDone() {
        int id = CustomerDB.currCusId;
        for(Cart order : cart) {
            CartArea.addOrderHistory(id, order);
        }

        cart.clear();
    }
    public static void invoice() throws InterruptedException {
        System.out.println("Your Cart is being Prepared ");
        Thread.sleep(1000);
        System.out.println("---------------------------------------------------------------");
        System.out.println("| Category \t brand \t model \t quantity \t total |");
        int total = 0;
        for(Cart orders : cart) {
            System.out.println("| "+orders.category+"\t"+orders.brand+"\t"+orders.model+"\t"+orders.quantity+"\t"+
                    (InventoryDB.getPrice(orders.category, orders.brand, orders.model))*orders.quantity+" |");
            total += InventoryDB.getPrice(orders.category, orders.brand, orders.model)*orders.quantity;
        }
        System.out.println("---------------------------------------------------------------");
        System.out.println();
        System.out.println("You have to pay :"+total);
        System.out.println("Just enter the same amount");

        int amount = scan.nextInt();
        if(amount >= total) {
            System.out.println("Hurrayyyy...!  Order is Done....!");
            for(Cart orders : cart) {
                InventoryDB.setInventory(orders.category,orders.brand,orders.model,orders.quantity);
            }
            CartArea.OrderDone();
        } else {
            System.out.println("You entered the low amount");
        }
    }
}
