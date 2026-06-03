
package io.cug.modules.admin.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.cug.common.utils.Constant;
import io.cug.common.utils.MapUtils;
import io.cug.modules.admin.dao.AppMenuDao;
import io.cug.modules.admin.entity.AppMenuEntity;
import io.cug.modules.admin.service.AppMenuService;
import io.cug.modules.admin.service.AppRoleMenuService;
import io.cug.modules.admin.service.AppUserService;
import io.cug.modules.sys.service.SysRoleMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;


@Service("appMenuService")
public class AppMenuServiceImpl extends ServiceImpl<AppMenuDao, AppMenuEntity> implements AppMenuService {
	@Autowired
	private AppUserService appUserService;

	@Autowired
	private AppRoleMenuService appRoleMenuService;
	
	@Override
	public List<AppMenuEntity> queryListParentId(Long parentId, List<Long> menuIdList) {
		List<AppMenuEntity> menuList = queryListParentId(parentId);
		if(menuIdList == null){
			return menuList;
		}
		
		List<AppMenuEntity> userMenuList = new ArrayList<>();
		for(AppMenuEntity menu : menuList){
			if(menuIdList.contains(menu.getMenuId())){
				userMenuList.add(menu);
			}
		}
		return userMenuList;
	}

	@Override
	public List<AppMenuEntity> queryListParentId(Long parentId) {
		return baseMapper.queryListParentId(parentId);
	}

	@Override
	public List<AppMenuEntity> queryNotButtonList() {
		return baseMapper.queryNotButtonList();
	}

	@Override
	public List<AppMenuEntity> getUserMenuList(Integer uid) {
		//系统管理员，拥有最高权限
		if(uid == Constant.SUPER_ADMIN){
			return getMenuList(null);
		}
		
		//用户菜单列表
		List<Long> menuIdList = appUserService.queryAllMenuId(uid);
		return getMenuList(menuIdList);
	}

	/**
	 * 获取拥有的菜单列表
	 * @param menuIdList
	 * @return
	 */
	private List<AppMenuEntity> getMenuList(List<Long> menuIdList) {
		// 查询拥有的所有菜单
		List<AppMenuEntity> menus = this.baseMapper.selectList(new QueryWrapper<AppMenuEntity>()
				.in(Objects.nonNull(menuIdList), "menu_id", menuIdList).in("type", 0, 1));
		// 将id和菜单绑定
		HashMap<Long, AppMenuEntity> menuMap = new HashMap<>(12);
		for (AppMenuEntity s : menus) {
			menuMap.put(s.getMenuId(), s);
		}
		// 使用迭代器,组装菜单的层级关系
		Iterator<AppMenuEntity> iterator = menus.iterator();
		while (iterator.hasNext()) {
			AppMenuEntity menu = iterator.next();
			AppMenuEntity parent = menuMap.get(menu.getParentId());
			if (Objects.nonNull(parent)) {
				parent.getList().add(menu);
				// 将这个菜单从当前节点移除
				iterator.remove();
			}
		}

		return menus;
	}

	@Override
	public void delete(Long menuId){
		//删除菜单
		this.removeById(menuId);
		//删除菜单与角色关联
		appRoleMenuService.removeByMap(new MapUtils().put("menu_id", menuId));
	}

	/**
	 * 获取所有菜单列表
	 */
	private List<AppMenuEntity> getAllMenuList(List<Long> menuIdList){
		//查询根菜单列表
		List<AppMenuEntity> menuList = queryListParentId(0L, menuIdList);
		//递归获取子菜单
		getMenuTreeList(menuList, menuIdList);
		
		return menuList;
	}

	/**
	 * 递归
	 */
	private List<AppMenuEntity> getMenuTreeList(List<AppMenuEntity> menuList, List<Long> menuIdList){
		List<AppMenuEntity> subMenuList = new ArrayList<AppMenuEntity>();
		
		for(AppMenuEntity entity : menuList){
			//目录
			if(entity.getType() == Constant.MenuType.CATALOG.getValue()){
				entity.setList(getMenuTreeList(queryListParentId(entity.getMenuId(), menuIdList), menuIdList));
			}
			subMenuList.add(entity);
		}
		
		return subMenuList;
	}
}
