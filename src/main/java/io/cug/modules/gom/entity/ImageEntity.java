
package io.cug.modules.gom.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.sql.Blob;


/**
 * 图像文件image
 *
 */
@Data
@TableName("gom_image")
public class ImageEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * ID
	 */
	private Long Id;

	/**
	 * 所属项目ID
	 */
	private Long projectId;

	/**
	 * 类型：
	 */
	private int type;
	private String name;
	private String url;

	/**
	 * 状态:99-无效或删除，
	 */
	private int status;

}
