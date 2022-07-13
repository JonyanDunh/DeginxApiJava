package deginx.http.routes;

import deginx.MainVerticle;
import deginx.http.response.Response;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.auth.AuthProvider;
import io.vertx.ext.auth.User;
import io.vertx.ext.web.Router;
import java.util.Map;

public class test {


    public Router create(Vertx vertx) {
        Router router = Router.router(vertx);

        router.post("/").handler(request -> request.request().body(body-> {

            System.out.println(request.session().data());

            Response.message(request, 200, request.user());

        }));

        return router;
    }
}
