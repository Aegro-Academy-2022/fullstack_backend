package com.aegro.repository;

import java.util.List;
import java.util.Optional;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.aegro.model.Farm;

@RunWith(SpringJUnit4ClassRunner.class)
@DataMongoTest
public class FarmRepositoryTest {
	
	@Autowired
	FarmRepository farmRepo;
	
	private Farm farm;
	
	@Before
	public void setUp() {
		this.farm = new Farm("Test Farm"); 
	}
	
	@After
	public void remove() throws Exception {
		farmRepo.deleteById(farm.getId());;
	}
	
	@Test
	public void deveriaCriaNovaFazenda() throws Exception {
		Farm _farm = farmRepo.save(farm);
		
		Assertions.assertEquals(_farm.getName(), "Test Farm");
	}
		
	@Test
	public void listaNaoDeveriaEstarVazia() throws Exception {
		farmRepo.save(farm);
		List <Farm> farms = farmRepo.findAll();
		
		Assertions.assertFalse(farms.isEmpty());
	}
	
	@Test
	public void deveriaRetornarFazenda() throws Exception {
		Farm _farm = farmRepo.save(farm);
		
		Optional<Farm> response = farmRepo.findById(_farm.getId());
		
		Assertions.assertEquals(response.get().getId(), _farm.getId());
		Assertions.assertEquals(response.get().getName(), _farm.getName());
	}
	
	@Test
	public void deveriaAtualizarDadosDaFazenda() throws Exception {
		Farm _farm = farmRepo.save(farm);
		
		Farm response = new Farm();
		response.setId(_farm.getId());
		response.setName("Test Farm 2");
		
		farmRepo.save(response);
		
		Assertions.assertEquals(farmRepo.findById(_farm.getId()).get().getName(), response.getName());
	}


}
