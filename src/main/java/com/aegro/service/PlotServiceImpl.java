package com.aegro.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aegro.model.Plot;
import com.aegro.repository.PlotRepositoryImpl;

@Service
public class PlotServiceImpl implements PlotService{
	@Autowired
	private PlotRepositoryImpl plotRepo;
	
	@Autowired
	private Validation validation;
	
	@Autowired
	private Productivity productivity;
	
	@Override
	public Plot insert(Plot plot, String fkFarm) {
		try {
			if(validation.verifyName(plot.getName())
					|| validation.verifyNum(plot.getArea())) {
				return new Plot();
			}

			return plotRepo.save(plot, fkFarm);
		}catch(Exception e) {
			return new Plot();
		}
	}

	@Override
	public List<Plot> getAll(String fkFarm) {
		try {
			return plotRepo.findAll(fkFarm);
		}catch(Exception e) {
			return new ArrayList<>();
		}
	}

	@Override
	public Plot getById(String fkFarm, String id) {
		try {
			return plotRepo.findById(fkFarm, id);
		}catch(Exception e) {
			return new Plot();
		}
	}

	@Override
	public Plot update(String fkFarm, String id, Plot plot) {
		try {
			if(validation.verifyName(plot.getName())
					|| validation.verifyNum(plot.getArea())) {
				return new Plot();
			}
			
			Plot _plot = plotRepo.update(fkFarm, id, plot);
			
			System.out.println(_plot.getId());
			
			if(productivity.defineProductivityPlot(fkFarm, id) && productivity.defineProductivityFarm(fkFarm)) {
				return _plot;
			}
			
			return new Plot();
		}catch(Exception e) {
			return new Plot();
		}
	}

	@Override
	public boolean remove(String fkFarm, String id) {
		try {
			plotRepo.delete(fkFarm, id);
			
			if(productivity.defineProductivityFarm(fkFarm)) {
				return true;
			}
			return false;
		}catch(Exception e) {
			return false;
		}
	}

}
