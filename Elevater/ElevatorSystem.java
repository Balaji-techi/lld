package Elevator;
import java.util.*;

public class ElevatorSystem {
    public static void main(String[] args) {
        List<Elevator> elevators = new ArrayList<>();
        elevators.add(new Elevator(1));
        elevators.add(new Elevator(2));
        elevators.add(new Elevator(3));

        Controller controller = new Controller(elevators);

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\n--- Elevator System ---");
            System.out.println("1. Request Elevator");
            System.out.println("2. Simulate Elevator Movement");
            System.out.println("3. Enable/Disable Emergency Stop");
            System.out.println("4. Set Elevator to Maintenance");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1 -> {
                    System.out.print("Enter your current floor: ");
                    int currentFloor = scanner.nextInt();

                    System.out.print("Enter your destination floor: ");
                    int destinationFloor = scanner.nextInt();

                    controller.addRequest(currentFloor, destinationFloor);
                }
                case 2 -> controller.step();
                case 3 -> controller.toggleEmergencyStop();
                case 4 -> {
                    System.out.print("Enter the Elevator ID to set in maintenance: ");
                    int elevatorId = scanner.nextInt();
                    controller.setElevatorToMaintenance(elevatorId);
                }
                case 5 -> {
                    System.out.println("Exiting Elevator System. Goodbye!");
                    return;
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
enum Direction {
    UP, DOWN, IDLE
}
enum DoorState {
    OPEN, CLOSED
}
enum ElevatorState {
    OPERATIONAL, MAINTENANCE, EMERGENCY_STOP
}
class Elevator {
    private int id;
    private int currentFloor;
    private Direction direction;
    private DoorState doorState;
    private ElevatorState state;
    private PriorityQueue<Integer> upRequests;
    private PriorityQueue<Integer> downRequests;
    private static final int MAX_FLOOR = 10;
    public Elevator(int id) {
        this.id = id;
        this.currentFloor = 0;
        this.direction = Direction.IDLE;
        this.doorState = DoorState.CLOSED;
        this.state = ElevatorState.OPERATIONAL;
        this.upRequests = new PriorityQueue<>();
        this.downRequests = new PriorityQueue<>(Collections.reverseOrder());
    }
    public int getId() {
        return id;
    }
    public int getCurrentFloor() {
        return currentFloor;
    }
    public Direction getDirection() {
        return direction;
    }
    public ElevatorState getState() {
        return state;
    }
    public void setState(ElevatorState state) {
        this.state = state;
        System.out.println("Elevator " + id + " is now in " + state + " mode.");
    }
    public void addRequest(int floor, Direction requestDirection) {
    if (floor < 0 || floor > MAX_FLOOR) {
        System.out.println("Invalid floor request. Please choose a floor between 0 and " + MAX_FLOOR);
        return;
    }
    if (requestDirection == Direction.UP) {
        upRequests.add(floor);
    } else if (requestDirection == Direction.DOWN) {
        downRequests.add(floor);
    }
    }
    public void move() {
        if (state != ElevatorState.OPERATIONAL) {
            return;
        }
        if (!upRequests.isEmpty()) {
            direction = Direction.UP;
            processRequest(upRequests);
        } else if (!downRequests.isEmpty()) {
            direction = Direction.DOWN;
            processRequest(downRequests);
        } else {
            direction = Direction.IDLE;
        }
    }
    private void processRequest(PriorityQueue<Integer> requests) {
        int targetFloor = requests.peek();
        if (currentFloor < targetFloor) {
            currentFloor++;
        } else if (currentFloor > targetFloor) {
            currentFloor--;
        }
        System.out.println("Elevator " + id + " moving to floor " + currentFloor);
        if (currentFloor == targetFloor) {
            System.out.println("Elevator " + id + " reached floor " + currentFloor);
            openDoor();
            requests.poll();
        }
    }
    private void openDoor() {
        doorState = DoorState.OPEN;
        System.out.println("Elevator " + id + " door opened");
        closeDoor();
    }
    private void closeDoor() {
        doorState = DoorState.CLOSED;
        System.out.println("Elevator " + id + " door closed");
    }
}
class Controller {
    private List<Elevator> elevators;

    private boolean emergencyStop;

    public Controller(List<Elevator> elevators) {
        this.elevators = elevators;
        this.emergencyStop = false;
    }

    public void addRequest(int currentFloor, int destinationFloor) {
        if (emergencyStop) {
            System.out.println("System is in EMERGENCY STOP mode. No requests can be handled.");
            return;
        }

        Direction requestDirection = (destinationFloor > currentFloor) ? Direction.UP : Direction.DOWN;

        Elevator elevator = findClosestElevator(currentFloor, requestDirection);
        if (elevator != null) {
            elevator.addRequest(currentFloor, requestDirection);
            elevator.addRequest(destinationFloor, requestDirection);
            System.out.println("Request assigned to Elevator " + elevator.getId());
        } else {
            System.out.println("No available elevators at the moment.");
        }
    }

    private Elevator findClosestElevator(int floor, Direction requestDirection) {
        Elevator closestElevator = null;
        int minDistance = Integer.MAX_VALUE;

        for (Elevator elevator : elevators) {
            if (elevator.getState() != ElevatorState.OPERATIONAL) {
                continue;
            }

            int distance = Math.abs(elevator.getCurrentFloor() - floor);
            if (distance < minDistance && (elevator.getDirection() == requestDirection || elevator.getDirection() == Direction.IDLE)) {
                closestElevator = elevator;
                minDistance = distance;
            }
        }
        return closestElevator;
    }

    public void toggleEmergencyStop() {
        emergencyStop = !emergencyStop;

        for (Elevator elevator : elevators) {
            if (emergencyStop) {
                elevator.setState(ElevatorState.EMERGENCY_STOP);
            } else {
                elevator.setState(ElevatorState.OPERATIONAL);
            }
        }

        System.out.println("Emergency stop is now " + (emergencyStop ? "ENABLED" : "DISABLED"));
    }
    public void setElevatorToMaintenance(int elevatorId) {
        for (Elevator elevator : elevators) {
            if (elevator.getId() == elevatorId) {
                elevator.setState(ElevatorState.MAINTENANCE);
                return;
            }
        }
        System.out.println("No elevator found with ID " + elevatorId);
    }
    public void step() {
        if (emergencyStop) {
            System.out.println("System is in EMERGENCY STOP mode. No movement possible.");
            return;
        }
        for (Elevator elevator : elevators) {
            elevator.move();
        }
    }
}