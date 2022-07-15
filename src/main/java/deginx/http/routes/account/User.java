package deginx.http.routes.account;

import deginx.http.response.Response;
import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;

public class User {

    public Router create(Vertx vertx) {
        Router router = Router.router(vertx);

        router.get("/").handler(ctx-> {

            Response.message(ctx,200   ,ctx.user().principal().getMap());


        });

        return router;
    }
}
