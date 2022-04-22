package com.aegro.repository;

import org.springframework.stereotype.Repository;

import com.aegro.model.Farm;

import org.springframework.data.mongodb.repository.MongoRepository;

@Repository
public interface FarmRepository extends MongoRepository<Farm, String>{

}
