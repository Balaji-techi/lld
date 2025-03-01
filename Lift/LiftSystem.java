package Practice.Lift;
import java.util.*;

public class LiftSystem {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        LiftSystem liftSystem = new LiftSystem();

        while (true) {
            System.out.println("\nMenu:");
            System.out.println("1. Display lift positions");
            System.out.println("2. Assign lift");
            System.out.println("3. Assign lift based on capacity");
            System.out.println("4. Set lift under maintenance");
            System.out.println("5. Exit");

            int choice = scanner.nextInt();
            switch (choice) {
                case 1 -> liftSystem.displayLiftPositions();
                case 2 -> {
                    System.out.println("Enter source and destination floors:");
                    int fromFloor = scanner.nextInt();
                    int toFloor = scanner.nextInt();
                    liftSystem.assignLift(fromFloor, toFloor);
                }
                case 3 -> {
                    System.out.println("Enter source, destination floors, and number of people:");
                    int fromFloor = scanner.nextInt();
                    int toFloor = scanner.nextInt();
                    int people = scanner.nextInt();
                    liftSystem.assignLiftBasedOnCapacity(fromFloor, toFloor, people);
                }
                case 4 -> {
                    System.out.println("Enter lift name:");
                    String liftName = scanner.next();
                    liftSystem.setLiftUnderMaintenance(liftName);
                }
                case 5 -> {
                    System.out.println("Ok Bye...");
                    return;
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }
    static class Lift {
        String name;
        int currentFloor;
        boolean isUnderMaintenance;
        int capacity;
        int minFloor;
        int maxFloor;
        int stops;
        public Lift(String name,int capacity, int minFloor, int maxFloor) {
            this.name = name;
            this.currentFloor = currentFloor;
            this.isUnderMaintenance = isUnderMaintenance;
            this.capacity = capacity;
            this.minFloor = minFloor;
            this.maxFloor = maxFloor;
            this.stops = 0;
        }
    }
    private final List<Lift> lifts;
    public LiftSystem() {
        lifts = new ArrayList<>();
        initializeLifts();
    }
    private void initializeLifts() {
        lifts.add(new Lift("L1", 8, 0, 5));
        lifts.add(new Lift("L2", 8, 0, 5));
        lifts.add(new Lift("SL3", 10, 0, 10));
        lifts.add(new Lift("SL4", 10, 0, 10));
        lifts.add(new Lift("L5", 10, 0, 10));
    }
    public void displayLiftPositions() {
        System.out.println("Lift  : " + lifts.stream().map(l -> l.name).reduce((a, b) -> a + " " + b).orElse(""));
        System.out.println("Floor : " + lifts.stream().map(l -> l.isUnderMaintenance ? "M" : String.valueOf(l.currentFloor)).reduce((a, b) -> a + " " + b).orElse(""));
    }
    private Lift findBestLift(int fromFloor, int toFloor) {
        Lift bestLift = null;
        int minDistance = Integer.MAX_VALUE;

        for (Lift lift : lifts) {
            if (lift.isUnderMaintenance) continue;
            if (fromFloor < lift.minFloor || fromFloor > lift.maxFloor) continue;
            int distance = Math.abs(lift.currentFloor - fromFloor);
            boolean sameDirection = (toFloor - fromFloor) * (lift.currentFloor - fromFloor) >= 0;
            if (distance < minDistance || (distance == minDistance && sameDirection)) {
                minDistance = distance;
                bestLift = lift;
            }
        }
        return bestLift;
    }
    public void assignLift(int fromFloor, int toFloor) {
        Lift bestLift = findBestLift(fromFloor, toFloor);
        if (bestLift != null) {
            System.out.println(bestLift.name + " is assigned.");
            bestLift.currentFloor = toFloor;
            bestLift.stops += Math.abs(fromFloor - toFloor);
        } else {
            System.out.println("No available lifts.");
        }
    }
    public void assignLiftBasedOnCapacity(int fromFloor, int toFloor, int people) {
        Lift bestLift = null;

        for (Lift lift : lifts) {
            if (lift.isUnderMaintenance || people > lift.capacity) continue;
            if (fromFloor >= lift.minFloor && fromFloor <= lift.maxFloor) {
                bestLift = lift;
                break;
            }
        }
        if (bestLift != null) {
            System.out.println(bestLift.name + " is assigned based on capacity.");
            bestLift.currentFloor = toFloor;
        } else {
            System.out.println("No available lifts with sufficient capacity.");
        }
    }
    public void setLiftUnderMaintenance(String liftName) {
        for (Lift lift : lifts) {
            if (lift.name.equalsIgnoreCase(liftName)) {
                lift.isUnderMaintenance = true;
                lift.currentFloor = -1;
                System.out.println(lift.name + " is under maintenance.");
                return;
            }
        }
        System.out.println("Lift not found.");
    }
}