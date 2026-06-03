
package io.cug.modules.app.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * app用户信息修改
 *
 */
@Data
@ApiModel(value = "app用户信息修改")
public class AppUserUpdateForm {

    @ApiModelProperty(value = "头像")
    private String avatar;

    @ApiModelProperty(value = "性别")
    private Integer gender;

}
