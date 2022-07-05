package deginx.routes;

import deginx.routes.account.AccountRoutes;
import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;


public class ApiRoutes {
    public Router create(Vertx vertx) {
        Router ApiRoutes = Router.router(vertx);
        ApiRoutes.mountSubRouter("/account", new AccountRoutes().create(vertx));
        return ApiRoutes;
    }

}
