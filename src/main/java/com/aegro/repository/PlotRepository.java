package com.aegro.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.aegro.model.Plot;

@Repository
public interface PlotRepository extends MongoRepository<Plot, String>{
	
	@Query("{'fkFarm' : ?0}")
	List<Plot> findAllByFarmFk(String fkFarm);
	
	@Query("{'fkFarm' : ?0, 'id' : ?1}")
	Optional<Plot> findPlotById(String fkFarm, String id);
	
	@Query(value = "{'fkFarm' : ?0, 'id' : ?1}", delete = true)
	void deletePlotById(String fkFarm, String id);
}
