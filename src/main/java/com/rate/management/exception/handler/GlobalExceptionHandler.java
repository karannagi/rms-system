package com.rate.management.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import com.rate.management.constants.ErrorCodes;
import com.rate.management.dto.ErrorDto;
import com.rate.management.exception.RateManagementException;

import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
	@ExceptionHandler({ RateManagementException.class})
	public final ResponseEntity<ErrorDto> handleException(RateManagementException ex, WebRequest request) {
		log.error("Exception occured while processing request, errorCode: {}, errorDescription: {}",ex.getErrorCode(),ex.getMessage());
		if(ErrorCodes.valueOf(ex.getErrorCode())==ErrorCodes.RATE_ID_NOT_FOUND)
			return new ResponseEntity<>(ErrorDto.builder().error(ex.getMessage()).build(),HttpStatus.NOT_FOUND);
		else
			return new ResponseEntity<>(ErrorDto.builder().error(ex.getMessage()).build(),HttpStatus.INTERNAL_SERVER_ERROR);
	
	}
	
	@ExceptionHandler({ Exception.class})
	public final ResponseEntity<ErrorDto> handleException(Exception ex, WebRequest request) {
		log.error("Exception occured while processing request, errorMessage: {}",ex.getMessage());

			return new ResponseEntity<>(ErrorDto.builder().error(ex.getMessage()).build(),HttpStatus.INTERNAL_SERVER_ERROR);
	
	}
}
