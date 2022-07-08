package deginx.utility.url;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class UrlParameters {
    public static Map<String, String> get(String url){
        Map<String, String> params = new HashMap<String, String>();

        if (url == null) {
            return params;
        }

        String query;
        String[] urlParts = url.split("\\?");
        if (urlParts.length > 1) {
            query = urlParts[1];
        } else if (url.contains("=")) {
            query = url;
        } else {
            query = "";
        }

        for (String param : query.split("&")) {
            String[] pair = param.split("=");
            String key = URLDecoder.decode(pair[0], StandardCharsets.UTF_8);
            String value;
            if (pair.length > 1) {
                value = URLDecoder.decode(pair[1], StandardCharsets.UTF_8);
            } else {
                value = null;
            }
            params.put(key,value);
        }

        return params;
    }
}
