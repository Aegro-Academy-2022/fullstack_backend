package com.aegro.exception;

public class InvalidInputDataException extends RuntimeException {
	
	public InvalidInputDataException () {
		super ("Os dados de entrada informados são inválidos");
	}
}
