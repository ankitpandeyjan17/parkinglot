package com.parking.tools;

import com.parkinglot.enums.VehicleType;
import com.parkinglot.vo.ParkingSpot;

public interface ParkingSpotTools extends ToolsFactory {
	public abstract boolean removeVehicleFromSpot(ParkingSpot parkingSpot);
	public abstract boolean addVehicleToSpot(VehicleType type,ParkingSpot parkingSpot);
}
