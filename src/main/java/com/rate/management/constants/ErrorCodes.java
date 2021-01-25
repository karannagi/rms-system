package com.rate.management.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorCodes {
	
	RATE_ID_NOT_FOUND("RATE_ID_NOT_FOUND","RateId not found in RMS"),
	INTERNAL_SERVER_ERROR("INTERNAL_SERVER_ERROR","Internal server error. Please contact admin");
	private final String errorCode;
	private final String errorDescription;
	
}
