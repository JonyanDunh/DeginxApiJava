package deginx.http.routes.platform;

import deginx.MainVerticle;
import deginx.http.handler.UserAuthHandler;
import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.ChainAuthHandler;

public class PlatformRoutes {


    public Router create(Vertx vertx) {



        ChainAuthHandler chain = ChainAuthHandler.any().add(MainVerticle.basicAuthHandler);
        Router userRouter = Router.router(vertx);

        //userRouter.route().handler(new UserAuthHandler());

        userRouter.route("/*")
            .subRouter(new index().create((vertx)));
        return userRouter;
    }
}

