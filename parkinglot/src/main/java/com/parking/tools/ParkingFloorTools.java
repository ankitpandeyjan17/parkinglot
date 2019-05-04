package com.parking.tools;

import java.util.Map;

import com.parkinglot.enums.VehicleType;
import com.parkinglot.vo.ParkingFloor;
import com.parkinglot.vo.ParkingSpot;

public interface ParkingFloorTools extends ToolsFactory {
	public abstract ParkingSpot findParkingSpot(VehicleType type,boolean royalFamilyFlag,ParkingFloor parkingFloor);
	public abstract ParkingSpot allocateParkingSpot(ParkingFloor parkingFloor,boolean royalFamilyFlag,VehicleType type);
	public abstract boolean releaseparkingspot(ParkingFloor parkingFloor, int parkingSpotId);
	public abstract ParkingSpot checkForRoyalParkingSlot(Map<Integer, ParkingSpot> parkingMap);
	public abstract ParkingSpot checkForRandomParkingSpot(Map<Integer, ParkingSpot> parkingMap,boolean royalFamilyFlag,ParkingFloor parkingFloor); 
}
