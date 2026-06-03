
package io.cug.modules.admin.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.cug.modules.admin.entity.AppRoleMenuEntity;
import io.cug.modules.sys.entity.SysRoleMenuEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 角色与菜单对应关系
 *
 */
@Mapper
public interface AppRoleMenuDao extends BaseMapper<AppRoleMenuEntity> {
	
	/**
	 * 根据角色ID，获取菜单ID列表
	 */
	List<Long> queryMenuIdList(Long roleId);

	/**
	 * 根据角色ID数组，批量删除
	 */
	int deleteBatch(Long[] roleIds);
}
