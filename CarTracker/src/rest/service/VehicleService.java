package rest.service;


import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.JSONArray;
import org.json.JSONObject;;

@Path("rest")
public class VehicleService {

	@PUT
	@Path("/vehicle")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response crunchifyREST(InputStream incomingData) {
		StringBuilder crunchifyBuilder = new StringBuilder();
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(incomingData));
			String line = null;
			while ((line = in.readLine()) != null) {
				crunchifyBuilder.append(line);
			}
		} catch (Exception e) {
			System.out.println("Error Parsing: - ");
		}
		System.out.println("Data Received: " + crunchifyBuilder.toString());
 
		try{
			JSONArray vehicleArray = new JSONArray(crunchifyBuilder.toString());
			
			for(int i=0; i<vehicleArray.length();i++){
		        JSONObject jobj = vehicleArray.getJSONObject(i);

				System.out.println(jobj.get("vin"));
				System.out.println(jobj.get("make"));
				System.out.println(jobj.get("model"));
			}
			
		}	
		catch(Exception e){
			e.printStackTrace();
		}
		// return HTTP response 200 in case of success
		return Response.status(200).entity(crunchifyBuilder.toString()).build();
	}
	
}
