package com.aegro.service;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

@Service
public class Validation {
	
	public boolean verifyName(String name) {
		if(name.isBlank() || name.isEmpty()) {
			return true;
		}
		return false;
	}
	
	public boolean verifyNum(BigDecimal area) {
		if(area.compareTo(BigDecimal.ZERO) <= 0) {
			return true;
		}
		return false;
	}
}
