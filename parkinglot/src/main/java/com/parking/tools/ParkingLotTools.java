package com.parking.tools;

import com.parkinglot.enums.VehicleType;
import com.parkinglot.vo.ParkingFloor;
import com.parkinglot.vo.ParkingLot;
import com.parkinglot.vo.ParkingSpot;

public interface ParkingLotTools extends ToolsFactory {
	public abstract ParkingFloor findParkingFloor(ParkingLot parkingLot, boolean royalMember, boolean seniorCitizen,VehicleType type);
	public abstract ParkingSpot allocateParkingFloor(ParkingLot parkingLot,boolean royalMember,VehicleType type,boolean seniorCitizen);
	public abstract boolean releaseFromParkingFloor(ParkingLot parkingLot,int floorNumber,int spotId);
}
