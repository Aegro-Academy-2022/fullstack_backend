package com.aegro.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.aegro.model.Farm;
import com.aegro.repository.FarmRepository;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api/v1")
public class FarmController {
	
	@Autowired
	private FarmRepository farmRepository;
	
	@PostMapping("/farms")
	@ResponseBody 
	public ResponseEntity<Farm> createFarm(@RequestBody Farm farm) {
		try {
			Farm _farm = farmRepository.save(new Farm(farm.getName()));
			return new ResponseEntity<>(_farm, HttpStatus.CREATED);
			
		}catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}

}
