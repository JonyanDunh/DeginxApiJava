package deginx.http.routes.tools;

import com.google.gson.Gson;
import deginx.http.response.Response;
import deginx.model.user.ToolsModel;
import deginx.utility.url.FormData;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import static deginx.MainVerticle.mongoClient;

public class Get {
    public Router create(Vertx vertx) {
        Router router = Router.router(vertx);

        router.get("/:ItemType").handler(ctx -> {
            String ItemType=ctx.pathParam("ItemType");
            JsonObject query= new JsonObject();
            if (!Objects.equals(ItemType, "all"))
            query.put("ItemType", ItemType);
            mongoClient.find("deginx_tools",query, res -> {

                if (res.succeeded()) {
                    Response.message(ctx, 200, res.result());
                } else {
                    res.cause().printStackTrace();
                    Response.message(ctx, 500, "Database read failed");
                }
            });

        });

        return router;
    }
}
