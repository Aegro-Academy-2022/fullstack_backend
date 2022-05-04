package com.aegro.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.aegro.model.Production;
import com.mongodb.client.result.DeleteResult;

@Repository
public class ProductionRepository{
	@Autowired
	MongoTemplate mongoTemplate;

	public Production save(Production production, String fkPlot) {
		return mongoTemplate.save(new Production(production.getKilo(), fkPlot));
	}
	
	public List<Production> findAll(String fkPlot){
		List<Production> allProductions = mongoTemplate.find(new Query().addCriteria(Criteria.where("fkPlot").is(fkPlot)), Production.class);
		return allProductions;
	}
	
	public Production findById(String fkPlot, String id) {
		Query query = new Query(Criteria.where("id").is(id));
		List<Production> production = mongoTemplate.find(query, Production.class);
		
		if(production.get(0).isNull() || !production.get(0).getFkPlot().contentEquals(fkPlot)) {
			return new Production();
		}
		
		return production.get(0);
	}
	
	public Production update(String fkPlot, String id, Production production) {
		if(findById(fkPlot, id).isNull()) {
			return new Production();
		}
		production.setId(id);
		production.setFkPlot(fkPlot);
		return mongoTemplate.save(production);
	}
	
	public DeleteResult delete(String fkPlot, String id) {
		Production production = findById(fkPlot, id);
		if(production.isNull()) {
			return null;
		}
		return mongoTemplate.remove(production);
	}
	
	public void deleteAll(String fkPlot) {
		/*Query query = new Query(Criteria.where("fkPlot").is(fkPlot));
		List<Production> productions = mongoTemplate.findAllAndRemove(query, Production.class);
		
		if(productions.isEmpty()) {
			return null;
		}
		
		return productions;*/
		findAll(fkPlot).forEach( (production) -> delete(production.getFkPlot(), production.getId()));
	}
	
	public BigDecimal getTotalKilo(String fkPlot){
		BigDecimal totalKilo = findAll(fkPlot).stream().map(Production::getKilo).reduce(BigDecimal.ZERO, BigDecimal::add);
		if (totalKilo == null) {
			return new BigDecimal(0);
		}
		return totalKilo;
 
	}
}
