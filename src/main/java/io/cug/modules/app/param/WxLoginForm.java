
package io.cug.modules.app.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 微信登录表单
 *
 * code: loginCode,
 */
@Data
@ApiModel(value = "微信登录表单")
public class WxLoginForm {

    @ApiModelProperty(value = "code",required = true)
    @NotBlank(message="code不能为空")
    private String code;

    @ApiModelProperty(value = "用户名",required = true)
    private String username;

    @ApiModelProperty(value = "头像",required = true)
    private String avatar;

}
