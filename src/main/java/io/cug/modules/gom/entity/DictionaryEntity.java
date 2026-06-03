
package io.cug.modules.gom.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;


/**
 * 字典表
 *
 */
@Data
@TableName("sys_dictionary")
public class DictionaryEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * ID
	 */
	@TableId
	private Long id;

	/**
	 * 父节点ID
	 */
	private Long parentId;

	/**
	 * 名称
	 */
	private String name;

	/**
	 * 类型 0：字典类别；1：字典项
	 */
	private int type;

	/**
	 * 字典码
	 */
	private String code;

	/**
	 * 字典值
	 */
	private String value;

	/**
	 * 顺序
	 */
	private int sort;

	/**
	 * 状态:99-无效或删除，
	 */
	private int status;

	/**
	 * 备注
	 */
	private String remark;
}
