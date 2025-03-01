package Practice.Flight;

import java.util.*;

class Flight {
    private String flightName;
    private int availableSeats;
    private int ticketPrice;
    private Map<String, Passenger> bookings;
    private int bookingCounter;

    public Flight(String flightName) {
        this.flightName = flightName;
        this.availableSeats = 50;
        this.ticketPrice = 5000;
        this.bookings = new HashMap<>();
        this.bookingCounter = 0;
    }

    public String bookTickets(String passengerName, int age, int seats) {
        if (seats <= availableSeats) {
            bookingCounter++;
            String bookingId = "T" + bookingCounter;
            Passenger passenger = new Passenger(bookingId, passengerName, age, seats);
            bookings.put(bookingId, passenger);
            availableSeats -= seats;
            ticketPrice += 200 * seats;
            return bookingId;
        } else {
            System.out.println("Booking failed: Not enough seats available.");
            return null;
        }
    }

    public boolean cancelBooking(String bookingId) {
        Passenger passenger = bookings.get(bookingId);
        if (passenger != null) {
            int seats = passenger.getSeatsBooked();
            availableSeats += seats;
            ticketPrice -= 200 * seats;
            bookings.remove(bookingId);
            System.out.println("Booking canceled successfully. Refund issued for " + seats + " seats.");
            return true;
        } else {
            System.out.println("Cancellation failed: Booking ID not found.");
            return false;
        }
    }

    public void displayDetails() {
        System.out.println("\n-- Flight Details --");
        System.out.println("Flight: " + flightName);
        System.out.println("Available Seats: " + availableSeats);
        System.out.println("Current Ticket Price: ₹" + ticketPrice);
    }

    public void printPassengerDetails() {
        System.out.println("\n-- Passengers on " + flightName + " --");
        for (Passenger passenger : bookings.values()) {
            System.out.println(passenger);
        }
    }
}

class FlightReservationSystem {
    Map<String, Flight> flights;

    public FlightReservationSystem() {
        flights = new HashMap<>();
        flights.put("Indigo", new Flight("Indigo"));
        flights.put("SpiceJet", new Flight("SpiceJet"));
    }

    public String bookTicket(String flightName, String passengerName, int age, int seats) {
        Flight flight = flights.get(flightName);
        if (flight != null) {
            return flight.bookTickets(passengerName, age, seats);
        } else {
            System.out.println("Booking failed: Flight not found.");
            return null;
        }
    }

    public boolean cancelTicket(String flightName, String bookingId) {
        Flight flight = flights.get(flightName);
        if (flight != null) {
            return flight.cancelBooking(bookingId);
        } else {
            System.out.println("Cancellation failed: Flight not found.");
            return false;
        }
    }

    public void displayFlightDetails(String flightName) {
        Flight flight = flights.get(flightName);
        if (flight != null) {
            flight.displayDetails();
        } else {
            System.out.println("Flight not found.");
        }
    }

    public void printFlightDetails(String flightName) {
        Flight flight = flights.get(flightName);
        if (flight != null) {
            flight.printPassengerDetails();
        } else {
            System.out.println("Flight not found.");
        }
    }
}

public class Main {
    public static void main(String[] args) {
        FlightReservationSystem system = new FlightReservationSystem();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n--- Welcome to the Flight Reservation System ---");
            System.out.println("1. Book a Ticket");
            System.out.println("2. Cancel a Ticket");
            System.out.println("3. View Flight and Passenger Details");
            System.out.println("4. Exit");
            System.out.print("Please enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1: // Book Ticket
                    System.out.print("Enter flight name (Air india/ SpiceJet): ");
                    String flightName = scanner.nextLine();

                    if (system.flights.containsKey(flightName)) {
                        system.displayFlightDetails(flightName);
                        System.out.print("Enter passenger name: ");
                        String passengerName = scanner.nextLine();
                        System.out.print("Enter passenger age: ");
                        int age = scanner.nextInt();
                        System.out.print("Enter number of seats to book: ");
                        int seats = scanner.nextInt();

                        String bookingId = system.bookTicket(flightName, passengerName, age, seats);
                        if (bookingId != null) {
                            System.out.println("Booking successful! Your booking ID is: " + bookingId);
                        }
                    } else {
                        System.out.println("Invalid flight name. Please try again.");
                    }
                    break;

                case 2:
                    System.out.print("Enter flight name (Indigo/SpiceJet): ");
                    flightName = scanner.nextLine();
                    System.out.print("Enter booking ID: ");
                    String bookingId = scanner.nextLine();

                    system.cancelTicket(flightName, bookingId);
                    break;

                case 3: // View Flight and Passenger Details
                    System.out.print("Enter flight name (Indigo/SpiceJet): ");
                    flightName = scanner.nextLine();
                    system.printFlightDetails(flightName);
                    break;

                case 4: // Exit
                    System.out.println("Thank you for using the Flight Reservation System. Goodbye!");
                    scanner.close();
                    return;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}

class Passenger {
    private String bookingId;
    private String name;
    private int age;
    private int seatsBooked;

    public Passenger(String bookingId, String name, int age, int seatsBooked) {
        this.bookingId = bookingId;
        this.name = name;
        this.age = age;
        this.seatsBooked = seatsBooked;
    }

//    public String getBookingId() {
//        return bookingId;
//    }

    public int getSeatsBooked() {
        return seatsBooked;
    }

//    @Override
//    public String toString() {
//        return "Passenger ID: " + bookingId + " | Name: " + name + " | Age: " + age + " | Seats Booked: " + seatsBooked;
//    }
}