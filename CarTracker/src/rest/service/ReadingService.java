package rest.service;


import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import Business.ReadingOperations;

//Class create web service at path rest/reading
@Path("rest")
public class ReadingService {

	@POST
	@Path("/reading")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response crunchifyREST(InputStream incomingData) {
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
		ReadingOperations readOp = new ReadingOperations();
		readOp.insertReading(inputString.toString());
		readOp.verifyReading(inputString.toString());

		// return HTTP response 200 in case of success
		return Response.status(200).entity(inputString.toString()).build();
	}
	
}
