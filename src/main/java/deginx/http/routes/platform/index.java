package deginx.http.routes.platform;

import deginx.MainVerticle;
import deginx.http.response.Response;
import deginx.utility.auth.JWTAuth;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.auth.User;
import io.vertx.ext.web.Router;

import java.util.Map;

public class index {
    public Router create(Vertx vertx) {
        Router router = Router.router(vertx);

        router.post("/").handler(ctx-> {

            /*String token=JWTAuth.getTokenFromCookie(ctx);
            JWTAuth.authenticate(token,res->{
                if (res.succeeded()) {

                    Response.message(ctx, 200, res.result().principal().getMap());
                } else if (res.failed()) {

                    Response.message(ctx, 455, res.cause().getMessage());

                }
            });*/
            Response.message(ctx,500,"fuck");


        });

        return router;
    }
}
