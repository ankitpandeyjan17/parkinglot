package com.parking.tools;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.parkinglot.enums.VehicleType;
import com.parkinglot.vo.Booking;
import com.parkinglot.vo.Customer;
import com.parkinglot.vo.ParkingSpot;

public class BookingToolsImpl implements BookingTools{

	static int bookingIdSequence=1000;
	
	
	private Map<String, Booking> releasedBooking=new HashMap<String,Booking>();
	private Map<String, Booking> allocatedBooking=new HashMap<String,Booking>();
	private Map<String, Customer> registeredCustomer=new HashMap<String,Customer>();
	
	public Booking confirmBooking(Customer customer,ParkingSpot spot,VehicleType vehicleType,String vehicleNumber,boolean royalPark) {
		Booking bookingObj=new Booking();
		bookingObj.setBookingId(String.valueOf(++bookingIdSequence));
		bookingObj.setBookingStatus("PARKED");
		bookingObj.setCustomer(customer);
		bookingObj.setDateOfBooking(new Date());
		bookingObj.setFloorNumber(spot.getFloorNumber());
		bookingObj.setSpotNumber(spot.getSpotId());
		bookingObj.setVehicleNumber(vehicleNumber);	
		bookingObj.setVehicleType(vehicleType);
		bookingObj.setRoyalParking(royalPark);
		allocatedBooking.put(bookingObj.getBookingId(), bookingObj);
		registeredCustomer.put(customer.getPhone(), customer);
		return bookingObj;
	}

	public boolean vacateParking(String  bookingId) {
		
		Booking booking = allocatedBooking.get(bookingId);
		
		if(booking==null) {
			return false;
		}
		booking.setDateOfVacate(new Date());
		long timeInterval=(booking.getDateOfVacate().getTime()-booking.getDateOfBooking().getTime())/(60*60*1000)%24;
		// discount to registered customer
		if(registeredCustomer.containsKey(booking.getCustomer().getPhone())) {
			if(booking.isRoyalParking()) {
				booking.setBookingCost((timeInterval*100)-20);
			}else {
				if(booking.getVehicleType().equals(VehicleType.CAR)){
					booking.setBookingCost((timeInterval*60)-10);
				}else if(booking.getVehicleType().equals(VehicleType.BIKE)){
					booking.setBookingCost((timeInterval*40)-10);
				}
			}
		}else {
		if(booking.isRoyalParking()) {
			booking.setBookingCost(timeInterval*100);
		}else {
			if(booking.getVehicleType().equals(VehicleType.CAR)){
				booking.setBookingCost(timeInterval*60);
			}else if(booking.getVehicleType().equals(VehicleType.BIKE)){
				booking.setBookingCost(timeInterval*40);
			}
		}
		}
		
		System.out.println("Thanks for coming!!!!Visit Again::Amount Paid:::"+booking.getBookingCost());
		booking.setBookingStatus("LEFT PARKING");
		releasedBooking.put(booking.getBookingId(),booking);
		allocatedBooking.remove(booking.getBookingId());
		return true;
	}
	
	public Map<String, Booking> getReleasedBooking() {
		return releasedBooking;
	}

	public void setReleasedBooking(Map<String, Booking> releasedBooking) {
		this.releasedBooking = releasedBooking;
	}

	public Map<String, Booking> getAllocatedBooking() {
		return allocatedBooking;
	}

	public void setAllocatedBooking(Map<String, Booking> allocatedBooking) {
		this.allocatedBooking = allocatedBooking;
	}

	public Map<String, Customer> getRegisteredCustomer() {
		return registeredCustomer;
	}

	public void setRegisteredCustomer(Map<String, Customer> registeredCustomer) {
		this.registeredCustomer = registeredCustomer;
	}
	
}
