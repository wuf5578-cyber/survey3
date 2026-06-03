
package io.cug.modules.gom.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.List;


/**
 * 监测目标管理表
 *
 */
@Data
@TableName("gom_project_link_department")
public class ProLinkDepartEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 所属项目ID
	 */
	private Long projectId;

	/**
	 * 目标点ID
	 */
	private Long objectId;

	/**
	 * 部门ID
	 */
	private Long departmentId;

	/**
	 * 部门类型
	 */
	private int departmentType;

	/**
	 * 备注
	 */
	private String remark;

	/**
	 * 状态:99-无效或删除，
	 */
	private int status;

}
