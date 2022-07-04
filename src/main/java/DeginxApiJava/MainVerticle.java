package DeginxApiJava;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.MultiMap;
import io.vertx.core.Promise;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;

public class MainVerticle extends AbstractVerticle {
  @Override
  public void start() throws Exception {
    Router router = Router.router(vertx);

    router.route("/login").handler(this::login);
    router.post("/user").handler(this::addUser);
    router.delete("/user").handler(this::deleteUser);
    router.get("/users").handler(this::getUserList);
    vertx.createHttpServer()
      .requestHandler(router)
      .listen(8888)
      .onSuccess(server ->
        System.out.println(
          "HTTP server started on port " + server.actualPort()
        )
      );
  }
  private void getUserList(RoutingContext ctx) {
    out(ctx, "getUserList");
  }

  private void deleteUser(RoutingContext ctx) {
    out(ctx, "deleteUser");
  }

  private void addUser(RoutingContext ctx) {
    out(ctx, "addUser");
  }

  private void login(RoutingContext ctx) {
    out(ctx, "login");
  }

  private void out(RoutingContext ctx, String msg) {
    ctx.response().putHeader("Content-Type", "text/plain; charset=utf-8")
      .end(msg);
  }
}
