package com.my.energy.tests;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.my.energy.exception.ResourceNotFoundException;
import com.my.energy.model.PricePlanDetails;
import com.my.energy.model.SmartMeterReading;
import com.my.energy.model.SmartMeterReadingDTO;
import com.my.energy.repository.SmartMeterRepository;
import com.my.energy.service.SmartMeterService;

@RunWith(SpringRunner.class)
@SpringBootTest

public class SmartMeterServiceImplTest {

	@Autowired
	private SmartMeterService smartMeterService;

	@MockBean
	private SmartMeterRepository smartMeterRepository;

	@Test(expected = ResourceNotFoundException.class)
	public void testFindBySmartMeterIdNotFound() {
		String smartMeterId = "smart-meter-0";
		Mockito.when(smartMeterRepository.findBySmartMeterId(smartMeterId)).thenReturn(null);
		smartMeterService.findBySmartMeterId(smartMeterId);

	}

	@Test(expected = Exception.class)
	public void testStoredReadingsNegative() {

		SmartMeterReading reading = new SmartMeterReading(1579613461, 2.3, "smart-meter-0");
		SmartMeterReadingDTO readingDTO = new SmartMeterReadingDTO();
		Mockito.when(smartMeterRepository.save(reading)).thenReturn(null);
		smartMeterService.storeReading(readingDTO);

	}

	
	public void testStoredReadingsPositive() {

		SmartMeterReading reading = new SmartMeterReading(1579613461, 1.8, "smart-meter-1");
		SmartMeterReadingDTO readingDTO = new SmartMeterReadingDTO();
		List<SmartMeterReading> list = new ArrayList<>();
		list.add(reading);
		readingDTO.setSmartMeterId("smart-meter-1");
		readingDTO.setElectricityReadings(list);

		Mockito.when(smartMeterRepository.save(reading)).thenReturn(reading);
		
		assertThat(smartMeterService.storeReading(readingDTO).get(0).getReading()).isEqualTo(1.8);

	}

	@Test
	public void testFindBySmartMeterId() {
		String smartMeterId = "smart-meter-0";
		List<SmartMeterReading> list = new ArrayList<>();
		SmartMeterReading reading = new SmartMeterReading(1579613461, 2.3, "smart-meter-0");
		list.add(reading);
		Mockito.when(smartMeterRepository.findBySmartMeterId(smartMeterId)).thenReturn(list);
		List<SmartMeterReading> result = smartMeterService.findBySmartMeterId(smartMeterId);

		assertThat(result.get(0).getReading()).isEqualTo(2.3);

	}

	@Test
	public void testGetAllPricePlans() {

		List<PricePlanDetails> pricePlanList = setPricePlanList();
		Mockito.when(smartMeterRepository.getPricePlans()).thenReturn(pricePlanList);
		List<PricePlanDetails> result = smartMeterService.getAllPricePlans();

		assertThat(result.size()).isEqualTo(2);

	}

	private List<PricePlanDetails> setPricePlanList() {
		List<PricePlanDetails> pricePlanList = new ArrayList<>();
		PricePlanDetails pricePlan1 = new PricePlanDetails();
		pricePlan1.setPricePlanName("price-plan-0");
		pricePlan1.setUnitRate(10);
		pricePlanList.add(pricePlan1);
		PricePlanDetails pricePlan2 = new PricePlanDetails();
		pricePlan2.setPricePlanName("price-plan-1");
		pricePlan2.setUnitRate(1);
		pricePlanList.add(pricePlan2);
		return pricePlanList;
	}

	@Test
	public void testGetCurrentPricePlan() {
		String smartMeterId = "smart-meter-0";
		String currentPricePlan = "price-plan-0";
		Mockito.when(smartMeterRepository.getCurrentPricePlan(smartMeterId)).thenReturn(currentPricePlan);

		assertThat(smartMeterService.getCurrentPricePlan(smartMeterId)).isEqualTo(currentPricePlan);
	}

	@Test
	public void testGetUsageCost() {
		List<SmartMeterReading> readings = new ArrayList<>();
		SmartMeterReading reading1 = new SmartMeterReading(1579613461, 1.32, "smart-meter-0");
		SmartMeterReading reading2 = new SmartMeterReading(1879613461, 1.52, "smart-meter-1");
		readings.add(reading1);
		readings.add(reading2);
		List<PricePlanDetails> pricePlanList = setPricePlanList();
		Mockito.when(smartMeterRepository.getPricePlans()).thenReturn(pricePlanList);

		assertThat(smartMeterService.getUsageCost(readings).size()).isEqualTo(2);
	}

}
