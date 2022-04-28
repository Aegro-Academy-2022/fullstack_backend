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

import com.aegro.model.Production;
import com.aegro.repository.ProductionRepository;

@RestController
@RequestMapping("/api/v1/farms/{fkFarm}/plots/{fkPlot}/productions")
public class ProductionController {
	@Autowired
	private ProductionRepository productionRepo;
	
	@PostMapping
	public ResponseEntity<Production> createProduction(@PathVariable("fkPlot") String fkPlot, @RequestBody Production production) {
		try {		
			Production _production = productionRepo.save(new Production(production.getKilo(), fkPlot));
			
			return new ResponseEntity<>(_production, HttpStatus.CREATED);
		}catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping
	public ResponseEntity<List<Production>> getAllProduction(@PathVariable("fkPlot") String fkPlot) {
		try {
			List<Production> productions = productionRepo.findAllByPlotFK(fkPlot);
			
			if(productions.isEmpty()) {
				return new ResponseEntity<>(productions, HttpStatus.NO_CONTENT);
			}
			
			return new ResponseEntity<>(productions, HttpStatus.OK);
			
		}catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Optional<Production>> getProduction(@PathVariable("fkPlot") String fkPlot, @PathVariable("id") String id){
		try {
			Optional<Production> _production =productionRepo.findProductionById(fkPlot, id);
			
			if(_production == null) {
				return new ResponseEntity<>(_production, HttpStatus.NOT_FOUND);
			}
			
			return new ResponseEntity<>(_production, HttpStatus.OK);
			
		}catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PutMapping("/{id}") 
	public ResponseEntity<Production> updateProduction(@PathVariable("fkPlot") String fkPlot, @PathVariable("id") String id, @RequestBody Production production) {
		try {	
			Production  _production = productionRepo.findProductionById(fkPlot, id).get();
			
			_production.setKilo(production.getKilo());
			_production.setFkPlot(fkPlot);
			productionRepo.save(_production);
			
			return new ResponseEntity<>(_production, HttpStatus.OK);
			
		}catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Production> deleteProduction(@PathVariable("fkPlot") String fkPlot, @PathVariable("id") String id){
		try {
			productionRepo.deleteProductionById(fkPlot, id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			
		} catch(Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
			
	}


}
