package com.parking.tools;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;

import com.parkinglot.enums.VehicleType;
import com.parkinglot.vo.ParkingFloor;
import com.parkinglot.vo.ParkingLot;
import com.parkinglot.vo.ParkingSpot;

public class ParkingLotToolsImpl implements ParkingLotTools{
	
	public  static ParkingFloorTools parkingFloorTools;
	static {
		parkingFloorTools=(ParkingFloorTools) ToolsFactory.getTools("ParkingFloorTools");
	}
	public ParkingFloor findParkingFloor(ParkingLot parkingLot, boolean royalMember, boolean seniorCitizen,VehicleType type) {
		
		ParkingFloor parkingFloor=null;
		
		if(parkingLot.getPartiallyFilledFloorMap()!=null && parkingLot.getPartiallyFilledFloorMap().size()>0) {	
			//if floor are partial filled
			if(!seniorCitizen) {
				//if not a senior citizen then start allocating from top
				ArrayList keyList = new ArrayList(parkingLot.getPartiallyFilledFloorMap().keySet());
				for (int i = keyList.size() - 1; i >= 0; i--) {
					ParkingFloor floor = parkingLot.getPartiallyFilledFloorMap().get(keyList.get(i));
					ParkingSpot parkingSpot = parkingFloorTools.findParkingSpot(type, royalMember, floor);
					if(parkingSpot != null) {
						parkingFloor = floor;
						break;
					}
				}
			} else {
				//if senior citizen start allocating from bottom
				Set<Entry<Integer, ParkingFloor>> partialParkingFloorSet = parkingLot.getPartiallyFilledFloorMap()
						.entrySet();
				Iterator it = partialParkingFloorSet.iterator();
				while (it.hasNext()) {
					Entry<Integer, ParkingFloor> parkingFloorEntryObj = (Entry<Integer, ParkingFloor>) it.next();
					ParkingFloor floor = parkingFloorEntryObj.getValue();
					ParkingSpot parkingSpot = parkingFloorTools.findParkingSpot(type, royalMember, floor);
					if (parkingSpot != null) {
						parkingFloor = floor;
						break;
					}
				}
			}
		}else {
			//if floor are need to picked from available floor means partial filled floor are not available
				Set<Entry<Integer, ParkingFloor>> availableParkingFloorSet=parkingLot.getAvailableParkingFloorMap().entrySet();
				Iterator it = availableParkingFloorSet.iterator();
				while(it.hasNext()) {
					Entry<Integer, ParkingFloor> parkingFloorEntryObj = (Entry<Integer, ParkingFloor>) it.next();
					parkingFloor = parkingFloorEntryObj.getValue();
						break;
				}
			
		}
		
		return parkingFloor;
	}
	
	public ParkingSpot allocateParkingFloor(ParkingLot parkingLot,boolean royalMember,VehicleType type,boolean seniorCitizen) {
		ParkingFloor parkingFloor = findParkingFloor(parkingLot, royalMember, seniorCitizen, type);
		ParkingSpot spotAllocatedSuccess = parkingFloorTools.allocateParkingSpot(parkingFloor, royalMember, type);
		if(spotAllocatedSuccess!=null) {
			if(parkingLot.getPartiallyFilledFloorMap()!=null && parkingLot.getPartiallyFilledFloorMap().containsKey(parkingFloor.getFloorNumber())
					&& parkingFloor.isParkingFloorFull()) {
				
				parkingLot.getPartiallyFilledFloorMap().remove(parkingFloor.getFloorNumber());
				parkingLot.getFullyOccuParkingFloorMap().put(parkingFloor.getFloorNumber(), parkingFloor);
				
			}else if(parkingLot.getAvailableParkingFloorMap()!=null && parkingLot.getAvailableParkingFloorMap().containsKey(parkingFloor.getFloorNumber())){
				parkingLot.getAvailableParkingFloorMap().remove(parkingFloor.getFloorNumber());
				parkingLot.getPartiallyFilledFloorMap().put(parkingFloor.getFloorNumber(), parkingFloor);
			}
		}
		return spotAllocatedSuccess;
		
	}
	
	
	
	public boolean releaseFromParkingFloor(ParkingLot parkingLot,int floorNumber,int spotId) {
		if(parkingLot.getPartiallyFilledFloorMap()!=null && parkingLot.getPartiallyFilledFloorMap().containsKey(floorNumber)) {
			ParkingFloor parkingFloor=parkingLot.getPartiallyFilledFloorMap().get(floorNumber);
			parkingFloorTools.releaseparkingspot(parkingFloor, spotId);
			String parkingStatustOnFloor = parkingFloor.parkingStatustOnFloor();
			//if only one vehicle is there and got removed from partial then move to available
			if("EMPTY".equals(parkingStatustOnFloor)) {
				parkingLot.getAvailableParkingFloorMap().put(floorNumber, parkingFloor);
				parkingLot.getPartiallyFilledFloorMap().remove(floorNumber);
			}
		}else if(parkingLot.getFullyOccuParkingFloorMap()!=null && parkingLot.getFullyOccuParkingFloorMap().containsKey(floorNumber)){
			ParkingFloor parkingFloor=parkingLot.getFullyOccuParkingFloorMap().get(floorNumber);
			parkingFloorTools.releaseparkingspot(parkingFloor, spotId);
			parkingLot.getFullyOccuParkingFloorMap().get(floorNumber);
			parkingLot.getPartiallyFilledFloorMap().put(floorNumber, parkingFloor);
			String parkingStatustOnFloor = parkingFloor.parkingStatustOnFloor();
		}
		
		
		return true;
	}
	
	
}
