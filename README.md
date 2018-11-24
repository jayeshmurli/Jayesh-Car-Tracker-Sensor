# Jayesh Iyer - IoT Lab Vehicle Tracker Sensor Application
IoT Lab Vehicle Sensor Application to process vehicle readings and generate alerts for abormal values

## Coding Style
Insert Vehicle Information
- Vehicle informations are inserted or updated using below REST API with method PUT.
  http://localhost:8080/CarTracker/rest/vehicle

Insert Vehicle Readins
- Vehicle Readings are inserted using below REST API with method PUT.
  http://localhost:8080/CarTracker/rest/reading

Get Vehicle Information
- Vehicle informations are retreived using below REST API with method GET.
  http://localhost:8080/CarTracker/rest/vehicle

Get All Vehicle HIGH Priority Alerts
- Vehicle Alerts that have Priority HIGH are retreived using below REST API with method GET.
  http://localhost:8080/CarTracker/rest/alert

Get Vehicle Specific Alerts
- All alerts for a specific vehicle are retreived using below REST API with method GET.
  http://localhost:8080/CarTracker/rest/alert/vehicle/{vin}

  

## Built With 
- [Eclipse](https://www.eclipse.org/ide/)
- [MongoDB](https://www.mongodb.com/what-is-mongodb)
- [Apache Tomcat] (https://tomcat.apache.org/download-80.cgi)

## Versioning 
This whole project was maintained using [Github](https://github.com/) a Versioning tool.

## Prerequisite
 * Install [Java 1.7] (https://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html) or latest.
 * Install MongoDB and Apache Tomcat Server.
 
## Steps to Run
 * Download or Clone Repository to local directory `git clone URL`.
 * Open Command Prompt and navigate to local directory `cd <localdirectory-path>`.
 * Open the project using Eclipse IDE.
 * Install maven dependencies on Eclipse IDE. 
    Right Click on Project >> Run As >> Maven Build
 * Start MongoDB server and import given database.
    `mongorestore -d Sensor <localdirectory-path>/Sensor/ -u username -p password`
 * Start the Apache server
 * Use provided REST APIs to test the application.

## Author
- Jayesh Iyer

## Acknowledgments
- [Mongo Documentation](https://docs.mongodb.com/v3.6/)
