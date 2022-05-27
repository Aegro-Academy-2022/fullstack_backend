package com.aegro.service;

import java.util.List;

import com.aegro.model.Plot;

public interface PlotService {
	Plot insert(Plot plot, String fkFarm);
	
	List<Plot> getAll(String fkFarm);
	
	Plot getById(String fkFarm, String id);
	
	Plot update(String fkFarm, String id, Plot plot);
	
	void remove(String fkFarm, String id);

}
