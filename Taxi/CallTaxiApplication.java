package Practice.Taxi;

import java.util.*;

public class CallTaxiApplication {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        CallTaxiService taxiService = new CallTaxiService(4);
        boolean continueBooking = true;

        while (continueBooking) {
            System.out.println("\n1. Book a Taxi");
            System.out.println("2. Cancel a Ride");
            System.out.println("3. View Ride History");
            System.out.println("4. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            switch (choice) {
                case 1 -> {
                    System.out.print("Enter Customer ID: ");
                    int customerId = scanner.nextInt();
                    System.out.print("Enter Pickup Point (A-F): ");
                    String pickupPoint = scanner.next().toUpperCase();
                    System.out.print("Enter Drop Point (A-F): ");
                    String dropPoint = scanner.next().toUpperCase();
                    System.out.print("Enter Pickup Time (in hours, e.g. 9): ");
                    int pickupTime = scanner.nextInt();

                    System.out.println(taxiService.bookTaxi(customerId, pickupPoint, dropPoint, pickupTime));
                }
                case 2 -> {
                    System.out.print("Enter Customer ID: ");
                    int customerId = scanner.nextInt();
                    System.out.print("Enter Booking ID to cancel: ");
                    int bookingId = scanner.nextInt();
                    System.out.println(taxiService.cancelBooking(customerId, bookingId));
                }
                case 3 -> {
                    System.out.print("Enter Customer ID: ");
                    int customerId = scanner.nextInt();
                    taxiService.displayCustomerHistory(customerId);
                }
                case 4 -> {
                    continueBooking = false;
                    System.out.println("Thank you for using the Call Taxi Service!");
                }
                default -> System.out.println("Invalid option. Please try again.");
            }
        }
        scanner.close();
    }
}

class Taxi {
    private int taxiId;
    private String currentLocation;
    private boolean isFree;
    private double earnings;
    private List<Booking> bookings;

    public Taxi(int taxiId) {
        this.taxiId = taxiId;
        this.currentLocation = currentLocation;
        this.isFree = true;
        this.earnings = 0;
        this.bookings = new ArrayList<>();
    }
    public boolean isFree(int reqpicTime){
       if (bookings.isEmpty())return true;
       Booking lastBooking=bookings.get(bookings.size()-1);
       return lastBooking.getDropTime()<=reqpicTime;
    }
    double calculateDistance(String fromPoint,String toPoint){
        Map<String,Integer> map=Map.of("A",0,"B",1,"C",2,"D",3,"E",4);
        return Math.abs(map.get(fromPoint)-map.get(toPoint))*15;
    }
    public double calculateFare(double distance){
        if (distance<=5)return 100;
        else {
            double etraDistance = distance - 5;
            return 100;
        }
    }
    public void bookTaxi(int customerId, String pickupPoint, String dropPoint, int pickupTime) {
        double distance = calculateDistance(currentLocation, pickupPoint)
                + calculateDistance(pickupPoint, dropPoint);
        double amount = calculateFare(distance);
        int travelTime = (int) (distance / 15);
        int dropTime = pickupTime + travelTime;

        this.currentLocation = dropPoint;
        this.isFree = false;
        Booking booking = new Booking(bookings.size() + 1, customerId, pickupPoint, dropPoint, pickupTime, dropTime, amount);
        bookings.add(booking);
        earnings += amount;
    }

    public int getTaxiId() {
        return taxiId;
    }

    public double getEarnings() {
        return earnings;
    }

    public List<Booking> getBookings() {
        return bookings;
    }

    public String getCurrentLocation() {
        return currentLocation;
    }
}

class Booking {
    private int bookingId;
    private int customerId;
    private String from;
    private String to;
    private int pickupTime;
    private int dropTime;
    private double amount;

    public Booking(int bookingId, int customerId, String from, String to, int pickupTime, int dropTime, double amount) {
        this.bookingId = bookingId;
        this.customerId = customerId;
        this.from = from;
        this.to = to;
        this.pickupTime = pickupTime;
        this.dropTime = dropTime;
        this.amount = amount;
    }

    public int getBookingId() {
        return bookingId;
    }

    public int getDropTime() {
        return dropTime;
    }

    public double getAmount() {
        return amount;
    }

    @Override
    public String toString() {
        return bookingId + "\t" + customerId + "\t" + from + "\t" + to + "\t" + pickupTime + "\t" + dropTime + "\t" + amount;
    }
}

class CallTaxiService {
    private List<Taxi> taxis;
    private Map<Integer, List<Booking>> customerHistory;

    public CallTaxiService(int numTaxis) {
        taxis = new ArrayList<>();
        customerHistory = new HashMap<>();
        for (int i = 0; i < numTaxis; i++) {
            taxis.add(new Taxi(i + 1));
        }
    }

    public String bookTaxi(int customerId, String pickupPoint, String dropPoint, int pickupTime) {
        Taxi nearestTaxi = null;
        double minDistance = Double.MAX_VALUE;

        for (Taxi taxi : taxis) {
            if (taxi.isFree(pickupTime)) {
                double distanceToPickup = taxi.calculateDistance(taxi.getCurrentLocation(), pickupPoint);
                if (distanceToPickup < minDistance) {
                    minDistance = distanceToPickup;
                    nearestTaxi = taxi;
                }
            }
        }

        if (nearestTaxi != null) {
            nearestTaxi.bookTaxi(customerId, pickupPoint, dropPoint, pickupTime);
            customerHistory.putIfAbsent(customerId, new ArrayList<>());
            customerHistory.get(customerId).add(nearestTaxi.getBookings().get(nearestTaxi.getBookings().size() - 1));
            return "Taxi-" + nearestTaxi.getTaxiId() + " is allotted.\n";
        } else {
            return "Booking rejected. No taxis available.";
        }
    }

    public String cancelBooking(int customerId, int bookingId) {
        List<Booking> bookings = customerHistory.get(customerId);
        if (bookings != null) {
            for (Booking booking : bookings) {
                if (booking.getBookingId() == bookingId) {
                    bookings.remove(booking);
                    return "Booking canceled. Cancellation charge: ₹20";
                }
            }
        }
        return "Booking not found for cancellation.";
    }

    public void displayCustomerHistory(int customerId) {
        List<Booking> bookings = customerHistory.get(customerId);
        if (bookings != null && !bookings.isEmpty()) {
            System.out.println("Booking History for Customer ID: " + customerId);
            System.out.println("ID\tCustomer\tFrom\tTo\tPickup\tDrop\tAmount");
            for (Booking booking : bookings) {
                System.out.println(booking);
            }
        } else {
            System.out.println("No bookings found for Customer ID: " + customerId);
        }
    }
}