package Practice.sample;
//
//import java.util.*;
//public class RailwayBookingSystem {
//    public static void main(String[] args) {
//        initializeTrains();
//        Scanner scanner = new Scanner(System.in);
//
//        while (true) {
//            System.out.println("\n------ Railway Booking System ------");
//            System.out.println("1. Show Available Trains");
//            System.out.println("2. Show Available Coaches in a Train");
//            System.out.println("3. Book Ticket");
//            System.out.println("4. Cancel Ticket");
//            System.out.println("5. Check Booking Details");
//            System.out.println("6. Exit");
//            System.out.print("Select an option: ");
//
//            int choice = scanner.nextInt();
//            scanner.nextLine();
//
//            switch (choice) {
//                case 1:
//                    showAvailableTrains();
//                    break;
//                case 2:
//                    System.out.print("Enter Train Name: ");
//                    String trainName = scanner.nextLine();
//                    showAvailableCoaches(trainName);
//                    break;
//                case 3:
//                    bookTicket(scanner);
//                    break;
//                case 4:
//                    cancelTicket(scanner);
//                    break;
//                case 5:
//                    checkBookingDetails(scanner);
//                    break;
//                case 6:
//                    System.out.println("Thank you for using the Railway Booking System!");
//                    return;
//                default:
//                    System.out.println("Invalid option! Please try again.");
//            }
//        }
//    }
//    private static Map<String, Train> trains = new HashMap<>();
//    private static Random random = new Random();
//
//    private static void initializeTrains() {
//        Train express = new Train("Express");
//        express.addCoach(new Coach("Sleeper", 5));
//        express.addCoach(new Coach("3rd AC", 2));
//        express.addCoach(new Coach("2nd AC", 2));
//        express.addCoach(new Coach("1st AC", 1));
//        trains.put("Express", express);
//
//        Train superfast = new Train("Superfast");
//        superfast.addCoach(new Coach("Sleeper", 3));
//        superfast.addCoach(new Coach("3rd AC", 2));
//        trains.put("Superfast", superfast);
//    }
//
//    private static void showAvailableTrains() {
//        System.out.println("Available Trains:");
//        for (String trainName : trains.keySet()) {
//            System.out.println("- " + trainName);
//        }
//    }
//
//    private static void showAvailableCoaches(String trainName) {
//        Train train = trains.get(trainName);
//        if (train != null) {
//            train.showCoaches();
//        } else {
//            System.out.println("Train not found!");
//        }
//    }
//    private static void bookTicket(Scanner scanner) {
//        System.out.print("Enter Train Name: ");
//        String trainName = scanner.nextLine();
//        Train train = trains.get(trainName);
//        if (train == null) {
//            System.out.println("Train not found!");
//            return;
//        }
//        System.out.print("Enter Coach Type (Sleeper, 3rd AC, 2nd AC, 1st AC): ");
//        String coachType = scanner.nextLine();
//        System.out.print("Enter Passenger Name: ");
//        String name = scanner.nextLine();
//        System.out.print("Enter Passenger Age: ");
//        int age = scanner.nextInt();
//        scanner.nextLine(); // Consume newline
//        System.out.print("Enter Passenger Gender: ");
//        String gender = scanner.nextLine();
//        System.out.print("Enter Departure Station: ");
//        String departure = scanner.nextLine();
//        System.out.print("Enter Destination Station: ");
//        String destination = scanner.nextLine();
//        System.out.print("Enter Travel Date (DD-MM-YYYY): ");
//        String travelDate;
//        while (true) {
//            travelDate = scanner.nextLine();
//            if (travelDate.matches("\\d{2}-\\d{2}-\\d{4}")) {
//                break;
//            } else {
//                System.out.println("Invalid date format! Please enter in DD-MM-YYYY format:");
//            }
//        }
//        String pnr = generatePNR();
//
//        Passenger passenger = new Passenger(name, age, gender, pnr, departure, destination,travelDate);
//        train.bookTicket(coachType, passenger);
//    }
//
//    private static void cancelTicket(Scanner scanner) {
//        System.out.print("Enter PNR to cancel: ");
//        String pnr = scanner.nextLine();
//        boolean canceled = false;
//        for (Train train : trains.values()) {
//            if (train.cancelTicket(pnr)) {
//                canceled = true;
//                break;
//            }
//        }
//        if (!canceled) {
//            System.out.println("PNR not found.");
//        }
//    }
//
//    private static void checkBookingDetails(Scanner scanner) {
//        System.out.print("Enter PNR to check: ");
//        String pnr = scanner.nextLine();
//        for (Train train : trains.values()) {
//            Passenger passenger = train.getBookingDetails(pnr);
//            if (passenger != null) {
//                System.out.println("Booking Details: " + passenger);
//                return;
//            }
//        }
//        System.out.println("PNR not found.");
//    }
//    private static String generatePNR() {
//        String characters = "0123456789";
//        StringBuilder pnr = new StringBuilder();
//        Random random = new Random();
//
//        for (int i = 0; i < 8; i++) {
//            pnr.append(characters.charAt(random.nextInt(characters.length())));
//        }
//        return pnr.toString();
//    }
//}
//class Passenger {
//    private String name;
//    private int age;
//    private String gender;
//    private String pnr;
//    private String departure;
//    private String destination;
//
//    private String travelDate;
//
//    public Passenger(String name, int age, String gender, String pnr, String departure, String destination, String travelDate) {
//        this.name = name;
//        this.age = age;
//        this.gender = gender;
//        this.pnr = pnr;
//        this.departure = departure;
//        this.destination = destination;
//        this.travelDate = travelDate;
//    }
//
//    public String getPnr() {
//        return pnr;
//    }
//
//
//    public String getTravelDate() {
//        return travelDate;
//    }
//    @Override
//    public String toString() {
//        return "\n"+ "Passanger Name  =" + name + "\n" +
//                "Passange Age=" + age + "\n"+
//                "Passanger Gender=" + gender + "\n" +
//                "Passanger pnr=" + pnr + "\n" +
//                "Departure=" + departure + "\n" +
//                "Destination=" + destination + "\n" +
//                "Travel Date=" + travelDate + "\n" +
//                '}';
//    }
//}
//class Coach {
//    private String type;
//    private int capacity;
//    private Map<String, Passenger> bookings;
//    private Queue<Passenger> racList;
//    private Queue<Passenger> waitingList;
//    private static final int RAC_CAPACITY = 1;
//
//    private static final int WAITING_LIST_CAPACITY = 2;
//
//    public Coach(String type, int capacity) {
//        this.type = type;
//        this.capacity = capacity;
//        this.bookings = new HashMap<>();
//        this.racList = new LinkedList<>();
//        this.waitingList = new LinkedList<>();
//    }
//
//    public String getType() {
//        return type;
//    }
//
//    public int getAvailableSeats() {
//        return capacity - bookings.size();
//    }
//
//    public boolean bookTicket(Passenger passenger) {
//        if (getAvailableSeats() > 0) {
//            bookings.put(passenger.getPnr(), passenger);
//            System.out.println("Ticket booked successfully! PNR: " + passenger.getPnr());
//            return true;
//        } else if (racList.size() < RAC_CAPACITY) {
//            racList.add(passenger);
//            System.out.println("Added to RAC list. PNR: " + passenger.getPnr());
//            return true;
//        } else if (waitingList.size() < WAITING_LIST_CAPACITY) {
//            waitingList.add(passenger);
//            System.out.println("Added to Waiting list. PNR: " + passenger.getPnr());
//            return true;
//        } else {
//            System.out.println("No seats available in " + type);
//            return false;
//        }
//    }
//
//    public boolean cancelTicket(String pnr) {
//        if (bookings.containsKey(pnr)) {
//            bookings.remove(pnr);
//            System.out.println("Ticket with PNR " + pnr + " canceled successfully.");
//            if (!racList.isEmpty()) {
//                Passenger racPassenger = racList.poll();
//                bookings.put(racPassenger.getPnr(), racPassenger);
//                System.out.println("RAC passenger with PNR " + racPassenger.getPnr() + " moved to confirmed booking.");
//                if (!waitingList.isEmpty()) {
//                    Passenger waitPassenger = waitingList.poll();
//                    racList.add(waitPassenger);
//                    System.out.println("Waiting list passenger with PNR " + waitPassenger.getPnr() + " moved to RAC.");
//                }
//            }
//            return true;
//        }
//        return false;
//    }
//    public Passenger getBookingDetails(String pnr) {
//        if (bookings.containsKey(pnr)) {
//            return bookings.get(pnr);
//        }
//        for (Passenger p : racList) {
//            if (p.getPnr().equals(pnr)) {
//                return p;
//            }
//        }
//        for (Passenger p : waitingList) {
//            if (p.getPnr().equals(pnr)) {
//                return p;
//            }
//        }
//        return null;
//    }
//}
//class Train {
//    private String name;
//
//    private List<Coach> coaches;
//
//    public Train(String name) {
//        this.name = name;
//        this.coaches = new ArrayList<>();
//    }
//    public void addCoach(Coach coach) {
//        coaches.add(coach);
//    }
//    public void showCoaches() {
//        System.out.println("Available Coaches in " + name + ":");
//        for (Coach coach : coaches) {
//            System.out.println("- " + coach.getType() + ": " + coach.getAvailableSeats() + " seats available");
//        }
//    }
//    public boolean bookTicket(String coachType, Passenger passenger) {
//        for (Coach coach : coaches) {
//            if (coach.getType().equalsIgnoreCase(coachType)) {
//                return coach.bookTicket(passenger);
//            }
//        }
//        System.out.println("Coach type not found!");
//        return false;
//    }
//    public boolean cancelTicket(String pnr) {
//        for (Coach coach : coaches) {
//            if (coach.cancelTicket(pnr)) {
//                return true;
//            }
//        }
//        return false;
//    }
//    public Passenger getBookingDetails(String pnr) {
//        for (Coach coach : coaches) {
//            Passenger passenger = coach.getBookingDetails(pnr);
//            if (passenger != null) {
//                return passenger;
//            }
//        }
//        return null;
//    }
//}
import java.util.*;

class Train {
    String trainNumber;
    String trainName;
    String source;
    String destination;
    Map<String, Integer> availableTickets;
    List<Booking> bookings;

    public Train(String trainNumber, String trainName, String source, String destination, Map<String, Integer> availableTickets) {
        this.trainNumber = trainNumber;
        this.trainName = trainName;
        this.source = source;
        this.destination = destination;
        this.availableTickets = availableTickets;
        this.bookings = new ArrayList<>();
    }
}

class Booking {
    String pnr;
    Train train;
    User user;
    int numTickets;
    String classType;
    String status;

    public Booking(String pnr, Train train, User user, int numTickets, String classType, String status) {
        this.pnr = pnr;
        this.train = train;
        this.user = user;
        this.numTickets = numTickets;
        this.classType = classType;
        this.status = status;
    }
}
class User {
    String userId;
    String name;
    String email;
    List<Booking> bookingHistory;

    public User(String userId, String name, String email) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.bookingHistory = new ArrayList<>();
    }
}
class TrainService {
    Map<String, Train> trains = new HashMap<>();
    public void showTrainDetails() {
        System.out.println("\nAvailable Trains:");
        for (Train train : trains.values()) {
            System.out.println("Train Number: " + train.trainNumber + ", Name: " + train.trainName);
            System.out.println("Route: " + train.source + " -> " + train.destination);
            System.out.println("Available Tickets: " + train.availableTickets);
            System.out.println("------------------------");
        }
    }
    public int showAvailableTickets(String trainNumber, String classType) {
        Train train = trains.get(trainNumber);
        return train != null ? train.availableTickets.getOrDefault(classType, 0) : 0;
    }
}
class BookingService {
    Map<String, Booking> bookings = new HashMap<>();
    TrainService trainService;

    public BookingService(TrainService trainService) {
        this.trainService = trainService;
    }
    public String bookTicket(String trainNumber, User user, int numTickets, String classType) {
        Train train = trainService.trains.get(trainNumber);
        if (train == null) {
            System.out.println("Train not found!");
            return null;
        }

        int available = train.availableTickets.getOrDefault(classType, 0);
        if (available < numTickets) {
            System.out.println("Not enough tickets available!");
            return null;
        }
        train.availableTickets.put(classType, available - numTickets);
        String pnr = generatePNR(trainNumber);
        Booking booking = new Booking(pnr, train, user, numTickets, classType, "Booked");
        train.bookings.add(booking);
        user.bookingHistory.add(booking);
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
    }

    public String generatePNR(String trainNumber) {
        String characters = "0123456789";
        StringBuilder pnr = new StringBuilder();
        Random random = new Random();

        for (int i = 0; i < 8; i++) {
            pnr.append(characters.charAt(random.nextInt(characters.length())));
        }
        return pnr.toString();
    }

    public void showBookingHistory(User user) {
        System.out.println("\nBooking History:");
        for (Booking booking : user.bookingHistory) {
            System.out.println("PNR: " + booking.pnr + " | Train: " + booking.train.trainName + " | Class: " + booking.classType + " | Status: " + booking.status);
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
public class RailwayApp {
    public static void main(String[] args) {
        TrainService trainService = new TrainService();
        BookingService bookingService = new BookingService(trainService);
        ChartService chartService = new ChartService();

        Map<String, Integer> vaigai = new HashMap<>();
        vaigai.put("Sleeper", 100);
        vaigai.put("3rd AC", 50);
        vaigai.put("2nd AC", 30);
        vaigai.put("1st AC", 10);
        Map<String,Integer> pandian=new HashMap<>();
        pandian.put("Sleeper", 50);
        pandian.put("3rd AC", 10);
        pandian.put("2nd AC", 8);
        pandian.put("1st AC", 5);
        Train train = new Train("52341", "Vaigai", "Chennai", "Madurai", vaigai);
        Train train1=new Train("16735","Pandian","Chennai","Madurai",pandian);
        trainService.trains.put(train.trainNumber, train);
        trainService.trains.put(train1.trainNumber,train1);

        User user = new User("U001", "Bala", "bala@example.com");
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n=== Railway Ticket Booking System ===");
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
                    trainService.showTrainDetails();
                    break;
                case 2:
                    System.out.print("Enter Train Number: ");
                    String trainNumber = sc.next();
                    System.out.print("Enter Class (Sleeper/3rd AC/2nd AC/1st AC): ");
                    String classType = sc.next();
                    int available = trainService.showAvailableTickets(trainNumber, classType);
                    System.out.println("Available Tickets: " + available);
                    break;
                case 3:
                    System.out.print("Enter Train Number: ");
                    trainNumber = sc.next();
                    System.out.print("Enter Number of Tickets: ");
                    int numTickets = sc.nextInt();
                    System.out.print("Enter Class (Sleeper/3rd AC/2nd AC/1st AC): ");
                    classType = sc.next();
                    bookingService.bookTicket(trainNumber, user, numTickets, classType);
                    break;
                case 4:
                    System.out.print("Enter PNR: ");
                    String pnr = sc.next();
                    bookingService.cancelTicket(pnr);
                    break;
                case 5:
                    bookingService.showBookingHistory(user);
                    break;
                case 6:
                    System.out.print("Enter Train Number: ");
                    trainNumber = sc.next();
                    Train t = trainService.trains.get(trainNumber);
                    if (t != null) {
                        chartService.printChart(t);
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
