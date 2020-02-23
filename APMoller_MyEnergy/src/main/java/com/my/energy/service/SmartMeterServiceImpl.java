package com.my.energy.service;

import java.util.ArrayList;
import java.util.Comparator;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.my.energy.exception.ResourceNotFoundException;
import com.my.energy.model.PricePlanDetails;
import com.my.energy.model.SmartMeterReading;
import com.my.energy.model.SmartMeterReadingDTO;
import com.my.energy.repository.SmartMeterRepository;

/**
 * Service Layer Implementation class for writing business logic.
 * 
 * 
 * @author Bhawna Ahuja
 * 
 *
 */
@Service
public class SmartMeterServiceImpl implements SmartMeterService {

	@Autowired
	private SmartMeterRepository smartMeterRepository;

	@Override
	public List<SmartMeterReading> storeReading(SmartMeterReadingDTO readingData) {
		List<SmartMeterReading> storedReadings = new ArrayList<SmartMeterReading>();
		for (SmartMeterReading reading : readingData.getElectricityReadings()) {
			reading.setSmartMeterId(readingData.getSmartMeterId());

			storedReadings.add(smartMeterRepository.save(reading));
		}
		if (null != storedReadings && !storedReadings.isEmpty()) {
			return storedReadings;
		} else

			throw new RuntimeException(
					"Exception occured while storing Reading for id : " + readingData.getSmartMeterId());

	}

	/**
	 * This Method gets All the Price Plans currently available with My Energy
	 * company.
	 * 
	 * 
	 * 
	 *
	 * @return This returns List<PricePlanDetails>.
	 */
	@Override
	public List<PricePlanDetails> getAllPricePlans() {

		return smartMeterRepository.getPricePlans();
	}

	/**
	 * This Method gets the current price plan associated with the MeterID.
	 * 
	 * @param smartMeterId:String
	 * 
	 *
	 * @return > This returns String.
	 */
	@Override
	public String getCurrentPricePlan(String smartMeterId) {

		return smartMeterRepository.getCurrentPricePlan(smartMeterId);
	}

	/**
	 * This Method retrieves readings stored for Smart Meter
	 * 
	 * @param smartMeterId:String
	 * 
	 *
	 * @return > This returns List<SmartMeterReading>.
	 */
	@Override
	public List<SmartMeterReading> findBySmartMeterId(String smartMeterId) {

		List<SmartMeterReading> readings = smartMeterRepository.findBySmartMeterId(smartMeterId);
		if (null != readings && !readings.isEmpty()) {
			return readings;
		} else

			throw new ResourceNotFoundException("No Readings found for the Smart Meter  with id : " + smartMeterId);
	}

	/**
	 * This Method calculates the usage cost against all Price plan for the
	 * latest reading of the Smart Meter
	 * 
	 * @param readings:List<SmartMeterReading>
	 * 
	 *
	 * @return > This returns HashMap<String, Double>.
	 */

	@Override
	public HashMap<String, Double> getUsageCost(List<SmartMeterReading> readings) {
		SmartMeterReading maxReading = readings.stream()
				.collect(Collectors.maxBy(Comparator.comparing(SmartMeterReading::getTime))).get();
		HashMap<String, Double> pricePlanComparisonData = new HashMap<>();

		List<PricePlanDetails> pricePlans = getAllPricePlans();
		pricePlans.stream().forEach(x -> {
			pricePlanComparisonData.put(x.getPrice_plan_name(),
					x.getUnitRate().doubleValue() * maxReading.getReading());
		});
		return pricePlanComparisonData;
	}

}
