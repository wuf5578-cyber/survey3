
package io.cug.modules.gom.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;


/**
 * 故障管理表
 *
 */
@Data
@TableName("gom_fault")
public class FaultEntity implements Serializable {
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
	 * 故障描述
	 */
	private String faultDescribe;

	/**
	 * 故障类型
	 */
	private int type;

	/**
	 * 故障状态
	 */
	private int status;

	/**
	 * 故障原因
	 */
	private String faultCause;

	/**
	 * 处理方法
	 */
	private String method;

	//处理时间
	@JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
	private Date time;
}
