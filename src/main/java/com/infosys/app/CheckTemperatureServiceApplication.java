package com.infosys.app;

import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SpringBootApplication(scanBasePackages = {"com.infosys"})
public class CheckTemperatureServiceApplication  /*implements CommandLineRunner*/ {
	private static final Logger logger = LoggerFactory.getLogger(CheckTemperatureServiceApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(CheckTemperatureServiceApplication.class, args);
	}
	
/*	@Override
	public void run(String... arg0) throws Exception {	
	
//	 Scanner scan = new Scanner(System.in);
//	 String line = null;
//	 
//     try {
//         do {
//        	 System.out.print(">");
//             line = scan.nextLine();             
//         } while(!"quit".equals(line));
//
//     } finally {
//         scan.close();
//     }
	
	
	}*/

}
