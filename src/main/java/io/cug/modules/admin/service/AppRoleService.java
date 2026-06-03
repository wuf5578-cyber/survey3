
package io.cug.modules.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.cug.common.utils.PageUtils;
import io.cug.modules.admin.entity.AppRoleEntity;
import io.cug.modules.sys.entity.SysRoleEntity;

import java.util.List;
import java.util.Map;


/**
 * 角色
 *
 */
public interface AppRoleService extends IService<AppRoleEntity> {

	PageUtils queryPage(Map<String, Object> params);

	void saveRole(AppRoleEntity role);

	void update(AppRoleEntity role);

	void deleteBatch(Long[] roleIds);

	/**
	 * 查询用户创建的角色ID列表
	 */
	List<Long> queryRoleIdList(Long createUserId);
}
