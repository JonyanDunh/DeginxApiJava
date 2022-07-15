package deginx;


import deginx.http.response.Response;
import deginx.http.routes.ApiRoutes;
import deginx.utility.auth.UserAuth;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Handler;
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
import io.vertx.ext.web.handler.*;
import io.vertx.ext.web.sstore.redis.RedisSessionStore;
import io.vertx.redis.client.Redis;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.stream.IntStream;


@SuppressWarnings("SpellCheckingInspection")
public class MainVerticle extends AbstractVerticle {

    static final Vertx vertx = Vertx.vertx();
    final static Logger logger = LoggerFactory.getLogger("loggerName");
    public static AuthenticationProvider provider = new UserAuth();
    public static final AuthenticationHandler basicAuthHandler = BasicAuthHandler.create(provider);
    public static long AuthExpireSeconds = 3600*24*30;
    public static JWTAuth JWTprovider = JWTAuth.create(vertx, new JWTAuthOptions()
        .addPubSecKey(new PubSecKeyOptions()
            .setAlgorithm("HS256")
            .setBuffer("keyboard cat")));
    public static MongoClient mongoClient = MongoClient.createShared(vertx, new JsonObject()
        //.put("connection_string", "mongodb://deginx-mongodb:ZlRhwajBflUGcufeICIQg7bdeGNxg5WnrMmbSx83NLRbRupCcBVjYrg0gauxT5Ion9HlACeDchvrfKQkVSlMDg==@deginx-mongodb.mongo.cosmos.azure.com:10255/?ssl=true&replicaSet=globaldb&retrywrites=false&maxIdleTimeMS=120000&appName=@deginx-mongodb@")
        .put("connection_string", "mongodb://localhost:27017")
        .put("db_name", "DeginxApi"));
    final Handler<RoutingContext> loggingHandler = routingContext -> {

        logger.info("Logging this message!");
        routingContext.next();
        // here you access properties of routingContext.request() and log what you want
    };
    Redis redis = Redis.createClient(vertx, "redis://127.0.0.1:6379");
    RedisSessionStore redisSessionStore = RedisSessionStore.create(vertx, redis);

    public static void main(String[] args) {
        // 全局异常处理
        //Thread.setDefaultUncaughtExceptionHandler((t, e) -> e.printStackTrace());
        // 启动 Vertx
        new MainVerticle().start();
    }

    @Override
    public void start() {
        redis.connect().onSuccess(conn -> System.out.println("redis success connect")).onFailure(conn -> {
            System.out.println("redis failure connect");
        });

        Router router = Router.router(vertx);
        //router.route().handler(CorsHandler.create("*"));

        router.route().failureHandler(ctx -> Response.message(ctx, ctx.statusCode(), ctx.failure().getMessage()));
        router.route().handler(StaticHandler.create("resources/"));
        router.route().handler(SessionHandler.create(redisSessionStore));
        router.route("/api/*").subRouter(new ApiRoutes().create(vertx));
        router.route().handler(loggingHandler);
        vertx.createHttpServer()
            .requestHandler(router)
            .exceptionHandler(exec->{

                System.out.println(exec.getMessage());
            })
            .listen(8000, "0.0.0.0")
            .onSuccess(server -> System.out.printf("Server started on port %d%n", server.actualPort()));
    }
}
