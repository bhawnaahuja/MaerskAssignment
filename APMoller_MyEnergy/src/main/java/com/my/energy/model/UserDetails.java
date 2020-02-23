package com.my.energy.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/*
 * Entity Class for storing User Details
 */

@Entity
@Table(name = "user_details")
public class UserDetails {

	@NotNull
	@Id
	@Column(name = "smart_meter_id")
	private String smartMeterId;
	
	@Column(name = "current_price_plan")
	private String currentPricePlan;

	
	public UserDetails() {

	}

	public String getSmartMeterId() {
		return smartMeterId;
	}

	public void setSmartMeterId(String smartMeterId) {
		this.smartMeterId = smartMeterId;
	}


  
	public String getCurrentPricePlan() {
		return currentPricePlan;
	}

	public void setCurrentPricePlan(String currentPricePlan) {
		this.currentPricePlan = currentPricePlan;
	}



}
