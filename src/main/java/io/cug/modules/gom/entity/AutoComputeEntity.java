package io.cug.modules.gom.entity;

import cn.hutool.db.DaoTemplate;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 自动计算管理表
 *
 */
@Data
@TableName("gom_auto_compute")
public class AutoComputeEntity {
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId
    private Long id;

    /**
     * 测量分组id 用创建的时间来标识
     */
    private Long groupId;

    /**
     * 测站ID
     */
    private Long stationId;

    /**
     * 监测顺序
     */
    private int surveyIndex;

    /**
     * 状态
     */
    private int status;

    /**
     * 备注
     */
    private String remark;

    /**
     * 创建时间
     */
    private Date createTime;

    @TableField(exist = false)
    private String stationName;
}
