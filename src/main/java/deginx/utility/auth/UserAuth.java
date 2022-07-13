package deginx.utility.auth;

import com.google.gson.Gson;
import deginx.MainVerticle;
import deginx.model.user.UserModel;
import deginx.utility.user.UserInfo;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.Promise;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.auth.User;
import io.vertx.ext.auth.authentication.AuthenticationProvider;
import io.vertx.ext.auth.authentication.CredentialValidationException;
import io.vertx.ext.auth.authentication.Credentials;
import org.mindrot.jbcrypt.BCrypt;

import java.util.Date;

import static deginx.MainVerticle.main;
import static deginx.MainVerticle.mongoClient;

public class UserAuth implements AuthenticationProvider {

    public Future<User> authenticate(JsonObject credentials) {
        System.out.println(1);
        Promise<User> promise = Promise.promise();
        this.authenticate(credentials, promise);
        return promise.future();
    }


    public void authenticate(Credentials credentials, Handler<AsyncResult<User>> resultHandler) {
        System.out.println(2);
        try {
            credentials.checkValid(null);
            this.authenticate(credentials.toJson(), resultHandler);
        } catch (CredentialValidationException var4) {
            resultHandler.handle(Future.failedFuture(var4));
        }

    }


    public Future<User> authenticate(Credentials credentials) {
        System.out.println(3);
        Promise<User> promise = Promise.promise();
        this.authenticate(credentials, promise);
        return promise.future();
    }

    @Override
    public void authenticate(JsonObject authInfo, Handler<AsyncResult<User>> resultHandler) {
        String username = authInfo.getString("username");
        String password = authInfo.getString("password");

        mongoClient.find("deginx_users", new JsonObject()

            .put("username", username), result -> {
            if (result.succeeded()) {
                //查询成功
                if (!result.result().isEmpty()) {
                    //有此用户
                    if (BCrypt.checkpw(password, result.result().get(0).getValue("password").toString())) {
                        //密码正确

                        long CurrentSeconds=System.currentTimeMillis()/1000;
                        long ExpireTime=CurrentSeconds+ MainVerticle.AuthExpireSeconds;
                        User user = User.create(result.result().get(0).put("password", null).put("_id", null),new JsonObject().put("exp",ExpireTime));

                        assert false;
                        resultHandler.handle(Future.succeededFuture(user));
                    } else {
                        //密码错误
                        assert false;
                        resultHandler.handle(Future.failedFuture("The user does not exist or has an incorrect password"));
                    }
                } else {
                    //无此用户
                    assert false;
                    resultHandler.handle(Future.failedFuture("The user does not exist or has an incorrect password"));
                }
            } else {
                //查询失败
                assert false;
                resultHandler.handle(Future.failedFuture("The user does not exist or has an incorrect password"));
                result.cause().printStackTrace();
            }
        });
    }





}
