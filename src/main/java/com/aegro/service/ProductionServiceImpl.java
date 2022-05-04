package com.aegro.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aegro.model.Production;
import com.aegro.repository.ProductionRepository;

@Service
public class ProductionServiceImpl implements ProductionService{
	@Autowired
	private ProductionRepository productionRepo;
	
	@Autowired
	private Validation validation;
	
	@Autowired
	private Productivity productivity;

	@Override
	public Production insert(Production production, String fkPlot, String fkFarm) {
		try {
			if(validation.verifyNum(production.getKilo())) {
				return new Production();
			}

			Production _production =productionRepo.save(production, fkPlot);
			
			if(productivity.defineProductivityPlot(fkFarm, fkPlot) && productivity.defineProductivityFarm(fkFarm)) {
				return _production;
			}
			
			return new Production();
		}catch(Exception e) {
			return new Production();
		}
	}

	@Override
	public List<Production> getAll(String fkPlot) {
		try {
			return productionRepo.findAll(fkPlot);
		}catch(Exception e) {
			return new ArrayList<>();
		}
	}

	@Override
	public Production getById(String fkPlot, String id) {
		try {
			return productionRepo.findById(fkPlot, id);
		}catch(Exception e) {
			return new Production();
		}
	}

	@Override
	public Production update(String fkPlot, String id, Production production) {
		try {
			if(validation.verifyNum(production.getKilo())) {
				return new Production();
			}
			
			return productionRepo.update(fkPlot,id, production);
		}catch(Exception e) {
			return new Production();
		}
	}

	@Override
	public boolean remove(String fkFarm, String fkPlot, String id) {
		try {
			productionRepo.delete(fkPlot, id);
			
			if(productivity.defineProductivityPlot(fkFarm, fkPlot) && productivity.defineProductivityFarm(fkFarm)) {
				return true;
			}
			
			return false;
		}catch(Exception e) {
			return false;
		}
	}

}
