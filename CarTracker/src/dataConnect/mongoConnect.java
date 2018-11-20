package dataConnect;

import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoClient;
import com.mongodb.MongoClientSettings;
import com.mongodb.BasicDBObject;
import com.mongodb.ConnectionString;
import com.mongodb.ServerAddress;
import com.mongodb.MongoCredential;
import com.mongodb.client.MongoDatabase;
import com.mongodb.*;

import java.util.ArrayList;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.json.JSONArray;
import org.json.JSONObject;



public class mongoConnect {
	
	
	public void connect(){
		// Creating a Mongo client 
		MongoClient mongoClient = MongoClients.create("mongodb://admin:nu1472726@localhost/Sensor?authSource=admin&ssl=false");
    
		// Accessing the database 
		MongoDatabase database = mongoClient.getDatabase("Sensor"); 
		System.out.println("Connected success ::"); 
    
		MongoCollection<Document> collection = database.getCollection("vehicleData");

		System.out.println("Collection count : " + collection.count());
    
		Document doc = new Document("vin", "123")
            .append("make", "toyota")
            .append("model", "innova");
    
		collection.insertOne(doc);
		mongoClient.close();
	}
	
	public void insertDoc(ArrayList<String> inputs){
		MongoClient mongoClient = MongoClients.create("mongodb://admin:nu1472726@localhost/Sensor?authSource=admin&ssl=false");
	    
	    // Accessing the database 
	    MongoDatabase database = mongoClient.getDatabase("Sensor"); 
	    System.out.println("Connected success ::"); 
	    
	    MongoCollection<Document> collection = database.getCollection("vehicleData");

	    System.out.println("Collection count : " + collection.count());
	    
	    long count = collection.count(new Document("vin", inputs.get(0)));
	    if(count<=0)
	    {
	    	System.out.println("In if");

	    	Document doc = new Document("vin", inputs.get(0))
	            .append("make", inputs.get(1))
	            .append("model", inputs.get(2))
	            .append("year", Integer.parseInt(inputs.get(3)))
	            .append("redlineRpm", Integer.parseInt(inputs.get(4)))
	            .append("maxFuelVolume", Double.parseDouble(inputs.get(5)))
	            .append("lastServiceDate", inputs.get(6));
	    
	    	collection.insertOne(doc);
	    }
	    else{
	    	System.out.println("In else");
	    	Bson filter = new Document("vin", inputs.get(0));
	    	Bson newValue = new Document("vin", inputs.get(0))
	    			.append("make", inputs.get(1))
		            .append("model", inputs.get(2))
		            .append("year", Integer.parseInt(inputs.get(3)))
		            .append("redlineRpm", Integer.parseInt(inputs.get(4)))
		            .append("maxFuelVolume", Double.parseDouble(inputs.get(5)))
		            .append("lastServiceDate", inputs.get(6));
	    	Bson updateOperationDocument = new Document("$set", newValue);
	    	collection.updateOne(filter, updateOperationDocument);
	    	
	    }
	    mongoClient.close();
	}
	
	public void insertReading(String input){
		try{
		      JSONObject jobj = new JSONObject(input);

			mongoConnect dbCon = new mongoConnect();

			
			JSONObject jobj2 = new JSONObject(jobj.get("tires").toString());
		        Document readingDoc = new Document("vin", jobj.get("vin"))
		        		.append("latitude", jobj.get("latitude"))
		        		.append("longitude", jobj.get("longitude"))
		        		.append("timestamp", jobj.get("timestamp"))
		        		.append("fuelVolume", jobj.get("fuelVolume"))
		        		.append("speed", jobj.get("speed"))
		        		.append("engineHp", jobj.get("engineHp"))
		        		.append("checkEngineLightOn", jobj.get("checkEngineLightOn"))
		        		.append("engineCoolantLow", jobj.get("engineCoolantLow"))
		        		.append("cruiseControlOn", jobj.get("cruiseControlOn"))
		        		.append("engineRpm", jobj.get("engineRpm"))
		        		.append("tires", 
		        				new Document("frontLeft",jobj2.get("frontLeft"))
		        				.append("frontRight", jobj2.get("frontRight"))
		        				.append("rearLeft", jobj2.get("rearLeft"))
		        				.append("rearRight", jobj2.get("rearRight")));
			    

			    
			addReadingData(readingDoc);					
			
		}	
		catch(Exception e){
			e.printStackTrace();
		}

	}
	
	public void addReadingData(Document readingDoc){
		MongoClient mongoClient = MongoClients.create("mongodb://admin:nu1472726@localhost/Sensor?authSource=admin&ssl=false");
	    
	    // Accessing the database 
	    MongoDatabase database = mongoClient.getDatabase("Sensor"); 
	    System.out.println("Connected success ::"); 
	    
	    MongoCollection<Document> collection = database.getCollection("readingData");

	    System.out.println("Collection count : " + collection.count());
	    
	    collection.insertOne(readingDoc);
	    
	    mongoClient.close();

	}
	
}
																																																							