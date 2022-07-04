package DeginxApiJava.Routes.Account;

import io.vertx.ext.web.RoutingContext;

public class Register {
  public static void register(RoutingContext ctx) {
    ctx.response().putHeader("Content-Type", "text/plain; charset=utf-8")
      .end("register");
  }

}
