package com.aegro.service;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aegro.model.Plot;
import com.aegro.repository.FarmRepositoryImpl;
import com.aegro.repository.PlotRepositoryImpl;
import com.aegro.repository.ProductionRepositoryImpl;

@Service
public class Productivity {
	@Autowired
	FarmRepositoryImpl farmRepo;
	
	@Autowired
	PlotRepositoryImpl plotRepo;
	
	@Autowired
	ProductionRepositoryImpl productionRepo;
		
	@Autowired
	private Validation validation;
	
	public boolean defineProductivityPlot(String fkFarm, String fkPlot) {
		try {
			BigDecimal productivityPlot = getProductivityPlot(fkFarm, fkPlot);
			plotRepo.updateProductivity(fkFarm, fkPlot, productivityPlot);
			
			return true;		
			
		} catch (Exception e) {
			return false;
		}
		
	}
	
	public boolean defineProductivityFarm(String fkFarm) {
		try {
			BigDecimal productivityFarm = getProductivityFarm(fkFarm);
			farmRepo.updateProductivity(fkFarm, productivityFarm);
			
			return true;
			
		}catch(Exception e) {
			return false;
		}
	}
	
	public BigDecimal getProductivityFarm(String idFarm) {
		BigDecimal totalKilo = plotRepo.getTotalProduction(idFarm);
		BigDecimal totalArea = plotRepo.getTotalArea(idFarm);
		
		if (validation.verifyNum(totalKilo) || validation.verifyNum(totalArea)) {
			return new BigDecimal(0);
		}
	
		return totalKilo.divide(totalArea, 2, RoundingMode.HALF_UP); 
		
	
	}
	
	public BigDecimal getProductivityPlot(String fkFarm, String idPlot) {
		Plot plot = plotRepo.findById(fkFarm, idPlot);
		
		if(plot.isEmpty()) {
			return null;
		}
		
		BigDecimal totalKilo = productionRepo.getTotalKilo(idPlot);
		
		if (validation.verifyNum(totalKilo) || validation.verifyNum(plot.getArea())) {
			return new BigDecimal(0);
		}

		return totalKilo.divide(plot.getArea(), 2, RoundingMode.HALF_UP);	
	}

}
