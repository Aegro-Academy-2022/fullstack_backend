package com.aegro.service;

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
import com.aegro.repository.FarmRepositoryImpl;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FarmServiceImplTest {	
	
	@MockBean
	private FarmRepositoryImpl farmRepo;
	
	@MockBean
	private Validation validation;
	
	@LocalServerPort
	private int port;
	
	@Autowired
	private FarmServiceImpl farmService;
	
	private Farm farm;
	
	@BeforeEach
	public void setUp() {
		farm = new Farm("1", "Test Farm");
	}
	
	@Test
	public void deveriaInserirFazenda() {
		Mockito.when(farmRepo.save(farm)).thenReturn(farm);
		
		Farm _farm = farmService.insert(farm);
		
		Assertions.assertEquals(_farm, farm);
	}
	
	@Test
	public void gerarExcecaoQuandoInserirFazendaComValorNulo(){		
		Mockito.when(farmRepo.save(null)).thenReturn(null);
		
		assertThatThrownBy(() -> farmService.insert(null))
        .isInstanceOf(InternalServerException.class);
	}

	@Test 
	public void deveriaRetornaListaComFazenda() {
		List<Farm> farmList = new ArrayList<>();
		farmList.add(farm);
		
		Mockito.when(farmRepo.findAll()).thenReturn(farmList);
		
		List<Farm> _farm = farmService.getAll();
		
		Assertions.assertEquals(_farm, farmList);
	}
	
	@Test
	public void gerarExcecaoAoRetornarListaDeFazenda() {		
		Mockito.when(farmRepo.findAll()).thenThrow(new EmptyListException("fazenda"));
		
		assertThatThrownBy(() -> farmService.getAll())
        .isInstanceOf(EmptyListException.class);
	}
	
	@Test
	public void deveriaRetornaFazendaQuandoOIdValido() {
		Mockito.when(farmRepo.findById(farm.getId())).thenReturn(farm);
		
		Farm _farm = farmService.getById(farm.getId());
		
		Assertions.assertEquals(_farm, farm);
	}
	
	@Test
	public void gerarExcecaoQuandoOIdDaFazendaForInvalido() {
		Mockito.when(farmRepo.findById(null)).thenThrow(new ResourceNotFoundException("Fazenda"));
		
		assertThatThrownBy(() -> farmService.getById(null))
        .isInstanceOf(ResourceNotFoundException.class);
	}
	
	@Test
	public void deveriaAtulaizarDadosDaFazenda() {
		Farm farmAux = new Farm(farm.getId(), "Test Farm 2");
		
		Mockito.when(farmRepo.update(farm.getId(), farmAux)).thenReturn(farmAux);
		
		Farm _farm = farmService.update(farm.getId(), farmAux);
		
		Assertions.assertEquals(_farm, farmAux);
		
	}
	
	@Test
	public void gerarExcecaoAoAtualizarQuandoDadosDaFazendaQuandoForemInvalidos() {		
		Mockito.when(farmRepo.update(farm.getId(), null)).thenThrow(new ResourceNotFoundException("Fazenda"));
		
		assertThatThrownBy(() -> farmService.update(farm.getId(), null))
        .isInstanceOf(ResourceNotFoundException.class);
		
	}
	
	
	@Test
	public void gerarExcecaoQuandoTentarRemoverFazendaComIdInvalido() {
		Mockito.when(farmRepo.delete(null)).thenThrow(new ResourceNotFoundException("Fazenda"));
		
		assertThatThrownBy(() -> farmService.remove(null))
        .isInstanceOf(ResourceNotFoundException.class);
	}

}
