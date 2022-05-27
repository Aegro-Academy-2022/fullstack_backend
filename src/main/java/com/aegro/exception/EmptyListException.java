package com.aegro.exception;

public class EmptyListException extends RuntimeException{
	
	public EmptyListException(String objeto) {
		super("A lista de " + objeto + " está vazia");
	}

}
