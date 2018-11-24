package Business;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoDatabase;
import static com.mongodb.client.model.Filters.*;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.bson.Document;
import org.json.JSONObject;

public class ReadingOperations {

	public void insertReading(String input){

		JSONObject jobj = new JSONObject(input);
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
			    			    
		mongoConnect dbCon = new mongoConnect();
			
		// Accessing the database 
		MongoDatabase database = dbCon.connect();
		System.out.println("Connected success ::"); 
		    
		MongoCollection<Document> collection = database.getCollection("readings");
		//System.out.println("Collection count : " + collection.count());
		    
		collection.insertOne(readingDoc);		    
		dbCon.disconnect();
		
	}
		
	public void verifyReading(String input){
		int redlineRpm = 0;
	    Double maxFuelVolume = 0.0;
	    String alertPriority ;
	    String alertType ;
	    
		JSONObject jobj = new JSONObject(input);
	    JSONObject jobj2 = new JSONObject(jobj.get("tires").toString());
	    
	    String vin = (String)jobj.get("vin");	    
	    mongoConnect dbCon = new mongoConnect();
				
	    // Accessing the database 
	    MongoDatabase database = dbCon.connect();
	    System.out.println("Connected success ::");
	     	    
	    MongoCollection<Document> collection = database.getCollection("vehicles");
	    FindIterable<Document> vehicleData = collection.find(eq("vin", vin));
	    
	    for (Document doc : vehicleData) {
            System.out.println(doc.get("redlineRpm"));
            redlineRpm = (int)doc.get("redlineRpm");
            maxFuelVolume = Double.parseDouble(doc.get("maxFuelVolume").toString());
        }
	    
	    if(redlineRpm!=0 && isRpmAbnormal((int)jobj.get("engineRpm"),redlineRpm)){
	    	alertPriority = "HIGH";
	    	alertType = "Abnormal RPM Values";
	    	insertAlert(alertPriority,jobj);
	    }
	    if(maxFuelVolume!=0 && isFuelAbnormal(Double.parseDouble(jobj.get("fuelVolume").toString()),maxFuelVolume)){
	    	alertPriority = "MEDIUM";
	    	alertType = "Low Fuel Volume";
	    	insertAlert(alertPriority,jobj);
	    }
	    if(isTireAbnormal(jobj2.getInt("frontLeft")) || isTireAbnormal(jobj2.getInt("frontRight")) || isTireAbnormal(jobj2.getInt("rearLeft")) || isTireAbnormal(jobj2.getInt("rearRight"))){
	    	alertPriority = "LOW";
	    	alertType = "Tire Pressure Abnormal";
	    	insertAlert(alertPriority,jobj);
	    }	    	    
	    dbCon.disconnect();
	}
	
	public boolean isTireAbnormal(int pressure){
		if(pressure < 32 || pressure > 36)
			return true;
		else
			return false;
	}
	
	public boolean isRpmAbnormal(int rpm, int redRpm ){
		if(rpm>redRpm)
			return true;
		else
			return false;
	}
	
	public boolean isFuelAbnormal(double fuel, double maxFuel){
		if(fuel < (maxFuel * 0.1))
			return true;
		else
			return false;
	}
	
	public void insertAlert(String priority, JSONObject jobj){
		
	    Document alertDoc = new Document("vin", jobj.get("vin"))
	    		.append("timestamp", jobj.get("timestamp"));
		switch(priority){
		case "HIGH":
			alertDoc.append("alertPriority", "HIGH")
			.append("alertType", "RPM Values Abnormal")
			.append("abnormalReading", jobj.get("engineRpm"));
			break;
		case "MEDIUM":
			alertDoc.append("alertPriority", "MEDIUM")
			.append("alertType", "Fuel Volume Abnormal")
			.append("abnormalReading", jobj.get("fuelVolume"));
			break;
		case "LOW":
			JSONObject jobj2 = new JSONObject(jobj.get("tires").toString());
			alertDoc.append("alertPriority", "LOW")
			.append("alertType", "Tire Pressur Abnormal")
			.append("abnormalReading", new Document("frontLeft", jobj2.get("frontLeft"))
					.append("frontRight", jobj2.get("frontRight"))
					.append("rearLeft", jobj2.get("rearLeft"))
					.append("rearRight", jobj2.get("rearRight")));
			break;	
		default:
			System.out.println("No priority match");
			break;
		}
		
		mongoConnect dbCon = new mongoConnect();
		
	    // Accessing the database 
	    MongoDatabase database = dbCon.connect();
	    System.out.println("Connected success ::");
	    
	    //Accessing the collection
	    MongoCollection<Document> collection = database.getCollection("alerts");	    
		collection.insertOne(alertDoc);
	}
	
	public String getAlertDocument(){
		Date now = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(now);
		//Add extra 5 hours for GMT to EDT conversion
		cal.add(Calendar.HOUR, 3);
		Date newDate = cal.getTime();
	    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
	    Date alertDate = new Date();
	    		
		System.out.println("Threshold time is : " + newDate.toString());
		mongoConnect dbCon = new mongoConnect();
		
	    // Accessing the database
	    MongoDatabase database = dbCon.connect();
	    System.out.println("Connected success ::");
	    
	    MongoCollection<Document> collection = database.getCollection("alerts");
	    
	    FindIterable<Document> alertDoc = collection.find();
	    System.out.println("Total collection : " + collection.count() + " and filter count : " );
	    String output = "[";
	    for(Document doc : alertDoc){
	    	try{
	    		alertDate = simpleDateFormat.parse(doc.getString("timestamp"));
	    	}
	    	catch(Exception e){
	    		e.printStackTrace();
	    	}
	    	if(alertDate.after(newDate) && doc.getString("alertPriority").equalsIgnoreCase("HIGH"))
	    	{
	    		if(output == "[")
	    			output = output + doc.toJson();
	    		else
	    			output = output + "," + doc.toJson();
	    	}
	    }
	    return output + "]";
	}
	
	
	public String getVehicleAlertDocument(String vehicle){
		
		mongoConnect dbCon = new mongoConnect();
		
	    // Accessing the database\
	    MongoDatabase database = dbCon.connect();
	    System.out.println("Connected success ::");
	    
	    MongoCollection<Document> collection = database.getCollection("alerts");
	    
	    FindIterable<Document> alertDoc = collection.find(eq("vin", vehicle));
	    System.out.println("Total collection : " + collection.count() + " and filter count : " );
	    String output = "[";
	    for(Document doc : alertDoc){	    	
	    	if(output == "[")
	    		output = output + doc.toJson();
	    	else
	    		output = output + "," + doc.toJson();	    	
	    }
	    return output + "]";
	}
}
