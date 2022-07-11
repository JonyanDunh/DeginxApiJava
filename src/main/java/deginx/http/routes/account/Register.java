package deginx.http.routes.account;

import deginx.http.response.Response;
import deginx.model.user.UserModel;
import deginx.utility.url.FormData;
import deginx.utility.user.UserData;
import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import org.mindrot.jbcrypt.BCrypt;

import java.util.List;
import java.util.Map;

public class Register {
    public Router create(Vertx vertx) {
        Router router = Router.router(vertx);

        router.get("/").handler(request -> Response.message(request, 404, Map.of("errors", "Not Found")));

        router.post("/").handler(this::register);

        return router;
    }


    public void register(RoutingContext ctx) {
        ctx.request().bodyHandler(buff -> {
            Map<String, List<String>> PostData = FormData.getParams(ctx, buff);
            if (PostData != null) {
                String username = PostData.get("username").get(0);
                UserData.getUser(username, get_user_result -> {
                    if (get_user_result != null) {
                        Response.message(ctx, 452, "");
                    } else {
                        //无此用户
                        UserModel<Object> user = new UserModel<>();
                        user.setUsername(username);
                        user.setPassword(BCrypt.hashpw(PostData.get("password").get(0), BCrypt.gensalt()));
                        user.setEmail(PostData.get("email").get(0));

                        UserData.creatUser(username, user, created_user_result -> {
                            if (created_user_result != null) {
                                Response.message(ctx, 200, created_user_result.getMap());

                            } else {
                                Response.message(ctx, 453, "");
                            }

                        });

                    }
                });
            } else {
                //表单错误
                Response.message(ctx, 419, "Request Content-Type requirements are application/x-www-form-urlencoded");
            }

        });

    }


}
