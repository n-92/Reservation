package reservation;

import java.util.GregorianCalendar;

public class Reservation {
	
	GregorianCalendar currentDateAndTime, reservationDateAndTime ;
	private String name;
	private long  contact;
	private int pax;
	private int seatID;
	
	
	public Reservation(String n, long c, int p, int seatId, GregorianCalendar now, GregorianCalendar reserve ){
		name = n;
		contact = c;
		pax = p;
		currentDateAndTime = now;
		reservationDateAndTime = reserve;
		seatID = seatId;
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
	
	public GregorianCalendar getBookingDateAndTime(){
		return currentDateAndTime;
	}

	public GregorianCalendar getReservationDateAndTime(){
		return reservationDateAndTime;
	}
	
	public int getTableId(){
		return seatID;
	}
	
}
