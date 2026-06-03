
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
@TableName("gom_power_params")
public class PowerParamsEntity implements Serializable {
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

	/**
	 * 测量类型
	 */
	private int surveyType;

	private double Ec;
	private double Ac;
	private double Es;
	private double As;

	/**
	 * 状态:99-无效或删除，
	 */
	private int status;

}
