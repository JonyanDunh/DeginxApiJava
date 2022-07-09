package deginx.http.response;

import com.google.gson.Gson;
import io.vertx.core.http.HttpHeaders;
import io.vertx.ext.web.RoutingContext;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.HashMap;

public class Response {
    static final Gson gson = new Gson();

    @Data
    @AllArgsConstructor
    public static class Message<T> {

        int code;
        String message;
        T data;
    }

    static final HashMap<Integer, String> ResponseCode = new HashMap<>() {{
        put(200, "Success");
        put(500, "Server Error");
        put(404, "Not Found");
        put(400, "Error");
        put(403, "Forbidden");
        put(419, "Verify that the form data is accurate");
        put(452, "The user already exists");
        put(453, "Failed to create user, please try again");
        put(454, "The user does not exist or has an incorrect password");
    }};
    public static String message(int code) {
        return ResponseCode.getOrDefault(code, "");
    }

    public static <T> void message(RoutingContext ctx, int code, T data) {
        ctx.response()
            .putHeader(HttpHeaders.CONTENT_TYPE, "application/json")
            .end(gson.toJson(new Message<>(code, message(code),data)));

    }

    public static void message(RoutingContext ctx, int code, String data) {
        ctx.response()
            .putHeader(HttpHeaders.CONTENT_TYPE, "application/json")
            .end(gson.toJson(new Message<>(code, message(code),data)));
    }

}
