package deginx.http.routes.account;

import deginx.http.response.Response;
import deginx.model.user.UserModel;
import deginx.utility.auth.UserAuth;
import deginx.utility.url.FormData;
import deginx.utility.user.UserInfo;
import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import org.mindrot.jbcrypt.BCrypt;

import java.util.List;
import java.util.Map;
import java.util.Objects;

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
                UserInfo.getUser(username, res -> {
                    if (res.succeeded()) {
                        Response.message(ctx, 452, "User '"+username+"' already exists");
                    } else if(Objects.equals(res.cause().getMessage(), "The user does not exist")) {
                        //无此用户
                        UserModel<Object> user = new UserModel<>();
                        user.setUsername(username);
                        user.setPassword(BCrypt.hashpw(PostData.get("password").get(0), BCrypt.gensalt()));
                        user.setEmail(PostData.get("email").get(0));

                        UserInfo.creatUser(username, user, creat_user_res -> {
                            if (creat_user_res.succeeded()) {
                                Response.message(ctx, 200, creat_user_res.result().getMap());
                            } else if(creat_user_res.failed()){
                                Response.message(ctx, 453, creat_user_res.cause().getMessage());
                            }

                        });
                    }else if(Objects.equals(res.cause().getMessage(), "Database query failed")){
                        Response.message(ctx, 500, res.cause().getMessage());

                    }
                });
            } else {
                //表单错误
                Response.message(ctx, 419, "Request Content-Type requirements are application/x-www-form-urlencoded");
            }

        });

    }


}
