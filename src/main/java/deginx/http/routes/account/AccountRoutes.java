package deginx.http.routes.account;

import deginx.http.handler.UserAuthHandler;
import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;

public class AccountRoutes {
    public Router create(Vertx vertx) {
        Router userRouter = Router.router(vertx);

        userRouter.route("/login/out*")
            .subRouter(new LoginOut().create((vertx)));
        userRouter.route("/login*")
            .subRouter(new Login().create((vertx)));
        userRouter.route("/register*")
            .subRouter(new Register().create((vertx)));

        userRouter.route("/user*")
            .handler(new UserAuthHandler())
            .subRouter(new User().create((vertx)));

        return userRouter;
    }
}
