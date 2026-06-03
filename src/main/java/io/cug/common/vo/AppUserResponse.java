
package io.cug.common.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;


@Data
public class AppUserResponse implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 用户id
	 */
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
	 * 头像
	 */
	private String avatar;
	/**
	 * 性别(0未知，1男，2女)
	 */
	private Integer gender;
	/**
	 * 职位代码
	 */
	private Integer position;

	/**
	 * 个性签名
	 */
	private String email;

	/**
	 * 单位 部门 代码
	 */
	private Integer department_id;

	/**
	 * 最后登录ip
	 */
	private String lastLoginIp;



}
