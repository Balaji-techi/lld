package Practice.Food_Delivery_Application;

import java.util.*;
public class DeliveryExecutive {
    String id;
    int deliveryCharges = 0;
    int allowance = 0;
    int tripCount = 0;
    List<Booking> bookings = new ArrayList<>();

    public DeliveryExecutive(String id) {
        this.id = id;
    }

    public void addBooking(Booking booking, int deliveryCharge) {
        this.bookings.add(booking);
        this.deliveryCharges += deliveryCharge;
    }

    public void addAllowance() {

        this.allowance += 10;

    }

    public void incrementTripCount() {
        this.tripCount++;
    }

    public List<Booking> getBookings() {
        return bookings;
    }

    public String getId() {
        return id;
    }

    public int getTotalEarned() {
        return this.deliveryCharges + this.allowance;
    }

    @Override
    public String toString() {
        return id + " - Earned: " + getTotalEarned() + " (Delivery: " + deliveryCharges + ", Allowance: " + allowance + ", Trips: " + tripCount + ")";
    }
}