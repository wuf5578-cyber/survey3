
package io.cug.modules.gom.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;


/**
 * 预警管理表
 *
 */
@Data
@TableName("gom_project_survey")
public class ProjectSurveyEntity implements Serializable {
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
	 * 项目进度
	 */
	private String schedule;

	/**
	 * 巡视情况
	 */
	private String situation;

	/**
	 * 进度时间
	 */
	@JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
	private Date scheduleTime;

	/**
	 * 建议
	 */
	private String advice;

	/**
	 * 报警时间
	 */
	private Date createTime;

	/**
	 * 状态:99-无效或删除，
	 */
	private int status;
}
