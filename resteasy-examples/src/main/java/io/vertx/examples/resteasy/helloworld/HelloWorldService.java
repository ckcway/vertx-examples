package io.vertx.examples.resteasy.helloworld;

import io.vertx.ext.web.RoutingContext;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

@Path("/hello")
public class HelloWorldService {

  @GET
  @Path("/{name:.*}")
  public Response doGet(@PathParam("name") String name, @Context RoutingContext routingContext) {
    return Response.status(200).entity("Hello " + routingContext.request().absoluteURI()).build();
  }

  @GET
  @Path("/hhhh")
  public Response doGet(@Context RoutingContext routingContext) {
    return Response.status(200).entity(routingContext.request().absoluteURI()).build();
  }
}
