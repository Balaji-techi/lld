package Practice.Shoping;

import java.util.*;
public class Purchase {

    public static void buy() throws InterruptedException {
        Scanner scan = new Scanner(System.in);
        Map<String, List<Inventory>> inventoryDetails = InventoryDB.getInventory();

        System.out.println("-----------------------");
        System.out.println("| Choose the category |");
        System.out.println("-----------------------");
        for (Map.Entry<String, List<Inventory>> entry : inventoryDetails.entrySet()){
            System.out.println(entry.getKey());
        }

        String choice = scan.next();
        List<Inventory> items = null;

        for (Map.Entry<String, List<Inventory>> entry : inventoryDetails.entrySet()){
            if(entry.getKey().equals(choice)) {
                items = entry.getValue();
            }
        }
        System.out.println("------------------------------------------");
        System.out.println("| Brand \t Model \t\t  Price |");
        System.out.println("------------------------------------------");
        for (Inventory item : items) {
            System.out.println("| "+item.brand+"\t\t"+item.model+"\t\t"+item.price+" |");
        }
        System.out.println("------------------------------------------");

        boolean done = false;
        while(!done) {

            System.out.println("Enter the brand");
            String brand = scan.next();
            System.out.println("Enter the Model");
            String model = scan.next();
            System.out.println("How many quantity");
            int quantity = scan.nextInt();

            boolean available = InventoryDB.checkStock(choice,brand,model,quantity);

            if(available) {
                Cart obj = new Cart(choice,brand,model,quantity);
                CartArea.addCart(obj);

                System.out.println("Still want to purchase?....(yes/no)");
                String response = scan.next();

                if(response.equals("no")) { done = true ; }
            } else {
                System.out.println("Stock is less...");
                System.out.println("Still want to purchase?....(yes/no)");
                String response = scan.next();

                if(response.equals("no")) { done = true ; }
            }
        }
        CartArea.invoice();
    }
}