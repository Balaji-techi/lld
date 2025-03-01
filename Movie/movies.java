package Practice.Movie;

import java.util.*;
class movies{
	String mvName;
	String mvTime;
	String mvPrice;
	int availSeat=100;
	int noftic;
	int[][] seats = new int[10][10];
	Scanner sc = new Scanner(System.in);

	public movies(String mvName, String mvTime, String mvPrice) {
		this.mvName = mvName;
		this.mvTime = mvTime;
		this.mvPrice = mvPrice;
		int s=1;
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				seats[i][j]=s;
				s++;
			}
		}
	}
	public void showMovie(){
		System.out.println("-----------------------------");
		System.out.println("Movie name: "+mvName);
		System.out.println("Movie Time: "+mvTime);
		System.out.println("Movie Price: "+mvPrice);
		System.out.println("Saate avai: "+availSeat);
		System.out.println("----------------------");
	}
	public int[] book(){
		System.out.println("Enter the number of seats you want to book");
		noftic=sc.nextInt();
		viewseats();
		int[] booked=new int[noftic];
		for (int i = 0; i < noftic; i++) {
			System.out.println("Eter the number of seats for the person"+(i+1));
			int seaNo=sc.nextInt();
			for (int i1 = 0; i1 < 10; i1++) {
				for (int j = 0; j < 10; j++) {
					if (seats[i1][j]==seaNo){
						seats[i1][j]=0;
						booked[i]=seaNo;
					}
				}
			}
			System.out.println(seaNo+" is Booked");
		}return booked;
	}
	public void viewseats(){
		System.out.println("---------------------------------------------");
		System.out.println("---------------------screen------------------");
		System.out.println("----------------------------------------------");
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				System.out.print(" | "+seats[i][j]);
			}
			System.out.println("\n--------------------------------------------");
		}
		System.out.println("\n 0 means seats are booked");
	}
}