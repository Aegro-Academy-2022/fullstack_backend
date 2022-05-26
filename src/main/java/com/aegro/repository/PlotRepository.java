package com.aegro.repository;

import java.math.BigDecimal;
import java.util.List;

import com.aegro.model.Plot;
import com.mongodb.client.result.DeleteResult;

public interface PlotRepository {

	public Plot save(Plot plot, String fkFarm);
	
	public List<Plot> findAll(String fkFarm);
	
	public Plot findById(String fkFarm, String id);
	
	public Plot update(String fkFarm, String id, Plot plot);
	
	public Plot updateProductivity(String fkFarm, String id, BigDecimal productivity);
	
	public DeleteResult delete(String fkFarm, String id);
	
	public void deleteAll(String fkFarm);
	
	public BigDecimal getTotalArea(String fkFarm);

}
