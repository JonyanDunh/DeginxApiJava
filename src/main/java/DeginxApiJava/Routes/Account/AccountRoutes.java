package DeginxApiJava.Routes.Account;

import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;

public class AccountRoutes {
    public Router create(Vertx vertx) {
        Router userRouter = Router.router(vertx);

        userRouter.get("/login").handler(Login::login);
        userRouter.get("/register").handler(Register::register);

        userRouter.route().consumes("application/json").produces("application/json");

        return userRouter;
    }
}
