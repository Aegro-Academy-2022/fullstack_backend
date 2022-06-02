package com.aegro.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.aegro.model.Plot;
import com.mongodb.client.result.DeleteResult;

@Repository
public class PlotRepositoryImpl implements PlotRepository{
	
	@Autowired
	MongoTemplate mongoTemplate;
	
	@Autowired
	ProductionRepositoryImpl productionRepo;

	public Plot save(Plot plot, String fkFarm) {
		return mongoTemplate.save(new Plot(plot.getName(), plot.getArea(), fkFarm));
	}
	
	public List<Plot> findAll(String fkFarm){
		return mongoTemplate.find(new Query().addCriteria(Criteria.where("fkFarm").is(fkFarm)), Plot.class);
	}
	
	public Plot findById(String fkFarm, String id) {
		Plot plot = mongoTemplate.findById(id, Plot.class);
		
		if(plot.isEmpty() || !plot.getFkFarm().contentEquals(fkFarm)) {
			return null;
		}
		
		return plot;
	}
	
	public Plot update(String fkFarm, String id, Plot plot) {
		Plot _plot = findById(fkFarm, id);
		
		
		if(_plot.isEmpty()) {
			return null;
		}

		_plot.setName(plot.getName());
		_plot.setArea(plot.getArea());

		return mongoTemplate.save(_plot);
	}
	
	public Plot updateProductivity(String fkFarm, String id, BigDecimal productivity) {
		Plot plot = findById(fkFarm, id);
		
		if(plot.isEmpty()) {
			return null;
		}
		
		plot.setProductivity(productivity);
		return mongoTemplate.save(plot);
	}
	
	public DeleteResult delete(String fkFarm, String id) {
		Plot plot = findById(fkFarm, id);
		
		productionRepo.deleteAll(plot.getId());
		return mongoTemplate.remove(plot);
	}
	
	public void deleteAll(String fkFarm) {
		findAll(fkFarm).forEach( (plot) -> delete(plot.getFkFarm(), plot.getId()));
	}
	
	public BigDecimal getTotalArea(String fkFarm) {
		BigDecimal totalArea = findAll(fkFarm)
				.stream()
				.map(Plot::getArea)
				.reduce(BigDecimal.ZERO, BigDecimal::add);
		
		if (totalArea == null) {
			return new BigDecimal(0);
		}
		return totalArea;
	}
	
	public BigDecimal getTotalProduction(String fkFarm) {
		BigDecimal totalProduction = findAll(fkFarm)
				.stream()
				.map((plot) -> productionRepo.getTotalKilo(plot.getId()))
				.reduce(BigDecimal.ZERO, BigDecimal::add);
		
		if (totalProduction == null) {
			return new BigDecimal(0);
		}
		
		return totalProduction;
	}
}
