
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
@TableName("gom_object")
public class ObjectEntity implements Serializable {
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
	private int sortIndex;

	/**
	 * 名称
	 */
	private String name;

	private int surveyMethod;

	/**
	 * 定位
	 */
	private String position;

	/**
	 * 备注
	 */
	private String remark;

	/**
	 * 创建时间
	 */
	private Date createTime;

	/**
	 * 创建项目用户ID
	 */
	private Long createByUserid;

	/**
	 * 状态:99-无效或删除，
	 */
	private int status;

	/** 子类目标点数据 */
	@TableField(exist = false)
	private List<ObjectEntity> childObjectEntity;

	@TableField(exist = false)
	private double warningA1;
	@TableField(exist = false)
	private double warningA2;
	@TableField(exist = false)
	private double warningB1;
	@TableField(exist = false)
	private double warningB2;
	@TableField(exist = false)
	private ParamEntity paramEntity;
	@TableField(exist = false)
	private DisParamsEntity disParamsEntity;
}
