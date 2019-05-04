package com.parkinglot.vo;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.parkinglot.enums.VehicleType;

public class ParkingFloor {

	private int floorNumber;
	
	private Map<Integer, ParkingSpot> availableParkingSpotsMap = new HashMap<Integer, ParkingSpot>();
	private Map<Integer, ParkingSpot> partiallyFilledSpotsMap = new HashMap<Integer, ParkingSpot>();
	private Map<Integer, ParkingSpot> fullyOccuParkingSpotsMap = new HashMap<Integer, ParkingSpot>();
	private boolean senierCitizenParkingAvailable;
	
//	private List<ParkingSpot> royalOccParkingSpots;
	
	
	public int getFloorNumber() {
		return floorNumber;
	}
	public void setFloorNumber(int floorNumber) {
		this.floorNumber = floorNumber;
	}
	
	
	public Map<Integer, ParkingSpot> getAvailableParkingSpotsMap() {
		return availableParkingSpotsMap;
	}
	public void setAvailableParkingSpotsMap(Map<Integer, ParkingSpot> availableParkingSpotsMap) {
		this.availableParkingSpotsMap = availableParkingSpotsMap;
	}
	public Map<Integer, ParkingSpot> getPartiallyFilledSpotsMap() {
		return partiallyFilledSpotsMap;
	}
	public void setPartiallyFilledSpotsMap(Map<Integer, ParkingSpot> partiallyFilledSpotsMap) {
		this.partiallyFilledSpotsMap = partiallyFilledSpotsMap;
	}
	public Map<Integer, ParkingSpot> getFullyOccuParkingSpotsMap() {
		return fullyOccuParkingSpotsMap;
	}
	public void setFullyOccuParkingSpotsMap(Map<Integer, ParkingSpot> fullyOccuParkingSpotsMap) {
		this.fullyOccuParkingSpotsMap = fullyOccuParkingSpotsMap;
	}
	public boolean isSenierCitizenParkingAvailable() {
		return senierCitizenParkingAvailable;
	}
	public void setSenierCitizenParkingAvailable(boolean senierCitizenParkingAvailable) {
		this.senierCitizenParkingAvailable = senierCitizenParkingAvailable;
	}
	
	public boolean isParkingFloorFull() {	
		if(this.getFullyOccuParkingSpotsMap()!=null && this.getFullyOccuParkingSpotsMap().size()==20) {
			return true;
		}
		return false;
	}

	/**
	 * @return status
	 *   function will return status on the basis of difference between total number
	// of possible vehicle can be accommodated and actual parked vehicle count on spot
	 */
	public String parkingStatustOnFloor() {
		int parkedVechicalCount =0, totalParkingCapacity =0;
			if(this.getFullyOccuParkingSpotsMap()!=null) {
				Set<Entry<Integer,ParkingSpot>> spotEntryObj=this.getFullyOccuParkingSpotsMap().entrySet();
				Iterator it=spotEntryObj.iterator();
				while(it.hasNext()) {
					Entry< Integer, ParkingSpot> entry=(Entry<Integer, ParkingSpot>) it.next();
					ParkingSpot parkingSpot=entry.getValue();
					//if royal increment the the vehicle count by 1 since ony one vehicle is allowed on that spot 
					if( parkingSpot.isRoyalParked()) {
						totalParkingCapacity ++;
					}else if(VehicleType.BIKE.equals(parkingSpot.getVehicleType())) {
						//if normal park then increment the vehicle count by 5 in case of BIKE
						totalParkingCapacity +=5; 
					}else if(VehicleType.CAR.equals(parkingSpot.getVehicleType())) {
						//if normal park then increment the vehicle count by 5 in case of CAR
						totalParkingCapacity +=2; 
					}
					parkedVechicalCount+=parkingSpot.getVehicleCounter();
				}
			}
		

			if(parkedVechicalCount ==0 ) {
				// if only one vehicle is there on floor and got removed from spot then ins that
				// case fully will come null and parked vehicle count will be zero so status is empty
				return "EMPTY";
			}else {
				//calculate difference and if it is greater than 0 then status will be partial
				return totalParkingCapacity - parkedVechicalCount >0 ? "PARTIALLY":"FULL";
			}
		}
	
	
	
}
