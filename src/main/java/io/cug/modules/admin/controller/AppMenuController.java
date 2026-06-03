
package io.cug.modules.admin.controller;

import io.cug.common.annotation.SysLog;
import io.cug.common.exception.cugException;
import io.cug.common.utils.Constant;
import io.cug.common.utils.R;
import io.cug.modules.admin.entity.AppMenuEntity;
import io.cug.modules.admin.service.AppMenuService;
import io.cug.modules.sys.controller.AbstractController;
import io.cug.modules.sys.entity.SysMenuEntity;
import io.cug.modules.sys.service.ShiroService;
import io.cug.modules.sys.service.SysMenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * 系统菜单
 *
 */
@Api(tags = "前台系统菜单")
@RestController
@RequestMapping("/admin/menu")
public class AppMenuController extends AbstractController {
	@Autowired
	private AppMenuService appMenuService;
	@Autowired
	private ShiroService shiroService;

	/**
	 * 导航菜单
	 */
	@ApiOperation("导航菜单")
	@GetMapping("/nav")
	public R nav(){
		List<AppMenuEntity> menuList = appMenuService.getUserMenuList(1);
		Set<String> permissions = shiroService.getUserPermissions(getUserId());
		return R.ok().put("menuList", menuList).put("permissions", permissions);
	}
	/**
	 * 所有菜单列表
	 */
	@ApiOperation("所有菜单列表")
	@GetMapping("/list")
	@RequiresPermissions("admin:menu:list")
	public List<AppMenuEntity> list(){
		List<AppMenuEntity> menuList = appMenuService.list();
		HashMap<Long, AppMenuEntity> menuMap = new HashMap<>(12);
		for (AppMenuEntity s : menuList) {
			menuMap.put(s.getMenuId(), s);
		}
		for (AppMenuEntity s : menuList) {
			AppMenuEntity parent = menuMap.get(s.getParentId());
			if (Objects.nonNull(parent)) {
				s.setParentName(parent.getName());
			}

		}
		return menuList;
	}
	
	/**
	 * 选择菜单(添加、修改菜单)
	 */
	@ApiOperation("选择菜单")
	@GetMapping("/select")
	@RequiresPermissions("admin:menu:select")
	public R select(){
		//查询列表数据
		List<AppMenuEntity> menuList = appMenuService.queryNotButtonList();
		
		//添加顶级菜单
		AppMenuEntity root = new AppMenuEntity();
		root.setMenuId(0L);
		root.setName("一级菜单");
		root.setParentId(-1L);
		root.setOpen(true);
		menuList.add(root);
		
		return R.ok().put("menuList", menuList);
	}
	
	/**
	 * 菜单信息
	 */
	@ApiOperation("根据id获取菜单信息")
	@GetMapping("/info/{menuId}")
	@RequiresPermissions("admin:menu:info")
	public R info(@PathVariable("menuId") Long menuId){
		AppMenuEntity menu = appMenuService.getById(menuId);
		return R.ok().put("menu", menu);
	}
	
	/**
	 * 保存
	 */
	@ApiOperation("保存菜单")
	@SysLog("保存菜单")
	@PostMapping("/save")
	@RequiresPermissions("admin:menu:save")
	public R save(@RequestBody AppMenuEntity menu){
		//数据校验
		verifyForm(menu);
		
		appMenuService.save(menu);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@ApiOperation("修改菜单")
	@SysLog("修改菜单")
	@PostMapping("/update")
	@RequiresPermissions("admin:menu:update")
	public R update(@RequestBody AppMenuEntity menu){
		//数据校验
		verifyForm(menu);
				
		appMenuService.updateById(menu);
		
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@ApiOperation("删除菜单")
	@SysLog("删除菜单")
	@PostMapping("/delete/{menuId}")
	@RequiresPermissions("admin:menu:delete")
	public R delete(@PathVariable("menuId") long menuId){
//		if(menuId <= 31){
//			return R.error("系统菜单，不能删除");
//		}

		//判断是否有子菜单或按钮
		List<AppMenuEntity> menuList = appMenuService.queryListParentId(menuId);
		if(menuList.size() > 0){
			return R.error("请先删除子菜单或按钮");
		}

		appMenuService.delete(menuId);

		return R.ok();
	}
	
	/**
	 * 验证参数是否正确
	 */
	private void verifyForm(AppMenuEntity menu){
		if(StringUtils.isBlank(menu.getName())){
			throw new cugException("菜单名称不能为空");
		}
		
		if(menu.getParentId() == null){
			throw new cugException("上级菜单不能为空");
		}
		
		//菜单
		if(menu.getType() == Constant.MenuType.MENU.getValue()){
			if(StringUtils.isBlank(menu.getUrl())){
				throw new cugException("菜单URL不能为空");
			}
		}
		
		//上级菜单类型
		int parentType = Constant.MenuType.CATALOG.getValue();
		if(menu.getParentId() != 0){
			AppMenuEntity parentMenu = appMenuService.getById(menu.getParentId());
			parentType = parentMenu.getType();
		}
		
		//目录、菜单
		if(menu.getType() == Constant.MenuType.CATALOG.getValue() ||
				menu.getType() == Constant.MenuType.MENU.getValue()){
			if(parentType != Constant.MenuType.CATALOG.getValue()){
				throw new cugException("上级菜单只能为目录类型");
			}
			return ;
		}
		
		//按钮
		if(menu.getType() == Constant.MenuType.BUTTON.getValue()){
			if(parentType != Constant.MenuType.MENU.getValue()){
				throw new cugException("上级菜单只能为菜单类型");
			}
			return ;
		}
	}
}
