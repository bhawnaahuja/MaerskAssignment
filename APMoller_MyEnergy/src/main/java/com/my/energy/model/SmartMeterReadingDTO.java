package com.my.energy.model;

import java.util.List;

public class SmartMeterReadingDTO {

	private String smartMeterId;
	private List<SmartMeterReading> electricityReadings;

	public String getSmartMeterId() {
		return smartMeterId;
	}

	public void setSmartMeterId(String smartMeterId) {
		this.smartMeterId = smartMeterId;
	}

	public List<SmartMeterReading> getElectricityReadings() {
		return electricityReadings;
	}

	public void setElectricityReadings(List<SmartMeterReading> electricityReadings) {
		this.electricityReadings = electricityReadings;
	}

}
