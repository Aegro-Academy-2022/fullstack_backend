package com.aegro.service;

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
import com.aegro.repository.FarmRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FarmServiceImplTest {	
	
	@MockBean
	private FarmRepository farmRepo;
	
	@LocalServerPort
	private int port;
	
	@Autowired
	private FarmServiceImpl farmService;
	
	private Farm farm;
	private Farm farmEmpty;
	private Farm farmBlank;
	
	@Before
	public void setUp() {
		farm = new Farm("1", "Test Farm");
		farmEmpty = new Farm ("");
		farmBlank = new Farm(" ");
	}
	
	@Test
	public void deveriaInserirFazenda() {
		Mockito.when(farmRepo.save(this.farm)).thenReturn(this.farm);
		
		Farm _farm = farmService.insert(this.farm);
		
		Assertions.assertEquals(_farm, farm);
	}
	
	@Test
	public void naoDeveriaInserirFazendaComNomeVazio() {
		Mockito.when(farmRepo.save(this.farmEmpty)).thenReturn(new Farm());
		
		Farm _farm = farmService.insert(this.farmEmpty);
		
		Assertions.assertTrue(_farm.isNull());
	}
	
	@Test
	public void naoDeveriaInserirFazendaComNomeEmBranco() {
		Mockito.when(farmRepo.save(this.farmBlank)).thenReturn(new Farm());
		
		Farm _farm = farmService.insert(this.farmBlank);
		
		Assertions.assertTrue(_farm.isNull());
	}
	
	@Test
	public void gerarExcecaoQuandoInserirFazendaComValorNulo(){
		Mockito.when(farmRepo.save(null)).thenThrow(new RuntimeException());
		
		Farm _farm = farmService.insert(null);
		
		Assertions.assertTrue(_farm.isNull());
	}
	
	@Test 
	public void deveriaRetornaListaComFazenda() {
		List<Farm> farmList = new ArrayList<>();
		farmList.add(this.farm);
		
		Mockito.when(farmRepo.findAll()).thenReturn(farmList);
		
		List<Farm> _farm = farmService.getAll();
		
		Assertions.assertFalse(_farm.isEmpty());
	}
	
	@Test
	public void gerarExcecaoAoRetornarListaDeFazenda() {		
		Mockito.when(farmRepo.findAll()).thenThrow(new RuntimeException());
		
		List<Farm> _farm = farmService.getAll();
		
		Assertions.assertTrue(_farm.isEmpty());
	}
	
	@Test
	public void deveriaRetornaFazendaQuandoOIdValido() {
		Mockito.when(farmRepo.findById(this.farm.getId())).thenReturn(farm);
		
		Farm _farm = farmService.getById(this.farm.getId());
		
		Assertions.assertEquals(_farm, this.farm);
	}
	
	@Test
	public void gerarExcecaoQuandoOIdDaFazendaForInvalido() {
		Mockito.when(farmRepo.findById(null)).thenThrow(new RuntimeException());
		
		Farm _farm = farmService.getById(null);
		
		Assertions.assertTrue(_farm.isNull());
	}
	
	@Test
	public void deveriaAtulaizarDadosDaFazenda() {
		Farm farmAux = new Farm(this.farm.getId(), "Test Farm 2");
		
		Mockito.when(farmRepo.update(this.farm.getId(), farmAux)).thenReturn(farmAux);
		
		Farm _farm = farmService.update(this.farm.getId(), farmAux);
		
		Assertions.assertEquals(_farm, farmAux);
		
	}
	
	@Test
	public void naodeveriaAtulaizarDadosDaFazendaQuandoNomeForVazio() {		
		Mockito.when(farmRepo.update(this.farm.getId(), this.farmEmpty)).thenReturn(new Farm());
		
		Farm _farm = farmService.update(this.farm.getId(), this.farmEmpty);
		
		Assertions.assertTrue(_farm.isNull());
		
	}
	
	@Test
	public void naodeveriaAtulaizarDadosDaFazendaQuandoNomeEstiverEmBranco() {
		Mockito.when(farmRepo.update(this.farm.getId(), this.farmBlank)).thenReturn(new Farm());
		
		Farm _farm = farmService.update(this.farm.getId(), this.farmBlank);
		
		Assertions.assertTrue(_farm.isNull());
		
	}
	
	@Test
	public void gerarExcecaoQuandoAtualizarFazendaComIdInvalido() {
		Farm farmAux = new Farm(this.farm.getId(), "Test Farm 2");

		Mockito.when(farmRepo.update(null, farmAux)).thenThrow(new RuntimeException());
		
		Farm _farm = farmService.update(null, farmAux);
		
		Assertions.assertTrue(_farm.isNull());
	}
	
	@Test
	public void deveiaRemoverFazendaComIdValido() {
		Mockito.when(farmRepo.delete(this.farm.getId())).thenReturn(null);
		
		boolean _farm = farmService.remove(this.farm.getId());
		
		Assertions.assertTrue(_farm);
		
	}
	
	@Test
	public void gerarExcecaoQuandoTentarRemoverFazendaComIdInvalido() {
		Mockito.when(farmRepo.delete(null)).thenThrow(new RuntimeException());
		
		boolean _farm = farmService.remove(null);
		
		Assertions.assertFalse(_farm);
	}

}
