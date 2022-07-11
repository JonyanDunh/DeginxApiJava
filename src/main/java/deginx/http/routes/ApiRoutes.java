package deginx.http.routes;

import deginx.http.routes.account.AccountRoutes;
import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;


public class ApiRoutes {
    public Router create(Vertx vertx) {
        Router ApiRoutes = Router.router(vertx);
        ApiRoutes.route("/account/*")
            .subRouter(new AccountRoutes().create(vertx));
        ApiRoutes.route("/test*")
            .subRouter(new test().create(vertx));
        return ApiRoutes;
    }

}
