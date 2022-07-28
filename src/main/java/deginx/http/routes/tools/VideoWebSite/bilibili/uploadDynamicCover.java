package deginx.http.routes.tools.VideoWebSite.bilibili;

import com.google.gson.Gson;
import deginx.http.response.Response;
import deginx.model.user.ToolsModel;
import deginx.utility.url.FormData;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;

import java.util.List;
import java.util.Map;

import static deginx.MainVerticle.mongoClient;

public class uploadDynamicCover {
    public Router create(Vertx vertx) {
        Router router = Router.router(vertx);

        router.post("/").handler(ctx -> {
            ctx.request().bodyHandler(buff -> {

                Map<String, List<String>> PostData = FormData.getParams(ctx, buff);
                if (PostData != null) {
                    ToolsModel tool = new ToolsModel();

                    tool.setItemName(PostData.get("name").get(0));
                    tool.setItemImg(PostData.get("image").get(0));
                    tool.setItemDescribe(PostData.get("describe").get(0));
                    tool.setItemType(PostData.get("Type").get(0));
                    JsonObject ToolJson = new JsonObject(new Gson().toJson(tool));
                    mongoClient.save("deginx_tools", ToolJson, result -> {
                        if (result.succeeded()) {
                            Response.message(ctx, 200, ToolJson.getMap());
                        } else {
                            Response.message(ctx, 500, "Database write failed");
                        }
                    });

                } else {
                    //表单错误
                    Response.message(ctx, 419, "Request Content-Type requirements are application/x-www-form-urlencoded");
                }

            });


        });

        return router;
    }
}
