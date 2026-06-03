
package io.cug.modules.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.cug.modules.admin.dao.AppRoleMenuDao;
import io.cug.modules.admin.entity.AppRoleMenuEntity;
import io.cug.modules.admin.service.AppRoleMenuService;
import io.cug.modules.sys.dao.SysRoleMenuDao;
import io.cug.modules.sys.entity.SysRoleMenuEntity;
import io.cug.modules.sys.service.SysRoleMenuService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * 角色与菜单对应关系
 *
 */
@Service("appRoleMenuService")
public class AppRoleMenuServiceImpl extends ServiceImpl<AppRoleMenuDao, AppRoleMenuEntity> implements AppRoleMenuService {

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void saveOrUpdate(Long roleId, List<Long> menuIdList) {
		//先删除角色与菜单关系
		deleteBatch(new Long[]{roleId});

		if(menuIdList.size() == 0){
			return ;
		}

		//保存角色与菜单关系
		for(Long menuId : menuIdList){
			AppRoleMenuEntity appRoleMenuEntity = new AppRoleMenuEntity();
			appRoleMenuEntity.setMenuId(menuId);
			appRoleMenuEntity.setRoleId(roleId);

			this.save(appRoleMenuEntity);
		}
	}

	@Override
	public List<Long> queryMenuIdList(Long roleId) {
		return baseMapper.queryMenuIdList(roleId);
	}

	@Override
	public int deleteBatch(Long[] roleIds){
		return baseMapper.deleteBatch(roleIds);
	}

}
