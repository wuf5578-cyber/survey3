package io.cug.modules.gom.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@ApiModel(value = "周期风险等级")
public class PeriodAssessInfo {

    @ApiModelProperty(value = "周期id")
    private Long periodId;

    @ApiModelProperty(value = "周期名")
    private String periodName;

    @ApiModelProperty(value = "时间")
    private Date begin;

    @ApiModelProperty(value = "风险等级")
    private int degree;

}