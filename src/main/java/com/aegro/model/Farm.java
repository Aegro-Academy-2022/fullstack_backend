package com.aegro.model;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.springframework.data.mongodb.core.mapping.Document;

@Entity
@Document(collection="Farm")
public class Farm {
	
	@Id
	private String id;
	private String name;
	
	public Farm(String name) {
		this.name = name;
	}
	
	public Farm() {}
	
	public String getId() {
		return this.id;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setId(String id) {
		this.id = id;
	}

}
