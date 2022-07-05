package deginx.routes.account;

import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;

public class AccountRoutes {
    public Router create(Vertx vertx) {
        Router userRouter = Router.router(vertx);

        userRouter.mountSubRouter("/login",new Login().create((vertx)));
        userRouter.get("/register").handler(Register::register);

        userRouter.route().consumes("application/json").produces("application/json");

        return userRouter;
    }
}
