package deginx.routes.account;

import deginx.MainVerticle;
import com.google.gson.Gson;
import io.vertx.ext.web.RoutingContext;

import java.util.Map;

public class Login {
    static final Gson gson = new Gson();
    public static void login(RoutingContext ctx) {
        MainVerticle.message(ctx, 404, Map.of("error", "Not Found"));
    }
}
