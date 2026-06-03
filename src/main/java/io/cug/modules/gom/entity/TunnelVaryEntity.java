
package io.cug.modules.gom.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;


/**
 * 隧道环表
 *
 */
@Data
@TableName("gom_tunnel_vary")
public class TunnelVaryEntity implements Serializable {
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

	/**
	 * 环号
	 */
	private Long loopId;

	/**
	 * 周期
	 */
	private Long periodId;


	/**
	 * 收敛
	 */
	private double lrConvergence;
	private double hConvergence;

	/**
	 * 沉降
	 */
	private double domeSubsidence;
	private double bedSubsidence;
	private double differSubsidence;
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
	 * 状态:99-无效或删除，
	 */
	private int status;

	@TableField(exist = false)
	private Date begin;
	@TableField(exist = false)
	private int objectType;
	@TableField(exist = false)
	private Date startTime;
	@TableField(exist = false)
	private Date endTime;
	@TableField(exist = false)
	private String periodName;

}
