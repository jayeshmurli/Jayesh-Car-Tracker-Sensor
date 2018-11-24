package rest.service;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
//import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
//import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

@Path("rest")
//Class create web service at path rest/reading
public class ServiceHandler {
	
	@GET
	@Path("/getData")
	@Produces(MediaType.TEXT_PLAIN)
    public Response sayHello() {
        String output = "Hello, World from Jayesh!";
        return Response.status(200).entity(output).build();
    }
}
