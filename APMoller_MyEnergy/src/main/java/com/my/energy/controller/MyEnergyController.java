package com.my.energy.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.my.energy.model.ComparePlansResponse;
import com.my.energy.model.SmartMeterReading;
import com.my.energy.model.SmartMeterReadingDTO;
import com.my.energy.service.SmartMeterService;

/**
 * This is the Controller class for exposing Rest API endpoints.
 * 
 * @author Bhawna Ahuja
 * 
 */

@RestController
@RequestMapping("/api/v1")
public class MyEnergyController {

	private static final Logger LOGGER = LoggerFactory.getLogger(MyEnergyController.class);

	@Autowired
	private SmartMeterService smartMeterService;

	/**
	 * This API stores readings for Smart Meter
	 * 
	 * @param SmartMeterReadingDTO
	 *            This DTO holds the Smart Meter Reading Information
	 * 
	 * @return ResponseEntity<String>.
	 */

	@PostMapping("/readings/store")
	public ResponseEntity<List<SmartMeterReading>> storeReadings(@Valid @RequestBody SmartMeterReadingDTO readingData) {
		LOGGER.info("Store Readings Method start ");
		return ResponseEntity.status(HttpStatus.OK).body(smartMeterService.storeReading(readingData));

	}

	/**
	 * This API retreives stores readings associated with a Smart Meter
	 * 
	 * @param String
	 *            smartMeterId
	 * @return ResponseEntity<List<SmartMeterReading>>.
	 */

	@GetMapping("/readings/read/{smartMeterId}")
	public ResponseEntity<List<SmartMeterReading>> retreiveReadings(
			@PathVariable(value = "smartMeterId") String smartMeterId)  {
		LOGGER.info("Retreive Readings Method start ");
		return ResponseEntity.status(HttpStatus.OK).body(smartMeterService.findBySmartMeterId(smartMeterId));
	}

	/**
	 * This API provides the current Price Plan associated with the meterID and
	 * compares the usage cost against all plans for the current Reading.
	 * 
	 * @param String
	 *            smartMeterId
	 * @return ResponseEntity<ComparePlansResponse>.
	 */
	@GetMapping("/price-plans/compare-all/{smartMeterId}")
	public ResponseEntity<ComparePlansResponse> viewAndcompareAllPlans(

			@PathVariable(value = "smartMeterId") String smartMeterId) throws Exception {
		LOGGER.info("ViewAndCompareAllPlans Method start ");
		List<SmartMeterReading> readings = smartMeterService.findBySmartMeterId(smartMeterId);

		HashMap<String, Double> getUsageCostData = smartMeterService.getUsageCost(readings);

		ComparePlansResponse responseObject = new ComparePlansResponse();
		responseObject.setPricePlanComparisonData(getUsageCostData);
		responseObject.setPricePlanId(smartMeterService.getCurrentPricePlan(smartMeterId));

		return ResponseEntity.status(HttpStatus.OK).body(responseObject);
	}

	/**
	 * This API provides the most recommended Price Plan for usage.
	 * 
	 * @param String
	 *            smartMeterId
	 * @return ResponseEntity<Map<String, Double>>.
	 */

	@GetMapping(value = "/price-plans/recommend/{smartMeterId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> viewRecommendedPlan(@PathVariable(value = "smartMeterId") String smartMeterId)
			throws Exception {
		LOGGER.info("ViewRecommendedPlan Method start ");
		List<SmartMeterReading> readings = smartMeterService.findBySmartMeterId(smartMeterId);
		HashMap<String, Double> pricePlanComparisonData = smartMeterService.getUsageCost(readings);

		Map<String, Double> response = pricePlanComparisonData.entrySet().stream().sorted(Map.Entry.comparingByValue())
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (oldValue, newValue) -> oldValue,
						LinkedHashMap::new));
		List<JSONObject> responseData = generateResponse(response);

		return ResponseEntity.status(HttpStatus.OK).body(responseData.toString());

	}

	private List<JSONObject> generateResponse(Map<String, Double> response) {
		List<JSONObject> list = new ArrayList<>();

		for (Map.Entry<String, Double> entry : response.entrySet()) {
			JSONObject json_obj = new JSONObject();
			String key = entry.getKey();
			Object value = entry.getValue();
			try {
				list.add(json_obj.put(key, value));
			} catch (Exception e) {

				LOGGER.error("Exception occured while generating JSON Response");
			}
		}

		return list;
	}

}
