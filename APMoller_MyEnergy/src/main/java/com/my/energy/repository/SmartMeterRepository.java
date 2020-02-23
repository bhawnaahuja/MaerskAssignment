package com.my.energy.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.my.energy.model.PricePlanDetails;
import com.my.energy.model.SmartMeterReading;

/**
 * DAO Layer Implementation  for database interaction.
 * 
 * 
 * @author Bhawna Ahuja
 * 
 *
 */
@Repository
public interface SmartMeterRepository extends JpaRepository<SmartMeterReading, String> {

	@Query(" select r from SmartMeterReading r  where r.smartMeterId = :smartMeterId")
	List<SmartMeterReading> findBySmartMeterId(@Param("smartMeterId") String smartMeterId);

	@Query(" select r from PricePlanDetails r")
	List<PricePlanDetails> getPricePlans();

	@Query(" select currentPricePlan from  UserDetails where  smartMeterId= :smartMeterId")
	String getCurrentPricePlan(@Param("smartMeterId") String smartMeterId);

}
