package com.rate.management.exception;

import com.rate.management.constants.ErrorCodes;

import lombok.Getter;

@Getter
public class RateManagementException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6279014997256769280L;
	private final String errorCode;
	
	public RateManagementException(ErrorCodes errorCode) {
		super(errorCode.getErrorDescription());
		this.errorCode=errorCode.getErrorCode();
	}

}
