
package io.cug.modules.admin.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import io.cug.modules.admin.entity.AppUserRoleEntity;
import io.cug.modules.admin.service.AppUserRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import io.cug.modules.admin.entity.AppUserEntity;
import io.cug.modules.admin.service.AppUserService;
import io.cug.common.utils.PageUtils;
import io.cug.common.utils.R;



/**
 * 
 * 管理端——会员管理
 * @author
 * @email 2445465217@qq.com
 * @date 2022-01-20 12:10:43
 */
@Api(tags = "管理端——会员管理")
@RestController
@RequestMapping("admin/user")
public class AppUserController {
    @Autowired
    private AppUserService appUserService;

    @Autowired
    private AppUserRoleService appUserRoleService;


    @GetMapping("/list")
    @RequiresPermissions("admin:user:list")
    @ApiOperation("用户列表")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = appUserService.queryPage(params);

        return R.ok().put("page", page);
    }



    @GetMapping("/info/{uid}")
    @RequiresPermissions("admin:user:info")
    @ApiOperation("用户详情")
    public R info(@PathVariable("uid") Integer uid){
		AppUserEntity user = appUserService.getById(uid);
        //获取用户所属的角色列表
        List<Long> roleIdList = appUserRoleService.queryRoleIdList(uid);
        user.setRoleIdList(roleIdList);

        return R.ok().put("user", user);
    }


    @PostMapping("/save")
    @RequiresPermissions("admin:user:save")
    @ApiOperation("用户保存")
    public R save(@RequestBody AppUserEntity user){
		appUserService.save(user);
        appUserRoleService.saveOrUpdate(user.getUid(), user.getRoleIdList());
        return R.ok();
    }


    @PostMapping("/update")
    @RequiresPermissions("admin:user:update")
    @ApiOperation("用户修改")
    public R update(@RequestBody AppUserEntity user){
		appUserService.updateById(user);
        appUserRoleService.saveOrUpdate(user.getUid(), user.getRoleIdList());

        return R.ok();
    }


    @PostMapping("/delete")
    @RequiresPermissions("admin:user:delete")
    @ApiOperation("用户删除")
    public R delete(@RequestBody Integer[] uids){
		appUserService.removeByIds(Arrays.asList(uids));

        return R.ok();
    }


    @PostMapping("/ban/{id}")
    @RequiresPermissions("admin:user:update")
    @ApiOperation("用户禁用")
    public R ban(@PathVariable("id") Integer id){
		appUserService.ban(id);

        return R.ok();
    }


    @PostMapping("/openBan/{id}")
    @RequiresPermissions("admin:user:update")
    @ApiOperation("用户解除禁用")
    public R openBan(@PathVariable("id") Integer id){
		appUserService.openBan(id);

        return R.ok();
    }

}
