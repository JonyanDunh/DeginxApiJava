package deginx.utility.url;

import io.netty.handler.codec.http.QueryStringDecoder;
import io.vertx.core.buffer.Buffer;
import io.vertx.ext.web.RoutingContext;

import java.util.List;
import java.util.Map;

public class FormData {

    public static Map<String, List<String>> getParams(RoutingContext ctx, Buffer buff) {
        Map<String, List<String>> params = null;
        String contentType = ctx.request().headers().get("Content-Type");
        if ("application/x-www-form-urlencoded".equals(contentType)) {
            QueryStringDecoder qsd = new QueryStringDecoder(buff.toString(), false);
            params = qsd.parameters();
        }
        return params;
    }


}
