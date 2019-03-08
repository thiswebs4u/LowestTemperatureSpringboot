package com.infosys.controller;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.infosys.service.LowestTemperatureService;

@RestController
public class LowestTemperatureController {
	@Autowired
	LowestTemperatureService lowestTemperatureService;
	
	
	@GetMapping(value = "/get/{zipCode}")
	public String getTomorrowLowestTemperature(@PathVariable String zipCode) {
        String fileName = "./Weather.Service.Data.80421.xml";
        
        Calendar c = Calendar.getInstance();
        //c.set(Calendar.DAY_OF_WEEK,7);
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
        int targetDayOfWeek = dayOfWeek==7?1:dayOfWeek+1;
        
        System.out.println("Day of week = "+dayOfWeek+" Target day of week "+targetDayOfWeek);
        
        
        double lowestTemp = lowestTemperatureService.getLowestTemperature(fileName, targetDayOfWeek);
        
        System.out.println("Tomorrows lowest temp = "+lowestTemp);

		
		return "0";
	}

}
