
package io.cug.modules.admin.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.cug.modules.admin.entity.AppMenuEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 菜单管理
 *
 */
@Mapper
public interface AppMenuDao extends BaseMapper<AppMenuEntity> {
	
	/**
	 * 根据父菜单，查询子菜单
	 * @param parentId 父菜单ID
	 */
	List<AppMenuEntity> queryListParentId(Long parentId);
	
	/**
	 * 获取不包含按钮的菜单列表
	 */
	List<AppMenuEntity> queryNotButtonList();

}
