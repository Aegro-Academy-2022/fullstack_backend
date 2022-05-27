package com.aegro.exception;

public class NoInsertionExecutedException extends RuntimeException  {
	
	public NoInsertionExecutedException () {
		super ("Não foi possível realizar a inserção");
	}
}
