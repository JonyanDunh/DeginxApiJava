package DeginxApiJava.Routes.Account;

import DeginxApiJava.MainVerticle;
import com.google.gson.Gson;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;

import java.util.Map;

public class Login {
    static final Gson gson = new Gson();
    public static void login(RoutingContext ctx) {
        MainVerticle.message(ctx, 404, Map.of("error", "Not Found"));
    }
}
