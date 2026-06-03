package io.cug.modules.gom.controller;

import io.cug.common.utils.R;
import io.cug.modules.admin.entity.AppUserEntity;
import io.cug.modules.app.annotation.Login;
import io.cug.modules.app.annotation.LoginUser;
import io.cug.modules.gom.entity.TunnelStringEntity;
import io.cug.modules.gom.service.TunnelStringService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Api(tags = "断面弦长变化")
@RestController
@RequestMapping("gom/TunnelString")
public class TunnelStringController {

    @Autowired
    private TunnelStringService tunnelStringService;

    @Login
    @PostMapping("/select")
    @ApiOperation("通过环号查找")
    public R classList(@LoginUser AppUserEntity user, @RequestBody TunnelStringEntity tunnelStringEntity){
        TunnelStringEntity entity = tunnelStringService.SelectByStringLoopName(tunnelStringEntity.getPeriodId() , tunnelStringEntity.getLoopName());
        return R.ok().put("result", entity);
    }

    @Login
    @PostMapping("/add")
    @ApiOperation("增加信息")
    public R add(@LoginUser AppUserEntity user, @RequestBody TunnelStringEntity tunnelStringEntity) {

        if (tunnelStringService.save(tunnelStringEntity)) {
            return R.ok().put("result", tunnelStringEntity);
        }
        else {
            return R.error("添加失败!");
        }
    }

    @Login
    @PostMapping("/delete")
    @ApiOperation("删除设信息")
    public R delete(@LoginUser AppUserEntity user, @RequestBody TunnelStringEntity tunnelStringEntity){
        tunnelStringEntity.setStatus(99);
        tunnelStringService.updateById(tunnelStringEntity);
        return R.ok().put("result", "删除成功!");
    }

    @Login
    @PostMapping("/edit")
    @ApiOperation("修改信息")
    public R edit(@LoginUser AppUserEntity user, @RequestBody TunnelStringEntity tunnelStringEntity){
        tunnelStringService.updateById(tunnelStringEntity);
        return R.ok().put("result", "更新成功!");
    }

}
