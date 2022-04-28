package com.aegro.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.aegro.model.Plot;
import com.aegro.model.Production;

public interface ProductionRepository extends MongoRepository<Production, String>{
	@Query("{'fkPlot' : ?0}")
	List<Production> findAllByPlotFK(String fkPlot);
	
	@Query("{'fkPlot' : ?0, 'id' : ?1}")
	Optional<Production> findProductionById(String fkPlot, String id);
	
	@Query(value = "{'fkPlot' : ?0, 'id' : ?1}", delete = true)
	void deleteProductionById(String fkPlot, String id);

}
