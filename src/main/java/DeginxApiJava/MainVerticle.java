package DeginxApiJava;
import DeginxApiJava.Routes.ApiRoutes;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpHeaders;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;

import java.util.HashMap;

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
  public static String GetMessage(int code){
    HashMap<Integer, String> ResponseCode = new HashMap<Integer, String>();
    ResponseCode.put(200,"Success");
    ResponseCode.put(500,"Server Error");
    ResponseCode.put(404,"Not Found");
    ResponseCode.put(400,"Error");
    ResponseCode.put(403,"Forbidden");
    ResponseCode.put(419,"Verify that the form data is accurate");
    ResponseCode.put(452,"The user already exists");
    ResponseCode.put(453,"Failed to create user, please try again");
    ResponseCode.put(454,"The user does not exist or has an incorrect password");
    return ResponseCode.get(code);


  };
  public static void JsonResponese(RoutingContext ctx, int code, JsonObject data) {
    String message=GetMessage(code);
    JsonObject json = new JsonObject().put("code",code).put("message",message).put("data",data);
    ctx.response()
      .putHeader(HttpHeaders.CONTENT_TYPE, "application/json")
      .end(json.encode());

  }
  public static void StringResponese(RoutingContext ctx,int code, String data) {
    String message=GetMessage(code);
    JsonObject json = new JsonObject().put("code",code).put("message",message).put("data",data);
    ctx.response()
      .putHeader(HttpHeaders.CONTENT_TYPE, "application/json")
      .end(json.encode());
  }
}
