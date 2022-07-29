package deginx.http.routes.tools.VideoWebSite.bilibili;

import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;

public class bilibiliRoutes {
    public Router create(Vertx vertx) {

        Router userRouter = Router.router(vertx);


        userRouter.route("/uploadLiveDynamicCover*")
            .subRouter(new uploadLiveDynamicCover().create((vertx)));
        return userRouter;
    }
}
