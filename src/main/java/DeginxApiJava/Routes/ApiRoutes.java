package DeginxApiJava.Routes;

import DeginxApiJava.Routes.Account.AccountRoutes;
import DeginxApiJava.Routes.Account.Login;
import DeginxApiJava.Routes.Account.Register;
import io.vertx.core.Vertx;
import io.vertx.core.impl.logging.Logger;
import io.vertx.core.impl.logging.LoggerFactory;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;


public class ApiRoutes {
  public Router create(Vertx vertx) {
    Router ApiRoutes = Router.router(vertx);
    ApiRoutes.mountSubRouter("/account",new AccountRoutes().create(vertx));
    return ApiRoutes;
  }

}
