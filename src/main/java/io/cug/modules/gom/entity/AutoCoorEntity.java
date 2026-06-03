
package io.cug.modules.gom.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;


/**
 * 自动测站点数据管理表
 *
 */
@Data
@TableName("gom_auto_coor")
public class AutoCoorEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * ID
	 */
	@TableId
	private Long id;

	private Long projectId;

	/**
	 * 周期id 当id为-99时代表此点为已知点 id>0时代表为测量数据
	 */
	private Long periodId;

	/**
	 * 目标点ID
	 */
	private Long objectId;

	private double x;

	private double y;

	private double h;

	/**
	 * 状态:99-无效或删除，
	 */
	private int status;

	/**
	 * 备注
	 */
	private String remark;

	/**
	 * 创建时间
	 */
	private Date createTime;

	@TableField(exist = false)
	private String name;


}
