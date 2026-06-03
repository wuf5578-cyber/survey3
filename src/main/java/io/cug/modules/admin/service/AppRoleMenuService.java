
package io.cug.modules.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.cug.modules.admin.entity.AppRoleMenuEntity;
import io.cug.modules.sys.entity.SysRoleMenuEntity;

import java.util.List;


/**
 * 角色与菜单对应关系
 *
 */
public interface AppRoleMenuService extends IService<AppRoleMenuEntity> {
	
	void saveOrUpdate(Long roleId, List<Long> menuIdList);
	
	/**
	 * 根据角色ID，获取菜单ID列表
	 */
	List<Long> queryMenuIdList(Long roleId);

	/**
	 * 根据角色ID数组，批量删除
	 */
	int deleteBatch(Long[] roleIds);
	
}
