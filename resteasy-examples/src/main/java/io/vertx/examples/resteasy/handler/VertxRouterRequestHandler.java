package io.vertx.examples.resteasy.handler;

import io.netty.buffer.ByteBufInputStream;
import io.vertx.core.Context;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.RoutingContext;
import org.jboss.resteasy.core.SynchronousDispatcher;
import org.jboss.resteasy.plugins.server.embedded.SecurityDomain;
import org.jboss.resteasy.plugins.server.vertx.VertxHttpRequest;
import org.jboss.resteasy.plugins.server.vertx.VertxHttpResponse;
import org.jboss.resteasy.plugins.server.vertx.VertxUtil;
import org.jboss.resteasy.plugins.server.vertx.i18n.LogMessages;
import org.jboss.resteasy.plugins.server.vertx.i18n.Messages;
import org.jboss.resteasy.specimpl.ResteasyHttpHeaders;
import org.jboss.resteasy.spi.Failure;
import org.jboss.resteasy.spi.ResteasyDeployment;
import org.jboss.resteasy.spi.ResteasyUriInfo;

import java.io.IOException;

/**
 * Created by numbcode@gmail.com on 2018/2/7.
 */
public class VertxRouterRequestHandler implements Handler<RoutingContext> {
  private final Vertx vertx;
  protected final RouterRequestDispatcher dispatcher;
  private final String servletMappingPrefix;

  public VertxRouterRequestHandler(Vertx vertx, ResteasyDeployment deployment, String servletMappingPrefix, SecurityDomain domain) {
    this.vertx = vertx;
    this.dispatcher = new RouterRequestDispatcher((SynchronousDispatcher) deployment.getDispatcher(), deployment.getProviderFactory(), domain);
    this.servletMappingPrefix = servletMappingPrefix;
  }

  public void handle(RoutingContext routingContext) {
    HttpServerRequest request = routingContext.request();
    request.bodyHandler(buff -> {
      Context ctx = vertx.getOrCreateContext();
      ResteasyUriInfo uriInfo = VertxUtil.extractUriInfo(request, servletMappingPrefix);
      ResteasyHttpHeaders headers = VertxUtil.extractHttpHeaders(request);
      HttpServerResponse response = request.response();
      VertxHttpResponse vertxResponse = new VertxHttpResponse(response, dispatcher.getProviderFactory());
      VertxHttpRequest vertxRequest = new VertxHttpRequest(ctx, headers, uriInfo, request.rawMethod(), dispatcher.getDispatcher(), vertxResponse, false);
      if (buff.length() > 0) {
        ByteBufInputStream in = new ByteBufInputStream(buff.getByteBuf());
        vertxRequest.setInputStream(in);
      }

      try {
        dispatcher.service(ctx, request, response, vertxRequest,routingContext, vertxResponse, true);
      } catch (Failure e1) {
        vertxResponse.setStatus(e1.getErrorCode());
      } catch (Exception ex) {
        vertxResponse.setStatus(500);
        LogMessages.LOGGER.error(Messages.MESSAGES.unexpected(), ex);
      }

      if (!vertxRequest.getAsyncContext().isSuspended()) {
        try {
          vertxResponse.finish();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    });
  }
}
