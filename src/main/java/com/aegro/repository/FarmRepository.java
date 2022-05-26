package com.aegro.repository;

import java.math.BigDecimal;
import java.util.List;

import com.aegro.model.Farm;
import com.mongodb.client.result.DeleteResult;

public interface FarmRepository {

	public Farm save(Farm farm);
	
	public List<Farm> findAll();
	
	public Farm findById(String id);
	
	public Farm update(String id, Farm farm);
	
	public Farm updateProductivity(String id, BigDecimal productivity);
	
	public DeleteResult delete(String id);

}
