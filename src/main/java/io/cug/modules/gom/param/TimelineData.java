
package io.cug.modules.gom.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;
@Data
public class TimelineData {
    private int index;
    private String leftTime;
    private String startTime;
    private String endTime;
    private String color;
    private List<WarningData> yellowData;
    private List<WarningData> redData;
    private int objectNum;
    private int normalObjectNum;
    private int yellowObjectNum;
    private int redObjectNum;

    // Constructors, getters, and setters

    // Example constructor
    public TimelineData(int index, String leftTime, String startTime, String endTime, String color,
                        List<WarningData> yellowData, List<WarningData> redData,int objectNum,int normalObjectNum,int yellowObjectNum,int redObjectNum) {
        this.index = index;
        this.leftTime = leftTime;
        this.startTime = startTime;
        this.endTime = endTime;
        this.color = color;
        this.yellowData = yellowData;
        this.redData = redData;
        this.objectNum = objectNum;
        this.normalObjectNum = normalObjectNum;
        this.yellowObjectNum = yellowObjectNum;
        this.redObjectNum = redObjectNum;
    }
}
