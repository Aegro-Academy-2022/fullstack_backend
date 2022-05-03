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
import com.aegro.model.Plot;
import com.aegro.repository.PlotRepository;
import com.aegro.service.PlotServiceImpl;

@RestController
@RequestMapping("/api/v1/farms/{fk}/plots")
public class PlotController {
	@Autowired
	private PlotServiceImpl plotService;
	
	@PostMapping
	public ResponseEntity<Plot> createPlot(@PathVariable("fk") String fk, @RequestBody Plot plot) {
		Plot _plot = plotService.insert(plot, fk);
		
		if(_plot.isNull()) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<>(_plot, HttpStatus.CREATED);
	}
	
	@GetMapping
	public ResponseEntity<List<Plot>> getAllPlot(@PathVariable("fk") String fk) {
		List<Plot> plots = plotService.getAll(fk);
		
		if(plots.isEmpty()) {
			return new ResponseEntity<>(plots, HttpStatus.NO_CONTENT);
		}
		
		return new ResponseEntity<>(plots, HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Plot> getPlot(@PathVariable("fk") String fk, @PathVariable("id") String id){
		Plot plot = plotService.getById(fk, id);
		
		if(plot.isNull()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<>(plot, HttpStatus.OK);
	}
	
	@PutMapping("/{id}") 
	public ResponseEntity<Plot> updatePlot(@PathVariable("fk") String fk, @PathVariable("id") String id, @RequestBody Plot plot) {
		Plot _plot = plotService.update(fk, id, plot);
		
		if(_plot.isNull()) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<>(_plot, HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Plot> deletePlot(@PathVariable("fk") String fk, @PathVariable("id") String id){
		boolean response = plotService.remove(fk, id);
		
		if(response) {
			return new ResponseEntity<>(HttpStatus.OK);
		}
		
		return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
	}

}
