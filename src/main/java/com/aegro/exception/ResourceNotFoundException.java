package com.aegro.exception;

public class ResourceNotFoundException extends RuntimeException {

	public ResourceNotFoundException (String objeto) {
		super (objeto + " não encontrado(a)");
	}
}
