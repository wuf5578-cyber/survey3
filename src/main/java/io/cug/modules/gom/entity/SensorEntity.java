package io.cug.modules.gom.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("gom_sensor")
public class SensorEntity implements Serializable {
    @TableId
    private long id;

    private long projectId;

    private long deviceId;

    private double humidity;

    private double temperature;

    private double oxygenConcentration;

    private double gasConcentration;

    private double dustConcentration;

    private double windSpeed;

    private double airflow;

    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    private Date surveyTime;

}
