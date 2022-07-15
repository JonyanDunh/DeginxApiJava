package deginx.http.routes.account;

import deginx.http.response.Response;
import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;

import java.util.Map;

public class LoginOut {
    public Router create(Vertx vertx) {
        Router router = Router.router(vertx);
        router.route("/").handler(ctx -> {
            ctx.clearUser();
            Response.message(ctx,200,"Log out successfully");
        });
        return router;
    }
}
