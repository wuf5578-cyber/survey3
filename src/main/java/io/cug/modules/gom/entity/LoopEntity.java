
package io.cug.modules.gom.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;


/**
 * 隧道环表
 *
 */
@Data
@TableName("gom_loop")
public class LoopEntity implements Serializable {
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
	 * 隧道ID
	 */
	private Long tunnelId;
	/**
	 * 环号
	 */
	private Long loopName;

	/**
	 * 创建时间
	 */
	private Date createTime;

	/**
	 * 状态:99-无效或删除，
	 */
	private int status;
	@TableField(exist = false)
	private String objectName;
	@TableField(exist = false)
	private Long ObjectType;
}
