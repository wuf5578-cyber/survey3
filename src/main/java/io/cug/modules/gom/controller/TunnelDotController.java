package io.cug.modules.gom.controller;

import io.cug.common.utils.R;
import io.cug.modules.admin.entity.AppUserEntity;
import io.cug.modules.app.annotation.Login;
import io.cug.modules.app.annotation.LoginUser;
import io.cug.modules.gom.entity.TunnelDotEntity;
import io.cug.modules.gom.service.TunnelDotService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Api(tags = "隧道测量点位置信息")
@RestController
@RequestMapping("gom/TunnelDot")
public class TunnelDotController {

    @Autowired
    private TunnelDotService tunnelDotService;

    @Login
    @PostMapping("/select")
    @ApiOperation("通过点ID查找")
    public R classList(@LoginUser AppUserEntity user, @RequestBody TunnelDotEntity tunnelDotEntity){
        TunnelDotEntity entity = tunnelDotService.SelectDotByObjectId(tunnelDotEntity.getProjectId(), tunnelDotEntity.getObjectId(), tunnelDotEntity.getPeriodId());
        return R.ok().put("result", entity);
    }

    @Login
    @PostMapping("/add")
    @ApiOperation("增加信息")
    public R add(@LoginUser AppUserEntity user, @RequestBody TunnelDotEntity tunnelDotEntity) {

        if (tunnelDotService.save(tunnelDotEntity)) {
            return R.ok().put("result", tunnelDotEntity);
        }
        else {
            return R.error("添加失败!");
        }
    }

    @Login
    @PostMapping("/delete")
    @ApiOperation("删除设信息")
    public R delete(@LoginUser AppUserEntity user, @RequestBody TunnelDotEntity tunnelDotEntity){
        tunnelDotEntity.setStatus(99);
        tunnelDotService.updateById(tunnelDotEntity);
        return R.ok().put("result", "删除成功!");
    }

    @Login
    @PostMapping("/edit")
    @ApiOperation("修改信息")
    public R edit(@LoginUser AppUserEntity user, @RequestBody TunnelDotEntity tunnelDotEntity){
        tunnelDotService.updateById(tunnelDotEntity);
        return R.ok().put("result", "更新成功!");
    }

}
