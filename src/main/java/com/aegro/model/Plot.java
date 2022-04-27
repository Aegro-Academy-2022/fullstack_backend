package com.aegro.model;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.springframework.data.mongodb.core.mapping.Document;

@Entity
@Document(collection="Plot")
public class Plot {
	
	@Id
	private String id;
	private String name;
	private BigDecimal area;
	private String fkFarm;
	
	public Plot() {}
	
	public Plot(String name, BigDecimal area, String fkFarm) {
		this.name = name;
		this.area = area;
		this.fkFarm = fkFarm;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BigDecimal getArea() {
		return area;
	}

	public void setArea(BigDecimal area) {
		this.area = area;
	}

	public String getFkFarm() {
		return fkFarm;
	}

	public void setFkFarm(String fkFarm) {
		this.fkFarm = fkFarm;
	}

}
