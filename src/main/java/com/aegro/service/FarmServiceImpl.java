package com.aegro.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aegro.exception.*;
import com.aegro.model.Farm;
import com.aegro.repository.FarmRepositoryImpl;

@Service
public class FarmServiceImpl implements FarmService {
	
	@Autowired
	private FarmRepositoryImpl farmRepo;
	
	@Autowired
	private Validation validation;

	@Override
	public Farm insert(Farm farm) {
		try {
			if(validation.verifyName(farm.getName())) {
				throw new InvalidInputDataException();
			}

			return farmRepo.save(farm);
		}catch(Exception e) {
			throw new InternalServerException("Não foi possível realizar a inserção");
		}
	}

	@Override
	public List<Farm> getAll() {
		try {
			return farmRepo.findAll();
		}catch(Exception e) {
			throw new EmptyListException("fazenda");
		}
	}

	@Override
	public Farm getById(String id) {
		try {
			return farmRepo.findById(id);
		}catch(Exception e) {
			throw new ResourceNotFoundException("Fazenda");
		}
	}

	@Override
	public Farm update(String id, Farm farm) {
		try {
			if(validation.verifyName(farm.getName())) {
				throw new InvalidInputDataException();
			}
			
			return farmRepo.update(id, farm);
		}catch(Exception e) {
			throw new ResourceNotFoundException("Fazenda");
		}
	}

	@Override
	public void remove(String id) {
		try {
			farmRepo.delete(id);
		}catch(Exception e) {
			throw new ResourceNotFoundException("Fazenda");
		}
	}

}
