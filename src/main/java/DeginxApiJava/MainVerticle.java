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
  public enum ResponseCode {
    SUCCESS("Success", 200),
    SERVER_ERROR("Server Error", 500),
    NOT_FOUND("Not Found", 404),
    ERROR("Error", 400),
    FORBIDDEN("Forbidden", 403),
    FORM_DATA_NOT_ACCURATE("Verify that the form data is accurate", 419),
    USER_ALREADY_EXISTS("The user already exists", 452),
    CREATE_USER_ERROR("Failed to create user, please try again", 453),
    LOGIN_ERROR("The user does not exist or has an incorrect password", 454);
    // 成员变量
    private String message;
    private int code;
    // 构造方法
    private ResponseCode(String message, int code) {
      this.message = message;
      this.code = code;
    }
    // 普通方法
    public static String GetMessage(int index) {
      for (ResponseCode c : ResponseCode.values()) {
        if (c.getCode() == index) {
          return c.message;
        }
      }
      return null;
    }
    // get set 方法
    public int getCode() {
      return code;
    }
  }

  public static void JsonResponese(RoutingContext ctx, int code, JsonObject data) {
    String message=ResponseCode.GetMessage(400);
    JsonObject json = new JsonObject().put("code",code).put("message",message).put("data",data);
    ctx.response()
      .putHeader(HttpHeaders.CONTENT_TYPE, "application/json")
      .end(json.encode());

  }
  public static void StringResponese(RoutingContext ctx,int code, String data) {
    String message=ResponseCode.GetMessage(400);
    JsonObject json = new JsonObject().put("code",code).put("message",message).put("data",data);
    ctx.response()
      .putHeader(HttpHeaders.CONTENT_TYPE, "application/json")
      .end(json.encode());
  }
}
