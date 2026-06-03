package io.cug.modules.gom.entity;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
点位测量表
 */

@Data
@TableName("gom_loop_object")
public class LoopObjectEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     *ID
     */
    @TableId
    private Long id;
    /**
     * 项目ID
     */
    private Long projectId;
    /**
     * 周期ID
     */
    private Long periodId;
    /**
     * 点ID
     */
    private Long objectId;
    /**
     * x,y,z
     */
    private double x;
    private double y;
    private double z;
    /**
     * x,y,z位置变化
     */
    private double xChange;
    private double yChange;
    private double zChange;
    /**
     * x,y,z位置变化
     */
    private double xChangeAccumulative;
    private double yChangeAccumulative;
    private double zChangeAccumulative;
    /**
     * 位移
     */
    private double xDisplacement;
    private double yDisplacement;
    private double zDisplacement;
    /**
     *状态
     */
    private int status;
    /**
     * 点测量时间
     */
    private Date surveyTime;

    @TableField(exist = false)
    private Date begin;
    @TableField(exist = false)
    private long loopId;
    @TableField(exist = false)
    private int objectType;
    @TableField(exist = false)
    private Date startTime;
    @TableField(exist = false)
    private Date endTime;
    @TableField(exist = false)
    private String objectName;
}
