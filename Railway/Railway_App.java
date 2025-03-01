package Practice.Railway;

import java.util.*;

class Train {
    String trainNumber;
    String trainName;
    String source;
    String destiny;
    Map<String, Integer> availableTickets;
    List<Booking> bookings;
    Queue<Booking> racList;
    Queue<Booking> waitingList;
    final int RAC_LIMIT = 5;
    final int WAITING_LIST_LIMIT = 5;

    public Train(String trainNumber, String trainName, String source, String destiny, Map<String, Integer> availableTickets) {
        this.trainNumber = trainNumber;
        this.trainName = trainName;
        this.source = source;
        this.destiny = destiny;
        this.availableTickets = availableTickets;
        this.bookings = new ArrayList<>();
        this.racList = new LinkedList<>();
        this.waitingList = new LinkedList<>();
    }

    public boolean hasAvailableTickets(String classType, int numTickets) {
        return availableTickets.getOrDefault(classType, 0) >= numTickets;
    }
    public boolean hasRACAvailability() {
        return racList.size() < RAC_LIMIT;
    }

    public boolean hasWaitingListAvailability() {
        return waitingList.size() < WAITING_LIST_LIMIT;
    }
}
class Booking {
    String pnr;
    Train train;
    List<Passenger> passengers;
    int numTickets;
    String classType;
    String status;

    public Booking(String pnr, Train train, List<Passenger> passengers, int numTickets, String classType, String status) {
        this.pnr = pnr;
        this.train = train;
        this.passengers = passengers;
        this.numTickets = numTickets;
        this.classType = classType;
        this.status = status;
    }
}
class Passenger {
    String name;
    int age;
    String gender;

    public Passenger(String name, int age, String gender) {
        this.name = name;
        this.age = age;
        this.gender = gender;
    }
}
class TrainService {
    Map<String, Train> trains = new HashMap<>();

    public void showtraindetails() {
        System.out.println("\nAvailable Trains:");
        for (Train train : trains.values()) {
            System.out.println("Train Number: " + train.trainNumber + ", Name: " + train.trainName);
            System.out.println("Route: " + train.source + " -> " + train.destiny);
            System.out.println("Available Tickets: " + train.availableTickets);
            System.out.println("------------------------");
        }
    }

    public int showavailabletickets(String trainNumber, String classType) {
        Train train = trains.get(trainNumber);
        return train != null ? train.availableTickets.getOrDefault(classType, 0) : 0;
    }
}
class bookingService {
    Map<String, Booking> bookings = new HashMap<>();
    TrainService trainService;

    public bookingService(TrainService trainService) {
        this.trainService = trainService;
    }
    public String bookTicket(String trainNumber, List<Passenger> passengers, String classType) {
        Train train = trainService.trains.get(trainNumber);
        if (train == null) {
            System.out.println("Train not found!");
            return null;
        }
        int numTickets = passengers.size();
        String pnr = generatePNR();
        Booking booking = new Booking(pnr, train, passengers, numTickets, classType, "Booked");

        if (train.hasAvailableTickets(classType, numTickets)) {
            train.availableTickets.put(classType, train.availableTickets.get(classType) - numTickets);
            booking.status = "Confirmed";
        } else if (train.hasRACAvailability()) {
            booking.status = "RAC";
            train.racList.offer(booking);
        } else if (train.hasWaitingListAvailability()) {
            booking.status = "Waiting List";
            train.waitingList.offer(booking);
        } else {
            System.out.println("No tickets available!");
            return null;
        }
        train.bookings.add(booking);
        bookings.put(pnr, booking);
        System.out.println("Ticket Booked! Your PNR is: " + pnr);
        return pnr;
    }
    public void cancelTicket(String pnr) {
        Booking booking = bookings.get(pnr);
        if (booking == null || booking.status.equals("Cancelled")) {
            System.out.println("Invalid PNR or Ticket already cancelled!");
            return;
        }
        Train train = booking.train;
        train.availableTickets.put(booking.classType, train.availableTickets.get(booking.classType) + booking.numTickets);
        booking.status = "Cancelled";
        System.out.println("Ticket Cancelled Successfully!");
        if (!train.racList.isEmpty()) {
            Booking racBooking = train.racList.poll();
            racBooking.status = "Confirmed";
            train.availableTickets.put(racBooking.classType, train.availableTickets.get(racBooking.classType) - racBooking.numTickets);
            System.out.println("PNR: " + racBooking.pnr + " moved from RAC to Confirmed.");
        }

        if (!train.waitingList.isEmpty() && train.hasRACAvailability()) {
            Booking wlBooking = train.waitingList.poll();
            wlBooking.status = "RAC";
            train.racList.offer(wlBooking);
            System.out.println("PNR: " + wlBooking.pnr + " moved from Waiting List to RAC.");
        }
    }
    public String generatePNR() {
        String characters = "0123456789";
        StringBuilder pnr = new StringBuilder();
        Random random = new Random();

        for (int i = 0; i <= 6; i++) {
            pnr.append(characters.charAt(random.nextInt(characters.length())));
        }
        return pnr.toString();
    }
    public void showBookingHistory(String passengerName) {
        System.out.println("\nBooking History:");
        for (Booking booking : bookings.values()) {
            for (Passenger passenger : booking.passengers) {
                if (passenger.name.equalsIgnoreCase(passengerName)) {
                    System.out.println("PNR: " + booking.pnr + " | Train: " + booking.train.trainName + " | Class: " + booking.classType + " | Status: " + booking.status);
                }
            }
        }
    }
}
class ChartService {
    public void printChart(Train train) {
        System.out.println("\nPassenger Chart for Train: " + train.trainName);
        for (Booking booking : train.bookings) {
            System.out.println("PNR: " + booking.pnr + " | Class: " + booking.classType + " | Status: " + booking.status);
        }
    }
}
public class Railway_App {
    public static void main(String[] args) {
        TrainService trainService = new TrainService();
        bookingService bookingService = new bookingService(trainService);
        ChartService chartService = new ChartService();

        Map<String, Integer> vaigai = new HashMap<>();
        vaigai.put("Sleeper", 10);
        vaigai.put("3rd AC", 5);
        vaigai.put("2nd AC", 3);
        vaigai.put("1st AC", 2);

        Map<String, Integer> pandian = new HashMap<>();
        pandian.put("Sleeper", 10);
        pandian.put("3rd AC", 5);
        pandian.put("2nd AC", 3);
        pandian.put("1st AC", 2);

        Train train1 = new Train("52341", "Vaigai", "Chennai", "Madurai", vaigai);
        Train train2 = new Train("16735", "Pandian", "Chennai", "Madurai", pandian);

        trainService.trains.put(train1.trainNumber, train1);
        trainService.trains.put(train2.trainNumber, train2);

        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n======= Railway Ticket Booking System ===");
            System.out.println("1. Show Train Details");
            System.out.println("2. Show Available Tickets");
            System.out.println("3. Book Ticket");
            System.out.println("4. Cancel Ticket");
            System.out.println("5. Show Booking History");
            System.out.println("6. Print Passenger Chart");
            System.out.println("7. Exit");
            System.out.print("Enter your choice: ");
            int choice = sc.nextInt();

            switch (choice) {
                case 1:
                    trainService.showtraindetails();
                    break;
                case 2:
                    System.out.print("Enter Train Number: ");
                    String trainNumber = sc.next();
                    System.out.print("Enter Class (Sleeper/3rd AC/2nd AC/1st AC): ");
                    String classType = sc.next();
                    int available = trainService.showavailabletickets(trainNumber, classType);
                    System.out.println("Available Tickets: " + available);
                    break;
                case 3:
                    System.out.print("Enter Train Number: ");
                    trainNumber = sc.next();
                    List<Passenger> passengers = new ArrayList<>();
                    while (true) {
                        System.out.print("Enter Passenger Name: ");
                        String name = sc.next();
                        System.out.print("Enter Age: ");
                        int age = sc.nextInt();
                        System.out.print("Enter Gender (M/F): ");
                        String gender = sc.next();
                        passengers.add(new Passenger(name, age, gender));

                        System.out.print("Do you want to add more passengers? (yes/no): ");
                        String more = sc.next();
                        if (more.equalsIgnoreCase("no")) {
                            break;
                        }
                    }
                    System.out.print("Enter Class (Sleeper/3rd AC/2nd AC/1st AC): ");
                    classType = sc.next();
                    bookingService.bookTicket(trainNumber, passengers, classType);
                    break;
                case 4:
                    System.out.print("Enter PNR: ");
                    String pnr = sc.next();
                    bookingService.cancelTicket(pnr);
                    break;
                case 5:
                    System.out.print("Enter Passenger Name: ");
                    String passengerName = sc.next();
                    bookingService.showBookingHistory(passengerName);
                    break;
                case 6:
                    System.out.print("Enter Train Number: ");
                    trainNumber = sc.next();
                    Train train = trainService.trains.get(trainNumber);
                    if (train != null) {
                        chartService.printChart(train);
                    } else {
                        System.out.println("Train not found!");
                    }
                    break;
                case 7:
                    System.out.println("Exiting the system. Goodbye!");
                    sc.close();
                    return;
                default:
                    System.out.println("Invalid choice! Please try again.");
            }
        }
    }
}