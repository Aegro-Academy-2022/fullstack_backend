package com.aegro.repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.aegro.model.Production;
import com.mongodb.client.result.DeleteResult;

@Repository
public class ProductionRepositoryImpl implements ProductionRepository{
	@Autowired
	MongoTemplate mongoTemplate;
	
	@Autowired
	PlotRepositoryImpl plotRepo;

	public Production save(Production production, String fkPlot) {
		return mongoTemplate.save(new Production(production.getKilo(), fkPlot));
	}
	
	public List<Production> findAll(String fkPlot){
		return mongoTemplate.find(new Query().addCriteria(Criteria.where("fkPlot").is(fkPlot)), Production.class);
	}
	
	public Production findById(String fkPlot, String id) {
		Production production = mongoTemplate.findById(id, Production.class);
		
		if(production.isEmpty() || !production.getFkPlot().contentEquals(fkPlot)) {
			return null;
		}
		
		return production;
	}
	
	public Production update(String fkPlot, String id, Production production) {
		if(findById(fkPlot, id).isEmpty()) {
			return null;
		}
		
		production.setId(id);
		production.setFkPlot(fkPlot);
		return mongoTemplate.save(production);
	}
	
	public DeleteResult delete(String fkPlot, String id) {
		Production production = findById(fkPlot, id);
		if(production.isEmpty()) {
			return null;
		}
		
		return mongoTemplate.remove(production);
	}
	
	public void deleteAll(String fkPlot) {
		findAll(fkPlot).forEach( (production) -> delete(production.getFkPlot(), production.getId()));
	}
	
	public BigDecimal getTotalKilo(String fkPlot){
		BigDecimal totalKilo = findAll(fkPlot)
				.stream()
				.map(Production::getKilo)
				.reduce(BigDecimal.ZERO, BigDecimal::add);
		
		if (totalKilo == null) {
			return new BigDecimal(0);
		}
		
		return totalKilo;
	}
	
	public BigDecimal getTotalProduction(String fkFarm) {
		BigDecimal totalProduction = plotRepo.findAll(fkFarm)
				.stream()
				.map((plot) -> getTotalKilo(plot.getId()))
				.reduce(BigDecimal.ZERO, BigDecimal::add);
		
		if (totalProduction == null) {
			return new BigDecimal(0);
		}
		
		return totalProduction;
	}

}
