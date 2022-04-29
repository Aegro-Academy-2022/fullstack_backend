package com.aegro.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.aegro.model.Plot;
import com.mongodb.client.result.DeleteResult;

@Repository
public class PlotRepository{
	
	@Autowired
	MongoTemplate mongoTemplate;

	public Plot save(Plot plot, String fkFarm) {
		return mongoTemplate.save(new Plot(plot.getName(), plot.getArea(), fkFarm));
	}
	
	public List<Plot> findAll(String fkFarm){
		List<Plot> allPlots = mongoTemplate.find(new Query().addCriteria(Criteria.where("fkFarm").is(fkFarm)), Plot.class);
		return allPlots;
	}
	
	public Plot findById(String fkFarm, String id) {
		Query query = new Query(Criteria.where("id").is(id));
		List<Plot> plot = mongoTemplate.find(query, Plot.class);
		
		if(plot.get(0).isNull() || !plot.get(0).getFkFarm().contentEquals(fkFarm)) {
			return new Plot();
		}
		
		return plot.get(0);
	}
	
	public Plot update(String fkFarm, String id, Plot plot) {
		if(findById(fkFarm, id).isNull()) {
			return new Plot();
		}
		plot.setId(id);
		plot.setFkFarm(fkFarm);
		return mongoTemplate.save(plot);
	}
	
	public DeleteResult delete(String fkFarm, String id) {
		Plot plot = findById(fkFarm, id);
		if(plot.isNull()) {
			return null;
		}
		return mongoTemplate.remove(plot);
	}
	
	public List<Plot> deleteAll(String fkFarm) {
		Query query = new Query(Criteria.where("fkFarm").is(fkFarm));
		List<Plot> plots = mongoTemplate.findAllAndRemove(query, Plot.class);
		
		if(plots.isEmpty()) {
			return null;
		}
		
		return plots;
	}
}
