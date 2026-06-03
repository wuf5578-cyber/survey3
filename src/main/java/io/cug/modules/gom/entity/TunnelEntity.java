
package io.cug.modules.gom.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;


/**
 * 隧道表
 *
 */
@Data
@TableName("gom_tunnel")
public class TunnelEntity implements Serializable {
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
	 * 隧道名
	 */
	private String tunnelName;


	/**
	 * 创建时间
	 */
	private Date createTime;

	/**
	 * 状态:99-无效或删除，
	 */
	private int status;
}
