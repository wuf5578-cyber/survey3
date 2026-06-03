
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
@TableName("gom_point_link_survey")
public class PointEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * ID
	 */
	@TableId
	private Long id;

	/**
	 * 所属项目ID
	 */
	private Long projectId;

	/**
	 * 目标点ID
	 */
	private Long objectId;

	/**
	 * 检测信息类型
	 */
	private int surveyType;

	/**
	 * 备注
	 */
	private String remark;

	/**
	 * 状态:99-无效或删除，
	 */
	private int status;

}
