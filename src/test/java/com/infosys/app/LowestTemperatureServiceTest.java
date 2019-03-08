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

import com.infosys.service.LowestTemperatureService;

@Configuration
@PropertySource("classpath:application-test.properties")
@ComponentScan("com.infosys")
class DummyContext {
    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
       return new PropertySourcesPlaceholderConfigurer();
    }
}

@RunWith(SpringRunner.class)
@ContextConfiguration(classes=DummyContext.class,                                                   
                      loader=AnnotationConfigContextLoader.class)
public class LowestTemperatureServiceTest {
	@Rule
    public ExpectedException thrown= ExpectedException.none();
	
	@Test
	public void findTheLowestTemperature() throws Exception {
		LowestTemperatureService lowestTemperatureService = mock(LowestTemperatureService.class);
		when(lowestTemperatureService.getBufferedReader("mock")).thenReturn(new BufferedReader(new FileReader("src/test/resources/Weather.Service.Data.80421.xml")));
		when(lowestTemperatureService.getLowestTemperature("mock",6)).thenCallRealMethod();
		
		String lowestTemp = lowestTemperatureService.getLowestTemperature("mock",6);
		
		assertEquals("27.68", lowestTemp);
	}
	
	@Test(expected = XMLStreamException.class)
	public void findTheLowestTemperatureFail() throws Exception {
		
		LowestTemperatureService lowestTemperatureService = mock(LowestTemperatureService.class);
		when(lowestTemperatureService.getBufferedReader("mock")).thenReturn(null);
		when(lowestTemperatureService.getLowestTemperature("mock",6)).thenCallRealMethod();
		
		String lowestTemp = lowestTemperatureService.getLowestTemperature("mock",6);		
	}

}
