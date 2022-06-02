package com.aegro.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aegro.exception.*;
import com.aegro.model.Plot;
import com.aegro.repository.PlotRepositoryImpl;

@Service
public class PlotServiceImpl implements PlotService{
	@Autowired
	private PlotRepositoryImpl plotRepo;
	
	@Autowired
	private Validation validation;
	
	@Autowired
	private Productivity productivity;
	
	@Override
	public Plot insert(Plot plot, String fkFarm) {
		try {
			if(validation.verifyName(plot.getName())
					|| validation.verifyNum(plot.getArea())) {
				throw new InvalidInputDataException();
			}

			return plotRepo.save(plot, fkFarm);
		}catch(InvalidInputDataException e) {
			throw new InvalidInputDataException();
			
		}catch(Exception e) {
			throw new InternalServerException("Não foi possível realizar a inserção");
		}
	}

	@Override
	public List<Plot> getAll(String fkFarm) {
		try {
			return plotRepo.findAll(fkFarm);
		}catch(Exception e) {
			throw new EmptyListException("talhão");
		}
	}

	@Override
	public Plot getById(String fkFarm, String id) {
		try {
			return plotRepo.findById(fkFarm, id);
		}catch(Exception e) {
			throw new ResourceNotFoundException("Talhão");
		}
	}

	@Override
	public Plot update(String fkFarm, String id, Plot plot) {
		try {
			if(validation.verifyName(plot.getName())
					|| validation.verifyNum(plot.getArea())) {
				throw new InvalidInputDataException();
			}
			
			Plot _plot = plotRepo.update(fkFarm, id, plot);
						
			productivity.defineProductivityPlot(fkFarm, id);
			productivity.defineProductivityFarm(fkFarm);
			
			return _plot;
			
		}catch(InvalidInputDataException e) {
			throw new InvalidInputDataException();
			
		}catch(Exception e) {
			throw new ResourceNotFoundException("Talhão");
		}
	}

	@Override
	public void remove(String fkFarm, String id) {
		try {
			plotRepo.delete(fkFarm, id);
			
			productivity.defineProductivityFarm(fkFarm);
		}catch(Exception e) {
			throw new ResourceNotFoundException("Talhão");
		}
	}

}
