package com.my.energy.tests;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.my.energy.controller.MyEnergyController;
import com.my.energy.model.SmartMeterReading;
import com.my.energy.service.SmartMeterService;

@RunWith(SpringRunner.class)
@WebMvcTest(MyEnergyController.class)
public class MyEnergySpringBootApplicationTest {

	@Autowired
	private MockMvc mvc;

	@MockBean
	private SmartMeterService service;

	@Test

	public void retreiveReadings() throws Exception {
		List<SmartMeterReading> list = new ArrayList<>();
		SmartMeterReading reading = new SmartMeterReading(1579613461, 2.3, "smart-meter-0");

		list.add(reading);
		Mockito.when(service.findBySmartMeterId(Mockito.anyString())).thenReturn(list);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1/readings/read/smart-meter-0")
				.accept(MediaType.APPLICATION_JSON);

		MvcResult result = mvc.perform(requestBuilder).andReturn();
		assertThat(result.getResponse().getContentAsString()).isNotEmpty();

	}

	@Test

	public void testCompareAllPlans() throws Exception {
		List<SmartMeterReading> list = new ArrayList<>();
		SmartMeterReading reading = new SmartMeterReading(1579613461, 2.3, "smart-meter-0");
		HashMap<String, Double> response = new LinkedHashMap<>();
		response.put("price-plan-0", 2.5);
		response.put("price-plan-1", 6.0);
		response.put("price-plan-2", 8.0);

		list.add(reading);
		Mockito.when(service.findBySmartMeterId(Mockito.anyString())).thenReturn(list);
		Mockito.when(service.getUsageCost(list)).thenReturn(response);
		Mockito.when(service.getCurrentPricePlan("smart-meter-0")).thenReturn("price-plan-0");
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1/price-plans/compare-all/smart-meter-0")
				.accept(MediaType.APPLICATION_JSON);
		
		
		mvc.perform(requestBuilder).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(status().isOk()).andExpect(jsonPath("$.pricePlanId", is("price-plan-0")));

	}

	@Test
	public void testViewRecommendedPlan() throws Exception {
		List<SmartMeterReading> list = new ArrayList<>();
		SmartMeterReading reading = new SmartMeterReading(1579613461, 2.3, "smart-meter-0");
		HashMap<String, Double> response = new LinkedHashMap<>();
		response.put("price-plan-0", 2.5);
		response.put("price-plan-1", 6.0);
		response.put("price-plan-2", 1.0);

		list.add(reading);
		Mockito.when(service.findBySmartMeterId(Mockito.anyString())).thenReturn(list);
		Mockito.when(service.getUsageCost(list)).thenReturn(response);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1/price-plans/recommend/smart-meter-0")
				.accept(MediaType.APPLICATION_JSON);

		mvc.perform(requestBuilder).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(3)));

	}

}
