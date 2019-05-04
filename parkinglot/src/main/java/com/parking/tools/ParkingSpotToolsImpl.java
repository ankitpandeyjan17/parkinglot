package com.parking.tools;

import com.parkinglot.enums.VehicleType;
import com.parkinglot.vo.ParkingSpot;

public class ParkingSpotToolsImpl implements ParkingSpotTools {

	// addVehicleToSlot
	public boolean addVehicleToSpot(VehicleType type,ParkingSpot parkingSpot) {
		if(parkingSpot.getVehicleType()==null) {
			parkingSpot.setVehicleType(type);
		}
		parkingSpot.setVehicleCounter(parkingSpot.getVehicleCounter()+1);
		return true;
	}
	
	//removeVehicleFromSpot
	
	public boolean removeVehicleFromSpot(ParkingSpot parkingSpot) {
		
		if(parkingSpot==null) {
			return false;
		}
		if(parkingSpot.getVehicleCounter()==1) {
			parkingSpot.setVehicleType(null);
			parkingSpot.setVehicleCounter(0);
			parkingSpot.setRoyalParked(false);
			return true;
		}
		parkingSpot.setVehicleCounter(parkingSpot.getVehicleCounter()-1);
		return true;
	}
	
}
