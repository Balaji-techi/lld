package Practice.Food_Delivery_Application;

import java.util.*;

public class Food_Delivery {
    public static void main(String[] args) {
        while (true) {
            System.out.println("\nMenu:");
            System.out.println("1. Enter New Booking");
            System.out.println("2. Show Delivery History");
            System.out.println("3. Exit");
            System.out.print("Choose an option (1, 2, or 3): ");
            int option = scanner.nextInt();
            scanner.nextLine();
            switch (option) {
                case 1:
                    handleNewBooking();
                    break;
                case 2:
                    showDeliveryHistory();
                    break;
                case 3:
                    System.exit(0);
                default:
                    System.out.println("Invalid option, please try again.");
            }
        }
    }

    static Scanner scanner = new Scanner(System.in);
    static DeliveryExecutive[] executives = new DeliveryExecutive[5];

    static List<Booking> bookingHistory = new ArrayList<>();

    static {
        for (int i = 0; i < 5; i++) {
            executives[i] = new DeliveryExecutive("DE" + (i + 1));
        }
    }

    private static void handleNewBooking() {
        System.out.print("Enter Customer ID: ");
        int customerId = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Restaurant: ");
        String restaurant = scanner.nextLine();

        System.out.print("Destination: ");
        String destination = scanner.nextLine();

        System.out.print("Time (e.g., 9:00 AM): ");
        String time = scanner.nextLine();

        Booking newBooking = new Booking(customerId, restaurant, destination, time);
        bookingHistory.add(newBooking);

        DeliveryExecutive assignedExecutive = assignDeliveryExecutive(newBooking);

        System.out.println("Handling booking: " + newBooking);
        System.out.println("Assigned Delivery Executive: " + assignedExecutive.getId());
    }

    private static DeliveryExecutive assignDeliveryExecutive(Booking newBooking) {
        for (DeliveryExecutive executive : executives) {
            for (Booking booking : executive.getBookings()) {
                if (booking.getRestaurant().equals(newBooking.getRestaurant()) &&
                        booking.getDestination().equals(newBooking.getDestination()) &&
                        isWithin15Minutes(booking.getTime(), newBooking.getTime())) {

                    executive.addBooking(newBooking, 5);
                    executive.incrementTripCount();
                    return executive;
                }
            }
        }
        for (DeliveryExecutive executive : executives) {
            if (executive.getBookings().size() == 0) {
                executive.addBooking(newBooking, 50);
                executive.addAllowance();
                executive.incrementTripCount();
                return executive;
            }
        }
        DeliveryExecutive leastBusyExecutive = executives[0];
        for (DeliveryExecutive executive : executives) {
            if (executive.getBookings().size() < leastBusyExecutive.getBookings().size()) {
                leastBusyExecutive = executive;
            }
        }
        leastBusyExecutive.addBooking(newBooking, 50);
        leastBusyExecutive.addAllowance();
        leastBusyExecutive.incrementTripCount();
        return leastBusyExecutive;
    }

    private static boolean isWithin15Minutes(String time1, String time2) {
        int totalMinutes1 = convertToMinutes(time1);
        int totalMinutes2 = convertToMinutes(time2);

        int diffInMinutes = Math.abs(totalMinutes1 - totalMinutes2);
        return diffInMinutes <= 15;
    }

    private static int convertToMinutes(String time) {
        String[] parts = time.split(" ");
        String[] hourMinute = parts[0].split(":");
        int hours = Integer.parseInt(hourMinute[0]);
        int minutes = Integer.parseInt(hourMinute[1]);
        if (parts.length > 1 && parts[1].equalsIgnoreCase("PM") && hours < 12) {
            hours += 12;
        } else if (parts.length > 1 && parts[1].equalsIgnoreCase("AM") && hours == 12) {
            hours = 0;
        }

        return hours * 60 + minutes;
    }

    private static void showDeliveryHistory() {
        System.out.println("\nExecutive Summary:");
        for (DeliveryExecutive executive : executives) {
            System.out.println(executive);
        }
    }
}