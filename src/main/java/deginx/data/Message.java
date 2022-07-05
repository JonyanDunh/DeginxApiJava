package deginx.data;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Message<T> {
    T data;
    int code;
    String message;
}
