
package io.cug.modules.admin.controller;

import io.cug.common.annotation.SysLog;
import io.cug.common.utils.Constant;
import io.cug.common.utils.PageUtils;
import io.cug.common.utils.R;
import io.cug.common.validator.ValidatorUtils;
import io.cug.modules.admin.entity.AppRoleEntity;
import io.cug.modules.admin.service.AppRoleMenuService;
import io.cug.modules.admin.service.AppRoleService;
import io.cug.modules.sys.controller.AbstractController;
import io.cug.modules.sys.entity.SysRoleEntity;
import io.cug.modules.sys.service.SysRoleMenuService;
import io.cug.modules.sys.service.SysRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 角色管理
 *
 */
@Api(tags = "角色管理")
@RestController
@RequestMapping("/admin/role")
public class AppRoleController extends AbstractController {
	@Autowired
	private AppRoleService appRoleService;
	@Autowired
	private AppRoleMenuService appRoleMenuService;

	/**
	 * 角色列表
	 */
	@ApiOperation("角色列表")
	@GetMapping("/list")
	@RequiresPermissions("admin:role:list")
	public R list(@RequestParam Map<String, Object> params){
		//如果不是超级管理员，则只查询自己创建的角色列表
		if(getUserId() != Constant.SUPER_ADMIN){
			params.put("createUserId", getUserId());
		}

		PageUtils page = appRoleService.queryPage(params);

		return R.ok().put("page", page);
	}
	
	/**
	 * 角色列表
	 */
	@ApiOperation("角色查询")
	@GetMapping("/select")
	@RequiresPermissions("admin:role:select")
	public R select(){
		Map<String, Object> map = new HashMap<>();
		/*
		//如果不是超级管理员，则只查询自己所拥有的角色列表
		if(getUserId() != Constant.SUPER_ADMIN){
			map.put("create_user_id", getUserId());
		}*/
		List<AppRoleEntity> list = appRoleService.listByMap(map);
		
		return R.ok().put("list", list);
	}
	
	/**
	 * 角色信息
	 */
	@ApiOperation("根据id获取角色信息")
	@GetMapping("/info/{roleId}")
	@RequiresPermissions("admin:role:info")
	public R info(@PathVariable("roleId") Long roleId){
		AppRoleEntity role = appRoleService.getById(roleId);
		
		//查询角色对应的菜单
		List<Long> menuIdList = appRoleMenuService.queryMenuIdList(roleId);
		role.setMenuIdList(menuIdList);
		
		return R.ok().put("role", role);
	}
	
	/**
	 * 保存角色
	 */
	@ApiOperation("保存角色")
	@SysLog("保存角色")
	@PostMapping("/save")
	@RequiresPermissions("admin:role:save")
	public R save(@RequestBody AppRoleEntity role){
		ValidatorUtils.validateEntity(role);
		
		role.setCreateUserId(getUserId());
		appRoleService.saveRole(role);
		
		return R.ok();
	}
	
	/**
	 * 修改角色
	 */
	@ApiOperation("修改角色")
	@SysLog("修改角色")
	@PostMapping("/update")
	@RequiresPermissions("admin:role:update")
	public R update(@RequestBody AppRoleEntity role){
		ValidatorUtils.validateEntity(role);
		
		role.setCreateUserId(getUserId());
		appRoleService.update(role);
		
		return R.ok();
	}
	
	/**
	 * 删除角色
	 */
	@ApiOperation("删除角色")
	@SysLog("删除角色")
	@PostMapping("/delete")
	@RequiresPermissions("admin:role:delete")
	public R delete(@RequestBody Long[] roleIds){
		appRoleService.deleteBatch(roleIds);
		
		return R.ok();
	}
}
