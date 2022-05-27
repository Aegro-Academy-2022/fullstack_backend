package com.aegro.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aegro.exception.EmptyListException;
import com.aegro.exception.InvalidInputDataException;
import com.aegro.exception.ResourceNotFoundException;
import com.aegro.model.Production;
import com.aegro.repository.ProductionRepositoryImpl;

@Service
public class ProductionServiceImpl implements ProductionService{
	@Autowired
	private ProductionRepositoryImpl productionRepo;
	
	@Autowired
	private Validation validation;
	
	@Autowired
	private Productivity productivity;

	@Override
	public Production insert(Production production, String fkPlot, String fkFarm) {
		try {
			if(validation.verifyNum(production.getKilo())) {
				throw new InvalidInputDataException();
			}

			Production _production = productionRepo.save(production, fkPlot);
			
			productivity.defineProductivityPlot(fkFarm, fkPlot);
			productivity.defineProductivityFarm(fkFarm);
			
			return _production;
		}catch(Exception e) {
			throw new ResourceNotFoundException("Produção");
		}
	}

	@Override
	public List<Production> getAll(String fkPlot) {
		try {
			return productionRepo.findAll(fkPlot);
		}catch(Exception e) {
			throw new EmptyListException("produção");
		}
	}

	@Override
	public Production getById(String fkPlot, String id) {
		try {
			return productionRepo.findById(fkPlot, id);
		}catch(Exception e) {
			throw new ResourceNotFoundException("Produção");
		}
	}

	@Override
	public Production update(String fkFarm, String fkPlot, String id, Production production) {
		try {
			if(validation.verifyNum(production.getKilo())) {
				throw new InvalidInputDataException();
			}
			
			Production _production = productionRepo.update(fkPlot,id, production);
			
			productivity.defineProductivityPlot(fkFarm, fkPlot);
			productivity.defineProductivityFarm(fkFarm);
			
			return _production;
		}catch(Exception e) {
			throw new ResourceNotFoundException("Produção");
		}
	}

	@Override
	public void remove(String fkFarm, String fkPlot, String id) {
		try {
			productionRepo.delete(fkPlot, id);
			
			productivity.defineProductivityPlot(fkFarm, fkPlot);
			productivity.defineProductivityFarm(fkFarm);
			
		}catch(Exception e) {
			throw new ResourceNotFoundException("Produção");
		}
	}

}
