
package io.cug.modules.gom.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;


/**
 * 自动测站管理表
 *
 */
@Data
@TableName("gom_auto_station")
public class AutoStationEntity implements Serializable {
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
	 * 名称
	 */
	private String name;

	/**
	 * 设备ID
	 */
	private Long deviceId;

	/**
	 * 仪器高
	 */
	private double high;

	/**
	 * 状态:99-无效或删除，
	 */
	private int status;

	/**
	 * 备注
	 */
	private String remark;

	@TableField(exist = false)
	private DeviceEntity deviceEntity;
	@TableField(exist = false)
	private Long[] objectList;
}
