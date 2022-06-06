package com.aegro.service;


import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.aegro.exception.EmptyListException;
import com.aegro.exception.ResourceNotFoundException;
import com.aegro.model.Farm;
import com.aegro.model.Plot;
import com.aegro.model.Production;
import com.aegro.repository.ProductionRepositoryImpl;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProductionServiceImplTest {
	
	@MockBean
	private ProductionRepositoryImpl productionRepo;
		
	@MockBean
	private Productivity productivity;
	
	@Autowired
	private ProductionServiceImpl productionService;
	
	private Farm farm;
	private Plot plot;
	private Production production;
	
	@BeforeEach
	public void setUp() {
		farm = new Farm("1", "Test Farm");
		plot = new Plot("2", "Test Plot", new BigDecimal(40), farm.getId());
		production = new Production("3", new BigDecimal(100), plot.getId());
	}
	
	@Test
	public void deveriaInserirProducao() {
		Mockito.when(productionRepo.save(production, plot.getId())).thenReturn(production);
		
		Production _production = productionService.insert(production, plot.getId(), farm.getId());
		
		Assertions.assertEquals(_production, production);
	}
	
	@Test
	public void gerarExcecaoQuandoTentarInserirProducao() {
		Mockito.when(productionRepo.save(null, plot.getId())).thenThrow(new ResourceNotFoundException("Produção"));

		assertThatThrownBy(() -> productionService.insert(null, plot.getId(), farm.getId()))
        .isInstanceOf(ResourceNotFoundException.class);
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
		Mockito.when(productionRepo.findAll(null)).thenThrow(new EmptyListException("produção"));
	
		assertThatThrownBy(() -> productionService.getAll(null))
        .isInstanceOf(EmptyListException.class);
	}
	
	@Test
	public void deveriaRetornarProducaoComIdValido() {
		Mockito.when(productionRepo.findById(plot.getId(), production.getId())).thenReturn(production);
		
		Production _production = productionService.getById(plot.getId(), production.getId());
		
		Assertions.assertEquals(_production, production);
	}
	
	@Test
	public void gerarExcecaoQuandoIdProducaoForInvalido() {
		Mockito.when(productionRepo.findById(plot.getId(), null)).thenThrow(new ResourceNotFoundException("Produção"));
		
		assertThatThrownBy(() -> productionService.getById(plot.getId(), null))
        .isInstanceOf(ResourceNotFoundException.class);
	}
	
	@Test
	public void deveiaAtualizarDadosDeProducao() {
		production.setKilo(new BigDecimal(150));
		
		Mockito.when(productionRepo.update(plot.getId(), production.getId(), production)).thenReturn(production);
		
		Production _production = productionService.update(farm.getId(), plot.getId(), production.getId(), production);
		
		Assertions.assertEquals(_production, production);
	}
	
	@Test
	public void gerarExcecaoQuandoTentarAtualizarProducaoInvalida() {
		Mockito.when(productionRepo.update(plot.getId(), null, production)).thenThrow(new ResourceNotFoundException("Produção"));
		
		assertThatThrownBy(() -> productionService.update(farm.getId(), plot.getId(), null, production))
        .isInstanceOf(ResourceNotFoundException.class);
	}
	
	@Test
	public void gerarExcecaoQuandoTentarRemoverProducaoComDadosInvalidos() {
		Mockito.when(productionRepo.delete(plot.getId(),null)).thenThrow(new ResourceNotFoundException("Produção"));

		assertThatThrownBy(() -> productionService.remove(null, plot.getId(), null))
        .isInstanceOf(ResourceNotFoundException.class);
	}

}
