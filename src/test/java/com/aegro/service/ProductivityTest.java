package com.aegro.service;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.aegro.model.Farm;
import com.aegro.model.Plot;
import com.aegro.repository.FarmRepositoryImpl;
import com.aegro.repository.PlotRepositoryImpl;
import com.aegro.repository.ProductionRepositoryImpl;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProductivityTest {
	
	@MockBean
	FarmRepositoryImpl farmRepo;
	
	@MockBean
	PlotRepositoryImpl plotRepo;
	
	@MockBean
	ProductionRepositoryImpl productionRepo;
	
	@Autowired
	private Productivity productivity;
	
	private Farm farm;
	
	private Plot plot;
	private Plot plotNull;
	
	@BeforeEach
	public void setUp() {		
		farm = new Farm("1", "Test Farm");
		plot = new Plot("2", "Test Plot", new BigDecimal(40), farm.getId());
		plotNull = new Plot();
	}

	@Test
	public void deveriaRetornarProdutividadeDeTalhao() {
		BigDecimal productValue = new BigDecimal(100);

		Mockito.when(productionRepo.getTotalKilo(plot.getId())).thenReturn(productValue);
		Mockito.when(plotRepo.findById(farm.getId(), plot.getId())).thenReturn(plot);

		BigDecimal response = productivity.getProductivityPlot(farm.getId(), plot.getId());
		
		BigDecimal aux = productValue.divide(plot.getArea(), 2, RoundingMode.HALF_UP);	
		Assertions.assertEquals(response.compareTo(aux), 0);
	}
	
	@Test
	public void deveriaRetornarZeroQuandoKiloOuAreaForMenorIgualAZero() throws Exception{
		BigDecimal productValue = new BigDecimal(0);
		
		Mockito.when(productionRepo.getTotalKilo(plot.getId())).thenReturn(productValue);
		Mockito.when(plotRepo.findById(farm.getId(), plot.getId())).thenReturn(plot);
		
		BigDecimal response = productivity.getProductivityPlot(farm.getId(), plot.getId());
		
		Assertions.assertEquals(response.compareTo(new BigDecimal(0)), 0);
	}
	
	@Test
	public void deveriaRetornarNuloQuandoNaoHouverTalhao() throws Exception{
		Mockito.when(plotRepo.findById(farm.getId(), "")).thenReturn(plotNull);
				
		Assertions.assertNull(productivity.getProductivityPlot(farm.getId(), ""));
	}
	
	@Test 
	public void deveriaRetornarProdutividadeDeFazenda() {
		BigDecimal area = new BigDecimal(50);
		BigDecimal production =  new BigDecimal(200);
		
		Mockito.when(plotRepo.getTotalProduction(farm.getId())).thenReturn(production);
		Mockito.when(plotRepo.getTotalArea(farm.getId())).thenReturn(area);
		
		BigDecimal response = productivity.getProductivityFarm(farm.getId());
		BigDecimal aux = production.divide(area, 2, RoundingMode.HALF_UP); 
		
		Assertions.assertEquals(response.compareTo(aux), 0);
	}
	
	@Test
	public void deveriaRetornarZeroQuandoValoresForemMenorIgualAZero() {
		BigDecimal area = new BigDecimal(0);
		BigDecimal production =  new BigDecimal(200);
		
		Mockito.when(plotRepo.getTotalProduction(farm.getId())).thenReturn(production);
		Mockito.when(plotRepo.getTotalArea(farm.getId())).thenReturn(area);
		
		BigDecimal response = productivity.getProductivityFarm(farm.getId());
		
		Assertions.assertEquals(response.compareTo(new BigDecimal(0)), 0);
		
	}

}
