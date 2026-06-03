package io.cug.modules.gom.entity;


import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
弧长表
 */

@Data
@TableName("gom_tunnel_string")
public class TunnelStringEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     *ID
     */
    @TableId
    private Long id;
    /**
     * 周期ID
     */
    private Long periodId;
    /**
     * 环ID
     */
    private Long loopName;
    /**
     * 弦长
     */
    private double al;
    private double ar;
    private double ac;
    private double ad;
    private double cd;
    private double lc;
    private double ld;
    private double lr;
    private double rc;
    private double rd;
    /**
     * 弦长累计变化值
     */
    private double alAccumulative;
    private double arAccumulative;
    private double acAccumulative;
    private double adAccumulative;
    private double cdAccumulative;
    private double lcAccumulative;
    private double ldAccumulative;
    private double lrAccumulative;
    private double rcAccumulative;
    private double rdAccumulative;
    /**
     *状态
     */
    private int status;
}
