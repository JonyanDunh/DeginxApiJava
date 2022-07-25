package deginx.http.routes.tools;

import deginx.MainVerticle;

import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.ChainAuthHandler;

public class ToolsRoutes {
    public Router create(Vertx vertx) {

        Router userRouter = Router.router(vertx);


        userRouter.route("/add*")
            .subRouter(new Add().create((vertx)));
        userRouter.route("/get*")
            .subRouter(new Get().create((vertx)));
        return userRouter;
    }
}
