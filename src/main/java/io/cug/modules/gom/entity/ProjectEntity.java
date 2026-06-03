
package io.cug.modules.gom.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.joda.time.DateTime;

import java.io.Serializable;
import java.util.Date;


/**
 * 项目
 *
 */
@Data
@TableName("gom_project")
public class ProjectEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * ID
	 */
	@TableId
	private Long id;

	/**
	 * 项目名称
	 */
	private String name;

	/**
	 * 项目类型
	 */
	private int type;

	/**
	 * 属地代码
	 */
	private int placeId;

	/**
	 * 地址
	 */
	private String adress;

	/**
	 * 定位坐标
	 */
	private String position;

	/**
	 * 备注
	 */
	private String remark;

	/**
	 * 创建时间
	 */
	private Date createTime;

	/**
	 * 创建项目用户ID
	 */
	private Long createByUserid;

	/**
	 * 状态:99-无效或删除，
	 */
	private int status;

}
