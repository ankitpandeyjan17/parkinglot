package com.parkinglot.vo;

import java.util.Date;

import com.parkinglot.enums.VehicleType;

public class Booking {
	
	private String bookingId;
	private Customer customer;
	private String bookingStatus;
	private Date dateOfBooking;
	private VehicleType vehicleType;
	private double bookingCost;
	private int floorNumber;
	private int spotNumber;
	private String vehicleNumber;
	private Date dateOfVacate;
	private boolean royalParking; 
	
	public String getBookingId() {
		return bookingId;
	}
	public void setBookingId(String bookingId) {
		this.bookingId = bookingId;
	}
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	public String getBookingStatus() {
		return bookingStatus;
	}
	public void setBookingStatus(String bookingStatus) {
		this.bookingStatus = bookingStatus;
	}
	public Date getDateOfBooking() {
		return dateOfBooking;
	}
	public void setDateOfBooking(Date dateOfBooking) {
		this.dateOfBooking = dateOfBooking;
	}
	public VehicleType getVehicleType() {
		return vehicleType;
	}
	public void setVehicleType(VehicleType vehicleType) {
		this.vehicleType = vehicleType;
	}
	public double getBookingCost() {
		return bookingCost;
	}
	public void setBookingCost(double bookingCost) {
		this.bookingCost = bookingCost;
	}
	public int getFloorNumber() {
		return floorNumber;
	}
	public void setFloorNumber(int floorNumber) {
		this.floorNumber = floorNumber;
	}
	public int getSpotNumber() {
		return spotNumber;
	}
	public void setSpotNumber(int spotNumber) {
		this.spotNumber = spotNumber;
	}
	public String getVehicleNumber() {
		return vehicleNumber;
	}
	public void setVehicleNumber(String vehicleNumber) {
		this.vehicleNumber = vehicleNumber;
	}
	public Date getDateOfVacate() {
		return dateOfVacate;
	}
	public void setDateOfVacate(Date dateOfVacate) {
		this.dateOfVacate = dateOfVacate;
	}
	public boolean isRoyalParking() {
		return royalParking;
	}
	public void setRoyalParking(boolean royalParking) {
		this.royalParking = royalParking;
	}
	@Override
	public String toString() {
		return "Booking [bookingId=" + bookingId + ", customer=" + customer + ", bookingStatus=" + bookingStatus
				+ ", dateOfBooking=" + dateOfBooking + ", vehicleType=" + vehicleType + ", bookingCost=" + bookingCost
				+ ", floorNumber=" + floorNumber + ", spotNumber=" + spotNumber + ", vehicleNumber=" + vehicleNumber
				+ ", dateOfVacate=" + dateOfVacate + ", royalParking=" + royalParking + "]";
	}
	
	

}
