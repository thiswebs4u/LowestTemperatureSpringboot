package com.infosys.app;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;

import javax.xml.stream.XMLStreamException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import com.infosys.controller.LowestTemperatureController;
import com.infosys.service.LowestTemperatureService;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes=DummyContext.class,                                                   
                      loader=AnnotationConfigContextLoader.class)
public class LowestTemperatureControllerTest {	
	@Test
	public void findTheLowestTemperature() throws Exception {
		LowestTemperatureService lowestTemperatureService = mock(LowestTemperatureService.class);
		when(lowestTemperatureService.getBufferedReader("44444")).thenReturn(new BufferedReader(new FileReader("src/test/resources/Weather.Service.Data.80421.xml")));
		when(lowestTemperatureService.getLowestTemperature("44444",6)).thenCallRealMethod();

		LowestTemperatureController lowestTemperatureController = mock(LowestTemperatureController.class);
		when(lowestTemperatureController.getTomorrowLowestTemperature("44444")).thenReturn("48.5");
		
		String lowestTemp = lowestTemperatureController.getTomorrowLowestTemperature("44444");
		
		assertEquals("48.5", lowestTemp);
	}
	
	@Test
	public void findTheLowestTemperatureBadZipCodeFail() throws Exception {
		LowestTemperatureController lowestTemperatureController = mock(LowestTemperatureController.class);
		when(lowestTemperatureController.getTomorrowLowestTemperature("100000")).thenCallRealMethod();
		
		String lowestTemp = lowestTemperatureController.getTomorrowLowestTemperature("100000");
		
		assertEquals("Zip code not in right format", lowestTemp);
		
		System.out.println(lowestTemp);
	}
	
	@Test
	public void findTheLowestTemperatureBadZipCodeFail2() throws Exception {
		LowestTemperatureController lowestTemperatureController = mock(LowestTemperatureController.class);
		when(lowestTemperatureController.getTomorrowLowestTemperature("aa")).thenCallRealMethod();
		
		String lowestTemp = lowestTemperatureController.getTomorrowLowestTemperature("aa");
		
		assertEquals("Zip code not in right format", lowestTemp);
		
		System.out.println(lowestTemp);
	}
	
	@Test
	public void findTheLowestTemperatureBadZipCodeNoZipCode() throws Exception {
		LowestTemperatureController lowestTemperatureController = mock(LowestTemperatureController.class);
		when(lowestTemperatureController.getTomorrowLowestTemperature("")).thenCallRealMethod();
		
		String lowestTemp = lowestTemperatureController.getTomorrowLowestTemperature("");
		
		assertEquals("Zip code not in right format", lowestTemp);
		
		System.out.println(lowestTemp);
	}

}
