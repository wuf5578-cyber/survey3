
package io.cug.modules.app.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "项目ID")
public class ProjectIDForm {


    @ApiModelProperty(value = "projectId")
    private long projectId;

}
