package deginx.http.routes;

import deginx.http.response.Response;
import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;
import java.util.Map;

public class test {
    public Router create(Vertx vertx) {
        Router router = Router.router(vertx);

        router.post("/").handler(request -> request.request().body(body-> System.out.println(body.result())));

        return router;
    }
}
