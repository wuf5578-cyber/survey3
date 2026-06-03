
package io.cug.modules.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.cug.modules.admin.entity.AppUserRoleEntity;
import io.cug.modules.sys.entity.SysUserRoleEntity;

import java.util.List;


/**
 * 用户与角色对应关系
 *
 */
public interface AppUserRoleService extends IService<AppUserRoleEntity> {
	
	void saveOrUpdate(Integer uid, List<Long> roleIdList);
	
	/**
	 * 根据用户ID，获取角色ID列表
	 */
	List<Long> queryRoleIdList(Integer uid);

	/**
	 * 根据角色ID数组，批量删除
	 */
	int deleteBatch(Long[] roleIds);
}
