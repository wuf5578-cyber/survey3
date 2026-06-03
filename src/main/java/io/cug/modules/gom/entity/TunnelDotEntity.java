package io.cug.modules.gom.entity;


import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
点位测量表
 */

@Data
@TableName("gom_tunnel_dot")
public class TunnelDotEntity implements Serializable {
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
     *状态
     */
    private int status;
}
