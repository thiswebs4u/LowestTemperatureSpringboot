package com.infosys.service;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

import org.springframework.stereotype.Service;

@Service
public class LowestTemperatureService {	
    public double getLowestTemperature(String fileName, int targetDayOfWeek) {
        List<Long> empList = new ArrayList<Long>();
        int currentDayOfWeek = 0;
        double lowestTemp = 200.0;
        XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
        try {
            XMLEventReader xmlEventReader = xmlInputFactory.createXMLEventReader(new FileInputStream(fileName));
            
            while(xmlEventReader.hasNext()){
                XMLEvent xmlEvent = xmlEventReader.nextEvent();
               if (xmlEvent.isStartElement()){
                   StartElement startElement = xmlEvent.asStartElement();
                   //System.out.println("element is "+startElement.getName().getLocalPart());
                   if(startElement.getName().getLocalPart().equals("forecast")){
                       //Get the 'id' attribute from Employee element
                       Attribute idAttr = startElement.getAttributeByName(new QName("dayOfWeek"));
                       if(idAttr != null){
                    	   currentDayOfWeek = Integer.parseInt(idAttr.getValue());
                    	   
                    	   //if(currentDayOfWeek == targetDayOfWeek)
                    		//   System.out.println("value="+idAttr.getValue());
                       }
                   }
                   /**
                    * Get element temperature and compare to lowest variable.
                    */
                   if(startElement.getName().getLocalPart().equals("temperature")
                		   &&(currentDayOfWeek == targetDayOfWeek)){
                       xmlEvent = xmlEventReader.nextEvent();
                       double thisTemp = Double.parseDouble(xmlEvent.asCharacters().getData());
                       lowestTemp = Double.compare(thisTemp,lowestTemp)<0?thisTemp:lowestTemp;
                   }
               }
               if(xmlEvent.isEndElement()){
                   EndElement endElement = xmlEvent.asEndElement();
                   if(endElement.getName().getLocalPart().equals("forecast")){
                       currentDayOfWeek = 0;
                   }
               }
            }
            
        } catch (FileNotFoundException | XMLStreamException e) {
            e.printStackTrace();
        }
        return lowestTemp;
    }

}
