package DeginxApiJava;
import DeginxApiJava.Routes.ApiRoutes;
import io.vertx.core.AbstractVerticle;
import io.vertx.ext.web.Router;

public class MainVerticle extends AbstractVerticle {
  @Override
  public void start() throws Exception {


    Router router = Router.router(vertx);

    router.mountSubRouter("/api", new ApiRoutes().create(vertx));
    





    vertx.createHttpServer()
      .requestHandler(router)
      .listen(8000)
      .onSuccess(server ->
        System.out.println(
          "HTTP server started on port " + server.actualPort()
        )
      );
  }
}
