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

import com.aegro.model.Plot;
import com.aegro.repository.PlotRepository;

@RestController
@RequestMapping("/api/v1/farms/{fk}/plots")
public class PlotController {
	@Autowired
	private PlotRepository plotRepo;
	
	@PostMapping
	public ResponseEntity<Plot> createPlot(@PathVariable("fk") String fk, @RequestBody Plot plot) {
		try {		
			Plot _plot = plotRepo.save(new Plot(plot.getName(), plot.getArea(), fk));
			
			return new ResponseEntity<>(_plot, HttpStatus.CREATED);
		}catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping
	public ResponseEntity<List<Plot>> getAllPlot(@PathVariable("fk") String fk) {
		try {
			List<Plot> plots = plotRepo.findAllByFarmFk(fk);
			
			if(plots.isEmpty()) {
				return new ResponseEntity<>(plots, HttpStatus.NO_CONTENT);
			}
			
			return new ResponseEntity<>(plots, HttpStatus.OK);
			
		}catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Optional<Plot>> getPlot(@PathVariable("fk") String fk, @PathVariable("id") String id){
		try {
			Optional<Plot> _plot = plotRepo.findPlotById(fk, id);
			
			if(_plot == null) {
				return new ResponseEntity<>(_plot, HttpStatus.NOT_FOUND);
			}
			
			return new ResponseEntity<>(_plot, HttpStatus.OK);
			
		}catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PutMapping("/{id}") 
	public ResponseEntity<Plot> updatePlot(@PathVariable("fk") String fk, @PathVariable("id") String id, @RequestBody Plot plot) {
		try {			
			Plot _plot = plotRepo.findPlotById(fk, id).get();
			
			_plot.setName(plot.getName());
			_plot.setArea(plot.getArea());
			_plot.setFkFarm(fk);
			plotRepo.save(_plot);
			
			return new ResponseEntity<>(_plot, HttpStatus.OK);
			
		}catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Plot> deletePlot(@PathVariable("fk") String fk, @PathVariable("id") String id){
		try {
			plotRepo.deletePlotById(fk, id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			
		} catch(Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
			
	}

}
