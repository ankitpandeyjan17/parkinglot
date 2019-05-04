package com.parkinglot.vo;

import java.util.TreeMap;
import java.util.Map;

public class ParkingLot {

	public int totalParkingFloor;
	
	
	private Map<Integer, ParkingFloor> availableParkingFloorMap = new TreeMap<Integer, ParkingFloor>();
	private Map<Integer, ParkingFloor> partiallyFilledFloorMap = new TreeMap<Integer, ParkingFloor>();
	private Map<Integer, ParkingFloor> fullyOccuParkingFloorMap = new TreeMap<Integer, ParkingFloor>();
	

	public ParkingLot(int totalParkingFloor) {
		super();
		this.totalParkingFloor = totalParkingFloor;
	}
	
	public Map<Integer, ParkingFloor> getAvailableParkingFloorMap() {
		return availableParkingFloorMap;
	}



	public void setAvailableParkingFloorMap(Map<Integer, ParkingFloor> availableParkingFloorMap) {
		this.availableParkingFloorMap = availableParkingFloorMap;
	}



	public Map<Integer, ParkingFloor> getPartiallyFilledFloorMap() {
		return partiallyFilledFloorMap;
	}



	public void setPartiallyFilledFloorMap(Map<Integer, ParkingFloor> partiallyFilledFloorMap) {
		this.partiallyFilledFloorMap = partiallyFilledFloorMap;
	}



	public Map<Integer, ParkingFloor> getFullyOccuParkingFloorMap() {
		return fullyOccuParkingFloorMap;
	}



	public void setFullyOccuParkingFloorMap(Map<Integer, ParkingFloor> fullyOccuParkingFloorMap) {
		this.fullyOccuParkingFloorMap = fullyOccuParkingFloorMap;
	}

	
public boolean isFull() {
		
		if(this.getFullyOccuParkingFloorMap()!=null && this.getFullyOccuParkingFloorMap().size()==this.totalParkingFloor) {
			return true;
		}
		return false;
	}
	
}
