package reservation;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Scanner;

public class ReservationManager {
	
	static final int constantNumberOfSeats = 2;
	static int NoOfSeatsAvaiable = 2;
	ArrayList<Reservation> reservationArray = new ArrayList<Reservation>(); 
	Scanner scan = new Scanner(System.in);
	
	public boolean makeReservation (){
		String n; long c ; int p;
		
		scan.nextLine();
		if (NoOfSeatsAvaiable > 0){
		
		System.out.print("Enter the name of the customer: ");
		
		n = scan.nextLine();
		if (n == null){
			System.out.println("Name cannot be empty");
			return false;
		}
		
		System.out.print("Enter the contact of Customer: ");
		try {
			c = scan.nextLong();
		}
		catch (Exception e ){
			System.out.println("Invalid input");
			return false;
		}
		
		System.out.print("Enter the number of people: ");
		p = scan.nextInt();
		
		if (!(p> 0) || !(p%2==0) || !(p < 11)){
			System.out.println("System only allows even number of people Minimum 2 and Maximum 10");
			return false;
		}
		
			GregorianCalendar reserve = setReservationDate();
			GregorianCalendar now = (GregorianCalendar) GregorianCalendar.getInstance();
			reservationArray.add(new Reservation(n, c, p, 
					(constantNumberOfSeats-NoOfSeatsAvaiable+1) % constantNumberOfSeats, now, reserve));
			NoOfSeatsAvaiable--;
			return true;
		}
		else{
			System.out.println("No more seats available ! Sorry");
			return false;
		}
	}
	
	public boolean removeReservation(){
		String name;
		System.out.println("Enter the name: ");
		name = scan.nextLine();
		for (Reservation r: reservationArray){
			if (r.getName().equals(name)){
				reservationArray.remove(r);
				NoOfSeatsAvaiable++;
				return true;
			}
		}
		System.out.println("Reservation Not Found for this person");
		return false;
		
	}
	
	public GregorianCalendar setReservationDate(){
		/*The drawback of this technique is that let's say the reservation is supposed to be 
		 * made 30 minutes earlier from the current minute.  So when the program asks you to 
		 * key in the minute and you leave the computer at that state for 30 minutes and come 
		 * back later to key in the same minute, the system allows you pass and make 
		 * reservation even though the condition set for reservation to be made 30 minutes 
		 * earlier is violated since local variables are not being updated dynamically. 
		 * A better solution would be to use regular interrupts to update the variables.  
		 * Aung Naing Oo */
		
		
		int currentMonth = GregorianCalendar.getInstance().get(GregorianCalendar.MONTH)+1; //Month starts from zero in Java
		int currentYear = GregorianCalendar.getInstance().get(GregorianCalendar.YEAR);
		int currentDate = GregorianCalendar.getInstance().get(GregorianCalendar.DATE);
		int currentHour = GregorianCalendar.getInstance().get(GregorianCalendar.HOUR_OF_DAY);
		int currentMinute = GregorianCalendar.getInstance().get(GregorianCalendar.MINUTE);
		
		System.out.println("Today's date "+ currentDate );
		System.out.println("Current Month "+ currentMonth );
		System.out.println("Current Year "+ currentYear );
		System.out.println("Current Hour "+ currentHour );
		System.out.println("Current Year "+ currentMinute );
		System.out.println("\t---\t---\t---\t---\t---");
		int inputMonth, inputYear, inputDate, inputHour, inputMinute;
		
		GregorianCalendar reserveDate = (GregorianCalendar) GregorianCalendar.getInstance();
		
		System.out.print("Enter the year: ");
		inputYear = scan.nextInt();
		
		while( inputYear < currentYear){
			System.out.println("Old year entry is not allowed !\n");
			System.out.print("Enter the year: ");
			inputYear = scan.nextInt();
		}	
		reserveDate.set(GregorianCalendar.YEAR,inputYear); 
	
		System.out.print("Enter the month: ");
		inputMonth = scan.nextInt();	
		
		while (inputMonth < 1 || inputMonth > 12){
			System.out.println("Invalid Month entry!");
			System.out.print("Enter the month again: ");
			inputMonth = scan.nextInt();
		}
		
		while((inputMonth < currentMonth) && (inputYear == currentYear)){
					
			System.out.println("Old month entry is not allowed !\n");
			System.out.print("Enter the month: ");
			inputMonth = scan.nextInt();
			
		}
		reserveDate.set(GregorianCalendar.MONTH,inputMonth);
		
		System.out.print("Enter the date for reservation: ");
		inputDate = scan.nextInt();
		
		while( inputDate < 1 || inputDate > 31){
			System.out.println("Invalid date entry!");
			System.out.print("Enter the date again: ");
			inputDate = scan.nextInt();
		}
		
		while((inputDate < currentDate) && (inputMonth == currentMonth)){
			System.out.println("Past Day is not allowed!\n");
			System.out.print("Enter the date for reservation: ");
			inputDate = scan.nextInt();
		}
		reserveDate.set(GregorianCalendar.DATE,inputDate);
		
		System.out.print("Enter hour (24-hr format): ");
		inputHour = scan.nextInt();
		
		while( inputHour < 0 || inputHour > 23){
			System.out.println("Invalid hour entry!");
			System.out.print("Enter the hour again: ");
			inputHour = scan.nextInt();
		}
		
		while(inputHour < currentHour && inputDate == currentDate){
			
			if (inputHour < 0 && inputHour > 23){
				System.out.println("Invalid Hour entry");
			}
			else{
				System.out.println("Past hour is not allowed!\n");
				System.out.print("Enter hour (24-hr format): ");
				inputHour = scan.nextInt();
			}
		}
		reserveDate.set(GregorianCalendar.HOUR_OF_DAY,inputHour); //This uses 24 hour clock
		
		System.out.print("Enter minute: ");
		inputMinute = scan.nextInt();
		
		while(inputMinute < 0 || inputMinute > 59){
			System.out.println("Invalid Minute entry");
			System.out.print("Enter minute again: ");
			inputMinute = scan.nextInt();
		}
		
		while((inputMinute-currentMinute) < 30 && inputHour == currentHour){
			
			System.out.println("Reservation can only be made 30 minutes earlier or invalid input !");
			System.out.print("Enter minute: ");
			inputMinute = scan.nextInt();	
		}
		
		reserveDate.set(GregorianCalendar.MINUTE,inputMinute);		
		System.out.println("Date Reserved!");
		return reserveDate;
	}
	
	public void printReservationFor(String name){
	
	for (Reservation r: reservationArray){
		if (r.getName().equals(name)){
			System.out.println("\t\t==\t\t==\t\t==\n\n");
			System.out.print("Date and time of Booking : "); 
			formattedDate(r.getBookingDateAndTime());
			
			System.out.print("Date and Time of Reservation : "); 
			formattedDate(r.getReservationDateAndTime());
			
			System.out.println("Table Number = " + r.getTableId());
			System.out.println("Name = " + r.getName());
			System.out.println("Number of People = "+r.getPax());
			System.out.println("Contact Number = " + r.getContact());
			break;
			}
		}
	}
	
	public void printAllReservation(){
		
		for (Reservation r: reservationArray){
			System.out.println("\t\t==\t\t==\t\t==\n\n");
			System.out.print("Date and time of Booking : "); 
			formattedDate(r.getBookingDateAndTime());
			
			System.out.print("Date and Time of Reservation : "); 
			formattedDate(r.getReservationDateAndTime());
			
			System.out.println("Table Number = " + r.getTableId());
			System.out.println("Name = " + r.getName());
			System.out.println("Number of People = "+r.getPax());
			System.out.println("Contact Number = " + r.getContact());
		}
		
		System.out.println("\n\nNumber of seats left : " + NoOfSeatsAvaiable);
	}
	
	//TODO Expiry checking part
	private void checkReservationValidityAndRemove(){
		
		for (Reservation r: reservationArray){
			if (r.getBookingDateAndTime().get(GregorianCalendar.DATE)==GregorianCalendar.getInstance().get(GregorianCalendar.DATE)){
				if (r.getBookingDateAndTime().get(GregorianCalendar.MONTH)==GregorianCalendar.getInstance().get(GregorianCalendar.MONTH)){
					if (r.getBookingDateAndTime().get(GregorianCalendar.HOUR_OF_DAY)==GregorianCalendar.getInstance().get(GregorianCalendar.HOUR_OF_DAY)){
						if (r.getBookingDateAndTime().get(GregorianCalendar.MINUTE) > 30 + r.getBookingDateAndTime().get(GregorianCalendar.MINUTE) ){
							reservationArray.remove(r);
							NoOfSeatsAvaiable++;
						}
					}
				}
			}
		}
	}
	
	private void formattedDate(GregorianCalendar l){
		
		System.out.print(l.get(GregorianCalendar.DATE)+"/"+l.get(GregorianCalendar.MONTH)
				+"/"+l.get(GregorianCalendar.YEAR)+"  Time: "+l.get(GregorianCalendar.HOUR_OF_DAY)+":"
				+l.get(GregorianCalendar.MINUTE)+"\n");
	}
	
	
}
