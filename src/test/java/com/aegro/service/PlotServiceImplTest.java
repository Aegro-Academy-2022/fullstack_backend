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
import org.springframework.boot.web.server.LocalServerPort;

import com.aegro.exception.EmptyListException;
import com.aegro.exception.InternalServerException;
import com.aegro.exception.ResourceNotFoundException;
import com.aegro.model.Farm;
import com.aegro.model.Plot;
import com.aegro.repository.PlotRepositoryImpl;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PlotServiceImplTest {

	@MockBean
	private PlotRepositoryImpl plotRepo;
	
	@MockBean
	private Productivity productivity;
	
	@LocalServerPort
	private int port;
	
	@Autowired
	private PlotServiceImpl plotService;
	
	private Farm farm;
	private Plot plot;

	
	@BeforeEach
	public void setUp() {
		farm = new Farm("1", "Test Farm");
		plot = new Plot("2", "Test Plot", new BigDecimal(40), farm.getId());
	}
	
	@Test
	public void deveriaCriarTalhao() {
		Mockito.when(plotRepo.save(plot, farm.getId())).thenReturn(plot);
		
		Plot _plot = plotService.insert(plot, farm.getId());
		
		Assertions.assertEquals(_plot, plot);
		
	}
	
	@Test
	public void gerarExcecaoQuandoTentarInserirTalhaInvalido() {
		Mockito.when(plotRepo.save(null, farm.getId())).thenThrow(new RuntimeException());

		assertThatThrownBy(() -> plotService.insert(null, farm.getId()))
        .isInstanceOf(InternalServerException.class);
	}
	
	@Test
	public void deveriaRetornarListaDeTalhoes() {
		List<Plot> plots = new ArrayList<>();
		plots.add(plot);
		
		Mockito.when(plotRepo.findAll(farm.getId())).thenReturn(plots);
		
		List<Plot> _plots = plotService.getAll(farm.getId());
		
		Assertions.assertFalse(_plots.isEmpty());
	}
	
	@Test
	public void gerarExcecaoAoRetornarListaDeTalhoes() {
		Mockito.when(plotRepo.findAll(farm.getId())).thenThrow(new EmptyListException("talhão"));
		
		assertThatThrownBy(() -> plotService.getAll(farm.getId()))
        .isInstanceOf(EmptyListException.class);
	}
	
	@Test
	public void deveriaRetornarTalhaoComIdValido() {
		Mockito.when(plotRepo.findById(farm.getId(), plot.getId())).thenReturn(plot);
		
		Plot _plot = plotService.getById(farm.getId(), plot.getId());
		
		Assertions.assertEquals(_plot, plot);
	}
	
	@Test
	public void gerarExececaoQuandoIdTalhaoForInvalido() {
		Mockito.when(plotRepo.findById(farm.getId(), null)).thenThrow(new ResourceNotFoundException("Talhão"));
		
		assertThatThrownBy(() -> plotService.getById(farm.getId(), null))
        .isInstanceOf(ResourceNotFoundException.class);
	}
	
	@Test
	public void deveriaAtualizarDadosDeTalhao() {
		plot.setName("Test Plot Updated");
		
		Mockito.when(plotRepo.update(farm.getId(), plot.getId(), plot)).thenReturn(plot);
		
		Plot _plot = plotService.update(farm.getId(), plot.getId(), plot);
		
		Assertions.assertEquals(_plot.getId(), plot.getId());
	}

	@Test
	public void gerarExcecaoAoAtualizarQuandoDadosDoTalhaoForemInvalidos() {
		plot.setName("Test Plot Updated");

		Mockito.when(plotRepo.update(farm.getId(), null, plot)).thenThrow( new ResourceNotFoundException("Talhão"));
		
		assertThatThrownBy(() -> plotService.update(farm.getId(), null, plot))
        .isInstanceOf(ResourceNotFoundException.class);
	}
	

	@Test
	public void gerarExcecaoQuandoTentarRemoverTalhaoComIdInvalido() {
		Mockito.when(plotRepo.delete(farm.getId(), null)).thenThrow(new ResourceNotFoundException("Talhão"));

		assertThatThrownBy(() -> plotService.remove(farm.getId(), null))
        .isInstanceOf(ResourceNotFoundException.class);
		
	}
	
}
