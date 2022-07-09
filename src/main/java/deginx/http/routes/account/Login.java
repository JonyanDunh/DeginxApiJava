package deginx.http.routes.account;

import deginx.http.response.Response;
import deginx.utility.url.FormData;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import org.mindrot.jbcrypt.BCrypt;

import java.util.List;
import java.util.Map;

import static deginx.MainVerticle.mongoClient;

public class Login {

    public Router create(Vertx vertx) {
        Router router = Router.router(vertx);

        router.get("/").handler(request -> Response.message(request, 404, Map.of("errors", "Not Found")));
        router.post("/").handler(this::login);

        return router;
    }

    public void login(RoutingContext ctx) {
        ctx.request().bodyHandler(buff -> {
            Map<String, List<String>> PostData = FormData.getParams(ctx, buff);
            if(PostData!=null) {
                JsonObject query = new JsonObject()
                    .put("username", PostData.get("username").get(0));
                mongoClient.find("deginx_users", query, res -> {
                    if (res.succeeded()) {
                        //查询成功
                        if (!res.result().isEmpty()) {
                            //有此用户
                            for (JsonObject json : res.result()) {
                                //遍历结果
                                if (BCrypt.checkpw(PostData.get("password").get(0), json.getValue("password").toString())) {
                                    //密码正确
                                    Response.message(ctx, 200, json.put("password", null).getMap());
                                } else {
                                    //密码错误
                                    Response.message(ctx, 454, "");
                                }
                            }
                        } else {
                            //无此用户
                            Response.message(ctx, 454, "");
                        }
                    } else {
                        //查询失败
                        Response.message(ctx, 500, "");
                        res.cause().printStackTrace();

                    }
                });
            }else {
                //表单错误
                Response.message(ctx, 419, "Request Content-Type requirements are application/x-www-form-urlencoded");
            }

        });

    }
}
