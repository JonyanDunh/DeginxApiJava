package deginx;



import deginx.http.routes.ApiRoutes;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.MongoClient;
import io.vertx.ext.web.Router;

@SuppressWarnings("SpellCheckingInspection")
public class MainVerticle extends AbstractVerticle {

    static final Vertx vertx = Vertx.vertx();
    static JsonObject mongoconfig = new JsonObject()
        .put("connection_string",
            "mongodb://deginx-mongodb:ZlRhwajBflUGcufeICIQg7bdeGNxg5WnrMmbSx83NLRbRupCcBVjYrg0gauxT5Ion9HlACeDchvrfKQkVSlMDg==@deginx-mongodb.mongo.cosmos.azure.com:10255/?ssl=true&replicaSet=globaldb&retrywrites=false&maxIdleTimeMS=120000&appName=@deginx-mongodb@")
        .put("db_name", "DeginxApi");
    public static MongoClient mongoClient = MongoClient.createShared(vertx, mongoconfig);

    public static void main(String[] args) {
        // 全局异常处理
        Thread.setDefaultUncaughtExceptionHandler((t, e) -> e.printStackTrace());
        // 启动 Vertx
        new MainVerticle().start();
    }


    @Override
    public void start() {
        Router router = Router.router(vertx);
        router.route("/api/*")
            .subRouter(new ApiRoutes().create(vertx));

        vertx.createHttpServer()
            .requestHandler(router)
            .listen(8000)
            .onSuccess(server -> System.out.printf("Server started on port %d%n", server.actualPort()));
    }
}
