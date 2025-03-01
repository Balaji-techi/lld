package Practice.Movie;

import java.util.*;
class ticket{
	int ticketId;
	String mvName;
	String mvTime;
	String mvPrice;
	int tofper;
	int[] bookseats;
	public ticket(int ticketId, String mvName, String mvTime, String mvPrice, int tofper, int[] bookseats) {
		this.ticketId = ticketId;
		this.mvName = mvName;
		this.mvTime = mvTime;
		this.mvPrice = mvPrice;
		this.tofper = tofper;
		this.bookseats = bookseats;
	}
	public void showTicket(){
		System.out.println("----------------------------");
		System.out.println("Ticket ID: "+ticketId);
		System.out.println("Movie Name: "+mvName);
		System.out.println("Movie Time: "+mvTime);
		System.out.println("Movie Price: "+mvPrice);
		System.out.println("Movie number of person: "+tofper);
		System.out.println("Seart areallocated: "+Arrays.toString(bookseats));
	}
}