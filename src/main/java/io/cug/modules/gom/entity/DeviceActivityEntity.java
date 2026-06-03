
package io.cug.modules.gom.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;


/**
 * 设备活动表
 *
 */
@Data
@TableName("gom_device_activity")
public class DeviceActivityEntity implements Serializable {
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
	 * 设备ID
	 */
	private Long deviceId;
	/**
	 * iot ID
	 */
	private Long IotId;
	/**
	 * 设备名
	 */
	private String deviceName;
	/**
	 * 测站ID
	 */
	private Long stationId;

	/**
	 * 周期ID
	 */
	private Long periodId;
	/**
	 * 周期名
	 */
	private String periodName;

	/**
	 * 开始时间
	 */
	@JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
	private Date beginTime;

	/**
	 * 持续时间
	 */
	private Long duration;

	/**
	 * 状态:99-无效或删除，
	 */
	private int status;

	private String stationName;

}
