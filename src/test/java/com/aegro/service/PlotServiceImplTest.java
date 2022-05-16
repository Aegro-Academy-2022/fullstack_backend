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
		plot = new Plot("2", "Test Plot", new BigDecimal(40), this.farm.getId());
		plotBlankName = new Plot("2", "  ", new BigDecimal(40), this.farm.getId());
		plotInvalidArea = new Plot("2", "Test Plot Area", new BigDecimal(0), this.farm.getId());
	}
	
	@Test
	public void deveriaCriarTalhao() {
		Mockito.when(plotRepo.save(this.plot, this.farm.getId())).thenReturn(this.plot);
		
		Plot _plot = plotService.insert(this.plot, this.farm.getId());
		
		Assertions.assertEquals(_plot, this.plot);
		
	}
	
	@Test
	public void naoDeveriaCriarTalhaoComNomeInvalido() {
		Mockito.when(plotRepo.save(this.plotBlankName, this.farm.getId())).thenReturn(new Plot());
		
		Plot _plot = plotService.insert(this.plotBlankName, this.farm.getId());
		
		Assertions.assertTrue(_plot.isNull());
	}
	
	@Test
	public void naoDeveriaCriarTalhaoComAreaIncalida() {
		Mockito.when(plotRepo.save(this.plotInvalidArea, this.farm.getId())).thenReturn(new Plot());
		
		Plot _plot = plotService.insert(this.plotInvalidArea, this.farm.getId());
		
		Assertions.assertTrue(_plot.isNull());
	}
	
	@Test
	public void gerarExcecaoQuandoTentarInserirTalhaInvalido() {
		Mockito.when(plotRepo.save(null, this.farm.getId())).thenThrow(new RuntimeException());
		
		Plot _plot = plotService.insert(null, this.farm.getId());
		
		Assertions.assertTrue(_plot.isNull());
	}
	
	@Test
	public void deveriaRetornarListaDeTalhoes() {
		List<Plot> plots = new ArrayList<>();
		plots.add(this.plot);
		
		Mockito.when(plotRepo.findAll(this.farm.getId())).thenReturn(plots);
		
		List<Plot> _plots = plotService.getAll(this.farm.getId());
		
		Assertions.assertFalse(_plots.isEmpty());
	}
	
	@Test
	public void gerarExcecaoAoRetornarListaDeTalhoes() {
		Mockito.when(plotRepo.findAll(this.farm.getId())).thenThrow(new RuntimeException());
		
		List<Plot> plots = plotService.getAll(this.farm.getId());
		
		Assertions.assertTrue(plots.isEmpty());
	}
	
	@Test
	public void deveriaRetornarTalhaoComIdValido() {
		Mockito.when(plotRepo.findById(this.farm.getId(), this.plot.getId())).thenReturn(this.plot);
		
		Plot _plot = plotService.getById(this.farm.getId(), this.plot.getId());
		
		Assertions.assertEquals(_plot, this.plot);
	}
	
	@Test
	public void gerarExececaoQuandoIdTalhaoForInvalido() {
		Mockito.when(plotRepo.findById(this.farm.getId(), null)).thenThrow(new RuntimeException());
		
		Plot _plot = plotService.getById(this.farm.getId(), null);
		
		Assertions.assertTrue(_plot.isNull());
	}
	
	@Test
	public void deveriaAtualizarDadosDeTalhao() {
		this.plot.setName("Test Plot Updated");
		
		Mockito.when(plotRepo.update(this.farm.getId(), this.plot.getId(), this.plot)).thenReturn(this.plot);
		
		Plot _plot = plotService.update(this.farm.getId(), this.plot.getId(), this.plot);
		
		Assertions.assertEquals(_plot, this.plot);
	}
	
	@Test
	public void naoDeveriaAtualizarTalhaoComNomeInvalido() {
		Mockito.when(plotRepo.update(this.farm.getId(), this.plotBlankName.getId(), this.plotBlankName)).thenReturn(new Plot());
		
		Plot _plot = plotService.update(this.farm.getId(), this.plotBlankName.getId(), this.plotBlankName);
		
		Assertions.assertTrue(_plot.isNull());
		
	}
	
	@Test
	public void naoDeveriaAtualizarTalhaoComAreaInvalida() {
		Mockito.when(plotRepo.update(this.farm.getId(), this.plotInvalidArea.getId(), this.plotInvalidArea)).thenReturn(new Plot());
		
		Plot _plot = plotService.update(this.farm.getId(), this.plotInvalidArea.getId(), this.plotInvalidArea);
		
		Assertions.assertTrue(_plot.isNull());
	}
	
	@Test
	public void gerarExcecaoQuandoDadosDoTalhaoForemInvalidos() {
		this.plot.setName("Test Plot Updated");

		Mockito.when(plotRepo.update(this.farm.getId(), null, this.plot)).thenThrow( new RuntimeException());
		
		Plot _plot = plotService.update(this.farm.getId(), null, this.plot);
		
		Assertions.assertTrue(_plot.isNull());
	}
	
	@Test
	public void deveriaRemoverTalhaoComIdValido() {
		Mockito.when(plotRepo.delete(this.farm.getId(), this.plot.getId())).thenReturn(null);
		Mockito.when(productivity.defineProductivityFarm(this.farm.getId())).thenReturn(true);
		
		boolean _plot = plotService.remove(this.farm.getId(), this.plot.getId());
		
		Assertions.assertTrue(_plot);
	}
	
	@Test
	public void naodeveriaRemoverTalhaoComIdInvalido() {
		Mockito.when(plotRepo.delete(this.farm.getId(), null)).thenReturn(null);
		
		boolean _plot = plotService.remove(this.farm.getId(), null);
		
		Assertions.assertFalse(_plot);
	}
	
	@Test
	public void gerarExcecaoQuandoTentarRemoverTalhaoComIdInvalido() {
		Mockito.when(plotRepo.delete(this.farm.getId(), null)).thenThrow(new RuntimeException());
		
		boolean _plot = plotService.remove(this.farm.getId(), null);
		
		Assertions.assertFalse(_plot);
		
	}
	

}
