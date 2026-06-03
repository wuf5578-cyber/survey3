
package io.cug.modules.admin.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.cug.modules.admin.entity.AppRoleEntity;
import io.cug.modules.sys.entity.SysRoleEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 角色管理
 *
 */
@Mapper
public interface AppRoleDao extends BaseMapper<AppRoleEntity> {
	
	/**
	 * 查询用户创建的角色ID列表
	 */
	List<Long> queryRoleIdList(Long createUserId);
}
