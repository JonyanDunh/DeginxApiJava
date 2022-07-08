package deginx.data;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Message<T> {

    int code;
    String message;
    T data;
}
