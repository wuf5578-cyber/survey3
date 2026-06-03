
package io.cug.modules.gom.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;


/**
 * 监测目标管理表
 *
 */
@Data
@TableName("gom_survey_displacement")
public class DisplacementEntity implements Serializable {
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

	@TableField(exist = false)
	private String objectName;

	/**
	 * 周期ID
	 */
	private Long periodId;

	/**
	 * 设备ID
	 */
	private Long deviceId;

	@TableField(exist = false)
	private double initialX;

	@TableField(exist = false)
	private double initialY;

	/**
	 * x
	 */
	private double x;

	/**
	 * y
	 */
	private double y;

	/**
	 * load
	 */
	private double loadData;
	/**
	 * 本次沉降量
	 */
	private double valueOne;
	/**
	 * 累计沉降量
	 */
	private double valueAccumulative;

	private double speed;

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
	private Date begin;

	@TableField(exist = false)
	private Date end;

}
