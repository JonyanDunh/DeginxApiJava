package deginx.http.handler;

import deginx.http.response.Response;
import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;

public class UserAuthHandler implements Handler<RoutingContext> {
    public void handle(RoutingContext ctx) {
        if(ctx.user()==null){
            Response.message(ctx,401,"You are not logged in, please log in");
        }else if(ctx.user().expired()){
            Response.message(ctx,401,"Your login credentials have expired, please log in again");
        }else if(!ctx.user().principal().isEmpty()){
            ctx.next();
        }
    }

}
