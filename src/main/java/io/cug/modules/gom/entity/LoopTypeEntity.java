
package io.cug.modules.gom.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;


/**
 * 隧道环点类型表
 *
 */
@Data
@TableName("gom_loop_type")
public class LoopTypeEntity implements Serializable {
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
	 * 环号
	 */
	private Long loopId;

	/**
	 * type
	 */
	private Long type;

	/**
	 * 点ID
	 */
	private Long objectId;

	/**
	 * 状态:99-无效或删除，
	 */
	private int status;
}
