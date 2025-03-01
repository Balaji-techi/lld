import java.util.*;

class TrainTicketBooking {
    private static final int TOTAL_SEATS = 8;
    private static final int TOTAL_WL = 2;
    private static int pnrCounter = 1;
    static class Ticket {
        int pnr;
        String source, destination;
        List<Integer> seats;
        boolean isCancelled;

        Ticket(int pnr, String source, String destination, List<Integer> seats) {
            this.pnr = pnr;
            this.source = source;
            this.destination = destination;
            this.seats = new ArrayList<>(seats);
            this.isCancelled = false;
        }
    }
    private static Map<Integer, Ticket> bookedTickets = new HashMap<>();
    private static List<Integer> availableSeats = new ArrayList<>();
    private static Queue<Ticket> waitingList = new LinkedList<>();
    static {
        for (int i = 1; i <= TOTAL_SEATS; i++) {
            availableSeats.add(i);
        }
    }
    public static void bookTicket(String source, String destination, int numSeats) {
        if (numSeats <= 0) {
            System.out.println("Invalid seat request. Enter a positive number.");
            return;
        }

        List<Integer> allocatedSeats = new ArrayList<>();
        if (numSeats <= availableSeats.size()) {
            for (int i = 0; i < numSeats; i++) {
                allocatedSeats.add(availableSeats.remove(0));
            }
        } else if (waitingList.size() <= TOTAL_WL) {
            for (int i = 0; i < numSeats; i++) {
                allocatedSeats.add(-1);
            }
            System.out.println("PNR " + pnrCounter + " booked with " + numSeats + " WL tickets.");
        } else {
            System.out.println("Booking failed. Not enough seats available, including waitlist.");
            return;
        }
        Ticket ticket = new Ticket(pnrCounter++, source, destination, allocatedSeats);
        bookedTickets.put(ticket.pnr, ticket);

        if (allocatedSeats.contains(-1)) {
            waitingList.add(ticket);
        }
        System.out.println("PNR " + ticket.pnr + " booked from " + source + " to " + destination + " with seats: " + allocatedSeats);
    }
    public static void cancelTicket(int pnr, int numSeatsToCancel) {
        if (!bookedTickets.containsKey(pnr) || bookedTickets.get(pnr).isCancelled) {
            System.out.println("Invalid PNR or ticket already cancelled.");
            return;
        }

        Ticket ticket = bookedTickets.get(pnr);
        List<Integer> cancelledSeats = new ArrayList<>();
        for (int i = 0; i < numSeatsToCancel && !ticket.seats.isEmpty(); i++) {
            int seat = ticket.seats.remove(0);
            if (seat != -1) availableSeats.add(seat);
            cancelledSeats.add(seat);
        }
        if (ticket.seats.isEmpty()) {
            ticket.isCancelled = true;
        }
        System.out.println("PNR " + pnr + " cancelled for seats: " + cancelledSeats);
        processWaitingList();
    }

    private static void processWaitingList() {
        Iterator<Ticket> iterator = waitingList.iterator();

        while (iterator.hasNext() && !availableSeats.isEmpty()) {
            Ticket ticket = iterator.next();
            List<Integer> updatedSeats = new ArrayList<>();

            for (int i = 0; i < ticket.seats.size(); i++) {
                if (ticket.seats.get(i) == -1 && !availableSeats.isEmpty()) {
                    updatedSeats.add(availableSeats.remove(0));
                } else {
                    updatedSeats.add(ticket.seats.get(i));
                }
            }
            ticket.seats = updatedSeats;
            if (!ticket.seats.contains(-1)) {
                iterator.remove();
                System.out.println("PNR " + ticket.pnr + " moved from waitlist to confirmed with seats: " + ticket.seats);
            }
        }
    }

    public static void printChart() {
        System.out.println("\nSeat Chart:");
        System.out.println("      A       B       C       D       E");
        for (int i = 1; i <= TOTAL_SEATS; i++) {
            System.out.print(i + "   ");
            for (Ticket ticket : bookedTickets.values()) {
                if (!ticket.isCancelled && ticket.seats.contains(i)) {
                    System.out.print("*   ");
                } else {
                    System.out.print("    ");
                }
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\nEnter command (e.g., book,A,E,5 or cancel,1,3 or chart):");
            String input = scanner.nextLine().trim();
            String[] parts = input.split(",");
            if (parts.length == 1 && parts[0].equalsIgnoreCase("chart")) {
                printChart();
            } else if (parts.length ==4 && parts[0].equalsIgnoreCase("book")) {
                try {
                    String source = parts[1].trim();
                    String destination = parts[2].trim();
                    int numSeats = Integer.parseInt(parts[3].trim());
                    bookTicket(source, destination, numSeats);
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Format: book,Source,Destination,NumberOfSeats");
                }
            } else if (parts.length == 3 && parts[0].equalsIgnoreCase("cancel")) {
                try {
                    int pnr = Integer.parseInt(parts[1].trim());
                    int numSeatsToCancel = Integer.parseInt(parts[2].trim());
                    cancelTicket(pnr, numSeatsToCancel);
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Format: cancel,PNR,NumberOfSeats");
                }
            } else
                System.out.println("Invalid command. Try again.");
        }
    }
}