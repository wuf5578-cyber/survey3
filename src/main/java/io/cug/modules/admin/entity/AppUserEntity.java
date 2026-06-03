
package io.cug.modules.admin.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/**
 * 
 * 
 * @author
 * @email 3582996245@qq.com
 * @date 2022-01-20 12:10:43
 */
@Data
@TableName("gom_user")
@JsonIgnoreProperties(value = {"password"})
public class AppUserEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 用户id
	 */
	@TableId(value = "uid",type = IdType.AUTO)
	private Integer uid;

	/**
	 * 手机号
	 */
	private String mobile;

	/**
	 * 用户名
	 */
	private String username;

	/**
	 * 密码
	 */
	private String password;

	/**
	 * 性别(0未知，1男，2女)
	 */
	private Integer gender;

	/**
	 * 单位 部门 ID
	 */
	private Integer departmentId;

	/**
	 * 头像
	 */
	private String avatar;

	/**
	 * 微信登录唯一标识
	 */
	private String Openid;

	/**
	 * 状态
	 */
	private Integer status;

	/**
	 * 最后登录ip
	 */
	private String lastLoginIp;

	/**
	 * 职位 代码
	 */
	private Integer position;

	/**
	 * 邮箱
	 */
	private String email;

	/**
	 * 角色ID列表
	 */
	@TableField(exist=false)
	private List<Long> roleIdList;

	/**
	 * 更新时间
	 */
	private Date updateTime;
	/**
	 * 创建时间
	 */
	private Date createTime;

}
