package deginx.http.routes.account;

import com.google.gson.Gson;
import deginx.http.response.Response;
import deginx.model.user.UserModel;
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

public class Register{
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
                final Gson gson = new Gson();

                UserModel<Object> user = new UserModel<>();
                user.setUsername(PostData.get("username").get(0));
                user.setPassword(BCrypt.hashpw(PostData.get("password").get(0), BCrypt.gensalt()));
                user.setQq("1309634881");

                JsonObject document = new JsonObject(gson.toJson(user));

                mongoClient.save("deginx_users", document, res -> {
                    if (res.succeeded()) {
                        String id = res.result();
                        System.out.println("Saved book with id " + id);
                    } else {
                        res.cause().printStackTrace();
                    }
                });

                Response.message(ctx, 200, PostData);
            } else {
                //表单错误
                Response.message(ctx, 419, "Request Content-Type requirements are application/x-www-form-urlencoded");
            }

        });

    }



}
