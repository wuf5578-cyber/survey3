package io.cug.modules.gom.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@ApiModel(value = "监测项周期列表")
public class PeriodTypeInfo {

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "label")
    private String label;

    @ApiModelProperty(value = "子节点")
    private List<PeriodTypeInfo> children;

}