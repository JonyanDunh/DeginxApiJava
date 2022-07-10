package deginx.utility.user;

import com.google.gson.Gson;
import deginx.model.user.UserModel;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;

import static deginx.MainVerticle.mongoClient;

public class UserData {
    public static void getUser(String username, Handler<JsonObject> handler) {
        mongoClient.find("deginx_users", new JsonObject()
            .put("username", username), result -> {
            if (result.succeeded()) {
                //查询成功
                if (!result.result().isEmpty()) {
                    //有此用户
                    assert false;
                    handler.handle(result.result().get(0));
                } else {
                    //无此用户
                    assert false;
                    handler.handle(null);
                }
            } else {
                //查询失败
                assert false;
                handler.handle(null);
                result.cause().printStackTrace();
            }
        });

    }

    public static void creatUser(String username, UserModel user, Handler<JsonObject> handler) {


        mongoClient.save("deginx_users", new JsonObject(new Gson().toJson(user)), result -> {
            if (result.succeeded()) {
                UserData.getUser(username, saved_user -> {
                    assert false;
                    handler.handle(saved_user);
                });
            } else {
                handler.handle(null);
            }
        });
    }


}
