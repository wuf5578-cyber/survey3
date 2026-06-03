
package io.cug.modules.gom.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;


/**
 * 预警管理表
 *
 */
@Data
@TableName("gom_alarm")
public class AlarmEntity implements Serializable {
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
	 * 目标点ID
	 */
	private Long objectId;

	/**
	 * 周期ID
	 */
	private Long periodId;

	/**
	 * 报警测量类型
	 */
	private int surveyType;

	/**
	 * 报警级别
	 */
	private int alarmType;

	/**
	 * 报警时间
	 */
	private Date alarmTime;

	/**
	 * 状态:99-无效或删除，
	 */
	private int status;
	/**
	 * 目标点name
	 */
	@TableField(exist = false)
	private String objectName;

	/**
	 * 周期时间
	 */
	@TableField(exist = false)
	private Date periodBegin;
}
