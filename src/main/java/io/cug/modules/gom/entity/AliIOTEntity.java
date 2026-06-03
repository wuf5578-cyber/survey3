
package io.cug.modules.gom.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;


/**
 * 阿里云IOT mqtt连接
 *
 */
@Data
@TableName("gom_iot_mqtt")
public class AliIOTEntity implements Serializable {
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
	 * productKey
	 */
	private String productKey;

	/**
	 * deviceName
	 */
	private String deviceName;

	/**
	 * DeviceSecret
	 */
	private String deviceSecret;
	/**
	 * 解析器id
	 */
	private Long parserId;
	/**
	 * 数据源id
	 */
	private Long dataSourceId;
	/**
	 * 数据目的id
	 */
	private Long destinationId;

	/**
	 * 类型：0：user 1：测量仪器
	 */
	private int type;

	/**
	 * 状态:0:ONlINE 1:OFFLINE 2:UNACTIVE 99:删除
	 */
	private int status;
}
