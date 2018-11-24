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

import Business.VehicleOperations;

@Path("rest")
public class VehicleService {

	@PUT
	@Path("/vehicle")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response vehicleREST(InputStream incomingData) {
		StringBuilder inputString = new StringBuilder();
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(incomingData));
			String line = null;
			while ((line = in.readLine()) != null) {
				inputString.append(line);
			}
		} catch (Exception e) {
			System.out.println("Error Parsing: - ");
		}
		System.out.println("Data Received: " + inputString.toString());
		
		VehicleOperations vehicleOp = new VehicleOperations();
		vehicleOp.insertVehicle(inputString.toString());
		
		// return HTTP response 200 in case of success
		return Response.status(200).entity(inputString.toString()).build();
	}
	
	@GET
	@Path("/vehicle")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getVehicle() {
		
		VehicleOperations vehicleOp = new VehicleOperations();		
		String output = vehicleOp.getVehicleDocument();
		
		// return HTTP response 200 in case of success
		return Response.ok(output, MediaType.APPLICATION_JSON).build();	
	}
	
}
