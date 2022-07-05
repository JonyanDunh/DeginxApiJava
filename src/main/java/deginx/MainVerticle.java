package deginx;

import deginx.routes.ApiRoutes;
import deginx.data.Message;
import com.google.gson.Gson;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpHeaders;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;

import java.util.HashMap;

@SuppressWarnings("SpellCheckingInspection")
public class MainVerticle extends AbstractVerticle {
    static final Gson gson = new Gson();
    static final Vertx vertx = Vertx.vertx();
    static final HashMap<Integer, String> ResponseCode = new HashMap<>() {{
        put(200, "Success");
        put(500, "Server Error");
        put(404, "Not Found");
        put(400, "Error");
        put(403, "Forbidden");
        put(419, "Verify that the form data is accurate");
        put(452, "The user already exists");
        put(453, "Failed to create user, please try again");
        put(454, "The user does not exist or has an incorrect password");
    }};

    public static String message(int code) {
        return ResponseCode.getOrDefault(code, "");
    }

    public static <T> void message(RoutingContext ctx, int code, T data) {
        ctx.response()
            .putHeader(HttpHeaders.CONTENT_TYPE, "application/json")
            .end(gson.toJson(new Message<>(data, code, message(code))));
    }

    ;

    public static void message(RoutingContext ctx, int code, String data) {
        ctx.response()
            .putHeader(HttpHeaders.CONTENT_TYPE, "application/json")
            .end(gson.toJson(new Message<>(data, code, message(code))));
    }

    public static void main(String[] args) throws Exception {
        // 全局异常处理
        Thread.setDefaultUncaughtExceptionHandler((t, e) -> e.printStackTrace());

        // 启动 Vertx
        new MainVerticle().start();
    }

    @Override
    public void start() throws Exception {
        Router router = Router.router(vertx);

        router.mountSubRouter("/api", new ApiRoutes().create(vertx)).produces("application/json");

        vertx.createHttpServer()
            .requestHandler(router)
            .listen(8000)
            .onSuccess(server -> System.out.printf("Server started on port %d%n", server.actualPort()));
    }
}
