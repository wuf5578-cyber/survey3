package io.cug.modules.gom.controller;

import io.cug.common.utils.DateUtil;
import io.cug.common.utils.R;
import io.cug.modules.admin.entity.AppUserEntity;
import io.cug.modules.app.annotation.Login;
import io.cug.modules.app.annotation.LoginUser;
import io.cug.modules.gom.entity.AutoComputeEntity;
import io.cug.modules.gom.entity.AutoComputeGroupEntity;
import io.cug.modules.gom.service.AutoComputeGroupService;
import io.cug.modules.gom.service.AutoComputeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@Api(tags = "监测——测量")
@RestController
@RequestMapping("gom/AutoCompute")
public class AutoComputeController {

    @Autowired
    private AutoComputeService autoComputeService;

    @Login
    @PostMapping("/List")
    @ApiOperation("列表")
    public R classList(@LoginUser AppUserEntity user, @RequestBody AutoComputeEntity autoComputeEntity){
        List<AutoComputeEntity> list = autoComputeService.QueryValidCompute(autoComputeEntity.getGroupId());
        return R.ok().put("result", list);
    }

    @Login
    @PostMapping("/add")
    @ApiOperation("增加")
    public R add(@LoginUser AppUserEntity user, @RequestBody AutoComputeEntity autoComputeEntity) {
        autoComputeEntity.setCreateTime(DateUtil.nowDateTime());
        if (autoComputeService.save(autoComputeEntity)) {
            return R.ok().put("result", autoComputeEntity);
        }
        else {
            return R.error("添加失败!");
        }
    }

    @Login
    @PostMapping("/delete")
    @ApiOperation("删除")
    public R delete(@LoginUser AppUserEntity user, @RequestBody AutoComputeEntity autoComputeEntity){
        autoComputeEntity.setStatus(99);//99-删除状态
        autoComputeService.updateById(autoComputeEntity);
        return R.ok().put("result", "删除成功!");
    }

    @Login
    @PostMapping("/edit")
    @ApiOperation("修改")
    public R edit(@LoginUser AppUserEntity user, @RequestBody AutoComputeEntity autoComputeEntity){
        autoComputeService.updateById(autoComputeEntity);
        return R.ok().put("result", "更新成功!");
    }
}
