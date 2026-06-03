
package io.cug.modules.app.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Data
@ApiModel(value = "SQL Where")
public class SqlWhereForm {

    @NotBlank
    @Length(max = 1000, message = "消息不能超过1000个字符")
    @ApiModelProperty(value = "where")
    private String where;

}
