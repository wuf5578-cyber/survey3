
package io.cug.modules.gom.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.joda.time.DateTime;

import java.io.Serializable;
import java.util.Date;
import java.util.List;


/**
 * 周期管理表
 *
 */
@Data
@TableName("gom_period")
public class PeriodEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * ID
	 */
	@TableId
	private Long id;

	/**
	 * 名称
	 */
	private String name;

	/**
	 * 所属项目ID
	 */
	private Long projectId;

	/**
	 * 开始时间
	 */
	@JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
	private Date begin;

	/**
	 * 结束时间
	 */
	@JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
	private Date end;

	/**
	 * 备注
	 */
	private String remark;

	/**
	 * 创建时间
	 */
	private Date createTime;

	/**
	 * 创建用户ID
	 */
	private Long createByUserId;

	/**
	 * 状态:99-无效或删除，
	 */
	private int status;

	@TableField(exist = false)
	private String keyWord;

	@TableField(exist = false)
	private double riskProbability;

	@TableField(exist = false)
	private int degree;

}
