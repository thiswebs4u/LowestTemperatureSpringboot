package com.infosys.controller;

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
	LowestTemperatureService LowestTemperatureService;
	
	
	@GetMapping(value = "/get/{zipCode}")
	public String getTomorrowLowestTemperature(@PathVariable String zipCode) {
		
		
		return "0";
	}

}
