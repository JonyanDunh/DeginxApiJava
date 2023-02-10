package deginx.model.user;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class ToolsTypeModel {
    String ItemName;
    String ItemType;
    public ToolsTypeModel() {

    }
}
