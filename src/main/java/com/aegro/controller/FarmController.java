package com.aegro.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aegro.model.Farm;
import com.aegro.service.FarmServiceImpl;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/v1/farms")
public class FarmController {
	
	@Autowired
	private FarmServiceImpl farmService;

	@PostMapping
	public ResponseEntity<Farm> createFarm(@RequestBody Farm farm) {
		Farm _farm = farmService.insert(farm);
			
		return new ResponseEntity<>(_farm, HttpStatus.CREATED);
	}
	
	@GetMapping
	public ResponseEntity<List<Farm>> getAllFarms() {
		List<Farm> farms = farmService.getAll();

		return new ResponseEntity<>(farms, HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Farm> getFarm(@PathVariable("id") String id){
		Farm farm = farmService.getById(id);
		
		return new ResponseEntity<>(farm, HttpStatus.OK);
	}
	
	@PutMapping("/{id}") 
	public ResponseEntity<Farm> updateFarm(@PathVariable("id") String id, @RequestBody Farm farm) {
		Farm _farm = farmService.update(id, farm);
		
		return new ResponseEntity<>(_farm, HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Farm> deleteFarm(@PathVariable("id") String id){
		farmService.remove(id);
		
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
