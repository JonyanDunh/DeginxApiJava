package deginx.http.routes.tools.VideoWebSite.bilibili;

import deginx.http.routes.tools.Add;
import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;

public class bilibiliRoutes {
    public Router create(Vertx vertx) {

        Router userRouter = Router.router(vertx);


        userRouter.route("/uploadDynamicCover*")
            .subRouter(new uploadDynamicCover().create((vertx)));
        return userRouter;
    }
}
