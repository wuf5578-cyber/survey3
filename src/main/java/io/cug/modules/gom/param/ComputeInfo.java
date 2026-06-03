package io.cug.modules.gom.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@ApiModel(value = "计算组计算info")
public class ComputeInfo {

    @ApiModelProperty(value = "项目id")
    private Long projectId;

    @ApiModelProperty(value = "周期id")
    private Long periodId;

    @ApiModelProperty(value = "计算组id")
    private Long computeGroupId;

}