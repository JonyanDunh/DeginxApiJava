package deginx.model.user;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserModel<T> {
    String username;
    String password;
    String qq;
    String phone;
    String wechat;
    String avatar;
    String user_uuid;
    String token;

    public UserModel() {

    }
}
