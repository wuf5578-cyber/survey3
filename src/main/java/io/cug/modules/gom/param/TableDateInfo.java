package io.cug.modules.gom.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@ApiModel(value = "报表上传数据info")
public class TableDateInfo {

    @ApiModelProperty(value = "项目id")
    private Long projectId;

    @ApiModelProperty(value = "周期id列表")
    private Long[] periodIdList;

    @ApiModelProperty(value = "测量类型")
    private int surveyType;

}