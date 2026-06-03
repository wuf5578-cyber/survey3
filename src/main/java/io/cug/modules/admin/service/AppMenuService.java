
package io.cug.modules.admin.service;


import com.baomidou.mybatisplus.extension.service.IService;
import io.cug.modules.admin.entity.AppMenuEntity;

import java.util.List;


/**
 * 菜单管理
 *
 */
public interface AppMenuService extends IService<AppMenuEntity> {

	/**
	 * 根据父菜单，查询子菜单
	 * @param parentId 父菜单ID
	 * @param menuIdList  用户菜单ID
	 */
	List<AppMenuEntity> queryListParentId(Long parentId, List<Long> menuIdList);

	/**
	 * 根据父菜单，查询子菜单
	 * @param parentId 父菜单ID
	 */
	List<AppMenuEntity> queryListParentId(Long parentId);
	
	/**
	 * 获取不包含按钮的菜单列表
	 */
	List<AppMenuEntity> queryNotButtonList();
	
	/**
	 * 获取用户菜单列表
	 */
	List<AppMenuEntity> getUserMenuList(Integer uid);

	/**
	 * 删除
	 */
	void delete(Long menuId);
}
