package com.parking.tools;

public interface ToolsFactory {
static ToolsFactory getTools(String factoryType) {
	if("BookingTools".equals(factoryType))
	return new BookingToolsImpl();
	else if("ParkingFloorTools".equals(factoryType)) 
		return new ParkingFloorToolsImpl();
	else if("ParkingSpotTools".equals(factoryType)) 
		return new ParkingSpotToolsImpl();
	else if("ParkingLotTools".equals(factoryType))
		return new ParkingLotToolsImpl();
	return null;
	
}
}
