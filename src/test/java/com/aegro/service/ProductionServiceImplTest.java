package com.aegro.service;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

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
import com.aegro.model.Production;
import com.aegro.repository.ProductionRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProductionServiceImplTest {
	
	@MockBean
	private ProductionRepository productionRepo;
		
	@MockBean
	private Productivity productivity;
	
	@Autowired
	private ProductionServiceImpl productionService;
	
	private Farm farm;
	private Plot plot;
	private Production production;
	private Production productionInvalidKilo;
	
	@Before
	public void setUp() {
		farm = new Farm("1", "Test Farm");
		plot = new Plot("2", "Test Plot", new BigDecimal(40), farm.getId());
		production = new Production("3", new BigDecimal(100), plot.getId());
		productionInvalidKilo = new Production("4", new BigDecimal(0), plot.getId());
	}
	
	@Test
	public void deveriaInserirProducao() {
		Mockito.when(productionRepo.save(production, plot.getId())).thenReturn(production);
		Mockito.when(productivity.defineProductivityFarm(farm.getId())).thenReturn(true);
		Mockito.when(productivity.defineProductivityPlot(farm.getId(), plot.getId())).thenReturn(true);
		
		Production _production = productionService.insert(production, plot.getId(), farm.getId());
		
		Assertions.assertEquals(_production, production);
	}
	
	@Test
	public void naoDeveriaInserirProducaoComKiloInvalido() {
		Mockito.when(productionRepo.save(productionInvalidKilo, plot.getId())).thenReturn(new Production());
		
		Production _production = productionService.insert(productionInvalidKilo, plot.getId(), farm.getId());
		
		Assertions.assertTrue(_production.isNull());
	}
	
	@Test
	public void naoDeveriaInserirProducaoQuandoIdFazendaForInvalido() {
		Mockito.when(productionRepo.save(production, plot.getId())).thenReturn(production);
		Mockito.when(productivity.defineProductivityFarm(null)).thenReturn(false);
		Mockito.when(productivity.defineProductivityPlot(null, plot.getId())).thenReturn(false);
		
		Production _production = productionService.insert(production, plot.getId(), null);
		
		Assertions.assertTrue(_production.isNull());		
	}
	
	@Test
	public void gerarExcecaoQuandoTentarInserirProducao() {
		Mockito.when(productionRepo.save(null, plot.getId())).thenThrow(new RuntimeException());
		
		Production _production = productionService.insert(null, plot.getId(), farm.getId());
		
		Assertions.assertTrue(_production.isNull());
	}
	
	
	@Test
	public void deveriaRetornarListaComProducoes() {
		List<Production> productions = new ArrayList<>();
		productions.add(production);
		
		Mockito.when(productionRepo.findAll(plot.getId())).thenReturn(productions);
		
		List<Production> _productions = productionService.getAll(plot.getId());
		
		Assertions.assertFalse(_productions.isEmpty());
	}
	
	@Test
	public void gerarExcecaoQuandoRetornaListaDeProducoes() {
		Mockito.when(productionRepo.findAll(null)).thenThrow(new RuntimeException());
		
		List<Production> productions = productionService.getAll(null);
		
		Assertions.assertTrue(productions.isEmpty());
	}
	
	@Test
	public void deveriaRetornarProducaoComIdValido() {
		Mockito.when(productionRepo.findById(plot.getId(), production.getId())).thenReturn(production);
		
		Production _production = productionService.getById(plot.getId(), production.getId());
		
		Assertions.assertEquals(_production, production);
	}
	
	@Test
	public void gerarExcecaoQuandoIdProducaoForInvalido() {
		Mockito.when(productionRepo.findById(plot.getId(), null)).thenThrow(new RuntimeException());
		
		Production _production = productionService.getById(plot.getId(), null);
		
		Assertions.assertTrue(_production.isNull());
	}
	
	@Test
	public void deveiaAtualizarDadosDeProducao() {
		production.setKilo(new BigDecimal(150));
		
		Mockito.when(productionRepo.update(plot.getId(), production.getId(), production)).thenReturn(production);
		Mockito.when(productivity.defineProductivityFarm(farm.getId())).thenReturn(true);
		Mockito.when(productivity.defineProductivityPlot(farm.getId(), plot.getId())).thenReturn(true);
		
		Production _production = productionService.update(farm.getId(), plot.getId(), production.getId(), production);
		
		Assertions.assertEquals(_production, production);
	}
	
	@Test
	public void naoDeveriaAtualizarProducaoComKiloInvalido() {
		Mockito.when(productionRepo.update(plot.getId(), productionInvalidKilo.getId(), productionInvalidKilo)).thenReturn(new Production());
		
		Production _production = productionService.update(farm.getId(), plot.getId(), productionInvalidKilo.getId(), productionInvalidKilo);
		
		Assertions.assertTrue(_production.isNull());	
	}
	
	@Test
	public void naoDeveriaAtualizarProducaoComIdFazendaInvalido() {
		Mockito.when(productionRepo.update(plot.getId(), production.getId(), production)).thenReturn(new Production());
		Mockito.when(productivity.defineProductivityFarm(null)).thenReturn(false);
		Mockito.when(productivity.defineProductivityPlot(null, plot.getId())).thenReturn(false);
		
		Production _production = productionService.update(null, plot.getId(), production.getId(), production);
		
		Assertions.assertTrue(_production.isNull());
	}
	
	@Test
	public void gerarExcecaoQuandoTentarAtualizarProducaoInvalida() {
		Mockito.when(productionRepo.update(plot.getId(), null, production)).thenThrow(new RuntimeException());
		
		Production _production = productionService.update(farm.getId(), plot.getId(), null, production);
		
		Assertions.assertTrue(_production.isNull());
		
	}
	
	@Test
	public void deveriaRemoverProducao() {
		Mockito.when(productionRepo.delete(plot.getId(), production.getId())).thenReturn(null);
		Mockito.when(productivity.defineProductivityFarm(farm.getId())).thenReturn(true);
		Mockito.when(productivity.defineProductivityPlot(farm.getId(), plot.getId())).thenReturn(true);
		
		boolean _production = productionService.remove(farm.getId(), plot.getId(), production.getId());
		
		Assertions.assertTrue(_production);
		
	}
	
	@Test
	public void nãoDeveriaRemoverProducao() {
		Mockito.when(productionRepo.delete(plot.getId(), production.getId())).thenReturn(null);
		Mockito.when(productivity.defineProductivityFarm(null)).thenReturn(false);
		Mockito.when(productivity.defineProductivityPlot(null, plot.getId())).thenReturn(false);
		
		boolean _production = productionService.remove(null, plot.getId(), production.getId());
		
		Assertions.assertFalse(_production);
	}
	
	@Test
	public void gerarExcecaoQuandoTentarRemoverProducaoComDadosInvalidos() {
		Mockito.when(productionRepo.delete(plot.getId(),null)).thenThrow(new RuntimeException());
		
		boolean _production = productionService.remove(null, plot.getId(), null);
		
		Assertions.assertFalse(_production);
	}
	
	

}
