package DeginxApiJava.Routes.Account;

import DeginxApiJava.MainVerticle;
import io.vertx.core.http.HttpHeaders;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;

public class Login {
  public static void login(RoutingContext ctx) {

    MainVerticle.JsonResponese(ctx, new JsonObject().put("fuck","you"));
  }


}
