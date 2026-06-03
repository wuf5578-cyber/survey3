
package io.cug.modules.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.cug.common.utils.MapUtils;
import io.cug.modules.admin.dao.AppUserRoleDao;
import io.cug.modules.admin.entity.AppUserRoleEntity;
import io.cug.modules.admin.service.AppUserRoleService;
import io.cug.modules.sys.dao.SysUserRoleDao;
import io.cug.modules.sys.entity.SysUserRoleEntity;
import io.cug.modules.sys.service.SysUserRoleService;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * 用户与角色对应关系
 *
 */
@Service("appUserRoleService")
public class AppUserRoleServiceImpl extends ServiceImpl<AppUserRoleDao, AppUserRoleEntity> implements AppUserRoleService {

	@Override
	public void saveOrUpdate(Integer uid, List<Long> roleIdList) {
		//先删除用户与角色关系
		this.removeByMap(new MapUtils().put("uid", uid));

		if(roleIdList == null || roleIdList.size() == 0){
			return ;
		}

		//保存用户与角色关系
		for(Long roleId : roleIdList){
			AppUserRoleEntity appUserRoleEntity = new AppUserRoleEntity();
			appUserRoleEntity.setUid(uid);
			appUserRoleEntity.setRoleId(roleId);

			this.save(appUserRoleEntity);
		}
	}

	@Override
	public List<Long> queryRoleIdList(Integer uid) {
		return baseMapper.queryRoleIdList(uid);
	}

	@Override
	public int deleteBatch(Long[] roleIds){
		return baseMapper.deleteBatch(roleIds);
	}
}
