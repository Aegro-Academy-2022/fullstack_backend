package com.aegro.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.aegro.model.Farm;
import com.aegro.model.Plot;
import com.aegro.service.PlotServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(controllers = PlotController.class)
class PlotControllerTest {
	
	@Autowired
	private MockMvc mvc;
	
	@MockBean
	private PlotServiceImpl plotService;
	
	private final String url = "http://localhost:8080/api/v1/farms/1/plots/";
	
	private ObjectMapper mapper = new ObjectMapper();
	
	private Farm farm;
	private Plot plot;
	
	@BeforeEach
	public void setUp() {
		farm = new Farm("1", "Test Farm");
		plot = new Plot("2", "Test Plot", new BigDecimal(40), farm.getId());
	}
	
	@Test
	public void deveriaCriarTalhao() throws Exception{
		Mockito.when(plotService.insert(plot, farm.getId())).thenReturn(plot);
		String plotJson = mapper.writeValueAsString(plot);
		
		mvc.perform(post(url)
				.content(plotJson)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated());
	}
	
	@Test
	public void deveriaBuscarListaDeTalhoes() throws Exception{
		List <Plot> plots = new ArrayList<>();
		plots.add(plot);
		
		Mockito.when(plotService.getAll(farm.getId())).thenReturn(plots);
		String plotsJson = mapper.writeValueAsString(plots);
		
		mvc.perform(get(url))
				.andExpect(status().isOk())
				.andExpect(content().string(plotsJson));
	}
	
	@Test
	public void deveriaBuscarTalhaoPorId() throws Exception{
		Mockito.when(plotService.getById(farm.getId(), plot.getId())).thenReturn(plot);
		String plotJson = mapper.writeValueAsString(plot);
		
		mvc.perform(get(url + plot.getId()))
				.andExpect(status().isOk())
				.andExpect(content().string(plotJson));
	}
	
	@Test
	public void deveriaAtualizarTalhao() throws Exception {
		plot.setArea(new BigDecimal(100));
		
		Mockito.when(plotService.update(farm.getId(), plot.getId(), plot)).thenReturn(plot);
		String plotJson = mapper.writeValueAsString(plot);

		
		mvc.perform(put (url + plot.getId())
				.content(plotJson)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}
	
	@Test
	public void deveriaRemoverTalhao() throws Exception{
		Mockito.doNothing().when(plotService).remove(farm.getId(), plot.getId());
		
		mvc.perform(delete (url + plot.getId()))
				.andExpect(status().isOk());
	}
}
