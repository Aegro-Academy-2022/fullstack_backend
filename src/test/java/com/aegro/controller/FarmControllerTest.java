package com.aegro.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.aegro.model.Farm;
import com.aegro.service.FarmServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;


import java.util.ArrayList;
import java.util.List;

@WebMvcTest(controllers = FarmController.class)
public class FarmControllerTest {

	@Autowired
	private MockMvc mvc;
	
	@MockBean
	private FarmServiceImpl farmService;
	
	private final String url = "http://localhost:8080/api/v1/farms/";
	
	private ObjectMapper mapper = new ObjectMapper();
	
	private Farm farm;
	
	@BeforeEach
	public void setUp() {
		farm = new Farm("1", "Test Farm");
	}
	
	@Test
	public void deveriaCriarFazenda() throws Exception{
		Mockito.when(farmService.insert(farm)).thenReturn(farm);
		String farmJson = mapper.writeValueAsString(farm);
		
		mvc.perform(post(url)
				.content(farmJson)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated());
	}
	
	@Test
	public void deveriaBuscarListaDeFazendas() throws Exception{
		List <Farm> farms = new ArrayList<>();
		farms.add(farm);
		
		Mockito.when(farmService.getAll()).thenReturn(farms);
		String farmsJson = mapper.writeValueAsString(farms);
		
		mvc.perform(get(url))
				.andExpect(status().isOk())
				.andExpect(content().string(farmsJson));
	}
	
	@Test
	public void deveriaBuscarFazendaPorId() throws Exception{
		Mockito.when(farmService.getById(farm.getId())).thenReturn(farm);
		String farmJson = mapper.writeValueAsString(farm);
		
		mvc.perform(get(url + farm.getId()))
				.andExpect(status().isOk())
				.andExpect(content().string(farmJson));
	}
	
	@Test
	public void deveriaAtualizarFazenda() throws Exception {
		
		String farmJson = mapper.writeValueAsString(farm);
		farm.setName("Test Farm 2");
		
		Mockito.when(farmService.update(farm.getId(), farm)).thenReturn(farm);
		
		mvc.perform(put (url + farm.getId())
				.content(farmJson)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}
	
	@Test
	public void deveriaRemoverFazenda() throws Exception{
		Mockito.doNothing().when(farmService).remove(farm.getId());
		
		mvc.perform(delete (url + farm.getId()))
				.andExpect(status().isOk());
	}
}