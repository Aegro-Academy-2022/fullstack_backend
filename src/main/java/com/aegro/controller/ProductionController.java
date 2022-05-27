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

import com.aegro.model.Production;
import com.aegro.service.ProductionServiceImpl;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/v1/farms/{fkFarm}/plots/{fkPlot}/productions")
public class ProductionController {
	@Autowired
	private ProductionServiceImpl productionService;
	
	@PostMapping
	public ResponseEntity<Production> createProduction(@PathVariable("fkFarm") String fkFarm, @PathVariable("fkPlot") String fkPlot, @RequestBody Production production) {
		Production _production = productionService.insert(production, fkPlot, fkFarm);

		return new ResponseEntity<>(_production, HttpStatus.CREATED);
	}

	@GetMapping
	public ResponseEntity<List<Production>> getAllProduction(@PathVariable("fkPlot") String fkPlot) {
		List<Production> productions = productionService.getAll(fkPlot);

		return new ResponseEntity<>(productions, HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Production> getProduction(@PathVariable("fkPlot") String fkPlot, @PathVariable("id") String id){
		Production production = productionService.getById(fkPlot, id);

		return new ResponseEntity<>(production, HttpStatus.OK);
	}
	
	@PutMapping("/{id}") 
	public ResponseEntity<Production> updateProduction(@PathVariable("fkFarm") String fkFarm, @PathVariable("fkPlot") String fkPlot, @PathVariable("id") String id, @RequestBody Production production) {
		Production _production = productionService.update(fkFarm, fkPlot, id, production);
		
		return new ResponseEntity<>(_production, HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Production> deleteProduction(@PathVariable("fkFarm") String fkFarm,@PathVariable("fkPlot") String fkPlot, @PathVariable("id") String id){
		productionService.remove(fkFarm, fkPlot, id);
		
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
}
