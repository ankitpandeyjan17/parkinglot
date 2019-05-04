package com.parkinglot.vo;

import com.parkinglot.enums.VehicleType;

public class ParkingSpot {
	private int vehicleCounter;
	private boolean stack;
	private int spotId;
	private VehicleType vehicleType;
	private boolean royalParked;
	private int floorNumber;
	public int getVehicleCounter() {
		return vehicleCounter;
	}
	public void setVehicleCounter(int vehicleCounter) {
		this.vehicleCounter = vehicleCounter;
	}
	public boolean isStack() {
		return stack;
	}
	public void setStack(boolean stack) {
		this.stack = stack;
	}
	public int getSpotId() {
		return spotId;
	}
	public void setSpotId(int spotId) {
		this.spotId = spotId;
	}
	public VehicleType getVehicleType() {
		return vehicleType;
	}
	public void setVehicleType(VehicleType vehicleType) {
		this.vehicleType = vehicleType;
	}
	public boolean isRoyalParked() {
		return royalParked;
	}
	public void setRoyalParked(boolean royalParked) {
		this.royalParked = royalParked;
	}
	public int getFloorNumber() {
		return floorNumber;
	}
	public void setFloorNumber(int floorNumber) {
		this.floorNumber = floorNumber;
	}

	public boolean isFull() {

		if (vehicleType == null)
			return false;
		
		if(isRoyalParked()){
			return true;
		}
		
		if (vehicleType.equals(VehicleType.BIKE) && vehicleCounter == 5) {
			return true;
		}
		if (vehicleType.equals(VehicleType.CAR) && vehicleCounter == 2) {
			return true;
		}
		return false;
	}
	@Override
	public String toString() {
		return "ParkingSpot [vehicleCounter=" + vehicleCounter + ", stack=" + stack + ", spotId=" + spotId
				+ ", vehicleType=" + vehicleType + ", royalParked=" + royalParked + ", floorNumber=" + floorNumber
				+ "]";
	}
	
}
