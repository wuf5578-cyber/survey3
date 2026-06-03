
package io.cug.common.utils;

import com.baomidou.mybatisplus.annotation.TableField;

import java.util.List;

public class TreeEntity {
    private long id; //子级
    private String name; //名称
    private long parent_id; //父级id

    @TableField(exist = false)
    private List<TreeEntity> childrenList; //子级集合
}
