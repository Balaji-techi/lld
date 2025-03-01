package Practice.Parking;
import java.util.Scanner;
class Vehicle {
    private String vehicleId;
    private String vehicleType; // Car, Bike, Truck
    private String licensePlate;
    private String ownerName;

    public Vehicle(String vehicleId, String vehicleType, String licensePlate, String ownerName) {
        this.vehicleId = vehicleId;
        this.vehicleType = vehicleType;
        this.licensePlate = licensePlate;
        this.ownerName = ownerName;
    }

    public String getVehicleDetails() {
        return "ID: " + vehicleId + "\n" + "Type: " + vehicleType + "\n" + "License Plate: " + licensePlate + "\n" + "Owner: " + ownerName;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public String getVehicleId() {
        return vehicleId;
    }
}
class ParkingSpace {
    private int spaceId;
    private boolean isOccupied;
    private Vehicle vehicle;

    public ParkingSpace(int spaceId) {
        this.spaceId = spaceId;
        this.isOccupied = false;
    }

    public boolean isAvailable() {
        return !isOccupied;
    }

    public void assignVehicle(Vehicle vehicle) {
        if (isAvailable()) {
            this.vehicle = vehicle;
            isOccupied = true;
        } else {
            throw new IllegalStateException("Parking space is already occupied.");
        }
    }

    public void removeVehicle() {
        if (isOccupied) {
            this.vehicle = null;
            isOccupied = false;
        } else {
            throw new IllegalStateException("No vehicle to remove.");
        }
    }

    public int getSpaceId() {
        return spaceId;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }
}
class ParkingLot {
    private int totalSpaces;
    private int availableSpaces;
    private ParkingSpace[] spaces;
    public ParkingLot(int totalSpaces) {
        this.totalSpaces = totalSpaces;
        this.availableSpaces = totalSpaces;
        this.spaces = new ParkingSpace[totalSpaces];
        for (int i = 0; i < totalSpaces; i++) {
            spaces[i] = new ParkingSpace(i + 1);
        }
    }
    public ParkingSpace getAvailableSpace() {
        for (ParkingSpace space : spaces) {
            if (space.isAvailable()) {
                return space;
            }
        }
        return null;
    }
    public void assignSpace(Vehicle vehicle) {
        ParkingSpace space = getAvailableSpace();
        if (space != null) {
            space.assignVehicle(vehicle);
            availableSpaces--;
            System.out.println("\n" + "Vehicle assigned to space " + space.getSpaceId());
        } else {
            System.out.println("No available space for vehicle.");
        }
    }

    public void releaseSpace(int spaceId) {
        if (spaceId <= totalSpaces) {
            ParkingSpace space = spaces[spaceId - 1];
            space.removeVehicle();
            availableSpaces++;
            System.out.println("Parking space " + spaceId + " is now free.");
        } else {
            System.out.println("Invalid parking space.");
        }
    }

    public boolean isFull() {
        return availableSpaces == 0;
    }
}
class Payment {
    public boolean makePayment(double amount) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Total Payment Amount: " + amount);
        System.out.print("Enter payment amount: ");
        double paymentAmount = scanner.nextDouble();

        if (paymentAmount >= amount) {
            System.out.println("Payment successful. Change: " + (paymentAmount - amount));
            return true;
        } else {
            System.out.println("Insufficient payment. Try again.");
            return false;
        }
    }
}
public class ParkingManagementSystem {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ParkingLot parkingLot = new ParkingLot(5);

        System.out.println("---------------Welcome to the Parking Management System!---------------------");

        while (!parkingLot.isFull()) {
            System.out.print("Enter vehicle type (Car/Bike/Truck): ");
            String vehicleType = scanner.nextLine();

            System.out.print("Enter vehicle license plate: ");
            String licensePlate = scanner.nextLine();

            System.out.print("Enter owner name: ");
            String ownerName = scanner.nextLine();

            String vehicleId = licensePlate;

            Vehicle vehicle = new Vehicle(vehicleId, vehicleType, licensePlate, ownerName);

            double parkingFee = 0;
            switch (vehicleType.toLowerCase()) {
                case "car":
                    parkingFee = 10.0; // Car: $40
                    break;
                case "bike":
                    parkingFee = 5.0; // Bike: $20
                    break;
                case "truck":
                    parkingFee = 15.0; //truck: $50
                    break;
                default:
                    System.out.println("Invalid vehicle type.");
                    continue;
            }

            Payment payment = new Payment();
            boolean paymentSuccess = false;
            while (!paymentSuccess) {
                paymentSuccess = payment.makePayment(parkingFee);
            }
            parkingLot.assignSpace(vehicle);
            System.out.println("Vehicle Details: " + vehicle.getVehicleDetails());
        }

        System.out.println("Parking lot is full. No more vehicles can be parked.");
    }
}

