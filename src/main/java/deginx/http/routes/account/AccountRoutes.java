package deginx.http.routes.account;

import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;

public class AccountRoutes {
    public Router create(Vertx vertx) {
        Router userRouter = Router.router(vertx);


        userRouter.route("/login*")
                .subRouter(new Login().create((vertx)));
        userRouter.route("/register*")
            .subRouter(new Register().create((vertx)));

        return userRouter;
    }
}
