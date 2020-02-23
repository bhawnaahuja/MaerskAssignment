package com.my.energy.service;

import java.util.HashMap;
import java.util.List;

import com.my.energy.model.PricePlanDetails;
import com.my.energy.model.SmartMeterReading;
import com.my.energy.model.SmartMeterReadingDTO;

public interface SmartMeterService {
	List<SmartMeterReading> storeReading(SmartMeterReadingDTO readingData);

	List<PricePlanDetails> getAllPricePlans();

	String getCurrentPricePlan(String smartMeterId);

	List<SmartMeterReading> findBySmartMeterId(String smartMeterId);

	HashMap<String, Double> getUsageCost(List<SmartMeterReading> readings);

}
