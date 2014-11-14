package rrpss;

/**
 * 
 * @author  Aung Naing Oo
 * @version 5.0
 * @since   2014-11-20
 * 
 */

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Scanner;

public class ReservationManager {
/*	
*This class takes care of all the activities related to the Reservation system 
*It also controls the timing 
*/
	private ArrayList<Reservation> reservationArray = new ArrayList<Reservation>(); 
	
	private TableManager tablemanager;
	Scanner scan = new Scanner(System.in);
	
	public ReservationManager(TableManager tablemanager){
		this.tablemanager = tablemanager;
	}
	
	
	
	public void checkReservation(){
		Calendar cal = Calendar.getInstance();
		for(int r=0;r<reservationArray.size();r++){
			if(reservationArray.get(r).getReservationDateAndTime().getTimeInMillis()<cal.getTimeInMillis()){
				tablemanager.unreserveTable(reservationArray.get(r).getTableId()-1);
				reservationArray.remove(r);
				System.out.println("Reservation Removed");
			}
		}
	}
	
	
	public boolean makeReservation (){
		Reservation reservation;
		String n; long c ; int p;
		Table table;
		if (tablemanager.getAvailable() > 0){
		
		System.out.print("Enter the name of the customer: ");
		n = scan.next();
		scan.nextLine();
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
		
		table=tablemanager.chooseTable(p);
		if (table==null){
			return false;
		}
		    
			Calendar reserve = setReservationDate();
			Calendar now = Calendar.getInstance();
			reservation = new Reservation(n, c, p, 
					table.getTableNumber(), now, reserve);
			tablemanager.reserveTable(table.getTableNumber()-1);
			reservationArray.add(reservation);
			return true;
		}
		System.out.println("Sorry, there is no more table left.");
		return false;
	}
	public boolean removeReservation(){
		String name;
		System.out.println("Enter the name:");
		name = scan.next();
		scan.nextLine();
		for (Reservation r: reservationArray){
			if (r.getName().equals(name)){
				tablemanager.unreserveTable((r.getTableId())-1);
				reservationArray.remove(r);
				System.out.println("Reservation Removed");
				return true;
			}
		}
		System.out.println("Reservation Not Found for this person");
		return false;
		
	}
	
	public void removeReservationByObject(Reservation reservation){
		reservationArray.remove(reservation);
		tablemanager.unreserveTable((reservation.getTableId())-1);
		System.out.println("Reservation Removed");
	}
	
	public Calendar setReservationDate(){
		/*The drawback of this technique is that let's say the reservation is supposed to be 
		 * made 30 minutes earlier from the current minute.  So when the program asks you to 
		 * key in the minute and you leave the computer at that state for 30 minutes and come 
		 * back later to key in the same minute, the system allows you pass and make 
		 * reservation even though the condition set for reservation to be made 30 minutes 
		 * earlier is violated since local variables are not being updated dynamically. 
		 * A better solution would be to use regular interrupts to update the variables.  
		 * Aung Naing Oo */
		
		
		int currentMonth = Calendar.getInstance().get(Calendar.MONTH)+1; //Month starts from zero in Java
		int currentYear = Calendar.getInstance().get(Calendar.YEAR);
		int currentDate = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
		int currentHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
		int currentMinute = Calendar.getInstance().get(Calendar.MINUTE);
		
		
		System.out.println("Current Year "+ currentYear );
		System.out.println("Current Month "+ currentMonth );
		System.out.println("Today's date "+ currentDate );
		System.out.println("Current Hour "+ currentHour );
		System.out.println("Current Minute "+ currentMinute );
		System.out.println("\t---\t---\t---\t---\t---");
		int inputMonth, inputYear, inputDate, inputHour, inputMinute;
		
		Calendar reserveDate = Calendar.getInstance();
		
		System.out.print("Enter the year: ");
		inputYear = scan.nextInt();
		scan.nextLine();
		
		while( inputYear < currentYear){
			System.out.println("Old year entry is not allowed !\n");
			System.out.print("Enter the year: ");
			inputYear = scan.nextInt();
			scan.nextLine();
		}	
		reserveDate.set(Calendar.YEAR,inputYear); 
	
		System.out.print("Enter the month: ");
		inputMonth = scan.nextInt();	
		scan.nextLine();
		
		while (inputMonth < 1 || inputMonth > 12){
			System.out.println("Invalid Month entry!");
			System.out.print("Enter the month again: ");
			inputMonth = scan.nextInt();
			scan.nextLine();
		}
		
		while((inputMonth < currentMonth) && (inputYear == currentYear)){
					
			System.out.println("Old month entry is not allowed !\n");
			System.out.print("Enter the month: ");
			inputMonth = scan.nextInt();
			scan.nextLine();
			
		}
		reserveDate.set(Calendar.MONTH,inputMonth-1);//MONTH is from 0 to 11.
		
		System.out.print("Enter the date for reservation: ");
		inputDate = scan.nextInt();
		scan.nextLine();
		
		while( inputDate < 1 || inputDate > 31){
			System.out.println("Invalid date entry!");
			System.out.print("Enter the date again: ");
			inputDate = scan.nextInt();
			scan.nextLine();
		}
		
		while((inputDate < currentDate) && (inputMonth == currentMonth)&&(inputYear==currentYear)){
			System.out.println("Past Day is not allowed!\n");
			System.out.print("Enter the date for reservation: ");
			inputDate = scan.nextInt();
			scan.nextLine();
		}
		reserveDate.set(Calendar.DAY_OF_MONTH,inputDate);
		
		System.out.print("Enter hour (24-hr format): ");
		inputHour = scan.nextInt();
		scan.nextLine();
		
		while( inputHour < 0 || inputHour > 23){
			System.out.println("Invalid hour entry!");
			System.out.print("Enter the hour again: ");
			inputHour = scan.nextInt();
			scan.nextLine();
		}
		
		while(inputHour < currentHour && inputDate == currentDate&&inputMonth == currentMonth&&inputYear==currentYear){
			
			if (inputHour < 0 && inputHour > 23){
				System.out.println("Invalid Hour entry");
			}
			else{
				System.out.println("Past hour is not allowed!\n");
				System.out.print("Enter hour (24-hr format): ");
				inputHour = scan.nextInt();
				scan.nextLine();
			}
		}
		reserveDate.set(Calendar.HOUR_OF_DAY,inputHour); //This uses 24 hour clock
		
		System.out.print("Enter minute: ");
		inputMinute = scan.nextInt();
		scan.nextLine();
		
		while(inputMinute < 0 || inputMinute > 59){
			System.out.println("Invalid Minute entry");
			System.out.print("Enter minute again: ");
			inputMinute = scan.nextInt();
			scan.nextLine();
		}
		
		while((inputMinute-currentMinute) <= 0 && inputHour == currentHour){
			
			//System.out.println("Reservation can only be made 30 minutes earlier or invalid input !");
			System.out.print("Invalid minute");
			System.out.print("Enter minute again: ");
			inputMinute = scan.nextInt();	
			scan.nextLine();
		}
		
		reserveDate.set(Calendar.MINUTE,inputMinute);
		System.out.println("Date Reserved!");
		return reserveDate;
	}
	
	public void printReservationFor(String name){
	
	for (Reservation r: reservationArray){
		if (r.getName().equals(name)){
			System.out.println("=======================\n\n");
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
			if(r==null){
		      System.out.println("No reservation");
		      return;
			}
			System.out.println("=======================\n\n");
			System.out.print("Date and time of Booking : "); 
			formattedDate(r.getBookingDateAndTime());
			
			System.out.print("Date and Time of Reservation : "); 
			formattedDate(r.getReservationDateAndTime());
			
			System.out.println("Table Number = " + r.getTableId());
			System.out.println("Name = " + r.getName());
			System.out.println("Number of People = "+r.getPax());
			System.out.println("Contact Number = " + r.getContact());
		}
		
		System.out.println("\n\nNumber of tables left : " + tablemanager.getAvailable());
	}
	
	public void writeReservation() throws IOException{
		FileWriter fw = new FileWriter("ReservationList.txt");
		for(Reservation r:reservationArray){
        fw.write("Name: "+r.getName()+"\r\n");
        fw.write("Number of People: "+r.getPax()+"\r\n");
        fw.write("TableID: "+r.getTableId()+"\r\n");
        fw.write("ContactNumber: "+r.getContact()+"\r\n");
        fw.write("ReservationDate: "+r.getReservationDateAndTime().get(Calendar.DAY_OF_MONTH)+"/"+r.getReservationDateAndTime().get(Calendar.MONTH)
				+"/"+r.getReservationDateAndTime().get(Calendar.YEAR)+"  Time: "+r.getReservationDateAndTime().get(Calendar.HOUR_OF_DAY)+":"
				+r.getReservationDateAndTime().get(Calendar.MINUTE)+"\r\n\r\n");
		
			
	}
		fw.close();

	}
	
	//TODO Expiry checking part
	private void checkReservationValidityAndRemove(){
		
		for (Reservation r: reservationArray){
			if (r.getBookingDateAndTime().get(Calendar.DAY_OF_MONTH)==Calendar.getInstance().get(Calendar.DAY_OF_MONTH)){
				if (r.getBookingDateAndTime().get(Calendar.MONTH)==Calendar.getInstance().get(Calendar.MONTH)){
					if (r.getBookingDateAndTime().get(Calendar.HOUR_OF_DAY)==Calendar.getInstance().get(Calendar.HOUR_OF_DAY)){
						if (r.getBookingDateAndTime().get(Calendar.MINUTE) > 30 + r.getBookingDateAndTime().get(Calendar.MINUTE) ){
							reservationArray.remove(r);
							tablemanager.increaseAvailable();;
						}
					}
				}
			}
		}
	}
	
	private void formattedDate(Calendar l){
		
		System.out.print(l.get(Calendar.DAY_OF_MONTH)+"/"+l.get(Calendar.MONTH)
				+"/"+l.get(Calendar.YEAR)+"  Time: "+l.get(Calendar.HOUR_OF_DAY)+":"
				+l.get(Calendar.MINUTE)+"\n");
	}
	
	
}
