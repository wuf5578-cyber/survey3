
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
@TableName("gom_survey_extend")
public class ExtendEntity implements Serializable {
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
	 * 设备ID
	 */
	private Long deviceId;

	/**
	 * 测量值的个数
	 */
	private Long count;

	/**
	 * 观测值
	 */
	private String value;
	private double inclination;
	private double inclinationRate;
	/**
	 * 类型
	 */
	private int type;

	/**
	 * 状态:99-无效或删除，
	 */
	private int status;

	/**
	 * 备注
	 */
	private String remark;
	@TableField(exist = false)
	private String periodName;
	@TableField(exist = false)
	private String objectName;
	@TableField(exist = false)
	private Date begin;
	@TableField(exist = false)
	private Date end;

}
