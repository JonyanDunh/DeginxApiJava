package deginx.model.user;

import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.auth.AuthProvider;
import io.vertx.ext.auth.User;
import io.vertx.ext.auth.authorization.Authorization;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;


@Data
@AllArgsConstructor
public  class UserModel<T> {
    String uuid = UUID.randomUUID().toString();
    String username;
    String password;
    String qq;
    String phone;
    String wechat;
    String avatar = "https://i0.hdslb.com/bfs/face/aca2fd55a19fd6ae29d4b27115c500be15681feb.jpg@240w_240h_1c_1s.webp";
    String token;
    String email;

    public UserModel() {

    }
}
