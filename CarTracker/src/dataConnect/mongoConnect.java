package dataConnect;

import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoClient;
import com.mongodb.MongoClientSettings;
import com.mongodb.ConnectionString;
import com.mongodb.ServerAddress;
import com.mongodb.MongoCredential;
import com.mongodb.client.MongoDatabase;

import java.util.ArrayList;

import org.bson.Document;



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
	    
	    Document doc = new Document("vin", inputs.get(0))
	            .append("make", inputs.get(1))
	            .append("model", inputs.get(2))
	            .append("year", Integer.parseInt(inputs.get(3)))
	            .append("redlineRpm", Integer.parseInt(inputs.get(4)))
	            .append("maxFuelVolume", Integer.parseInt(inputs.get(5)))
	            .append("lastServiceDate", inputs.get(6));
	    
	    collection.insertOne(doc);
	    mongoClient.close();
	}
	
}
																																																							