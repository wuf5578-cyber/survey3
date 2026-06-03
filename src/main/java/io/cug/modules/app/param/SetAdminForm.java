
package io.cug.modules.app.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "设置管理员请求")
public class SetAdminForm {

    @ApiModelProperty(value = "用户id")
    private Integer uid;

    @ApiModelProperty(value = "topicId")
    private Integer topicId;

}
