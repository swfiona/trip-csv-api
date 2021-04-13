package com.swfiona.tlctrip.exception;

public class BoroughNotFoundException extends RuntimeException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public BoroughNotFoundException(String borough) {
		super(borough + " Not Found");
	}

	public BoroughNotFoundException(String borough, Throwable cause) {
        super(borough + " Not Found", cause);
    }
}
