package com.aegro.controller;

import java.util.ArrayList;
import java.util.List;

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
import com.aegro.service.FarmServiceImpl;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class FarmControllerTest {
	@Autowired
	private TestRestTemplate restTemplate;
	@LocalServerPort
	private int port;
	@MockBean
	private FarmServiceImpl farmService;
	
	@Test
	public void deveriaCriarNovaFazenda() {
		Farm farm = new Farm("Test Farm");
		Mockito.when(farmService.insert(farm)).thenReturn(farm);
		
		ResponseEntity<String> response = restTemplate.postForEntity("/api/v1/farms",farm, String.class);
		Assertions.assertEquals(response.getStatusCode(), HttpStatus.CREATED);
	}
	
	@Test
	public void naoDeveriaCriarFazendaComNomeEmBranco() {
		Farm farm = new Farm("  ");
		Mockito.when(farmService.insert(farm)).thenReturn(farm);
		
		ResponseEntity<String> response = restTemplate.postForEntity("/api/v1/farms",farm, String.class);
		Assertions.assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
	}
	
	@Test
	public void naoDeveriaCriarFazendaComNomeVazio() {
		Farm farm = new Farm("");
		Mockito.when(farmService.insert(farm)).thenReturn(farm);
		
		ResponseEntity<String> response = restTemplate.postForEntity("/api/v1/farms",farm, String.class);
		Assertions.assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
	}
	
	@Test
	public void naoDeveriaCriarFazendaComNomeNulo() {
		Farm farm = new Farm(null);
		Mockito.when(farmService.insert(farm)).thenReturn(null);
		
		ResponseEntity<String> response = restTemplate.postForEntity("/api/v1/farms",farm, String.class);
		Assertions.assertEquals(response.getStatusCode(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@Test
	public void deveriaRetornarListaVaziaDeFazendas() {
		List<Farm> farms = new ArrayList<>();
		
		Mockito.when(farmService.getAll()).thenReturn(farms);
		
		ResponseEntity<String> response = restTemplate.getForEntity("/api/v1/farms", String.class);
		Assertions.assertEquals(response.getStatusCode(), HttpStatus.NO_CONTENT);
	}
	
	@Test
	public void deveriaRetornarListaDeFazendas() {
		List<Farm> farms = new ArrayList<>();
		Farm farm = new Farm("Test Farm");
		
		farms.add(farm);
		
		Mockito.when(farmService.getAll()).thenReturn(farms);
		
		ResponseEntity<String> response = restTemplate.getForEntity("/api/v1/farms", String.class);
		Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);
	}
	
	@Test
	public void naoDeveriaRetornarFazendaQuandoOIdForInvalido() {	
		String id = "1";
		
		Mockito.when(farmService.getById(id)).thenReturn(null);
		
		ResponseEntity<String> response = restTemplate.getForEntity("/api/v1/farms/{id}", String.class, id);
		Assertions.assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
	}
	
	@Test
	public void deveriaRetornarUmaFazendaQuandoOIdForValido() {	
		Farm farm = new Farm("1", "Test Farm");
		
		Mockito.when(farmService.getById(farm.getId())).thenReturn(farm);
		
		ResponseEntity<String> response = restTemplate.getForEntity("/api/v1/farms/{id}", String.class, farm.getId());
		Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);
	}
	
	
}
