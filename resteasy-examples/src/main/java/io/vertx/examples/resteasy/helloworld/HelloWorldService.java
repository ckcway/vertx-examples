package io.vertx.examples.resteasy.helloworld;

import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

@Path("/")
public class HelloWorldService {

  @GET
  @Path("/a")
  public Response a(@Context RoutingContext routingContext) {
    return Response.status(200).entity("Hello " + routingContext.request().absoluteURI()).build();
  }

  @GET
  @Path("/b")
  public Response b(@Context RoutingContext routingContext) {
    return Response.status(200).entity(routingContext.request().absoluteURI()).build();
  }

  /**
   * 直接返回一个对象，框架自动转成json字符串
   * @param routingContext
   * @return
   */
  @GET
  @Path("/c.json")
  @Produces("application/json")
  public DemoPojo c(@Context RoutingContext routingContext) {
    return new DemoPojo().setName("老谢").setAge("18");
  }
}
