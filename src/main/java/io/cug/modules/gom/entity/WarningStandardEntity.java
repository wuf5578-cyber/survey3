
package io.cug.modules.gom.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("gom_warning_standard")
public class WarningStandardEntity implements Serializable {

	private Long Id;

	private int surveyType;

	private String objectType;//目标类型

	private int foundationType;//基坑级别 0：一级二级 1：一级 2：二级

	private double warningA1;
	private double warningA2;
	private double warningB1;
	private double warningB2;

	private int type;

	private int status;

}
