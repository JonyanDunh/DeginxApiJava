package DeginxApiJava;
import DeginxApiJava.Routes.ApiRoutes;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpHeaders;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;

public class MainVerticle extends AbstractVerticle {
  @Override
  public void start() throws Exception {


    Router router = Router.router(vertx);

    router.mountSubRouter("/api", new ApiRoutes().create(vertx))

          .produces("application/json");


    vertx.createHttpServer()
      .requestHandler(router)
      .listen(8000)
      .onSuccess(server ->
        System.out.println(
          "HTTP server started on port " + server.actualPort()
        )
      );
  }
  public static void JsonResponese(RoutingContext ctx, JsonObject data) {
    JsonObject json = new JsonObject().put("data",data);
    ctx.response()
      .putHeader(HttpHeaders.CONTENT_TYPE, "application/json")
      .end(json.encode());

  }
  public static void StringResponese(RoutingContext ctx, String data) {
    JsonObject json = new JsonObject().put("data",data);
    ctx.response()
      .putHeader(HttpHeaders.CONTENT_TYPE, "application/json")
      .end(json.encode());
  }
}
