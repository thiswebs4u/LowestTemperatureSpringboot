package com.infosys.app;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.client.RestTemplate;

import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * SpringBootApplication to host web Service and Service to get lowest temperature
 * tomorrow given a zip code.
 * 
 * @author john.hart
 *
 */
@SpringBootApplication(scanBasePackages = {"com.infosys"})
public class CheckTemperatureServiceApplication  implements CommandLineRunner {
	private static final Logger logger = LoggerFactory.getLogger(CheckTemperatureServiceApplication.class);
	
	@Value("http://localhost:${server.port}")
	String serverURL;

	public static void main(String[] args) {
		SpringApplication.run(CheckTemperatureServiceApplication.class, args);
	}
	
	@Override
	public void run(String... arg0) throws Exception {	
		Scanner scan = new Scanner(System.in);
		String zipCode = null;

		try {
			do {
				System.out.println("Please enter zip code., exit to quit application");
				System.out.print(">");
				zipCode = scan.nextLine();
				
				if(!"exit".equals(zipCode)) {
					RestTemplate restTemplate = new RestTemplate();
					String temperature = restTemplate.getForObject(serverURL+"/getTomorrowLowestTemperature/"+zipCode, String.class);
					System.out.println("Lowest temperature hourly for tomorrow is "+temperature+" F");
				}
				
			} while (!"exit".equals(zipCode));
			logger.debug("exiting");
		} finally {
			scan.close();
			System.exit(0);
		}	
	}
}
