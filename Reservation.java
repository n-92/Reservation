package rrpss;

import java.util.Calendar;

public class Reservation {
	
	Calendar currentDateAndTime, reservationDateAndTime ;
	private String name;
	private long  contact;
	private int pax;
	private int tableID;
	
	
	public Reservation(String n, long c, int p, int tableId, Calendar now, Calendar reserve ){
		name = n;
		contact = c;
		pax = p;
		currentDateAndTime = now;
		reservationDateAndTime = reserve;
		tableID = tableId;
	}
	
	public String getName (){
		return name;
	}
	
	public long getContact (){
		return contact;
	}
	
	public int getPax (){
		return pax;
	}
	
	public Calendar getBookingDateAndTime(){
		return currentDateAndTime;
	}

	public Calendar getReservationDateAndTime(){
		return reservationDateAndTime;
	}
	
	
	public int getTableId(){
		return tableID;
	}
	
}
