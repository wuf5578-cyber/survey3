
package io.cug.modules.gom.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;


/**
 * 字典表
 *
 */
@Data
@TableName("gom_dis_params")
public class DisParamsEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * ID
	 */
	@TableId
	private Long id;

	/**
	 * 节点ID
	 */
	private Long objectId;

	private int xRatio;
	private int yRatio;
	private double x0;
	private double y0;

	/**
	 * 状态:99-无效或删除，
	 */
	private int status;

}
