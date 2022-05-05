package com.aegro.repository;

import java.math.BigDecimal;
import java.math.RoundingMode;
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
	
	@Autowired
	ProductionRepository productionRepo;

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
	
	public Plot updateProductivity(String fkFarm, String id, BigDecimal productivity) {
		Plot plot = findById(fkFarm, id);
		
		if(plot.isNull()) {
			return new Plot();
		}
		
		plot.setProductivity(productivity);
		return mongoTemplate.save(plot);
	}
	
	public DeleteResult delete(String fkFarm, String id) {
		Plot plot = findById(fkFarm, id);
		if(plot.isNull()) {
			return null;
		}
		
		productionRepo.deleteAll(plot.getId());
		return mongoTemplate.remove(plot);
	}
	
	public void deleteAll(String fkFarm) {
		findAll(fkFarm).forEach( (plot) -> delete(plot.getFkFarm(), plot.getId()));
	}
	
	public BigDecimal getProductivity(String fkFarm){
		BigDecimal productivity = findAll(fkFarm).
				stream()
				.map(Plot::getProductivity)
				.reduce(BigDecimal.ZERO, BigDecimal::add);

		if (productivity == null) {
			return new BigDecimal(0);
		}
		
		BigDecimal size = new BigDecimal(findAll(fkFarm).size());
		
		return productivity.divide(size, 2, RoundingMode.HALF_UP);
 
	}

}
