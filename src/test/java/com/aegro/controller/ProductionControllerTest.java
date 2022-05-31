package com.aegro.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
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
import com.aegro.model.Production;
import com.aegro.service.ProductionServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;


@WebMvcTest(controllers = ProductionController.class)
class ProductionControllerTest {
	
	@Autowired
	private MockMvc mvc;
	
	@MockBean
	private ProductionServiceImpl productionService;
	
	private final String url = "http://localhost:8080/api/v1/farms/1/plots/2/productions/";
	
	private ObjectMapper mapper = new ObjectMapper();
	
	private Farm farm;
	private Plot plot;
	private Production production;
	
	@BeforeEach
	public void setUp() {
		farm = new Farm("1", "Test Farm");
		plot = new Plot("2", "Test Plot", new BigDecimal(40), farm.getId());
		production = new Production("3", new BigDecimal(0), plot.getId());
	}
	
	@Test
	public void deveriaCriarProducao() throws Exception{
		Mockito.when(productionService.insert(production, plot.getId(), farm.getId())).thenReturn(production);
		String productionJson = mapper.writeValueAsString(production);
		
		mvc.perform(post(url)
				.content(productionJson)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated());
	}
	
	@Test
	public void deveriaBuscarListaDeProducoes() throws Exception{
		List <Production> productions = new ArrayList<>();
		productions.add(production);
		
		Mockito.when(productionService.getAll(plot.getId())).thenReturn(productions);
		String productionsJson = mapper.writeValueAsString(productions);
		
		mvc.perform(get(url))
				.andExpect(status().isOk())
				.andExpect(content().string(productionsJson));
	}
	
	@Test
	public void deveriaBuscarProducaoPorId() throws Exception{
		Mockito.when(productionService.getById(plot.getId(), production.getId())).thenReturn(production);
		String productionJson = mapper.writeValueAsString(production);
		
		mvc.perform(get(url + production.getId()))
				.andExpect(status().isOk())
				.andExpect(content().string(productionJson));
	}
	
	@Test
	public void deveriaAtualizarProducao() throws Exception {
		production.setKilo(new BigDecimal(100));
		
		Mockito.when(productionService.update(farm.getId(), plot.getId(), production.getId(), production)).thenReturn(production);
		String productionJson = mapper.writeValueAsString(production);

		
		mvc.perform(put (url + production.getId())
				.content(productionJson)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}

	@Test
	public void deveriaRemoverProducao() throws Exception{
		Mockito.doNothing().when(productionService).remove(farm.getId(), plot.getId(), production.getId());
		
		mvc.perform(delete (url + production.getId()))
				.andExpect(status().isOk());
	}


}
