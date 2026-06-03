
package io.cug.modules.gom.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("gom_object_early_warning")
public class EarlyWarningEntity implements Serializable {
	/**
	 * ID
	 */
	private Long Id;
	private Long projectId;
	/**
	 * 测量点ID
	 */
	private Long objectId;

	/**
	 * 测量类型
	 */
	private int surveyType;

	private double warningA1;
	private double warningA2;
	private double warningB1;
	private double warningB2;
	private int type;
	private int status;

}
