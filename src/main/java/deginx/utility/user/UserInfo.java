package deginx.utility.user;

import com.google.gson.Gson;
import deginx.model.user.UserModel;
import deginx.utility.auth.UserAuth;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.auth.User;

import javax.validation.constraints.Null;

import static deginx.MainVerticle.mongoClient;

public class UserInfo {
    public static void getUser(String username, Handler<AsyncResult<JsonObject>> handler) {
        mongoClient.find("deginx_users", new JsonObject()
            .put("username", username), result -> {
            if (result.succeeded()) {
                //查询成功
                if (!result.result().isEmpty()) {
                    //有此用户
                    assert false;
                    handler.handle(Future.succeededFuture(result.result().get(0).put("password", null).put("_id",null)));
                } else if(result.result().isEmpty()){
                    System.out.println(result.result());
                    //无此用户
                    assert false;
                    handler.handle(Future.failedFuture("The user does not exist"));
                }
            } else {
                //查询失败
                assert false;
                handler.handle(Future.failedFuture("Database query failed"));
                result.cause().printStackTrace();
            }
        });

    }
    public static void creatUser(String username, UserModel user, Handler<AsyncResult<JsonObject>> handler) {


        mongoClient.save("deginx_users", new JsonObject(new Gson().toJson(user)), result -> {
            if (result.succeeded()) {
                getUser(username, saved_user -> {
                    assert false;
                    handler.handle(Future.succeededFuture(saved_user.result().put("password", null).put("_id", null)));
                });
            } else {
                handler.handle((Future.failedFuture("Database write failed")));
            }
        });
    }

    public static void updateUser(User user, JsonObject update, Handler<AsyncResult<JsonObject>> handler) {
        String username=user.principal().getString("username");
        JsonObject query = new JsonObject()
            .put("username", username);
        mongoClient.updateCollection("deginx_users", query,update, result -> {
            if (result.succeeded()) {
                getUser(username, saved_user -> {
                    assert false;
                    handler.handle(Future.succeededFuture(saved_user.result().put("password", null).put("_id", null)));
                });
            } else {
                handler.handle((Future.failedFuture("Database write failed")));
            }
        });
    }
}
