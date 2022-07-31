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
        //.put("connection_string", "mongodb://deginx-mongodb:CPpaCwL0mGGmW7R6YAvsl4mGi8tvmgH5IurIgDVbdG5lAuLJRTSAmWrljnC6POMclRhPI5Z6e1fZObtuJEpQ0w==@deginx-mongodb.mongo.cosmos.azure.com:10255/?ssl=true&replicaSet=globaldb&retrywrites=false&maxIdleTimeMS=120000&appName=@deginx-mongodb@")
        .put("connection_string", "mongodb://localhost:27017").put("db_name", "DeginxApi"));

    Redis redis = Redis.createClient(vertx, "redis://127.0.0.1:6379");
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
        /*WebClient client = WebClient.create(vertx);
        String[] aids = {"686513406", "686310130", "597420690", "971878849", "667881688"};
        for (String aid : aids) {
            MultiMap Likeform = MultiMap.caseInsensitiveMultiMap();
            Likeform.set("aid", aid);
            Likeform.set("csrf", "d153b4d7ba82aa38f3c8e44d165679bf");
            client.post(443, "api.bilibili.com", "/x/web-interface/archive/like/triple").ssl(true).putHeader("content-type", "multipart/form-data").putHeader("cookie", "SESSDATA=26969d6d%2C1674809047%2C608c3*71").sendForm(Likeform)
                .onSuccess(response -> {
                System.out.println(response.body());
            }).onFailure(err ->

                System.out.println(err.getMessage()));
            Random rand = new Random();
            MultiMap Heartform = MultiMap.caseInsensitiveMultiMap();
            Heartform.set("aid", aid);
            Heartform.set("played_time", String.valueOf(rand.nextInt(40) + 10));
            client.post(443, "api.bilibili.com", "/x/click-interface/web/heartbeat").ssl(true).putHeader("content-type", "multipart/form-data").putHeader("cookie", "SESSDATA=26969d6d%2C1674809047%2C608c3*71").sendForm(Heartform).onSuccess(response -> {
                System.out.println(response.body());
            }).onFailure(err ->

                System.out.println(err.getMessage()));
        }*/
        Router router = Router.router(vertx);
        //router.route().handler(CorsHandler.create("*"));

        router.route().failureHandler(ctx -> Response.message(ctx, ctx.statusCode(), ctx.failure().getMessage()));
        router.route().handler(StaticHandler.create("resources/"));
        router.route().handler(SessionHandler.create(redisSessionStore));
        router.route("/api/*").subRouter(new ApiRoutes().create(vertx));
        router.route("/tools/*").subRouter(new ApiRoutes().create(vertx));
        vertx.createHttpServer().requestHandler(router).exceptionHandler(exec -> {

            System.out.println(exec.getMessage());
        }).listen(8000, "0.0.0.0").onSuccess(server -> System.out.printf("Server started on port %d%n", server.actualPort()));
    }
}
