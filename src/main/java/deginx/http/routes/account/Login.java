package deginx.http.routes.account;

import deginx.http.response.Response;
import deginx.utility.url.FormData;
import deginx.utility.user.UserData;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.CorsHandler;
import org.mindrot.jbcrypt.BCrypt;

import java.util.List;
import java.util.Map;

public class Login {

    public Router create(Vertx vertx) {
        Router router = Router.router(vertx);

        router.get("/").handler(request -> Response.message(request, 404, Map.of("errors", "Not Found")));
        router.post("/")
            .handler(this::login);

        return router;
    }

    public void login(RoutingContext ctx) {
        ctx.request().bodyHandler(buff -> {
            Map<String, List<String>> PostData = FormData.getParams(ctx, buff);
            if (PostData != null) {
                UserData.authenticateUser(PostData.get("username").get(0),PostData.get("password").get(0), result -> {
                    if (result != null) {
                        //有此用户
                        System.out.println(result.getMap());
                        Response.message(ctx, 200, result.getMap());
                    } else {
                        //无此用户
                        Response.message(ctx, 454, "");
                    }
                });
            } else {
                //表单错误
                Response.message(ctx, 419, "Request Content-Type requirements are application/x-www-form-urlencoded");
            }

        });

    }
}
