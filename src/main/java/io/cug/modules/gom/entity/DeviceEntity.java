
package io.cug.modules.gom.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;


/**
 * 监测项目设备
 *
 */
@Data
@TableName("gom_project_device")
public class DeviceEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * ID
	 */
	private Long Id;

	/**
	 * 所属项目ID
	 */
	private Long projectId;

	/**
	 * 设备类型：0-全站仪，1-数字水准仪,...
	 */
	private int type;

	/**
	 * 设备名称
	 */
	private String name;
	/**
	 * 标定系数
	 */
	private double parameter;
	/**
	 * 初始频率
	 */
	private double frequency;
	/**
	 * 仪器截面积
	 */
	private double area;
	/**
	 * 测角精度
	 */
	private double angularAccuracy;
	/**
	 * 测距精度
	 */
	private double rangeAccuracy;
	/**
	 * 备注
	 */
	private String remark;

	/**
	 * 状态:99-无效或删除，
	 */
	private int status;

	@TableField(exist = false)
	private AliIOTEntity aliIOTEntity;

}
