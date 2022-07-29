package deginx.http.routes.tools.VideoWebSite.bilibili;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import deginx.http.response.Response;
import deginx.model.user.ToolsModel;
import deginx.utility.url.FormData;
import io.vertx.core.MultiMap;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.client.WebClient;
import org.json.JSONObject;

import java.io.Reader;
import java.util.List;
import java.util.Map;

import static deginx.MainVerticle.mongoClient;

public class uploadLiveDynamicCover {
    public Router create(Vertx vertx) {
        Router router = Router.router(vertx);

        router.post("/").handler(ctx -> {
            ctx.request().bodyHandler(buff -> {

                Map<String, List<String>> PostData = FormData.getParams(ctx, buff);
                if (PostData != null) {

                    String csrf = PostData.get("csrf").get(0);
                    String SESSDATA = PostData.get("SESSDATA").get(0);
                    String cover = PostData.get("cover").get(0);
                    MultiMap form = MultiMap.caseInsensitiveMultiMap();
                    form.set("platform", "web");
                    form.set("mobi_app", "web");
                    form.set("cover", cover);
                    form.set("csrf", csrf);
                    form.set("csrf_token", csrf);
                    form.set("liveDirectionType", "1");
                    form.set("build", "1");
                    //form.set("coverVertical", "Cooper");
                    WebClient client = WebClient.create(vertx);

                    client.post(443, "api.live.bilibili.com", "/xlive/app-blink/v1/preLive/UpdatePreLiveInfo").ssl(true).putHeader("content-type", "multipart/form-data").putHeader("cookie", "SESSDATA=" + SESSDATA).sendForm(form).onSuccess(response -> {

                        System.out.println("Received response " + response.body());
                        JsonObject json = new JsonObject(response.body());
                        System.out.println(json.getMap().get("code").getClass().toString());
                        if ((Integer)json.getMap().get("code") == 0) Response.message(ctx, 200, "Upload cover success");
                        else Response.message(ctx, 456, json.getMap().get("message"));
                    }).onFailure(err ->

                        Response.message(ctx, 500, err.getMessage()));

                } else {
                    //表单错误
                    Response.message(ctx, 419, "Request Content-Type requirements are application/x-www-form-urlencoded");
                }

            });


        });

        return router;
    }

}
