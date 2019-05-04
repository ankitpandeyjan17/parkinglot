package com.parking.tools;

import com.parkinglot.enums.VehicleType;
import com.parkinglot.vo.Booking;
import com.parkinglot.vo.Customer;
import com.parkinglot.vo.ParkingSpot;

public interface BookingTools extends ToolsFactory {
	public abstract Booking confirmBooking(Customer customer,ParkingSpot spot,VehicleType vehicleType,String vehicleNumber,boolean royalPark);
	public abstract boolean vacateParking(String  bookingId);
	
}
