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
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.aegro.model.Farm;
import com.aegro.model.Plot;
import com.aegro.repository.PlotRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PlotServiceImplTest {

	@MockBean
	private PlotRepository plotRepo;
	
	@MockBean
	private Productivity productivity;
	
	@LocalServerPort
	private int port;
	
	@Autowired
	private PlotServiceImpl plotService;
	
	private Farm farm;
	private Plot plot;
	private Plot plotBlankName;
	private Plot plotInvalidArea;
	
	@Before
	public void setUp() {
		farm = new Farm("1", "Test Farm");
		plot = new Plot("2", "Test Plot", new BigDecimal(40), farm.getId());
		plotBlankName = new Plot("3", "  ", new BigDecimal(40), farm.getId());
		plotInvalidArea = new Plot("4", "Test Plot Area", new BigDecimal(0), farm.getId());
	}
	
	@Test
	public void deveriaCriarTalhao() {
		Mockito.when(plotRepo.save(plot, farm.getId())).thenReturn(plot);
		
		Plot _plot = plotService.insert(plot, farm.getId());
		
		Assertions.assertEquals(_plot, plot);
		
	}
	
	@Test
	public void naoDeveriaCriarTalhaoComNomeInvalido() {
		Mockito.when(plotRepo.save(plotBlankName, farm.getId())).thenReturn(new Plot());
		
		Plot _plot = plotService.insert(plotBlankName, farm.getId());
		
		Assertions.assertTrue(_plot.isNull());
	}
	
	@Test
	public void naoDeveriaCriarTalhaoComAreaIncalida() {
		Mockito.when(plotRepo.save(plotInvalidArea, farm.getId())).thenReturn(new Plot());
		
		Plot _plot = plotService.insert(plotInvalidArea, farm.getId());
		
		Assertions.assertTrue(_plot.isNull());
	}
	
	@Test
	public void gerarExcecaoQuandoTentarInserirTalhaInvalido() {
		Mockito.when(plotRepo.save(null, farm.getId())).thenThrow(new RuntimeException());
		
		Plot _plot = plotService.insert(null, farm.getId());
		
		Assertions.assertTrue(_plot.isNull());
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
		Mockito.when(plotRepo.findAll(farm.getId())).thenThrow(new RuntimeException());
		
		List<Plot> plots = plotService.getAll(farm.getId());
		
		Assertions.assertTrue(plots.isEmpty());
	}
	
	@Test
	public void deveriaRetornarTalhaoComIdValido() {
		Mockito.when(plotRepo.findById(farm.getId(), plot.getId())).thenReturn(plot);
		
		Plot _plot = plotService.getById(farm.getId(), plot.getId());
		
		Assertions.assertEquals(_plot, plot);
	}
	
	@Test
	public void gerarExececaoQuandoIdTalhaoForInvalido() {
		Mockito.when(plotRepo.findById(farm.getId(), null)).thenThrow(new RuntimeException());
		
		Plot _plot = plotService.getById(farm.getId(), null);
		
		Assertions.assertTrue(_plot.isNull());
	}
	
	@Test
	public void deveriaAtualizarDadosDeTalhao() {
		plot.setName("Test Plot Updated");
		
		Mockito.when(plotRepo.update(farm.getId(), plot.getId(), plot)).thenReturn(plot);
		Mockito.when(productivity.defineProductivityFarm(farm.getId())).thenReturn(true);
		Mockito.when(productivity.defineProductivityPlot(farm.getId(), plot.getId())).thenReturn(true);

		
		Plot _plot = plotService.update(farm.getId(), plot.getId(), plot);
		
		Assertions.assertEquals(_plot.getId(), plot.getId());
	}
	
	@Test
	public void naodeveriaAtualizarDadosDeTalhao() {
		plot.setName("Test Plot Updated");
		
		Mockito.when(plotRepo.update(farm.getId(), plot.getId(), plot)).thenReturn(new Plot());
		Mockito.when(productivity.defineProductivityFarm(null)).thenReturn(false);
		Mockito.when(productivity.defineProductivityPlot(null, plot.getId())).thenReturn(false);
		
		Plot _plot = plotService.update(farm.getId(), plot.getId(), plot);
		
		Assertions.assertTrue(_plot.isNull());
	}
	
	@Test
	public void naoDeveriaAtualizarTalhaoComNomeInvalido() {
		Mockito.when(plotRepo.update(farm.getId(), plotBlankName.getId(), plotBlankName)).thenReturn(new Plot());
		
		Plot _plot = plotService.update(farm.getId(), plotBlankName.getId(), plotBlankName);
		
		Assertions.assertTrue(_plot.isNull());
		
	}
	
	@Test
	public void naoDeveriaAtualizarTalhaoComAreaInvalida() {
		Mockito.when(plotRepo.update(farm.getId(), plotInvalidArea.getId(), plotInvalidArea)).thenReturn(new Plot());
		
		Plot _plot = plotService.update(farm.getId(), plotInvalidArea.getId(), plotInvalidArea);
		
		Assertions.assertTrue(_plot.isNull());
	}
	
	
	@Test
	public void gerarExcecaoQuandoDadosDoTalhaoForemInvalidos() {
		plot.setName("Test Plot Updated");

		Mockito.when(plotRepo.update(farm.getId(), null, plot)).thenThrow( new RuntimeException());
		
		Plot _plot = plotService.update(farm.getId(), null, plot);
		
		Assertions.assertTrue(_plot.isNull());
	}
	
	@Test
	public void deveriaRemoverTalhaoComIdValido() {
		Mockito.when(plotRepo.delete(farm.getId(), plot.getId())).thenReturn(null);
		Mockito.when(productivity.defineProductivityFarm(farm.getId())).thenReturn(true);
		
		boolean _plot = plotService.remove(farm.getId(), plot.getId());
		
		Assertions.assertTrue(_plot);
	}
	
	@Test
	public void naodeveriaRemoverTalhaoComIdInvalido() {
		Mockito.when(plotRepo.delete(farm.getId(), null)).thenReturn(null);
		
		boolean _plot = plotService.remove(farm.getId(), null);
		
		Assertions.assertFalse(_plot);
	}
	
	@Test
	public void gerarExcecaoQuandoTentarRemoverTalhaoComIdInvalido() {
		Mockito.when(plotRepo.delete(farm.getId(), null)).thenThrow(new RuntimeException());
		
		boolean _plot = plotService.remove(farm.getId(), null);
		
		Assertions.assertFalse(_plot);
		
	}
	

}
