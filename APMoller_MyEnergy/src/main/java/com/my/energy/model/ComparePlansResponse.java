package com.my.energy.model;

import java.util.HashMap;

/*
 * POJO Class for providing Comparision of all Price Plans to User
 */

public class ComparePlansResponse {
	
	private HashMap<String, Double> pricePlanComparisonData;
	private String pricePlanId;
	public HashMap<String, Double> getPricePlanComparisonData() {
		return pricePlanComparisonData;
	}
	public void setPricePlanComparisonData(HashMap<String, Double> pricePlanComparisonData) {
		this.pricePlanComparisonData = pricePlanComparisonData;
	}
	public String getPricePlanId() {
		return pricePlanId;
	}
	public void setPricePlanId(String pricePlanId) {
		this.pricePlanId = pricePlanId;
	}

}
