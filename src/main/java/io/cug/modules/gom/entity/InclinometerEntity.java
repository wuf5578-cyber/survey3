
package io.cug.modules.gom.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;


/**
 * 监测目标管理表
 *
 */
@Data
@TableName("gom_survey_inclinometer")
public class InclinometerEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * ID
	 */
	@TableId
	private Long id;

	/**
	 * 目标点ID
	 */
	private Long objectId;

	/**
	 * 周期ID
	 */
	private Long periodId;

	/**
	 * 测斜孔初始位置的深度
	 */
	private float initialDepth;

	/**
	 * 测斜孔的深度的步距
	 */
	private float stepDepth;

	/**
	 * 设备ID
	 */
	private Long deviceId;

	/**
	 * 测斜孔以深度排列 测量值的 个数
	 */
	private Long count;

	/**
	 * 变形观测值
	 */
	private String value;
	/**
	 * 变形观测值变化值
	 */
	private String valueChange;
	private String valueAccumulative;
	private String speed;
	/**
	 * 状态:99-无效或删除，
	 */
	private int status;

	/**
	 * 备注
	 */
	private String remark;

	@TableField(exist = false)
	private String initialValue;

	@TableField(exist = false)
	private String periodName;

	@TableField(exist = false)
	private String objectName;

	@TableField(exist = false)
	private Date begin;

	@TableField(exist = false)
	private Date end;

}
