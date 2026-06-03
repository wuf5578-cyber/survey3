
package io.cug.modules.gom.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;


/**
 * power管理表
 *
 */
@Data
@TableName("gom_survey_power")
public class PowerEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * ID
	 */
	@TableId
	private Long id;

	/**
	 * 所属目标ID
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
	 * 率定系数
	 */
	private double calibrationCoefficient;

	/**
	 * 测量值
	 */
	private double value;
	/**
	 * 本次应力
	 */
	private double power;
	/**
	 * 应力变化值
	 */
	private double powerChange;
	/**
	 * 应力累计值
	 */
	private double powerAccumulative;

	private double speed;

	/**
	 * 力的类型：0-支撑轴力、1-钢筋应力、2-土压力 ...
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
	private double initialValue;

	@TableField(exist = false)
	private String periodName;

	@TableField(exist = false)
	private String objectName;

	@TableField(exist = false)
	private Date begin;

	@TableField(exist = false)
	private Date end;

}
