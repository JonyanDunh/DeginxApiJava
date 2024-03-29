package deginx;


import deginx.http.response.Response;
import deginx.http.routes.ApiRoutes;
import deginx.utility.auth.UserAuth;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Handler;
import io.vertx.core.MultiMap;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.SLF4JLogDelegateFactory;
import io.vertx.ext.auth.PubSecKeyOptions;
import io.vertx.ext.auth.authentication.AuthenticationProvider;
import io.vertx.ext.auth.jwt.JWTAuth;
import io.vertx.ext.auth.jwt.JWTAuthOptions;
import io.vertx.ext.mongo.MongoClient;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.client.WebClient;
import io.vertx.ext.web.handler.*;
import io.vertx.ext.web.sstore.redis.RedisSessionStore;
import io.vertx.redis.client.Redis;

import java.io.File;
import java.util.Random;
import java.util.stream.IntStream;


@SuppressWarnings("SpellCheckingInspection")
public class MainVerticle extends AbstractVerticle {

    static final Vertx vertx = Vertx.vertx();

    public static AuthenticationProvider provider = new UserAuth();
    public static final AuthenticationHandler basicAuthHandler = BasicAuthHandler.create(provider);
    public static long AuthExpireSeconds = 3600 * 24 * 30;
    public static JWTAuth JWTprovider = JWTAuth.create(vertx, new JWTAuthOptions().addPubSecKey(new PubSecKeyOptions().setAlgorithm("HS256").setBuffer("keyboard cat")));
    public static MongoClient mongoClient = MongoClient.createShared(vertx, new JsonObject()
        .put("connection_string",
            "mongodb://Deginx:C8nJpFZNsMxiYPLG@127.0.0.1:27017/Deginx"
        )
        .put("db_name", "Deginx"));

    Redis redis = Redis.createClient(vertx, "redis://:deginxredis@127.0.0.1:6379");
    RedisSessionStore redisSessionStore = RedisSessionStore.create(vertx, redis);

    public static void main(String[] args) {
        // 全局异常处理
        Thread.setDefaultUncaughtExceptionHandler((t, e) -> e.printStackTrace());
        // 启动 Vertx
        new MainVerticle().start();

    }

    @Override
    public void start() {
        redis.connect().onSuccess(conn -> System.out.println("redis success connect")).onFailure(conn -> {
            System.out.println("redis failure connect");
        });
        Router router = Router.router(vertx);

        router.route().failureHandler(ctx -> Response.message(ctx, ctx.statusCode(), ctx.failure().getMessage()));
        router.route().handler(StaticHandler.create("resources/"));
        router.route().handler(SessionHandler.create(redisSessionStore));
        router.route("/api/*").subRouter(new ApiRoutes().create(vertx));
        router.route("/tools/*").subRouter(new ApiRoutes().create(vertx));
        vertx.createHttpServer().requestHandler(router).exceptionHandler(exec -> {

            System.out.println(exec.getMessage());
        }).listen(4399, "0.0.0.0").onSuccess(server -> System.out.printf("Server started on port %d%n", server.actualPort()));
    }
}
