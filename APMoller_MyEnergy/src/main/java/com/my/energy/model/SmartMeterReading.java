package com.my.energy.model;

import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.TimeZone;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

/*
 * Entity Class for storing Smart Meter Readings
 */

@Entity
@Table(name = "METER_READING")
public class SmartMeterReading {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "reading_id")
	private Integer id;

	
	@JsonProperty("time")
	@Column(name = "time_of_reading")
	@NotNull
	private Long timeOfReading;

	@NotNull
	@Column(name = "reading")
	private Double reading;

	@NotNull
	@Column(name = "smart_meter_id")
	private String smartMeterId;
	
	public SmartMeterReading(){
		
	}
	
	public SmartMeterReading(long time,double reading,String smartMeterID){
		
		this.timeOfReading=time;
		this.reading=reading;
		this.smartMeterId=smartMeterID;
	}

	@JsonIgnore
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Double getReading() {
		return reading;
	}

	public void setReading(Double reading) {
		this.reading = reading;
	}

	@JsonIgnore
	public String getSmartMeterId() {
		return smartMeterId;
	}

	public void setSmartMeterId(String smartMeterId) {
		this.smartMeterId = smartMeterId;
	}

	public String getTime() {

		Date date = new Date(timeOfReading * 1000L);
		// format of the date
		SimpleDateFormat jdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
		jdf.setTimeZone(TimeZone.getTimeZone("UTC"));
		String formattedTime = jdf.format(date);
		return formattedTime;
	}

	public void setTime(Long time) {
		this.timeOfReading = time;
	}

}
