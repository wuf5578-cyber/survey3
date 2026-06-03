package io.cug.modules.gom.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "风险评估")
public class RiskAssessInfo {

    @ApiModelProperty(value = "项目id")
    private Long projectId;

    @ApiModelProperty(value = "周期id")
    private Long periodId;

}