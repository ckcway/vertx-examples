package io.vertx.examples.resteasy.handler;

import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.ext.web.RoutingContext;
import org.jboss.resteasy.plugins.server.embedded.SecurityDomain;
import org.jboss.resteasy.spi.ResteasyDeployment;

/**
 * Created by numbcode@gmail.com on 2018/2/8.
 */
public interface RestEasyHandler extends Handler<RoutingContext> {
  static RestEasyHandler create(Vertx vertx, ResteasyDeployment deployment) {
    return new RestEasyHandlerImpl(vertx, deployment, "", null);
  }

  static RestEasyHandler create(Vertx vertx, ResteasyDeployment deployment, String servletMappingPrefix, SecurityDomain domain) {
    return new RestEasyHandlerImpl(vertx, deployment, servletMappingPrefix, domain);
  }

}
