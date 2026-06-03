package io.cug.modules.gom.controller;

import io.cug.common.utils.R;
import io.cug.modules.admin.entity.AppUserEntity;
import io.cug.modules.app.annotation.Login;
import io.cug.modules.app.annotation.LoginUser;
import io.cug.modules.gom.entity.TunnelVaryEntity;
import io.cug.modules.gom.service.TunnelVaryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@Api(tags = "位移、沉降、收敛变化表")
@RestController
@RequestMapping("gom/TunnelVary")
public class TunnelVaryController {

    @Autowired
    private TunnelVaryService tunnelVaryService;

    @Login
    @PostMapping("/selectByPeriodId")
    @ApiOperation("通过周期查找")
    public R classList(@LoginUser AppUserEntity user, @RequestBody TunnelVaryEntity tunnelVaryEntity){
        List<TunnelVaryEntity> list = tunnelVaryService.SelectByPeriodId(tunnelVaryEntity.getProjectId() , tunnelVaryEntity.getLoopId(), tunnelVaryEntity.getPeriodId());
        return R.ok().put("result", list);
    }

    @Login
    @PostMapping("/add")
    @ApiOperation("增加信息")
    public R add(@LoginUser AppUserEntity user, @RequestBody TunnelVaryEntity tunnelVaryEntity) {

        if (tunnelVaryService.save(tunnelVaryEntity)) {
            return R.ok().put("result", tunnelVaryEntity);
        }
        else {
            return R.error("添加失败!");
        }
    }

    @Login
    @PostMapping("/delete")
    @ApiOperation("删除设信息")
    public R delete(@LoginUser AppUserEntity user, @RequestBody TunnelVaryEntity tunnelVaryEntity){
        tunnelVaryEntity.setStatus(99);
        tunnelVaryService.updateById(tunnelVaryEntity);
        return R.ok().put("result", "删除成功!");
    }

    @Login
    @PostMapping("/edit")
    @ApiOperation("修改信息")
    public R edit(@LoginUser AppUserEntity user, @RequestBody TunnelVaryEntity tunnelVaryEntity){
        tunnelVaryService.updateById(tunnelVaryEntity);
        return R.ok().put("result", "更新成功!");
    }

    @Login
    @PostMapping("/AnalyseData")
    @ApiOperation("查找断面沉降数据")
    public R analyseList(@LoginUser AppUserEntity user, @RequestBody TunnelVaryEntity tunnelVaryEntity){
        List<TunnelVaryEntity> list = tunnelVaryService.SelectAnalyseData(
                tunnelVaryEntity.getLoopId(),
                tunnelVaryEntity.getStartTime(),
                tunnelVaryEntity.getEndTime());
        return R.ok().put("result", list);
    }

}
