package com.infosys.controller;

import java.util.Calendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.infosys.service.LowestTemperatureService;

/**
 * 
 * Finds lowest temperature for tomorrow given a zipcode.
 * 
 * 
 * @author john.hart@infosys.com
 *
 */
@RestController
public class LowestTemperatureController {
	private static final Logger logger = LoggerFactory.getLogger(LowestTemperatureController.class);

	@Value("${weather.service.endpoint}")
	private String endPoint;

	@Autowired
	private LowestTemperatureService lowestTemperatureService;

	private String lowestTemp = null;

	/**
	 * getTomorrowLowestTemperature finds lowest temperature for tomorrow from given
	 * zipcode
	 * 
	 * @param zipCode
	 * @return
	 * @throws Exception
	 */
	@GetMapping(value = "/getTomorrowLowestTemperature/{zipCode}")
	public String getTomorrowLowestTemperature(@PathVariable String zipCode) throws Exception {
		Calendar c = Calendar.getInstance();
		int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
		int targetDayOfWeek = dayOfWeek == 7 ? 1 : dayOfWeek + 1;

		if((zipCode!=null)&&(zipCode.length() == 5)&&(Integer.valueOf(zipCode) <= 99999))
			lowestTemp = lowestTemperatureService.getLowestTemperature(endPoint+zipCode, targetDayOfWeek);
		else 
			return "Zip code not in right format";

		return lowestTemp;
	}

	@ExceptionHandler({ Exception.class })
	public String databaseError() {
		return "Weather Service Error";
	}

}
