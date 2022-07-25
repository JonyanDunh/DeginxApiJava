package deginx.model.user;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class ToolsModel {
    String ItemUUID = UUID.randomUUID().toString();
    String ItemName;
    String ItemImg;
    String ItemDescribe;
    String ItemType;
    boolean disabled=true;
    public ToolsModel() {

    }
}
