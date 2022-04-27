package com.aegro.controller;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.aegro.model.Farm;
import com.aegro.model.Plot;
import com.aegro.repository.PlotRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class PlotControllerTest {
	@Autowired
	private TestRestTemplate restTemplate;
	@LocalServerPort
	private int port;
	@MockBean
	private PlotRepository plotRepo;
	
	private Farm farm;
	
	@Before
	public void setUp() {
		this.farm = new Farm("1", "Test Farm");
	}
	
	@Test
	public void deveriaCriarNovoTalhao() {
		Plot plot = new Plot("Test Plot", new BigDecimal(42.1), farm.getId());
		Mockito.when(plotRepo.save(plot)).thenReturn(plot);
		
		ResponseEntity<String> response = restTemplate.postForEntity("/api/v1/farms/{fk}/plots/",farm, String.class, farm.getId());
		Assertions.assertEquals(response.getStatusCode(), HttpStatus.CREATED);
	}
	
	@Test
	public void deveriaRetornarListaVaziaDeTalhoes() {
		List<Plot> plots = new ArrayList<>();
		
		Mockito.when(plotRepo.findAllByFarmFk(farm.getId())).thenReturn(plots);
		
		ResponseEntity<String> response = restTemplate.getForEntity("/api/v1/farms/{fk}/plots/", String.class, farm.getId());
		Assertions.assertEquals(response.getStatusCode(), HttpStatus.NO_CONTENT);
	}
	
	@Test
	public void deveriaRetornarListaDeTalhoes() {
		List<Plot> plots = new ArrayList<>();
		Plot plot = new Plot("1", "Test Plot", new BigDecimal(45.5), farm.getId());
		
		plots.add(plot);
		
		Mockito.when(plotRepo.findAllByFarmFk(farm.getId())).thenReturn(plots);
		
		ResponseEntity<String> response = restTemplate.getForEntity("/api/v1/farms/{fk}/plots/", String.class, farm.getId());
		Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);
	}
	
	@Test
	public void naoDeveriaRetornarTalhaoQuandoOIdForInvalido() {	
		String id = "1";
		
		Mockito.when(plotRepo.findPlotById(farm.getId(), id)).thenReturn(null);
		
		ResponseEntity<String> response = restTemplate.getForEntity("/api/v1/farms/{fk}/plots/{id}", String.class, farm.getId(), id);
		Assertions.assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
	}
	
	@Test
	public void deveriaRetornarUmTalhaouandoOIdForValido() {	
		Plot plot = new Plot("1", "Test Plot", new BigDecimal(45.5), farm.getId());
		
		Mockito.when(plotRepo.findPlotById(farm.getId(), plot.getId())).thenReturn(Optional.of(plot));
		
		ResponseEntity<String> response = restTemplate.getForEntity("/api/v1/farms/{fk}/plots/{id}", String.class, farm.getId(), plot.getId());
		Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);
	}

}
