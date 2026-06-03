package io.cug.modules.gom.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 自动计算管理表
 *
 */
@Data
@TableName("gom_auto_compute_group")
public class AutoComputeGroupEntity {
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId
    private Long id;

    /**
     * 项目ID
     */
    private Long projectId;

    private String name;

    private int count;

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
    private List<AutoComputeEntity> autoComputeEntityList;
}
