package com.aegro.service;

public class Validation {
	
	public boolean verifyName(String name) {
		if(name.isBlank() || name.isEmpty()) {
			return true;
		}
		return false;
	}
}
