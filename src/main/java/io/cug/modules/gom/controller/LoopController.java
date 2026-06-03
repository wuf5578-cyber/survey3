package io.cug.modules.gom.controller;

import io.cug.common.utils.DateUtil;
import io.cug.common.utils.R;
import io.cug.modules.admin.entity.AppUserEntity;
import io.cug.modules.app.annotation.Login;
import io.cug.modules.app.annotation.LoginUser;
import io.cug.modules.gom.entity.LoopEntity;
import io.cug.modules.gom.service.LoopService;
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
@RequestMapping("gom/Loop")
public class LoopController {

    @Autowired
    private LoopService loopService;

    @Login
    @PostMapping("/list")
    @ApiOperation("查找所有环号")
    public R classList(@LoginUser AppUserEntity user, @RequestBody LoopEntity loopEntity){
        List<LoopEntity> list = loopService.SelectAll(loopEntity.getProjectId());
        return R.ok().put("result", list);
    }

    @Login
    @PostMapping("/select")
    @ApiOperation("通过环号查找")
    public R ListByLoopName(@LoginUser AppUserEntity user, @RequestBody LoopEntity loopEntity){
        List<LoopEntity> list = loopService.SelectByLoopName(loopEntity.getProjectId(), loopEntity.getLoopName());
        return R.ok().put("result", list);
    }

    @Login
    @PostMapping("/loopData")
    @ApiOperation("查找所有环号点位信息")
    public R classLoop(@LoginUser AppUserEntity user, @RequestBody LoopEntity loopEntity){
        List<LoopEntity> list = loopService.SelectLoop(loopEntity.getProjectId());
        return R.ok().put("result", list);
    }

    @Login
    @PostMapping("/add")
    @ApiOperation("增加信息")
    public R add(@LoginUser AppUserEntity user, @RequestBody LoopEntity loopEntity) {
        loopEntity.setCreateTime(DateUtil.nowDateTime());

        if (loopService.save(loopEntity)) {
            return R.ok().put("result", loopEntity);
        }
        else {
            return R.error("添加失败!");
        }
    }

    @Login
    @PostMapping("/delete")
    @ApiOperation("删除设信息")
    public R delete(@LoginUser AppUserEntity user, @RequestBody LoopEntity loopEntity){
        loopEntity.setStatus(99);
        loopService.updateById(loopEntity);
        return R.ok().put("result", "删除成功!");
    }

    @Login
    @PostMapping("/edit")
    @ApiOperation("修改信息")
    public R edit(@LoginUser AppUserEntity user, @RequestBody LoopEntity loopEntity){
        loopService.updateById(loopEntity);
        return R.ok().put("result", "更新成功!");
    }

}
