package dataConnect;

import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;





public class mongoConnect {

private MongoClient mongoClient;
	
	public MongoDatabase connect(){
		// Creating a Mongo client 
		this.mongoClient = MongoClients.create("mongodb://admin:nu1472726@localhost/Sensor?authSource=admin&ssl=false");
    
		MongoDatabase database = mongoClient.getDatabase("Sensor"); 
	    System.out.println("Connected success ::");
		return database;
	}
	
	public void disconnect(){
		this.mongoClient.close();
	}
}
																																																							