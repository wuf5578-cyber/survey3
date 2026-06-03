package io.cug.modules.gom.controller;

import io.cug.common.utils.R;
import io.cug.modules.admin.entity.AppUserEntity;
import io.cug.modules.app.annotation.Login;
import io.cug.modules.app.annotation.LoginUser;
import io.cug.modules.gom.entity.LoopTypeEntity;
import io.cug.modules.gom.service.LoopTypeService;
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
@RequestMapping("gom/LoopType")
public class LoopTypeController {

    @Autowired
    private LoopTypeService loopTypeService;

    @Login
    @PostMapping("/list")
    @ApiOperation("查找所有点号")
    public R classList(@LoginUser AppUserEntity user, @RequestBody LoopTypeEntity loopTypeEntity){
        List<LoopTypeEntity> list = loopTypeService.SelectAll(loopTypeEntity.getObjectId());
        return R.ok().put("result", list);
    }

    /*@Login
    @PostMapping("/select")
    @ApiOperation("通过环号查找")
    public R ListByLoopName(@LoginUser AppUserEntity user, @RequestBody LoopTypeEntity loopTypeEntity){
        List<LoopTypeEntity> list = loopTypeService.SelectByLoopName(loopEntity.getProjectId(), loopEntity.getLoopName());
        return R.ok().put("result", list);
    }*/

    @Login
    @PostMapping("/add")
    @ApiOperation("增加信息")
    public R add(@LoginUser AppUserEntity user, @RequestBody LoopTypeEntity loopTypeEntity) {

        if (loopTypeService.save(loopTypeEntity)) {
            return R.ok().put("result", loopTypeEntity);
        }
        else {
            return R.error("添加失败!");
        }
    }

    @Login
    @PostMapping("/insertObjectType")
    @ApiOperation("增加点类型信息")
    public R insert(@LoginUser AppUserEntity user, @RequestBody LoopTypeEntity loopTypeEntity){
        try {
            loopTypeService.InsertObjectType(loopTypeEntity);
            return R.ok().put("result", loopTypeEntity);
        } catch (Exception e) {
            return R.error("添加 LoopType 信息失败: " + e.getMessage());
        }
    }

    @Login
    @PostMapping("/delete")
    @ApiOperation("删除设信息")
    public R delete(@LoginUser AppUserEntity user, @RequestBody LoopTypeEntity loopTypeEntity){
        loopTypeEntity.setStatus(99);
        loopTypeService.updateById(loopTypeEntity);
        return R.ok().put("result", "删除成功!");
    }

    @Login
    @PostMapping("/edit")
    @ApiOperation("修改信息")
    public R edit(@LoginUser AppUserEntity user, @RequestBody LoopTypeEntity loopTypeEntity){
        loopTypeService.updateById(loopTypeEntity);
        return R.ok().put("result", "更新成功!");
    }

}
