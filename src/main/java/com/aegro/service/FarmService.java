package com.aegro.service;

import java.util.List;

import com.aegro.model.Farm;

public interface FarmService {
	
	Farm insert(Farm farm);
	
	List<Farm> getAll();
	
	Farm getById(String id);
	
	Farm update(String id, Farm farm);
	
	boolean remove(String id);
	

}
