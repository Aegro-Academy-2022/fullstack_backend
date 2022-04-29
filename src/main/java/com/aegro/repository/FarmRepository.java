package com.aegro.repository;

import org.springframework.stereotype.Repository;

import com.aegro.model.Farm;
import com.mongodb.client.result.DeleteResult;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;

@Repository
public class FarmRepository {
	
	@Autowired
	MongoTemplate mongoTemplate;

	public Farm save(Farm farm) {
		return mongoTemplate.save(new Farm(farm.getName()));
	}
	
	public List<Farm> findAll(){
		return mongoTemplate.findAll(Farm.class);
	}
	
	public Farm findById(String id) {
		Farm farm = mongoTemplate.findById(id, Farm.class);
		if(farm.isNull()) {
			return null;
		}
		return farm;
	}
	
	public Farm update(String id, Farm farm) {
		if(mongoTemplate.findById(id, Farm.class).isNull()) {
			return new Farm();
		}
		farm.setId(id);
		return mongoTemplate.save(farm);
	}
	
	public DeleteResult delete(String id) {
		Farm farm = mongoTemplate.findById(id, Farm.class);
		if(farm.isNull()) {
			return null;
		}
		return mongoTemplate.remove(farm);
	}

	
}
