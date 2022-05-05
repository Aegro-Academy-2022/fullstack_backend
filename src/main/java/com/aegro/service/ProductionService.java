package com.aegro.service;

import java.util.List;

import com.aegro.model.Production;

public interface ProductionService {
	Production insert(Production production, String fkPlot, String fkFarm);
	
	List<Production> getAll(String fkPlot);
	
	Production getById(String fkPlot, String id);
	
	Production update(String fkPlot, String id, Production production);
	
	boolean remove(String fkFarm, String fkPlot, String id);

}
