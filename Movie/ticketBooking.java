package Practice.Movie;
import java.util.*;
class ticketBooking {
	public static void main(String args[]){
		ticketBooking tbs = new ticketBooking();
		tbs.getChoice();
	}
	Scanner sc = new Scanner(System.in);
	String mvname="Toy Story",mvprice="$50",mvtime="10 AM";
	ArrayList<movies> mov=new ArrayList<movies>();
	ArrayList<ticket> tickets = new ArrayList<ticket>();
	public void getChoice(){
		System.out.println("1.Show Movies");
		System.out.println("2.Book Ticket");
		System.out.println("3.View Ticket");
		System.out.println("4.Cancel Ticket");
		System.out.println("5.Exit");
		int ch = sc.nextInt();
		switch(ch){
			case 1:{
					mov.add(new movies(mvname,mvprice,mvtime));
					movies m2 = new movies("BOSS BABY","$45","1 PM");
					mov.add(m2);
					movies m3 = new movies("ICE AGE","$65","5 PM");
					mov.add(m3);
					for(movies x:mov){
						x.showMovie();
						}
						getChoice();
					break;
				}
			case 2:{
				sc.nextLine();
				System.out.println("Enter movie name:");
				String mn=sc.nextLine();
				String mt="";
				String pr="";
				int noftic=0;
				int loc=0;
				for (movies x:mov){
					if ((x.mvName).equalsIgnoreCase(mn)){
						mn= x.mvName;
						mt=x.mvTime;
						pr=x.mvPrice;
						noftic=x.noftic;
						int[] seats=x.book();
						mov.set(loc,x);
						int id=tickets.size();
						tickets.add(new ticket(id,mn,mt,pr, x.noftic, seats));
						System.out.println("your tickets id is: "+id);
						System.out.println("seats are: "+Arrays.toString(seats));
					}loc++;
				}getChoice();
				break;
			}
			case 3:{
				sc.nextLine();
				System.out.println("Enter ticket id:");
				int tid=sc.nextInt();
				ticket t= tickets.get(tid);
				t.showTicket();
				getChoice();
				break;
			}
			case 4:{
				sc.nextLine();
				System.out.println("Enter ticket id:");
				int tid=sc.nextInt();
				ticket t= tickets.get(tid);
				int loc1=0;
				for(movies a:mov){
				if((a.mvName).equalsIgnoreCase(t.mvName)){
					for(int sn:t.bookseats){
					int ids=1;
						for(int i=0;i<10;i++){
							for(int j=0;j<10;j++){
								if(ids==sn){
									a.seats[i][j]=ids;
								}
								ids++;
							}
						}
					}
					mov.set(loc1,a);
					}
				loc1++;
			}
			tickets.remove(tid);
			System.out.println("Ticket cancelled...successfully");
			getChoice();
			break;
			}
			case 5:{
				return;
			}
			default:{
			System.out.println("Sorry Invalid Option");
			break;
			}
		}
	}
}