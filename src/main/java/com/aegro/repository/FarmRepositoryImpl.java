package com.aegro.repository;

import org.springframework.stereotype.Repository;

import com.aegro.model.Farm;
import com.mongodb.client.result.DeleteResult;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;

@Repository
public class FarmRepositoryImpl implements FarmRepository {
	
	@Autowired
	MongoTemplate mongoTemplate;
	
	@Autowired
	PlotRepositoryImpl plotRepo;

	public Farm save(Farm farm) {
		return mongoTemplate.save(new Farm(farm.getName()));
	}
	
	public List<Farm> findAll(){
		return mongoTemplate.findAll(Farm.class);
	}
	
	public Farm findById(String id) {
		Farm farm = mongoTemplate.findById(id, Farm.class);
		
		if(farm.isEmpty()) {
			return null;
		}
		
		return farm;
	}
	
	public Farm update(String id, Farm farm) {
		if(findById(id).isEmpty()) {
			return null;
		}
		
		farm.setId(id);
		return mongoTemplate.save(farm);
	}
	
	public Farm updateProductivity(String id, BigDecimal productivity) {
		Farm farm = findById(id);
		
		if(farm.isEmpty()) {
			return null;
		}
		
		farm.setProductivity(productivity);
		return mongoTemplate.save(farm);
	}
	
	public DeleteResult delete(String id) {
		Farm farm = findById(id);
		
		if(farm.isEmpty()) {
			return null;
		}
	
		plotRepo.deleteAll(farm.getId());
		return mongoTemplate.remove(farm);
	}

	
}
