package com.aegro.exception;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CommonExceptionHandler {
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<?> handlerResourceNotFoundException(ResourceNotFoundException e){
		CommonExceptionDetails rnfDetails = CommonExceptionDetails.Builder
			.newBuilder()
			.timestamp(new Date().getTime())
			.status(HttpStatus.NOT_FOUND.value())
			.title("Resource not found")
			.message(e.getMessage())
			.build();
		
		return new ResponseEntity<>(rnfDetails, HttpStatus.NOT_FOUND);
	}
	
	
	@ExceptionHandler(InvalidInputDataException.class)
	public ResponseEntity<?> handlerInvalidInputDataException(InvalidInputDataException e){
		CommonExceptionDetails rnfDetails = CommonExceptionDetails.Builder
			.newBuilder()
			.timestamp(new Date().getTime())
			.status(HttpStatus.BAD_REQUEST.value())
			.title("Invalid input data")
			.message(e.getMessage())
			.build();
		
		return new ResponseEntity<>(rnfDetails, HttpStatus.BAD_REQUEST);
	}
	
	
	@ExceptionHandler(InternalServerException.class)
	public ResponseEntity<?> handlerInternalServerException(InternalServerException e){
		CommonExceptionDetails rnfDetails = CommonExceptionDetails.Builder
			.newBuilder()
			.timestamp(new Date().getTime())
			.status(HttpStatus.INTERNAL_SERVER_ERROR.value())
			.title("No insertion executed")
			.message(e.getMessage())
			.build();
		
		return new ResponseEntity<>(rnfDetails, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	
	@ExceptionHandler(EmptyListException.class)
	public ResponseEntity<?> handlerEmptyListException(EmptyListException e){
		CommonExceptionDetails rnfDetails = CommonExceptionDetails.Builder
			.newBuilder()
			.timestamp(new Date().getTime())
			.status(HttpStatus.NO_CONTENT.value())
			.title("No insertion executed")
			.message(e.getMessage())
			.build();
		
		return new ResponseEntity<>(rnfDetails, HttpStatus.NO_CONTENT);
	}
}
