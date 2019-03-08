#LowestTemperatureSpringboot

##To Run: 
Change to project directory mvn spring-boot:run
##User Story: 
As an User running an application I want to get the lowest temperature tomorrow given a zip code for USA. So that So I can prepare for tomorrow's weather
##Acceptance Criteria: 
Enter zip code for United State System returns lowest temperate in farenhight for tomorrow 
If error occurs systems returns "Weather Service Error" or "Zip code not in right format"
Considerations With Design:
•    Which parser to use when getting weather service payload back from external service
•    Could have used an all in memory parser, file size was around 200k.
•    Because I didn't know how much memory the target machine had I chose with doing and event driven xml parser javax.xml.stream StAX parser.
•    For the client I decided to use Springboots commandline parser then just call back to the webservice controller.


