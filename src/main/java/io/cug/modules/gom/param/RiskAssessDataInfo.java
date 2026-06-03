package io.cug.modules.gom.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "风险评估数据")
public class RiskAssessDataInfo {

    @ApiModelProperty(value = "节点id")
    private Long objectId;

    @ApiModelProperty(value = "节点名")
    private String objectName;

    @ApiModelProperty(value = "变化速率")
    private double speed;

    @ApiModelProperty(value = "累计变化")
    private double accumulative;

    @ApiModelProperty(value = "变化速率黄色预警")
    private double speedWarning1;

    @ApiModelProperty(value = "变化速率红色预警")
    private double speedWarning2;

    @ApiModelProperty(value = "累计变化黄色预警")
    private double accumulativeWarning1;

    @ApiModelProperty(value = "累计变化红色预警")
    private double accumulativeWarning2;

    @ApiModelProperty(value = "计算结果")
    private double result;

}