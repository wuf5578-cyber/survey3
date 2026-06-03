
package io.cug.modules.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.cug.common.exception.cugException;
import io.cug.common.utils.Constant;
import io.cug.common.utils.PageUtils;
import io.cug.common.utils.Query;
import io.cug.modules.admin.dao.AppRoleDao;
import io.cug.modules.admin.entity.AppRoleEntity;
import io.cug.modules.admin.service.AppRoleMenuService;
import io.cug.modules.admin.service.AppRoleService;
import io.cug.modules.admin.service.AppUserRoleService;
import io.cug.modules.admin.service.AppUserService;
import io.cug.modules.sys.dao.SysRoleDao;
import io.cug.modules.sys.entity.SysRoleEntity;
import io.cug.modules.sys.service.SysRoleMenuService;
import io.cug.modules.sys.service.SysRoleService;
import io.cug.modules.sys.service.SysUserRoleService;
import io.cug.modules.sys.service.SysUserService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 角色
 *
 */
@Service("appRoleService")
public class AppRoleServiceImpl extends ServiceImpl<AppRoleDao, AppRoleEntity> implements AppRoleService {
	@Autowired
	private AppRoleMenuService appRoleMenuService;
	@Autowired
	private AppUserService appUserService;
    @Autowired
    private AppUserRoleService appUserRoleService;

	@Override
	public PageUtils queryPage(Map<String, Object> params) {
		String roleName = (String)params.get("roleName");
		Long createUserId = (Long)params.get("createUserId");

		IPage<AppRoleEntity> page = this.page(
			new Query<AppRoleEntity>().getPage(params),
			new QueryWrapper<AppRoleEntity>()
				.like(StringUtils.isNotBlank(roleName),"role_name", roleName)
				.eq(createUserId != null,"create_user_id", createUserId)
		);

		return new PageUtils(page);
	}

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveRole(AppRoleEntity role) {
        role.setCreateTime(new Date());
        this.save(role);

        //保存角色与菜单关系
        appRoleMenuService.saveOrUpdate(role.getRoleId(), role.getMenuIdList());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(AppRoleEntity role) {
        this.updateById(role);

        //更新角色与菜单关系
        appRoleMenuService.saveOrUpdate(role.getRoleId(), role.getMenuIdList());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteBatch(Long[] roleIds) {
        //删除角色
        this.removeByIds(Arrays.asList(roleIds));

        //删除角色与菜单关联
        appRoleMenuService.deleteBatch(roleIds);

        //删除角色与用户关联
        appUserRoleService.deleteBatch(roleIds);
    }

    @Override
	public List<Long> queryRoleIdList(Long createUserId) {
		return baseMapper.queryRoleIdList(createUserId);
	}

}
