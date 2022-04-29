package com.aegro.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aegro.model.Farm;
import com.aegro.repository.FarmRepository;

@Service
public class FarmServiceImpl implements FarmService {
	
	@Autowired
	private FarmRepository farmRepo;
	
	private Validation validation = new Validation();

	@Override
	public Farm insert(Farm farm) {
		try {
			if(validation.verifyName(farm.getName())) {
				return new Farm();
			}

			return farmRepo.save(farm);
		}catch(Exception e) {
			return new Farm();
		}
	}

	@Override
	public List<Farm> getAll() {
		try {
			return farmRepo.findAll();
		}catch(Exception e) {
			return new ArrayList<>();
		}
	}

	@Override
	public Farm getById(String id) {
		try {
			return farmRepo.findById(id);
		}catch(Exception e) {
			return new Farm();
		}
	}

	@Override
	public Farm update(String id, Farm farm) {
		try {
			if(validation.verifyName(farm.getName())) {
				return new Farm();
			}
			
			return farmRepo.update(id, farm);
		}catch(Exception e) {
			return new Farm();
		}
	}

	@Override
	public boolean remove(String id) {
		try {
			farmRepo.delete(id);
			return true;
		}catch(Exception e) {
			return false;
		}
	}

}
