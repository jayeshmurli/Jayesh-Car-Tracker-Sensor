package rest.service;


import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.JSONArray;
import org.json.JSONObject;

import dataConnect.mongoConnect;;

@Path("rest")
public class ReadingService {

	@POST
	@Path("/reading")
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
		mongoConnect dbCon = new mongoConnect();
		dbCon.insertReading(crunchifyBuilder.toString());

		// return HTTP respons e 200 in case of success
		return Response.status(200).entity(crunchifyBuilder.toString()).build();
	}
	
}
