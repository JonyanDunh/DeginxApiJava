package deginx.routes.account;

import deginx.MainVerticle;
import com.google.gson.Gson;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;

import java.util.Map;

public class Login {
    public Router create(Vertx vertx) {
        Router router = Router.router(vertx);

        router.get("/").handler(this::login);
        router.post("/").handler(routingContext -> {
            String username = routingContext.request().getParam("username");
            String password = routingContext.request().getParam("password");

           System.out.printf(String.valueOf(routingContext.request().formAttributes().get("username")));
            routingContext.response().putHeader("Content-Type", "text/html").end("姓名：" + username + "，密码：" + password);
        });









        return router;
    }
    static final Gson gson = new Gson();
    public void login(RoutingContext ctx) {

        MainVerticle.message(ctx, 404, Map.of("errors", "Not Found"));
    }
}
