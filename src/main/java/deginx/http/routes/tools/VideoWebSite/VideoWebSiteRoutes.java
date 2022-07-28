package deginx.http.routes.tools.VideoWebSite;

import deginx.http.routes.tools.Add;
import deginx.http.routes.tools.Get;
import deginx.http.routes.tools.ToolsRoutes;
import deginx.http.routes.tools.VideoWebSite.bilibili.bilibiliRoutes;
import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;

public class VideoWebSiteRoutes {
    public Router create(Vertx vertx) {

        Router userRouter = Router.router(vertx);


        userRouter.route("/bilibili*")
            .subRouter(new bilibiliRoutes().create((vertx)));
        return userRouter;
    }
}
