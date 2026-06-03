package io.cug.modules.gom.controller;

import io.cug.common.utils.DateUtil;
import io.cug.common.utils.R;
import io.cug.modules.admin.entity.AppUserEntity;
import io.cug.modules.app.annotation.Login;
import io.cug.modules.app.annotation.LoginUser;
import io.cug.modules.gom.entity.TunnelEntity;
import io.cug.modules.gom.service.TunnelService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@Api(tags = "隧道环信息")
@RestController
@RequestMapping("gom/Tunnel")
public class TunnelController {

    @Autowired
    private TunnelService tunnelService;

    @Login
    @PostMapping("/list")
    @ApiOperation("查找所有隧道")
    public R classList(@LoginUser AppUserEntity user, @RequestBody TunnelEntity tunnelEntity){
        List<TunnelEntity> list = tunnelService.SelectAll(tunnelEntity.getProjectId());
        return R.ok().put("result", list);
    }

    @Login
    @PostMapping("/add")
    @ApiOperation("增加信息")
    public R add(@LoginUser AppUserEntity user, @RequestBody TunnelEntity tunnelEntity) {
        tunnelEntity.setCreateTime(DateUtil.nowDateTime());

        if (tunnelService.save(tunnelEntity)) {
            return R.ok().put("result", tunnelEntity);
        }
        else {
            return R.error("添加失败!");
        }
    }

    @Login
    @PostMapping("/delete")
    @ApiOperation("删除设信息")
    public R delete(@LoginUser AppUserEntity user, @RequestBody TunnelEntity tunnelEntity){
        tunnelEntity.setStatus(99);
        tunnelService.updateById(tunnelEntity);
        return R.ok().put("result", "删除成功!");
    }

    @Login
    @PostMapping("/edit")
    @ApiOperation("修改信息")
    public R edit(@LoginUser AppUserEntity user, @RequestBody TunnelEntity tunnelEntity){
        tunnelService.updateById(tunnelEntity);
        return R.ok().put("result", "更新成功!");
    }

}
