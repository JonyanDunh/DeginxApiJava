package deginx.utility.auth;

import deginx.MainVerticle;
import deginx.http.response.Response;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.http.Cookie;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.auth.JWTOptions;
import io.vertx.ext.auth.User;
import io.vertx.ext.web.RoutingContext;

public class JWTAuth {
    public static String creatToken(JsonObject data){

    return MainVerticle.JWTprovider.generateToken(data,new JWTOptions().setExpiresInSeconds((int) MainVerticle.AuthExpireSeconds));

    }

    public static void setTokenCookie(RoutingContext ctx, String token){
        ctx.response().addCookie(Cookie.cookie("token", token).setPath("/").setMaxAge(MainVerticle.AuthExpireSeconds));
    }

    public static String getTokenFromCookie(RoutingContext ctx){
        return ctx.request().getCookie("token").getValue();
    }
    public static void authenticate(String token, Handler<AsyncResult<User>> resultHandler){

        MainVerticle.JWTprovider.authenticate(new JsonObject().put("token", token), resultHandler);
    }
}

