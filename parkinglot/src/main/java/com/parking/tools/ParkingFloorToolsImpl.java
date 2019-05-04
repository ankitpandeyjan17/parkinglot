package com.parking.tools;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.parkinglot.enums.VehicleType;
import com.parkinglot.vo.ParkingFloor;
import com.parkinglot.vo.ParkingSpot;

public class ParkingFloorToolsImpl implements ParkingFloorTools{

	
	static ParkingSpotTools parkingSpotTools=null;
	static {
		parkingSpotTools=(ParkingSpotToolsImpl) ToolsFactory.getTools("ParkingSpotTools");
	}
	
	public ParkingSpot findParkingSpot(VehicleType type,boolean royalFamilyFlag,ParkingFloor parkingFloor) {
		ParkingSpot parkingSpot=null;
		//if not royal family and some spot is partial filled
		if(!royalFamilyFlag &&parkingFloor.getPartiallyFilledSpotsMap()!=null && parkingFloor.getPartiallyFilledSpotsMap().size()>0) {
			Set partiallyFilledSet=parkingFloor.getPartiallyFilledSpotsMap().entrySet();
			Iterator it=partiallyFilledSet.iterator();
			while (it.hasNext()) {
				Map.Entry<Integer, ParkingSpot> partiallyParkingobj=(Entry) it.next();
				if( partiallyParkingobj.getValue().getVehicleType().equals(type) && !partiallyParkingobj.getValue().isFull()) {
					parkingSpot = partiallyParkingobj.getValue();
					break;
				}
			}
		}else if(royalFamilyFlag && parkingFloor.getAvailableParkingSpotsMap() != null && !parkingFloor.getAvailableParkingSpotsMap().isEmpty()){
			// if royal family then first allocate to corner most spots
			parkingSpot = this.checkForRoyalParkingSlot(parkingFloor.getAvailableParkingSpotsMap());
			if(parkingSpot==null) {
				//if corner spots are not available for parking then check for random spots such that previous and nex parking is not there
				parkingSpot = this.checkForRandomParkingSpot(parkingFloor.getAvailableParkingSpotsMap(), royalFamilyFlag,parkingFloor);
			}
			parkingSpot.setRoyalParked(royalFamilyFlag);
			
		} else {
			// agal bagal royal na ho 
			parkingSpot = this.checkForRandomParkingSpot(parkingFloor.getAvailableParkingSpotsMap(), royalFamilyFlag,parkingFloor);
		}
		return parkingSpot;
	}
	
	
	public ParkingSpot allocateParkingSpot(ParkingFloor parkingFloor,boolean royalFamilyFlag,VehicleType type) {
		ParkingSpot parkingSpot = findParkingSpot(type, royalFamilyFlag, parkingFloor);
		parkingSpot.setRoyalParked(royalFamilyFlag);
		
		boolean success = parkingSpotTools.addVehicleToSpot(type, parkingSpot);
		
		if(success && parkingFloor.getAvailableParkingSpotsMap().containsKey((parkingSpot.getSpotId()))) {
			parkingFloor.getAvailableParkingSpotsMap().remove(parkingSpot.getSpotId());
			if(royalFamilyFlag) {
				parkingFloor.getFullyOccuParkingSpotsMap().put(parkingSpot.getSpotId(),parkingSpot);
				if((parkingSpot.getSpotId() %5 == 1) ) {
					ParkingSpot parkingSpot2 = parkingFloor.getAvailableParkingSpotsMap().get(parkingSpot.getSpotId() +1);
					if(VehicleType.CAR.equals(type)) {
						parkingSpot2.setVehicleType(type);
						parkingSpot2.setVehicleCounter(2);
					}else if(VehicleType.BIKE.equals(type)) {
						parkingSpot2.setVehicleType(type);
						parkingSpot2.setVehicleCounter(5);
					}
					parkingFloor.getAvailableParkingSpotsMap().remove(parkingSpot2.getSpotId());
					parkingFloor.getFullyOccuParkingSpotsMap().put(parkingSpot2.getSpotId(),parkingSpot2);
					
				}else if(parkingSpot.getSpotId() %5 == 0 ) {
					ParkingSpot parkingSpot2 = parkingFloor.getAvailableParkingSpotsMap().get(parkingSpot.getSpotId() -1);
					parkingFloor.getAvailableParkingSpotsMap().remove(parkingSpot2.getSpotId());
					parkingFloor.getFullyOccuParkingSpotsMap().put(parkingSpot2.getSpotId(),parkingSpot2);
					if(VehicleType.CAR.equals(type)) {
						parkingSpot2.setVehicleType(type);
						parkingSpot2.setVehicleCounter(2);
					}else if(VehicleType.BIKE.equals(type)) {
						parkingSpot2.setVehicleType(type);
						parkingSpot2.setVehicleCounter(5);
					}
				}else {
					ParkingSpot parkingSpotPre = parkingFloor.getAvailableParkingSpotsMap().get(parkingSpot.getSpotId() -1);
					ParkingSpot parkingSpotNext = parkingFloor.getAvailableParkingSpotsMap().get(parkingSpot.getSpotId() + 1);
					parkingFloor.getAvailableParkingSpotsMap().remove(parkingSpotPre.getSpotId());
					parkingFloor.getAvailableParkingSpotsMap().remove(parkingSpotNext.getSpotId());
					parkingFloor.getFullyOccuParkingSpotsMap().put(parkingSpotNext.getSpotId(),parkingSpotNext);
					parkingFloor.getFullyOccuParkingSpotsMap().put(parkingSpotPre.getSpotId(),parkingSpotPre);
					if(VehicleType.CAR.equals(type)) {
						parkingSpotPre.setVehicleType(type);
						parkingSpotPre.setVehicleCounter(2);
						parkingSpotNext.setVehicleType(type);
						parkingSpotNext.setVehicleCounter(2);
					}else if(VehicleType.BIKE.equals(type)) {
						parkingSpotPre.setVehicleType(type);
						parkingSpotPre.setVehicleCounter(5);
						parkingSpotNext.setVehicleType(type);
						parkingSpotNext.setVehicleCounter(5);
					}
				}
			}else{
				parkingFloor.getPartiallyFilledSpotsMap().put(parkingSpot.getSpotId(),parkingSpot);
			}
		}
		if(success && parkingFloor.getPartiallyFilledSpotsMap().containsKey(parkingSpot.getSpotId())) {
			if(parkingSpot.isFull()) {
		 		parkingFloor.getPartiallyFilledSpotsMap().remove(parkingSpot.getSpotId());
				parkingFloor.getFullyOccuParkingSpotsMap().put(parkingSpot.getSpotId(),parkingSpot);
			}
		}
		parkingSpot.setFloorNumber(parkingFloor.getFloorNumber());
		return parkingSpot;
	}
	
	
	public boolean releaseparkingspot(ParkingFloor parkingFloor, int parkingSpotId) {
		ParkingSpot parkingSpot = null;
		if(parkingFloor.getPartiallyFilledSpotsMap().containsKey(parkingSpotId)) {
			parkingSpot = parkingFloor.getPartiallyFilledSpotsMap().get(parkingSpotId);
		}else if(parkingFloor.getFullyOccuParkingSpotsMap().containsKey(parkingSpotId)) {
			parkingSpot=parkingFloor.getFullyOccuParkingSpotsMap().get(parkingSpotId);
		}
		boolean royalFamilyFlag = parkingSpot.isRoyalParked();
		boolean removeVehicleFromSpot = parkingSpotTools.removeVehicleFromSpot(parkingSpot);
		
		if(removeVehicleFromSpot && parkingSpot.getVehicleCounter()==0) {
			
			if(parkingFloor.getFullyOccuParkingSpotsMap().containsKey(parkingSpot.getSpotId())) {
				if(royalFamilyFlag && parkingSpot.getSpotId() %5 ==1) {
					ParkingSpot parkingSpot2 = parkingFloor.getFullyOccuParkingSpotsMap().get(parkingSpot.getSpotId()+1);
					parkingSpot2.setVehicleCounter(0);
					parkingSpot2.setVehicleType(null);
					parkingFloor.getFullyOccuParkingSpotsMap().remove(parkingSpot2.getSpotId());
					parkingFloor.getAvailableParkingSpotsMap().put(parkingSpot.getSpotId(),parkingSpot);
				}else if(royalFamilyFlag && parkingSpot.getSpotId() %5 ==0) {
					ParkingSpot parkingSpot2 = parkingFloor.getFullyOccuParkingSpotsMap().get(parkingSpot.getSpotId() - 1);
					parkingSpot2.setVehicleCounter(0);
					parkingSpot2.setVehicleType(null);
					parkingFloor.getFullyOccuParkingSpotsMap().remove(parkingSpot2.getSpotId());
					parkingFloor.getAvailableParkingSpotsMap().put(parkingSpot.getSpotId(),parkingSpot);
				} else if(royalFamilyFlag) {
					ParkingSpot parkingSpotNext = parkingFloor.getFullyOccuParkingSpotsMap().get(parkingSpot.getSpotId()+1);
					ParkingSpot parkingSpotPre = parkingFloor.getFullyOccuParkingSpotsMap().get(parkingSpot.getSpotId() - 1);
					parkingSpotNext.setVehicleCounter(0);
					parkingSpotNext.setVehicleType(null);
					parkingSpotPre.setVehicleCounter(0);
					parkingSpotPre.setVehicleType(null);
					parkingFloor.getFullyOccuParkingSpotsMap().remove(parkingSpotNext.getSpotId());
					parkingFloor.getFullyOccuParkingSpotsMap().remove(parkingSpotPre.getSpotId());
					parkingFloor.getAvailableParkingSpotsMap().put(parkingSpotNext.getSpotId(),parkingSpotNext);
					parkingFloor.getAvailableParkingSpotsMap().put(parkingSpotPre.getSpotId(),parkingSpotPre);
				}
			}else if(parkingFloor.getPartiallyFilledSpotsMap().containsKey(parkingSpot.getSpotId())) {
				parkingFloor.getPartiallyFilledSpotsMap().remove(parkingSpot.getSpotId());
				parkingFloor.getAvailableParkingSpotsMap().put(parkingSpot.getSpotId(),parkingSpot);
			}
		}else if(removeVehicleFromSpot && parkingSpot.getVehicleCounter() > 0 ) {
			if(parkingFloor.getFullyOccuParkingSpotsMap().containsKey(parkingSpot.getSpotId())) {
				parkingFloor.getFullyOccuParkingSpotsMap().remove(parkingSpot.getSpotId());
				parkingFloor.getPartiallyFilledSpotsMap().put(parkingSpot.getSpotId(),parkingSpot);
			}
		}
		return removeVehicleFromSpot;
	}
	
	public ParkingSpot checkForRoyalParkingSlot(Map<Integer, ParkingSpot> parkingMap) {
		Set availableParkingSet =parkingMap.entrySet();
		ParkingSpot parkingSpot=null;
		Iterator it =availableParkingSet.iterator();
		while(it.hasNext()) {
			Map.Entry<Integer, ParkingSpot> availableParkingobj=(Entry) it.next();
			if(availableParkingobj.getKey()%5==0 && parkingMap.containsKey((availableParkingobj.getKey()%5)-1)) {
				parkingSpot= availableParkingobj.getValue();
			}else if(availableParkingobj.getKey()%5==1 && parkingMap.containsKey(availableParkingobj.getKey()%5+1)) {
				parkingSpot= availableParkingobj.getValue();
			}
		}
		
		return parkingSpot;
	}
	
	public ParkingSpot checkForRandomParkingSpot(Map<Integer, ParkingSpot> parkingMap, boolean royalFamilyFlag,
			ParkingFloor parkingFloor) {
		ParkingSpot parkingSpot = null;
		Map<Integer, ParkingSpot> fullyOccParkMap = parkingFloor.getFullyOccuParkingSpotsMap();
		Set availableParkingSet = parkingMap.entrySet();
		Iterator it = availableParkingSet.iterator();

		while (it.hasNext()) {
			Map.Entry<Integer, ParkingSpot> availableParkingobj = (Entry) it.next();
			if (royalFamilyFlag) {
				
				if (parkingMap.containsKey(availableParkingobj.getKey() + 1)
						&& parkingMap.containsKey(availableParkingobj.getKey() - 1)) {
					parkingSpot = availableParkingobj.getValue();
					break;
				}

			} else {
				if (availableParkingobj.getKey() % 5 == 0) {
					if (parkingFloor.getFullyOccuParkingSpotsMap().containsKey(availableParkingobj.getKey() - 1)
							&& !parkingFloor.getFullyOccuParkingSpotsMap().get(availableParkingobj.getKey() - 1)
									.isRoyalParked()) {
						parkingSpot = availableParkingobj.getValue();
						break;
					}
					if (parkingFloor.getPartiallyFilledSpotsMap().containsKey(availableParkingobj.getKey() - 1)) {
						parkingSpot = availableParkingobj.getValue();
						break;
					}
					if (parkingFloor.getAvailableParkingSpotsMap().containsKey(availableParkingobj.getKey() - 1)) {
						parkingSpot = availableParkingobj.getValue();
						break;
					}
				} else if (availableParkingobj.getKey() % 5 == 1) {
					if (parkingFloor.getFullyOccuParkingSpotsMap().containsKey(availableParkingobj.getKey() + 1)
							&& !parkingFloor.getFullyOccuParkingSpotsMap().get(availableParkingobj.getKey() + 1)
									.isRoyalParked()) {
						parkingSpot = availableParkingobj.getValue();
						break;
					}
					if (parkingFloor.getPartiallyFilledSpotsMap().containsKey(availableParkingobj.getKey() + 1)) {
						parkingSpot = availableParkingobj.getValue();
						break;
					}
					if (parkingFloor.getAvailableParkingSpotsMap().containsKey(availableParkingobj.getKey() + 1)) {
						parkingSpot = availableParkingobj.getValue();
						break;
					}
				} else {
					if ((parkingFloor.getFullyOccuParkingSpotsMap().containsKey(availableParkingobj.getKey() + 1)
							&& !parkingFloor.getFullyOccuParkingSpotsMap().get(availableParkingobj.getKey() + 1)
									.isRoyalParked())
							&& (parkingFloor.getFullyOccuParkingSpotsMap().containsKey(availableParkingobj.getKey() - 1)
									&& !parkingFloor.getFullyOccuParkingSpotsMap().get(availableParkingobj.getKey() - 1)
											.isRoyalParked())) {
						parkingSpot = availableParkingobj.getValue();
						break;
					} else if ((parkingFloor.getFullyOccuParkingSpotsMap().containsKey(availableParkingobj.getKey() - 1)
							&& !parkingFloor.getFullyOccuParkingSpotsMap().get(availableParkingobj.getKey() - 1)
									.isRoyalParked())
							&& (parkingFloor.getPartiallyFilledSpotsMap()
									.containsKey(availableParkingobj.getKey() + 1))) {
						parkingSpot = availableParkingobj.getValue();
						break;
					} else if ((parkingFloor.getFullyOccuParkingSpotsMap().containsKey(availableParkingobj.getKey() - 1)
							&& !parkingFloor.getFullyOccuParkingSpotsMap().get(availableParkingobj.getKey() - 1)
									.isRoyalParked())
							&& (parkingFloor.getAvailableParkingSpotsMap()
									.containsKey(availableParkingobj.getKey() + 1))) {
						parkingSpot = availableParkingobj.getValue();
						break;
					}
					if ((parkingFloor.getPartiallyFilledSpotsMap().containsKey(availableParkingobj.getKey() - 1))
							&& (parkingFloor.getFullyOccuParkingSpotsMap().containsKey(availableParkingobj.getKey() + 1)
									&& !parkingFloor.getFullyOccuParkingSpotsMap().get(availableParkingobj.getKey() + 1)
											.isRoyalParked())) {
						parkingSpot = availableParkingobj.getValue();
						break;
					} else if ((parkingFloor.getPartiallyFilledSpotsMap().containsKey(availableParkingobj.getKey() - 1))
							&& (parkingFloor.getPartiallyFilledSpotsMap()
									.containsKey(availableParkingobj.getKey() + 1))) {
						parkingSpot = availableParkingobj.getValue();
						break;
					} else if ((parkingFloor.getPartiallyFilledSpotsMap().containsKey(availableParkingobj.getKey() - 1))
							&& (parkingFloor.getAvailableParkingSpotsMap()
									.containsKey(availableParkingobj.getKey() + 1))) {
						parkingSpot = availableParkingobj.getValue();
						break;
					} else {
						parkingSpot = availableParkingobj.getValue();
						break;
					}

				}

			}

		}
		return parkingSpot;
	}
	

}
