package deginx.http.routes.tools;

import deginx.MainVerticle;

import deginx.http.routes.tools.VideoWebSite.VideoWebSiteRoutes;
import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.ChainAuthHandler;

public class ToolsRoutes {
    public Router create(Vertx vertx) {

        Router userRouter = Router.router(vertx);


        userRouter.route("/add*")
            .subRouter(new Add().create((vertx)));
        userRouter.route("/add_item_type*")
            .subRouter(new AddItemType().create((vertx)));
        userRouter.route("/get*")
            .subRouter(new Get().create((vertx)));
        userRouter.route("/get_item_type*")
            .subRouter(new GetItemType().create((vertx)));
//        userRouter.route("/VideoWebSite*")
//            .subRouter(new VideoWebSiteRoutes().create(vertx));
        return userRouter;
    }
}
