package deginx.http.routes.platform;
import deginx.MainVerticle;
import deginx.http.handler.UserAuthHandler;
import deginx.utility.auth.UserAuth;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.ext.auth.ChainAuth;
import io.vertx.ext.auth.authentication.AuthenticationProvider;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.AuthenticationHandler;
import io.vertx.ext.web.handler.BasicAuthHandler;
import io.vertx.ext.web.handler.ChainAuthHandler;
import io.vertx.ext.web.handler.JWTAuthHandler;

public class PlatformRoutes {




    public Router create(Vertx vertx) {



        ChainAuthHandler chain = ChainAuthHandler.any().add(MainVerticle.basicAuthHandler);
        Router userRouter = Router.router(vertx);

        userRouter.route().handler(new UserAuthHandler());

        userRouter.route("/*")
            .subRouter(new index().create((vertx)));
        return userRouter;
    }
}

