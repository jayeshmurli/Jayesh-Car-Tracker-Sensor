package rest.service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.JSONArray;
import org.json.JSONObject;

import dataConnect.ReadingOperations;
import dataConnect.mongoConnect;

@Path("rest")
public class AlertService {
	
	@GET
	@Path("/alert")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAlerts() {
		
		ReadingOperations readOp = new ReadingOperations();
		String output = readOp.getAlertDocument();
		
		// return HTTP respons e 200 in case of success
		return Response.ok(output, MediaType.APPLICATION_JSON).build();	
	}

	@GET
	@Path("/alert/vehicle/{vin}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getVehicleAlert(@PathParam("vin") String vin) {
		
		ReadingOperations readOp = new ReadingOperations();
		String output = readOp.getVehicleAlertDocument(vin);
		
		// return HTTP respons e 200 in case of success
		return Response.ok(output, MediaType.APPLICATION_JSON).build();	
	}
}
