package com.swfiona.tlctrip.exception;

public class VehicleNotFoundException extends RuntimeException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public VehicleNotFoundException(String vehicle) {
		super("Invalid vehicle type " + vehicle +" ! Valid vehicles are FHV, YELLOW and GREEN.");
	}

	public VehicleNotFoundException(String vehicle, Throwable cause) {
        super("Invalid vehicle type " + vehicle +" ! Valid vehicles are FHV, YELLOW and GREEN.", cause);
    }
}
