package io.cug.modules.gom.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@ApiModel(value = "温湿度info")
public class SensorInfo {

    @ApiModelProperty(value = "温度")
    private double humidity;

    @ApiModelProperty(value = "湿度")
    private double temperature;

    @ApiModelProperty(value = "时间")
    private Date surveyTime;

}