package deginx.http.routes.account;

import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;

public class AccountRoutes {
    public Router create(Vertx vertx) {
        Router userRouter = Router.router(vertx);

        userRouter.mountSubRouter("/login",new Login().create((vertx)));
        userRouter.mountSubRouter("/register",new Register().create((vertx)));

        userRouter.route().consumes("application/json").produces("application/json");

        return userRouter;
    }
}
