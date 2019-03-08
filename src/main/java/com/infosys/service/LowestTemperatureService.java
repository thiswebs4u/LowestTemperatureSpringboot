package com.infosys.service;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class LowestTemperatureService {
	private static final Logger logger = LoggerFactory.getLogger(LowestTemperatureService.class);

	public String getLowestTemperature(String endpoint, int targetDayOfWeek) throws Exception {
		int currentDayOfWeek = 0;
		double lowestTemp = 1000.0;
		XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
		try {
			XMLEventReader xmlEventReader = xmlInputFactory.createXMLEventReader(getBufferedReader(endpoint));
//			XMLEventReader xmlEventReader = xmlInputFactory.createXMLEventReader(new FileInputStream("./Weather.Service.Data.80421.xml"));
			

			while (xmlEventReader.hasNext()) {
				XMLEvent xmlEvent = xmlEventReader.nextEvent();
				if (xmlEvent.isStartElement()) {
					StartElement startElement = xmlEvent.asStartElement();
					if (startElement.getName().getLocalPart().equals("forecast")) {
						Attribute idAttr = startElement.getAttributeByName(new QName("dayOfWeek"));
						if (idAttr != null) {
							currentDayOfWeek = Integer.parseInt(idAttr.getValue());
							logger.debug("currentDayOfWeek = "+currentDayOfWeek);
						}
					}
					/**
					 * Get element temperature and compare to lowest variable.
					 */
					if (startElement.getName().getLocalPart().equals("temperature")
							&& (currentDayOfWeek == targetDayOfWeek)) {
						xmlEvent = xmlEventReader.nextEvent();
						double thisTemp = Double.parseDouble(xmlEvent.asCharacters().getData());
						lowestTemp = Double.compare(thisTemp, lowestTemp) < 0 ? thisTemp : lowestTemp;
					}
				}
				if (xmlEvent.isEndElement()) {
					EndElement endElement = xmlEvent.asEndElement();
					if (endElement.getName().getLocalPart().equals("forecast")) {
						currentDayOfWeek = 0;
					}
				}
			}

		} catch (FileNotFoundException | XMLStreamException e) {
			e.printStackTrace();
			throw e;
		}
		return String.valueOf(lowestTemp);
	}

	public BufferedReader getBufferedReader(String endpoint) throws Exception {
		URL url = new URL(endpoint);
		url.openStream().close();
		URLConnection con = url.openConnection();
		InputStream is = con.getInputStream();

		return new BufferedReader(new InputStreamReader(is));
	}

}
