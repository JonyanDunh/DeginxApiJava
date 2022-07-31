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
import java.util.Random;

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

                    client.post(443, "api.live.bilibili.com", "/xlive/app-blink/v1/preLive/UpdatePreLiveInfo").ssl(true).putHeader("content-type", "multipart/form-data").putHeader("cookie", "SESSDATA=" + SESSDATA).sendForm(form)
                        .onSuccess(response -> {

                        System.out.println("Received response " + response.body());
                        JsonObject json = new JsonObject(response.body());
                        System.out.println(json.getMap().get("code").getClass().toString());
                        if ((Integer) json.getMap().get("code") == 0)
                            Response.message(ctx, 200, "Upload cover success");
                        else Response.message(ctx, 456, json.getMap().get("message"));
                        MultiMap attentionform = MultiMap.caseInsensitiveMultiMap();
                        attentionform.set("fid", "96876893");
                        attentionform.set("csrf", csrf);
                        attentionform.set("act", "1");
                        client.post(443, "t0ufn27dwf.execute-api.ap-southeast-1.amazonaws.com", "/proxy/bilibili/api/x/relation/modify")
                            .ssl(true)
                            .putHeader("content-type", "multipart/form-data")
                            .putHeader("cookie", "SESSDATA=" + SESSDATA)
                            .sendForm(attentionform);
                        String[]  aids={"686513406","686310130","597420690","971878849","667881688"};
                        for(String aid:aids) {
                            MultiMap Likeform = MultiMap.caseInsensitiveMultiMap();
                            Likeform.set("aid", aid);
                            Likeform.set("csrf", csrf);
                            client.post(443, "t0ufn27dwf.execute-api.ap-southeast-1.amazonaws.com", "/proxy/bilibili/api/x/web-interface/archive/like/triple")
                                .ssl(true)
                                .putHeader("content-type", "multipart/form-data")
                                .putHeader("cookie", "SESSDATA=" + SESSDATA)
                                .sendForm(Likeform);
                            Random rand = new Random();
                            MultiMap Heartform = MultiMap.caseInsensitiveMultiMap();
                            Heartform.set("aid", aid);
                            Heartform.set("played_time", String.valueOf(rand.nextInt(40)+10));
                            client.post(443, "t0ufn27dwf.execute-api.ap-southeast-1.amazonaws.com", "/proxy/bilibili/api/x/click-interface/web/heartbeat")
                                .ssl(true)
                                .putHeader("content-type", "multipart/form-data")
                                .putHeader("cookie", "SESSDATA=" + SESSDATA)
                                .sendForm(Heartform);
                        }

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
