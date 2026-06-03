
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
@TableName("sys_department")
public class DepartmentEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * ID
	 */
	@TableId
	private Long id;

	/**
	 * 分组组ID
	 */
	private Long parentId;

	/**
	 * 类型
	 */
	private int type;

	/**
	 * 顺序
	 */
	private int sort;

	/**
	 * 名称
	 */
	private String name;

	/**
	 * 定位
	 */
	private int areaId;

	/**
	 * 更新时间
	 */
	private Date updateTime;

	/**
	 * 创建时间
	 */
	private Date createTime;

	/**
	 * 创建项目用户ID
	 */
	private Long createBy;

	/**
	 * 状态:99-无效或删除，
	 */
	private int status;

	/** 子类目标点数据 */
	@TableField(exist = false)
	private List<DepartmentEntity> childDepartmentEntity;
}
