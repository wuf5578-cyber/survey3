
package io.cug.modules.gom.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;


/**
 * 应力参数表
 *
 */
@Data
@TableName("gom_power_params")
public class ParamEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * ID
	 */
	@TableId
	private Long id;

	/**
	 * 测量点ID
	 */
	private Long objectId;

	/**
	 * 设备ID
	 */
	private Long deviceId;

	/**
	 * 报警测量类型
	 */
	private int surveyType;

	private double elasticityConcrete;
	private double areaConcrete;
	private double elasticitySteel;
	private double areaSteel;

	/**
	 * 状态:99-无效或删除，
	 */
	private int status;

	@TableField(exist = false)
	private DeviceEntity deviceEntity;
}
