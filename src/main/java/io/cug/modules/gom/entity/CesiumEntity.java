
package io.cug.modules.gom.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;


/**
 * 3dtiles表
 *
 */
@Data
@TableName("gom_3dtiles")
public class CesiumEntity implements Serializable {
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

	private int surveyType;

	private String pointId;

	private double longitude;

	private double latitude;

	private double height;
	/**
	 * 状态:99-无效或删除，
	 */
	private int status;

	@TableField(exist = false)
	private ObjectEntity objectEntity;

}
