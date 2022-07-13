package deginx.http.routes.account;

import deginx.MainVerticle;
import deginx.http.response.Response;
import deginx.utility.auth.JWTAuth;
import deginx.utility.url.FormData;
import deginx.utility.user.UserInfo;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.auth.User;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;

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


                JsonObject authInfo = new JsonObject().put("username", PostData.get("username").get(0)).put("password", PostData.get("password").get(0));

                MainVerticle.provider.authenticate(authInfo, result -> {
                    if (result.succeeded()) {
                        //有此用户
                        User user = result.result();
                        ctx.setUser(user);
                        Response.message(ctx, 200, user.principal().getMap());
                    } else {
                        //无此用户
                        Response.message(ctx, 454, "User '"+PostData.get("username").get(0)+"' does not exist or has the wrong password");
                    }
                });


            } else {
                //表单错误
                Response.message(ctx, 419, "Request Content-Type requirements are application/x-www-form-urlencoded");
            }

        });

    }


}
