package com.aegro.repository;

import java.math.BigDecimal;
import java.util.List;

import com.aegro.model.Production;
import com.mongodb.client.result.DeleteResult;

public interface ProductionRepository {
	
	public Production save(Production production, String fkPlot);
	
	public List<Production> findAll(String fkPlot);
	
	public Production findById(String fkPlot, String id);
	
	public Production update(String fkPlot, String id, Production production);
	
	public DeleteResult delete(String fkPlot, String id);
	
	public void deleteAll(String fkPlot);
	
	public BigDecimal getTotalKilo(String fkPlot);

}
