package DeginxApiJava.Routes.Account;

import io.vertx.ext.web.RoutingContext;

public class Login {
  public static void login(RoutingContext ctx) {
    out(ctx, "login");
  }

  private static void out(RoutingContext ctx, String msg) {
    ctx.response().putHeader("Content-Type", "text/plain; charset=utf-8")
      .end(msg);
  }
}
