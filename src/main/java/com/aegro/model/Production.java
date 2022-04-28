package com.aegro.model;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.springframework.data.mongodb.core.mapping.Document;

@Entity
@Document(collection="Production")
public class Production {
	
	@Id
	private String id;
	private BigDecimal kilo;
	private String fkPlot;
	
	public Production() {}
	
	public Production(BigDecimal kilo, String fkPlot) {
		this.kilo = kilo;
		this.fkPlot = fkPlot;
	}
	
	public Production(String id, BigDecimal kilo, String fkPlot) {
		this.id = id;
		this.kilo = kilo;
		this.fkPlot = fkPlot;
	}
	
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public BigDecimal getKilo() {
		return kilo;
	}
	
	public void setKilo(BigDecimal kilo) {
		this.kilo = kilo;
	}
	
	public String getFkPlot() {
		return fkPlot;
	}
	
	public void setFkPlot(String fkPlot) {
		this.fkPlot = fkPlot;
	}
	
}
