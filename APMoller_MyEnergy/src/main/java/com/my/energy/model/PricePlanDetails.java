package com.my.energy.model;

import javax.persistence.Column;
/*
 * Entity Class for storing all Price Plans
 */
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "price_plan")
public class PricePlanDetails {

	@NotNull
	@Id
	@Column(name = "price_plan_name")
	private String pricePlanName;
	
	@Column(name = "unit_rate")
	private Integer unitRate;

	

	public String getPrice_plan_name() {
		return pricePlanName;
	}

	public void setPricePlanName(String pricePlanName) {
		this.pricePlanName = pricePlanName;
	}

	public Integer getUnitRate() {
		return unitRate;
	}

	public void setUnitRate(Integer unitRate) {
		this.unitRate = unitRate;
	}

}
