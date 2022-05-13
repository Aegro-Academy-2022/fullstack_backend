package com.aegro.service;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aegro.model.Plot;
import com.aegro.repository.FarmRepository;
import com.aegro.repository.PlotRepository;
import com.aegro.repository.ProductionRepository;

@Service
public class Productivity {
	@Autowired
	FarmRepository farmRepo;
	
	@Autowired
	PlotRepository plotRepo;
	
	@Autowired
	ProductionRepository productionRepo;
		
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
		BigDecimal totalKilo = productionRepo.getTotalKilo(idPlot);
		if (validation.verifyNum(totalKilo)) {
			return new BigDecimal(0);
		}
		Plot plot = plotRepo.findById(fkFarm, idPlot);
		if(plot.isNull()) {
			return null;
		}
		return totalKilo.divide(plot.getArea(), 2, RoundingMode.HALF_UP);	
	}

}
