package deginx.http.routes.account;

import com.google.gson.Gson;
import deginx.MainVerticle;
import deginx.http.response.Response;
import deginx.utility.url.UrlParameters;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;

import java.util.Map;

import static deginx.MainVerticle.mongoClient;

public class Login {
    public Router create(Vertx vertx) {
        Router router = Router.router(vertx);

        router.get("/").handler(request -> {
            Response.message(request, 404, Map.of("errors", "Not Found"));
            });

        router.post("/").handler(this::login);

        return router;
    }

    public void login(RoutingContext request) {
        request.request().bodyHandler(body->{

            Map<String, String> PostData= UrlParameters.get(body.toString());
            JsonObject response =new JsonObject();

            response.put("username",PostData.get("username"));
            response.put("password",PostData.get("password"));
            JsonObject document = new JsonObject()
                .put("username", PostData.get("username"))
                .put("password", PostData.get("password"));
            mongoClient.save("deginx_users", document, res -> {
                if (res.succeeded()) {
                    String id = res.result();
                    System.out.println("Saved book with id " + id);
                } else {
                    res.cause().printStackTrace();
                }
            });

            Response.message(request, 200,PostData);
        });

    }


    static final Gson gson = new Gson();

}
