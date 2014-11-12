package reservation;

import java.util.Scanner;

public class ReservationTester {

	static ReservationManager rs = new ReservationManager();
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner scan = new Scanner(System.in);
	while(true){
		System.out.println("Reservation Testing .... Press Enter to continue ");
		
		int i = 0;
		
		while(i!=-1){ 
			
			rs.makeReservation();
			System.out.print("Do you want to make more reservations: enter -1 <no>: ");
			i = scan.nextInt();
			
		}
		System.out.println("Enter the name to view reservation: ");
		scan.nextLine();
		rs.printReservationFor(scan.nextLine());
		//rs.removeReservation();
		//rs.printReservation();
		//rs.printAllReservation();
		}
	}

}
