package com.aegro.service;


import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.aegro.model.Farm;
import com.aegro.model.Plot;
import com.aegro.repository.FarmRepositoryImpl;
import com.aegro.repository.PlotRepositoryImpl;
import com.aegro.repository.ProductionRepositoryImpl;


@RunWith(SpringJUnit4ClassRunner.class)
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
	
	Plot plot;
	
	@Before
	public void setUp() {		
		farm = new Farm("1", "Test Farm");
		plot = new Plot("2", "Test Plot", new BigDecimal(40), farm.getId());
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
	public void gerarExcecaoQuandoTentarDefinirProdutividadeDeTalhao() {
		
	}


}
