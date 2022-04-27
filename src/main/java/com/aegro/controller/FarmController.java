package com.aegro.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aegro.model.Farm;
import com.aegro.repository.FarmRepository;

@RestController
@RequestMapping("/api/v1/farms")
public class FarmController {
	@Autowired
	private FarmRepository farmRepo;

	@PostMapping
	public ResponseEntity<Farm> createFarm(@RequestBody Farm farm) {
		try {
			if(farm.getName().isBlank() || farm.getName().isEmpty()) {
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
			
			Farm _farm = farmRepo.save(new Farm(farm.getName()));
			
			return new ResponseEntity<>(_farm, HttpStatus.CREATED);
		}catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	@GetMapping
	public ResponseEntity<List<Farm>> getAllFarms() {
		try {
			List<Farm> farms = farmRepo.findAll();
			
			if (farms.isEmpty()) {
				return new ResponseEntity<>(farms, HttpStatus.NO_CONTENT);
			}
			
			return new ResponseEntity<>(farms, HttpStatus.OK);
			
		}catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Optional<Farm>> getFarm(@PathVariable("id") String id){
		try {
			Optional<Farm> _farm = farmRepo.findById(id);
			
			if(_farm == null) {
				return new ResponseEntity<>(_farm, HttpStatus.NOT_FOUND);
			}
			
			return new ResponseEntity<>(_farm, HttpStatus.OK);
			
		}catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PutMapping("/{id}") 
	public ResponseEntity<Farm> updateFarm(@PathVariable("id") String id, @RequestBody Farm farm) {
		try {
			if(farm.getName().isBlank() || farm.getName().isEmpty()) {
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
			
			Farm _farm = farmRepo.findById(id).get();
			
			_farm.setName(farm.getName());
			farmRepo.save(_farm);
			
			return new ResponseEntity<>(_farm, HttpStatus.OK);
			
		}catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Farm> deleteFarm(@PathVariable("id") String id){
		try {
			farmRepo.deleteById(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			
		} catch(Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
			
	}
}
