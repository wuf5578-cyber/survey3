
package io.cug.modules.gom.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;


/**
 * 项目报表内容管理表
 *
 */
@Data
@TableName("gom_project_report")
public class ReportEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * ID
	 */
	@TableId
	private long id;

	/**
	 * 项目ID
	 */
	private long projectId;

	private String chapter1;
	private String chapter2;
	private String chapter3;
	private String chapter4;
	private String chapter5;
	private String chapter6;
	private String chapter7;
	private String chapter8;
	private String chapter9;
	private String chapter10;
	private String chapter11;
	private String chapter12;

	private int status;

}
