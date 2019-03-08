package com.infosys.controller;

import java.util.Calendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.infosys.app.CheckTemperatureServiceApplication;
import com.infosys.service.LowestTemperatureService;

@RestController
public class LowestTemperatureController {
	private static final Logger logger = LoggerFactory.getLogger(LowestTemperatureController.class);

	@Value("${weather.service.endpoint}")
	private String endPoint;

	@Autowired
	private LowestTemperatureService lowestTemperatureService;
	
	@GetMapping(value = "/getTomorrowLowestTemperature/{zipCode}")
	public String getTomorrowLowestTemperature(@PathVariable String zipCode) {
        
        Calendar c = Calendar.getInstance();
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
        int targetDayOfWeek = dayOfWeek==7?1:dayOfWeek+1;
        
        logger.debug("Day of week = "+dayOfWeek+" Target day of week "+targetDayOfWeek);
        
        
        String lowestTemp = null;
        
        endPoint += zipCode;
        
		try {
			lowestTemp = lowestTemperatureService.getLowestTemperature(endPoint, targetDayOfWeek);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
		logger.debug("Tomorrows lowest temp = "+lowestTemp);

		
		return lowestTemp;
	}

}
