
package io.cug.modules.gom.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;


/**
 * 自动学习管理表
 *
 */
@Data
@TableName("gom_auto_learn")
public class AutoLearnEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * ID
	 */
	@TableId
	private Long id;

	/**
	 * 测站ID
	 */
	private Long stationId;

	/**
	 * 目标点ID
	 */
	private Long objectId;

	/**
	 * 水平角
	 */
	private double hAngle;

	/**
	 * 竖直角
	 */
	private double vAngle;

	/**
	 * 距离
	 */
	private double distance;

	/**
	 * 目标高
	 */
	private double high;

    /**
	 * 观测顺序
	 */
	private int surveyIndex;

	/**
	 * 状态:99-无效或删除，
	 */
	private int status;

	/**
	 * 备注
	 */
	private String remark;

	@TableField(exist = false)
	private String name;

}
