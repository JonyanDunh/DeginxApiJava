package deginx;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestStreamHandler;
import com.google.gson.Gson;
import deginx.http.response.Response;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpHeaders;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.ext.web.Router;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.time.LocalDateTime;

public class AWSRequestStreamHandler implements RequestStreamHandler{


    @Override
    public void handleRequest(InputStream inputStream, OutputStream outputStream, Context context) throws IOException {

        Vertx vertx = Vertx.vertx();
        Router router;
        router = Router.router(vertx);

        router.route().handler(rc -> {
            LocalDateTime now = LocalDateTime.now();
            rc.response().putHeader("content-type", "text/html").end("Hello from Lambda at " + now);
        });

    }

    }



