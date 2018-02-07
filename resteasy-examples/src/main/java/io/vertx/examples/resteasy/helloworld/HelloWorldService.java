package io.vertx.examples.resteasy.helloworld;

import io.vertx.ext.web.RoutingContext;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

@Path("/")
public class HelloWorldService {

  @GET
  @Path("/{name:.*}")
  public Response doGet(@PathParam("name") String name, @Context RoutingContext routingContext) {
    if (name == null || name.isEmpty()) {
      name = "World";
    }
    System.err.println(routingContext.request().absoluteURI());
    return Response.status(200).entity("Hello " + name).build();
  }
}
