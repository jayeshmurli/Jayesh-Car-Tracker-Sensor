package Business;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoDatabase;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.json.JSONArray;
import org.json.JSONObject;

public class VehicleOperations {

	
	
	public void insertVehicle(String inputs){
	    
		mongoConnect dbCon = new mongoConnect();
		
	    // Accessing the database 
		MongoDatabase database = dbCon.connect(); 
	    System.out.println("Connected success ::"); 
	    
	    //Accessing the desired collection
	    MongoCollection<Document> collection = database.getCollection("vehicles");
	    
	    JSONArray vehicleArray = new JSONArray(inputs);
		
		for(int i=0; i<vehicleArray.length();i++){
			JSONObject jobj = vehicleArray.getJSONObject(i);
		       
		    Document vehicleDoc = new Document("vin", jobj.get("vin"))
		    				.append("make", jobj.get("make"))
		    				.append("model", jobj.get("model"))
		    				.append("year", jobj.get("year"))
		    				.append("redlineRpm", jobj.get("redlineRpm"))
		    				.append("maxFuelVolume", jobj.get("maxFuelVolume"))
		    				.append("lastServiceDate", jobj.get("lastServiceDate"));		        
		        
		    long count = collection.count(new Document("vin", jobj.get("vin")));
			if(count<=0){
				collection.insertOne(vehicleDoc);
			}
			else{
				Bson filter = new Document("vin", jobj.get("vin"));
				Bson newValue = vehicleDoc;
				Bson updateOperationDocument = new Document("$set", newValue);
				collection.updateOne(filter, updateOperationDocument);			    	
			}				
		}
		dbCon.disconnect();
	}
	
	
	public String getVehicleDocument(){
		mongoConnect dbCon = new mongoConnect();
		
	    // Accessing the database 
	    MongoDatabase database = dbCon.connect();
	    System.out.println("Connected success ::");
	    
	    MongoCollection<Document> collection = database.getCollection("vehicles");

	    FindIterable<Document> vehicleData = collection.find();
	    String output = "[";
	    for(Document doc : vehicleData){
	    	if(output == "[")
	    		output = output + doc.toJson();
	    	else
	    		output = output + "," + doc.toJson();	    	
	    }
	    return output + "]";
	}

}
