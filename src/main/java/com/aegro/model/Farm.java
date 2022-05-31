package com.aegro.model;

import java.math.BigDecimal;

import javax.persistence.Entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Document(collection="Farm")
public class Farm {
	
	@Id
	private String id;
	
	private String name;
	private BigDecimal productivity;
	
	public Farm(String name) {
		this.name = name;
		this.productivity = new BigDecimal(0);
	}
	
	public Farm(String id, String name) {
		this.id = id;
		this.name = name;
		this.productivity = new BigDecimal(0);
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

	public void setName(String name) {
		this.name = name;
	}
	
	public BigDecimal getProductivity() {
		return productivity;
	}

	public void setProductivity(BigDecimal productivity) {
		this.productivity = productivity;
	}
	
	@JsonIgnore
	public boolean isEmpty() {
		if(this.name == null && this.id ==null) {
			return true;
		}
		return false;
	}
}
